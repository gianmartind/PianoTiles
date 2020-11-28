package com.example.pianotiles;

import android.graphics.PointF;
import android.util.Log;

import java.util.Random;

public class PlayThread extends Thread{
    protected PointF viewSize;
    protected boolean isStopped;
    ThreadHandler threadHandler;
    Random random;

    public PlayThread(PointF viewSize, ThreadHandler threadHandler){
        this.random = new Random();
        this.viewSize = viewSize;
        this.threadHandler = threadHandler;
        this.isStopped = false;
    }

    public void stopThread(){
        this.isStopped = true;
    }

    public void run(){
        int exclude = this.random.nextInt(4) + 1;
        while(!this.isStopped){
            int num = this.getRandomWithExclusion(1, 4, exclude);
            try {
                Thread.sleep((long) (this.viewSize.y/2.5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.threadHandler.generate(num);
            exclude = num;
        }
        return;
    }

    public int getRandomWithExclusion(int start, int end, int... exclude) {
        int random = start + this.random.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }
}
