package cgt.lose;

import cgt.core.CGTActor;

public class LifeDepleted implements Lose{
	private CGTActor actor;
	
	public LifeDepleted(CGTActor actor){
		this.actor = actor;
	}
	
	public void start(){
		
	}
	
	@Override
	public boolean lost() {
		if(actor.getLife()>0)
			return false;
		else
			return true;
	}

}
