package com.morjodrom.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.morjodrom.helpers.AssetLoader;

import java.util.Random;

public class Pipe extends Scrollable {
    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = AssetLoader.skullDown.getRegionWidth();
    public static final int SKULL_HEIGHT = AssetLoader.skullDown.getRegionHeight();

    private float groundY;
    private Random r;

    public Rectangle getSkullUp() {
        return skullUp;
    }

    public Rectangle getSkullDown() {
        return skullDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    private Rectangle skullUp, skullDown, barUp, barDown;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();
        this.groundY = groundY;

        skullUp = new Rectangle();
        skullDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
    }



    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        barUp.set(position.x, position.y, width, height);
        barDown.set(
                position.x,
                position.y + height + VERTICAL_GAP,
                width,
                groundY - (position.y + height + VERTICAL_GAP)
        );

        int skullOffset = (SKULL_WIDTH - width) / 2;
        skullUp.set(
                position.x - skullOffset,
                position.y + height
                -SKULL_HEIGHT,
                SKULL_WIDTH,
                SKULL_HEIGHT
        );
        skullDown.set(
                position.x - skullOffset,
                barDown.y,
                SKULL_WIDTH,
                SKULL_HEIGHT
        );
    }

    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            Rectangle[] shapes = {barUp, barDown, skullUp, skullDown};
            for (Rectangle shape : shapes) {
                if(Intersector.overlaps(bird.getCollisionArea(), shape)){
                    return true;
                }
            }
        }
        return false;
    }
}
