package com.projetocgt.personagens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CGTProjectile {
	private Texture textura;
	private Vector2 position;
	private Rectangle bounds= new Rectangle();;
	/***
	 * Recebe uma posicao inicial
	 * @param position
	 */
	public CGTProjectile(Vector2 position,float size){
		this.setPosition(position);
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
}
