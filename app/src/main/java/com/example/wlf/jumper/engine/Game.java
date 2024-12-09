package com.example.wlf.jumper.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.wlf.jumper.devices.DrawDotMatrix;
import com.example.wlf.jumper.devices.PushButtonsMonitor;
import com.example.wlf.jumper.elements.GameStatus;
import com.example.wlf.jumper.elements.BackGround;
import com.example.wlf.jumper.elements.GameClear;
import com.example.wlf.jumper.elements.Pipes;
import com.example.wlf.jumper.elements.Cat;
import com.example.wlf.jumper.elements.Level;
import com.example.wlf.jumper.elements.GameOver;
import com.example.wlf.jumper.graphics.Screen;


public class Game extends SurfaceView implements Runnable, View.OnTouchListener {

    private boolean isRunning = true;
    private final SurfaceHolder holder = getHolder();
    private Screen screen;
    //private Bitmap background;
    private BackGround BG;
    private Pipes pipes;
    private Canvas canvas;
    private Cat cat;
    private Level level;
//    private Sound sound;
    private Context context;
    private boolean cktouch=false;
    private Handler mainHandler;

    private static final int INTERVAL = 32;


    public Game( Context context, Handler mainHandler  )
    {
        super( context );
        this.context = context;
        screen = new Screen( context );
        this.BG =new BackGround(screen,context);
        initElements();
        setOnTouchListener( this );
        this.mainHandler = mainHandler;

    }

    private void initElements()
    {
        this.cat = new Cat(screen, context);
        this.level = new Level();
        this.pipes = new Pipes(screen, level, context );
        BG.setX(0);
//        sound = new Sound(context);
        if (GameStatus.getInstance().getLife() <= 0 || GameStatus.getInstance().isGameClear()){
            GameStatus.getInstance().init();
        }
        GameStatus.getInstance().printStatus();
    }

    ////////////////////////////////////////////////////////////////

    @Override
    public void run() {
        runningGame();
        Log.d("Game", "terminate");
    }

    public boolean valid(){
        return holder.getSurface().isValid();
    }
    public void runningGame(){
        while ( isRunning ){
            long beforeTime = System.currentTimeMillis();
            if (!valid()){
                continue;
            }

            canvas = holder.lockCanvas();
            BG.update();
            BG.drawbg(canvas);

            if(!cktouch)
                cat.fall2();
            else{
                cat.fall();
            }

            if(level.passedHurdleNumber()>=40&& cat.getXspot()< screen.getWidth()){
                cat.xMoveEnd();//게임 클리어
                pipes.move();
                pipes.draw(canvas);
            }else{
                cat.xMove();
                pipes.move();
                pipes.draw(canvas);
            }
            cat.desenhaNo(canvas);
            level.draw(canvas);

            if(level.passedHurdleNumber()>=40&& cat.getXspot()> screen.getWidth()){
                new GameClear(screen).drawClear(canvas);
                isRunning=false;
                Message msg = Message.obtain();
                msg.what = 0;
                mainHandler.sendMessage(msg);
                GameStatus.getInstance().setGameClear();
                DrawDotMatrix.getInstance().setDraw(DrawDotMatrix.CLEAR);
            }

            if ( new CollisionDetector(cat, pipes).checkCollision() ) //게임 오버
            {
//                sound.playSound(Sound.COLISAO);
                new GameOver(screen).desenhaNo(canvas);
                isRunning = false;
                Message msg = Message.obtain();
                msg.what = 0;
                mainHandler.sendMessage(msg);
                GameStatus.getInstance().decreaseLife();
                DrawDotMatrix.getInstance().setDraw(DrawDotMatrix.OVER);
                PushButtonsMonitor.getInstance().setRetryUnLock();
            }
            holder.unlockCanvasAndPost(canvas);
            long afterTime = System.currentTimeMillis();
            long executionTime = afterTime - beforeTime;


            if (INTERVAL > executionTime) {
                try {
                    Thread.sleep(INTERVAL - executionTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
//                Log.d("Game", "runningGame execution time : " + (executionTime));
            }
        }
    }

    public void resume() {
        this.isRunning = true;
    }
    public void pause() {
        this.isRunning = false;
    }

    public void jump() {
        cat.jump();
        cktouch=true;
    }

    @Override
    public boolean onTouch( View view, MotionEvent motionEvent ) {
        jump();
        return false;
    }
    public void resetGame(){
        screen = new Screen( context );
        initElements();  //게임 시작하고 초기화하고 초기화시 필요한 객체 생성
        isRunning = true;
        cktouch = false;
    }
}
