package com.projetocgt.personagens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projetocgt.personagens.Personagem.State;

/**
 * Classe responsavel pelos sprites do personagem
 * @author roberto.bruno@gmail.com
 */
public class SpritePersonagem {
	
	//Animation
	private static final float RUNNING_FRAME_DURATION = 0.08f;// controla o tempo que um quadro na / ciclo andando corrida será exibido
	
	private TextureRegion bobFrame;
	private static final int    FRAME_COLS = 3;     // 
    private static final int    FRAME_ROWS = 12;     // 
    private int linhaDoSpriteUp = 2;     //
    private int linhaDoSpriteDown = 1;     //
    private int linhaDoSpriteLeft = 4;     //
    private int linhaDoSpriteRight = 3;     //
    private Texture             walkSheet;      //
    private TextureRegion[]     walkFramesUp;
    private TextureRegion[]     walkFramesDown;
    private TextureRegion[]     walkFramesLeft;
    private TextureRegion[]     walkFramesRight;
    private Animation walkAnimationUp; 
    private Animation walkAnimationDown;
    private Animation walkAnimationLeft;
    private Animation walkAnimationRight; 

    float stateTime;

	private TextureRegion[] walkFramesCenario;

	private Animation walkAnimationFogo;   
	
	public SpritePersonagem(){
		
	}
	/***
	 * 
	 * @param caminho
	 * 				Informa qual o caminho do arquivo
	 */
	public void AniRL2(String caminho){
		walkSheet = new Texture(Gdx.files.internal(caminho)); 
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFramesUp = new TextureRegion[FRAME_COLS];
        walkFramesDown = new TextureRegion[FRAME_COLS];
        walkFramesLeft = new TextureRegion[FRAME_COLS];
        walkFramesRight = new TextureRegion[FRAME_COLS];
        
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
            	walkFramesUp[j] = tmp[linhaDoSpriteUp-1][j];
            	walkFramesDown[j] = tmp[linhaDoSpriteDown-1][j];
            	walkFramesLeft[j] = tmp[linhaDoSpriteLeft-1][j];
            	walkFramesRight[j] = tmp[linhaDoSpriteRight-1][j];
            }
        }
        walkAnimationUp = new Animation(RUNNING_FRAME_DURATION, walkFramesUp);
        walkAnimationDown = new Animation(RUNNING_FRAME_DURATION, walkFramesDown);
        walkAnimationLeft = new Animation(RUNNING_FRAME_DURATION, walkFramesLeft);
        walkAnimationRight = new Animation(RUNNING_FRAME_DURATION, walkFramesRight);
	}
	
	public  TextureRegion AniCreCorrendo(Personagem personagem){
		bobFrame = walkAnimationDown.getKeyFrame(personagem.getStateTime(), false);
		//Verifica se o personagem esta olhando para cima e faz a aniamcao
		if(personagem.getState().equals(State.LOOKUP))
			return walkAnimationUp.getKeyFrame(personagem.getStateTime(), true);
		
		if(personagem.getState().equals(State.LOOKDOWN))
			return walkAnimationDown.getKeyFrame(personagem.getStateTime(), true);
		
		if(personagem.getState().equals(State.LOOKLEFT))
			return walkAnimationLeft.getKeyFrame(personagem.getStateTime(), true);
		
		if(personagem.getState().equals(State.LOOKRIGHT))
			return walkAnimationRight.getKeyFrame(personagem.getStateTime(), true);
		
		return bobFrame;
	}
	
	public void AnimaCenario(String caminho,int FRAME_COLS, int FRAME_ROWS){
		walkSheet = new Texture(Gdx.files.internal(caminho)); 
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFramesCenario = new TextureRegion[FRAME_COLS*FRAME_ROWS];
        int index=0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
            	walkFramesCenario[index++] = tmp[i][j];
            }
        }
        walkAnimationFogo = new Animation(RUNNING_FRAME_DURATION, walkFramesCenario);
	}
	
	public  TextureRegion Cenario(Personagem personagem){
		return walkAnimationFogo.getKeyFrame(personagem.getStateTime(), true);
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
	
	
}
