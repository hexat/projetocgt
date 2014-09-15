package cgt;

import cgt.screen.CGTScreen;

public class CGTGame {
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
