package com.projetocgt.personagens;

import java.util.ArrayList;

import cgt.behaviors.Behavior;
import com.badlogic.gdx.math.Vector2;

public class Opposite extends GameObject{
	//private AIPolicy move;
	private boolean block;
	private boolean destroyable;
	private int damage;
	//private Projectile fire;
	private ArrayList<Behavior> behaviors;
	
	/**
	 * 
	 * @param position posicao na tela
	 * @param width largura do objecto
	 * @param height altura do objecto
	 * @param colider 
	 * @param posXColider
	 * @param posYColider
	 */
	public Opposite(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ){
		super(position, width, height, colider, posXColider, posYColider);
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
	 * @return the behaviors
	 */
	public ArrayList<Behavior> getBehaviors() {
		return behaviors;
	}
	/**
	 * @param behaviors the behaviors to set
	 */
	public void setBehaviors(ArrayList<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
}
