package com.example.wlf.jumper.elements;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.wlf.jumper.graphics.Cores;

public class Level {

    private static final Paint WHITE = Cores.getLevelColor();

    private int weight = 0;

    public void increase()
    {
        GameStatus.getInstance().increaseLevel();
        addScore();
    }

    public void addScore() {
        if (GameStatus.getInstance().getLevel() % 10 == 1) {
            weight += 10;
        }
        GameStatus.getInstance().sumScore(weight);
    }

    public int passedHurdleNumber(){return GameStatus.getInstance().getLevel();}

    public void draw(Canvas canvas )
    {
        canvas.drawText( String.valueOf(GameStatus.getInstance().getLevel()), 100, 150, WHITE);
    }
    public int passPipe(){
        return GameStatus.getInstance().getLevel();
    }
}
