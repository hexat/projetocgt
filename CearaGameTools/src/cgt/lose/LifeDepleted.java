package cgt.lose;

import cgt.core.CGTActor;
import cgt.game.LoseCriteria;
import cgt.policy.LosePolicy;

public class LifeDepleted extends LoseCriteria {
    private CGTActor actor;

    public LifeDepleted() {
        actor = null;
    }

    public void start() {
        actor = getWorld().getActor();
    }

    @Override
    public LosePolicy getPolicy() {
        return LosePolicy.ACTOR_DEAD;
    }

    @Override
    public boolean lost() {
        if (actor.getLife() > 0)
            return false;
        else
            return true;
    }

}
