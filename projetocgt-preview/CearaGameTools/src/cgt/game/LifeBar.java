package cgt.game;

import cgt.hud.HUDComponent;
import cgt.util.CGTTexture;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class LifeBar extends HUDComponent {
	protected String worldId;
	private String id;

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

	protected void setWorld(CGTGameWorld world) {
		worldId = world.getId();
	}
	
	public boolean removeFromWorld() {
		if (getWorld() != null) {
			getWorld().getHUD().remove(this);
		}
		worldId = null;
		return false;
	}

	public CGTGameWorld getWorld() {
//		System.out.println(worldId);
		return CGTGame.get().getWorld(worldId);
	}

	public String getId(){ return this.id; }
	public void setId(String id){ this.id = id; }
}
