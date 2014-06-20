package com.progetocgt.behaviors;

public class Direction implements Behavior{
	private int maxSpeed;
	private int acceleration;
	private int deceleration;
	//private DirectionPolicy directionPolicy;
	@Override
	public String getBehaviorPolicy() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}
	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	/**
	 * @return the acceleration
	 */
	public int getAcceleration() {
		return acceleration;
	}
	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}
	/**
	 * @return the deceleration
	 */
	public int getDeceleration() {
		return deceleration;
	}
	/**
	 * @param deceleration the deceleration to set
	 */
	public void setDeceleration(int deceleration) {
		this.deceleration = deceleration;
	}

}
