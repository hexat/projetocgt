package cgt.util;

import java.io.Serializable;

import cgt.policy.GameModePolicy;

public class Camera implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012536048841242556L;
	private GameModePolicy gameMode;
	private float relativeWidth;
	private float relativeHeight;
	private float scale;
	

	public Camera(GameModePolicy gameMode){
		setGameMode(gameMode);
		setRelativeWidth(0.5f);
		setRelativeHeight(0.5f);
		setScale(10);
	}
	public GameModePolicy getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameModePolicy gameMode) {
		this.gameMode = gameMode;
	}
	@Override
	public String toString() {
		return "Camera [gameMode=" + gameMode + "]";
	}
	public float getRelativeWidth() {
		return relativeWidth;
	}
	public void setRelativeWidth(float relativeWidth) {
		this.relativeWidth = relativeWidth;
	}
	public float getRelativeHeight() {
		return relativeHeight;
	}
	public void setRelativeHeight(float relativeHeight) {
		this.relativeHeight = relativeHeight;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
