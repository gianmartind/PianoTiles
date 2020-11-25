package com.example.pianotiles;

import android.graphics.PointF;
import android.util.Log;

public class MainThread extends Thread{
    protected ThreadHandler threadHandler;
    protected int pos;
    protected PointF viewSize;
    protected PointF start;
    protected PointF end;

    protected boolean isClicked;


    public MainThread(ThreadHandler threadHandler, int pos, PointF viewSize){
        this.threadHandler = threadHandler;
        this.pos = pos;
        this.viewSize = viewSize;
        this.isClicked = false;
        this.start = new PointF();
        this.end = new PointF();
        this.setPoint();
    }

    public void setPoint(){
        if(this.pos == 1){
            this.start.set(0, -40);
            this.end.set(this.viewSize.x/4, 0);
        } else if(this.pos == 2){
            this.start.set(this.viewSize.x/4, -40);
            this.end.set(this.viewSize.x/2, 0);
        } else if(this.pos == 3){
            this.start.set(this.viewSize.x/2, -40);
            this.end.set(this.viewSize.x/4*3, 0);
        } else if(this.pos == 4){
            this.start.set(this.viewSize.x/4*3, -40);
            this.end.set(this.viewSize.x, 0);
        }
    }

    public void run(){

        while(check()){
            Log.d("TAG", "run: ");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.threadHandler.clearRect(new RectPoint(this.start, this.end));
            this.start.y += 10;
            this.end.y += 10;
            this.threadHandler.drawRect(new RectPoint(this.start, this.end));
        }
        return;
    }

    public boolean check(){
        if(this.start.y >= this.viewSize.y){
            return false;
        }
        return true;
    }
}
