package com.projetocgt.unit;

import java.util.ArrayList;

import com.progetocgt.policy.InputPolicy;
import com.projetocgt.personagens.ActorCGT;

public abstract class Action {
	private ArrayList<InputPolicy> inputs;
	//private Sound sound;
	private int spriteLine;
	private int numberOfColumns;
	private ActorCGT owner;
	private float spriteVelocity;
	//private AnimationPolicy animationPolicy;
	
	public Action(){
		setInputs(new ArrayList<InputPolicy>());
	}

	/**
	 * @return the inputs
	 */
	public ArrayList<InputPolicy> getInputs() {
		return inputs;
	}

	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(ArrayList<InputPolicy> inputs) {
		this.inputs = inputs;
	}

	/**
	 * @return the spriteLine
	 */
	public int getSpriteLine() {
		return spriteLine;
	}

	/**
	 * @param spriteLine the spriteLine to set
	 */
	public void setSpriteLine(int spriteLine) {
		this.spriteLine = spriteLine;
	}

	/**
	 * @return the numberOfColumns
	 */
	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	/**
	 * @param numberOfColumns the numberOfColumns to set
	 */
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	/**
	 * @return the owner
	 */
	public ActorCGT getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(ActorCGT owner) {
		this.owner = owner;
	}

	/**
	 * @return the spriteVelocity
	 */
	public float getSpriteVelocity() {
		return spriteVelocity;
	}

	/**
	 * @param spriteVelocity the spriteVelocity to set
	 */
	public void setSpriteVelocity(float spriteVelocity) {
		this.spriteVelocity = spriteVelocity;
	}
}
