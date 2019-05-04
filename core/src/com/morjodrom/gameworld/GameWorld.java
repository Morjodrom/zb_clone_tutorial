package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.morjodrom.gameobjects.Bird;
import com.morjodrom.gameobjects.GlidingBird;
import com.morjodrom.gameobjects.ScrollHandler;
import com.morjodrom.helpers.AssetLoader;

import java.awt.*;

public class GameWorld {
    public boolean isReady() {
        return this.currentState == GameState.READY;
    }

    public void start() {
        this.currentState = GameState.RUNNING;
    }

    public boolean isGameOver() {
        return this.currentState == GameState.GAMEOVER;
    }

    public enum GameState {
        READY, RUNNING, GAMEOVER
    }
    private GameState currentState;

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void addScore(int increment){
        this.score += increment;
        Gdx.app.log("Score", Integer.toString(this.score));
//        AssetLoader.coin.play();
    }


    private ScrollHandler scroller;
    private Rectangle rect = new Rectangle(0, 0, 17, 12);

    public Bird getBird() {
        return bird;
    }

    private Bird bird;

    public GameWorld(int midPointY) {
        this.bird = createBird(midPointY);
        scroller = createScrollHandler(midPointY);
        currentState = GameState.READY;
        this.midPointY = midPointY;
    }

    private ScrollHandler createScrollHandler(int midPointY) {
        return new ScrollHandler(midPointY + 66);
    }

    private Bird createBird(int midPointY) {
        //return new Bird(33, midPointY - 5, 17, 12);
        return new GlidingBird(33, midPointY - 5, 17, 12);

    }

    public ScrollHandler getScroller() {
        return scroller;
    }


    public void update(float delta){
        switch (currentState){
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
            default:
                updateRunning(delta);
                break;
        }
    }

    public void updateReady(float delta){

    }

    public void updateRunning(float delta) {
        bird.update(delta);

        Vector2 direction = new Vector2(1, 0);
        direction.rotate(bird.getRotation());
        float speed = Math.round(direction.len() * Math.cos(direction.angleRad()) * 1000) / 1000f;

        scroller.update(delta, speed);

        boolean isHitTheGround = bird.getY() + bird.getHeight() > scroller.getFrontGrass().getY();
        if (scroller.collides(bird) || isHitTheGround) {
//            onCrash();
//            currentState = GameState.GAMEOVER;
        }
        if (scroller.passes(bird)) {
            addScore(1);
        }
    }

    private int midPointY;

    public void restart(){
        currentState = GameState.READY;
        score = 0;
        this.bird = createBird(midPointY);
        this.scroller = createScrollHandler(midPointY);
    }


    private void onCrash() {
        if (bird.isAlive()) {
            scroller.stop();
            AssetLoader.crash.play();
            bird.die();
        }
    }

    public Rectangle getRect() {
        return rect;
    }
}
