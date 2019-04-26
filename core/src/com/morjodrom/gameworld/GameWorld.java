package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.morjodrom.gameobjects.Bird;
import com.morjodrom.gameobjects.ScrollHandler;
import com.morjodrom.helpers.AssetLoader;

import java.awt.*;
import java.io.Console;

public class GameWorld {
    private ScrollHandler scroller;
    private Rectangle rect = new Rectangle(0, 0, 17, 12);

    public Bird getBird() {
        return bird;
    }

    private Bird bird;

    public GameWorld(int midPointY) {
        this.bird = new Bird(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(midPointY + 66);

    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public void update(float delta) {
        bird.update(delta);
        scroller.update(delta);

        boolean isHitTheGround = bird.getY() + bird.getHeight() > scroller.getFrontGrass().getY();
        if (scroller.collides(bird) || isHitTheGround) {
            onCrash();
        }
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
