package com.example.pianotiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment implements MainFragmentPresenter.IMainFragment, View.OnClickListener {
    MainFragmentPresenter mainFragmentPresenter;
    Button startButton;
    ImageView ivCanvas;
    Canvas canvas;
    Bitmap bitmap;
    TextView score;
    Boolean initiated;

    public MainFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.ivCanvas = view.findViewById(R.id.ivCanvas);
        this.startButton = view.findViewById(R.id.start_btn);
        this.score = view.findViewById(R.id.score);
        this.startButton.setOnClickListener(this);

        this.mainFragmentPresenter = new MainFragmentPresenter(this);

        this.initiated = false;
        return view;
    }

    @Override
    public void updateCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.ivCanvas.invalidate();
    }

    @Override
    public void initiateCanvas(Bitmap bitmap, Canvas canvas) {
        this.bitmap = bitmap;
        this.ivCanvas.setImageBitmap(this.bitmap);
        this.canvas = canvas;
    }

    @Override
    public void onClick(View v) {
        if(!this.initiated){
            this.mainFragmentPresenter.initiateCanvas(this.ivCanvas);
            this.startButton.setText("TEST");
            this.initiated = true;
        } else {
            this.mainFragmentPresenter.test();
        }
    }
}
