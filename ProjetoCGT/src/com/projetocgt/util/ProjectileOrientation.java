package com.projetocgt.util;

import java.util.ArrayList;

import cgt.policy.StatePolicy;

import com.badlogic.gdx.math.Vector2;

public class ProjectileOrientation {
	// private ActionMovePolicy movePolicyActor;
	private int spriteLine;
	private int spriteVelocity;
	private int spriteNumberOfColumns;
	private Vector2 positionRetativeToGameObject;
	private ArrayList<StatePolicy> state;

	public ProjectileOrientation(){
		state=new ArrayList<StatePolicy>();
	}
	/**
	 * @return the spriteLine
	 */
	public int getSpriteLine() {
		return spriteLine;
	}

	/**
	 * @param spriteLine
	 *            the spriteLine to set
	 */
	public void setSpriteLine(int spriteLine) {
		this.spriteLine = spriteLine;
	}

	/**
	 * @return the spriteVelocity
	 */
	public int getSpriteVelocity() {
		return spriteVelocity;
	}

	/**
	 * @param spriteVelocity
	 *            the spriteVelocity to set
	 */
	public void setSpriteVelocity(int spriteVelocity) {
		this.spriteVelocity = spriteVelocity;
	}

	/**
	 * @return the spriteNumberOfColumns
	 */
	public int getSpriteNumberOfColumns() {
		return spriteNumberOfColumns;
	}

	/**
	 * @param spriteNumberOfColumns
	 *            the spriteNumberOfColumns to set
	 */
	public void setSpriteNumberOfColumns(int spriteNumberOfColumns) {
		this.spriteNumberOfColumns = spriteNumberOfColumns;
	}


	/**
	 * @return the positionRetativeToGameObject
	 */
	public Vector2 getPositionRetativeToGameObject() {
		return positionRetativeToGameObject;
	}

	/**
	 * @param positionRetativeToGameObject the positionRetativeToGameObject to set
	 */
	public void setPositionRetativeToGameObject(
			Vector2 positionRetativeToGameObject) {
		this.positionRetativeToGameObject = positionRetativeToGameObject;
	}

	/**
	 * @return the state
	 */
	public ArrayList<StatePolicy> getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ArrayList<StatePolicy> state) {
		this.state = state;
	}
	
	public void addState(StatePolicy state){
		this.state.add(state);
	}
}
