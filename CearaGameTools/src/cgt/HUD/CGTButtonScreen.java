package cgt.hud;

import cgt.screen.CGTWindow;

public class CGTButtonScreen extends CGTButton {
	private CGTWindow screenToGo;

	public CGTWindow getScreenToGo() {
		return screenToGo;
	}

	public void setScreenToGo(CGTWindow screenToGo) {
		this.screenToGo = screenToGo;
	}
}
