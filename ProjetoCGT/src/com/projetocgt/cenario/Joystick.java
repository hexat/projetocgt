package com.projetocgt.cenario;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * define as caracteristicas do Joystick
 * @author bruno
 *
 */
public class Joystick {
	private Texture textura;
	private Vector2 	position = new Vector2();		//Vetor que informa a posicao do personagem
	private Rectangle 	bounds = new Rectangle();		// Area que sera' desenhado o personagem
	public static final float SIZE = 0.4f; 			//Metade de uma unidade
	
	
	Joystick(Vector2 position){
		this.position = position;		//Posicao inicial 
		this.bounds.height = SIZE;		//Altura do personagem (Altura da �rea onde o personagem ser� desenhado)
		this.bounds.width = SIZE;		//Largura do personagem (Largura da �rea onde o personagem ser� desenhado)
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}

	public Texture getTextura() {
		return textura;
	}

	public void setTextura(Texture textura) {
		this.textura = textura;
	}

	public void setTextura1(Texture texturaSetaBaixo) {
		// TODO Auto-generated method stub
		
	}

}
