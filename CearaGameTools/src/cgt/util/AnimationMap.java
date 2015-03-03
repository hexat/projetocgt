package cgt.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import cgt.core.CGTAnimation;
import cgt.policy.StatePolicy;

public class AnimationMap implements Serializable {
	private StatePolicy statePolicy;
	private ArrayList<CGTAnimation> animations;
	
	public AnimationMap(){
		
	}
	public AnimationMap(StatePolicy statePolicy, CGTAnimation... anis) {
		setStatePolicy(statePolicy);
		setAnimations(new ArrayList<CGTAnimation>());
		for (int i = 0; i < anis.length; i++) {
			addAnimation(anis[i]);
		}
	}

	public StatePolicy getStatePolicy() {
		return statePolicy;
	}

	public void setStatePolicy(StatePolicy statePolicy) {
		this.statePolicy = statePolicy;
	}

	public ArrayList<CGTAnimation> getAnimations() {
		return animations;
	}

	public void setAnimations(ArrayList<CGTAnimation> animations) {
		this.animations = animations;
	}

	public CGTAnimation getRandomAnimation() {
		return animations.get(new Random().nextInt(animations.size()));
	}

	public void addAnimation(CGTAnimation animation) {
		animations.add(animation);
	}
	
}
