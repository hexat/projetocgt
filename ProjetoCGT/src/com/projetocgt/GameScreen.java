package com.projetocgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
	private Personagem bob;
	private ElementosCPN elementosCPN;
	
	//Vetor que sera utilizado para armazenar a posicao do bob. 
	private Vector2 vetorPosi;
	private Vector2 vetor;
	private int width, height;
	private Vector2 pos;
	
	public GameScreen() {
		super();

		Texture.setEnforcePotImages(false);//desabilita a opcao de potencia de dois.
		elementosCPN = new ElementosCPN(Gdx.files.internal("data/game.cpn").read());
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
		System.out.println("Teste");
		world = new World(elementosCPN);
		renderer = new WorldRenderer(world, true);
		controller = new WorldController(world);
		bob=new Personagem();
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
	System.out.println("down");
		if (x < width / 2 && y > height / 2) {
			//controller.leftReleased();
			
		}
		if (x > width / 2 && y > height / 2) {
			//controller.rightReleased();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		System.out.println("up");
		if (x < width / 2 && y > height / 2) {
			//controller.leftReleased();
		}
		if (x > width / 2 && y > height / 2) {
			//controller.rightReleased();
		}
		return true;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		System.out.println("dragged");
		float a=x/(width/10);
		float b=y/(height/7);
		controller.movimeto(a, b);
		
		if (x < width / 2 && y > height / 2) {
			//controller.leftPressed();
			//bob.setPosition2(getPosX());
		}
		if (x > width / 2 && y > height / 2) {
			//controller.rightPressed();	
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
