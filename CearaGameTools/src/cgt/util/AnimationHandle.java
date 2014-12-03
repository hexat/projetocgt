package cgt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cgt.core.CGTAddOn;
import cgt.core.CGTGameObject;
import cgt.policy.AnimationPolicy;
import cgt.policy.StatePolicy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Classe responsavel pelos sprites do personagem
 * 
 * @author roberto.bruno@gmail.com
 */
public class AnimationHandle {
	// Animation
	public static final float RUNNING_FRAME_DURATION = 0.08f;
	private TextureRegion bobFrame;
	private Texture walkSheet;
	private AnimationPolicy policy;
	private Map<StatePolicy, Animation> mapa;

	private CGTGameObject owner;
	private Animation animationGDX;

	public AnimationHandle(CGTAnimation animation) {
		this.owner = animation.getOwner();
		mapa = new HashMap<StatePolicy, Animation>();
		walkSheet = animation.getSpriteSheet().getTexture().getTextureGDX();
		int numberOfColumns = animation.getSpriteSheet().getColumns();
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / animation.getSpriteSheet().getColumns(), walkSheet.getHeight()
				/ animation.getSpriteSheet().getRows());

		ArrayList<TextureRegion> textureList = new ArrayList<TextureRegion>();
		TextureRegion texture = null;
//		for (CGTAnimation a : owner.getAnimarions()) {
//		if (animation.getOwner() == owner) {
			int initialY = (int)animation.getInitialFrame().y;
			int initialX = (int)animation.getInitialFrame().x;
			int endingY = (int)animation.getEndingFrame().y;
			int endingX = (int)animation.getEndingFrame().x;

			for (int i = initialY; i < endingY; i++) {
				for(int j = initialX; j < numberOfColumns; j++){
					texture = new TextureRegion(tmp[i][j]);
					texture.flip(animation.isFlipHorizontal(), animation.isFlipVertical());
					textureList.add(texture);
				}
				initialX = 0;
			}

			for(int j = 0; j <= endingX; j++){
				texture = new TextureRegion(tmp[endingY][j]);
				texture.flip(animation.isFlipHorizontal(), animation.isFlipVertical());
				textureList.add(texture);
			}

			TextureRegion[] arrayTex = new TextureRegion[0];

			animationGDX = new Animation(animation.getSpriteVelocity(), textureList.toArray(arrayTex));
			animationGDX.setPlayMode(animation.getAnimationPolicy());

		bobFrame = textureList.get(0);
	}

	/***
	 * A animacao e setada a partir do delta da LibGDX
	 * @param owner
	 *            O personagem que a funcao bra fazer a animacao
	 * @return bobFrama o freme do personagem
	 */
	public TextureRegion getAnimationFrame(){
		return animationGDX.getKeyFrame(owner.getStateTime());
	}

	/**
	 * @return the policy
	 */
	public AnimationPolicy getPolicy() {
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy(AnimationPolicy policy) {
		this.policy = policy;
	}
	
	public Animation getAnimationGDX() {
		return animationGDX;
	}
	
	public boolean isDrawing() {
		return animationGDX.isAnimationFinished(owner.getStateTime());
	}
}
