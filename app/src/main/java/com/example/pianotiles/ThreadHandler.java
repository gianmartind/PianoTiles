package com.example.pianotiles;

import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class ThreadHandler extends Handler {
    protected final static int DRAW_RECT = 1;
    protected final static int CLEAR_RECT = 2;
    protected final static int ADD_SCORE = 3;
    protected final static int POP_OUT = 4;
    protected final static int GENERATE = 5;
    protected final static int REMOVE_HEALTH = 6;

    protected MainFragmentPresenter mainFragmentPresenter;

    public ThreadHandler(MainFragmentPresenter mainFragmentPresenter){
        this.mainFragmentPresenter = mainFragmentPresenter;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if(msg.what == ThreadHandler.DRAW_RECT){
            PointF param = (PointF) msg.obj;
            this.mainFragmentPresenter.drawRect(param);
        } else if(msg.what == ThreadHandler.CLEAR_RECT){
            PointF param = (PointF) msg.obj;
            this.mainFragmentPresenter.clearRect(param);
        } else if(msg.what == ThreadHandler.ADD_SCORE){
            this.mainFragmentPresenter.addScore();
        } else if(msg.what == ThreadHandler.POP_OUT){
            this.mainFragmentPresenter.popOut();
        } else if(msg.what == ThreadHandler.GENERATE){
            this.mainFragmentPresenter.test();
        } else if(msg.what == ThreadHandler.REMOVE_HEALTH){
            this.mainFragmentPresenter.removeHealth();
        }
    }

    public void drawRect(PointF point, boolean isClicked){
        if(isClicked) return;
        Message msg = new Message();
        msg.what = ThreadHandler.DRAW_RECT;
        msg.obj = point;
        this.sendMessage(msg);
    }

    public void clearRect(PointF point){
        Message msg = new Message();
        msg.what = ThreadHandler.CLEAR_RECT;
        msg.obj = point;
        this.sendMessage(msg);
    }

    public void addScore(){
        Message msg = new Message();
        msg.what = ThreadHandler.ADD_SCORE;
        this.sendMessage(msg);
    }

    public void removeHealth(){
        Message msg = new Message();
        msg.what = ThreadHandler.REMOVE_HEALTH;
        this.sendMessage(msg);
    }

    public void popOut(){
        Message msg = new Message();
        msg.what = ThreadHandler.POP_OUT;
        this.sendMessage(msg);
    }

    public void generate(){
        Message msg = new Message();
        msg.what = ThreadHandler.GENERATE;
        this.sendMessage(msg);
    }
}
