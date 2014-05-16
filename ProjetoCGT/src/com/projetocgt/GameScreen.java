package com.projetocgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.projetocgt.cenario.MyWorld;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.Personagem.DirectionPolicy;

public class GameScreen extends Table implements Screen, InputProcessor {
	
	private static final boolean DEBUG = true;
	
	private MyWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private Personagem personagem;
	private int width, height;
	private boolean flagTouchInBob; // flag para verificar se foi tocado no bob
	private Music music;
	
	public GameScreen() {
		super();
		flagTouchInBob = false;
		
		//Carrega os audios
		music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/temabombeiro.wav"));
		new Texture(Gdx.files.internal("data/Joystick/setaDireita.png"));
		}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		controller.update(delta);
		renderer.render();
		//renderer.getCam().position.x-=0.005f;
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
		
		world = new MyWorld();
		renderer = new WorldRenderer(world, DEBUG);
		controller = new WorldController(world, renderer);
		personagem = world.getPersonagem();

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
		//renderer.dispose();
	}
	

	// InputProcessor methods
	//Funciona na descida do botao
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT){
			controller.leftPressed();
		}
		if (keycode == Keys.RIGHT){
			controller.rightPressed();
		}
		if (keycode == Keys.UP){
			controller.upPressed();
		}
		if (keycode == Keys.DOWN){
			controller.downPressed();
		}
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
		
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		flagTouchInBob = false;
		return true;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
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
