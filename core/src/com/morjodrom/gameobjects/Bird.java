package com.morjodrom.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
    }

    public void update(float delta){
        velocity.add(acceleration.cpy().scl(delta));

        if(velocity.y > 200){
            velocity.y = 200;
        }

        if(velocity.y < 0){
            rotation -= 600 * delta;
            rotation = Math.max(rotation, -20);
        }

        if (isFalling()) {
            rotation += 480 * delta;
            rotation = Math.min(rotation, 90);
        }

        position.add(velocity.cpy().scl(delta));

        if(position.y > 200){
            position.y = 200;
        }
    }

    public void onClick(){
        velocity.y = -140;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isFalling(){
        return velocity.y > 110;
    }

    public boolean shouldStopFlap(){
        return velocity.y > 70;
    }
}
