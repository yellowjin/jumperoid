package com.example.wlf.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.wlf.jumper.devices.DotMatrix;
import com.example.wlf.jumper.graphics.Cores;
import com.example.wlf.jumper.graphics.Screen;

public class
GameClear {
    private final Screen screen;
    private static final Paint VERMELHO = Cores.pntgameclear();
    public GameClear( Screen screen)
    {
        this.screen = screen;
    }

    public void drawClear( Canvas canvas )
    {
        String gameClear = "Game Clear";
        int centHorizontal = centertxt( gameClear );
        canvas.drawText( gameClear, centHorizontal, screen.getHeight() / 2, VERMELHO );
//        DotMatrix.getInstance().writeGameClear();
    }

    private int centertxt( String texto )
    {
        Rect limiteDoTexto = new Rect();
        VERMELHO.getTextBounds(texto, 0, texto.length(), limiteDoTexto);
        int centerHorizontal = screen.getWidth()/2 - ( limiteDoTexto.right - limiteDoTexto.left ) /2;
        return centerHorizontal;
    }
}
