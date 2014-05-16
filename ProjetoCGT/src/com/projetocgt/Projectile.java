package com.projetocgt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
	private Texture textura;
	private Vector2 position;
	private Rectangle bounds= new Rectangle();;
	/***
	 * Recebe uma posicao inicial
	 * @param position
	 */
	public Projectile(Vector2 position,float size){
		this.position=position;
		this.bounds.height=size;
		this.bounds.width=size;
	}
	/**
	 * @return the textura
	 */
	public Texture getTextura() {
		return textura;
	}

	/**
	 * @param textura the textura to set
	 */
	public void setTextura(Texture textura) {
		this.textura = textura;
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
}
