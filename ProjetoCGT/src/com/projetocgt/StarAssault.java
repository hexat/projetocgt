package com.projetocgt;

import com.badlogic.gdx.Game;
public class StarAssault extends Game {
	
	private static StarAssault instance =  null;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {
	//	setScreen(new GameScreen());
		setScreen(new GeneralScreen(this));
	}
	
	public static StarAssault getInstance(){
		if (instance == null){
		instance =	new StarAssault();
			
		}
		return instance;
	}
}
