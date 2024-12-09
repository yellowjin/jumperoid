package com.example.wlf.jumper.devices;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DrawDotMatrix implements Runnable{
    private static final int INTERVAL = 1000;

    public static final int NONE = 4;
    public static final int RUNNING = 1;
    public static final int CLEAR = 2;
    public static final int OVER = 3;

    private boolean isRunning = true;

    private int draw = 0;

    public ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void setScheduler(ScheduledExecutorService scheduler) { this.scheduler = scheduler; }
    protected static class Singleton {
        private static final DrawDotMatrix instance = new DrawDotMatrix();
    }
    public static DrawDotMatrix getInstance() { return DrawDotMatrix.Singleton.instance; }

    @Override
    public void run() {
//        Log.d("DrawDotMatrix", "start thread");
//        while (isRunning) {
//            Log.d("DrawDotMatrix", "draw : " + draw);
//            long beforeTime = System.currentTimeMillis();
//            drawing();
//            long afterTime = System.currentTimeMillis();
//            long executionTime = afterTime - beforeTime;
//            if (executionTime < INTERVAL){
//                try {
//                    long interval = INTERVAL - executionTime;
//                    Thread.sleep(interval);
//                    Log.d("DrawDotMatrix", "drawDotMatrix sleep interval : " + (interval));
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            else {
//                Log.d("DrawDotMatrix", "drawDotMatrix execution time : " + (executionTime));
//            }

            scheduler.scheduleWithFixedDelay(() -> {
                long beforeTime = System.currentTimeMillis();
                drawing();
                long afterTime = System.currentTimeMillis();
                long executionTime = afterTime - beforeTime;
//                if (executionTime > INTERVAL) {
//                    Log.d("DrawDotMatrix", "drawDotMatrix execution time : " + (executionTime));
//                }
            }, 0, INTERVAL, TimeUnit.MILLISECONDS);

//        }
//        Log.d("DrawDotMatrix", "terminate thread");
    }
    public void setDraw(int draw) {
        Log.d("DrawDotMatrix", "setDraw : " + draw);
        this.draw = draw;
    }
    public void terminate() {
        isRunning = false;
    }
    public synchronized void drawing() {
//        Log.d("DeviceMonitorThread", "drawDotMatrix : " + draw);
        switch (draw){
            case NONE:
                DotMatrix.getInstance().init();
                break;
            case RUNNING:
                DotMatrix.getInstance().writeRunning();
                break;
            case CLEAR:
                DotMatrix.getInstance().writeGameClear();
                break;
            case OVER:
                DotMatrix.getInstance().writeGameOver();
                break;
        }
    }
}
