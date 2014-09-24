package cgt.HUD;

import cgt.policy.InputPolicy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;


public class CGTButton extends Actor {
	private InputPolicy input; 
	private boolean active;
	private boolean released;
	private Texture textureUp;
	private Texture textureDown;
	private float relativeX;
	private float relativeY;
	private float relativeWidth;
	private float relativeHeight;

	public CGTButton(){
		super();
		active=false;
		released=false;
		setTouchable(Touchable.enabled);
		setInputListener();
		relativeX=0.5f;
		relativeY=0.5f;
		relativeWidth=0.2f;
		relativeHeight=0.2f;
	}

	private void setInputListener(){
		addListener(new InputListener() {

			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				active=true;
				return true;
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
				active=true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				active=false;
				setReleased(true);
			}

			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				active=false;
				setReleased(true);
			}
		});
	}
	
	public void autosize(){
			setWidth(getStage().getWidth()*relativeWidth);
			setHeight(getStage().getHeight()*relativeHeight);

			setX(getStage().getWidth()*relativeX);
			setY(getStage().getHeight()*relativeY);
	}
	
	@Override
	public void act(float delta){
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void draw(Batch batch, float parentAlpha){
		if(active)
			batch.draw(textureDown, getX(), getY(), getWidth(), getHeight());
		else
			batch.draw(textureUp, getX(), getY(), getWidth(), getHeight());
	}

	public Texture getTextureUp() {
		return textureUp;
	}

	public void setTextureUp(Texture textureUp) {
		this.textureUp = textureUp;
	}

	public Texture getTextureDown() {
		return textureDown;
	}

	public void setTextureDown(Texture textureDown) {
		this.textureDown = textureDown;
	}

	public InputPolicy getInput() {
		return input;
	}

	public void setInput(InputPolicy input) {
		this.input = input;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
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

	public void setRelativeWidth(float relativeWidth) {
		this.relativeWidth = relativeWidth;
	}

	public float getRelativeHeight() {
		return relativeHeight;
	}

	public void setRelativeHeight(float relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

}
