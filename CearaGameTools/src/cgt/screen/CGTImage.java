package cgt.screen;

import cgt.util.CGTTexture;

import com.badlogic.gdx.math.Rectangle;

public class CGTImage {
	private Rectangle bounds;
	private CGTTexture background;
	
	public CGTImage() {
		bounds = new Rectangle();
		background = null;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public CGTTexture getBackground() {
		return background;
	}

	public void setBackground(CGTTexture background) {
		this.background = background;
	}
	
}
