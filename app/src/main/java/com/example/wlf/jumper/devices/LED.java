package com.example.wlf.jumper.devices;

public class LED {
    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI LED
    static {
        System.loadLibrary("led");
    }

    protected static class Singleton {
        private static final LED instance = new LED();
    }

    public static LED getInstance() { return LED.Singleton.instance; }

    public native int ledInit();

    public native int decreaseLife();

    public native int ledClear();
}
