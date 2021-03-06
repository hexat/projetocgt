package com.projetocgt.cenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import cgt.game.CGTGameWorld;
import cgt.core.CGTActor;
import cgt.core.CGTProjectile;
import cgt.policy.*;
import cgt.unit.Action;

/**
 * Controla os movimentos do mundo e dos personagens
 * @author roberto.bruno007@gmail.com
 *
 */
public class WorldController {

	private CGTGameWorld world;
	private CGTActor actor;
	private WorldRenderer renderer;
	static Map<ActionMovePolicy, Boolean> keys = new HashMap<ActionMovePolicy, Boolean>();
	static {
        keys.put(ActionMovePolicy.WALK_RIGHT, false);
        keys.put(ActionMovePolicy.WALK_LEFT, false);

        keys.put(ActionMovePolicy.WALK_UP, false);
        keys.put(ActionMovePolicy.WALK_DOWN, false);
//        keys.put(ActionMovePolicy.RUN_RIGHT, false);
//        keys.put(ActionMovePolicy.RUN_LEFT, false);
//
//        keys.put(ActionMovePolicy.RUN_UP, false);
//        keys.put(ActionMovePolicy.RUN_DOWN, false);

		keys.put(ActionMovePolicy.FIRE, false);
	};
	private ArrayList<ActionMovePolicy> actions;

	// Este construtor recebe o mundo como parametro
	public WorldController(CGTGameWorld world,WorldRenderer render) {
		this.world = world;
		this.renderer = render;
		// Posicao inicial do actor
		this.actor = world.getActor();

		releaseAllDirectionKeys();
		actions = new ArrayList<ActionMovePolicy>();
	}

	public void activateKey(InputPolicy policy){
		Action action = actor.getActionFromInput(policy);
		if (action != null){
            // colocado para conseguir desativar o tap
			if(policy == InputPolicy.TAP) actions.add(action.getActionPolicy());
			keys.put(action.getActionPolicy(),true);
		}
	}
	
	public void deactivateKey(InputPolicy policy){
        Action action = actor.getActionFromInput(policy);
		if (action != null){
//            if (!(action.getActionPolicy() == ActionMovePolicy.RUN_DOWN
//                    || action.getActionPolicy() == ActionMovePolicy.RUN_LEFT
//                    || action.getActionPolicy() == ActionMovePolicy.RUN_RIGHT
//                    || action.getActionPolicy() == ActionMovePolicy.RUN_UP)) {
                keys.put(action.getActionPolicy(), false);
                stopAni();
//            }
		}
	}

	private void stopAni() {
		if(actor.getState().equals(StatePolicy.LOOKUP)) {
			actor.setState(StatePolicy.IDLEUP);
		} else if(actor.getState().equals(StatePolicy.LOOKDOWN)) {
			actor.setState(StatePolicy.IDLEDOWN);
		} else if(actor.getState().equals(StatePolicy.LOOKLEFT)) {
			actor.setState(StatePolicy.IDLELEFT);
		} else if(actor.getState().equals(StatePolicy.LOOKRIGHT)) {
			actor.setState(StatePolicy.IDLERIGHT);
		}
	}

	public void fire(){
        if(keys.get(ActionMovePolicy.FIRE)
                && world.getActor().getProjectileDefault() != null
                && !world.getActor().isFireActivate()
                && world.getActor().getProjectileDefault().hasOrientation(actor.getState())) {
            world.getActor().setFireActivate(true);
            if (world.getActor().getProjectileDefault().getAmmo() > 0){
                ammoDowner(world.getActor().getProjectileDefault());
            }
		}
	}
	
	public void ammoDowner(final CGTProjectile projectile){
        if (projectile.getInterval() > 0) {
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    projectile.ammoDown();
                    world.getActor().setFireActivate(false);
                    world.getActor().getProjectileDefault().setStateTime(0);
                }
            }, projectile.getInterval());
        }
	}

	public void releaseAllDirectionKeys() {
		keys.put(ActionMovePolicy.WALK_RIGHT, false);
		keys.put(ActionMovePolicy.WALK_LEFT, false);
		keys.put(ActionMovePolicy.WALK_UP, false);
		keys.put(ActionMovePolicy.WALK_DOWN, false);

        activateKey(InputPolicy.DEFAULT);
	}
	
	public void releaseSlices(){
		deactivateKey(InputPolicy.SLIDE_DOWN);
		deactivateKey(InputPolicy.SLIDE_UP);
		deactivateKey(InputPolicy.SLIDE_LEFT);
		deactivateKey(InputPolicy.SLIDE_RIGHT);
	}
	
	public void releaseAccelerometer(){
		deactivateKey(InputPolicy.ACEL_DOWN);
		deactivateKey(InputPolicy.ACEL_UP);
		deactivateKey(InputPolicy.ACEL_LEFT);
		deactivateKey(InputPolicy.ACEL_RIGHT);
	}


	/***
	 * O metodo update e chamado a cada ciclo do loop principal.
	 * @param delta
	 */
	public void update(float delta) {
		// Processa a entrada de algum parametro
		if(actor.isInputsEnabled()){
            moveKeys();
            fire();
            verifyActions();
		}else{
			actor.getVelocity().y = 0;
			actor.getVelocity().x = 0;
			releaseAllDirectionKeys();
		}

        actor.update(delta);

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
	}

	private void moveKeys(){
        if (keys.get(ActionMovePolicy.WALK_UP)) {
            //Verifica se o actor pode andar
            actor.setState(StatePolicy.LOOKUP);
            if( (actor.getPosition().y + actor.getBounds().height) < renderer.getWorld().getBackground().getTextureGDX().getHeight())
                actor.getVelocity().y = actor.getSpeed();

            else{
                actor.getVelocity().y = 0;
            }
        }

        if (keys.get(ActionMovePolicy.WALK_DOWN)) {
            // O actor esta olhando para a baixo
            actor.setState(StatePolicy.LOOKDOWN);
            if(actor.getPosition().y > 0)
                actor.getVelocity().y = -actor.getSpeed();
            else
                actor.getVelocity().y = 0;
        }

        if (keys.get(ActionMovePolicy.WALK_LEFT)) {
            actor.setState(StatePolicy.LOOKLEFT);
            if(actor.getPosition().x > 0){
                actor.getVelocity().x = -actor.getSpeed();
            }
            else
                actor.getVelocity().x = 0;

        }

        if (keys.get(ActionMovePolicy.WALK_RIGHT)) {
            actor.setState(StatePolicy.LOOKRIGHT);
            if ( (actor.getPosition().x+ actor.getBounds().width) < renderer.getWorld().getBackground().getTextureGDX().getWidth()) {
                actor.getVelocity().x = actor.getSpeed();
            }
            else
                actor.getVelocity().x = 0;
        }
        // run
//        if (keys.get(ActionMovePolicy.RUN_UP)) {
//            //Verifica se o actor pode andar
//            actor.setState(StatePolicy.LOOKUP);
//            if ( (actor.getPosition().y + actor.getBounds().height) < renderer.getWorld().getBackground().getTextureGDX().getHeight())
//                actor.getVelocity().y = actor.getSpeed();
//
//            else{
//                actor.getVelocity().y = 0;
//            }
//            return;
//        }
//
//        if (keys.get(ActionMovePolicy.RUN_DOWN)) {
//            // O actor esta olhando para a baixo
//            actor.setState(StatePolicy.LOOKDOWN);
//            if(actor.getPosition().y > 0)
//                actor.getVelocity().y = -actor.getSpeed();
//            else
//                actor.getVelocity().y = 0;
//            return;
//        }
//
//        if (keys.get(ActionMovePolicy.RUN_LEFT)) {
//            actor.setState(StatePolicy.LOOKLEFT);
//            if(actor.getPosition().x > 0){
//                actor.getVelocity().x = -actor.getSpeed();
//            }
//            else
//                actor.getVelocity().x = 0;
//            return;
//
//        }
//
//        if (keys.get(ActionMovePolicy.RUN_RIGHT)) {
//            actor.setState(StatePolicy.LOOKRIGHT);
//            if( (actor.getPosition().x+ actor.getBounds().width) < renderer.getWorld().getBackground().getTextureGDX().getWidth()){
//                actor.getVelocity().x = actor.getSpeed();
//            }
//            else
//                actor.getVelocity().x = 0;
//            return;
//        }
        //

		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(ActionMovePolicy.WALK_LEFT) && keys.get(ActionMovePolicy.WALK_RIGHT))
		   ||(!keys.get(ActionMovePolicy.WALK_LEFT) && !(keys.get(ActionMovePolicy.WALK_RIGHT)))) {
			actor.getVelocity().x = 0;
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(ActionMovePolicy.WALK_UP) && keys.get(ActionMovePolicy.WALK_DOWN))
				|| (!keys.get(ActionMovePolicy.WALK_UP) && !(keys.get(ActionMovePolicy.WALK_DOWN)))) {
			//actor.setState(State.IDLE);
			// acceleration is 0 on the y
			//actor.getAcceleration().y = 0;
			// Vertival speed is 0
			actor.getVelocity().y = 0;
		}
	}
	
	private void verifyActions(){
		if(actions.size()>0){
			for (ActionMovePolicy key : keys.keySet()) {
				if(keys.get(key) && actions.contains(key)){
					deactivateKey(InputPolicy.TAP);
				}
			}
		}
	}
}
