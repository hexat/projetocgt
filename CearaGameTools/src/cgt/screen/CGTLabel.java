package cgt.screen;

import cgt.util.CGTTexture;

import com.badlogic.gdx.math.Rectangle;

public class CGTLabel {

	private Rectangle bounds;
	private String text;
	private CGTTexture background;
	
	public CGTLabel() {
		bounds = new Rectangle(0, 0, 0, 0);
		text = "";
		background = null;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public CGTTexture getBackground() {
		return background;
	}

	public void setBackground(CGTTexture background) {
		this.background = background;
	}

}
