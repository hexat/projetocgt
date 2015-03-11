package cgt.behaviors;

import java.io.Serializable;

import cgt.core.AbstractBehavior;
import cgt.policy.MovementPolicy;

public class Sine extends AbstractBehavior {
	private MovementPolicy movementPolicy;
	private int min;
	private int max;

	// used in gdx mode
	private boolean atFirstStep;

    public Sine() {}
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
    public void act() {
        if (movementPolicy == MovementPolicy.VERTICAL) {
            if (isAtFirstStep())
                getOwner().getVelocity().y = getOwner().getSpeed();
            else
                getOwner().getVelocity().y = -getOwner().getSpeed();

            if (getOwner().getPosition().y < getMin()) {
                setAtFirstStep(true);
            }
            if (getOwner().getPosition().y > getMax()) {
                setAtFirstStep(false);
            }
        } else if (movementPolicy == MovementPolicy.HORIZONTAL) {

            if (isAtFirstStep())
                getOwner().getVelocity().x = -getOwner().getSpeed();
            else
                getOwner().getVelocity().x = getOwner().getSpeed();

            if (getOwner().getPosition().x < getMin()) {
                setAtFirstStep(false);
            }
            if (getOwner().getPosition().x > getMax()) {
                setAtFirstStep(true);
            }
        } else if (movementPolicy == MovementPolicy.WIDTH) {
            if (isAtFirstStep()) {
                getOwner().getCollision().width += getOwner().getSpeed();
                getOwner().getBounds().width += getOwner().getSpeed();
            }

            else {
                getOwner().getBounds().width -= getOwner().getSpeed();
                getOwner().getCollision().width -= getOwner().getSpeed();
            }

            if (getOwner().getCollision().width < getMin())
                setAtFirstStep(true);
            else if (getOwner().getCollision().width > getMax())
                setAtFirstStep(false);
        } else if (movementPolicy == MovementPolicy.HEIGHT) {
            if (isAtFirstStep()) {
                getOwner().getBounds().height += getOwner().getSpeed();
                getOwner().getCollision().height += getOwner().getSpeed();
            }
            else {
                getOwner().getBounds().height -= getOwner().getSpeed();
                getOwner().getCollision().height -= getOwner().getSpeed();
            }

            if (getOwner().getCollision().height < getMin())
                setAtFirstStep(true);
            else if (getOwner().getCollision().height > getMax())
                setAtFirstStep(false);
        }
    }

    @Override
    public void start() {

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
 
