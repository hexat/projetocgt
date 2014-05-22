package com.projetocgt.personagens;

public abstract class CGTGameObject {
	private SpriteSheet sprite;
	//private Sound sound;
	//private Sound soundDie;
	//private Sound soundDamage;
	//private Position position;
	private int life;
	private SpriteSheet spriteSheet;
	/**
	 * @return the sprite
	 */
	public SpriteSheet getSprite() {
		return sprite;
	}
	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(SpriteSheet sprite) {
		this.sprite = sprite;
	}
	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}
	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
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