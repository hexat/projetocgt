package com.projetocgt.personagens;

import java.util.ArrayList;

import cgt.policy.AIPolicy;
import cgt.policy.BonusPolicy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bonus extends GameObject{
	//private LabelID labelID;
	private AIPolicy move;
	private int score;
	private boolean destroyable;
	private int lifetime;
	private Texture texture;
	//private CGTSpriteSheet spriteSheet;
	private ArrayList<BonusPolicy> policys;
	
	public Bonus(Vector2 position, float width, float height, float colider, float posXColider, float posYColider) {
		super(position, width, height, colider, posXColider, posYColider);
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the destroyable
	 */
	public boolean isDestroyable() {
		return destroyable;
	}
	/**
	 * @param destroyable the destroyable to set
	 */
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	/**
	 * @return the lifetime
	 */
	public int getLifetime() {
		return lifetime;
	}
	/**
	 * @param lifetime the lifetime to set
	 */
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	
	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	
	/**
	 * @return the move
	 */
	public AIPolicy getMove() {
		return move;
	}
	/**
	 * @param move the move to set
	 */
	public void setMove(AIPolicy move) {
		this.move = move;
	}
	/**
	 * @return the policys
	 */
	public ArrayList<BonusPolicy> getPolicys() {
		return policys;
	}
	/**
	 * @param policys the policys to set
	 */
	public void setPolicys(ArrayList<BonusPolicy> policys) {
		this.policys = policys;
	}
}
