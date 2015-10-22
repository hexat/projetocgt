package cgt.win;

import cgt.game.WinCriteria;
import cgt.policy.WinPolicy;

/**
 * Created by luanjames on 09/03/15.
 */
public class GetAllBonus extends WinCriteria {

    @Override
    public boolean achieved() {
    	
        return getWorld().getBonus().size() == 0;
    }

    @Override
    public WinPolicy getPolicy() {
        return WinPolicy.GET_ALL_BONUS;
    }

    @Override
    public void start() {

    }
}
