package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.behaviors.Behavior;
import cgt.policy.StatePolicy;
import cgt.unit.LabelID;

public class CGTEnemy extends CGTGameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5889174566567350080L;
	private StatePolicy state;
	private int damage;
	private ArrayList<Behavior> behaviors;
	private boolean block;
	private boolean destroyable;
	private String group;
	private float alpha; //nivel de transparencia
	private boolean vulnerable;
	
	public CGTEnemy(){
		state=StatePolicy.IDLEDOWN;
		damage=0;
		behaviors = new ArrayList<Behavior>();
		block=false;
		destroyable=true;
		group="";
		vulnerable=true;
		alpha = 1;
	}
	
	public CGTEnemy(LabelID id){
		super(id);
		behaviors = new ArrayList<Behavior>(); 
		group = "General";
	}

	public StatePolicy getState() {
		return state;
	}

	public void setState(StatePolicy state) {
		this.state = state;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	
	public ArrayList<Behavior> getBehaviors(){
		return behaviors;
	}
	public void addBehavior(Behavior behavior) {
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
		return super.toString() + "CGTEnemy [state=" + state + ", damage=" + damage
				+ ", behaviors=" + behaviors + ", block=" + block
				+ ", destroyable=" + destroyable + "]";
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
	
}
