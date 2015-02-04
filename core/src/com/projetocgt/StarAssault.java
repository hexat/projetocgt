package com.projetocgt;

import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.screen.CGTScreen;
import cgt.screen.CGTWindow;
import cgt.util.CGTFile;
import cgt.util.ScreenHandleInterface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.projetocgt.cenario.MyWorldChicken;

import java.io.File;
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
        restart(game.getGame());
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
