package com.example.wlf.jumper.devices;

import android.util.Log;

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

    public native char getButton();

    public char getPressedKeyFromDevice() {
        char pressedBtn = 0x0000;
        try {
            pressedBtn = PushButtons.getInstance().getButton();
        } catch (Exception e){
            System.out.println("Push Button Device Error");

        }
        return pressedBtn;
    }
}
