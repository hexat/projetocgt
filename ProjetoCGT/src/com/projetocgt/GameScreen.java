package com.projetocgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.projetocgt.cenario.Joystick;
import com.projetocgt.cenario.Menu;
import com.projetocgt.cenario.MyWorld;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;
import com.projetocgt.core.petri.ElementosCPN;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.SpritePersonagem;

public class GameScreen extends Table implements Screen, InputProcessor {
	
	private MyWorld world;
	private World world2;
	private Box2DDebugRenderer debugoRender;
	private WorldRenderer renderer;
	private WorldController	controller;
	private ElementosCPN elementosCPN;
	private Personagem personagem;
	private Joystick setaBaixo;
	private Joystick setaDireita;
	private Joystick setaCima;
	private Joystick setaEsquerda;
	private int width, height;
	private boolean flagTouchInBob; // flag para verificar se foi tocado no bob
	private Music music;
	private SpriteBatch batch;	//Utilizada para desenhar o menu 
	private Menu spriteMenu;
	
	public GameScreen() {
		super();
		flagTouchInBob = false;
		elementosCPN = new ElementosCPN(Gdx.files.internal("data/game.cpn").read());
		
		//Carrega os audios
		music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/temabombeiro.wav"));
		new Texture(Gdx.files.internal("data/Joystick/setaDireita.png"));
		}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		controller.update(delta);
		renderer.render();
	}
	

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		//Atribui a "this.width" a largura da tela 
		this.width = width;
		//Atribui a "this.width" a altura da tela
		this.height = height;	
	}

	@Override
	public void show() {
		//Habilita a musica 
		music.play();
		music.setLooping(true);
		
		//criando um mundo com fisica
		/*world2 = new World(new Vector2(0, -9.8f),true);
		debugoRender = new Box2DDebugRenderer();
		//Definicoes do body
		BodyDef balDef = new BodyDef();
		balDef.type= BodyType.StaticBody;
		balDef.position.set(0, 0);
		//Circulo
		CircleShape shape = new CircleShape();
		shape.setRadius(1f);
		//Fixe def
		//FixtureDef fixTureDef = new FixtureDef();
		world2.createBody(balDef);
		shape.dispose();*/
		
		world = new MyWorld(elementosCPN);
		renderer = new WorldRenderer(world, true);
		controller = new WorldController(world);
		personagem = world.getPersonagem();
		//Joystick
		setaDireita = world.getJoystickDireita();
		setaBaixo = world.getJoystickBaixo();
		setaEsquerda = world.getJoystickEsquerda();
		setaCima = world.getJoystickCima();
		
		//Habilitando GDX para captura processos de entrada 
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	

	// InputProcessor methods
	//Funciona na descida do botao
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		if (keycode == Keys.UP)
			controller.upPressed();
		if (keycode == Keys.DOWN)
			controller.downPressed();
		return true;
	}

	//Funciona na subida do botao 
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftReleased();
		if (keycode == Keys.RIGHT)
			controller.rightReleased();
		if (keycode == Keys.UP)
			controller.upReleased();
		if (keycode == Keys.DOWN)
			controller.downReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		//Personagem bob = world.getPersonagem();
		float newPosX = x / (width / world.getNumBlocosH()); 
		float newPosY = world.getNumBlocosV() - y / (height / world.getNumBlocosV());
		//Verifica se tocou no personagem
		if (newPosX >= personagem.getPosition().x &&
				newPosX <= personagem.getPosition().x + personagem.getBounds().getWidth() &&
				newPosY >= personagem.getPosition().y && 
				newPosY <= personagem.getPosition().y + personagem.getBounds().getHeight()) {
			flagTouchInBob = true;
		}
		
		if (newPosX >= setaBaixo.getPosition().x &&
				newPosX <= setaBaixo.getPosition().x +  setaBaixo.getBounds().getWidth()&&
				newPosY >= setaBaixo.getPosition().y && 
				newPosY <= setaBaixo.getPosition().y + setaBaixo.getBounds().getHeight()) {
				controller.downPressed();
			//flagTouchInBob = true;
			}
		//verifica se tocou na seta   para direita
		if (newPosX >= setaDireita.getPosition().x &&
			newPosX <= setaDireita.getPosition().x +  setaDireita.getBounds().getWidth()&&
			newPosY >= setaDireita.getPosition().y && 
			newPosY <= setaDireita.getPosition().y + setaDireita.getBounds().getHeight()) {
			controller.rightPressed();
		//flagTouchInBob = true;
		}
		
		//verifica se tocou na seta   para esquerda
		if (newPosX >= setaEsquerda.getPosition().x &&
			newPosX <= setaEsquerda.getPosition().x +  setaEsquerda.getBounds().getWidth()&&
			newPosY >= setaEsquerda.getPosition().y && 
			newPosY <= setaEsquerda.getPosition().y + setaEsquerda.getBounds().getHeight()) {
			controller.leftPressed();
			//flagTouchInBob = true;
		}
		
		//verifica se tocou na seta   para direita
		if (newPosX >= setaCima.getPosition().x &&
			newPosX <= setaCima.getPosition().x +  setaCima.getBounds().getWidth()&&
			newPosY >= setaCima.getPosition().y && 
			newPosY <= setaCima.getPosition().y + setaCima.getBounds().getHeight()) {
			controller.upPressed();
			//flagTouchInBob = true;
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		controller.leftReleased();
		controller.rightReleased();
		controller.upReleased();
		controller.downReleased();
		//joystick.Acao(controller);
		flagTouchInBob = false;
		return true;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (flagTouchInBob) {
			float newPosX = x / (width / world.getNumBlocosH()) - personagem.getBounds().width/2; 
			float newPosY = world.getNumBlocosV() - y / (height / world.getNumBlocosV()) - personagem.getBounds().height/2;
			controller.movimeto(newPosX, newPosY);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
