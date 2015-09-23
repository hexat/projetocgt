package cgt.util;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import cgt.policy.StatePolicy;

public class ProjectileOrientation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7053267478421888656L;
	private ArrayList<StatePolicy> states;
	private Vector2 positionRelativeToGameObject;
//	private int spriteLine;
//	private int spriteVelocity;
//	private int spriteNumberOfColumns;
	
	public ProjectileOrientation() {
		setStates(new ArrayList<StatePolicy>());
        positionRelativeToGameObject = new Vector2();
	}

//	public int getSpriteLine() {
//		return spriteLine;
//	}
//	
//	public void setSpriteLine(int spriteLine) {
//		this.spriteLine = spriteLine;
//	}
//	
//	public int getSpriteVelocity() {
//		return spriteVelocity;
//	}
//	
//	public void setSpriteVelocity(int spriteVelocity) {
//		this.spriteVelocity = spriteVelocity;
//	}
//
//	public int getSpriteNumberOfColumns() {
//		return spriteNumberOfColumns;
//	}
//
//	public void setSpriteNumberOfColumns(int spriteNumberOfColumns) {
//		this.spriteNumberOfColumns = spriteNumberOfColumns;
//	}

	/**
	 * @return the positionRelativeToGameObject
	 */
	public Vector2 getPositionRelativeToGameObject() {
		return positionRelativeToGameObject;
	}

	/**
	 * @param positionRelativeToGameObject the positionRelativeToGameObject to set
	 */
	public void setPositionRelativeToGameObject(
			Vector2 positionRelativeToGameObject) {
		this.positionRelativeToGameObject = positionRelativeToGameObject;
	}

	/**
	 * @return the states
	 */
	public ArrayList<StatePolicy> getStates() {
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(ArrayList<StatePolicy> states) {
		this.states = states;
	}
	
	public void addState(StatePolicy state) {
		this.states.add(state);
	}

	/*
	@Override
	public String toString() {
		return "ProjectileOrientation [statePolicyActor=" + statePolicyActor
				+ ", position=" + position + ", spriteLine=" + spriteLine
				+ ", spriteVelocity=" + spriteVelocity
				+ ", spriteNumberOfColumns=" + spriteNumberOfColumns + "]";
	}*/
	
}
