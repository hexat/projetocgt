package cgt.lose;

import cgt.screen.CGTLabel;

import com.badlogic.gdx.utils.Timer;

public class TargetTime implements Lose{
	public int timer;
	public CGTLabel label;
	public boolean expired;
	
	public TargetTime(int time, CGTLabel label){
		timer = time;
		this.label = label;
		expired=false;
	}
	
	public void start(){
		label.setText(String.valueOf(timer));
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
				x = Integer.parseInt(String.valueOf(label.getText()));
				x -= 1;
				label.setText(String.valueOf(x));
				System.out.println(x);
				if (x <= 0){
					expired = true;
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
