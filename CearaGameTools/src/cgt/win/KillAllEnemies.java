package cgt.win;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.core.CGTEnemy;
import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.game.WinCriteria;
import cgt.policy.WinPolicy;
import cgt.win.Win;

public class KillAllEnemies extends WinCriteria {
	private ArrayList<CGTEnemy> enemies;
	private WinPolicy policy;

	public KillAllEnemies(){
		enemies = null;
		policy = WinPolicy.KILL_ENEMIES;
	}
	
	@Override
	public boolean achieved(){
		boolean ganhou = true;

		for (int index = 0; index < enemies.size() && ganhou; index++) {
			if (enemies.get(index).isDestroyable()
					&& enemies.get(index).getLife() > 0)
				ganhou = false;
		}
		return ganhou;
		
	}
	
	public void start(){
		enemies = CGTGame.get().getWorld(worldId).getEnemies();
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

}
