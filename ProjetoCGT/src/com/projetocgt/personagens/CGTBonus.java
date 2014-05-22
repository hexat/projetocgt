package com.projetocgt.personagens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CGTBonus {
	//private LabelID labelID;
	//private AIPolicy move;
	private int score;
	private boolean destroyable;
	private int lifetime;
	private Vector2 position;
	private Rectangle bounds=new Rectangle();
	private float posXColider, posYColider;
	private Rectangle rectangle;
	private Texture texture;
	private SpriteSheet spriteSheet;
	//private ArrayList<BonusPolicy> policys;
	
	public CGTBonus(Vector2 position, float width, float height, float colider, float posXColider, float posYColider) {
		this.setPosition(position);		//Posicao inicial
		this.bounds.height=width;
		this.bounds.width=height;
		this.posXColider=posXColider;
		this.posYColider=posYColider;
		this.setRectangle(new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider));
	}
	/**
	 * Utilizada para ficar atualizando a posicao do personagem
	 * @param delta
	 */
	public void update(float delta) {
		rectangle.x=this.position.x+posXColider;
		rectangle.y=this.position.y+posYColider;
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
}
