package com.example.wlf.jumper.devices;

public class DotMatrix {
    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI DotMatrix
    static {
        System.loadLibrary("dotmatrix");
    }

    protected static class Singleton {
        private static final DotMatrix instance = new DotMatrix();
    }

    public static DotMatrix getInstance() { return DotMatrix.Singleton.instance; }

    public native int init();

    public native int writeRunning();

    public native int writeGameClear();

    public native int writeGameOver();
}
