package com.morjodrom.gameobjects;

import com.badlogic.gdx.math.MathUtils;

public class ScrollHandler {
    private Grass frontGrass, backGrass;

    private Pipe pipe1, pipe2, pipe3;

    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 49;

    public ScrollHandler(float yPos) {
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

        pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }

    public void update(float delta, float speed){
        float deltaNormalized = MathUtils.lerp(delta, delta * speed, .5f);
        frontGrass.update(deltaNormalized);
        backGrass.update(deltaNormalized);
        pipe1.update(deltaNormalized);
        pipe2.update(deltaNormalized);
        pipe3.update(deltaNormalized);

        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }


        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }

    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }

    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }

    public Pipe[] getPipes(){
        return new Pipe[]{pipe1, pipe2, pipe3};
    }

    public boolean passes(Bird bird) {
        float birdPassBorder = bird.getX() + bird.getWidth();
        for (Pipe pipe : getPipes()) {
            float pipePassBorder = pipe.getX() + (float) pipe.getWidth() / 2;
            if (!pipe.checkIsScored() && birdPassBorder > pipePassBorder) {
                pipe.markAsPassed();
                // can pass only one pipe
                return true;
            }
        }
        return false;
    }

    public boolean collides(Bird bird){
        for (Pipe pipe : getPipes()) {
            if (pipe.collides(bird)) {
                return true;
            }
        }
        return false;
    }
}
