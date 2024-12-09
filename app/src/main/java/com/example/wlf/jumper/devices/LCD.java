package com.example.wlf.jumper.devices;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.wlf.jumper.elements.GameStatus;

public class LCD {
    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI LED
    static {
        System.loadLibrary("lcd");
    }

    private static final int MAX_LEVEL = 40;

    protected static class Singleton {
        private static final LCD instance = new LCD();
    }

    public static LCD getInstance() { return LCD.Singleton.instance; }

    public native int init();
    public native int writeLCD(int current);
    public native int writeLCDString(String first, String second);

    public void lcdWrite(){

        int currentLevel = GameStatus.getInstance().getLevel();
//        int progress = currentLevel * 100 / MAX_LEVEL;
//        @SuppressLint("DefaultLocale")
//        StringBuilder first = new StringBuilder(String.format("LEVEL:%2d", currentLevel));
//        for (int i =0; i < 8; i++){
//            first.append(" ");
//        }
//
//        @SuppressLint("DefaultLocale") StringBuilder second = new StringBuilder(String.format("%3d", progress));
//        second.append("%");
//        second.append("[");
//        for (int i = 0; i < 10; i++) {
//            if (i < progress / 10)
//                second.append("=");
//            else if (i == progress / 10)
//                second.append("*");
//            else
//                second.append("-");
//        }
//        second.append("]");
        long beforeTime = System.currentTimeMillis();
        if (DeviceManager.getInstance().isEmbeddedUse())
            writeLCD(currentLevel);
//            LCD.getInstance().writeLCD(first.toString());
//        Log.d("Level", first + " length : " + first.length());
//        Log.d("Level", second.toString() + " length : " + second.length());
        long afterTime = System.currentTimeMillis();
        long executionTime = afterTime - beforeTime;
        Log.d("LCD", "lcdWrite execution time : " + (executionTime));

    }
}
