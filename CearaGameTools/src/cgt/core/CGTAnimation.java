package cgt.core;

import java.io.Serializable;

import cgt.core.CGTGameObject;
import cgt.game.CGTGame;
import cgt.game.CGTSpriteSheet;
import cgt.game.SpriteSheetDB;
import cgt.util.AnimationHandle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

public class CGTAnimation implements Serializable{
	private String spriteSheetId;
	private Vector2 initialFrame;
	private Vector2 endingFrame;
	private CGTGameObject owner;
	private float spriteVelocity;
	private boolean flipHorizontal;
	private boolean flipVertical;
	private PlayMode animationPolicy;
	private AnimationHandle animation;

	public CGTAnimation(String spriteSheetId) {
		this.spriteSheetId = spriteSheetId;
		spriteVelocity = 1;
		flipHorizontal = false;
		flipVertical = false;
		animationPolicy = PlayMode.LOOP;
	}
	

	public CGTGameObject getOwner() {
		return owner;
	}

	protected void setOwner(CGTGameObject owner) {
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

	public TextureRegion getAnimation() {
		if(animation==null){
			this.animation = new AnimationHandle(this);
		}
		return animation.getAnimationFrame();
	}

	public AnimationHandle getCGTAnimation() {
		if(animation==null){
			this.animation = new AnimationHandle(this);
		}
		return animation;
	}

	public CGTSpriteSheet getSpriteSheet() {
        return CGTGame.get().getSpriteDB().find(spriteSheetId);
	}
}
