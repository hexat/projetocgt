package com.projetocgt.personagens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.progetocgt.policy.StatePolicy;

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
	private Texture walkSheet;
	private TextureRegion[] walkFramesUp;
	private TextureRegion[] walkFramesDown;
	private TextureRegion[] walkFramesLeft;
	private TextureRegion[] walkFramesRight;
	private TextureRegion[] walkFramesStandBy;
	private Animation walkAnimationUp;
	private Animation walkAnimationDown;
	private Animation walkAnimationLeft;
	private Animation walkAnimationRight;
	private boolean loop;
	float stateTime;

	private TextureRegion[] walkCGTFrames;

	private Animation walkCGTAnimation;

	private Animation walkAnimationStandBy;

	public SpriteSheet() {

	}

	/***
	 * Utilizada para carregar o sprite sheet 
	 * @param caminho
	 *            Informa qual o caminho do arquivo
	 *        FRAME_COLS
	 *        		Informa o numero de colunas do Sprite Sheet  
	 */
	public void loadSpiteCGTACtor(String caminho, int FRAME_ROWS, int FRAME_COLS) {
		walkSheet = new Texture(Gdx.files.internal(caminho));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		TextureRegion[][] tmpLeft = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);

		walkFramesUp = new TextureRegion[FRAME_COLS];
		walkFramesDown = new TextureRegion[FRAME_COLS];
		walkFramesLeft = new TextureRegion[FRAME_COLS];
		walkFramesRight = new TextureRegion[FRAME_COLS];

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFramesUp[j] = tmp[linhaDoSpriteUp - 1][j];
				walkFramesDown[j] = tmp[linhaDoSpriteDown - 1][j];
				walkFramesLeft[j] = tmpLeft[linhaDoSpriteLeft - 1][j];
				walkFramesLeft[j].flip(true, false);
				walkFramesRight[j] = tmp[linhaDoSpriteRight - 1][j];
			}
		}

		// Animacao do personagem para cima
		walkAnimationUp = new Animation(RUNNING_FRAME_DURATION, walkFramesUp);
		// Animacao do personagem para baixo
		walkAnimationDown = new Animation(RUNNING_FRAME_DURATION,
				walkFramesDown);
		// Animacao do personagem para esquerda
		walkAnimationLeft = new Animation(RUNNING_FRAME_DURATION,
				walkFramesLeft);
		// Animacao do personagem para direita
		walkAnimationRight = new Animation(RUNNING_FRAME_DURATION,
				walkFramesRight);
	}

	/***
	 * 
	 * @param personagem
	 *            O personagem que a funcao ira fazer a animacao
	 * @return bobFrama o freme do personagem
	 */
	public TextureRegion CGTActorAnimation(CGTActor personagem) {

		// animacao inicial do personagem
		bobFrame = walkAnimationDown.getKeyFrame(personagem.getStateTime(),false);
		// Verifica se o personagem esta olhando para cima e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKUP))
			return walkAnimationUp.getKeyFrame(personagem.getStateTime(), loop);
		// Verifica se o personagem esta olhando para baixo e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKDOWN))
			return walkAnimationDown.getKeyFrame(personagem.getStateTime(),loop);
		// Verifica se o personagem esta olhando para esquerda e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKLEFT))
			return walkAnimationLeft.getKeyFrame(personagem.getStateTime(),loop);
		// Verifica se o personagem esta olhando para direita e faz a aniamcao
		if (personagem.getState().equals(StatePolicy.LOOKRIGHT))
			return walkAnimationRight.getKeyFrame(personagem.getStateTime(),loop);
		return bobFrame;
	}
	/**
	 * Carrega o sprite sheet do GameObject
	 * @param caminho
	 * 				Informa o caminho do arquivo
	 * @param FRAME_ROWS
	 * 				Informa o numero de linhas do SpriteSheet
	 * @param FRAME_COLS
	 * 				Informa o numero de colunas do SpriteSheet
	 */
	public void loadingSpriteSheet(String caminho, int FRAME_ROWS, int FRAME_COLS) {
		walkSheet = new Texture(Gdx.files.internal(caminho));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()/ FRAME_ROWS);
		walkCGTFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkCGTFrames[index++] = tmp[i][j];
			}
		}
		walkCGTAnimation = new Animation(RUNNING_FRAME_DURATION,walkCGTFrames);
	}

	public TextureRegion CGTAnimation(CGTActor personagem) {
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

}
