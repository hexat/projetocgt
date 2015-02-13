package cgt.hud;

import java.io.Serializable;

import cgt.game.CGTGame;
import cgt.screen.CGTWindow;

public class CGTButtonScreen extends CGTButton implements Serializable{
	private String windowId;

	public CGTWindow getScreenToGo() {
		return CGTGame.get().getScreen(windowId);
	}

	public void setScreenToGo(String screenToGo) {
		this.windowId = screenToGo;
	}
}
