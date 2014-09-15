package cgt.screen;

import cgt.util.CGTButton;

public class CGTButtonScreen extends CGTButton {
	private CGTWindow screenToGo;

	public CGTWindow getScreenToGo() {
		return screenToGo;
	}

	public void setScreenToGo(CGTWindow screenToGo) {
		this.screenToGo = screenToGo;
	}
}
