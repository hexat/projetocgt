package com.projetocgt;


import cgt.policy.InputPolicy;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Gesture implements GestureListener{
	private GameScreen game;
	private GestureDetector gd;
	private boolean longTap;
	private Vector2 firstPoint;
	private int contador;
	
	public Gesture(GameScreen game){
		this.game = game;
		gd = new GestureDetector(this);
        longTap = false;
        firstPoint = new Vector2();
        contador = 0;
        
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		firstPoint.x = x;
		firstPoint.y = y;
		return false;
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		if(longTap){
			longTap = false;
			game.getController().deactivateKey(InputPolicy.HOLD_TAP);
			return false;
		} else{
			game.getController().activateKey(InputPolicy.TAP);
			System.out.println("TAP");
			return true;
		}
		
	}

	@Override
	public boolean longPress(float x, float y) {
		longTap = true;
		game.getController().activateKey(InputPolicy.HOLD_TAP);
		System.out.println("HOLD TAP");
		return true;
	}
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		
		if(Math.abs(firstPoint.x - x) > 5 || Math.abs(firstPoint.y-y) > 5){
			contador += 1;
			if (contador > 5){
				game.getController().releaseSlices();
				if(Math.abs(firstPoint.x - x) > Math.abs(firstPoint.y-y)){
					if (firstPoint.x > x){		
						game.getController().activateKey(InputPolicy.SLIDE_LEFT);
					} else {
						game.getController().activateKey(InputPolicy.SLIDE_RIGHT);
					}
				} else {
					if(firstPoint.y > y){
						game.getController().activateKey(InputPolicy.SLIDE_UP);
					} else {
						game.getController().activateKey(InputPolicy.SLIDE_DOWN);
						
					}
				}
			firstPoint.x = x;
			firstPoint.y = y;
			contador  = 0;
			}
			
		}
		
		return true;
//		if(Math.abs(deltaX)> 5 || Math.abs(deltaY) > 5){
//			if (Math.abs(deltaX) >= Math.abs(deltaY)){
//				game.getController().releaseSlices();
//				if(deltaX > 0){
//					game.getController().activateKey(InputPolicy.SLIDE_RIGHT);
////					System.out.println("SLIDE RIGHT");
//				} else {
//					game.getController().activateKey(InputPolicy.SLIDE_LEFT);
////					System.out.println("SLIDE LEFT");
//				}
//			} else {
//				if(deltaY > 0){
//					game.getController().activateKey(InputPolicy.SLIDE_DOWN);
////					System.out.println("SLIDE DOWN");
//					
//				} else {
//					game.getController().activateKey(InputPolicy.SLIDE_UP);
////					System.out.println("SLIDE UP");
//				}
//			}
//			
//			
//		} 
//		return true;
	}
	
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		System.out.println("FLIG");
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		game.getController().releaseSlices();
		System.out.println("OIIII");
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		System.out.println("ZOOM");
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		System.out.println("PINCH");
		return false;
	}

	public GestureDetector getGd() {
		return gd;
	}

	public void setGd(GestureDetector gd) {
		this.gd = gd;
	}

	
	
	
	
}
