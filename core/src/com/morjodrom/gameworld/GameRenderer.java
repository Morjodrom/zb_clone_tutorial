package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.morjodrom.gameobjects.*;
import com.morjodrom.helpers.AssetLoader;
import com.sun.xml.internal.ws.policy.AssertionSet;

public class GameRenderer {
    private GameWorld world;

    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    private SpriteBatch spriteBatch;

    private int midPointY;

    private int gameHeight;


    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        this.world = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, 204);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Bird bird = world.getBird();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(55 / 255f, 80 / 255f, 100 / 255f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        shapeRenderer.setColor(111 / 255f, 186 / 255f, 45 / 255f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        shapeRenderer.setColor(145 / 255f, 80 / 255f, 27 / 255f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();



        spriteBatch.begin();
        spriteBatch.disableBlending();
        spriteBatch.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);

        drawGrass();


        spriteBatch.enableBlending();

        TextureRegion birdTexture;
        if (bird.shouldStopFlap()) {
            birdTexture = AssetLoader.bird;
        }
        else {
            birdTexture = (TextureRegion) AssetLoader.birdAnimation.getKeyFrame(runTime);
        }
        spriteBatch.end();

        drawPipes();

        spriteBatch.begin();

        spriteBatch.draw(
                birdTexture,
                bird.getX(),
                bird.getY(),
                bird.getWidth() / 2f,
                bird.getHeight() / 2f,
                bird.getWidth(),
                bird.getHeight(),
                1,
                1,
                bird.getRotation()
        );

        spriteBatch.end();

        drawCollisions(bird.getCollisionArea());
    }

    private void drawCollisions(Shape2D collidable){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1f,0f,0f, .3f);

        if (collidable instanceof Circle) {
            Circle collisionArea = (Circle) collidable;
            shapeRenderer.circle(
                    collisionArea.x,
                    collisionArea.y,
                    collisionArea.radius
            );
        }
        if (collidable instanceof Rectangle) {
            Rectangle collisionArea = (Rectangle) collidable;
            shapeRenderer.rect(collisionArea.x, collisionArea.y, collisionArea.width, collisionArea.height);
        }

        shapeRenderer.end();
    }

    private void drawPipes() {

        Pipe[] pipes = {
                world.getScroller().getPipe1(),
                world.getScroller().getPipe2(),
                world.getScroller().getPipe3()
        };

        int skullOffset = (AssetLoader.skullDown.getRegionWidth() - AssetLoader.bar.getRegionWidth()) / 2;

        for (Pipe pipe : pipes) {
            float bottomPipeY = pipe.getY() + pipe.getHeight() + 45;
            spriteBatch.begin();
            spriteBatch.draw(
                    AssetLoader.bar,
                    pipe.getX(),
                    bottomPipeY,
                    pipe.getWidth(),
                    midPointY + 66 - (pipe.getHeight()) + 45
            );
            spriteBatch.draw(
                    AssetLoader.skullDown,
                    pipe.getX() - skullOffset,
                    bottomPipeY,
                    AssetLoader.skullDown.getRegionWidth(),
                    AssetLoader.skullDown.getRegionHeight()
            );

            spriteBatch.draw(
                    AssetLoader.bar,
                    pipe.getX(),
                    pipe.getY(),
                    pipe.getWidth(),
                    pipe.getHeight()
            );

            spriteBatch.draw(
                    AssetLoader.skullUp,
                    pipe.getX() - skullOffset,
                    pipe.getY() + pipe.getHeight() - AssetLoader.skullUp.getRegionHeight(),
                    AssetLoader.skullUp.getRegionWidth(),
                    AssetLoader.skullUp.getRegionHeight()
            );

            spriteBatch.end();

            drawCollisions(pipe.getBarDown());
            drawCollisions(pipe.getBarUp());
            drawCollisions(pipe.getSkullDown());
            drawCollisions(pipe.getSkullUp());
        }

    }

    private void drawGrass(){
        Grass frontGrass = world.getScroller().getFrontGrass();
        spriteBatch.draw(
                AssetLoader.grass,
                frontGrass.getX(),
                frontGrass.getY(),
                frontGrass.getWidth(),
                frontGrass.getHeight()
        );
        Grass backGrass = world.getScroller().getBackGrass();
        spriteBatch.draw(
                AssetLoader.grass,
                backGrass.getX(),
                backGrass.getY(),
                backGrass.getWidth(),
                backGrass.getHeight()
        );
    }


}
