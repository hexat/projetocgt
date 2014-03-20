package com.projetocgt.cenario;

import com.badlogic.gdx.math.Vector2;
import com.projetocgt.core.petri.entity.Place;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.Personagem.State;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

	// Possiveis movimentos do personagem
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE, UP, DOWN
	};

	private World world;
	private Personagem bob;
	private boolean flagCanWalk;
	private Vector2 posAuxBob;
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);

		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);

		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
	};

	// Este construtor recebe o mundo como parametro
	public WorldController(World world) {
		this.world = world;
		// Posicao inicial do personagem
		this.bob = world.getPersonagem();
	}

	// ** Key presses and touches **************** //
	// Funciona na descida do botao
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	// Funciona na subida do botao
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	// Retorna aposicao do personagem em forma de Vetor2
	public Vector2 posicaoBob() {
		return bob.getPosition();
	}

	/** The main update method **/
	// O metodo update e chamado a cada ciclo do loop principal.
	public void update(float delta) {
		// Processa a entrada de algum parametro
		processInput();
		// Atualizaes do Personagem. Personagem tem um metodo de atualizacoo
		// dedicado.
		bob.update(delta);
	}

	public void movimeto(float x, float y) {
		bob.setState(State.WALKING);
		if (bob.getPosition().x < x) {
			bob.setFacingLeft(true);
		} else {
			bob.setFacingLeft(false);
		}
		
		// verifica se o bob esta fora do screen
		if (x + bob.getBounds().getWidth() > world.getNumBlocosH()) {
			x = world.getNumBlocosH() - bob.getBounds().getWidth();
		}
		if (y + bob.getBounds().getHeight() > world.getNumBlocosV()) {
			y = world.getNumBlocosV() - bob.getBounds().getHeight();
		}
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		// fim da verificacao

		bob.getPosition().x = x;
		bob.getPosition().y = y;
	}

	public boolean onScreen() {

		return !(bob.getPosition().y + bob.getBounds().height > (world.getNumBlocosV() - 0.01f)) ||
		(bob.getPosition().y < 0.0f) ||
		(bob.getPosition().x < 0.0f) || 
		bob.getPosition().x + bob.getBounds().getWidth() > (world.getNumBlocosH() - 0.01f);
				
	}
	public boolean onScreen(float x, float y) {

		return !(y + bob.getBounds().height > (world.getNumBlocosV())) ||
		(y < 0.0f) ||
		(x < 0.0f) || 
		x + bob.getBounds().getWidth() > (world.getNumBlocosH());
				
	}

	/** Change Bob's state and parameters based on input controls **/
	private void processInput() {

		if (keys.get(Keys.UP)) {
			// Verifica se o personagem pode andar
			if (bob.getPosition().y + bob.getBounds().height > (world
					.getNumBlocosV() - 0.01f)) {
				bob.getVelocity().y = 0.0f;
			} else {
				// O personagem esta olhando para a cima
				bob.setFacingLeft(false);
				bob.setState(State.WALKING);
				bob.getVelocity().y = Personagem.SPEED;
			}
		}

		if (keys.get(Keys.DOWN)) {
			// Verifica se o personagem pode andar
			if (bob.getPosition().y < 0.0f) {
				bob.getVelocity().y = 0.0f;
			} else {
				// O personagem esta olhando para a baixo
				bob.setFacingLeft(true);
				bob.setState(State.WALKING);
				bob.getVelocity().y = -Personagem.SPEED;
			}
		}

		if (keys.get(Keys.LEFT)) {
			// Verifica se o personagem pode andar
			if (bob.getPosition().x < 0.0f) {
				bob.getVelocity().x = 0.0f;
			} else {
				// O personagem esta olhando para a esquerda
				bob.setFacingLeft(true);
				bob.setState(State.WALKING);
				bob.getVelocity().x = -Personagem.SPEED;

			}
		}
		if (keys.get(Keys.RIGHT)) {
			// if(verifica(8)==false){
			// Verifica se o personagem pode andar
			if (bob.getPosition().x + bob.getBounds().getWidth() > (world
					.getNumBlocosH() - 0.01f)) {
				bob.getVelocity().x = 0.0f;
			} else {
				// O personagem esta olhando para a direita
				bob.setFacingLeft(false);
				bob.setState(State.WALKING);
				bob.getVelocity().x = Personagem.SPEED;
			}
			// }
		}

		// Verifica se ambos ou nenhum dos sentidos sao presionados
		//
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT))
				|| (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			bob.setState(State.IDLE);
			// acceleration is 0 on the x
			bob.getAcceleration().x = 0;
			// horizontal speed is 0
			bob.getVelocity().x = 0;
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN))
				|| (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
			bob.setState(State.IDLE);
			// acceleration is 0 on the y
			bob.getAcceleration().y = 0;
			// Vertival speed is 0
			bob.getVelocity().y = 0;
		}

	}
	
	public boolean permitido() {
		bob = world.getPersonagem();
		Vector2 vector2 = bob.getPosition();
		boolean res = true;
		
		if(flagCanWalk==true){
			posAuxBob = new Vector2(bob.getPosition());
			flagCanWalk=false;
		}
			
		//Verifica se o personagem esta olhando para a esquerda
		//Se ele estiver olhando para a esquerda printo e valor normal 
		//Caso contrario ele esta olhando para a direita, logo somo a base dela com a sua posi��o
		if (bob.isFacingLeft()) {
			//Analisa a posicao inicial com a posicao atual
			//Verifica se o personagem entrou no bloco
			if( (int)vector2.x != (int)posAuxBob.x ){
				Place a = world.getBlock((int)vector2.x, (int)vector2.y).getPlace();
				Place b = world.getBlock((int)posAuxBob.x, (int)posAuxBob.x).getPlace();
				if (world.getElementosCPN().dispararTransicao(a, b)) {
					posAuxBob.x=vector2.x;
					return true;
				} else {
					vector2.x = posAuxBob.x;
					return false;
				}
				//Recebe uma nova posicao inicial
			}
		} else {
			//Analisa a posicao inicial com a posicao atual
			//Verifica se o personagem entrou no bloco
			if((int)(vector2.x+bob.getBounds().getWidth())!=(int)posAuxBob.x){
				Place a = world.getBlock((int)(vector2.x+bob.getBounds().getWidth()), (int)vector2.y).getPlace();
				Place b = world.getBlock((int)posAuxBob.x, (int)posAuxBob.x).getPlace();
				if (world.getElementosCPN().dispararTransicao(a, b)) {
					posAuxBob.x=vector2.x+bob.getBounds().getWidth();
					return true;
				} else {
//					posAuxBob.x=vector2.x+bob.getBounds().getWidth();
					return false;
				}//Recebe uma nova posicao inicial
			}
		}	
			
		//Verifica se o personagem esta olhando para a baixo
			//Se ele estiver olhando para a baixo printo e valor normal 
			//Caso contrario ele esta olhando para a cima, logo somo a altura dela com a sua posi��o na vertical
			if (bob.isFacingLeft()) {
				//Analisa a posicao inicial com a posicao atua
				//Verifica se o personagem entrou no bloco
				if((int)vector2.y!=(int)posAuxBob.y){
					Place a = world.getBlock((int)(vector2.x+bob.getBounds().getWidth()), (int)vector2.y).getPlace();
					Place b = world.getBlock((int)posAuxBob.x, (int)posAuxBob.x).getPlace();
					if (world.getElementosCPN().dispararTransicao(a, b)) {
						posAuxBob.x=vector2.x+bob.getBounds().getWidth();
						return true;
					} else {
//						posAuxBob.x=vector2.x+bob.getBounds().getWidth();
						return false;
					}//Recebe uma nova posicao inicial
					//Recebe uma nova posicao inicial
					posAuxBob.y=vector2.y;
				}
				
			}else {
				//Analisa a posicao inicial com a posicao atual
				//Verifica se o personagem entrou no bloco
				if((int)(vector2.y+bob.getBounds().getHeight())!=(int)posAuxBob.y){
					System.out.println("Entrou no bloco");
					//Recebe uma nova posicao inicial
					posAuxBob.y=vector2.y+bob.getBounds().getHeight();
				}
			}
			return res;
	}
}
