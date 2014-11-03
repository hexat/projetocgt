package cgt.win;

import java.io.Serializable;

import com.badlogic.gdx.math.Rectangle;

import cgt.CGTGameWorld;
import cgt.core.CGTActor;
import cgt.policy.WinPolicy;

public class CompleteCrossing implements Win, Serializable {
	private CGTGameWorld world;
	private Rectangle rectangle;
	private WinPolicy policy;
	
	public CompleteCrossing(CGTGameWorld world, Rectangle rectangle){
		this.world = world;
		this.rectangle = rectangle;
	}
	

	@Override
	public boolean achieved() {
		boolean ganhou = true;
		if (world.getActor().getCollision().overlaps(rectangle)){
			ganhou = true;
		} else{
			ganhou = false;
		}
		return ganhou;
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

	@Override
	public void start() {
		
	}

}
