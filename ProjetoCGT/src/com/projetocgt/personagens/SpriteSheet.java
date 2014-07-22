package com.projetocgt.personagens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cgt.policy.ActionMovePolicy;
import cgt.policy.MovementPolicy;
import cgt.policy.StatePolicy;
import cgt.unit.Action;
import cgt.unit.ActionCreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
	private boolean loop;
	float stateTime;

	private Map<StatePolicy, Animation> mapa;
	
	private TextureRegion[] walkCGTFrames;

	private Animation walkCGTAnimation;

	private Animation walkAnimationStandBy;

	
	public SpriteSheet() {
		mapa = new HashMap<StatePolicy, Animation>();
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
		TextureRegion[][] tmpInvert = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);

		TextureRegion tex[] = null;
		for (Action a : ActionCreator.getInstance().getActions()) {
			System.out.println(a.getActionName());
			if (a.getActionName().equals(ActionMovePolicy.WALK_DOWN.name())) {
				tex = new TextureRegion[a.getNumberOfColumns()];

				for (int j = 0; j < tex.length; j++) {
					if(!a.isFlip()){
						tex[j]=tmp[a.getSpriteLine() - 1][j];
					}else{
						tex[j]=tmpInvert[a.getSpriteLine() - 1][j];
						tex[j].flip(true, false);
					}
				}
				mapa.put(StatePolicy.LOOKDOWN, new Animation(a.getSpriteVelocity(), tex));
			} else if (a.getActionName().equals(ActionMovePolicy.WALK_UP.name())) {
				tex = new TextureRegion[a.getNumberOfColumns()];

				for (int j = 0; j < tex.length; j++) {
					if(!a.isFlip()){
						tex[j]=tmp[a.getSpriteLine() - 1][j];
					}else{
						tex[j]=tmpInvert[a.getSpriteLine() - 1][j];
						tex[j].flip(true, false);
					}
				}
				mapa.put(StatePolicy.LOOKUP, new Animation(a.getSpriteVelocity(), tex));
			} else if (a.getActionName().equals(ActionMovePolicy.WALK_LEFT.name())) {
				tex = new TextureRegion[a.getNumberOfColumns()];

				for (int j = 0; j < tex.length; j++) {
					if(!a.isFlip()){
						tex[j]=tmp[a.getSpriteLine() - 1][j];
					}else{
						tex[j]=tmpInvert[a.getSpriteLine() - 1][j];
						tex[j].flip(true, false);
					}
				}
				mapa.put(StatePolicy.LOOKLEFT, new Animation(a.getSpriteVelocity(), tex));
			} else if (a.getActionName().equals(ActionMovePolicy.WALK_RIGHT.name())) {
				tex = new TextureRegion[a.getNumberOfColumns()];

				for (int j = 0; j < tex.length; j++) {
					if(!a.isFlip()){
						tex[j]=tmp[a.getSpriteLine() - 1][j];
					}else{
						tex[j]=tmpInvert[a.getSpriteLine() - 1][j];
						tex[j].flip(true, false);
					}
				}
				mapa.put(StatePolicy.LOOKRIGHT, new Animation(a.getSpriteVelocity(), tex));
			}
		}
		
		
		
		
		
		
		
		
		
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
	 * @param personagem
	 *            O personagem que a funcao ira fazer a animacao
	 * @return bobFrama o freme do personagem
	 */
	public TextureRegion CGTActorAnimation(ActorCGT personagem) {

		// animacao inicial do personagem
		bobFrame = mapa.get(StatePolicy.LOOKLEFT).getKeyFrame(personagem.getStateTime(),false);
		// Verifica se o personagem esta olhando para cima e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKUP))
			return mapa.get(StatePolicy.LOOKUP).getKeyFrame(personagem.getStateTime(), loop);
		// Verifica se o personagem esta olhando para baixo e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKDOWN))
			return mapa.get(StatePolicy.LOOKDOWN).getKeyFrame(personagem.getStateTime(),loop);
		// Verifica se o personagem esta olhando para esquerda e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKLEFT))
			return mapa.get(StatePolicy.LOOKLEFT).getKeyFrame(personagem.getStateTime(),loop);
		// Verifica se o personagem esta olhando para direita e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKRIGHT))
			return mapa.get(StatePolicy.LOOKRIGHT).getKeyFrame(personagem.getStateTime(),loop);
		if (personagem.getState().equals(StatePolicy.DAMAGE))
			return animationDamege.getKeyFrame(personagem.getStateTime(),loop);
		return bobFrame;
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

	public TextureRegion CGTAnimation(ActorCGT personagem) {
		return walkCGTAnimation.getKeyFrame(personagem.getStateTime(), true);
	}

	public void stopAni() {
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

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
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

}
