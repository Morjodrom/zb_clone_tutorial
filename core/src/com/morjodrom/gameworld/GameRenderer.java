package com.morjodrom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.morjodrom.gameobjects.Bird;
import com.morjodrom.gameobjects.Grass;
import com.morjodrom.gameobjects.Pipe;
import com.morjodrom.helpers.AssetLoader;

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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.line(
                bird.getPosition(),
                bird.getPosition().cpy().add(new Vector2(10, 0).rotate(bird.getRotation()))
        );
        shapeRenderer.end();



        spriteBatch.begin();
        spriteBatch.disableBlending();
        float bgOffset = bird.isAlive() ? runTime * 7 % 136 : 0;
        spriteBatch.draw(AssetLoader.bg, -bgOffset, midPointY + 23, 136, 43);
        spriteBatch.draw(AssetLoader.bg, 136 - bgOffset, midPointY + 23, 136, 43);

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

        drawScore();

        spriteBatch.end();

        drawCollisions(bird.getCollisionArea());

        if (world.isReady()) {
            spriteBatch.begin();
            print("Touch me", (136 / 2) - 42, 76);
            spriteBatch.end();
        } else if (world.isGameOver()) {
            spriteBatch.begin();
            print("Game Over", 25, 56);
            print("Try Again", 24, 75);
            spriteBatch.end();
        }
    }

    private void drawScore() {
        AssetLoader.shadow.draw(spriteBatch, world.getScore() + "", 10, 10);
        AssetLoader.font.draw(spriteBatch, world.getScore() + "", 10, 10);
        print(world.getScore() + "", 10, 10);
    }

    private void print(String string, float x, float y){
        AssetLoader.shadow.draw(spriteBatch, string, x, y);
        AssetLoader.font.draw(spriteBatch, string, x, y);
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
        Pipe[] pipes = world.getScroller().getPipes();

        int skullOffset = (AssetLoader.skullDown.getRegionWidth() - AssetLoader.bar.getRegionWidth()) / 2;

        for (Pipe pipe : pipes) {
            float bottomPipeY = pipe.getY() + pipe.getHeight() + pipe.getVerticalGap();
            spriteBatch.begin();
            spriteBatch.draw(
                    AssetLoader.bar,
                    pipe.getX(),
                    bottomPipeY,
                    pipe.getWidth(),
                    midPointY + 66 - (pipe.getHeight()) + pipe.getVerticalGap()
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
