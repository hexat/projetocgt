package com.projetocgt.cenario;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
	static final float SIZE = 1f;

	private Vector2 	position = new Vector2();	//Posi�ao inicial do bloco(Retangulo)
	private Rectangle 	bounds = new Rectangle();	//�rea que sera construida cada bloco do cenario		
	
	//Construtor padr�o que recebe uma posi��o inicial como parametro
	public Block(Vector2 pos) {
		this.position = pos;				//posi��o inicial
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

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
