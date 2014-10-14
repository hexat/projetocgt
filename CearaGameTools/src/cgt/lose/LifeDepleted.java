package cgt.lose;

import java.io.Serializable;

import cgt.core.CGTActor;

public class LifeDepleted implements Lose, Serializable{
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
