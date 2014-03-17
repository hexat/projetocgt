package com.projetocgt.cenario;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import core.petri.entity.Place;

public class Block {
	static final float SIZE = 1f;
	private Place place;
	private Vector2 	position = new Vector2();	//Posicao inicial do bloco(Retangulo)
	private Rectangle 	bounds = new Rectangle();	//Area que sera construida cada bloco do cenario		
	
	//Construtor padrao que recebe uma posicao inicial como parametro
	public Block(Vector2 pos, Place place) {
		this.place = place;
		this.position = pos;				//posicao inicial
		this.bounds.width = SIZE;			//Largura do bloco 
		this.bounds.height = SIZE;			//Altura do bloco
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	public Place getPlace() {
		return place;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
