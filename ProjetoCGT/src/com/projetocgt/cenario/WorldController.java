package com.projetocgt.cenario;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.projetocgt.core.petri.entity.Place;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.Personagem.State;
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
	private Personagem bob;
	private Personagem opositor;
	private Personagem opositor2;
	
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
	public WorldController(MyWorld world) {
		this.world = world;
		// Posicao inicial do personagem
		this.bob = world.getPersonagem();
		this.opositor = world.getOpositor();
		this.opositor2 = world.getOpositor2();
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
		//bob.setState(State.IDLE);
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
		//bob.setState(State.IDLE);
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
		opositor.update(delta);
		opositor2.update(delta);
	}
	
	public void movimeto(float x, float y) {
		//if (permitido(x, y)) {
			//bob.setState(State.WALKING);
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
		//}
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
	public void movimento(){
		//Controler do carro
		if(opositor.getPosition().y + opositor.getBounds().height > 0.0f) {
			opositor.getVelocity().y = -Personagem.SPEED;
			opositor2.getVelocity().y = -Personagem.SPEED;
		}else{
			opositor.getPosition().y = world.getNumBlocosV() + 0.5f;
			opositor2.getPosition().y = world.getNumBlocosV() + 0.2f;
		}
	}
	
	private void processInput() {
		movimento();
		if (keys.get(Keys.UP)) {
			// Verifica se o personagem pode andar
			if (bob.getPosition().y + bob.getBounds().height > (world.getNumBlocosV() - 0.01f)) {
				bob.getVelocity().y = 0.0f;
			} else {
				// O personagem esta olhando para a cima
				bob.setFacingLeft(false);
				bob.setState(State.LOOKUP);
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
				bob.setState(State.LOOKDOWN);
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
				bob.setState(State.LOOKLEFT);
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
				bob.setState(State.LOOKRIGHT);
				bob.getVelocity().x = Personagem.SPEED;
			}
			// }
		}

		// Verifica se ambos ou nenhum dos sentidos sao presionados
		//
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT))
				|| (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			//bob.setState(State.IDLE);
			// acceleration is 0 on the x
			bob.getAcceleration().x = 0;
			// horizontal speed is 0
			bob.getVelocity().x = 0;
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN))
				|| (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
			//bob.setState(State.IDLE);
			// acceleration is 0 on the y
			bob.getAcceleration().y = 0;
			// Vertival speed is 0
			bob.getVelocity().y = 0;
		}

	}
	

	/**
	 * Verifica se o sprite pode ou nao passar para outro block(lugar).
	 * @param otherX nova posicao X
	 * @param otherY nova posicao Y
	 * @return true se e' permitido passar. false caso contrario
	 */
	public boolean permitido(float otherX, float otherY) {
		bob = world.getPersonagem();
		int bobPositionX = (int)bob.getPosition().x;
		int bobPositionY = (int)bob.getPosition().y;
		boolean res = true;

		Place placeA = null, placeB = null;
		if ((int)otherX < bobPositionX) {
			placeA = world.getBlock(bobPositionX, bobPositionY).getPlace();
			placeB = world.getBlock((int)otherX, (int)otherY).getPlace();
		} else if ((int)(otherX+bob.getBounds().getWidth()) > (int)(bob.getPosition().x+bob.getBounds().getWidth())) {
			placeA = world.getBlock(bobPositionX, bobPositionY).getPlace();
			placeB = world.getBlock((int)(otherX+bob.getBounds().getWidth()), bobPositionY).getPlace();
		} else if ((int)otherY < bobPositionY) {
			placeA = world.getBlock(bobPositionX, bobPositionY).getPlace();
			placeB = world.getBlock((int)otherY, bobPositionY).getPlace();
		} else if ((int)(otherY+bob.getBounds().getHeight()) > (int)(bob.getPosition().y+bob.getBounds().getHeight())) {
			placeA = world.getBlock(bobPositionX, bobPositionY).getPlace();
			placeB = world.getBlock(bobPositionX, (int)(otherY+bob.getBounds().getHeight())).getPlace();
		}
		if (placeA != null && placeB != null) {
			res = world.getElementosCPN().dispararTransicao(placeA, placeB);
		}
		return res;
	}
}
