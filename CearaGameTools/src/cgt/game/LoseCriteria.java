package cgt.game;

import cgt.lose.Lose;

/**
 * Created by Luan on 28/02/2015.
 */
public abstract class LoseCriteria implements Lose {
    protected String worldId;

    protected void setWorld(CGTGameWorld world) {
        this.worldId = world.getId();
    }

    protected CGTGameWorld getWorld() {
        return CGTGame.get().getWorld(worldId);
    }

    public void remove() {
        if (worldId != null && CGTGame.get().getWorld(worldId) != null) {
            CGTGame.get().getWorld(worldId).removeLoseCriteria(this);
        }
        worldId = null;
    }
}
