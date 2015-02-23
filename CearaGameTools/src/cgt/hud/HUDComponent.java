package cgt.hud;

import java.io.Serializable;

import cgt.unit.Action;

import cgt.util.Validator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class HUDComponent extends Actor implements Serializable, Validator {
	private float relativeX;
	private float relativeY;
	private float relativeWidth;
	private float relativeHeight;

	public void setup(){
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

    public boolean validate() {
        return relativeHeight > 0 && relativeWidth > 0;
    }
}
