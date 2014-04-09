package com.projetocgt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;



public class StarAssault extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
