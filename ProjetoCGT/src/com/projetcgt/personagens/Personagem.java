package com.projetcgt.personagens;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Personagem {
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 2f;				//Velocidade do personagem
	static final float JUMP_VELOCITY = 1f;		//Velocidade do pulo do personagem
	static final float SIZE = 0.5f; 			//Metade de uma unidade

	Vector2 	position = new Vector2();		//Vetor que informa a posi��o do personagem
	Vector2 	acceleration = new Vector2();	//Vetor que informa a acelera��o do personagem
	Vector2 	velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	Rectangle 	bounds = new Rectangle();		// �rea que ser� desenhado o personagem
	State 		state = State.IDLE;
	boolean 	facingLeft = true;
	
	//Construtor padr�o que recebe uma posi��o inicial 
	public Personagem(Vector2 position) {
		this.position = position;		//Posi��o inicial 
		this.bounds.height = SIZE;		//Altura do personagem (Altura da �rea onde o personagem ser� desenhado)
		this.bounds.width = SIZE;		//Largura do personagem (Largura da �rea onde o personagem ser� desenhado)
	}
}
