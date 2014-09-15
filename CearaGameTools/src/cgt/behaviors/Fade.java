package cgt.behaviors;

import java.io.Serializable;

import cgt.policy.FadePolicy;

public class Fade implements Behavior, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4876605710575310734L;

	private int fadeInTIme;
	private boolean started; 
	private int fadeOutTime;
	private FadePolicy fadePolicy;
	
	public Fade(FadePolicy fadePolicy){
		setFadePolicy(fadePolicy);
	}
	
	public int getFadeInTime() {
		return fadeInTIme;
	}

	public void setFadeInTime(int fadeInTIme) {
		this.fadeInTIme = fadeInTIme;
	}

	public int getFadeOutTime() {
		return fadeOutTime;
	}
	
	public void setFadeOutTime(int fadeOutTime) {
		this.fadeOutTime = fadeOutTime;
	} 
	
	public FadePolicy getFadePolicy() {
		return fadePolicy;
	}

	public void setFadePolicy(FadePolicy fadePolicy) {
		this.fadePolicy = fadePolicy;
	}

	@Override
	public String getBehaviorPolicy() {
		return fadePolicy.name();
	}

	@Override
	public String toString() {
		return "Fade [fadeInTIme=" + fadeInTIme + ", fadeOutTime="
				+ fadeOutTime + ", fadePolicy=" + fadePolicy + "]";
	}


	/**
	 * @return the started
	 */

	public boolean isStarted() {
		return started;
	}


	/**
	 * @param started the started to set
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}
	
}
 
