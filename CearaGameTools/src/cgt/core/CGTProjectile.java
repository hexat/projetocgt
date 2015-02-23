package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.unit.LabelID;
import cgt.util.ProjectileOrientation;

import com.badlogic.gdx.math.Vector2;

public class CGTProjectile extends CGTGameObject implements Serializable{
	private int numFiresForOneInput;
	private int damage;
	private int interval;
	private int ammo;
	private int maxAmmo;
	private float angle;
	private CGTGameObject owner;
	private ArrayList<ProjectileOrientation> orientations;
	private ArrayList<String> groups;

	public CGTProjectile(){
		super();
		orientations = new ArrayList<ProjectileOrientation>();
		
		groups = new ArrayList<String>();
		groups.add("General");
	}

    public void setDamage(int damage) {
		this.damage = damage;
	}
	 
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	/**
	 *  
	 * @return interval time projectile will drow in screen
	 */
	public int getInterval() {
		return interval;
	}
	
	public int addAmmo(int ammoRecharge){
		if ((maxAmmo-ammo) <= ammoRecharge){
			int valueRecharge = maxAmmo - ammo;
			ammo = ammo + (maxAmmo-ammo);
			return valueRecharge;
		} else {
			ammo = ammoRecharge;
			return ammoRecharge;
		}
	}
	 
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	
	public void ammoDown() {
		this.ammo -= 1;
	}
	 
	public int getAmmo() {
		return ammo;
	}
	 
	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	
	public CGTGameObject getOwner() {
		return owner;
	}
	
	public void setOwner(CGTGameObject gameObject) {
		this.owner = gameObject;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	 
	public float getAngle() {
		return angle;
	}

	public int getNumFiresForOneInput() {
		return numFiresForOneInput;
	}

	public void setNumFiresForOneInput(int numFiresForOneInput) {
		this.numFiresForOneInput = numFiresForOneInput;
	}

	public int getDamage() {
		return damage;
	}
	
	public ArrayList<ProjectileOrientation> getOrientations() {
		return orientations;
	}

	public void addOrientation(ProjectileOrientation orientation){
		orientations.add(orientation);
	}
	
	public void removeOrientation(int indice){
		orientations.remove(indice);
	}

	public ArrayList<String> getGroups() {
		return groups;
	}

	public void addGroup(String group) {
		if (!groups.contains(group.trim())) {
			groups.add(group);
		}
	}
	
	public boolean removeGroup(String group) {
		return groups.remove(group);
	}

	@Override
	public String toString() {
		return  "CGTProjectile [numFiresForOneInput="
				+ numFiresForOneInput + ", damage=" + damage + ", interval="
				+ interval + ", ammo=" + ammo + ", maxAmmo=" + maxAmmo
				+ ", velocityInitial=" + getSpeed() + ", angle=" + angle
				+ ", labelID=" + getLabel() + ", orientations="
				+ orientations + "]";
	}
}
 
