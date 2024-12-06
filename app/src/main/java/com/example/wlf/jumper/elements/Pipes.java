package com.example.wlf.jumper.elements;

import android.content.Context;
import android.graphics.Canvas;
import com.example.wlf.jumper.graphics.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Pipes {

    private static final int NUMBER_OF_PIPES = 2;
    private static final int INITIAL_POSITION = 400;
    private static final int DISTANCE_BETWEEN_PIPES = 200;
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
                return true;
            }
        }
        return false;
    }
}

