package cgt.behaviors;

import java.io.Serializable;

import cgt.core.AbstractBehavior;
import com.badlogic.gdx.math.Vector2;

public class SineWave extends AbstractBehavior {

	private double amplitude;
	private double frequency;
	private double phase;
	private int maxX;
	private Vector2 enemyPosition;
	
	
	public SineWave(double amplitude, double frequency, double phase){
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.phase = phase;
		enemyPosition = null;
	}
	
	@Override
	public String getBehaviorPolicy() {
		return "SineWave";
	}

    @Override
    public void act() {
        if(getEnemyPosition() == null){
            setEnemyPosition(getOwner().getPosition().cpy());
        }
        float point = (float) (getAmplitude()*(Math.sin(2*getFrequency()*Math.PI*getOwner().getPosition().x + getPhase())));
        getOwner().getPosition().y = point;
        getOwner().getVelocity().x = getOwner().getSpeed();

        if (getOwner().getSpeed() > 0) {
            if (getOwner().getPosition().x > getMaxX()){
                getOwner().getPosition().x = getEnemyPosition().x;
                getOwner().getPosition().y = getEnemyPosition().y;
                getOwner().getVelocity().y = 0;
                getOwner().getVelocity().x = 0;
            }
        } else {
            if (getOwner().getPosition().x < getMaxX()){
                getOwner().getPosition().x = getEnemyPosition().x;
                getOwner().getPosition().y = getEnemyPosition().y;
                getOwner().getVelocity().y = 0;
                getOwner().getVelocity().x = 0;
            }
        }
    }

    @Override
    public void start() {

    }

    public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public double getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public double getPhase() {
		return phase;
	}

	public void setPhase(double phase) {
		this.phase = phase;
	}

	public Vector2 getEnemyPosition() {
		return enemyPosition;
	}

	public void setEnemyPosition(Vector2 enemyPosition) {
		this.enemyPosition = enemyPosition;
	}
}
