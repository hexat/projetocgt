package cgt.HUD;

import cgt.core.CGTGameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class LifeBar extends HUDComponent {
	protected Texture bar;
	protected Texture backgroundBar;
	protected float maxLife;
	protected float currentLife;
	protected float lifebarOffsetX;
	protected float lifeRate;
	
	public void draw(Batch batch, float parentAlpha){
			batch.draw(backgroundBar, getX(), getY(), getWidth(), getHeight());
			batch.draw(bar, getX() + lifebarOffsetX*getWidth(), getY(), (1 - lifebarOffsetX)*getWidth()*lifeRate, getHeight());
	}
	
	public void setOffsetX(float offsetX){
		lifebarOffsetX = offsetX;
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
