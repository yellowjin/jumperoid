package com.example.wlf.jumper.devices;

public class PushButtons {
    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI LED
    static {
        System.loadLibrary("pbutton");
    }

    protected static class Singleton {
        private static final PushButtons instance = new PushButtons();
    }

    public static PushButtons getInstance() { return PushButtons.Singleton.instance; }

    public native int[] getButtonBuffer();
}
