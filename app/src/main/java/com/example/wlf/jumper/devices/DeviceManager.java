package com.example.wlf.jumper.devices;

import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DeviceManager {
    private final Thread pButtonMonitorThread = new Thread(PushButtonsMonitor.getInstance());
    private final Thread drawDotMatrixThread = new Thread(DrawDotMatrix.getInstance());

    public ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private boolean embeddedUse = false;

    protected static class Singleton {
             private static final DeviceManager instance = new DeviceManager();
    }

    public static DeviceManager getInstance() { return DeviceManager.Singleton.instance; }

    public DeviceManager () {
        if(LCD.getInstance().init() == 0) {
            embeddedUse = true;
            LED.getInstance().ledClear();
            DotMatrix.getInstance().init();
            LCD.getInstance().init();
            LCD.getInstance().writeLCDString(" Press 1 button ", "     START!    ");
            SevenSegment.getInstance().SSegmentWrite(0);
//            PushButtonsMonitor.getInstance().setScheduler(scheduler);
//            DrawDotMatrix.getInstance().setScheduler(scheduler);
        } else {
            Log.d("DeviceManager", "device init error");
            DrawDotMatrix.getInstance().terminate();
            PushButtonsMonitor.getInstance().terminate();
        }
    }

    public void start() {
        pButtonMonitorThread.setName("pButtonMonitorThread");
        drawDotMatrixThread.setName("drawDotMatrixThread");
//        pButtonMonitorThread.setPriority(10);
//        drawDotMatrixThread.setPriority(10);
        pButtonMonitorThread.start();
        drawDotMatrixThread.start();

        Log.d("DeviceManager", "start threads");
    }

    public void LCDWrite(){
        if (DeviceManager.getInstance().isEmbeddedUse()){
            new Thread(() -> LCD.getInstance().lcdWrite()).start();
        }
    }
    public void join() {
        try {
            scheduler.shutdown();
            PushButtonsMonitor.getInstance().scheduler.shutdown();
            DrawDotMatrix.getInstance().scheduler.shutdown();
            DrawDotMatrix.getInstance().terminate();
            PushButtonsMonitor.getInstance().terminate();
            pButtonMonitorThread.join();
            drawDotMatrixThread.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (isEmbeddedUse()) {
            LED.getInstance().ledClear();
            SevenSegment.getInstance().SSegmentWrite(0);
            DotMatrix.getInstance().init();
            LCD.getInstance().init();
        }
    }
    public boolean isEmbeddedUse() {
        return embeddedUse;
    }
}
