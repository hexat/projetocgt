package com.projetocgt.personagens;

import java.util.ArrayList;
import cgt.behaviors.Behavior;
import cgt.policy.StatePolicy;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
	private StatePolicy state;
	private Vector2 velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	private float speed;
	private float alpha; //nivel de transparencia
	
	public Enemy(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ){
		this.setPosition(position);		//Posicao inicial
		this.bounds.height=height;
		this.bounds.width=width;
		this.posXColider=posXColider;
		this.posYColider=posYColider;
		this.alpha=1f;
		this.setRectangle(new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider));
		behaviors = new ArrayList<Behavior>();
	}
	/**
	 * Utilizada para ficar atualizando a posicao do personagem CGTEnemy
	 * @param delta
	 */
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
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
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}
