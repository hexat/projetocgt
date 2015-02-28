package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import cgt.behaviors.Behavior;
import cgt.policy.DirectionPolicy;
import cgt.policy.StatePolicy;
import cgt.unit.LabelID;

public class CGTEnemy extends CGTOpposite implements Serializable {
	private int damage;
	private ArrayList<Behavior> behaviors;
	private String group;
	private float alpha; //nivel de transparencia
	private boolean vulnerable;
	private float timeToRecovery;
	private boolean attacking;

	public CGTEnemy() {
		this("Inimigo");
	}

    public CGTEnemy(String label) {
		super(label);
		damage=0;
		behaviors = new ArrayList<Behavior>();
		group="";
		vulnerable=true;
		alpha = 1;
		attacking = false;
		timeToRecovery = 0;
    }

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getBehaviorsSize(){
		return behaviors.size();
	}
	
	public Behavior getBehavior(int index) {
		return behaviors.get(index);
	}
	
	//TODO implementar singleton World 
	public void addBehavior(Behavior behavior) {
		if(behavior.getBehaviorPolicy().equals("TWO_POINTS_DIRECTION")){
			cgt.behaviors.Direction direction = (cgt.behaviors.Direction) behavior;
			direction.setOwner(this);
			this.setPosition(direction.getInitialPosition().cpy());
		}
		behaviors.add(behavior);
	}

	public void removeBehavior(Behavior behavior) {
		behaviors.remove(behavior);
	}
	
	public void removeBehavior(int index){
		behaviors.remove(index);
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return super.toString() + "CGTEnemy [damage=" + damage
				+ ", behaviors=" + behaviors + ", block=" + "]";
	}

	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

	public float getTimeToRecovery() {
		return timeToRecovery;
	}

	public void setTimeToRecovery(float timeToRecovery) {
		this.timeToRecovery = timeToRecovery;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	
	
}
