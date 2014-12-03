package com.projetocgt;

import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.screen.CGTScreen;
import cgt.screen.CGTWindow;
import cgt.util.ScreenHandleInterface;

import com.badlogic.gdx.Game;

public class StarAssault extends Game {
	private static StarAssault instance =  null;
	private CGTScreen menu;
	private ScreenHandleInterface screenHandle;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {

		

//		setScreen(new GeneralScreen(new MyWorldChicken().getGame().getMenu()));
		restart(null);	}
	
//	public CGTScreen getMenu() {
//		return CGTGame.getSavedGame().;
//	}

	public void restart(CGTWindow window){
		//TODO
//		if(window instanceof CGTGameWorld){
//			setScreen(new SplashScreen());
//		}
//		else

		CGTGame game = new MyWorldChicken().getGame();
		GameScreen a = new GameScreen((CGTGameWorld) game.getGame());
		if (game.startWithGame()) {
			setScreen(a);	
		} else {
			setScreen(new GeneralScreen((CGTScreen) game.getGame())); 
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
