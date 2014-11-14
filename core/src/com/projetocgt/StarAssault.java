package com.projetocgt;

import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.screen.CGTScreen;
import cgt.screen.CGTWindow;

import com.badlogic.gdx.Game;
import com.projetocgt.cenario.MyWorldChicken;
import com.projetocgt.cenario.MyWorldPexe;

public class StarAssault extends Game {
	private static StarAssault instance =  null;
	private CGTScreen menu;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {
//		setScreen(new GeneralScreen(new MyWorldChicken().getGame().getMenu()));
		restart(null);
	}
	
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
}
