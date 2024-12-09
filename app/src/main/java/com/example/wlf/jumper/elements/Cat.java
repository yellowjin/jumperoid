package com.example.wlf.jumper.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.example.wlf.jumper.R;
import com.example.wlf.jumper.engine.Sound;
import com.example.wlf.jumper.graphics.Screen;

public class Cat {

    public static final int RADIOUS = 50;
    public static int J = -13;
    private Screen screen;
    private final Bitmap cat;
//    private final Sound sound;
    private int gravity = 1;
    private int vy=J;

    private int height;
    private int xspot;

    private int weight = 15;

    public Cat(Screen screen, Context context )
    {
        this.screen = screen;
        this.setHeight(screen.getHeight()/3);
        this.setXspot(50);

        Bitmap bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.cat );

        cat = Bitmap.createScaledBitmap( bp, RADIOUS, RADIOUS, false );
//        sound = new Sound(context);
    }

    public void desenhaNo( Canvas canvas )
    {
        canvas.drawBitmap(cat, xspot - RADIOUS, height - RADIOUS, null );
    }
    public void xMove(){
        boolean checkCenter = getXspot() + RADIOUS <= (screen.getWidth()/3);
        if(checkCenter){
            setXspot(getXspot()+3);
        }
    }
    public void xMoveEnd(){
        boolean checkCenter = getXspot()- RADIOUS <= screen.getWidth();
        if(checkCenter){
            setXspot(getXspot()+5);
        }
    }

    // 터치 시
    public void fall()
    {
        boolean reachedOnGround = getHeight() > screen.getHeight();
        if ( ! reachedOnGround )
        {
            this.vy+=this.gravity;
            setHeight(getHeight() + this.vy);
        }
    }
    public void fall2()
    {
        boolean reachedOnGround = getHeight()  > screen.getHeight();
        if ( ! reachedOnGround){
            setHeight(getHeight() + weight);
        }
    }

    public void jump()
    {
        if(getHeight() > RADIOUS) {
            this.vy=J;
            fall();
//            sound.playSound(Sound.JUMP);
        }
    }

    public int getHeight() {
        return height;
    }
    public int getXspot(){return xspot;}

    public void setHeight(int height ) {
        this.height = height;
    }
    public void setXspot(int xspot){
        this.xspot=xspot;
    }

    public void increaseGravity(){
        this.gravity++;
    }
}
