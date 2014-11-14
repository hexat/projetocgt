package cgt.hud;

import java.io.Serializable;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class HUDComponent extends Actor implements Serializable{
	private float relativeX;
	private float relativeY;
	private float relativeWidth;
	private float relativeHeight;

	public void autosize(){
		System.out.println(getStage().getWidth()*relativeWidth);
		System.out.println(getStage().getWidth()*relativeHeight);
		setWidth(getStage().getWidth()*relativeWidth);
		setHeight(getStage().getHeight()*relativeHeight);
		System.out.println(getStage().getWidth()*relativeX);
		System.out.println(getStage().getWidth()*relativeY);
		setX(getStage().getWidth()*relativeX);
		setY(getStage().getHeight()*relativeY);
		System.out.println(getX());
		System.out.println(getY());
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
