package com.projetocgt;

import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.screen.CGTScreen;
import cgt.screen.CGTWindow;

import com.badlogic.gdx.Game;

public class StarAssault extends Game {
	private static StarAssault instance =  null;
	private CGTScreen menu;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {
		setScreen(new GeneralScreen(CGTGame.getSavedGame().getMenu()));
	}
	
//	public CGTScreen getMenu() {
//		return CGTGame.getSavedGame().;
//	}
	
	public void setScreen(CGTWindow window){
		//TODO
//		if(window instanceof CGTGameWorld){
//			setScreen(new GameScreen(CGTGame.getSavedGame().g));
//		}
//		else
		//setScreen(new GeneralScreen(new MyWorldPexe().getGame().getMenu()));
	
		setScreen(new GeneralScreen(CGTGame.getSavedGame().getMenu()));

	}
	
	public static StarAssault getInstance() {
		if (instance == null) {
			instance =	new StarAssault();
		}
		return instance;
	}
}
