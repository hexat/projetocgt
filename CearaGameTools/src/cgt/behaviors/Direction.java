package cgt.behaviors;

import java.io.Serializable;

import cgt.policy.DirectionPolicy;

public class Direction implements Behavior, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2118059060366691269L;

	//Demarcadores da area de movimentacao
	
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	
	
	
	private boolean inteligenceMoviment;
	private float distancia;
	
	//Comportamento a ser aplicado

	private DirectionPolicy directionPolicy;
	
	public Direction(DirectionPolicy directionPolicy){
		setDirectionPolicy(directionPolicy);
		distancia = 500;
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

	/**
	 * @return the minX
	 */
	public int getMinX() {
		return minX;
	}

	/**
	 * @param minX the minX to set
	 */
	public void setMinX(int minX) {
		this.minX = minX;
	}

	/**
	 * @return the minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @param minY the minY to set
	 */
	public void setMinY(int minY) {
		this.minY = minY;
	}

	/**
	 * @return the maxX
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * @param maxX the maxX to set
	 */
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	/**
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @param maxY the maxY to set
	 */
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public void atualizaDistancia(){
		
	}
	
	
	 
}
 
