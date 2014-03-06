package com.projetocgt.cenario;


import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.Personagem.State;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

		//Possiveis movimentos do personagem
		enum Keys {
			LEFT, RIGHT, JUMP, FIRE
		};

		private World world;
		private Personagem 	bob;

		static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
		static {
			keys.put(Keys.LEFT, false);
			keys.put(Keys.RIGHT, false);
			keys.put(Keys.JUMP, false);
			keys.put(Keys.FIRE, false);
		};

		public WorldController(World world) {
			this.world = world;
			this.bob = world.getPersonagem();
		}

		// ** Key presses and touches **************** //

		public void leftPressed() {
			keys.get(keys.put(Keys.LEFT, true));
		}

		public void rightPressed() {
			keys.get(keys.put(Keys.RIGHT, true));
		}

		public void jumpPressed() {
			keys.get(keys.put(Keys.JUMP, true));
		}

		public void firePressed() {
			keys.get(keys.put(Keys.FIRE, false));
		}

		public void leftReleased() {
			keys.get(keys.put(Keys.LEFT, false));
		}

		public void rightReleased() {
			keys.get(keys.put(Keys.RIGHT, false));
		}

		public void jumpReleased() {
			keys.get(keys.put(Keys.JUMP, false));
		}

		public void fireReleased() {
			keys.get(keys.put(Keys.FIRE, false));
		}
		
		//Retorna aposição do personagem em forma de Vetor2
		public Vector2 posicaoBob(){
			return bob.getPosition();
		}
		
		/** The main update method **/
		public void update(float delta) {
			processInput();
			bob.update(delta);
		}

		/** Change Bob's state and parameters based on input controls **/
		private void processInput() {
			
			if (keys.get(Keys.LEFT)) {
				// left is pressed
				if(bob.getPosition().x < 6){
					bob.getVelocity().x = 0.0f;
				} else {
					bob.setFacingLeft(true);
					bob.setState(State.WALKING);
					bob.getVelocity().x = -Personagem.SPEED;
					
				}
			}
			if (keys.get(Keys.RIGHT)) {
				// left is pressed
				//
				if(bob.getPosition().x+bob.getBounds().getWidth() > 8){
					bob.getVelocity().x = 0.0f;
				} else {
					bob.setFacingLeft(false);
					bob.setState(State.WALKING);
					bob.getVelocity().x = Personagem.SPEED;
					
				}
			}
			// need to check if both or none direction are pressed, then Bob is idle
			if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
					(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
				bob.setState(State.IDLE);
				// acceleration is 0 on the x
				bob.getAcceleration().x = 0;
				// horizontal speed is 0
				bob.getVelocity().x = 0;
			}
		}
	}

