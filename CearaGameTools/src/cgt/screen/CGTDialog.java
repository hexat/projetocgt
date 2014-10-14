package cgt.screen;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.util.CGTTexture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class CGTDialog extends Actor implements Serializable{
	private ArrayList<CGTButtonScreen> screenSwitchButtons;
	private CGTButton closeButton;
	private boolean active;
	private CGTTexture rightBottomCorner;
	private CGTTexture horizontalBorderTexture;
	private CGTTexture window;
	private float relativeWidth;
	private float relativeHeight;
	private float relativeX;
	private float relativeY;

	public CGTDialog(){
		screenSwitchButtons = new ArrayList<CGTButtonScreen>();
		setTouchable(Touchable.enabled);
	}

	public void autosize(){
		setWidth(getStage().getWidth()*relativeWidth);
		setHeight(getStage().getHeight()*relativeHeight);

		setX(getStage().getWidth()*relativeX);
		setY(getStage().getHeight()*relativeY);
		if (closeButton != null){
			closeButton.autosize();
		}

	}

	public void act(float delta){
		super.act(delta);
	}


	public void draw(Batch batch, float parentAlpha){
		if(isActive()){
			batch.draw(window.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
			drawBorders(batch);
			if (closeButton != null){
				closeButton.draw(batch, parentAlpha);
			}
		}
	}
	
	public void drawBorders(Batch batch){
		float borderHeight = horizontalBorderTexture.getTextureRegion().getRegionHeight();
		float borderWidth = horizontalBorderTexture.getTextureRegion().getRegionWidth();
		float borderX = this.getX();
		float borderY = this.getY();
		float upperBorderY = borderY + this.getHeight()-borderHeight;
		
		//Desenho das linhas horizontais
		while(borderX+borderWidth<this.getX() + this.getWidth()){
			batch.draw(horizontalBorderTexture.getTextureRegion(), borderX, borderY, borderWidth, borderHeight);
			batch.draw(horizontalBorderTexture.getTextureRegion(), borderX, upperBorderY, borderWidth, borderHeight);
			borderX+=borderWidth;
		}
		
		borderX = this.getX()+borderHeight;
		borderY = this.getY();
		float rightBorderX = borderX + this.getWidth()-borderHeight;
		
		//Desenha linhas verticais
		while(borderY+borderWidth<this.getY() + this.getHeight()){
			batch.draw(horizontalBorderTexture.getTextureRegion(), borderX, borderY, 0, 0, borderWidth, borderHeight, 1, 1, 90);
			batch.draw(horizontalBorderTexture.getTextureRegion(), rightBorderX, borderY, 0, 0, borderWidth, borderHeight, 1, 1, 90);
			borderY+=borderHeight;
		}
		float cornerX = this.getX()+this.getWidth()-rightBottomCorner.getTextureRegion().getRegionWidth();
		float cornerY = this.getY();
		float cornerWidth = rightBottomCorner.getTextureRegion().getRegionWidth();
		float cornerHeight = rightBottomCorner.getTextureRegion().getRegionHeight();
		
		batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, 0, 0, cornerWidth, cornerHeight, 1, 1, 0); //direito de baixo
		
		cornerY = this.getY()+this.getHeight()-cornerHeight;
		batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, cornerWidth/2, cornerHeight/2, cornerWidth, cornerHeight, 1, 1, 90); // direito de cima
		
		cornerX = this.getX();
		batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, cornerWidth/2, cornerHeight/2, cornerWidth, cornerHeight, 1, 1, 180); //esquerdo de cima
		
		cornerY = this.getY();
		batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, cornerWidth/2, cornerHeight/2, cornerWidth, cornerHeight, 1, 1, 270); //esquerdo de baixo
		
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CGTTexture getWindow() {
		return window;
	}

	public void setWindow(CGTTexture window) {
		this.window = window;
	}

	public float getRelativeWidth() {
		return relativeWidth;
	}

	public void setRelativeWidth(float relativeWidth) {
		this.relativeWidth = relativeWidth;
	}

	public float getRelativeHeight() {
		return relativeHeight;
	}

	public void setRelativeHeight(float relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

	public float getRelativeX() {
		return relativeX;
	}

	public void setRelativeX(float relativeX) {
		this.relativeX = relativeX;
	}

	public float getRelativeY() {
		return relativeY;
	}

	public void setRelativeY(float relativeY) {
		this.relativeY = relativeY;
	}

	public ArrayList<CGTButtonScreen> getButtons() {
		return screenSwitchButtons;
	}

	public void addButton(CGTButtonScreen button){
		screenSwitchButtons.add(button);
	}

	public CGTButton getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(CGTButton closeButton) {
		this.closeButton = closeButton;
		//addActor(closeButton);
	}

	public TextureRegion getRightBottomCorner() {
		return rightBottomCorner.getTextureRegion();
	}

	public void setRightBottomCorner(CGTTexture rightBottomCorner) {
		this.rightBottomCorner = rightBottomCorner;
	}

	public TextureRegion getHorizontalBorderTexture() {
		return horizontalBorderTexture.getTextureRegion();
	}

	public void setHorizontalBorderTexture(CGTTexture horizontalBorderTexture) {
		this.horizontalBorderTexture = horizontalBorderTexture;
	}
}