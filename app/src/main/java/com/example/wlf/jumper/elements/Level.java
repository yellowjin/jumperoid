package com.example.wlf.jumper.elements;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.wlf.jumper.devices.DeviceManager;
import com.example.wlf.jumper.devices.LCD;
import com.example.wlf.jumper.graphics.Cores;

public class Level {

    private static final Paint WHITE = Cores.getLevelColor();

    private int weight = 0;
    private static final int MAX_LEVEL = 40;
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
        int currentLevel = GameStatus.getInstance().getLevel();
        int progress = currentLevel * 100 / MAX_LEVEL;

        @SuppressLint("DefaultLocale") String first = String.format("LEVEL:%2d        ", currentLevel);
        @SuppressLint("DefaultLocale") StringBuilder second = new StringBuilder(String.format("%3d", progress));
        second.append("% ");
        second.append("[");
        for (int i = 0; i < 10; i++) {
            if (i < progress / 10)
                second.append("=");
            else if (i == progress / 10)
                second.append("*");
            else
                second.append("-");
        }
        second.append("]");
        if (DeviceManager.getInstance().isEmbeddedUse())
            LCD.getInstance().writeLCD(first, second.toString());
        Log.d("Level", first);
        Log.d("Level", second.toString());
    }
    public int passPipe(){
        return GameStatus.getInstance().getLevel();
    }
}
