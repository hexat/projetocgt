package cgt.win;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import cgt.core.CGTActor;
import cgt.policy.WinPolicy;

public class Survive implements Win{
	private CGTActor actor;
	private int timer;
	private boolean timeEnded;
	
	public Survive(CGTActor actor, int time){
		this.actor = actor;
		timer = time;
		timeEnded=false;
	}
	
	public void start(){
		Timer.schedule(new Task() {
			@Override
			public void run() {
				timeEnded=true;
			}
		}, timer);
	}

	@Override
	public boolean achieved() {
		if(actor.getLife()>0 && timeEnded)
			return true;
		else
			return false;
	}

	@Override
	public WinPolicy getPolicy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
