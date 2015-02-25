package cgt.hud;

import java.io.Serializable;

import cgt.game.CGTGame;
import cgt.screen.CGTWindow;

public class CGTButtonScreen extends CGTButton implements Serializable {
	private String windowId;

	public CGTButtonScreen() {
		windowId = null;
	}

	public CGTWindow getScreenToGo() {
		return CGTGame.get().getWindow(windowId);
	}

	public void setScreenToGo(String screenToGo) {
		this.windowId = screenToGo;
	}

    public boolean validate() {
        return windowId != null && super.validate();
    }

    @Override
    public String toString() {
        return getTextureUp().getFile().getFilename();
    }
}
