package cgt.util;

import cgt.hud.CGTButton;
import cgt.hud.CGTControllerButton;
import cgt.hud.HUDComponent;
import cgt.policy.InputPolicy;

public class ButtonPad extends HUDComponent {
	private CGTButton buttonBase;
	private CGTControllerButton buttonUp;
	private CGTControllerButton buttonDown;
	private CGTControllerButton buttonLeft;
	private CGTControllerButton buttonRight;
	private float tamanho;
	
	
	public ButtonPad(float tamanho){		
		
		
		
		buttonBase = new CGTButton();
		CGTTexture textureUp = new CGTTexture("data/buttons/base.png");
		buttonBase.setTextureUp(textureUp);
		CGTTexture textureDown = new CGTTexture("data/buttons/base.png");
		buttonBase.setTextureDown(textureDown);
		buttonBase.setRelativeX(0*tamanho);
		buttonBase.setRelativeY(0*tamanho);
		buttonBase.setRelativeWidth(0.22f*tamanho);
		buttonBase.setRelativeHeight(0.29f*tamanho);
		buttonBase.setBounds(0*tamanho, 0*tamanho, (textureUp.getWidth() / 3)*tamanho, (textureUp.getHeight() / 3)*tamanho);

		buttonUp = new CGTControllerButton();
		buttonUp.setInput(InputPolicy.BTN_UP);

		textureUp = new CGTTexture("data/buttons/bt_up_up.png");
		buttonUp.setTextureUp(textureUp);

		textureDown = new CGTTexture("data/buttons/bt_up_press.png");
		buttonUp.setTextureDown(textureDown);

		buttonUp.setRelativeX(0.095f*tamanho);
		buttonUp.setRelativeY(0.145f*tamanho);
		buttonUp.setRelativeWidth(0.1f*tamanho);
		buttonUp.setRelativeHeight(0.1f*tamanho);
		buttonUp.setBounds((137 / 3)*tamanho, (184.7f / 3)*tamanho, (textureUp.getWidth() / 3)*tamanho, (textureUp.getHeight() / 3)*tamanho);

		buttonDown = new CGTControllerButton();
		buttonDown.setInput(InputPolicy.BTN_DOWN);

		textureUp = new CGTTexture("data/buttons/bt_down_up.png");
		buttonDown.setTextureUp(textureUp);
		textureDown = new CGTTexture("data/buttons/bt_down_press.png");
		buttonDown.setTextureDown(textureDown);

		buttonDown.setRelativeX(0.095f*tamanho);
		buttonDown.setRelativeY(0.028f*tamanho);
		buttonDown.setRelativeWidth(0.1f*tamanho);
		buttonDown.setRelativeHeight(0.1f*tamanho);
		buttonDown.setBounds((137 / 3)*tamanho, (36 / 3)*tamanho, (textureUp.getWidth() / 3)*tamanho, (textureUp.getHeight() / 3)*tamanho);

		buttonLeft = new CGTControllerButton();
		buttonLeft.setInput(InputPolicy.BTN_LEFT);

		textureUp = new CGTTexture("data/buttons/bt_left_up.png");
		buttonLeft.setTextureUp(textureUp);

		textureDown = new CGTTexture("data/buttons/bt_left_press.png");
		buttonLeft.setTextureDown(textureDown);

		buttonLeft.setRelativeX(0.048f*tamanho);
		buttonLeft.setRelativeY(0.09f*tamanho);
		buttonLeft.setRelativeWidth(0.1f*tamanho);
		buttonLeft.setRelativeHeight(0.1f*tamanho);
		buttonLeft.setBounds((64 / 3)*tamanho, (126 / 3)*tamanho, (textureUp.getWidth() / 3)*tamanho, (textureUp.getHeight() / 3)*tamanho);

		buttonRight = new CGTControllerButton();
		buttonRight.setInput(InputPolicy.BTN_RIGHT);

		textureUp = new CGTTexture("data/buttons/bt_right_up.png");
		buttonRight.setTextureUp(textureUp);
		textureDown = new CGTTexture("data/buttons/bt_right_press.png");
		buttonRight.setTextureDown(textureDown);

		buttonRight.setRelativeX(0.142f*tamanho);
		buttonRight.setRelativeY(0.09f*tamanho);
		buttonRight.setRelativeWidth(0.1f*tamanho);
		buttonRight.setRelativeHeight(0.1f*tamanho);
		buttonRight.setBounds((183 / 3)*tamanho, (126 / 3)*tamanho, (textureUp.getWidth() / 3)*tamanho, (textureUp.getHeight() / 3)*tamanho);		
		

	}


	public CGTButton getButtonBase() {
		return buttonBase;
	}


	public void setButtonBase(CGTButton buttonBase) {
		this.buttonBase = buttonBase;
	}


	public CGTControllerButton getButtonUp() {
		return buttonUp;
	}


	public void setButtonUp(CGTControllerButton buttonUp) {
		this.buttonUp = buttonUp;
	}


	public CGTControllerButton getButtonDown() {
		return buttonDown;
	}


	public void setButtonDown(CGTControllerButton buttonDown) {
		this.buttonDown = buttonDown;
	}


	public CGTControllerButton getButtonLeft() {
		return buttonLeft;
	}


	public void setButtonLeft(CGTControllerButton buttonLeft) {
		this.buttonLeft = buttonLeft;
	}


	public CGTControllerButton getButtonRight() {
		return buttonRight;
	}


	public void setButtonRight(CGTControllerButton buttonRight) {
		this.buttonRight = buttonRight;
	}
	
	
	
}
