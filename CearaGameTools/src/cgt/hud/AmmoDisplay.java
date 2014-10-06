package cgt.hud;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import cgt.core.CGTProjectile;
import cgt.util.CGTTexture;

public class AmmoDisplay extends HUDComponent implements Serializable{
	CGTTexture icon;
	Label label;
	CGTProjectile owner;
	
	public void autosize(){
		super.autosize();
		label.setX(this.getX() + getWidth());
		label.setY(this.getY());		
	}
	
	public void act(float delta){
		label.setText("x "+String.valueOf(owner.getAmmo()));
	}
	
	public AmmoDisplay(CGTTexture icon, CGTProjectile projectile, Label label){
		owner = projectile;
		this.icon = icon;
		this.label = label;
	}
	
	public void draw(Batch batch, float parentAlpha){
		batch.draw(icon.getTextureGDX(), getX(), getY(), getWidth(), getHeight());
		label.draw(batch, parentAlpha);
	}
}
