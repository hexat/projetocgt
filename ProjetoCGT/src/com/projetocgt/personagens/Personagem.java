package com.projetocgt.personagens;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Personagem {
	
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}
	
	

	public static final float SPEED = 2f;				//Velocidade do personagem
	public static final float JUMP_VELOCITY = 1f;		//Velocidade do pulo do personagem
	public static final float SIZE = 0.5f; 			//Metade de uma unidade

	private Vector2 	position = new Vector2();		//Vetor que informa a posi��o do personagem
	private Vector2 	acceleration = new Vector2();	//Vetor que informa a acelera��o do personagem
	private Vector2 	velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	private Rectangle 	bounds = new Rectangle();		// �rea que ser� desenhado o personagem
	private State 		state = State.IDLE;				//
	private boolean 	facingLeft = true;				//
	
	//Construtor padr�o que recebe uma posi��o inicial 
	public Personagem(Vector2 position) {
		this.position = position;		//Posi��o inicial 
		this.bounds.height = SIZE;		//Altura do personagem (Altura da �rea onde o personagem ser� desenhado)
		this.bounds.width = SIZE;		//Largura do personagem (Largura da �rea onde o personagem ser� desenhado)
	}
	
	public Personagem() {
		this.bounds.height = SIZE;		//Altura do personagem (Altura da �rea onde o personagem ser� desenhado)
		this.bounds.width = SIZE;		//Largura do personagem (Largura da �rea onde o personagem ser� desenhado)
	}
	
	public void setState(State newState) {
		this.state = newState;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
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

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public State getState() {
		return state;
	}


	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}
}
