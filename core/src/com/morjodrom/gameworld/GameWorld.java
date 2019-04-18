package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.morjodrom.gameobjects.Bird;
import com.morjodrom.gameobjects.ScrollHandler;

import java.awt.*;

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
    }

    public Rectangle getRect() {
        return rect;
    }
}
