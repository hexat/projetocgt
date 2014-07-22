package com.projetocgt.personagens;

import java.util.ArrayList;

import cgt.behaviors.Behavior;
import cgt.core.CGTOpposite;

import com.badlogic.gdx.math.Vector2;

public class Opposite extends GameObject{

	private boolean block;
	private boolean destroyable;
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
//	public Opposite(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ){
//		super(position, width, height, colider, posXColider, posYColider);
//		setCgtGameObject(new CGTOpposite());
//	}
	
	public Opposite(CGTOpposite opposite){
		super(opposite);
		block = opposite.isBlock();
		destroyable = opposite.isDestroyable();
		behaviors = opposite.getBehaviors();
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
	 * @param behaviors the behaviors to set
	 */
	public void setBehaviors(ArrayList<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
}
