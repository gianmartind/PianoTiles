package com.example.pianotiles.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pianotiles.FragmentListener;
import com.example.pianotiles.R;
import com.example.pianotiles.presenter.MainFragmentPresenter;

public class MainFragment extends Fragment implements MainFragmentPresenter.IMainFragment, View.OnClickListener, View.OnTouchListener, SensorEventListener {
    FragmentListener fragmentListener;
    MainFragmentPresenter mainFragmentPresenter;
    Button startButton;
    ImageView ivCanvas;
    Canvas canvas;
    Bitmap bitmap;
    Button score, health;
    Boolean initiated;
    CustomToast toast;
    SensorManager sensorManager;
    Sensor accelerometer, magnetometer;
    float[] accelerometerReading, magnetometerReading;

    public MainFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast, container, false);
        this.toast = new CustomToast(toastView, getActivity().getApplicationContext());
        this.ivCanvas = view.findViewById(R.id.ivCanvas);
        this.startButton = view.findViewById(R.id.start_btn);
        this.score = view.findViewById(R.id.score);
        this.health = view.findViewById(R.id.health);

        this.startButton.setOnClickListener(this);
        this.health.setOnClickListener(this);

        this.ivCanvas.setOnTouchListener(this);
        this.mainFragmentPresenter = new MainFragmentPresenter(this, this.toast);

        this.initiated = false;

        this.sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        this.accelerometerReading = new float[3];
        this.magnetometerReading = new float[3];
        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fragmentListener = (FragmentListener) context;
        } else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void updateCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.ivCanvas.postInvalidate();
    }

    @Override
    public void initiateCanvas(Bitmap bitmap, Canvas canvas) {
        this.bitmap = bitmap;
        this.ivCanvas.setImageBitmap(this.bitmap);
        this.canvas = canvas;
    }

    @Override
    public void updateScore(int score) {
        this.score.setText(Integer.toString(score));
    }

    @Override
    public void updateHealth(int health) {
        if(health > 0){
            this.health.setText(Integer.toString(health));
        }
    }

    @Override
    public void gameOver(int score) {
        this.fragmentListener.setScore(score);
        this.fragmentListener.changePage(2);
    }

    @Override
    public void registerSensor() {
        if(this.accelerometer != null){
            this.sensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(this.magnetometer != null){
            this.sensorManager.registerListener(this, this.magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void unregisterSensor() {
        this.sensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == this.startButton){
            if(!this.initiated){
                this.mainFragmentPresenter.initiateCanvas(this.ivCanvas);
                this.startButton.setText("STOP");
                this.startButton.setVisibility(View.INVISIBLE);
                this.score.setVisibility(View.VISIBLE);
                this.health.setVisibility(View.VISIBLE);
                this.initiated = true;
            } else {
                this.mainFragmentPresenter.stop();
                this.startButton.setText("START");
                this.initiated = false;
            }
        } else if(v == this.health){
            this.mainFragmentPresenter.removeHealth();
            this.mainFragmentPresenter.removeHealth();
            this.mainFragmentPresenter.removeHealth();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.mainFragmentPresenter.checkScore(new PointF(event.getX(), event.getY()));
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                this.accelerometerReading = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.magnetometerReading = event.values.clone();
                break;
        }

        final float[] rotationMatrix = new float[9];
        this.sensorManager.getRotationMatrix(rotationMatrix, null, this.accelerometerReading, this.magnetometerReading);

        final float[] orientationAngles = new float[3];
        this.sensorManager.getOrientation(rotationMatrix, orientationAngles);

        float azimuth = orientationAngles[0];
        //float pitch = orientationAngles[1];
        //float roll = orientationAngles[2];
        //Log.d("TAG", "onSensorChanged: " + azimuth);
        this.mainFragmentPresenter.checkSensor(azimuth);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
