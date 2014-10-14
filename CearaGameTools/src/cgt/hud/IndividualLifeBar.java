package cgt.hud;

import java.io.Serializable;

import cgt.core.CGTGameObject;

public class IndividualLifeBar extends LifeBar implements Serializable{
	private CGTGameObject owner;
	
	public IndividualLifeBar(CGTGameObject own){
		setMaxLife(own.getLife());
		this.owner = own;
		
	}
	
	public void act(float delta){
		currentLife = owner.getLife();
		lifeRate = currentLife/maxLife;
		if(lifeRate<0)
			lifeRate=0;
	}
	
	public CGTGameObject getOwner() {
		return owner;
	}

	public void setOwner(CGTGameObject owner) {
		this.owner = owner;
	}
}
