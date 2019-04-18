package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.morjodrom.gameobjects.Bird;
import com.morjodrom.gameobjects.Grass;
import com.morjodrom.gameobjects.Pipe;
import com.morjodrom.gameobjects.ScrollHandler;
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
        drawPipes();


        spriteBatch.enableBlending();

        TextureRegion birdTexture;
        if (bird.shouldStopFlap()) {
            birdTexture = AssetLoader.bird;
        }
        else {
            birdTexture = (TextureRegion) AssetLoader.birdAnimation.getKeyFrame(runTime);
        }
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
    }

    private void drawPipes() {
        Pipe[] pipes = {
                world.getScroller().getPipe1(),
                world.getScroller().getPipe2(),
                world.getScroller().getPipe3()
        };

        for (Pipe pipe : pipes) {
            spriteBatch.draw(
                    AssetLoader.bar,
                    pipe.getX(),
                    pipe.getY() + pipe.getHeight() + 45,
                    pipe.getWidth(),
                    midPointY + 66 - (pipe.getHeight()) + 45
            );
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
