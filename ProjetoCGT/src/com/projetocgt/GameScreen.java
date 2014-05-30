package com.projetocgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.projetocgt.cenario.MyWorld;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	
	private static final boolean DEBUG = true;
	
	private MyWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private float acelerometroX=0;
	private Music music;

	private float acelerometroY;
	public GameScreen() {
		super();
		
		//Carrega os audios
		music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/temabombeiro.wav"));
		}
	
	@Override
	public void render(float delta) {
		/*acelerometroX=Gdx.input.getAccelerometerX();
		MathUtils.clamp(acelerometroX, acelerometroX-0.5f, acelerometroX+0.5f);
		if( acelerometroX >=1.5f)
			controller.downPressed();
		else{
			if(acelerometroX+0.1 > 0)
				controller.downReleased();
		}
		
		if(acelerometroX <=-1.5f)
			controller.upPressed();
		else{
			if(acelerometroX-0.1 < 0)
				controller.upReleased();
		}
		
		acelerometroY=Gdx.input.getAccelerometerY();
		MathUtils.clamp(acelerometroY, acelerometroY-0.5f, acelerometroY+0.5f);
		if( acelerometroY >=1.5f)
			controller.rightPressed();
		else{
			if(acelerometroY+0.1 > 0)
				controller.rightReleased();
		}
		
		if(acelerometroY <=-1.5f)
			controller.leftPressed();
		else{
			if(acelerometroY-0.1 < 0)
				controller.leftReleased();
		}*/
		
		controller.update(delta);
		renderer.render();
	}
	

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		//Habilita a musica 
		music.play();
		music.setLooping(true);
		
		world = new MyWorld();
		renderer = new WorldRenderer(world, DEBUG);
		controller = new WorldController(world, renderer);
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
		renderer.dispose();
	}
	
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
		if (keycode == Keys.A){
			controller.firePressed();
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
		if (keycode == Keys.A)
			controller.fireReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		//TODO Verificar se a politica se toque foi habilitada
		controller.firePressed();
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		//TODO Verificar se a politica se toque foi habilitada
		controller.fireReleased();
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
