package cgt.screen;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.util.CGTTexture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class CGTDialog extends Actor implements Serializable{
	private ArrayList<CGTButtonScreen> screenSwitchButtons;
	private CGTButton closeButton;
	private boolean active;
	private CGTTexture rightBottomCorner;
	private CGTTexture horizontalBorderTexture;
	private CGTTexture window;
	private float relativeWidth;
	private float relativeHeight;
	private float relativeX;
	private float relativeY;
	private SequenceAction sequence;

	public CGTDialog(){
		screenSwitchButtons = new ArrayList<CGTButtonScreen>();
		setTouchable(Touchable.enabled);
	}

	public void autosize(){
		setWidth(getStage().getWidth()*relativeWidth);
		setHeight(getStage().getHeight()*relativeHeight);

		setX(getStage().getWidth()*relativeX);
		setY(getStage().getHeight()*relativeY);
		if (closeButton != null){
			closeButton.setWidth(this.getWidth()*closeButton.getRelativeWidth());
			closeButton.setHeight(this.getHeight()*closeButton.getRelativeHeight());
			closeButton.setX(this.getX()+ this.getWidth()*closeButton.getRelativeX());
			closeButton.setY(this.getY()+ this.getHeight()*closeButton.getRelativeY());
		}
		
		for(int i = 0; i < screenSwitchButtons.size(); i++){
			screenSwitchButtons.get(i).setWidth(this.getWidth()*screenSwitchButtons.get(i).getRelativeWidth());
			screenSwitchButtons.get(i).setHeight(this.getHeight()*screenSwitchButtons.get(i).getRelativeHeight());
			screenSwitchButtons.get(i).setX(this.getX()+this.getWidth()*screenSwitchButtons.get(i).getRelativeX());
			screenSwitchButtons.get(i).setY(this.getY()+this.getHeight()*screenSwitchButtons.get(i).getRelativeY());
		}
		
		
	}

	public void act(float delta){
		super.act(delta);
	}

	@Override
	public void addAction(Action action) {
		SequenceAction sequence = (SequenceAction) action;
		SequenceAction sequenceDialog = new SequenceAction();
		
		for(int i = 0; i < screenSwitchButtons.size(); i++){
			SequenceAction sequenceButtons = new SequenceAction();
			
			for(int j = 0; j < sequence.getActions().size; j++){
				MoveToAction actionMove = (MoveToAction) sequence.getActions().get(j);
				MoveToAction actionMoveButtons = new MoveToAction();
				actionMoveButtons.setPosition(screenSwitchButtons.get(i).getX()-this.getX()+ (getStage().getWidth()*actionMove.getX()),screenSwitchButtons.get(i).getY()-this.getY()+ (getStage().getHeight()*actionMove.getY()));
				actionMoveButtons.setDuration(actionMove.getDuration());
				sequenceButtons.addAction(actionMoveButtons);
				MoveToAction moveDialog = new MoveToAction();
				moveDialog.setPosition(getStage().getWidth()*actionMove.getX(),getStage().getHeight()*actionMove.getY() );
				moveDialog.setDuration(actionMove.getDuration());
				sequenceDialog.addAction(moveDialog);
			}
			screenSwitchButtons.get(i).addAction(sequenceButtons);
		}
		
		if(closeButton != null){
			SequenceAction sequenceButtons = new SequenceAction();
			
			for(int j = 0; j < sequence.getActions().size; j++){
				MoveToAction actionMove = (MoveToAction) sequence.getActions().get(j);
				MoveToAction actionMoveButtons = new MoveToAction();
				actionMoveButtons.setPosition(closeButton.getX()-this.getX()+ (getStage().getWidth()*actionMove.getX()),closeButton.getY()-this.getY()+ (getStage().getHeight()*actionMove.getY()));
				actionMoveButtons.setDuration(actionMove.getDuration());
				sequenceButtons.addAction(actionMoveButtons);
			}
			closeButton.addAction(sequenceButtons);
		}
		
		
		
		super.addAction(sequenceDialog);
	}

	public void draw(Batch batch, float parentAlpha){
		if(isActive()){
			if(window != null){
				batch.draw(window.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
			}
			drawBorders(batch);
			if (closeButton != null){
				closeButton.draw(batch, parentAlpha);
			}
		}
	}
	
	public void drawBorders(Batch batch){
		if(horizontalBorderTexture != null){
			float borderHeight = horizontalBorderTexture.getTextureRegion().getRegionHeight();
			float borderWidth = horizontalBorderTexture.getTextureRegion().getRegionWidth();
			float borderX = this.getX();
			float borderY = this.getY();
			float upperBorderY = borderY + this.getHeight()-borderHeight;
			
			//Desenho das linhas horizontais
			while(borderX+borderWidth<this.getX() + this.getWidth()){
				batch.draw(horizontalBorderTexture.getTextureRegion(), borderX, borderY, borderWidth, borderHeight);
				batch.draw(horizontalBorderTexture.getTextureRegion(), borderX, upperBorderY, borderWidth, borderHeight);
				borderX+=borderWidth;
			}
			
			borderX = this.getX()+borderHeight;
			borderY = this.getY();
			float rightBorderX = borderX + this.getWidth()-borderHeight;
			
			//Desenha linhas verticais
			while(borderY+borderWidth<this.getY() + this.getHeight()){
				batch.draw(horizontalBorderTexture.getTextureRegion(), borderX, borderY, 0, 0, borderWidth, borderHeight, 1, 1, 90);
				batch.draw(horizontalBorderTexture.getTextureRegion(), rightBorderX, borderY, 0, 0, borderWidth, borderHeight, 1, 1, 90);
				borderY+=borderHeight;
			}
		}
        if (rightBottomCorner != null) {
            float cornerX = this.getX() + this.getWidth() - rightBottomCorner.getTextureRegion().getRegionWidth();
            float cornerY = this.getY();
            float cornerWidth = rightBottomCorner.getTextureRegion().getRegionWidth();
            float cornerHeight = rightBottomCorner.getTextureRegion().getRegionHeight();

            batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, 0, 0, cornerWidth, cornerHeight, 1, 1, 0); //direito de baixo

            cornerY = this.getY() + this.getHeight() - cornerHeight;
            batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, cornerWidth / 2, cornerHeight / 2, cornerWidth, cornerHeight, 1, 1, 90); // direito de cima

            cornerX = this.getX();
            batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, cornerWidth / 2, cornerHeight / 2, cornerWidth, cornerHeight, 1, 1, 180); //esquerdo de cima

            cornerY = this.getY();
            batch.draw(rightBottomCorner.getTextureRegion(), cornerX, cornerY, cornerWidth / 2, cornerHeight / 2, cornerWidth, cornerHeight, 1, 1, 270); //esquerdo de baixo
        }
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CGTTexture getWindow() {
		return window;
	}

	public void setWindow(CGTTexture window) {
		this.window = window;
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

	public ArrayList<CGTButtonScreen> getButtons() {
		return screenSwitchButtons;
	}

	public void addButton(CGTButtonScreen button){
		screenSwitchButtons.add(button);
	}

	public CGTButton getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(CGTButton closeButton) {
		this.closeButton = closeButton;
		//addActor(closeButton);
	}

	public CGTTexture getRightBottomCorner() {
		return rightBottomCorner;
	}

	public void setRightBottomCorner(CGTTexture rightBottomCorner) {
		this.rightBottomCorner = rightBottomCorner;
	}

	public CGTTexture getHorizontalBorderTexture() {
		return horizontalBorderTexture;
	}

	public void setHorizontalBorderTexture(CGTTexture horizontalBorderTexture) {
		this.horizontalBorderTexture = horizontalBorderTexture;
	}

	public SequenceAction getSequence() {
		return sequence;
	}


	public void setSequence(SequenceAction sequence) {
		this.sequence = sequence;
	}

    public boolean validate() {
        return rightBottomCorner != null && relativeHeight > 0 && relativeWidth > 0;
    }
}