package com.projetocgt.personagens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	//private Sound sound;
	private Music soundDie;
	private Music soundDamage;
	private Vector2 position;
	private int life;
	private SpriteSheet spriteSheet;
	
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
	 * @return the soundDie
	 */
	public Music getSoundDie() {
		return soundDie;
	}
	/**
	 * @param soundDie the soundDie to set
	 */
	public void setSoundDie(Music soundDie) {
		this.soundDie = soundDie;
	}
	/**
	 * @return the soundDamage
	 */
	public Music getSoundDamage() {
		return soundDamage;
	}
	/**
	 * @param soundDamage the soundDamage to set
	 */
	public void setSoundDamage(Music soundDamage) {
		this.soundDamage = soundDamage;
	}
}
