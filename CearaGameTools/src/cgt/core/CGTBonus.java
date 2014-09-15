package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.policy.BonusPolicy;
import cgt.unit.LabelID;

public class CGTBonus extends CGTGameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4497239857672964461L;
	private int score;
	private boolean destroyable;
	private int lifetime;
	private ArrayList<BonusPolicy> policies;

	public CGTBonus(LabelID labelID){
		super(labelID);
		policies = new ArrayList<BonusPolicy>();
	}
	
	public CGTBonus(){
		policies = new ArrayList<BonusPolicy>();
	}
	
	public void setScore(int value) {
		this.score = value;
	}
	 
	public int getScore() {
		return score;
	}
 
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	 
	public boolean isDestroyable() {
		return destroyable;
	}
	 
	public void setLifeTime(int time) {
		this.lifetime = time;
	}
	 
	public float getLifeTime() {
		return lifetime;
	}
	
	public ArrayList<BonusPolicy> getPolicies(){
		return policies;
	}
	public void addPolicy(BonusPolicy policy) {
		policies.add(policy);
	}
	 
	public boolean hasPolicy(BonusPolicy policy) {
		return policies.contains(policy);
	}
	 
	public boolean removePolicy(BonusPolicy policy) {
		return policies.remove(policy);
	}
	 
	public BonusPolicy removePolicy(int index) {
		return policies.remove(index);
	}


	@Override
	public String toString() {
		return super.toString() + "\nCGTBonus [score="
				+ score + ", destroyable=" + destroyable + ", lifetime="
				+ lifetime + ", policies=" + policies
				+ "]";
	}
	
}
 
