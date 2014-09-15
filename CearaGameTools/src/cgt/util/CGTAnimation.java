package cgt.util;

import java.util.ArrayList;

import cgt.core.CGTGameObject;
import cgt.policy.StatePolicy;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

public class CGTAnimation {
	private CGTSound sound;
	private Vector2 initialFrame;
	private Vector2 endingFrame;
	private CGTGameObject owner;
	private float spriteVelocity;
	private boolean flipHorizontal;
	private boolean flipVertical;
	private PlayMode animationPolicy;
	private ArrayList<StatePolicy> statePolicies;

	public CGTAnimation() {
		statePolicies = new ArrayList<StatePolicy>();
		spriteVelocity = 1;
		flipHorizontal = false;
		flipVertical = false;
		animationPolicy = PlayMode.LOOP;
	}

	public CGTAnimation(CGTGameObject object) {
		this();
		this.owner = object;
	}

	public CGTSound getSound() {
		return sound;
	}

	public void setSound(CGTSound sound) {
		this.sound = sound;
	}

	public void setSpriteLine(int spriteLine) {
		initialFrame = new Vector2(0, spriteLine - 1);
		endingFrame = new Vector2(owner.getSpriteSheet().getColumns() - 1,
				spriteLine - 1);
	}

	public CGTGameObject getOwner() {
		return owner;
	}

	public void setOwner(CGTGameObject owner) {
		this.owner = owner;
	}

	public float getSpriteVelocity() {
		return spriteVelocity;
	}

	public void setSpriteVelocity(float spriteVelocity) {
		this.spriteVelocity = spriteVelocity;
	}

	public PlayMode getAnimationPolicy() {
		return animationPolicy;
	}

	public void setAnimationPolicy(PlayMode animationPolicy) {
		this.animationPolicy = animationPolicy;
	}

	public ArrayList<StatePolicy> getStatePolicies() {
		return statePolicies;
	}

	public void setStatePolicies(ArrayList<StatePolicy> statePolicy) {
		this.statePolicies = statePolicy;
	}

	public void addStatePolicy(StatePolicy state) {
		if (!statePolicies.contains(state)) {
			statePolicies.add(state);
		}
	}

	public Vector2 getInitialFrame() {
		return initialFrame;
	}

	public void setInitialFrame(Vector2 initialFrame) {
		this.initialFrame = initialFrame;
	}

	public Vector2 getEndingFrame() {
		return endingFrame;
	}

	public void setEndingFrame(Vector2 endingFrame) {
		this.endingFrame = endingFrame;
	}

	public boolean isFlipVertical() {
		return flipVertical;
	}

	public void setFlipVertical(boolean flipVertical) {
		this.flipVertical = flipVertical;
	}

	public boolean isFlipHorizontal() {
		return flipHorizontal;
	}

	public void setFlipHorizontal(boolean flipHorizontal) {
		this.flipHorizontal = flipHorizontal;
	}
}
