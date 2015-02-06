package cgt.game;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.hud.CGTButtonScreen;
import cgt.hud.CGTLabel;
import cgt.screen.CGTImage;
import cgt.screen.CGTWindow;
import cgt.util.CGTTexture;

public class CGTScreen extends CGTWindow implements Serializable{
	private ArrayList<CGTLabel> labels;
	private ArrayList<CGTImage> images;
	private ArrayList<CGTButtonScreen> buttons;
	private CGTTexture background;

    protected CGTScreen() {
        labels = new ArrayList<CGTLabel>();
        images = new ArrayList<CGTImage>();
        buttons = new ArrayList<CGTButtonScreen>();
        background = null;
    }

    protected CGTScreen(CGTTexture texture) {
        this();
        background = texture;
    }

	public ArrayList<CGTLabel> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<CGTLabel> labels) {
		this.labels = labels;
	}

	public ArrayList<CGTImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<CGTImage> images) {
		this.images = images;
	}

	public ArrayList<CGTButtonScreen> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<CGTButtonScreen> buttons) {
		this.buttons = buttons;
	}

	public CGTTexture getBackground() {
		return background;
	}

	public void setBackground(CGTTexture background) {
		this.background = background;
	}
}
