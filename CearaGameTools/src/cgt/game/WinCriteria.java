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

    @Override
    public void remove() {
        if (worldId != null && CGTGame.get().getWorld(worldId) != null) {
            CGTGame.get().getWorld(worldId).removeWinCriteria(this);
        }
        worldId = null;
    }
}
