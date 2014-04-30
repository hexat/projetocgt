package com.projetocgt.personagens;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Classe utilizada para representar os Personagens do jogo
 * cada personagem sofre uma acao.
 * @autor Bruno Roberto
 */
public class Personagem {
	
	public enum State {
		IDLE, WALKING, JUMPING, DYING, LOOKUP, LOOKDOWN, LOOKLEFT, LOOKRIGHT 
	}
	
	
	private float stateTime = 0;
	public static final float SPEED = 0.3f;				//Velocidade do personagem
	public static final float JUMP_VELOCITY = 1f;		//Velocidade do pulo do personagem
	private float size; 			//Metade de uma unidade
	private boolean colidiu;
	private Vector2 	position = new Vector2();		//Vetor que informa a posi��o do personagem
	private Vector2 	acceleration = new Vector2();	//Vetor que informa a aceleracao do personagem
	private Vector2 	velocity = new Vector2();		//Vetor que informa a velocidade do personagem
	private Rectangle 	bounds = new Rectangle();		// Area que sera' desenhado o personagem
	private State 		state = State.IDLE;				//
	private boolean 	facingLeft = true;				//
	static public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
	private int life;
	private boolean opositor;							//Utilizada para verificar se o personagem e' um opositor
	private int  bonus; 									//Utilizada para verificar o numero de bonus.
	
	/**
	 * Construtor padrao que recebe uma posicao inicial, um life e verifica se o personagem e'
	 * um opositor ou não.
	 * @param position,life,opositor,bonus
	 */
	public Personagem(Vector2 position, int life,boolean opositor,int bonus,float size ) {
		this.bonus=bonus;				//Numero de bonus inicial do personagem
		this.opositor=opositor;
		this.life=life;	
		this.position = position;		//Posicao inicial
		this.bounds.height = size;		//Altura do personagem (Altura da �rea onde o personagem ser� desenhado)
		this.bounds.width =  size;		//Largura do personagem (Largura da �rea onde o personagem ser� desenhado)
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

	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
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
	/**
	 * Executa uma sobre o personagem 
	 * a acao sera' de atropelamento
	 */
	public void acaoAtropelamento(Personagem opositor,float var){

		Vector2 vectorOpo = opositor.getPosition();
		Vector2 vector = this.position;
		if(vector.x > vectorOpo.x+(opositor.getBounds().getWidth()/2)){
			//Sofre uma variacao
			vector.x = vector.x+var;
			vector.y = vector.y+var;
		}else{
			vector.x = vector.x-var;
			vector.y = vector.y-var;
		}
		this.setPosition(vector);
		
	}
	
	public void pare(){
		this.getVelocity().x = 0.0f;
	}
	
	/**
	 * @return the colidiu
	 */
	public boolean isColidiu() {
		return colidiu;
	}

	/**
	 * @param colidiu the colidiu to set
	 */
	public void setColidiu(boolean colidiu) {
		this.colidiu = colidiu;
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

	
}
