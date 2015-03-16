package cgt.hud;

import java.io.Serializable;

import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import com.badlogic.gdx.graphics.g2d.Batch;

import cgt.core.CGTProjectile;
import cgt.util.CGTTexture;
//TODO Fazer igual
public class AmmoDisplay extends HUDComponent implements Serializable{
	private CGTTexture icon;
	private CGTLabel label;
	private CGTProjectile owner;

	private String ownerId;
	private String worldId;

	public void setup(){
		super.setup();
		owner = getWorld().findProjectile(ownerId);

		label.getLabelGDX().setX(this.getX() + getWidth());
		label.getLabelGDX().setY(this.getY());		
	}
	
	public void act(float delta){
		System.out.println(owner);
		label.getLabelGDX().setText("x "+String.valueOf(owner.getAmmo()));
	}

	public AmmoDisplay() {
		worldId = null;
		ownerId = null;
		owner = null;
		this.icon = null;
		this.label = new CGTLabel();
	}

	public AmmoDisplay(String worldId){
		this();
		this.worldId = worldId;
	}

	public void setIcon(CGTTexture icon) {
		this.icon = icon;
	}

	public void setOwner(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerLabel() {
		return ownerId;
	}

	public void setLabel(CGTLabel label) {
		this.label = label;
	}

	public CGTLabel getLabel() {
		return label;
	}

	public CGTTexture getIcon() {
		return icon;
	}

	public void draw(Batch batch, float parentAlpha){
		batch.draw(icon.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
		label.getLabelGDX().draw(batch, parentAlpha);
	}

	@Override
	public boolean validate() {
		return super.validate() && icon != null && label != null;
	}

	public CGTGameWorld getWorld() {
		return CGTGame.get().getWorld(worldId);
	}

	protected void setWorld(String id) {
		this.worldId = id;
	}
}
