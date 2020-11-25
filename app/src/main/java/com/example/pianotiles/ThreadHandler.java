package com.example.pianotiles;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public class ThreadHandler extends Handler {
    protected final static int DRAW_RECT = 1;
    protected final static int CLEAR_RECT = 2;

    protected MainFragmentPresenter mainFragmentPresenter;

    public ThreadHandler(MainFragmentPresenter mainFragmentPresenter){
        this.mainFragmentPresenter = mainFragmentPresenter;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if(msg.what == ThreadHandler.DRAW_RECT){
            RectPoint param = (RectPoint) msg.obj;
            this.mainFragmentPresenter.drawRect(param);
        } else if(msg.what == ThreadHandler.CLEAR_RECT){
            RectPoint param = (RectPoint) msg.obj;
            this.mainFragmentPresenter.clearRect(param);
        }
    }

    public void drawRect(RectPoint point){
        Message msg = new Message();
        msg.what = ThreadHandler.DRAW_RECT;
        msg.obj = point;
        this.sendMessage(msg);
    }

    public void clearRect(RectPoint point){
        Message msg = new Message();
        msg.what = ThreadHandler.CLEAR_RECT;
        msg.obj = point;
        this.sendMessage(msg);
    }
}
