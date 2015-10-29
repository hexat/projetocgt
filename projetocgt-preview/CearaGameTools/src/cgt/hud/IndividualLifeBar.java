package cgt.hud;

import java.io.Serializable;

import cgt.core.CGTGameObject;
import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.game.LifeBar;

public class IndividualLifeBar extends LifeBar {
	private String ownerId;

	private CGTGameObject object;

    public IndividualLifeBar(){
        super();
    }

    public IndividualLifeBar(String id){
        this();
		setId(id);
    }

	public void act(float delta){
		if (object != null) {
			currentLife = object.getLife();
			lifeRate = currentLife / maxLife;
			if (lifeRate < 0)
				lifeRate = 0;
		}
	}

	@Override
	public void setup() {
		super.setup();
		if (object == null) {
			object = getWorld().getObjectByLabel(ownerId);
			setMaxLife(object.getMaxLife());
		}
	}

	public void setOwner(String label) {
		this.ownerId = label;
	}

	@Override
	public boolean validate() {
		return super.validate() && ownerId != null && getWorld() != null &&
				getWorld().getObjectByLabel(ownerId) != null;
	}

	public String getOwnerId() {
		return ownerId;
	}
}
