package com.example.wlf.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.wlf.jumper.devices.DotMatrix;
import com.example.wlf.jumper.graphics.Cores;
import com.example.wlf.jumper.graphics.Screen;

public class GameOver {
    private final Screen screen;
    private static final Paint VERMELHO = Cores.getCorDoGameOver();
    public GameOver( Screen screen)
    {
        this.screen = screen;
    }

    public void desenhaNo( Canvas canvas )
    {
        String gameOver = "Game Over";
        int centroHorizontal = centralizaTexto( gameOver );
        canvas.drawText( gameOver, centroHorizontal, screen.getHeight() / 2, VERMELHO );
//        DotMatrix.getInstance().writeGameOver();
    }

    private int centralizaTexto( String texto )
    {
        Rect limiteDoTexto = new Rect();
        VERMELHO.getTextBounds(texto, 0, texto.length(), limiteDoTexto);
        int centroHorizontal = screen.getWidth()/2 - ( limiteDoTexto.right - limiteDoTexto.left ) /2;
        return centroHorizontal;
    }
}


