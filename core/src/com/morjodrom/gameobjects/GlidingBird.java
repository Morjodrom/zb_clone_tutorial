package com.morjodrom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.morjodrom.helpers.AssetLoader;

public class GlidingBird extends Bird {
    protected static int maxFalling = 200;
    protected static int maxUpRotation = -60;
    protected static int maxFallingRotation = 90;
    protected static int flyAcceleration = 130;


    public GlidingBird(float x, float y, int width, int height) {
        super(x, y, width, height);
    }



    @Override
    public void update(float delta) {
        if (!isAlive) {
            return;
        }
        velocity.add(gravity.cpy().scl(delta));

        if(velocity.y > maxFalling){
            velocity.y = maxFalling;
        }

        position.add(velocity.cpy().scl(delta));
        if(position.y < 0){
            position.y = 0;
        }


        if(position.y > maxFalling){
            position.y = maxFalling;
        }

        collisionArea.setPosition(position.x + (float) width / 2, position.y + collisionArea.radius);


        if (velocity.y < 0) {
            rotation = MathUtils.lerp(rotation, velocity.y, .3f);
        }
        else {
            float test = Math.min(velocity.y, 90);
            rotation = MathUtils.lerp(rotation, test, .01f);
        }

        Gdx.app.log("ROTATION", rotation + "");

        if (rotation < maxUpRotation) {
            rotation = maxUpRotation;
        }
        if (rotation > maxFallingRotation) {
            rotation = maxFallingRotation;
        }
        Gdx.app.log("ROTATION_2", rotation + "");

    }


    private float rotation = 0;

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void onClick() {
        if (!isAlive) {
            return;
        }
        if (velocity.y > 0) {
            velocity.y = MathUtils.lerp(velocity.y, -flyAcceleration,.7f) ;
        }
        else {
            velocity.y = -flyAcceleration;
        }
        AssetLoader.flap.play();
    }
}
