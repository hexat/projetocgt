package cgt.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cgt.policy.StatePolicy;

public class AnimationMap implements Serializable {
	private StatePolicy statePolicy;
	private ArrayList<CGTAnimation> animations;
	
	public AnimationMap(StatePolicy statePolicy, CGTAnimation animation) {
		setStatePolicy(statePolicy);
		setAnimations(new ArrayList<CGTAnimation>());
		addAnimation(animation);;
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

	public TextureRegion getRandomAnimation() {
		return animations.get(new Random().nextInt(animations.size())).getAnimation();
	}

	public void addAnimation(CGTAnimation animation) {
		animations.add(animation);
	}
	
}
