package com.example.pianotiles;

import android.graphics.PointF;
import android.util.Log;

public class PlayThread extends Thread{
    protected PointF viewSize;
    protected boolean isStopped;
    ThreadHandler threadHandler;

    public PlayThread(PointF viewSize, ThreadHandler threadHandler){
        this.viewSize = viewSize;
        this.threadHandler = threadHandler;
        this.isStopped = false;
    }

    public void stopThread(){
        this.isStopped = true;
    }

    public void run(){
        while(!this.isStopped){
            try {
                Thread.sleep((long) (this.viewSize.y/2.5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadHandler.generate();
        }
        return;
    }
}
