package com.projetocgt;

import cgt.CGTGameWorld;
import cgt.policy.InputPolicy;
import cgt.policy.StatePolicy;
import cgt.screen.CGTWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g3d.Renderable;
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
//		float xRelative;
//		float yRelative;
//		
//		xRelative = world.getBackground().getTextureGDX().getWidth()* screenX/Gdx.graphics.getWidth();
//		yRelative = world.getBackground().getTextureGDX().getHeight()* screenY/Gdx.graphics.getHeight();
//		
//		
//		
//		if (xRelative > world.getActor().getPosition().x && xRelative < (world.getActor().getPosition().x + world.getActor().getBounds().width)){
//			if (yRelative > world.getActor().getPosition().y && yRelative < (world.getActor().getPosition().y + world.getActor().getBounds().height)){
//				System.out.println("ESTOU TOCANDO NO PERSONAGEM");
//				world.getActor().getPosition().x = xRelative;
//				world.getActor().getPosition().y = yRelative;
//				touchPerson = true;
//			}
//		}
		
		ready = true;
		firstPoint.x = screenX;
		firstPoint.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (currentDragged == null && ready == true){
			controller.activateKey(InputPolicy.TAP);
		} else {
			ready = true;
			//System.out.println("desabilitando");
			controller.deactivateKey(InputPolicy.SLIDE_UP);
			controller.deactivateKey(InputPolicy.SLIDE_DOWN);
			controller.deactivateKey(InputPolicy.SLIDE_LEFT);
			controller.deactivateKey(InputPolicy.SLIDE_RIGHT);
		}
		currentDragged = null;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		if (touchPerson){
//			world.getActor().getPosition().x = xRelative;
//			world.getActor().getPosition().y = yRelative;
//		}
//		
//		return false;
////////////////////////////////////////////////////////////////////////////
		if (world.getActor().getState() != StatePolicy.LOOKRIGHT) {
			world.getActor().setState(StatePolicy.IDLERIGHT);
			world.getActor().getPosition().y = (game.getHeight()-screenY)*(world.getBackground().getTextureGDX().getHeight()-150)/game.getHeight() + 100;
		}
////////////////////////////////////////////////////////////////////////////
		contador += 1;
		if (contador > 5){
			lastPoint.x = screenX;
			lastPoint.y = screenY;
			
			
//			System.out.println(world.getActor().getPosition().y);
//			System.out.println(screenY);
			
			
			if(Math.abs(firstPoint.x - lastPoint.x) > Math.abs(firstPoint.y-lastPoint.y)){
				if (firstPoint.x > lastPoint.x){
					//System.out.println("SLIDE LEFT");
					if (currentDragged == InputPolicy.SLIDE_RIGHT){
						controller.deactivateKey(InputPolicy.SLIDE_RIGHT);
					}
					controller.activateKey(InputPolicy.SLIDE_LEFT);
					currentDragged = InputPolicy.SLIDE_LEFT;
				} else {
					if (currentDragged == InputPolicy.SLIDE_LEFT){
						controller.deactivateKey(InputPolicy.SLIDE_LEFT);
					}
					//System.out.println("SLIDE RIGHT");
					controller.activateKey(InputPolicy.SLIDE_RIGHT);
					currentDragged = InputPolicy.SLIDE_RIGHT;
				}
			} else {
				if(firstPoint.y > lastPoint.y){
					//System.out.println("SLIDE UP");
					if (currentDragged == InputPolicy.SLIDE_DOWN){
						controller.deactivateKey(InputPolicy.SLIDE_DOWN);
					}
					controller.activateKey(InputPolicy.SLIDE_UP);
					currentDragged = InputPolicy.SLIDE_UP;
				} else {
					//System.out.println("SLIDE DOWN");
					if (currentDragged == InputPolicy.SLIDE_UP){
						controller.deactivateKey(InputPolicy.SLIDE_UP);
					}
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
