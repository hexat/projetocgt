package cgt.util;

import java.io.Serializable;

import cgt.policy.GameModePolicy;

public class Camera implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012536048841242556L;
	private GameModePolicy gameMode;

	Camera(GameModePolicy gameMode){
		setGameMode(gameMode);
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
	
}
