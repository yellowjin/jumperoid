package com.example.wlf.jumper.devices;

import android.util.Log;

public class DeviceManager {
    private final Thread pButtonMonitorThread = new Thread(PushButtonsMonitor.getInstance());
    private Thread drawDotMatrixThread = new Thread(DrawDotMatrix.getInstance());

    private boolean embeddedUse = false;

    protected static class Singleton {
             private static final DeviceManager instance = new DeviceManager();
    }

    public static DeviceManager getInstance() { return DeviceManager.Singleton.instance; }

    public DeviceManager () {
        if(LCD.getInstance().init() == 0) {
            embeddedUse = true;
            LED.getInstance().ledClear();
            SevenSegment.getInstance().SSegmentWrite(0);
        } else {
            Log.d("DeviceManager", "device init error");
            DrawDotMatrix.getInstance().terminate();
            PushButtonsMonitor.getInstance().terminate();
        }
    }

    public void start() {
        pButtonMonitorThread.start();
        drawDotMatrixThread.start();
        Log.d("DeviceManager", "start threads");
    }

    public void join() {
        LED.getInstance().ledClear();
        SevenSegment.getInstance().SSegmentWrite(0);
        LCD.getInstance().init();

        DrawDotMatrix.getInstance().terminate();
        PushButtonsMonitor.getInstance().terminate();
        try {
            pButtonMonitorThread.join();
            drawDotMatrixThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isEmbeddedUse() {
        return embeddedUse;
    }
}
