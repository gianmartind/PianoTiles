package com.example.pianotiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MainFragmentPresenter {
    IMainFragment ui;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    Paint transparent;
    ThreadHandler threadHandler;
    LinkedList<MainThread> threads;
    PlayThread playThread;
    PointF viewSize;
    float endPointY;
    float endPointX;
    int score;
    int health;

    public MainFragmentPresenter(IMainFragment ui){
        this.ui = ui;
        this.threadHandler = new ThreadHandler(this);
        this.threads = new LinkedList<>();
    }

    public void initiateCanvas(ImageView ivCanvas){
        this.bitmap = Bitmap.createBitmap(ivCanvas.getWidth(), ivCanvas.getHeight(), Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        this.transparent = new Paint();
        this.transparent.setColor(Color.TRANSPARENT);
        this.transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        this.viewSize = new PointF(ivCanvas.getWidth(), ivCanvas.getHeight());
        this.ui.initiateCanvas(this.bitmap, this.canvas);

        this.endPointY = this.viewSize.y/4;
        this.endPointX = this.viewSize.x/4;

        this.score = 0;
        this.ui.updateScore(this.score);

        this.health = 3;
        this.ui.updateHealth(this.health);

        this.playThread = new PlayThread(new PointF(ivCanvas.getWidth(), ivCanvas.getHeight()), this.threadHandler);
        this.playThread.start();
    }

    public void drawRect(PointF point){
        this.canvas.drawRect(point.x, point.y, point.x+this.endPointX, point.y+this.endPointY, this.paint);
        //this.canvas.drawCircle(point.x, point.y, 100, this.paint);
        this.ui.updateCanvas(this.canvas);
    }

    public void clearRect(PointF point){
        this.canvas.drawRect(point.x, point.y, point.x+this.endPointX, point.y+this.endPointY, this.transparent);
        //this.canvas.drawCircle(point.x, point.y, 100, this.transparent);
        this.ui.updateCanvas(this.canvas);
    }

    public void test(){
        Random random = new Random();
        int pos = random.nextInt(4) + 1;
        MainThread newThread = new MainThread(this.threadHandler, pos, this.viewSize);
        newThread.start();
        this.threads.addLast(newThread);
    }

    public void stop(){
        this.playThread.stopThread();
    }

    public void checkScore(PointF tap){
        for(int i = 0; i < this.threads.size(); i++){
            this.threads.get(i).checkScore(tap);
        }
    }

    public void popOut(){
        Log.d("TAG", "popOut: " + this.threads.size());
        this.threads.removeFirst();
    }

    public void addScore(){
        this.score++;
        this.ui.updateScore(score);
    }

    public void removeHealth(){
        this.health--;
        if(this.health == 0){
            this.playThread.stopThread();
        }
        this.ui.updateHealth(this.health);
    }

    public interface IMainFragment{
        void updateCanvas(Canvas canvas);
        void initiateCanvas(Bitmap bitmap, Canvas canvas);
        void updateScore(int score);
        void updateHealth(int health);
    }
}
