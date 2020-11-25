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

import java.util.Random;

public class MainFragmentPresenter {
    IMainFragment ui;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    Paint transparent;
    ThreadHandler threadHandler;
    PointF viewSize;

    public MainFragmentPresenter(IMainFragment ui){
        this.ui = ui;
        this.threadHandler = new ThreadHandler(this);
    }

    public void initiateCanvas(ImageView ivCanvas){
        this.bitmap = Bitmap.createBitmap(ivCanvas.getWidth(), ivCanvas.getHeight(), Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        this.transparent = new Paint();
        this.transparent.setColor(Color.WHITE);
        //this.transparent.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        this.viewSize = new PointF(ivCanvas.getWidth(), ivCanvas.getHeight());
        this.ui.initiateCanvas(this.bitmap, this.canvas);
    }

    public void drawRect(RectPoint point){
        PointF start = point.getStart();
        PointF end = point.getEnd();
        this.canvas.drawRect(start.x, start.y, end.x, end.y, this.paint);
        this.ui.updateCanvas(this.canvas);
    }

    public void clearRect(RectPoint point){
        PointF start = point.getStart();
        PointF end = point.getEnd();
        this.canvas.drawRect(start.x, start.y, end.x, end.y, this.transparent);
        Log.d("TAG", "clearRect: ");
        this.ui.updateCanvas(this.canvas);
    }

    public void test(){
        Random random = new Random();
        int pos = random.nextInt(3) + 1;
        MainThread newThread = new MainThread(this.threadHandler, pos, this.viewSize);
        newThread.start();
    }

    public interface IMainFragment{
        void updateCanvas(Canvas canvas);
        void initiateCanvas(Bitmap bitmap, Canvas canvas);
    }
}
