package cgt.hud;

import java.io.Serializable;

import cgt.screen.CGTWindow;

public class CGTButtonScreen extends CGTButton implements Serializable{
	private CGTWindow screenToGo;

	public CGTWindow getScreenToGo() {
		return screenToGo;
	}

	public void setScreenToGo(CGTWindow screenToGo) {
		this.screenToGo = screenToGo;
	}
}
