package cgt.win;

import java.io.Serializable;

import com.badlogic.gdx.math.Rectangle;

import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.hud.CGTLabel;
import cgt.policy.WinPolicy;

public class CompleteCrossing implements Win, Serializable {
    private CGTGameWorld world;
    private String worldId;
	private Rectangle rectangle;
	private WinPolicy policy; 
	private CGTLabel label;
	
	public CompleteCrossing(String worldId, Rectangle rectangle, CGTLabel label){
		this.worldId = worldId;
        world = null;
		this.rectangle = rectangle;
		this.label = label;
		label.setText("");
	}
	

	@Override
	public boolean achieved() {
		boolean ganhou = false;
        if (getWorld() != null) {
            if (world.getActor().getCollision().overlaps(rectangle)) {
                ganhou = true;
            } else {
                ganhou = false;
            }
            int a = (int) ((getWorld().getActor().getPosition().x + getWorld().getActor().getBounds().width) / (getWorld().getBackground().getTextureGDX().getWidth()) * 101);
            label.getLabelGDX().setText(a + "");
        }
		return ganhou;
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

	@Override
	public void start() {
        if (getWorld() != null) {
            label.getLabelGDX().setText(String.valueOf(world.getActor().getPosition().x / world.getBackground().getTextureGDX().getWidth()));
        }
	}

    public CGTGameWorld getWorld() {
        if (world == null) {
            world = CGTGame.get().getWorld(worldId);
        }
        return world;
    }
}
