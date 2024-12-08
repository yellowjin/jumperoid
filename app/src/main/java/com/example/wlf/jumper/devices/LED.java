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

    public native int ledInit(int life);    //int life

    public native int decreaseLife(int life);   //int life

    public native int ledClear();
}
