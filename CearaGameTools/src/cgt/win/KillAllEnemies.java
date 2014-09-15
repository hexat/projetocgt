package cgt.win;

import java.util.ArrayList;

import cgt.core.CGTEnemy;
import cgt.policy.WinPolicy;

public class KillAllEnemies implements Win{
	private ArrayList<CGTEnemy> enemies;
	private WinPolicy policy;

	public KillAllEnemies(ArrayList<CGTEnemy> enemies){
		this.enemies = enemies;
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

	public void setPolicy(WinPolicy policy) {
		this.policy = policy;
	}

	@Override
	public WinPolicy getPolicy() {
		return policy;
	}

}
