package com.example.wlf.jumper;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.wlf.jumper.devices.DotMatrix;
import com.example.wlf.jumper.devices.DrawDotMatrix;
import com.example.wlf.jumper.devices.LCD;
import com.example.wlf.jumper.devices.LED;
import com.example.wlf.jumper.devices.PushButtonsMonitor;
import com.example.wlf.jumper.devices.SevenSegment;
import com.example.wlf.jumper.elements.GameStatus;
import com.example.wlf.jumper.engine.BackgroundSoundService;
import com.example.wlf.jumper.engine.Game;

public class MainActivity extends AppCompatActivity {
    Game game;
    Button retry;
    Button start;

    PushButtonsMonitor pButtonMonitor = PushButtonsMonitor.getInstance();
    Thread pButtonMonitorThread = new Thread(pButtonMonitor);

//    private DrawDotMatrix drawDotMatrix = DrawDotMatrix.getInstance();
//    private Thread drawDotMatrixThread = new Thread(drawDotMatrix);

    private boolean embeddedUse = false;
    private FrameLayout container;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (FrameLayout) findViewById(R.id.container);

        game = new Game(this, mainHandler);
        retry = findViewById(R.id.retry);
        start = findViewById(R.id.start);
        retry.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);

        start.setOnClickListener(v -> {
            startGame();
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            new Thread(this::backgrounds).start();
        }, 500);


        Log.d("MainActivity", "onCreate() end");
    }
    private void backgrounds(){
        // Embedded System ADD
        getDisplayResolution();
        deviceInit();
        if(embeddedUse) {
            pButtonMonitor.setMainHandler(mainHandler);
            pButtonMonitor.setGame(game);
            pButtonMonitorThread.start();
//            drawDotMatrixThread.start();
        }
        GameStatus.getInstance().init();
        LCD.getInstance().writeLCD("Press 1 button", "start");
    }
    public void deviceInit(){
        LED.getInstance().ledClear();
        LCD.getInstance().init();
        if(SevenSegment.getInstance().SSegmentWrite(0) == 0) {
            embeddedUse = true;
        } else {
            Log.d("MainActivity", "device init error");
        }
    }
    private void getDisplayResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); // 화면 크기를 Point 객체에 저장
        int width = size.x; // 가로 길이
        int height = size.y; // 세로 길이

        Log.d("MainActivity", "Width: " + width + ", Height: " + height);
    }

    public void startGame(){
        container.addView(game);
        new Thread(game).start();
        start.setVisibility(View.GONE);
//        drawDotMatrix.setDraw(1);
    }

    public void resetGame(){
        game.resetGame();
        new Thread(game).start();
        retry.setVisibility(View.GONE);
//        drawDotMatrix.setDraw(1);
    }

    @SuppressLint("HandlerLeak")
    Handler mainHandler =  new Handler(){
        public void handleMessage(Message msg){
            Log.d("MainActivity", "msg.wat : " + msg.what);
            switch (msg.what){
                case 0:
                    retry.setVisibility(View.VISIBLE);
                    retry.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            resetGame();
                        }
                    });
                    break;
                case 1:
                    startGame();
                    break;
                case 3:
                    resetGame();
                    PushButtonsMonitor.getInstance().setRetryUnLock();
                    break;
                case 9:
                    killProcess();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }

    public void onBackPressed(){
        killProcess();
    }
    public void PlayBackgroundSound(MainActivity view) {
        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intent);
    }

    public void killProcess() {
        if (embeddedUse) {
            pButtonMonitor.terminate();
            try {
                pButtonMonitorThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            drawDotMatrix.terminate();
//            try {
//                drawDotMatrixThread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            deviceInit();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
