package com.example.wlf.jumper.devices;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.wlf.jumper.engine.Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PushButtonsMonitor implements Runnable{
    private static final int INTERVAL = 80;
    private boolean isRunning = true;
    private boolean startLock = false;
    private boolean retryLock = false;


    private Handler mainHandler;
    private Game game = null;

    public ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    public void setScheduler(ScheduledExecutorService scheduler) { this.scheduler = scheduler; }
    public void setGame(Game game) {
        this.game = game;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }
    protected static class Singleton {
        private static final PushButtonsMonitor instance = new PushButtonsMonitor();
    }
    public static PushButtonsMonitor getInstance() { return PushButtonsMonitor.Singleton.instance; }

    @Override
    public void run() {
        Log.d("PushButtonsMonitor", "start thread");
        while (isRunning) {
            long beforeTime = System.currentTimeMillis();
            char pressedBtn = PushButtons.getInstance().getPressedKeyFromDevice();
            action(pressedBtn);
            long afterTime = System.currentTimeMillis();
            long executionTime = afterTime - beforeTime;

            if (executionTime < INTERVAL){
                try {
                    Thread.sleep(INTERVAL-executionTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
//                Log.d("PushButtonsMonitor", "pressBtn execution time : " + (executionTime));
            }
        }

//        scheduler.scheduleWithFixedDelay(() -> {
//            long beforeTime = System.currentTimeMillis();
//            char pressedBtn = PushButtons.getInstance().getPressedKeyFromDevice();
//            action(pressedBtn);
//            long afterTime = System.currentTimeMillis();
//            long executionTime = afterTime - beforeTime;
//            if (executionTime > INTERVAL) {
//                Log.d("PushButtonsMonitor", "PushButtonsMonitor execution time : " + (executionTime));
//            }
//        }, 0, INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void terminate() {
        isRunning = false;
    }

    public void action(char pressedBtn){
        if (pressedBtn == 0x0000){
            return;
        }
        Message msg = Message.obtain();
        switch (pressedBtn){
            // 1: start button
            case 0x0001:
                if (!game.valid() && !startLock) {
                    startLock = true;
                    msg.what = 1;
                    mainHandler.sendMessage(msg);
//                    Log.d("DeviceMonitorThread", String.format("valid: %b, press : 0x%04X", game.valid(), (int) pressedBtn));
//                    try {
//                        Thread.sleep(INTERVAL);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                }
                break;
            // 3 : retry button
            case 0x0004:
                if (game.valid() && !retryLock){
                    retryLock = true;
                    msg.what = 3;
                    mainHandler.sendMessage(msg);
//                    Log.d("DeviceMonitorThread", String.format("valid: %b, press : 0x%04X", game.valid(), (int) pressedBtn));
//                    try {
//                        Thread.sleep(INTERVAL);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                }
                break;
            // 5 : jump button
            case 0x0010:
                if (game.valid()){
                    game.jump();
//                    Log.d("DeviceMonitorThread", String.format("valid: %b, press : 0x%04X", game.valid(), (int) pressedBtn));
                }
//                    try {
//                        Thread.sleep(INTERVAL);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                break;
            // 79 : exit button
            case 0x0140:
                msg.what = 9;
                mainHandler.sendMessage(msg);
//                Log.d("DeviceMonitorThread", String.format("valid: %b, press : 0x%04X", game.valid(), (int) pressedBtn));
//                try {
//                    Thread.sleep(INTERVAL);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                break;
            default:
                break;
        }
    }

    public void setRetryUnLock() {
        Log.d("PushButtonsMonitor", "setRetryUnLock");
        retryLock = false;
    }
}
