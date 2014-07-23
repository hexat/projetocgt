package com.projetocgt.personagens;
import java.util.ArrayList;

import cgt.core.CGTActor;
import cgt.policy.DirectionPolicy;
import cgt.util.Jump;
import cgt.util.Move;

import com.badlogic.gdx.math.Vector2;
/**
 * Classe utilizada para representar os Personagens do jogo
 * cada personagem sofre uma acao.
 * @autor Bruno Roberto
 */
public class ActorCGT extends GameObject {
					
	private boolean facingLeft = true;				
	private DirectionPolicy directionPolicy;
	private ArrayList<Move> moves;
	private ArrayList<Jump> jumps;
	private ArrayList<Projectile> listaDeProjectiles = new ArrayList<Projectile>();
	private int jumpDefault;
	private int fireDefault;
	private boolean invincible;
	
	/**
	 * Construtor padrao que recebe uma posicao inicial, um life e verifica se o personagem e'
	 * um opositor ou não.
	 * @param position,life,opositor,bonus,size, colider
	 */
	public ActorCGT(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ) {
		super(position, width, height, colider, posXColider, posYColider);
		setCgtGameObject(new CGTActor());
		this.directionPolicy = DirectionPolicy.FOUR_DIRECTION;
		this.invincible=false;
	}
	
	public ActorCGT(CGTActor actor){
		super(actor);

		moves = actor.getMoves();
		jumps = actor.getJumps();

		
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
	
	
	public void updateProjectiles(){
		for(int i=0; i < listaDeProjectiles.size(); i++){
			listaDeProjectiles.get(i).setPosition(getPosition());
		}
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

	public void pare(){
		this.getVelocity().x = 0.0f;
	}

	public DirectionPolicy getDirectionPolicy() {
		return directionPolicy;
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

	/**
	 * @return the moves
	 */
	public ArrayList<Move> getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}

	/**
	 * @return the jumps
	 */
	public ArrayList<Jump> getJumps() {
		return jumps;
	}

	/**
	 * @param jumps the jumps to set
	 */
	public void setJumps(ArrayList<Jump> jumps) {
		this.jumps = jumps;
	}

	/**
	 * @return the listaDeProjectiles
	 */
	public ArrayList<Projectile> getListaDeProjectiles() {
		return listaDeProjectiles;
	}

	/**
	 * @param listaDeProjectiles the listaDeProjectiles to set
	 */
	public void setListaDeProjectiles(ArrayList<Projectile> projectiles) {
		this.listaDeProjectiles = projectiles;
	}

	/**
	 * @return the invincible
	 */
	public boolean isInvincible() {
		return invincible;
	}

	/**
	 * @param invincible the invincible to set
	 */
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
}
