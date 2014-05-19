package com.projetocgt.personagens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CGTOpposite {
	//private AIPolicy move;
	private boolean block;
	private boolean destroyable;
	private int damage;
	private CGTProjectile fire;
	private SpriteSheet spriteSheet;
	private Texture texture;
	private Vector2 position;
	private Rectangle bounds = new Rectangle();		// Area que sera' desenhado o personagem
	float posXColider, posYColider; 
	private Rectangle rectangle;
	//private ArrayList behaviors;
	public CGTOpposite(Vector2 position,float size,float colider,float posXColider,float posYColider ){
		this.setPosition(position);		//Posicao inicial
		this.bounds.height=size;
		this.bounds.width=size;
		this.posXColider=posXColider;
		this.posYColider=posYColider;
		this.setRectPer(new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider));
	}
	public void update(float delta) {
		rectangle.x=this.position.x+posXColider;
		rectangle.y=this.position.y+posYColider;
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
	 * @return the fire
	 */
	public CGTProjectile getFire() {
		return fire;
	}
	/**
	 * @param fire the fire to set
	 */
	public void setFire(CGTProjectile fire) {
		this.fire = fire;
	}
	/**
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
	 * @return the textura
	 */
	public Texture getTexture() {
		return texture;
	}
	/**
	 * @param textura the textura to set
	 */
	public void setTexture(Texture textura) {
		this.texture = textura;
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
	 * @return the rectPer
	 */
	public Rectangle getRectPer() {
		return rectangle;
	}
	/**
	 * @param rectPer the rectPer to set
	 */
	public void setRectPer(Rectangle rectPer) {
		this.rectangle = rectPer;
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
}
