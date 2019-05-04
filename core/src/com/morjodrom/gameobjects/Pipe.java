package com.morjodrom.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.morjodrom.helpers.AssetLoader;

import java.util.Random;

public class Pipe extends Scrollable {
    private static final int VERTICAL_GAP = 50;
    private static final int SKULL_WIDTH = AssetLoader.skullDown.getRegionWidth();
    private static final int SKULL_HEIGHT = AssetLoader.skullDown.getRegionHeight();

    private boolean isScored = false;

    public int getVerticalGap() {
        return verticalGap;
    }

    private int verticalGap = VERTICAL_GAP;


    public boolean checkIsScored(){
        return isScored;
    }

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
        super.reset(newX + r.nextInt(15));
        height = r.nextInt(90) + 15;
        verticalGap = VERTICAL_GAP + r.nextInt(10);
        isScored = false;
    }

    public void markAsPassed(){
        this.isScored = true;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        barUp.set(position.x, position.y, width, height);
        barDown.set(
                position.x,
                position.y + height + verticalGap,
                width,
                groundY - (position.y + height + verticalGap)
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
