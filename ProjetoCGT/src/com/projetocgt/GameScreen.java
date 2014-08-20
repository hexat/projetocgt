package com.projetocgt;

import cgt.CGTGameWorld;

import cgt.policy.*;
import cgt.util.CGTButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.cenario.MyWorld;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;
	

public class GameScreen extends Stage implements Screen, InputProcessor{

	private static final boolean DEBUG = false;

	private CGTGameWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private SpriteBatch spriteBatch;
	private float acelerometroX=0;
	private Music music;
	private float acelerometroY;
	private boolean flagTouch;


	public GameScreen() {
		super();
		//Carrega os audios
		music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/principal.wav"));
		world = new MyWorld().getCGT();
		renderer = new WorldRenderer(world, DEBUG);
		
		for(int i = 0; i < world.getWinCriteria().size(); i++){
			world.getWinCriteria().get(i).start();
		}
		
		for(int i = 0; i < world.getLoseCriteria().size(); i++){
			world.getLoseCriteria().get(i).start();
		}
		
		for(Actor button : world.getButtons()){
			this.addActor(button);
		}
		
		for(Actor lifebar : world.getLifeBars()){
			this.addActor(lifebar);
		}
		//this.setViewport(renderer.getViewport());
		setSpriteBatch(new SpriteBatch());
		//renderer = new WorldRenderer(instanciaStream("teste"), DEBUG); //Desenhando a partir de um MyWorld criado pelo arquivo
		controller = new WorldController(world, renderer);
		
	}

	public boolean touchDown(int screenx, int screeny, int pointer, int button){
		System.out.println("x: "+screenx);
		System.out.println("y: "+screeny);
		return false;
	}

	public void buttonHandler(){
		for(CGTButton button : world.getButtons()){
			if(button.isActive()){
				pressHandler(button);
			}
			else if(button.isReleased()){
				releaseHandler(button);
				button.setReleased(false);
			}

		}
	}
	
	public void pressHandler(CGTButton button){
		if(button.getInput()==InputPolicy.BTN_1)
			controller.firePressedTouch();
		if(button.getInput()==InputPolicy.BTN_UP)
			controller.upPressed();
		else if(button.getInput()==InputPolicy.BTN_DOWN)
			controller.downPressed();
		else if(button.getInput()==InputPolicy.BTN_LEFT)
			controller.leftPressed();
		else if(button.getInput()==InputPolicy.BTN_RIGHT)
			controller.rightPressed();
	}
	
	public void releaseHandler(CGTButton button){
		if(button.getInput()==InputPolicy.BTN_1)
			controller.fireReleasedTouch();
		if(button.getInput()==InputPolicy.BTN_UP)
			controller.upReleased();
		else if(button.getInput()==InputPolicy.BTN_DOWN)
			controller.downReleased();
		else if(button.getInput()==InputPolicy.BTN_LEFT)
			controller.leftReleased();
		else if(button.getInput()==InputPolicy.BTN_RIGHT)
			controller.rightReleased();
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
		buttonHandler();
		this.act();
		getSpriteBatch().begin();
		this.draw();
		getSpriteBatch().end();
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
		renderer.getViewport().update(width, height);
	}


	@Override
	public void show() {
		//Habilita a musica 
		music.play();
		music.setLooping(true);
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
		this.dispose();
	}

	
	//Funciona na descida do botao
	@Override
	public boolean keyDown(int keycode) {
		
			if (keycode == Keys.LEFT ){
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




	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}


	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}
}
