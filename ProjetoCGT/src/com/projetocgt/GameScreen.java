package com.projetocgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.cenario.World;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;
import com.projetocgt.personagens.Personagem;

public class GameScreen implements Screen, InputProcessor{
	
	private World world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private Personagem bob;
	
	//Vetor que sera utilizado para armazenar a posicao do bob. 
	private Vector2 vetorPosi;
	private int width, height;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		renderer.render();
		
		
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;

		
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, true);
		controller = new WorldController(world);
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

	//Funciona na subida do bot�o 
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
		
		//bob.getPosition().x=(float)Gdx.input.getX();
		//bob.getPosition().y=(float)Gdx.input.getY();
		//controller.downPressed();
		if (x < width / 2 && y > height / 2) {
			controller.leftPressed();
			controller.upPressed();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightPressed();
			controller.upPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (x < width / 2 && y > height / 2) {
			controller.leftReleased();
			controller.downPressed();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightReleased();
			controller.downPressed();
		}
		return true;
	}
	
	public boolean touchRight(int x, int y, int pointer, int button) {
		//if (x < width / 2 && y > height / 2) {
			//controller.upPressed();
		//}
		//if (x > width / 2 && y > height / 2) {
			//controller.downPressed();
		//}
		return true;
	}

	public boolean touchLeft(int x, int y, int pointer, int button) {
		/*if (x < width / 2 && y > height / 2) {
			controller.upReleased();
		}
		if (x > width / 2 && y > height / 2) {
			controller.downReleased();
		}*/
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
