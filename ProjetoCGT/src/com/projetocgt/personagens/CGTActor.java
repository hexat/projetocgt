package com.projetocgt.personagens;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.progetocgt.policy.StatePolicy;
/**
 * Classe utilizada para representar os Personagens do jogo
 * cada personagem sofre uma acao.
 * @autor Bruno Roberto
 */
public class CGTActor extends CGTGameObject{

	public enum DirectionPolicy {
		FOUR_DIRECTION 
	}
	private float stateTime = 0;
	private float speed = 0f;				//Velocidade do personagem
	private Vector2 position = new Vector2();		//Vetor que informa a posicao do personagem
	private Vector2 velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	private Rectangle bounds = new Rectangle();		// Area que sera' desenhado o personagem
	private StatePolicy state = StatePolicy.IDLE;				//
	private boolean facingLeft = true;				
	private Rectangle rectPer;
	private float posXColider, posYColider; 
	private SpriteSheet spriteSheet;
	private DirectionPolicy directionPolicy;
	private int jumpDefault;
	private int fireDefault;
	/**
	 * Construtor padrao que recebe uma posicao inicial, um life e verifica se o personagem e'
	 * um opositor ou não.
	 * @param position,life,opositor,bonus,size, colider
	 */
	public CGTActor(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ) {
		this.position = position;		//Posicao inicial
		this.bounds.height = width;		//Altura do personagem (Altura da area onde o personagem sera desenhado)
		this.bounds.width =  height;		//Largura do personagem (Largura da area onde o personagem sera desenhado)
		this.posXColider=posXColider;
		this.posYColider=posYColider;
		this.rectPer = new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider);
		
		this.directionPolicy = DirectionPolicy.FOUR_DIRECTION;
	}
	
	/**
	 * Adiciona o estado atual do Personagem
	 * @param newState 
	 * 					Estado do Personagem
	 */
	public void setState(StatePolicy newState) {
		this.state = newState;
	}
	/*Adicionar a distância percorrida no delta segundos para a posição actual de Bob. 
	 * Usamos velocity.tmp () porque a tmp () cria um novo objeto com o mesmo valor de velocidade e multiplicarmos o 
	 * valor desse objeto com o delta tempo decorrido. Em Java, temos de ter cuidado
	 *  em como estamos usando referências como velocidade e posição são ambos objetos Vector2. Mais informações sobre 
	 *  vetores aqui
	 */
	/**
	 * Adiciona a distância percorrida delta segundos para a posição atual de Bob.
	 * @param delta
	 */
	public void update(float delta) {
		setStateTime(getStateTime() + delta);
		position.add(velocity.cpy().scl(delta));
		rectPer.x=this.position.x+posXColider;
		rectPer.y=this.position.y+posYColider;
	}

	public Vector2 getPosition() {
		return position;
	}
	/***
	 * 
	 * @param p
	 */
	public void setPosition(Vector2 p) {
		this.position.x = p.x;
		this.position.y = p.y;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public StatePolicy getState() {
		return this.state;
	}

	/**
	 * Verificas se esta olhando para a esquerda
	 * @return
	 */
	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	public void pare(){
		this.getVelocity().x = 0.0f;
	}

	/**
	 * @return the rectPer
	 */
	public Rectangle getRectPer() {
		return rectPer;
	}

	/**
	 * @param rectPer the rectPer to set
	 */
	public void setRectPer(Rectangle rectPer) {
		this.rectPer = rectPer;
	}


	public void setSpriteSheet(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	public DirectionPolicy getDirectionPolicy() {
		return directionPolicy;
	}

	/**
	 * @return the sPEED
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param sPEED the sPEED to set
	 */
	public void setSpeed(float sPEED) {
		speed = sPEED;
	}

	/**
	 * @return the jumpDefault
	 */
	public int getJumpDefault() {
		return jumpDefault;
	}

	/**
	 * @param jumpDefault the jumpDefault to set
	 */
	public void setJumpDefault(int jumpDefault) {
		this.jumpDefault = jumpDefault;
	}

	/**
	 * @return the fireDefault
	 */
	public int getFireDefault() {
		return fireDefault;
	}

	/**
	 * @param fireDefault the fireDefault to set
	 */
	public void setFireDefault(int fireDefault) {
		this.fireDefault = fireDefault;
	}
}
