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
//            if (pressedBtn != 0x0000){
//                Log.d("pbutton Monitor",
//                        String.format("pressed button 0x%04X, %s\n",
//                                (int) pressedBtn,
//                                String.format("0b%16s", Integer.toBinaryString(pressedBtn))
//                                        .replace(" ", "0")
//                        )
//                );
//            }
        } catch (Exception e){
            System.out.println("Push Button Device Error");

        }
        return pressedBtn;
    }
}
