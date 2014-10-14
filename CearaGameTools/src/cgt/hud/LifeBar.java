package cgt.hud;

import cgt.core.CGTGameObject;
import cgt.util.CGTTexture;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class LifeBar extends HUDComponent {
	protected CGTTexture bar;
	protected CGTTexture backgroundBar;
	protected float maxLife;
	protected float currentLife;
	protected float lifebarOffsetX;
	protected float lifeRate;
	
	public void draw(Batch batch, float parentAlpha){
			batch.draw(backgroundBar.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
			batch.draw(bar.getTextureGDX(), getX() + lifebarOffsetX*getWidth(), getY(), (1 - lifebarOffsetX)*getWidth()*lifeRate, getHeight());
	}
	
	public void setOffsetX(float offsetX){
		lifebarOffsetX = offsetX;
	}
	
	public CGTTexture getBar() {
		return bar;
	}

	public void setBar(CGTTexture bar) {
		this.bar = bar;
	}

	public CGTTexture getBackgroundBar() {
		return backgroundBar;
	}

	public void setBackgroundBar(CGTTexture backgroundBar) {
		this.backgroundBar = backgroundBar;
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
