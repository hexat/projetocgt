package cgt.lose;

import java.io.Serializable;

import cgt.hud.CGTLabel;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

public class TargetTime implements Lose, Serializable{
	public int timer;
	public CGTLabel label;
	public boolean expired;
	
	public TargetTime(int time, CGTLabel label){
		timer = time;
		this.label = label;
		expired=false;
		label.setText(""+timer);
	}
	
	public TargetTime(int time){
		timer = time;
		this.label = null;
		expired=false;
	}
	
	public CGTLabel getLabel(){
		return label;
	}
	
	public boolean hasLabel(){
		if(label==null)
			return false;
		else
			return true;
	}
	
	public void start(){
		label.getLabelGDX().setText(String.valueOf(timer));
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
	
	public boolean lost(){
		return expired;
	}
	
	public int getTimer(){
		return timer;
	}
}
