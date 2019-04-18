package com.morjodrom.zombiebird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.morjodrom.gameworld.GameRenderer;
import com.morjodrom.gameworld.GameWorld;
import com.morjodrom.helpers.AssetLoader;
import com.morjodrom.screens.GameScreen;

public class ZBGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("ZBGame", "Created");
		AssetLoader.load();

        setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		AssetLoader.dispose();
	}
}
