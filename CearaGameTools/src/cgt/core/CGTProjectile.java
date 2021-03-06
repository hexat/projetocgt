package cgt.core;

import java.util.ArrayList;
import cgt.policy.StatePolicy;
import cgt.util.ProjectileOrientation;

public class CGTProjectile extends CGTGameObject {
	private int numFiresForOneInput;
	private int damage;
	private int interval;
	private float angle;
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
	
	public void addAmmo(int ammoRecharge){
		addLife(ammoRecharge);
	}
	
	public void ammoDown() {
		setLife(getLife()-1);
	}
	 
	public int getAmmo() {
		return getLife();
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

    public boolean hasOrientation(StatePolicy actorState) {
        for (ProjectileOrientation o : orientations) {
            if (o.getStates().contains(actorState)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeGroup(String group) {
		return groups.remove(group);
	}

    public ProjectileOrientation getOrientation() {
        for (int w = 0; w < getOrientations().size(); w++) {
            if (getOrientations().get(w).getStates().contains(getState())) {
                return getOrientations().get(w);
            }
        }
        return null;
    }

	@Override
	public String toString() {
		return  "CGTProjectile [numFiresForOneInput="
				+ numFiresForOneInput + ", damage=" + damage + ", interval="
				+ interval + ", velocityInitial=" + getSpeed() + ", angle=" + angle
				+ ", labelID=" + getId() + ", orientations="
				+ orientations + "]";
	}

    public boolean containsGroup(String group) {
        return groups.size() == 0 || groups.contains(group);
    }
}
 
