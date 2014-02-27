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

	Vector2 	position = new Vector2();		//Vetor que informa a posição do personagem
	Vector2 	acceleration = new Vector2();	//Vetor que informa a aceleração do personagem
	Vector2 	velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	Rectangle 	bounds = new Rectangle();
	State 		state = State.IDLE;
	boolean 	facingLeft = true;
	
	//Construto 
	public Personagem(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
}
