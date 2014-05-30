package com.progetocgt.util;

import com.badlogic.gdx.math.Vector2;
import com.progetocgt.policy.StatePolicy;

public class ProjectileOrientation {
	// private ActionMovePolicy movePolicyActor;
	private Vector2 position;
	private int spriteLine;
	private int spriteVelocity;
	private int spriteNumberOfColumns;
	private Vector2 positionRetativeToGameObject;
	private StatePolicy state;

	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
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
	 * @return the state
	 */
	public StatePolicy getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(StatePolicy state) {
		this.state = state;
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
}
