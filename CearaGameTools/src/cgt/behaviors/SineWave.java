package cgt.behaviors;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class SineWave implements Behavior,Serializable {

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
