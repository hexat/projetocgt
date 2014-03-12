package com.projetocgt.cenario;


import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.Personagem.State;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

		//Possiveis movimentos do personagem
		enum Keys {
			LEFT, RIGHT, JUMP, FIRE,UP,DOWN
		};

		private World world;
		private Personagem 	bob;

		static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
		static {
			keys.put(Keys.LEFT, false);
			keys.put(Keys.RIGHT, false);
			
			keys.put(Keys.UP, false);
			keys.put(Keys.DOWN, false);
			
			keys.put(Keys.JUMP, false);
			keys.put(Keys.FIRE, false);
		};
		
		//Este construtor recebe o mundo como parametro
		public WorldController(World world) {
			this.world = world;
			//Posição inicial do personagem
			this.bob = world.getPersonagem();
		}

		// ** Key presses and touches **************** //
		//Funciona na descida do botao
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
		
		//Funciona na subida do botao
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
		
		//Retorna aposição do personagem em forma de Vetor2
		public Vector2 posicaoBob(){
			return bob.getPosition();
		}
		
		/** The main update method **/
		//O método update e chamado a cada ciclo do loop principal.
		public void update(float delta) {
			//Processa a entrada de algum parametro
			processInput();
			//Atualizações do Personagem. Personagem tem um método de atualização dedicado.
			bob.update(delta);
		}
		//Função que verifica se o personagem pode andar sobre esse bloco
		public boolean verifica(float x){
			//Base
			//Altura
			//Verifica se o personagem pode andar
			if(bob.getPosition().x+bob.getBounds().getWidth() > x){
				return true;
			} else {
				return false;
			}
		}
		/** Change Bob's state and parameters based on input controls **/
		private void processInput() {
			
			if (keys.get(Keys.UP)) {
				//Verifica se o personagem pode andar
				if(bob.getPosition().y+bob.getBounds().height > 7){
					bob.getVelocity().y = 0.0f;
				}
				else{
				//O personagem esta olhando para a cima
				bob.setFacingLeft(false);
				bob.setState(State.WALKING);
				bob.getVelocity().y = Personagem.SPEED;
				}
			}
			
			if (keys.get(Keys.DOWN)) {
				//Verifica se o personagem pode andar
				if(bob.getPosition().y < 0.0f){
					bob.getVelocity().y = 0.0f;
				}
				else{
				//O personagem esta olhando para a baixo
				bob.setFacingLeft(true);
				bob.setState(State.WALKING);
				bob.getVelocity().y = -Personagem.SPEED;
				}
			}
			
			if (keys.get(Keys.LEFT)) {
				//Verifica se o personagem pode andar 
				if(bob.getPosition().x < 0.0f){
					 bob.getVelocity().x = 0.0f;
				} else {
					//O personagem esta olhando para a esquerda
					bob.setFacingLeft(true);
					bob.setState(State.WALKING);
					bob.getVelocity().x = -Personagem.SPEED;
					
				}
			}
			if (keys.get(Keys.RIGHT)) {
				//if(verifica(8)==false){
					//Verifica se o personagem pode andar
					if(bob.getPosition().x+bob.getBounds().getWidth() > 10){
						bob.getVelocity().x = 0.0f;
					} else {
						//O personagem esta olhando para a direita
						bob.setFacingLeft(false);
						bob.setState(State.WALKING);
						bob.getVelocity().x = Personagem.SPEED;
					}
				//}
			}
			
			//Verifica se ambos ou nenhum dos sentidos são presionados
			//
			if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
					(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
				bob.setState(State.IDLE);
				// acceleration is 0 on the x
				bob.getAcceleration().x = 0;
				// horizontal speed is 0
				bob.getVelocity().x = 0;
			}
			//Verifica se ambos ou nenhum dos sentidos são presionados
			if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
					(!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
				bob.setState(State.IDLE);
				// acceleration is 0 on the y
				bob.getAcceleration().y = 0;
				//Vertival speed is 0
				bob.getVelocity().y = 0;
			}

		}
	}

