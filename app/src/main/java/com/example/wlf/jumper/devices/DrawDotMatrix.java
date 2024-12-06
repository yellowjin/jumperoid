package com.example.wlf.jumper.devices;

import android.util.Log;

public class DrawDotMatrix implements Runnable{
    private boolean isRunning = true;

    private int draw = 0;

    protected static class Singleton {
        private static final DrawDotMatrix instance = new DrawDotMatrix();
    }
    public static DrawDotMatrix getInstance() { return DrawDotMatrix.Singleton.instance; }

    @Override
    public void run() {
        while (isRunning) {
            Log.d("DrawDotMatrix", "draw : " + draw);
            switch (draw){
                case 0:
//                    DotMatrix.getInstance().init();
                    break;
                case 1:
                    DotMatrix.getInstance().writeRunning();
                    break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }
    public void terminate() {
        isRunning = false;
    }
}
