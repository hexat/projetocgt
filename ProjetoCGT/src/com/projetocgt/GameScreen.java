package com.projetocgt;

import cgt.CGTGameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.cenario.MyWorld;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	
	private static final boolean DEBUG = true;
	
	private CGTGameWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private float acelerometroX=0;
	private Music music;
	private float acelerometroY;
	private boolean flagTouch;
	
	
	public GameScreen() {
		super();
		//Carrega os audios
		music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/principal.wav"));
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
		}
		*/
		controller.update(delta);
		renderer.render();
		//Utilizado para ativar o projectili por um determinado Tempo
		//Com um touch
		if(flagTouch){
			Timer.schedule(new Task(){
				@Override
				public void run() {
					flagTouch=false;
					//verifica se o ammo é zero
					controller.fireReleasedTouch();
					Timer.instance().clear();
				}
			}, renderer.getInterval());
		}
	}
	

	@Override
	public void resize(int width, int height) {
	}


	@Override
	public void show() {
		//Habilita a musica 
		music.play();
		music.setLooping(true);
		world = new MyWorld().getCGT();
		renderer = new WorldRenderer(world, DEBUG);
		//renderer = new WorldRenderer(instanciaStream("teste"), DEBUG); //Desenhando a partir de um MyWorld criado pelo arquivo
		
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
	
	public static void main(String[] args) {
		System.out.println(Keys.toString(30));
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
			flagTouch=true;
			controller.firePressedTouch();
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
		if (keycode == Keys.A){
			flagTouch=false;
			//controller.fireReleased();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		//TODO Verificar se a politica de toque foi habilitada
		//TODO Cada policy terá uma funcao para habilitar ou desabilitar o projectili
		flagTouch=true;
		controller.firePressedTouch();
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		//TODO Verificar se a politica se toque foi habilitada
		//controller.fireReleasedTouch();
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
