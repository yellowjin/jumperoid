package com.example.wlf.jumper.engine;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.wlf.jumper.R;

public class Sound {

    private SoundPool soundPool;
    public static int JUMP;
    public static int PONTOS;
    public static int COLISAO;

    public Sound(Context context )
    {
        soundPool = new SoundPool( 3, AudioManager.STREAM_MUSIC, 0 );
        JUMP = soundPool.load(context, R.raw.pulo, 1);
        PONTOS = soundPool.load(context, R.raw.pontos, 1);
        COLISAO = soundPool.load(context, R.raw.colisao,1);
    }

    public void playSound(int som )
    {
        soundPool.play(som, 1, 1, 1, 0, 1);
    }
}
