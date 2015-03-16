package com.projetocgt;

import cgt.game.CGTGameWorld;
import cgt.policy.InputPolicy;
import cgt.policy.StatePolicy;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.cenario.WorldController;

public class TouchInputs implements InputProcessor{
	
	private Vector2 lastPoint;
	private Vector2 firstPoint;
	private int contador;
	private InputPolicy currentDragged;
	private WorldController controller;
	private GameScreen game;
	private boolean ready;
	private CGTGameWorld world;
	private float xRelative;
	private float yRelative;
	private boolean touchPerson;
	
	public TouchInputs(GameScreen gameScreen){
		game = gameScreen;
		lastPoint = new Vector2();
		firstPoint = new Vector2();
		contador = 0;
		currentDragged = null;
		ready = false;
		this.controller = gameScreen.getController();
		this.world = gameScreen.getWorld();
		xRelative = 0;
		yRelative = 0;
		touchPerson = false;
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		ready = true;
		firstPoint.x = screenX;
		firstPoint.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		controller.releaseAllDirectionKeys();
		
		if (currentDragged == null && ready == true){
			controller.activateKey(InputPolicy.TAP);
		} else {
			ready = true;
		}
		currentDragged = null;
		controller.deactivateKey(InputPolicy.SLIDE_DOWN);
		controller.deactivateKey(InputPolicy.SLIDE_UP);
		controller.deactivateKey(InputPolicy.SLIDE_LEFT);
		controller.deactivateKey(InputPolicy.SLIDE_RIGHT);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		contador += 1;
		if (contador > 5){
			lastPoint.x = screenX;
			lastPoint.y = screenY;
			
			if(Math.abs(firstPoint.x - lastPoint.x) > Math.abs(firstPoint.y-lastPoint.y)){
				controller.releaseAllDirectionKeys();
				if (firstPoint.x > lastPoint.x){		
					controller.activateKey(InputPolicy.SLIDE_LEFT);
					currentDragged = InputPolicy.SLIDE_LEFT;
					
				} else {
					controller.activateKey(InputPolicy.SLIDE_RIGHT);
					currentDragged = InputPolicy.SLIDE_RIGHT;
				}
			} else {
				controller.releaseAllDirectionKeys();
				if(firstPoint.y > lastPoint.y){
					controller.activateKey(InputPolicy.SLIDE_UP);
					currentDragged = InputPolicy.SLIDE_UP;
				} else {
					controller.activateKey(InputPolicy.SLIDE_DOWN);
					currentDragged = InputPolicy.SLIDE_DOWN;
				}
			}
		firstPoint.x = screenX;
		firstPoint.y = screenY;
		contador  = 0;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
