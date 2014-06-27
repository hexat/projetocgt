package com.projetocgt.cenario;

import java.util.HashMap;
import java.util.Map;

import cgt.policy.StatePolicy;
import com.projetocgt.personagens.ActorCGT;
import com.projetocgt.personagens.ActorCGT.DirectionPolicy;
import com.projetocgt.personagens.SpriteSheet;

/**
 * Controla os movimentos do mundo e dos personagens
 * @author roberto.bruno007@gmail.com
 *
 */
public class WorldController {

	//Possiveis movimentos do personagem
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE, UP, DOWN
	};

	private MyWorld world;
	private ActorCGT bob;
	private SpriteSheet actorAnimation;
	private WorldRenderer renderer;
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
	public WorldController(MyWorld world,WorldRenderer render) {
		this.world = world;
		this.renderer = render;
		// Posicao inicial do personagem
		this.bob = world.getPersonagem();
		this.actorAnimation = world.getPersonagem().getSpriteSheet();
	}

	// Funciona na descida do botao
	public void leftPressed() {
		if (bob.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		}
		keys.get(keys.put(Keys.LEFT, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void rightPressed() {
		if (bob.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		}
		keys.get(keys.put(Keys.RIGHT, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void upPressed() {
		if (bob.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		}
		keys.get(keys.put(Keys.UP, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	private void releaseAllDirectionKeys() {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	}

	public void downPressed() {
		if (bob.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		}
		keys.get(keys.put(Keys.DOWN, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
		//Habilita o loop da animacao
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
		for(int i=0;i<world.getListaDeProjectili().size();i++){
			//if(world.getListaDeProjectili().get(i).getActionFire().getInputs().get(0) == InputPolicy.GO_TAP){
				world.getListaDeProjectili().get(i).setPosition(bob.getPosition());
				world.getListaDeProjectili().get(i).setFlagAtivar(true);	
			//}
		}
		
	}

	// Funciona na subida do botao
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
		//Desabilita o loop da animacao
		actorAnimation.setLoop(false);
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
		//Desabilita o loop da animacao
		actorAnimation.setLoop(false);
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));	
		//Desabilita o loop da animacao
		actorAnimation.setLoop(false);
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
		//Desabilita o loop da animacao
		actorAnimation.setLoop(false);
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		//Desabilita o loop da animacao
		actorAnimation.setLoop(false);
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
		//Desabilita o loop da animacao
		for(int i=0;i<world.getListaDeProjectili().size();i++){
			//so vai pra falso quem tiver ativo
			if(world.getListaDeProjectili().get(i).isFlagAtivar()){
				world.getListaDeProjectili().get(i).ammoDown();
				world.getListaDeProjectili().get(i).setFlagAtivar(false);
				//if(world.getListaDeProjectili().get(i).getAmmo() == 0){
					//world.getListaDeProjectili().get(i).setFlagAtivar(false);}
					
			}
		}
	}


	/***
	 * O metodo update e chamado a cada ciclo do loop principal.
	 * @param delta
	 */
	public void update(float delta) {
		// Processa a entrada de algum parametro
		processInput();
		bob.update(delta);
		for (int i=0; i<world.getListaDeOpposite().size(); i++) {
			world.getListaDeOpposite().get(i).update(delta);
		}
		for (int i=0; i<world.getListaDeBonus().size(); i++) {
			world.getListaDeBonus().get(i).update(delta);
		}
		for (int i=0; i<world.getListaDeProjectili().size(); i++) {
			world.getListaDeProjectili().get(i).update(delta);
		}
	}
	
	private void processInput() {
		//movimento();
		if (keys.get(Keys.UP)) {
			// Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//bob.setPosition(renderer.getPosAnterior());
				bob.setState(StatePolicy.LOOKUP);
			} else {
				// O personagem esta olhando para a cima
				if(bob.getVelocity().y!=0)
					renderer.getCam().position.y+=bob.getSpeed()/60;
				bob.setState(StatePolicy.LOOKUP);
				bob.getVelocity().y = bob.getSpeed();
			}
			
		}

		if (keys.get(Keys.DOWN)) {
			// Verifica se o personagem pode andar
			if (renderer.isColision() ) {
				//bob.setPosition(renderer.getPosAnterior());
				bob.setState(StatePolicy.LOOKDOWN);
			} else {
				if (bob.getVelocity().y!=0) {
					renderer.getCam().position.y-=bob.getSpeed()/60;
				}
				
				// O personagem esta olhando para a baixo
				bob.setState(StatePolicy.LOOKDOWN);
				bob.getVelocity().y = -bob.getSpeed();
			}
			
		}

		if (keys.get(Keys.LEFT)) {
			// Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//bob.setPosition(renderer.getPosAnterior());
				bob.setState(StatePolicy.LOOKLEFT);
			} else {
				if (bob.getVelocity().x != 0) {
					renderer.getCam().position.x-=bob.getSpeed()/60;
				}
				bob.setState(StatePolicy.LOOKLEFT);
				bob.getVelocity().x = -bob.getSpeed();;
			}		
		}
		if (keys.get(Keys.RIGHT)) {
			// Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//bob.setPosition(renderer.getPosAnterior());
				bob.setState(StatePolicy.LOOKRIGHT);
			} else {
				if (bob.getVelocity().x!=0) {
					renderer.getCam().position.x+=bob.getSpeed()/60;
				}
				bob.setState(StatePolicy.LOOKRIGHT);
				bob.getVelocity().x = bob.getSpeed();;
			}	
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT))
				|| (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			//bob.setState(State.IDLE);
			// acceleration is 0 on the x
			// horizontal speed is 0
			bob.getVelocity().x = 0;
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN))
				|| (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
			//bob.setState(State.IDLE);
			// acceleration is 0 on the y
			//bob.getAcceleration().y = 0;
			// Vertival speed is 0
			bob.getVelocity().y = 0;
		}
	}

}
