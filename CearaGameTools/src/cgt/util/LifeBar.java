package cgt.util;

import cgt.core.CGTGameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class LifeBar extends Actor {
	protected Texture bar;
	protected Texture backgroundBar;
	protected float maxLife;
	protected float currentLife;
	protected float lifebarOffsetX;
	protected float lifeRate;
	protected float relativeX;
	protected float relativeY;
	protected float relativeWidth;
	protected float relativeHeight;
	
	public void autosize(){
		setWidth(getStage().getWidth()*relativeWidth);
		setHeight(getStage().getHeight()*relativeHeight);
	
		setX(getStage().getWidth()*relativeX);
		setY(getStage().getHeight()*relativeY);
	}
	
	public void draw(Batch batch, float parentAlpha){
			batch.draw(backgroundBar, getX(), getY(), getWidth(), getHeight());
			batch.draw(bar, getX() + lifebarOffsetX*getWidth(), getY(), (1 - lifebarOffsetX)*getWidth()*lifeRate, getHeight());
	}
	
	public float getRelativeX() {
		return relativeX;
	}
	
	public void setOffsetX(float offsetX){
		lifebarOffsetX = offsetX;
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
	public float getRelativeWidth() {
		return relativeWidth;
	}
	public Texture getBar() {
		return bar;
	}

	public void setBar(Texture bar) {
		this.bar = bar;
	}

	public Texture getBackgroundBar() {
		return backgroundBar;
	}

	public void setBackgroundBar(Texture backgroundBar) {
		this.backgroundBar = backgroundBar;
	}

	public void setRelativeWidth(float initialRelativeWidth) {
		this.relativeWidth = initialRelativeWidth;
	}
	public float getRelativeHeight() {
		return relativeHeight;
	}
	public void setRelativeHeight(float initialRelativeHeight) {
		this.relativeHeight = initialRelativeHeight;
	}
	public float getMaxLife() {
		return maxLife;
	}
	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
	public float getCurrentLife() {
		return currentLife;
	}
	public void setCurrentLife(int currentLife) {
		this.currentLife = currentLife;
	}
}
