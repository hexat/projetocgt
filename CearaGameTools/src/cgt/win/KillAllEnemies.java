package cgt.win;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.core.CGTEnemy;
import cgt.policy.WinPolicy;

public class KillAllEnemies implements Win, Serializable{
	private ArrayList<CGTEnemy> enemies;
	private WinPolicy policy;

	public KillAllEnemies(ArrayList<CGTEnemy> enemies){
		this.enemies = enemies;
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
		
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

}
