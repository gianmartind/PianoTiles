package com.example.pianotiles.presenter;

public class SensorThread extends Thread{
    protected ThreadHandler threadHandler;
    float azimuth;
    int counter;
    boolean isLeft;

    public SensorThread(ThreadHandler threadHandler, boolean isLeft){
        this.threadHandler = threadHandler;
        this.isLeft = isLeft;
        this.azimuth = 0;
        this.counter = 0;
    }

    public void changeAzimuth(float azimuth){
        this.azimuth = azimuth;
    }

    public void run(){
        while (this.counter < 2000){
            if(checkValid()){
                this.threadHandler.addSensorScore();
                return;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.threadHandler.removeHealth();
        return;
    }

    public boolean checkValid(){
        if(this.isLeft){
            if(azimuth < -0.6f){
                return true;
            }
        } else if(!this.isLeft){
            if(this.azimuth > 0.6f){
                return true;
            }
        }
        return false;
    }
}
