package cgt.hud;

import java.io.Serializable;

import cgt.policy.InputPolicy;
import cgt.util.CGTTexture;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;


public class CGTButtonStartGame extends CGTButton implements Serializable{
	
	private boolean actionCamera;

	public CGTButtonStartGame(float x, float y, float relativeWidth, float relativeHeight) {
		super(x, y, relativeWidth, relativeHeight);
		actionCamera = true;
	}

	public boolean isActionCameraFull() {
		return actionCamera == true;
	}

	public boolean isActionCameraClose() {
		return actionCamera == false;
	}

	public void setActionCameraClose() {
		actionCamera = false;
	}
	
	public void setActionCameraFull() {
		actionCamera = true;
	}
}
