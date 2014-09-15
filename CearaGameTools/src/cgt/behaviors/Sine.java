package cgt.behaviors;

import java.io.Serializable;

import cgt.policy.MovementPolicy;

public class Sine implements Behavior, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6057692222413272350L;
	private MovementPolicy movementPolicy;
	private int min;
	private int max;
	private boolean atFirstStep;
	
	public Sine(MovementPolicy movementPolicy){
		setMovementPolicy(movementPolicy);
	}
	
	public void setMovementPolicy(MovementPolicy movementPolicy) {
		this.movementPolicy = movementPolicy;
	}
	 

	public MovementPolicy getMovementPolicy() {
		return movementPolicy;
	}

	@Override
	public String getBehaviorPolicy() {
		return movementPolicy.name();
	}

	@Override
	public String toString() {
		return "Sine [movementPolicy=" + movementPolicy + "]";
	}

	public boolean isAtFirstStep() {
		return atFirstStep;
	}

	public void setAtFirstStep(boolean firstStep) {
		this.atFirstStep = firstStep;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	 
}
 
