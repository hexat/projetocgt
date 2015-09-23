package cgt.win;

import java.io.Serializable;

import cgt.game.WinCriteria;
import cgt.win.Win;
import com.badlogic.gdx.math.Rectangle;

import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.hud.CGTLabel;
import cgt.policy.WinPolicy;

public class CompleteCrossing extends WinCriteria {
	private Rectangle rectangle;
	private WinPolicy policy; 
	private CGTLabel label;

    // in gdx mode
    private CGTGameWorld world;

	public CompleteCrossing(){
		this.worldId = null;
        world = null;
		this.rectangle = new Rectangle();
		this.label = new CGTLabel();
        policy = WinPolicy.COMPLETE_CROSSING;
	}

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
	public boolean achieved() {
		boolean win = false;
        if (world != null) {
            if (world.getActor().getCollision().overlaps(rectangle)) {
                win = true;
            } else {
                win = false;
            }
            int a = (int) ((world.getActor().getPosition().x + world.getActor().getBounds().width) / (world.getBackground().getTextureGDX().getWidth()) * 101);
            label.getLabelGDX().setText(a + "");
        }
		return win;
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

    @Override
    public void remove() {
        super.remove();
        world = null;
    }

    public CGTGameWorld getWorld() {
        if (world == null) {
            world = CGTGame.get().getWorld(worldId);
        }
        return world;
    }
}
