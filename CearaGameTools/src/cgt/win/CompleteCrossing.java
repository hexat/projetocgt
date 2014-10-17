package cgt.win;

import java.io.Serializable;

import cgt.CGTGameWorld;
import cgt.core.CGTActor;
import cgt.policy.WinPolicy;

public class CompleteCrossing implements Win, Serializable {
	private CGTActor actor;
	private CGTGameWorld world;
	private WinPolicy policy;
	
	public CompleteCrossing(CGTActor actor, CGTGameWorld world){
		this.actor = actor;
		this.world = world;
	}
	

	@Override
	public boolean achieved() {
		boolean ganhou = true;
		
		if (world.getBackground().getTextureGDX().getWidth() == world.getActor().getPosition().x){
			return true;
		} else{
			return false;
		}
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

	@Override
	public void start() {
		
	}

}
