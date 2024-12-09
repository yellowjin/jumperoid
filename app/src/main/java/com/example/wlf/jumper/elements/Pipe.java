package com.example.wlf.jumper.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.wlf.jumper.R;
import com.example.wlf.jumper.graphics.Screen;

public class Pipe {
//    private final Paint GREEN = Cores.getCorDoCano();
    private static final int PIPE_SIZE = 80;
    public int PIPE_WIDTH = 50;

    private  Bitmap canoInferior;
    private  Bitmap canoSuperior;
    private Screen screen;
    private int bottomPipeHeight;
    public int preTopLength;
    public int preBottomLength;
    private int upperPipeHeight;
    private int position;
    private int passPipe;
    private int level;
    private final int DEFAULT_SIZE = 50;
    private int preTopPipeSize;
    private int preBottomPipeSize;
    private int topClock;
    private int bottomClock;
    private final int topSpeed;
    private final int bottomSpeed;
    private boolean checkPassed;
    private Context context;

    public Pipe(Screen screen, int position, int passPipe, Context context )
    {
        int choiceHurdle = (int)(Math.random()*7) + 1;
        this.screen = screen;
        this.position = position;
        this.context = context;
        this.passPipe = passPipe;
        this.preBottomPipeSize = 0;
        this.preTopPipeSize = 0;
        this.topClock = 0;
        this.bottomClock = 0;
        this.topSpeed = (int)(Math.random()*7) + 6;
        this.bottomSpeed = (int)(Math.random()*7) + 6;
        this.checkPassed =false;

        this.level = (this.passPipe / 5) * 2;
        Bitmap bp=null;
        Bitmap bp_re=null;

        randomValue();
        this.preTopLength = DEFAULT_SIZE;
        this.preBottomLength = DEFAULT_SIZE;
        if(choiceHurdle == 1){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.hurdle_1 );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.hurdle_1 );
        }else if(choiceHurdle == 2){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.hurdle_2 );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.hurdle_2 );
        }else if(choiceHurdle==3){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr1 );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr1re);
            PIPE_WIDTH =100;
        }else if(choiceHurdle==4){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr2 );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr2re);
            PIPE_WIDTH =100;
        }else if(choiceHurdle==5){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr3 );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr3re);
            PIPE_WIDTH =100;
        }else if(choiceHurdle==6){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr4 );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_tr4re);
            PIPE_WIDTH =100;
        }else if(choiceHurdle==7){
            bp = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_snm );
            bp_re = BitmapFactory.decodeResource( context.getResources(), R.drawable.ob_snmre);
            PIPE_WIDTH =120;
        }
        try{
            canoInferior = Bitmap.createScaledBitmap(bp, PIPE_WIDTH, bottomPipeHeight, false);
        } catch (Exception e) {
            canoInferior = Bitmap.createScaledBitmap(bp, PIPE_WIDTH, 100, false);
        }
        try{
            canoSuperior = Bitmap.createScaledBitmap(bp_re, PIPE_WIDTH, upperPipeHeight, false);
        } catch (Exception e){
            canoSuperior = Bitmap.createScaledBitmap(bp_re, PIPE_WIDTH, 100, false);
        }
        if (bp != null && !bp.isRecycled()) {
            bp.recycle();
        }
        if (bp_re != null && bp_re.isRecycled()){
            bp_re.recycle();
        }
    }

    private void randomValue(){
        int preLevel = (this.level % 8);
        int gap = (15 - preLevel)*30;
//        int gap = (20 - preLevel)*30;
        int range =(int)(Math.random()*(screen.getHeight()-gap- PIPE_SIZE *2))+ PIPE_SIZE;

        this.bottomPipeHeight = screen.getHeight()-(range+gap);
        this.upperPipeHeight = range; //길이
//        Log.d("Pipe", String.format("preLevel : %d, gap : %d, range %d",
//                preLevel, gap, range));
//        Log.d("Pipe", String.format("upper height : %d, bottom : %d",
//                upperPipeHeight, bottomPipeHeight));

    }

    public void draw(Canvas canvas )
    {
        drawBottomPipeOn(canvas);
        drawUpperPipeOn(canvas);
    }

    private void drawUpperPipeOn(Canvas canvas )
    {
        int width= screen.getWidth();

        if(position >=(width-(width/5))){
            canvas.drawBitmap(canoSuperior, position, -upperPipeHeight + DEFAULT_SIZE, null );
            this.preTopLength = DEFAULT_SIZE;
        }else{
            int preHeight = -upperPipeHeight + DEFAULT_SIZE;
            if(this.level<8) {
                if (preHeight + this.preTopPipeSize < 0) {
                    this.preTopPipeSize += ((this.upperPipeHeight - DEFAULT_SIZE) / 15);
                    this.preTopLength = DEFAULT_SIZE + preTopPipeSize;
                    canvas.drawBitmap(canoSuperior, position, preHeight + preTopPipeSize, null);

                } else {
                    this.preTopLength = upperPipeHeight + DEFAULT_SIZE;
                    canvas.drawBitmap(canoSuperior, position, 0, null);
                }
            }else{
                if(this.topClock ==1){
                    if (preHeight + this.preTopPipeSize < 0) {
                        this.preTopPipeSize += ((this.upperPipeHeight - DEFAULT_SIZE) / (this.topSpeed *5));
                        this.preTopLength = DEFAULT_SIZE + preTopPipeSize;
                        canvas.drawBitmap(canoSuperior, position, preHeight + preTopPipeSize, null);
                    } else {
                        this.preTopLength = upperPipeHeight + DEFAULT_SIZE;
                        canvas.drawBitmap(canoSuperior, position, 0, null);
                        this.topClock = 0;
                    }
                }else{
                    if((preHeight + this.preTopPipeSize) >(-upperPipeHeight + DEFAULT_SIZE)){
                        this.preTopPipeSize -= ((this.upperPipeHeight - DEFAULT_SIZE) / (this.topSpeed *5));
                        this.preTopLength = DEFAULT_SIZE + preTopPipeSize;
                        canvas.drawBitmap(canoSuperior, position, preHeight + preTopPipeSize, null);
                    }else{
                        this.preTopLength = DEFAULT_SIZE;
                        canvas.drawBitmap(canoSuperior, position, preHeight, null);
                    this.topClock =1;
                    }
                }
            }
        }
    }

    private void drawBottomPipeOn(Canvas canvas )
    {
        int width= screen.getWidth();

        if(position >=(width-(width/5))){
            canvas.drawBitmap( canoInferior, position, screen.getHeight()- DEFAULT_SIZE, null );
            this.preBottomLength = screen.getHeight()- DEFAULT_SIZE;
        }else{
            int preHeight= screen.getHeight()- DEFAULT_SIZE;
            if(this.level<8){
                if(preHeight-this.preBottomPipeSize > screen.getHeight()-this.bottomPipeHeight){
                this.preBottomPipeSize +=((this.bottomPipeHeight - DEFAULT_SIZE)/15);
                this.preBottomLength = screen.getHeight()- DEFAULT_SIZE -this.preBottomPipeSize;
                canvas.drawBitmap( canoInferior, position,preHeight- preBottomPipeSize, null );
            }else{
                this.preBottomLength = screen.getHeight()-this.bottomPipeHeight;
                canvas.drawBitmap(canoInferior, position, screen.getHeight()- this.bottomPipeHeight, null);
            }
            }else{
                if(this.bottomClock ==1){
                    if(preHeight-this.preBottomPipeSize > screen.getHeight()-this.bottomPipeHeight){
                        this.preBottomPipeSize +=((this.bottomPipeHeight - DEFAULT_SIZE)/(this.bottomSpeed *5));
                        this.preBottomLength = screen.getHeight()- DEFAULT_SIZE -this.preBottomPipeSize;
                        canvas.drawBitmap( canoInferior, position,preHeight- preBottomPipeSize, null );
                    }else{
                        this.preBottomLength = screen.getHeight()-this.bottomPipeHeight;
                        canvas.drawBitmap(canoInferior, position, screen.getHeight()- this.bottomPipeHeight, null);
                        this.bottomClock =0;
                    }
                }else{
                    if(preHeight-this.preBottomPipeSize < screen.getHeight()- DEFAULT_SIZE){
                        this.preBottomPipeSize -=((this.bottomPipeHeight - DEFAULT_SIZE)/(this.bottomSpeed *5));
                        this.preBottomLength = screen.getHeight()-this.preBottomPipeSize - DEFAULT_SIZE;
                        canvas.drawBitmap( canoInferior, position,preHeight- preBottomPipeSize, null );
                    }else{
                        this.preBottomLength = screen.getHeight()- DEFAULT_SIZE;
                        canvas.drawBitmap( canoInferior, position, screen.getHeight()- DEFAULT_SIZE, null );
                        this.bottomClock =1;
                    }
                }
            }
        }
    }

    public void move()
    {
        position -=10;
    }
    public boolean leftScreen()
    {
        return position + PIPE_WIDTH < 0;
    }
    public int getPosition()
    {
        return position;
    }
    public boolean checkVerticalCollisionWith(Cat cat)
    {
        int ySpot = cat.getHeight();
        return ySpot < this.preTopLength ||
                ySpot > this.preBottomLength||
                ySpot >= screen.getHeight();
    }

    public boolean checkHorizontalCollisionWith(Cat cat)
    {
        return cat.getXspot() <= getPosition() + PIPE_WIDTH &&
                cat.getXspot() >= getPosition() ||
                cat.getHeight() >= screen.getHeight();
    }
    public boolean checkPassed(Cat cat){
        if(cat.getXspot()- Cat.RADIOUS > getPosition()+ PIPE_WIDTH &&
                !this.checkPassed){
            this.checkPassed =true;
            return true;
        }else return false;
    }
}
