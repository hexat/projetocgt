package com.projetocgt.personagens;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.Personagem.DirectionPolicy;
/**
 * Classe utilizada para representar os Personagens do jogo
 * cada personagem sofre uma acao.
 * @autor Bruno Roberto
 */
public class Personagem {

	public enum State {
		IDLE, WALKING, JUMPING, DYING, LOOKUP, LOOKDOWN, LOOKLEFT, LOOKRIGHT 
	}

	public enum DirectionPolicy {
		FOUR_DIRECTION 
	}
	
	
	private float stateTime = 0;
	public static final float SPEED = 180f;				//Velocidade do personagem
	public static final float JUMP_VELOCITY = 1f;		//Velocidade do pulo do personagem
	private float size; 			//Metade de uma unidade
	private Vector2 	position = new Vector2();		//Vetor que informa a posicao do personagem
	private Vector2 	acceleration = new Vector2();	//Vetor que informa a aceleracao do personagem
	private Vector2 	velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	private Rectangle 	bounds = new Rectangle();		// Area que sera' desenhado o personagem
	private State 		state = State.IDLE;				//
	private boolean 	facingLeft = true;				//
	static public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
	private int life;
	private boolean opositor;							//Utilizada para verificar se o personagem e' um opositor
	private int  bonus; 									//Utilizada para verificar o numero de bonus.
	private Rectangle rectPer;
	private Texture texturePersonagem;
	float posXColider, posYColider; 
	
	
	private SpriteSheet spriteSheet;
	private DirectionPolicy directionPolicy;
	
	/**
	 * Construtor padrao que recebe uma posicao inicial, um life e verifica se o personagem e'
	 * um opositor ou não.
	 * @param position,life,opositor,bonus,size, colider
	 */
	public Personagem(Vector2 position, int life,boolean opositor,int bonus,float size,float colider,float posXColider,float posYColider ) {
		this.bonus=bonus;				//Numero de bonus inicial do personagem
		this.opositor=opositor;
		this.life=life;	
		this.position = position;		//Posicao inicial
		this.bounds.height = size;		//Altura do personagem (Altura da area onde o personagem sera desenhado)
		this.bounds.width =  size;		//Largura do personagem (Largura da area onde o personagem sera desenhado)
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
	public void setState(State newState) {
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

	public Vector2 getAcceleration() {
		return acceleration;
		
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public State getState() {
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isOpositor() {
		return opositor;
	}

	public void setOpositor(boolean opositor) {
		this.opositor = opositor;
	}
	
	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	/***
	 * 
	 * @return retornao valor do bonus menos um
	 */
	public int tiraUmDoLife(){
		return this.life--;
	}
	
	public void pare(){
		this.getVelocity().x = 0.0f;
	}
	
	/**
	 * @return the size
	 */
	public float getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(float size) {
		this.size = size;
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

	/**
	 * @return the texturePersonagem
	 */
	public Texture getTexturePersonagem() {
		return texturePersonagem;
	}

	/**
	 * @param texturePersonagem the texturePersonagem to set
	 */
	public void setTexturePersonagem(Texture texturePersonagem) {
		this.texturePersonagem = texturePersonagem;
	}

	public void setSprite(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public SpriteSheet getSprite() {
		return spriteSheet;
	}

	public DirectionPolicy getDirectionPolicy() {
		return directionPolicy;
	}
	
}
