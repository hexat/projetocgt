package cgt.win;

import java.io.Serializable;

import com.badlogic.gdx.math.Rectangle;

import cgt.CGTGameWorld;
import cgt.core.CGTActor;
import cgt.hud.CGTLabel;
import cgt.policy.WinPolicy;

public class CompleteCrossing implements Win, Serializable {
	private CGTGameWorld world;
	private Rectangle rectangle;
	private WinPolicy policy; 
	private CGTLabel label;
	
	public CompleteCrossing(CGTGameWorld world, Rectangle rectangle, CGTLabel label){
		this.world = world;
		this.rectangle = rectangle;
		this.label = label;
		label.setText("");
	}
	

	@Override
	public boolean achieved() {
		boolean ganhou = true;
		if (world.getActor().getCollision().overlaps(rectangle)){
			ganhou = true;
		} else{
			ganhou = false;
		}
		int a =(int) ((world.getActor().getPosition().x+ world.getActor().getBounds().width)/(world.getBackground().getTextureGDX().getWidth())*101);
		label.getLabelGDX().setText(a+"");
		return ganhou;
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

	@Override
	public void start() {
		label.getLabelGDX().setText(String.valueOf(world.getActor().getPosition().x/world.getBackground().getTextureGDX().getWidth()));
	}

}
