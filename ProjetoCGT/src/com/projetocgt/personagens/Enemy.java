package com.projetocgt.personagens;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.progetocgt.behaviors.Behavior;

public class Enemy extends GameObject{
	private int damage;
	private ArrayList<Behavior> behaviors;
	private boolean block;
	private boolean destroyable;
	private SpriteSheet spriteSheet;
	private Vector2 position;
	private Rectangle bounds = new Rectangle();		// Area que sera' desenhado o personagem
	private float posXColider, posYColider; 
	private Rectangle rectangle;
	
	public Enemy(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ){
		this.setPosition(position);		//Posicao inicial
		this.bounds.height=height;
		this.bounds.width=width;
		this.posXColider=posXColider;
		this.posYColider=posYColider;
		this.setRectangle(new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider));
	}
	/**
	 * Utilizada para ficar atualizando a posicao do personagem CGTEnemy
	 * @param delta
	 */
	public void update(float delta) {
		rectangle.x=this.position.x+posXColider;
		rectangle.y=this.position.y+posYColider;
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
	 * @param behaviors the behaviors to set
	 */
	public void setBehaviors(ArrayList<Behavior> behaviors) {
		this.behaviors = behaviors;
	}
	
	 /* 
	  * @return the spriteSheet
	 */
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	/**
	* @param spriteSheet the spriteSheet to set
	*/
	public void setSpriteSheet(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	/**
	 * @return the rectangle
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}
	/**
	 * @param rectangle the rectangle to set
	 */
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	/**
	 * @return the posYColider
	 */
	public float getPosYColider() {
		return posYColider;
	}
	/**
	 * @param posYColider the posYColider to set
	 */
	public void setPosYColider(float posYColider) {
		this.posYColider = posYColider;
	}
	/**
	 * @return the posXColider
	 */
	public float getPosXColider() {
		return posXColider;
	}
	/**
	 * @param posXColider the posXColider to set
	 */
	public void setPosXColider(float posXColider) {
		this.posXColider = posXColider;
	}
	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
