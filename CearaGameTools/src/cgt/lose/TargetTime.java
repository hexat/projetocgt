package cgt.lose;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class TargetTime implements Lose{
	public int timer;
	public boolean expired;
	
	public TargetTime(int time){
		timer = time;
		expired=false;
	}
	
	public void start(){
		Timer.schedule(new Task() {
			@Override
			public void run() {
				expired=true;
			}
		}, timer);
	}
	
	public boolean lost(){
		return expired;
	}
}
