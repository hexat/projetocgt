package cgt;

import java.io.Serializable;

import cgt.screen.CGTScreen;

public class CGTGame implements Serializable{
	private CGTScreen menu;

	public CGTGame() {
		setMenu(null);
	}
	
	public CGTGame(CGTScreen menu) {
		this.setMenu(menu);
	}

	public CGTScreen getMenu() {
		return menu;
	}

	public void setMenu(CGTScreen menu) {
		this.menu = menu;
	}
	
	
}
