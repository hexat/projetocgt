package com.projetocgt;

import cgt.CGTGameWorld;
import cgt.screen.CGTScreen;
import cgt.screen.CGTWindow;

import com.badlogic.gdx.Game;
import com.projetocgt.cenario.MyWorldPexe;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
public class StarAssault extends Game {
	
	private static StarAssault instance =  null;
	private CGTScreen menu;
	
	private StarAssault(){
		
	}

	@Override
	public void create() {
		setScreen(menu);
	}
	
	public CGTScreen getMenu() {
		return new MyWorldPexe().getGame().getMenu();
	}
	
	public void setScreen(CGTWindow window){
		if(window instanceof CGTGameWorld){
			setScreen(new GameScreen(new MyWorldPexe().getWorld()));
		}
		else
			setScreen(new GeneralScreen(new MyWorldPexe().getGame().getMenu()));

	}
	
	public static StarAssault getInstance(){
		if (instance == null){
			instance =	new StarAssault();
		}
		return instance;
	}
}
