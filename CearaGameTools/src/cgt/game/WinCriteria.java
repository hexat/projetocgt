package cgt.game;

import cgt.win.Win;

/**
 * Created by Luan on 28/02/2015.
 */
public abstract class WinCriteria implements Win {
    protected String worldId;

    protected void setWorld(CGTGameWorld world) {
        worldId = world.getId();
    }

    protected CGTGameWorld getWorld() {
        return CGTGame.get().getWorld(worldId);
    }

    @Override
    public void remove() {
        if (worldId != null && getWorld() != null) {
            getWorld().removeWinCriteria(this);
        }
        worldId = null;
    }
}
