package cgt.behaviors;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

import cgt.policy.DirectionPolicy;

public class Direction implements Behavior, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2118059060366691269L;

	//Demarcadores da area de movimentacao
	private Vector2 initialPosition;
	private Vector2 finalPosition;

	private boolean inteligenceMoviment;
	
	//Comportamento a ser aplicado

	private DirectionPolicy directionPolicy;

	private float distance;
	
	private Vector2 actorPosition;
	
	public Direction(DirectionPolicy directionPolicy){
		setDirectionPolicy(directionPolicy);
		this.initialPosition = new Vector2();
		this.finalPosition = new Vector2();
		actorPosition = null;
		distance = 0;
	}
	 
	public boolean isInteligenceMoviment() {
		return inteligenceMoviment;
	}



	public void setInteligenceMoviment(boolean inteligenceMoviment) {
		this.inteligenceMoviment = inteligenceMoviment;
	}



	public DirectionPolicy getDirectionPolicy() {
		return directionPolicy;
	}
	 
	public void setDirectionPolicy(DirectionPolicy directionPolicy) {
		this.directionPolicy = directionPolicy;
	}

	@Override
	public String getBehaviorPolicy() {
		return directionPolicy.name();
	}
	@Override
	public String toString() {
		return "Direction [directionPolicy=" + directionPolicy + "]";
	}

	public Vector2 getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Vector2 initialPosition) {
		this.initialPosition = initialPosition;
	}

	public Vector2 getFinalPosition() {
		return finalPosition;
	}

	public void setFinalPosition(Vector2 finalPosition) {
		this.finalPosition = finalPosition;
	}
	
	public float getDistance() {
		if (distance <= 0 && getFinalPosition() != null && getInitialPosition() != null) {
			distance = getFinalPosition().dst(getInitialPosition());
		}
		
		return distance;
	}

	public Vector2 getActorPosition() {
		return actorPosition;
	}

	public void setActorPosition(Vector2 actorPosition) {
		if (this.actorPosition == null) {
			this.actorPosition = new Vector2();
		}
		this.actorPosition.y = actorPosition.y;
		this.actorPosition.x = actorPosition.x;
	}

	public void invert() {
		Vector2 aux = initialPosition;
		initialPosition = finalPosition;
		finalPosition = aux;
	}
}
 
