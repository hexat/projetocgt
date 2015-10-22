package cgt.win;

import cgt.core.CGTGameObject;
import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.game.WinCriteria;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import cgt.core.CGTActor;
import cgt.policy.WinPolicy;

public class Survive extends WinCriteria {
	private int timer;
	private boolean timeEnded;
	private WinPolicy policy;

	private CGTActor actor;
	
	public Survive(){
		actor = null;
		timer = 10;
		timeEnded=false;
		policy = WinPolicy.SURVIVE;
	}
	
	public void start() {
		actor = CGTGame.get().getWorld(worldId).getActor();
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

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getTimer() {
		return timer;
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}
}
