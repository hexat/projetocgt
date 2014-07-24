package com.projetocgt.personagens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cgt.core.CGTGameObject;
import cgt.policy.ActionMovePolicy;
import cgt.policy.AnimationPolicy;
import cgt.policy.MovementPolicy;
import cgt.policy.StatePolicy;
import cgt.unit.Action;
import cgt.unit.ActionCreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projetocgt.cenario.WorldController;

/**
 * Classe responsavel pelos sprites do personagem
 * 
 * @author roberto.bruno@gmail.com
 */
public class SpriteSheet {

	// Animation
	private static final float RUNNING_FRAME_DURATION = 0.08f;
	private TextureRegion bobFrame;
	private int linhaDoSpriteUp; 
	private int linhaDoSpriteDown; 
	private int linhaDoSpriteLeft; 
	private int linhaDoSpriteRight; 
	private int linhaDoSpriteDamege;
	private Texture walkSheet;
	private boolean loop; 
	//	private TextureRegion[] walkFramesUp;
	//	private TextureRegion[] walkFramesDown;
	//	private TextureRegion[] walkFramesLeft;
	//	private TextureRegion[] walkFramesRight;
	private TextureRegion[] walkFramesStandBy;
	private TextureRegion[] framesDamege;
	//	private Animation walkAnimationUp;
	//	private Animation walkAnimationDown;
	//	private Animation walkAnimationLeft;
	//	private Animation walkAnimationRight;
	private Animation animationDamege;
	private AnimationPolicy policy;
	float stateTime;
	private Map<StatePolicy, Animation> mapa;
	private Map<Animation, PlayMode> playModes;
	private TextureRegion[] walkCGTFrames;
	private CGTGameObject owner;
	private GameObject gameObjectOwner;

	private Animation walkCGTAnimation;

	private Animation walkAnimationStandBy;

	public SpriteSheet(CGTGameObject owner) {
		this.owner = owner;
		mapa = new HashMap<StatePolicy, Animation>();
		playModes = new HashMap<Animation, PlayMode>();
	}

	/***
	 * Utilizada para carregar o sprite sheet 
	 * @param caminho
	 *            Informa qual o caminho do arquivo
	 *        FRAME_COLS
	 *        		Informa o numero de colunas do Sprite Sheet  
	 */
	public void loadSprite(String caminho, int FRAME_ROWS, int FRAME_COLS) {
		walkSheet = new Texture(Gdx.files.internal(caminho));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
				/ FRAME_ROWS);

		TextureRegion tex[] = null;
		for (Action a : ActionCreator.getInstance().getActions()) {
			if (a.getOwner() == owner && a.getStatePolicy() != null) {
				tex = new TextureRegion[a.getNumberOfColumns()];
				for (int j = 0; j < tex.length; j++) {
					tex[j]=tmp[a.getSpriteLine() - 1][j];
					if(a.isFlip()) {
						tex[j]=new TextureRegion(tex[j]);
						tex[j].flip(true, false);
					}
				}
				
				Animation animation = new Animation(a.getSpriteVelocity(), tex);
				mapa.put(a.getStatePolicy(), animation);
				mapa.get(a.getStatePolicy()).setPlayMode(PlayMode.valueOf(a.getAnimationPolicy().name()));
			}
		}
		bobFrame = tex[0];
		//		walkFramesUp = new TextureRegion[FRAME_COLS];
		//		walkFramesDown = new TextureRegion[FRAME_COLS];
		//		walkFramesLeft = new TextureRegion[FRAME_COLS];
		//		walkFramesRight = new TextureRegion[FRAME_COLS];
		//		framesDamege = new TextureRegion[FRAME_COLS];
		//
		//		for (int i = 0; i < 1; i++) {
		//			for (int j = 0; j < FRAME_COLS; j++) {
		//				framesDamege[j]=tmp[linhaDoSpriteDamege - 1][j];
		//				walkFramesUp[j] = tmp[linhaDoSpriteUp - 1][j];
		//				walkFramesDown[j] = tmp[linhaDoSpriteDown - 1][j];
		//				walkFramesLeft[j] = tmpLeft[linhaDoSpriteLeft - 1][j];
		//				walkFramesLeft[j].flip(true, false);
		//				walkFramesRight[j] = tmp[linhaDoSpriteRight - 1][j];
		//			}
		//		}

		// Animacao do personagem para cima
		//		walkAnimationUp = new Animation(RUNNING_FRAME_DURATION, walkFramesUp);
		//		// Animacao do personagem para baixo
		//		walkAnimationDown = new Animation(RUNNING_FRAME_DURATION,walkFramesDown);
		//		// Animacao do personagem para esquerda
		//		walkAnimationLeft = new Animation(RUNNING_FRAME_DURATION,walkFramesLeft);
		//		// Animacao do personagem para direita
		//		walkAnimationRight = new Animation(RUNNING_FRAME_DURATION,walkFramesRight);
		//Animacao do personagem com damege
		//		animationDamege = new Animation(RUNNING_FRAME_DURATION,framesDamege);

	}

	/***
	 * 
	 * @param object
	 *            O personagem que a funcao ira fazer a animacao
	 * @return bobFrama o freme do personagem
	 */
	public TextureRegion CGTActorAnimation(GameObject object) {
		gameObjectOwner = object;
		// animacao inicial do personagem
		Animation animation = null;
		// Verifica se o personagem esta olhando para cima e faz a aniamcao
		if (object.getState().equals(StatePolicy.LOOKUP)
				&& mapa.containsKey(StatePolicy.LOOKUP)) {
			animation = mapa.get(StatePolicy.LOOKUP);
			bobFrame = animation.getKeyFrame(object.getStateTime(), loop);
			return bobFrame;
			//return animation.getKeyFrame(object.getStateTime(), loop);
			//return mapa.get(StatePolicy.LOOKUP).getKeyFrame(object.getStateTime(), loop);
		}

		// Verifica se o personagem esta olhando para baixo e faz a aniamcao
		if (object.getState().equals(StatePolicy.LOOKDOWN)
				&& mapa.containsKey(StatePolicy.LOOKDOWN)) {
			animation = mapa.get(StatePolicy.LOOKDOWN);
			bobFrame = animation.getKeyFrame(object.getStateTime(), loop);
			return bobFrame;
			//return animation.getKeyFrame(object.getStateTime(), loop);
			//return mapa.get(StatePolicy.LOOKDOWN).getKeyFrame(object.getStateTime(), loop);
		}

		// Verifica se o personagem esta olhando para esquerda e faz a aniamcao
		if (object.getState().equals(StatePolicy.LOOKLEFT)
				&& mapa.containsKey(StatePolicy.LOOKLEFT)) {

			animation = mapa.get(StatePolicy.LOOKLEFT);
			bobFrame = animation.getKeyFrame(object.getStateTime(), loop);
			return bobFrame;
			
			//return animation.getKeyFrame(object.getStateTime(), loop);
			//return mapa.get(StatePolicy.LOOKLEFT).getKeyFrame(
			//	object.getStateTime(), loop);
		}

		// Verifica se o personagem esta olhando para direita e faz a aniamcao
		if (object.getState().equals(StatePolicy.LOOKRIGHT)
				&& mapa.containsKey(StatePolicy.LOOKRIGHT)) {

			animation = mapa.get(StatePolicy.LOOKRIGHT);
			bobFrame = animation.getKeyFrame(object.getStateTime(), loop);
			return bobFrame;
			//return animation.getKeyFrame(object.getStateTime(), loop);
			//return mapa.get(StatePolicy.LOOKRIGHT).getKeyFrame(
			//	object.getStateTime(), loop);
		}

		if (object.getState().equals(StatePolicy.IDLE)) {
			if(mapa.containsKey(StatePolicy.IDLE)){
				animation = mapa.get(StatePolicy.IDLE);
				bobFrame = animation.getKeyFrame(object.getStateTime(), loop);
				return bobFrame;
			}
			
			else{
					return bobFrame;
			}
		}

		//		if (object.getState().equals(StatePolicy.DAMAGE)) {
		//			mapa.get(StatePolicy.LOOKUP).setPlayMode(PlayMode.valueOf(policy.name()));
		//			return animationDamege.getKeyFrame(object.getStateTime(), loop);
		//		}
		//animation.getKeyFrame(object.getStateTime());

		return mapa.values().iterator().next().getKeyFrame(object.getStateTime());
	}

	/**
	 * Carrega o sprite sheet do GameObject
	 * @param caminho
	 * 				Informa o caminho do arquivo
	 * @param FRAME_ROWS
	 * 				Informa o numero de linhas do CGTSpriteSheet
	 * @param FRAME_COLS
	 * 				Informa o numero de colunas do CGTSpriteSheet
	 */
	//	public void loadingSpriteSheet(String caminho, int FRAME_ROWS, int FRAME_COLS) {
	//		walkSheet = new Texture(Gdx.files.internal(caminho));
	//		TextureRegion[][] tmp = TextureRegion.split(walkSheet,walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()/ FRAME_ROWS);
	//		walkCGTFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	//		int index = 0;
	//		for (int i = 0; i < FRAME_ROWS; i++) {
	//			for (int j = 0; j < FRAME_COLS; j++) {
	//				walkCGTFrames[index++] = tmp[i][j];
	//			}
	//		}
	//		walkCGTAnimation = new Animation(RUNNING_FRAME_DURATION,walkCGTFrames);
	//	}

	//	public TextureRegion CGTAnimation(ActorCGT personagem) {
	//		return walkCGTAnimation.getKeyFrame(personagem.getStateTime(), true);
	//	}

	public void stopAni() {
		gameObjectOwner.setState(StatePolicy.IDLE);
	}

	public void startAni() {
	}

	public int getLinhaDoSpriteUp() {
		return linhaDoSpriteUp;
	}

	public void setLinhaDoSpriteUp(int linhaDoSpriteUp) {
		this.linhaDoSpriteUp = linhaDoSpriteUp;
	}

	public int getLinhaDoSpriteDown() {
		return linhaDoSpriteDown;
	}

	public void setLinhaDoSpriteDown(int linhaDoSpriteDown) {
		this.linhaDoSpriteDown = linhaDoSpriteDown;
	}

	public int getLinhaDoSpriteLeft() {
		return linhaDoSpriteLeft;
	}

	public void setLinhaDoSpriteLeft(int linhaDoSpriteLeft) {
		this.linhaDoSpriteLeft = linhaDoSpriteLeft;
	}

	public int getLinhaDoSpriteRight() {
		return linhaDoSpriteRight;
	}

	public void setLinhaDoSpriteRight(int linhaDoSpriteRight) {
		this.linhaDoSpriteRight = linhaDoSpriteRight;
	}


	/**
	 * @return the linhaDoSpriteDamege
	 */
	public int getLinhaDoSpriteDamege() {
		return linhaDoSpriteDamege;
	}

	/**
	 * @param linhaDoSpriteDamege the linhaDoSpriteDamege to set
	 */
	public void setLinhaDoSpriteDamege(int linhaDoSpriteDamege) {
		this.linhaDoSpriteDamege = linhaDoSpriteDamege;
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

	/**
	 * @return the loop
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * @param loop the loop to set
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}



}
