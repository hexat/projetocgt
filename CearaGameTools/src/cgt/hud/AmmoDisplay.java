package cgt.hud;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import cgt.core.CGTProjectile;
import cgt.util.CGTTexture;

public class AmmoDisplay extends HUDComponent implements Serializable{
	CGTTexture icon;
	CGTLabel label;
	CGTProjectile owner;
	
	public void autosize(){
		super.autosize();
		label.getLabelGDX().setX(this.getX() + getWidth());
		label.getLabelGDX().setY(this.getY());		
	}
	
	public void act(float delta){
		label.getLabelGDX().setText("x "+String.valueOf(owner.getAmmo()));
	}
	
	public AmmoDisplay(CGTTexture icon, CGTProjectile projectile, CGTLabel label){
		owner = projectile;
		this.icon = icon;
		this.label = label;
	}
	
	public void draw(Batch batch, float parentAlpha){
		batch.draw(icon.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
		label.getLabelGDX().draw(batch, parentAlpha);
	}
}
