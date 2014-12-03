package cgt.hud;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.core.CGTEnemy;

public class EnemyGroupLifeBar extends LifeBar implements Serializable{
	private ArrayList<CGTEnemy> enemiesList;
	
	public EnemyGroupLifeBar(ArrayList<CGTEnemy> enemies){
		enemiesList = enemies;
		int maxLife = 0;
		
		for(CGTEnemy enemy : enemies){
			if(enemy.isDestroyable())
				maxLife++;
		}
		setMaxLife(maxLife);
	}
	
	public void act(float delta){
		int enemiesAlive=0;
		for(CGTEnemy enemy : enemiesList){
			if(enemy.isDestroyable() && enemy.getLife()>0)
				enemiesAlive++;
		}
		currentLife = enemiesAlive;
		lifeRate = currentLife/maxLife;
		if(lifeRate<0)
			lifeRate=0;
	}

}
