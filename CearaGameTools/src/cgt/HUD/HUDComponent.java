package cgt.HUD;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class HUDComponent extends Actor {
	private float relativeX;
	private float relativeY;
	private float relativeWidth;
	private float relativeHeight;

	public void autosize(){
		setWidth(getStage().getWidth()*relativeWidth);
		setHeight(getStage().getHeight()*relativeHeight);
	
		setX(getStage().getWidth()*relativeX);
		setY(getStage().getHeight()*relativeY);
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

	public float getRelativeWidth() {
		return relativeWidth;
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

}
