package com.example.wlf.jumper.elements;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.wlf.jumper.graphics.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Pipes {

    private static final int NUMBER_OF_PIPES = 2;
    private static final int INITIAL_POSITION = 600;
    private static final int DISTANCE_BETWEEN_PIPES = 300;
    private final List<Pipe> pipes = new ArrayList<Pipe>();
    private Screen screen;
    private final Level level;
    private Context context;
    private int makePipe;
    public Pipes(Screen screen, Level level, Context context ) {
        this.screen = screen;
        this.level = level;
        this.context = context;
        this.makePipe = 0;

        int initialPosition  = INITIAL_POSITION;

        for(int i = 0; i< NUMBER_OF_PIPES; i++)
        {
            initialPosition  += DISTANCE_BETWEEN_PIPES;
            this.makePipe++;
            pipes.add(new Pipe(screen, initialPosition , this.level.passPipe(), context));
        }
    }

    public void draw(Canvas canvas )
    {
        for(Pipe pipe : pipes)
            pipe.draw( canvas );
    }

    public void move()
    {
        ListIterator<Pipe> iterator = pipes.listIterator();
        while( iterator.hasNext() ) {
            Pipe pipe = iterator.next();
            pipe.move();

            if(pipe.leftScreen()){
                iterator.remove();

                if(this.makePipe < 40) {
                    this.makePipe++;
                    Pipe nextPipe = new Pipe(screen, getMaximum() + DISTANCE_BETWEEN_PIPES,
                            this.level.passPipe(), context);//
                    iterator.add(nextPipe);
                }
            }
        }
    }

    public int getMaximum()
    {
        int maximum = 0;

        for( Pipe pipe : pipes)
        {
            maximum = Math.max(pipe.getPosition() + pipe.PIPE_WIDTH, maximum );
        }

        return maximum;
    }

    public boolean checkCollisionWith(Cat cat)
    {
        for ( Pipe pipe : pipes)
        {
            if(pipe.checkPassed(cat)){
               level.increase();
//               if (GameStatus.getInstance().getLevel() % 5 == 0)
//                   cat.increaseGravity();
           }
            if ( pipe.checkHorizontalCollisionWith(cat) && pipe.checkVerticalCollisionWith(cat) )
            {
//                Log.d("Pipes",String.format(
//                        "cat x, y : %d,%d", cat.getXspot(), cat.getHeight()));
//                Log.d("Pipes", String.format(
//                   "pipe getPosition : %d", pipe.getPosition()
//                ));
//
//                Log.d("Pipes",
//                        String.format("cat x + radius : %d\n", cat.getXspot() + Cat.RADIOUS) +
//                        String.format("pipe width1 : %d\n", pipe.getPosition()) +
//                        String.format("pipe width2 : %d\n", pipe.getPosition() + pipe.PIPE_WIDTH) +
//                        String.format("cat y + radius : %d\n", cat.getHeight() + Cat.RADIOUS) +
//                        String.format("pipe top length : %d\n", pipe.preTopLength) +
//                        String.format("pipe bottom length : %d\n", pipe.preBottomLength)
//                );
                return true;
            }
        }
        return false;
    }
}

