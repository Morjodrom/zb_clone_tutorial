package com.morjodrom.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.morjodrom.helpers.AssetLoader;

public class Bird {
    private static final int GRAVITY = 300;
    private static final int FALLING_MAX = 200;
    private static final int ROTATION_RATIO = 600;
    private static final int MAX_FLY_ROTATION = -30;
    private static final int FALLING_ROTATION = 90;
    private static final int FLY_ACCELERATION = 90;
    private static final int FALLING_SPEED = 110;
    private static final int NO_FLAP_FALLING_SPEED = 70;

    public boolean isAlive() {
        return isAlive;
    }

    private boolean isAlive = true;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    private int height;

    public Circle getCollisionArea() {
        return collisionArea;
    }

    private Circle collisionArea;

    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, GRAVITY);

        collisionArea = new Circle(position, Math.min(width, height) / 2);
    }

    public void update(float delta){
        if (!isAlive) {
            return;
        }
        velocity.add(acceleration.cpy().scl(delta));


        if(velocity.y > FALLING_MAX){
            velocity.y = FALLING_MAX;
        }

        if(velocity.y < 0){
            rotation -= ROTATION_RATIO * delta;
            rotation = Math.max(rotation, MAX_FLY_ROTATION);
        }

        if (isFalling()) {
            rotation += 480 * delta;
            rotation = Math.min(rotation, FALLING_ROTATION);
        }


        position.add(velocity.cpy().scl(delta));
        if(position.y < 0){
            position.y = 0;
        }

        collisionArea.setPosition(position.x + (float) width / 2, position.y + collisionArea.radius);

        if(position.y > FALLING_MAX){
            position.y = FALLING_MAX;
        }
    }

    public void onClick(){
        if (!isAlive) {
            return;
        }
        velocity.y = -FLY_ACCELERATION;
        AssetLoader.flap.play();
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
        return velocity.y > FALLING_SPEED;
    }

    public boolean shouldStopFlap(){
        return velocity.y > NO_FLAP_FALLING_SPEED || !isAlive;
    }

    public void die(){
        this.isAlive = false;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
