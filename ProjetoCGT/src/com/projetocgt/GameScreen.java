package com.projetocgt;

import java.util.ArrayList;

import cgt.CGTGameWorld;
import cgt.policy.InputPolicy;
import cgt.util.CGTButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;


public class GameScreen extends Stage implements Screen, InputProcessor {

	public static final boolean DEBUG = false;

	private enum State{PAUSED, RESUMING, PLAYING;};
	private State state = State.PLAYING;
	private Array<Actor> actors;
	private CGTGameWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private SpriteBatch spriteBatch;
	private Music music;
	private boolean flagTouch;

	public GameScreen(CGTGameWorld world) {
		actors = new Array<Actor>();
		this.music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioDaPexe/temaDaPexe.wav"));
		this.world = world;
		renderer = new WorldRenderer(world);
		getActorsFromWorld();
		setSpriteBatch(new SpriteBatch());
		controller = new WorldController(world, renderer);
	}
	
	private void getActorsFromWorld(){
		for (int i = 0; i < world.getWinCriteria().size(); i++){
			world.getWinCriteria().get(i).start();
		}

		for (int i = 0; i < world.getLoseCriteria().size(); i++){
			world.getLoseCriteria().get(i).start();
		}

		for (Actor button : world.getButtons()){
			this.addActor(button);
		}

		for (Actor lifebar : world.getLifeBars()){
			this.addActor(lifebar);
		}
	}

	//	public boolean touchDown(int screenx, int screeny, int pointer, int button){
	//		System.out.println("x: "+screenx);
	//		System.out.println("y: "+screeny);
	//		return false;
	//	}

	public void buttonHandler(){
		for(CGTButton button : world.getButtons()){
			if(button.isActive()){
				pressHandler(button);
			}
			else if(button.isReleased()){
				releaseHandler(button);
				button.setReleased(false);
			}
			
		CGTButton closeButton = world.getDialog().getCloseButton();
		if (closeButton.isActive()){
			world.getDialog().setActive(false);
			resume();
		}

		}
	}

	public void pressHandler(CGTButton button){
		if(button.getInput()==InputPolicy.BTN_1)
			//controller.firePressedTouch();
			pause();
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
		//resume();
		if(button.getInput()==InputPolicy.BTN_UP)
			controller.upReleased();
		else if(button.getInput()==InputPolicy.BTN_DOWN)
			controller.downReleased();
		else if(button.getInput()==InputPolicy.BTN_LEFT)
			controller.leftReleased();
		else if(button.getInput()==InputPolicy.BTN_RIGHT)
			controller.rightReleased();
	}


	public void debug(Actor actor){
		ShapeRenderer shape = new ShapeRenderer();
		shape.begin(ShapeType.Line);
			shape.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		shape.end();
	}
	@Override
	public void render(float delta) {
		switch(state){
		case PLAYING:
			controller.update(delta);
			renderer.render();
			if(renderer.verifyLose())
				music.stop();
			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			
			getSpriteBatch().end();


			//TODO O trecho de codigo abaixo so' eh utilizado quando a tecla A e' pressionada
			//Utilizado para ativar o projectili por um determinado Tempo
			//Com um touch
			if(flagTouch){
				Timer.schedule(new Task(){
					@Override
					public void run() {
						flagTouch=false;
						//verifica se o ammo é zero
						controller.fireReleasedTouch();
						//Timer.instance().clear();
					}
				}, renderer.getCurrentActorProjectile().getInterval());
			}
			break;
			
		case PAUSED:
			Timer.instance().stop();
			if(world.getDialog().isActive()){
				renderer.getSpriteBatch().flush();
				actors = this.getActors();
				this.getActors().clear();
				music.pause();
				this.addActor(world.getDialog());
			}
			
			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			debug(world.getDialog().getChildren().first());
			getSpriteBatch().end();
			break;

		case RESUMING:
			Timer.instance().start();
			this.getActors().clear();
			getActorsFromWorld();
			music.play();
			state = State.PLAYING;
			break;
		}

	}


	@Override
	public void resize(int width, int height) {
		renderer.getViewport().update(width, height);
	}


	@Override
	public void show() {
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
		world.getDialog().setActive(true);
		state = State.PAUSED;

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		state = State.RESUMING;
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

		if (keycode == Keys.LEFT) {
			controller.leftReleased();
		}

		if (keycode == Keys.RIGHT) {
			controller.rightReleased();
		}

		if (keycode == Keys.UP) {
			controller.upReleased();
		}

		if (keycode == Keys.DOWN) {
			controller.downReleased();
		}

		if (keycode == Keys.A) {
			flagTouch=false;
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
