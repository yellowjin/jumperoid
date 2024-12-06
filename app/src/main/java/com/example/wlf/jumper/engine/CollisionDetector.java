package com.example.wlf.jumper.engine;

import com.example.wlf.jumper.elements.Pipes;
import com.example.wlf.jumper.elements.Cat;

public class CollisionDetector {

    private final Cat cat;
    private final Pipes pipes;

    public CollisionDetector(Cat cat, Pipes pipes)
    {
        this.cat = cat;
        this.pipes = pipes;
    }

    public boolean checkCollision()
    {
        return pipes.checkCollisionWith(cat);
    }
}
