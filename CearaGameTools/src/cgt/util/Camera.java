package cgt.util;

import java.io.Serializable;

import cgt.policy.GameModePolicy;

public class Camera implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012536048841242556L;
	private GameModePolicy gameMode;
	private float initialWidth;
	private float initialHeight;
	private float fullWidth;
	private float fullHeight;
	private float closeWidth;
	private float closeHeight;
	private float scale;
	

	public Camera(GameModePolicy gameMode){
		setGameMode(gameMode);
		setInitialWidth(1f);
		setInitialHeight(1f);
		setFullWidth(1f);
		setFullHeight(1f);
		setCloseWidth(0.5f);
		setCloseHeight(0.5f);
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
	
	public float getInitialWidth() {
		return initialWidth;
	}
	
	public void setInitialWidth(float relativeWidth) {
		this.initialWidth = relativeWidth;
	}
	
	public float getInitialHeight() {
		return initialHeight;
	}
	
	public void setInitialHeight(float relativeHeight) {
		this.initialHeight = relativeHeight;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale / 100;
	}
	
	public float getCloseWidth() {
		return closeWidth;
	}
	
	public void setCloseWidth(float relativeCloseWidth) {
		this.closeWidth = relativeCloseWidth;
	}
	
	public float getCloseHeight() {
		return closeHeight;
	}
	
	public void setCloseHeight(float relativeCloseHeight) {
		this.closeHeight = relativeCloseHeight;
	}
	public float getFullWidth() {
		return fullWidth;
	}
	public void setFullWidth(float fullWidth) {
		this.fullWidth = fullWidth;
	}
	public float getFullHeight() {
		return fullHeight;
	}
	public void setFullHeight(float fullHeight) {
		this.fullHeight = fullHeight;
	}
	
}
