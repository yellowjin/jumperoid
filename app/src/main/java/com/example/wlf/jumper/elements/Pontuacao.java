package com.example.wlf.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.example.wlf.jumper.graphics.Cores;

public class Pontuacao {

    private static final Paint BRANCO = Cores.getCorDaPontuacao();
    private int pontos = 0;

    public void aumenta()
    {
        pontos++;
        SSegmentWrite(pontos);
    }

    public int passhurdlenum(){return pontos;}

    public void desenhaNo( Canvas canvas )
    {
        canvas.drawText( String.valueOf( pontos ), 100, 150, BRANCO );
    }
    public int passpipe(){
        return pontos;
    }

    ////////////////////////////////////////////////////////////////
    // Embedded System ADD
    // JNI 7Segment
    static {
        System.loadLibrary("7segment");
    }

    public native int SSegmentWrite(int data);
}
