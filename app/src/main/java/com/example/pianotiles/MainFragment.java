package com.example.pianotiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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

import java.util.Random;

public class MainFragment extends Fragment implements MainFragmentPresenter.IMainFragment, View.OnClickListener, View.OnTouchListener {
    FragmentListener fragmentListener;
    MainFragmentPresenter mainFragmentPresenter;
    Button startButton;
    ImageView ivCanvas;
    Canvas canvas;
    Bitmap bitmap;
    TextView score, health;
    Boolean initiated;

    public MainFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.ivCanvas = view.findViewById(R.id.ivCanvas);
        this.startButton = view.findViewById(R.id.start_btn);
        this.score = view.findViewById(R.id.score);
        this.health = view.findViewById(R.id.health);
        this.startButton.setOnClickListener(this);

        this.ivCanvas.setOnTouchListener(this);
        this.mainFragmentPresenter = new MainFragmentPresenter(this);

        this.initiated = false;
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
    public void onClick(View v) {
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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.mainFragmentPresenter.checkScore(new PointF(event.getX(), event.getY()));
        return true;
    }
}
