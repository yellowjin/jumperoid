package com.example.wlf.jumper.devices;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.wlf.jumper.MainActivity;
import com.example.wlf.jumper.engine.Game;

public class PushButtonsMonitor implements Runnable{
    private boolean isRunning = true;
    private boolean startLock = false;
    private boolean retryLock = false;

    private Handler mainHandler;
    private Game game = null;

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
        while (isRunning) {
            char pressedBtn = PushButtons.getInstance().getPressedKeyFromDevice();
            action(pressedBtn);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("PushButtonsMonitor", "terminate");
    }

    public void terminate() {
        isRunning = false;
    }

    public void action(char pressedBtn){
        if (pressedBtn == 0x0000){
            return;
        }
        Log.d("PushButtonMonitor", String.format("valid: %b, press : 0x%04X", game.valid(), (int) pressedBtn));
        Message msg = Message.obtain();
        switch (pressedBtn){
            // 1: start button
            case 0x0001:
                if (!game.valid() && !startLock) {
                    msg.what = 1;
                    mainHandler.sendMessage(msg);
                    startLock = true;
                }
                break;
            // 3 : retry button
            case 0x0004:
                if (game.valid() && !retryLock){
                    msg.what = 3;
                    mainHandler.sendMessage(msg);
                    retryLock = true;
                }
                break;
            // 5 : jump button
            case 0x0010:
                if (game.valid())
                    game.jump();
                break;
            // 79 : exit button
            case 0x0140:
//                DotMatrix.getInstance().init();
                msg.what = 9;
                mainHandler.sendMessage(msg);
                break;
            default:
                break;
        }
    }

    public void setRetryUnLock() { retryLock = false;}
}
