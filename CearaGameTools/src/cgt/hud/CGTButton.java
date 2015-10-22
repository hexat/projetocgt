package cgt.hud;

import java.io.Serializable;

import cgt.policy.InputPolicy;
import cgt.util.CGTTexture;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;


public class CGTButton extends HUDComponent implements Serializable{
	
	private boolean active;
	private boolean released;
	private CGTTexture textureUp;
	private CGTTexture textureDown;

	public CGTButton() {
		super();
		active=false;
		released=false;
	}

	@Override
	public void setup() {
		super.setup();
		setTouchable(Touchable.enabled);
		setInputListener();
	}

	public CGTButton(float x, float y, float relativeWidth, float relativeHeight) {
		this();
		setRelativeX(x);
		setRelativeY(y);
		setRelativeWidth(relativeWidth);
		setRelativeHeight(relativeHeight);
		
	}

	public void setInputListener(){
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void draw(Batch batch, float parentAlpha){
		if(active) {
			if (textureDown != null) {
				batch.draw(textureDown.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
			}
		} else {
			if (textureUp != null) {
				batch.draw(textureUp.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
			}
		}
	}

	public CGTTexture getTextureUp() {
		return textureUp;
	}

	public void setTextureUp(CGTTexture cgtTexture) {
		this.textureUp = cgtTexture;
	}

	public CGTTexture getTextureDown() {
		return textureDown;
	}

	public void setTextureDown(CGTTexture textureDown) {
		this.textureDown = textureDown;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

    public boolean validate() {
        return textureUp != null && super.validate();
    }
}
