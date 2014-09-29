package cgt.HUD;

import cgt.policy.InputPolicy;

public class CGTControllerButton extends CGTButton{
	private InputPolicy input; 
	
	public InputPolicy getInput() {
		return input;
	}

	public void setInput(InputPolicy input) {
		this.input = input;
	}
}
