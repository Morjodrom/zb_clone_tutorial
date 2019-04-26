package com.morjodrom.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture texture;
    public static TextureRegion bg, grass;

    public static Animation birdAnimation;
    public static TextureRegion bird, birdDown, birdUp;

    public static TextureRegion skullUp, skullDown, bar;

    public static Sound crash = Gdx.audio.newSound(Gdx.files.internal("sounds/Crash.wav"));
    public static Sound flap = Gdx.audio.newSound(Gdx.files.internal("sounds/Flap.wav"));
    public static Sound coin = Gdx.audio.newSound(Gdx.files.internal("sounds/Pickup_Coin.wav"));

    public static BitmapFont font, shadow;

    public static void load(){
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);

        TextureRegion[] birds = {birdDown, bird, birdUp};
        birdAnimation = new Animation <TextureRegion>(0.06f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

        font = new BitmapFont(Gdx.files.internal("data/fonts/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        font = new BitmapFont(Gdx.files.internal("data/fonts/shadow.fnt"));
        font.getData().setScale(.25f, -.25f);
    }

    public static void dispose(){
        texture.dispose();
        crash.dispose();
        flap.dispose();
        coin.dispose();
    }


}
