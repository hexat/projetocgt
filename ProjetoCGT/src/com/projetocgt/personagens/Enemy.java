package com.projetocgt.personagens;

import java.util.ArrayList;
import cgt.behaviors.Behavior;
import cgt.core.CGTEnemy;
import cgt.policy.StatePolicy;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameObject{
	private int damage;
	private ArrayList<Behavior> behaviors;
	private boolean block;
	private boolean destroyable;
	private float alpha; //nivel de transparencia
	
	public Enemy(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ){
		super(position, width, height, colider, posXColider, posYColider);
		setCgtGameObject(new CGTEnemy());
		this.alpha=1f;
		behaviors = new ArrayList<Behavior>();
	}
	
	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	/**
	 * @return the block
	 */
	public boolean isBlock() {
		return block;
	}
	/**
	 * @param block the block to set
	 */
	public void setBlock(boolean block) {
		this.block = block;
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
	 * @return the behaviors
	 */
	public ArrayList<Behavior> getBehaviors() {
		return behaviors;
	}
	/**
	 * 
	 * @param behavior
	 */
	public void addBehavior(Behavior behavior){
		this.behaviors.add(behavior);
	}
	
	/**
	 * @param behaviors the behaviors to set
	 */
	public void setBehaviors(ArrayList<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
	
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}
