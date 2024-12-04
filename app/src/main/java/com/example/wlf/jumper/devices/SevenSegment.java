package com.example.wlf.jumper.devices;

public class SevenSegment {
    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI 7Segment
    static {
        System.loadLibrary("7segment");
    }

    protected static class Singleton {
        private static final SevenSegment instance = new SevenSegment();
    }

    public static SevenSegment getInstance() { return Singleton.instance; }

    public native int SSegmentWrite(int data);
}
