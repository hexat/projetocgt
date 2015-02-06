package com.projetocgt;

import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import cgt.screen.CGTWindow;
import cgt.util.ScreenHandleInterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.InputStream;

public class StarAssault extends Game {
	private static StarAssault instance =  null;
	private CGTScreen menu;
	private ScreenHandleInterface screenHandle;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {
        InputStream io = Gdx.files.internal("config.cgt").read();
        CGTGame game = CGTGame.getSavedGame(io);
        restart(game.getStartWindow());
    }

	public void restart(CGTWindow window){
		if(window instanceof CGTGameWorld) {
            setScreen(new GameScreen((CGTGameWorld) window));
        } else {
            setScreen(new GeneralScreen((CGTScreen) window));
        }
	}
	
	public static StarAssault getInstance() {
		if (instance == null) {
			instance =	new StarAssault();
		}
		return instance;
	}

	public void setScreenHandle(ScreenHandleInterface mainActivity) {
		this.screenHandle = mainActivity;
	}
	
	public ScreenHandleInterface getScreenHandle() {
		return screenHandle;
	}
}
