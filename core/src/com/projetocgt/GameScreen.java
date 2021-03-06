package com.projetocgt;

import cgt.game.CGTGameWorld;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.hud.CGTControllerButton;
import cgt.hud.HUDComponent;
import cgt.policy.GameModePolicy;
import cgt.policy.InputPolicy;
import cgt.screen.CGTDialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.cenario.WorldController;
import com.projetocgt.cenario.WorldRenderer;

public class GameScreen extends Stage implements Screen, InputProcessor {

	private enum State {
		PAUSED, RESUMING, PLAYING, WIN, LOSE;
	};

	private State state = State.PLAYING;
	private CGTGameWorld world;
	private WorldRenderer renderer;
	private WorldController controller;
	private SpriteBatch spriteBatch;
	private boolean flagTouch;
	private Gesture gesture;
	private InputMultiplexer inputMultiplexer;
	private AudioManager audioManager;
	private int contador;

	public GameScreen(CGTGameWorld world) {


		if (world.getActor().getBounds().width <= 0) {
			world.getActor().getBounds().width = world.getActor().getAnimation().getRegionWidth();
		}
		if (world.getActor().getBounds().height <= 0) {
			world.getActor().getBounds().height = world.getActor().getAnimation().getRegionHeight();
		}
		
		this.world = world;
		
		Timer.instance().start();

		for (int i = 0; i < world.getWinCriteria().size(); i++) {
			world.getWinCriteria().get(i).start();
		}

		for (int i = 0; i < world.getLoseCriteria().size(); i++) {
			world.getLoseCriteria().get(i).start();
		}

		getActorsFromWorld();
		audioManager = new AudioManager(world);
		renderer = new WorldRenderer(world,audioManager);
		setSpriteBatch(new SpriteBatch());
		controller = new WorldController(world, renderer);
		
		gesture = new Gesture(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gesture.getGd());
		// /////////////////////////////////////////////////////////////////////////////////////
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		int vezes = prefs.getInteger("num_vezes", 0);
		
		vezes += 1;
		prefs.putInteger("num_vezes", vezes);
		prefs.flush();
		contador = 0;
		
	}

	private void getActorsFromWorld() {
		for (HUDComponent component : world.getHUD()) {
			if (component instanceof CGTControllerButton) {
				CGTControllerButton button = (CGTControllerButton) component;
				button.setInputListener();
			}
			this.addActor(component);
			component.setup();
		}
		if (world.getInitialDialog() != null) {
			world.getInitialDialog().setActive(true);
			addDialog(world.getInitialDialog());
			world.getInitialDialog().autosize();
			// this.addActor(btn);
			// btn.setup();
		}
	}

	public void buttonHandler() {
		for (HUDComponent component : world.getHUD()) {
			if (component instanceof CGTControllerButton) {
				CGTControllerButton button = (CGTControllerButton) component;
				if (button.isActive()) {
					controller.activateKey(button.getInput());
				} else if (button.isReleased()) {
					controller.deactivateKey(button.getInput());
					button.setReleased(false);
				}
			}
		}

		if (world.getPauseDialog() != null) {
			for (CGTButtonScreen buttonScreen : world.getPauseDialog()
					.getButtons()) {
				if (buttonScreen.isActive()) {
					buttonScreen.setTouchable(Touchable.disabled);
					StarAssault.getInstance().restart(
							buttonScreen.getWindowId());
				}
			}

			CGTButton closeButton = world.getPauseDialog().getCloseButton();
			if (closeButton != null && closeButton.isActive()) {
				world.getPauseDialog().setActive(false);

				Gdx.input.setInputProcessor(new TouchInputs(this));
				resume();
			}

		}

		if (world.getInitialDialog() != null) {
			for (CGTButtonScreen buttonScreen : world.getInitialDialog()
					.getButtons()) {
				if (buttonScreen.isActive()) {
					buttonScreen.setTouchable(Touchable.disabled);
					StarAssault.getInstance().restart(
							buttonScreen.getWindowId());
				}
			}

			CGTButton closeButtonInitialDialog = world.getInitialDialog()
					.getCloseButton();
			if (closeButtonInitialDialog.isActive()) {
				world.getInitialDialog().setActive(false);
				world.getInitialDialog().setTouchable(Touchable.disabled);
				world.getInitialDialog().remove();
				for (int i = 0; i < world.getInitialDialog().getButtons()
						.size(); i++) {
					world.getInitialDialog().getButtons().get(i).remove();
				}

				closeButtonInitialDialog.remove();

				renderer.cameraFullScreen();

				if (world.getCamera().getGameMode() == GameModePolicy.TOUCH) {
					Gdx.input.setInputProcessor(new TouchInputs(this));
				}
				world.setStartGame(null);

			}

		}

		if (world.getWinDialog() != null) {
			for (CGTButtonScreen buttonScreen : world.getWinDialog()
					.getButtons()) {
				if (buttonScreen.isActive()) {
					buttonScreen.setTouchable(Touchable.disabled);
					world.stopSound(world.getSoundWin());
					StarAssault.getInstance().restart(
							buttonScreen.getWindowId());
				}
			}
		}

		if (world.getLoseDialog() != null) {
			for (CGTButtonScreen buttonScreen : world.getLoseDialog()
					.getButtons()) {
				if (buttonScreen.isActive()) {
					buttonScreen.setTouchable(Touchable.disabled);
					world.stopSound(world.getSoundLose());
					StarAssault.getInstance().restart(
							buttonScreen.getWindowId());
				}
			}
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
				Gdx.input.setInputProcessor(new TouchInputs(this));
			}
			world.setStartGame(null);
		}
	}

	public void debug(Actor actor) {
	}

	@Override
	public void render(float delta) {
		switch (state) {
		case PLAYING:			
			accelerometer();
			
			Gdx.input.setInputProcessor(gesture.getGd());

			controller.update(delta);
			renderer.render();
			if (renderer.verifyWin()) {
				state = State.WIN;
				audioManager.stopGameMusic();
				audioManager.playWinMusic();
			}
			if (renderer.lose()) {
				audioManager.stopGameMusic();
				state = State.LOSE;
				audioManager.playLoseMusic();
				
			}
			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();

			getSpriteBatch().end();

			// TODO O trecho de codigo abaixo so' eh utilizado quando a tecla A
			// e' pressionada
			// Utilizado para ativar o projectili por um determinado Tempo
			// Com um touch
			if (flagTouch) {
				Timer.schedule(new Task() {
					@Override
					public void run() {
						flagTouch = false;
						// verifica se o ammo é zero
						controller.deactivateKey(InputPolicy.BTN_1);
						// Timer.instance().clear();
					}
				}, renderer.getCurrentActorProjectile().getInterval());
			}
			break;

		case PAUSED:
			if (world.getPauseDialog() == null) {
				state = State.PLAYING;
				break;
			}
			Gdx.input.setInputProcessor(this);
			renderer.getSpriteBatch().begin();
			renderer.draw();
			renderer.getSpriteBatch().end();
			if (!world.getPauseDialog().isActive()) {
				world.getPauseDialog().setActive(true);
				// Para os behaviors
				// renderer.getSpriteBatch().flush();
				audioManager.stopGameMusic();
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
			if (world.getPauseDialog() == null) {
				state = State.PLAYING;
				break;
			}
			if (!world.getPauseDialog().isActive()) {
				state = State.PLAYING;
				Timer.instance().start();
				this.getActors().clear();
				getActorsFromWorld();
				audioManager.playGameMusic();
				break;
			}

			else {
				Timer.instance().stop();
				pause();
				break;
			}

		case WIN:
			Gdx.input.setInputProcessor(this);
			// Timer.instance().stop(); //Para os behaviors
			renderer.getSpriteBatch().begin();
			renderer.draw();
			renderer.getSpriteBatch().end();
			if (world.getWinDialog() != null && !world.getWinDialog().isActive()) {
				world.getWinDialog().setActive(true);
				renderer.getSpriteBatch().flush();
				audioManager.stopGameMusic();
				audioManager.playWinMusic();
				this.getActors().clear();
				addDialog(world.getWinDialog());
				world.getWinDialog().autosize();
			} else if (world.getWinDialog() == null) {
                StarAssault.getInstance().create();
            }
			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			getSpriteBatch().end();
			break;

		case LOSE:
			Gdx.input.setInputProcessor(this);
			renderer.getSpriteBatch().begin();
			renderer.draw();
			renderer.getSpriteBatch().end();
			if (world.getLoseDialog() != null && !world.getLoseDialog().isActive()) {
				world.getLoseDialog().setActive(true);

				renderer.getSpriteBatch().flush();
				audioManager.stopGameMusic();
				this.getActors().clear();

				addDialog(world.getLoseDialog());
				world.getLoseDialog().autosize();
			} else if (world.getLoseDialog() == null) {
                StarAssault.getInstance().create();
            }
			buttonHandler();
			this.act();
			getSpriteBatch().begin();
			this.draw();
			getSpriteBatch().end();
			break;
		}

	}

	private void addDialog(CGTDialog dialog) {
		addActor(dialog);

		for (CGTButtonScreen button : dialog.getButtons()) {
			addActor(button);
			button.setup();
			button.setInputListener();
		}
		if (dialog.getCloseButton() != null) {
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
		audioManager.playGameMusic();
		if (world.getCamera().getGameMode() == GameModePolicy.JOYSTICK
				|| world.getStartGame() != null) {
			Gdx.input.setInputProcessor(this);
		} else {
			Gdx.input.setInputProcessor(new TouchInputs(this));
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
		Timer.instance().start();
		// TODO Auto-generated method stub
		state = State.RESUMING;
	}

	@Override
	public void dispose() {
		renderer.dispose();
		this.dispose();
	}

	// Funciona na descida do botao
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			controller.activateKey(InputPolicy.BTN_LEFT);
			
		}
		if (keycode == Keys.RIGHT) {
			controller.activateKey(InputPolicy.BTN_RIGHT);
		}
		if (keycode == Keys.UP) {
			controller.activateKey(InputPolicy.BTN_UP);
		}
		if (keycode == Keys.DOWN) {
			controller.activateKey(InputPolicy.BTN_DOWN);
		}
		if (keycode == Keys.A) {

			controller.activateKey(InputPolicy.BTN_1);
		}
		if (keycode == Keys.P) {
			state = State.PAUSED;
		}
		if (keycode == Keys.BACK) {
			state = State.PAUSED;
		}
		return false;
		// return true;
	}

	// Funciona na subida do botao
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
			flagTouch = false;
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
	
	public AudioManager getAudioManager() {
		return audioManager;
	}

	public void setAudioManager(AudioManager audioManager) {
		this.audioManager = audioManager;
	}

	public void accelerometer(){		
		contador++;
		if(contador >=5){
			getController().releaseAccelerometer();
	    contador = 0;
		}
		if(Math.abs(Gdx.input.getAccelerometerX()) > Math.abs(Gdx.input.getAccelerometerY())){							
			if(Gdx.input.getAccelerometerX() > 0){
				controller.activateKey(InputPolicy.ACEL_DOWN);
			} else {
				controller.activateKey(InputPolicy.ACEL_UP);
			} 
		} else{
			if(Gdx.input.getAccelerometerY() > 0){
				controller.activateKey(InputPolicy.ACEL_RIGHT);
			} else {
				controller.activateKey(InputPolicy.ACEL_LEFT);
			} 
			
		}
		
	}
}
