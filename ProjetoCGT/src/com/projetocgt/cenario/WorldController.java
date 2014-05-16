package com.projetocgt.cenario;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.projetocgt.core.petri.entity.Place;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.Personagem.State;
import com.projetocgt.personagens.SpritePersonagem;
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
	private SpritePersonagem spriteAnimacao;
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
		this.opositor = world.getOpositor();
		this.spriteAnimacao = world.getSprite();
	}

	@SuppressWarnings("unused")
	private SpritePersonagem SpritePersonagem() {
		// TODO Auto-generated method stub
		return null;
	}

	// ** Key presses and touches **************** //
	// Funciona na descida do botao
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
		//Habilita o loop da animacao
		spriteAnimacao.setLoop(true);
		//renderer.getCam().position.x-=30;
		//renderer.getCam().translate(-30f,0,0);
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
		//Habilita o loop da animacao
		spriteAnimacao.setLoop(true);
		//renderer.getCam().position.x+=30;
		//renderer.getCam().translate(30f,0,0);
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
		//Habilita o loop da animacao
		spriteAnimacao.setLoop(true);
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
		//Habilita o loop da animacao
		spriteAnimacao.setLoop(true);
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
		//Habilita o loop da animacao
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, false));
		//Habilita o loop da animacao
		spriteAnimacao.setLoop(true);
	}

	// Funciona na subida do botao
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
		//Desabilita o loop da animacao
		spriteAnimacao.setLoop(false);
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
		//Desabilita o loop da animacao
		spriteAnimacao.setLoop(false);
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));	
		//Desabilita o loop da animacao
		spriteAnimacao.setLoop(false);
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
		//Desabilita o loop da animacao
		spriteAnimacao.setLoop(false);
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		//Desabilita o loop da animacao
		spriteAnimacao.setLoop(false);
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
		//Desabilita o loop da animacao
		spriteAnimacao.setLoop(false);
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
		//opositor2.update(delta);
	}
	
	public void movimeto(float x, float y) {
//		if (permitido(x, y)) {
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
//		}
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
	
	//
	public void movimento(){
		//Controler do carro
		if(opositor.getPosition().y + opositor.getBounds().height > 0.0f) {
			opositor.getVelocity().y = -Personagem.SPEED;
		}else{
			opositor.getPosition().y = world.getNumBlocosV() + 0.5f;
		}
	}
	
	private void processInput() {
		//movimento();
		if (keys.get(Keys.UP)) {
			// Verifica se o personagem pode andar
			if (renderer.isCol()) {
				bob.getVelocity().y = 0.0f;
				bob.setState(State.LOOKUP);
			} else {
				// O personagem esta olhando para a cima
				if(bob.getVelocity().y!=0)
					renderer.getCam().position.y+=3;
				bob.setState(State.LOOKUP);
				bob.getVelocity().y = Personagem.SPEED;
			}
			
		}

		if (keys.get(Keys.DOWN)) {
			// Verifica se o personagem pode andar
			if (renderer.isCol() ) {
				bob.getVelocity().y = 0.0f;
				bob.setState(State.LOOKDOWN);
			} else {
				if(bob.getVelocity().y!=0)
					renderer.getCam().position.y-=3;
				// O personagem esta olhando para a baixo
				bob.setState(State.LOOKDOWN);
				bob.getVelocity().y = -Personagem.SPEED;
			}
			
		}

		if (keys.get(Keys.LEFT)) {
			// Verifica se o personagem pode andar
			if (renderer.isCol()) {
				bob.getVelocity().x = 0.0f;
				bob.setState(State.LOOKLEFT);
			} else {
				// O personagem esta olhando para a esquerda
				//bob.setColidiu(true);
				//bob.setFacingLeft(true);
				if(bob.getVelocity().x!=0)
					renderer.getCam().position.x-=3;
				bob.setState(State.LOOKLEFT);
				bob.getVelocity().x = -Personagem.SPEED;
			}		
		}
		if (keys.get(Keys.RIGHT)) {
			// Verifica se o personagem pode andar
			if (renderer.isCol()) {
				bob.getVelocity().x = 0.0f;
				bob.setState(State.LOOKRIGHT);
			} else {
				// O personagem esta olhando para a direita
				//bob.setColidiu(true);
				//bob.setFacingLeft(false);
				if(bob.getVelocity().x!=0)
					renderer.getCam().position.x+=3;
				bob.setState(State.LOOKRIGHT);
				bob.getVelocity().x = Personagem.SPEED;
			}	
		}
		// Verifica se ambos ou nenhum dos sentidos sao presionados
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
			//bob.getAcceleration().y = 0;
			// Vertival speed is 0
			bob.getVelocity().y = 0;
		}
	}

}
