package com.projetocgt;

import java.util.ArrayList;

import cgt.CGTGameWorld;
import cgt.lose.Lose;
import cgt.lose.TargetTime;
import cgt.policy.InputPolicy;
import cgt.screen.CGTButtonScreen;
import cgt.screen.CGTDialog;
import cgt.util.CGTButton;
import cgt.util.LifeBar;

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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;


public class GameScreen extends Stage implements Screen, InputProcessor {

	public static final boolean DEBUG = false;

	private enum State{PAUSED, RESUMING, PLAYING, WIN, LOSE;};
	private State state = State.PLAYING;
	private CGTGameWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private SpriteBatch spriteBatch;
	private Music music;
	private boolean flagTouch;

	public GameScreen(CGTGameWorld world) {
		this.music = Gdx.audio.newMusic(Gdx.files.internal("data/AudioDaPexe/temaDaPexe.wav"));
		this.world = world;

		Timer.instance().start();

		for (int i = 0; i < world.getWinCriteria().size(); i++){
			world.getWinCriteria().get(i).start();
		}

		for (int i = 0; i < world.getLoseCriteria().size(); i++){
			world.getLoseCriteria().get(i).start();
		}

		getActorsFromWorld();
		renderer = new WorldRenderer(world);
		setSpriteBatch(new SpriteBatch());
		controller = new WorldController(world, renderer);
	}

	private void getActorsFromWorld(){

		for (CGTButton button : world.getButtons()){
			this.addActor(button);
			button.autosize();
		}

		for (LifeBar lifebar : world.getLifeBars()){
			this.addActor(lifebar);
			lifebar.autosize();
		}

		for (Lose lose : world.getLoseCriteria()){
			if(lose instanceof TargetTime){
				TargetTime targetTime = (TargetTime)lose;
				if(targetTime.hasLabel()){
					this.addActor(targetTime.getLabel());
					targetTime.getLabel().autosize();
				}
			}
		}


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


		for(CGTButtonScreen buttonScreen : world.getPauseDialog().getButtons()){
			if(buttonScreen.isActive()){
				buttonScreen.setTouchable(Touchable.disabled);
				StarAssault.getInstance().setScreen(buttonScreen.getScreenToGo());
			}
		}

		for(CGTButtonScreen buttonScreen : world.getWinDialog().getButtons()){
			if(buttonScreen.isActive()){
				buttonScreen.setTouchable(Touchable.disabled);
				StarAssault.getInstance().setScreen(buttonScreen.getScreenToGo());
			}
		}

		CGTButton closeButton = world.getPauseDialog().getCloseButton();
		if (closeButton.isActive()){
			world.getPauseDialog().setActive(false);
			resume();
		}
	}

	public void pressHandler(CGTButton button){
		if(button.getInput()==InputPolicy.BTN_1)
			controller.firePressedTouch();
		//pause();
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
	}

	@Override
	public void render(float delta) {
		switch(state){
		case PLAYING:
			controller.update(delta);
			renderer.render();
			if(renderer.verifyWin())
				state = State.WIN;
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
			if(!world.getPauseDialog().isActive()){
				world.getPauseDialog().setActive(true);
				//Para os behaviors
				//renderer.getSpriteBatch().flush();
				music.pause();
				this.getActors().clear();

				addDialog(world.getPauseDialog());
				world.getPauseDialog().autosize();
			}

			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			getSpriteBatch().end();
			break;

		case RESUMING:
			if(!world.getPauseDialog().isActive()){
				state = State.PLAYING;
				System.out.println("Entreiaki");
				Timer.instance().start();
				this.getActors().clear();
				getActorsFromWorld();
				music.play();
				break;
			}

			else{
				Timer.instance().stop();
				pause();
				break;
			}


		case WIN:
			//Timer.instance().stop(); //Para os behaviors
			world.getWinDialog().setActive(true);
			renderer.getSpriteBatch().flush();
			music.pause();
			this.getActors().clear();
			addDialog(world.getWinDialog());
			world.getWinDialog().autosize();

			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			getSpriteBatch().end();
			break;
		}


	}

	private void addDialog(CGTDialog dialog){
		addActor(dialog);

		for(CGTButtonScreen button : dialog.getButtons()){
			addActor(button);
			button.autosize();
		}
		if(dialog.getCloseButton()!=null){
			addActor(dialog.getCloseButton());
		}
	}

	private void removeDialog(CGTDialog dialog){
		getActors().indexOf(dialog, false);

		for(CGTButton button : dialog.getButtons()){
			addActor(button);
		}

		addActor(dialog.getCloseButton());
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
		Timer.instance().stop();
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
		if (keycode == Keys.P){
			state = State.PAUSED;
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
