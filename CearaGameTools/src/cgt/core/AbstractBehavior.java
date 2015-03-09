package cgt.core;

import cgt.behaviors.Behavior;
import cgt.game.CGTGame;

/**
 * Created by luanjames on 09/03/15.
 */
public abstract class AbstractBehavior implements Behavior {
    protected String ownerId;
    private CGTEnemy enemy = null;

    protected void remove() {
        getOwner().removeBehavior(this);
        enemy = null;
    }

    protected void setOwner(CGTEnemy enemy) {
        ownerId = enemy.getId();
    }

    public CGTEnemy getOwner() {
        if (enemy == null) {
            enemy = (CGTEnemy) CGTGame.get().findObject(ownerId);
        }
        return enemy;
    }
}
