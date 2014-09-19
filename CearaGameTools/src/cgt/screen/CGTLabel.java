package cgt.screen;

public class CGTLabel extends com.badlogic.gdx.scenes.scene2d.ui.Label {
	
	protected float relativeX;
	protected float relativeY;
	protected float relativeWidth;
	protected float relativeHeight;

	public CGTLabel(CharSequence text, LabelStyle style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}
	
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
