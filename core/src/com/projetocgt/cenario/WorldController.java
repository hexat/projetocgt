package com.projetocgt.cenario;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import cgt.CGTGameWorld;
import cgt.core.CGTActor;
import cgt.core.CGTAddOn;
import cgt.core.CGTProjectile;
import cgt.policy.*;
import cgt.unit.Action;
import cgt.util.AnimationHandle;

/**
 * Controla os movimentos do mundo e dos personagens
 * @author roberto.bruno007@gmail.com
 *
 */
public class WorldController {

	private boolean ammoCheck=true;
	//Possiveis movimentos do personagem

	private CGTGameWorld world;
	private CGTActor personagem;
	private WorldRenderer renderer;
	static Map<ActionMovePolicy, Boolean> keys = new HashMap<ActionMovePolicy, Boolean>();
	static {
		keys.put(ActionMovePolicy.WALK_RIGHT, false);
		keys.put(ActionMovePolicy.WALK_LEFT, false);

		keys.put(ActionMovePolicy.WALK_UP, false);
		keys.put(ActionMovePolicy.WALK_DOWN, false);

		keys.put(ActionMovePolicy.JUMP, false);
		keys.put(ActionMovePolicy.FIRE, false);
		
	};

	// Este construtor recebe o mundo como parametro
	public WorldController(CGTGameWorld world,WorldRenderer render) {
		this.world = world;
		this.renderer = render;
		// Posicao inicial do personagem
		this.personagem = world.getActor();

		releaseAllDirectionKeys();
	}

	public void activateKey(InputPolicy policy){
		Action action = world.getActionFromInput(policy);
		if (action != null){
			keys.put(action.getActionPolicy(),true);
			if (action.getActionPolicy() == ActionMovePolicy.WALK_RIGHT) {
				renderer.cameraCloseOnActor();
			}
		}
	}
	
	public void deactivateKey(InputPolicy policy){
		Action action = world.getActionFromInput(policy);
		if (action != null){
			keys.put(action.getActionPolicy(),false);
			stopAni();
		}
	}
	

	private void stopAni() {
		if(personagem.getState().equals(StatePolicy.LOOKUP)) {
			personagem.setState(StatePolicy.IDLEUP);
		} else if(personagem.getState().equals(StatePolicy.LOOKDOWN)) {
			personagem.setState(StatePolicy.IDLEDOWN);
		} else if(personagem.getState().equals(StatePolicy.LOOKLEFT)) {
			personagem.setState(StatePolicy.IDLELEFT);
		} else if(personagem.getState().equals(StatePolicy.LOOKRIGHT)) {
			personagem.setState(StatePolicy.IDLERIGHT);
		}
	}

	public void fire(){
		if(keys.get(ActionMovePolicy.FIRE)){
				world.getActor().setFireDefault(0);

				if (world.getActor().getProjectiles().get(0).getAmmo() > 0){
					ammoDowner(world.getActor().getProjectiles().get(0));
				}
		}
		else if(ammoCheck)
			world.getActor().setFireDefault(-1);
	}
	
	public void ammoDowner(final CGTProjectile projectile){
		if(ammoCheck && personagem.getProjectiles().get(0).getAmmo()>0){
			ammoCheck=false;
			Timer.schedule(new Task() {
				@Override
				public void run() {
					projectile.ammoDown();
					ammoCheck=true;
					world.getActor().setFireDefault(-1);
				}
			}, projectile.getInterval());
		}
	}

	private void releaseAllDirectionKeys() {
		keys.put(ActionMovePolicy.WALK_RIGHT, false);
		keys.put(ActionMovePolicy.WALK_LEFT, false);
		keys.put(ActionMovePolicy.WALK_UP, false);
		keys.put(ActionMovePolicy.WALK_DOWN, false);
	}


	/***
	 * O metodo update e chamado a cada ciclo do loop principal.
	 * @param delta
	 */
	public void update(float delta) {
		// Processa a entrada de algum parametro
		if(!personagem.isInputsEnabled()){
			processInput();	
		}else{
			personagem.getVelocity().y = 0;
			personagem.getVelocity().x = 0;
			releaseAllDirectionKeys();
		}

		personagem.update(delta);

		for (int i=0; i<world.getOpposites().size(); i++) {
			world.getOpposites().get(i).update(delta);
		}

		for (int i=0; i<world.getBonus().size(); i++) {
			world.getBonus().get(i).update(delta);
		}

		for (int i=0; i<world.getActor().getProjectiles().size(); i++) {
			world.getActor().getProjectiles().get(i).update(delta);
		}

		for (int i=0; i<world.getEnemies().size(); i++) {
			world.getEnemies().get(i).update(delta);
			if (world.getEnemies().get(i).getCollideAnimation() != null) {
				world.getEnemies().get(i).getCollideAnimation().update(delta);
			}
		}
		for (int i=0; i<renderer.getAddOns().size(); i++) {
			renderer.getAddOns().get(i).update(delta);
		}
	}

	private void moveKeys(){
		if (keys.get(ActionMovePolicy.WALK_UP)) {
			//Verifica se o personagem pode andar
			personagem.setState(StatePolicy.LOOKUP);
			if( (personagem.getPosition().y + personagem.getBounds().height) < renderer.getWorld().getBackground().getTextureGDX().getHeight())
				personagem.getVelocity().y = personagem.getSpeed();
			
			else{
				personagem.getVelocity().y = 0;
			}
		}

		if (keys.get(ActionMovePolicy.WALK_DOWN)) {
			// O personagem esta olhando para a baixo
			personagem.setState(StatePolicy.LOOKDOWN);
			if(personagem.getPosition().y > 0)
				personagem.getVelocity().y = -personagem.getSpeed();
			else
				personagem.getVelocity().y = 0;
			}

		if (keys.get(ActionMovePolicy.WALK_LEFT)) {
			personagem.setState(StatePolicy.LOOKLEFT);
			if(personagem.getPosition().x > 0){
				personagem.getVelocity().x = -personagem.getSpeed();
			}
			else
				personagem.getVelocity().x = 0;
				
		}
			
		if (keys.get(ActionMovePolicy.WALK_RIGHT)) {
			personagem.setState(StatePolicy.LOOKRIGHT);
			if( (personagem.getPosition().x+personagem.getBounds().width) < renderer.getWorld().getBackground().getTextureGDX().getWidth()){
				personagem.getVelocity().x = personagem.getSpeed();
			}
			else
				personagem.getVelocity().x = 0;			
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(ActionMovePolicy.WALK_LEFT) && keys.get(ActionMovePolicy.WALK_RIGHT))
		   ||(!keys.get(ActionMovePolicy.WALK_LEFT) && !(keys.get(ActionMovePolicy.WALK_RIGHT)))) {
			personagem.getVelocity().x = 0;
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(ActionMovePolicy.WALK_UP) && keys.get(ActionMovePolicy.WALK_DOWN))
				|| (!keys.get(ActionMovePolicy.WALK_UP) && !(keys.get(ActionMovePolicy.WALK_DOWN)))) {
			//personagem.setState(State.IDLE);
			// acceleration is 0 on the y
			//personagem.getAcceleration().y = 0;
			// Vertival speed is 0
			personagem.getVelocity().y = 0;
		}
	}
	private void processInput() {
		moveKeys();
		fire();
	}
}
