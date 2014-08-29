package com.projetocgt;

import cgt.screen.CGTScreen;

import com.badlogic.gdx.Game;
import com.projetocgt.cenario.MyWorldPexe;
public class StarAssault extends Game {
	
	private static StarAssault instance =  null;
	private CGTScreen menu;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {
		menu = new MyWorldPexe().getGame().getMenu();
		setScreen(new GeneralScreen(new MyWorldPexe().getGame().getMenu()));
	}
	
	public CGTScreen getMenu() {
		return menu;
	}
	
	public static StarAssault getInstance(){
		if (instance == null){
			instance =	new StarAssault();
		}
		return instance;
	}
}
