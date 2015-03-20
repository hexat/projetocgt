package cgt.lose;

import java.io.Serializable;

import cgt.game.LoseCriteria;
import cgt.hud.CGTLabel;

import cgt.policy.LosePolicy;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

public class TargetTime extends LoseCriteria {
	public int timer;
	public CGTLabel label;
	public boolean expired;
	
	public TargetTime(){
		timer = 10;
		this.label = new CGTLabel();
        this.label.setText("x");
        this.label.setRelativeHeight(0.05f);
        this.label.setRelativeX(0.45f);
        this.label.setRelativeY(0.9f);

		expired = false;
		label.setText(""+timer);
	}

	public CGTLabel getLabel(){
		return label;
	}

	public void setLabel(CGTLabel label) {
		this.label = label;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public void start(){
		label.getLabelGDX().setText(String.valueOf(timer));
        getWorld().getHUD().add(label);
		float delay = 1; // seconds
		float repete = 1;
		/*
		Timer.schedule(new Timer.Task(){
			@Override
			public void run() {
				expired=true;
			}
		}, timer);*/
		
		
		Timer.schedule(new Timer.Task() {
			int x;
			@Override
			public void run() {
				x = Integer.parseInt(String.valueOf(label.getLabelGDX().getText()));
				x -= 1;
				label.getLabelGDX().setText(String.valueOf(x));
				if (x <= 0){
					expired = true;
					this.cancel();
				}
			}
		}, delay, repete);
		
		
		
		
	}

	@Override
	public LosePolicy getPolicy() {
		return LosePolicy.TARGET_TIME;
	}

	public boolean lost(){
		return expired;
	}
	
	public int getTimer(){
		return timer;
	}
}
