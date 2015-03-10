package cgt.hud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cgt.core.CGTEnemy;
import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.game.LifeBar;

public class EnemyGroupLifeBar extends LifeBar {
	private List<CGTEnemy> enemiesList; //used in gdx mode

	private List<String> enemiesIds;

	public EnemyGroupLifeBar() {
		this.worldId = null;
		enemiesList = null;
		enemiesIds = new ArrayList<String>();
	}

	@Override
	public void setup() {
		super.setup();
		for (String s : enemiesIds) {
			enemiesList.add(getWorld().findEnemy(s));
		}
	}

	public void act(float delta){
		int enemiesAlive=0;
		System.out.println(enemiesList);
		for(CGTEnemy enemy : enemiesList){
			if(enemy.isDestroyable() && enemy.getLife()>0)
				enemiesAlive++;
		}
		currentLife = enemiesAlive;
		lifeRate = currentLife/maxLife;
		if(lifeRate<0)
			lifeRate=0;
	}

	@Override
	public boolean validate() {
		return super.validate() && worldId != null && getWorld() != null;
	}

	public boolean removeEnemy(String id) {
		return enemiesIds.remove(id);
	}

	public boolean contains(String label) {
		return enemiesIds.contains(label);
	}

	public void addEnemy(String id) {
		if (!enemiesIds.contains(id)) {
			enemiesIds.add(id);
		}
	}
}
