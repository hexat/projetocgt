package com.projetocgt.cenario;

import java.util.HashMap;
import java.util.Map;

import cgt.core.CGTActor;
import cgt.policy.*;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.personagens.CGTAnimation;

/**
 * Controla os movimentos do mundo e dos personagens
 * @author roberto.bruno007@gmail.com
 *
 */
public class WorldController {

	//Possiveis movimentos do personagem
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE, UP, DOWN, DAMAGE
	};

	private MyWorld world;
	private CGTActor personagem;
	private CGTAnimation actorAnimation;
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
		this.personagem = world.getPersonagem();
		this.actorAnimation = world.getPersonagem().getAnimation();
	}

	// Funciona na descida do botao
	public void leftPressed() {
		//if (personagem.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		//}
		keys.get(keys.put(Keys.LEFT, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void rightPressed() {
		//if (personagem.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		//}
		keys.get(keys.put(Keys.RIGHT, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void upPressed() {
		//if (personagem.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		//}
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
		//if (personagem.getDirectionPolicy() == DirectionPolicy.FOUR_DIRECTION) {
			releaseAllDirectionKeys();
		//}
		keys.get(keys.put(Keys.DOWN, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
		//Habilita o loop da animacao
	}
	
	public void damegePressed() {
		keys.get(keys.put(Keys.DAMAGE, true));
		//Habilita o loop da animacao
		actorAnimation.setLoop(true);
	}

	public void firePressedTouch() {
		keys.get(keys.put(Keys.FIRE, true));
		for(int i=0;i<world.getPersonagem().getListaDeProjectiles().size();i++){
			//TODO
			//if(world.getListaDeProjectili().get(i).getActionFire().getInputs().get(0) == InputPolicy.GO_TAP){
				//world.getPersonagem().getListaDeProjectiles().get(i).setPosition(personagem.getPosition());
				
				world.getPersonagem().getListaDeProjectiles().get(i).setState(personagem.getState());
				world.getPersonagem().getListaDeProjectiles().get(i).setFlagAtivar(true);	
			//}
		}
		
	}

	// Funciona na subida do botao
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
		//Desabilita o loop da animacao
		//actorAnimation.setLoop(false);
		actorAnimation.stopAni();
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
		//Desabilita o loop da animacao
		//actorAnimation.setLoop(false);
		actorAnimation.stopAni();
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));	
		//Desabilita o loop da animacao
		//actorAnimation.setLoop(false);
		actorAnimation.stopAni();
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
		//Desabilita o loop da animacao
		//actorAnimation.setLoop(false);
		actorAnimation.stopAni();
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		//Desabilita o loop da animacao
		//actorAnimation.setLoop(false);
		actorAnimation.stopAni();
	}
	
	public void damegeReleased() {
		keys.get(keys.put(Keys.DAMAGE, false));
		//Desabilita o loop da animacao
		//actorAnimation.setLoop(false);
		actorAnimation.stopAni();
	}
	
	public void fireReleasedTouch() {
		keys.get(keys.put(Keys.FIRE, false));
		//Desabilita o loop da animacao
		for(int i=0;i<world.getPersonagem().getListaDeProjectiles().size();i++){
			//so vai pra falso quem tiver ativo
			if(world.getPersonagem().getListaDeProjectiles().get(i).isFlagAtivar()){
				world.getPersonagem().getListaDeProjectiles().get(i).ammoDown();
				world.getPersonagem().getListaDeProjectiles().get(i).setFlagAtivar(false);
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
		personagem.update(delta);
		
		for (int i=0; i<world.getListaDeOpposite().size(); i++) {
			world.getListaDeOpposite().get(i).update(delta);
		}
		
		for (int i=0; i<world.getListaDeBonus().size(); i++) {
			world.getListaDeBonus().get(i).update(delta);
		}
		
		for (int i=0; i<world.getPersonagem().getListaDeProjectiles().size(); i++) {
			world.getPersonagem().getListaDeProjectiles().get(i).update(delta);
		}
		
		for (int i=0; i<world.getListaDeEnemy().size(); i++) {
			world.getListaDeEnemy().get(i).update(delta);
		}
	}
	
	private void processInput() {
		//movimento();
		if (keys.get(Keys.UP)) {
			//Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//personagem.setPosition(renderer.getPosAnterior());
				personagem.setState(StatePolicy.LOOKUP);
				//actionDamegeEnemyUp();
			}else {
				// O personagem esta olhando para a cima
				if(personagem.getVelocity().y!=0 && personagem.getPosition().y > renderer.getCam().viewportHeight/2)	
					if( personagem.getPosition().y < renderer.getWorld().getBackGround().getHeight()-renderer.getCam().viewportHeight/2)
						renderer.getCam().position.y+=personagem.getSpeed()/60;
				
				personagem.setState(StatePolicy.LOOKUP);
				if( (personagem.getPosition().y + personagem.getBounds().height) < renderer.getWorld().getBackGround().getHeight())
					personagem.getVelocity().y = personagem.getSpeed();
				else
					personagem.getVelocity().y = 0;
			}
			
		}

		if (keys.get(Keys.DOWN)) {
			//if(renderer.isColisaoEnemy())
				//actionDamegeEnemyDown();
			// Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//personagem.setPosition(renderer.getPosAnterior());
				//personagem.setState(StatePolicy.LOOKDOWN);
			} else {
				if (personagem.getVelocity().y!=0 && personagem.getPosition().y > renderer.getCam().viewportHeight/2) {
					
					if( personagem.getPosition().y < renderer.getWorld().getBackGround().getHeight()-renderer.getCam().viewportHeight/2)
						renderer.getCam().position.y-=personagem.getSpeed()/60;
				}
				
				// O personagem esta olhando para a baixo
				personagem.setState(StatePolicy.LOOKDOWN);
				if(personagem.getPosition().y > 0)
					personagem.getVelocity().y = -personagem.getSpeed();
				else
					personagem.getVelocity().y = 0;
			}
			
		}

		if (keys.get(Keys.LEFT)) {
			// Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//personagem.setPosition(renderer.getPosAnterior());
				personagem.setState(StatePolicy.LOOKLEFT);
				//actionDamegeEnemyLeft();
			} else {
				if (personagem.getVelocity().x != 0 && personagem.getPosition().x > renderer.getCam().viewportWidth/2) {
					if( personagem.getPosition().x < renderer.getWorld().getBackGround().getWidth()-renderer.getCam().viewportWidth/2)
						renderer.getCam().position.x-=personagem.getSpeed()/60;
				}
				
				personagem.setState(StatePolicy.LOOKLEFT);
				if(personagem.getPosition().x > 0)
					personagem.getVelocity().x = -personagem.getSpeed();
				else
					personagem.getVelocity().x = 0;
			}		
		}
		if (keys.get(Keys.RIGHT)) {
			// Verifica se o personagem pode andar
			if (renderer.isColision()) {
				//personagem.setPosition(renderer.getPosAnterior());
				personagem.setState(StatePolicy.LOOKRIGHT);
				//actionDamegeEnemyRight();
			} else {
				if (personagem.getVelocity().x!=0 && personagem.getPosition().x > renderer.getCam().viewportWidth/2) {
					if( personagem.getPosition().x < renderer.getWorld().getBackGround().getWidth()-renderer.getCam().viewportWidth/2)
						renderer.getCam().position.x+=personagem.getSpeed()/60;
				}
				
				personagem.setState(StatePolicy.LOOKRIGHT);
				if( (personagem.getPosition().x+personagem.getBounds().width) < renderer.getWorld().getBackGround().getWidth())
					personagem.getVelocity().x = personagem.getSpeed();
				else
					personagem.getVelocity().x = 0;
			}	
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT))
				|| (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			//personagem.setState(State.IDLE);
			// acceleration is 0 on the x
			// horizontal speed is 0
			personagem.getVelocity().x = 0;
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN))
				|| (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
			//personagem.setState(State.IDLE);
			// acceleration is 0 on the y
			//personagem.getAcceleration().y = 0;
			// Vertival speed is 0
			personagem.getVelocity().y = 0;
		}
	}
	public void actionDamegeEnemyDown(){
		//personagem.getPosition().y+=50;
		//renderer.getCam().position.y+=50;
		/*personagem.setState(StatePolicy.DAMAGE);
		damegePressed();
		Timer.schedule(new Task(){
			@Override
			public void run() {
				damegeReleased();
				personagem.setState(StatePolicy.LOOKDOWN);
			}
		}, 2);*/
	}
	
	public void actionDamegeEnemyUp(){
		//personagem.getPosition().y-=50;
		//renderer.getCam().position.y-=50;
		//personagem.setState(StatePolicy.DAMAGE);
	}
	
	public void actionDamegeEnemyRight(){
		//personagem.getPosition().x-=50;
		//renderer.getCam().position.x-=50;
		//personagem.setState(StatePolicy.DAMAGE);
	}
	
	public void actionDamegeEnemyLeft(){
		//personagem.getPosition().x+=50;
		//renderer.getCam().position.x+=50;
		//personagem.setState(StatePolicy.DAMAGE);
	}
	

}
