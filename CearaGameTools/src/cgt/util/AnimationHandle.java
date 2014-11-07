package cgt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

	public AnimationHandle(CGTSpriteSheet spriteSheet) {
		this.owner = spriteSheet.getOwner();
		mapa = new HashMap<StatePolicy, Animation>();
		walkSheet = spriteSheet.getTexture().getTextureGDX();
		int numberOfColumns = spriteSheet.getColumns();
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / spriteSheet.getColumns(), walkSheet.getHeight()
				/ spriteSheet.getRows());

		ArrayList<TextureRegion> textureList = new ArrayList<TextureRegion>();
		TextureRegion texture = null;
		for (CGTAnimation a : owner.getAnimarions()) {
			textureList.clear();
			if (a.getOwner() == owner && a.getStatePolicies() != null) {
				int initialY = (int)a.getInitialFrame().y;
				int initialX = (int)a.getInitialFrame().x;
				int endingY = (int)a.getEndingFrame().y;
				int endingX = (int)a.getEndingFrame().x;

				for (int i = initialY; i < endingY; i++) {
					for(int j = initialX; j < numberOfColumns; j++){
						texture = new TextureRegion(tmp[i][j]);
						texture.flip(a.isFlipHorizontal(), a.isFlipVertical());
						textureList.add(texture);
					}
					initialX = 0;
				}

				for(int j = 0; j <= endingX; j++){
					texture = new TextureRegion(tmp[endingY][j]);
					texture.flip(a.isFlipHorizontal(), a.isFlipVertical());
					textureList.add(texture);
				}

				TextureRegion[] arrayTex = new TextureRegion[0];
				Animation animation = new Animation(a.getSpriteVelocity(), textureList.toArray(arrayTex));
				animation.setPlayMode(a.getAnimationPolicy());
				
				for(StatePolicy state : a.getStatePolicies()){
					mapa.put(state, animation);
				}
			}
		}
		bobFrame = textureList.get(0);
	}

	/***
	 * 
	 * @param owner
	 *            O personagem que a funcao bra fazer a animacao
	 * @return bobFrama o freme do personagem
	 */
	public TextureRegion getAnimationFrame(){
		// animacao inicial do personagem
		Animation animation = null;

		if(mapa.containsKey(owner.getState())){
			animation = mapa.get(owner.getState());
			bobFrame = animation.getKeyFrame(owner.getStateTime());
		}

		return bobFrame;
	}

	public void stopAni() {

		if(owner.getState().equals(StatePolicy.LOOKUP)) {
			owner.setState(StatePolicy.IDLEUP);
		} else if(owner.getState().equals(StatePolicy.LOOKDOWN)) {
			owner.setState(StatePolicy.IDLEDOWN);
		} else if(owner.getState().equals(StatePolicy.LOOKLEFT)) {
			owner.setState(StatePolicy.IDLELEFT);
		} else if(owner.getState().equals(StatePolicy.LOOKRIGHT)) {
			owner.setState(StatePolicy.IDLERIGHT);
		}
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
}
