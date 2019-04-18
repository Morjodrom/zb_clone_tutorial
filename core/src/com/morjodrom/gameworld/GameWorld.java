package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.morjodrom.gameobjects.Bird;

import java.awt.*;

public class GameWorld {
    private Rectangle rect = new Rectangle(0, 0, 17, 12);

    public Bird getBird() {
        return bird;
    }

    private Bird bird;

    public GameWorld(int midPointY) {
        this.bird = new Bird(33, midPointY - 5, 17, 12);
    }

    public void update(float delta) {
        bird.update(delta);
    }

    public Rectangle getRect() {
        return rect;
    }
}
