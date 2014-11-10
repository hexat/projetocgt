package com.projetocgt;

import java.util.HashMap;
import java.util.Map;

import cgt.CGTGameWorld;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.hud.CGTControllerButton;
import cgt.hud.HUDComponent;
import cgt.policy.ActionMovePolicy;
import cgt.policy.GameModePolicy;
import cgt.policy.InputPolicy;
import cgt.screen.CGTDialog;
import cgt.screen.CGTWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;


public class GameScreen extends Stage implements Screen, InputProcessor {

	public static final boolean DEBUG = true;

	private enum State{PAUSED, RESUMING, PLAYING, WIN, LOSE;};
	private State state = State.PLAYING;
	private CGTGameWorld world;
	private WorldRenderer renderer;
	private WorldController	controller;
	private SpriteBatch spriteBatch;
	private Music music;
	private boolean flagTouch;
	private Vector2 lastPoint;
	
	
	public GameScreen(CGTGameWorld world) {
				
		this.world = world;
		this.music = world.getMusic();
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
		lastPoint = world.getActor().getPosition().cpy();
		
		 
	}

	private void getActorsFromWorld(){
		for(HUDComponent component : world.getHUD()){
			if(component instanceof CGTControllerButton){
				CGTControllerButton button = (CGTControllerButton)component;
				button.setInputListener();
			}
			this.addActor(component);
			component.autosize();
		}
		if (world.getStartGame() != null) {
			CGTButton btn = world.getStartGame();
			btn.setInputListener();
			this.addActor(btn);
			btn.autosize();
		}
	}

	public void buttonHandler(){
		for(HUDComponent component : world.getHUD()){
			if(component instanceof CGTControllerButton){
				CGTControllerButton button = (CGTControllerButton)component;
				if(button.isActive()){
					controller.activateKey(button.getInput());
				}
				else if(button.isReleased()){
					controller.deactivateKey(button.getInput());
					button.setReleased(false);
				}
			}
		}


		for(CGTButtonScreen buttonScreen : world.getPauseDialog().getButtons()){
			if(buttonScreen.isActive()){
				buttonScreen.setTouchable(Touchable.disabled);
				StarAssault.getInstance().restart(buttonScreen.getScreenToGo());
			}
		}

		for(CGTButtonScreen buttonScreen : world.getWinDialog().getButtons()){
			if(buttonScreen.isActive()){	
				buttonScreen.setTouchable(Touchable.disabled);
				world.stopSound(world.getSoundWin());
				StarAssault.getInstance().restart(buttonScreen.getScreenToGo());
			}
		}

		for(CGTButtonScreen buttonScreen : world.getLoseDialog().getButtons()){
			if(buttonScreen.isActive()){
				buttonScreen.setTouchable(Touchable.disabled);
				world.stopSound(world.getSoundLose());
				StarAssault.getInstance().restart(buttonScreen.getScreenToGo());
			}
		}

		CGTButton closeButton = world.getPauseDialog().getCloseButton();
		if (closeButton.isActive()){
			world.getPauseDialog().setActive(false);
			resume();
		}
		
		if (world.getStartGame() != null && world.getStartGame().isActive()) {
			world.getStartGame().setTouchable(Touchable.disabled);
			world.getStartGame().remove();
			if (world.getStartGame().isActionCameraClose()) {
				renderer.cameraCloseOnActor();
			} else {
				renderer.cameraFullScreen();
			}
			if (world.getCamera().getGameMode() == GameModePolicy.TOUCH) {
				Gdx.input.setInputProcessor(new TouchInputs(controller, world));
			}
			world.setStartGame(null);
		}
	}


	public void debug(Actor actor){
	}

	@Override
	public void render(float delta) {
		switch(state){
		case PLAYING:
			controller.update(delta);
			renderer.render();
			if(renderer.verifyWin()){
				state = State.WIN;
				world.playSoundWin();
			}
			if(renderer.lose()) {
				if (music != null){
					music.stop();
				}
				state = State.LOSE;
				world.playSoundLose();
			}
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
						controller.deactivateKey(InputPolicy.BTN_1);
						//Timer.instance().clear();
					}
				}, renderer.getCurrentActorProjectile().getInterval());
			}
			break;

		case PAUSED:
			Gdx.input.setInputProcessor(this);
			if(!world.getPauseDialog().isActive()){
				world.getPauseDialog().setActive(true);
				//Para os behaviors
				//renderer.getSpriteBatch().flush();
				if (music != null){
					music.pause();
				}
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
				Timer.instance().start();
				this.getActors().clear();
				getActorsFromWorld();
				if(music != null){
					music.play();
				}
				break;
			}

			else{
				Timer.instance().stop();
				pause();
				break;
			}


		case WIN:
			Gdx.input.setInputProcessor(this);
			//Timer.instance().stop(); //Para os behaviors
			world.getWinDialog().setActive(true);
			renderer.getSpriteBatch().flush();
			if (music != null){
				music.pause();
			}
			this.getActors().clear();
			addDialog(world.getWinDialog());
			world.getWinDialog().autosize();

			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			getSpriteBatch().end();
			break;

		case LOSE:

			Gdx.input.setInputProcessor(this);
			//Timer.instance().stop(); //Para os behaviors
			
			world.getLoseDialog().setActive(true);
			renderer.getSpriteBatch().flush();
			if (music != null){
				music.pause();
			}
			this.getActors().clear();
			addDialog(world.getLoseDialog());
			world.getLoseDialog().autosize();

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
			button.setInputListener();
		}
		if(dialog.getCloseButton()!=null){
			addActor(dialog.getCloseButton());
			dialog.getCloseButton().setInputListener();
			
		}
	}

	@Override
	public void resize(int width, int height) {
		renderer.getViewport().update(width, height);
	}


	@Override
	public void show() {
		if (music != null){
			music.play();
			music.setLooping(true);
		}
		if (world.getCamera().getGameMode() == GameModePolicy.JOYSTICK || world.getStartGame() != null){
			Gdx.input.setInputProcessor(this);
		} else {
			Gdx.input.setInputProcessor(new TouchInputs(controller, world));
		}	
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
			controller.activateKey(InputPolicy.BTN_LEFT);
		}
		if (keycode == Keys.RIGHT){
			controller.activateKey(InputPolicy.BTN_RIGHT);
		}
		if (keycode == Keys.UP){
			controller.activateKey(InputPolicy.BTN_UP);
		}
		if (keycode == Keys.DOWN){
			controller.activateKey(InputPolicy.BTN_DOWN);
		}
		if (keycode == Keys.A){
			
			controller.activateKey(InputPolicy.BTN_1);
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
			controller.deactivateKey(InputPolicy.BTN_LEFT);
		}

		if (keycode == Keys.RIGHT) {
			controller.deactivateKey(InputPolicy.BTN_RIGHT);
		}

		if (keycode == Keys.UP) {
			controller.deactivateKey(InputPolicy.BTN_UP);
		}

		if (keycode == Keys.DOWN) {
			controller.deactivateKey(InputPolicy.BTN_DOWN);
		}

		if (keycode == Keys.A) {
			flagTouch=false;
			controller.deactivateKey(InputPolicy.BTN_1);
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
	
	public WorldController getController() {
		return controller;
	}
	
	public WorldRenderer getRenderer() {
		return renderer;
	}
	
	public CGTGameWorld getWorld() {
		return world;
	}
}
