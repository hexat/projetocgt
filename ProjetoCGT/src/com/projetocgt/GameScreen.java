package com.projetocgt;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.cenario.World;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;
import com.projetocgt.core.petri.ElementosCPN;
import com.projetocgt.personagens.Personagem;

public class GameScreen implements Screen, InputProcessor{
	
	private World world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private ElementosCPN elementosCPN;
	
	//Vetor que sera utilizado para armazenar a posicao do bob. 
	private Vector2 vetorPosi;
	private Vector2 vetor;
	private int width, height;
	private Vector2 pos;
	private boolean flagTouchInBob; // flag para verificar se foi tocado no bob
	private Music music;
	private Audio audio;
	
	public GameScreen() {
		super();
		flagTouchInBob = false;
		Texture.setEnforcePotImages(false);//desabilita a opcao de potencia de dois.
		elementosCPN = new ElementosCPN(Gdx.files.internal("data/game.cpn").read());
		//Carrega os audios
		music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/temabombeiro.wav"));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//
		controller.update(delta);
		//
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
		
		music.play();
		music.setLooping(true);
		world = new World(elementosCPN);
		renderer = new WorldRenderer(world, true);
		controller = new WorldController(world);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Personagem bob = world.getPersonagem();
		float newPosX = x / (width / world.getNumBlocosH()); 
		float newPosY = world.getNumBlocosV() - y / (height / world.getNumBlocosV());
		if (newPosX >= bob.getPosition().x &&
				newPosX <= bob.getPosition().x + bob.getBounds().getWidth() &&
				newPosY >= bob.getPosition().y && 
				newPosY <= bob.getPosition().y + bob.getBounds().getHeight()) {
			flagTouchInBob = true;
		}

		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		flagTouchInBob = false;
		return true;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (flagTouchInBob) {
			Personagem bob = world.getPersonagem();
	
			float newPosX = x / (width / world.getNumBlocosH()) - bob.getBounds().width/2; 
			float newPosY = world.getNumBlocosV() - y / (height / world.getNumBlocosV()) - bob.getBounds().height/2;
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
