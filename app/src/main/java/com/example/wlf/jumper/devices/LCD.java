package com.example.wlf.jumper.devices;

public class LCD {
    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI LED
    static {
        System.loadLibrary("lcd");
    }

    protected static class Singleton {
        private static final LCD instance = new LCD();
    }

    public static LCD getInstance() { return LCD.Singleton.instance; }

    public void write(int level, int maxLevel) {


    }
    public native int init();
    public native int writeLCD(String first, String second);
}
