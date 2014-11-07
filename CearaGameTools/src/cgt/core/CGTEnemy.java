package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import cgt.behaviors.Behavior;
import cgt.policy.DirectionPolicy;
import cgt.policy.StatePolicy;
import cgt.unit.LabelID;

public class CGTEnemy extends CGTGameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5889174566567350080L;
	private int damage;

	//TODO Criar uma lista de behaviors onde o pai ficara disponivel para todos
	private ArrayList<Behavior> behaviors;
	private boolean block;
	private boolean destroyable;
	private String group;
	private float alpha; //nivel de transparencia
	private boolean vulnerable;
	
	public CGTEnemy(){
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
	
//	@Override
//	public void setPosition(Vector2 position) {
//		for(int i = 0; i < this.behaviors.size(); i++){
//			if (this.behaviors.get(i).equals(DirectionPolicy.TWO_POINTS_DIRECTION)){
//				cgt.behaviors.Direction direction = (cgt.behaviors.Direction) this.behaviors.get(i);
//				Vector2 initialPosition = new Vector2(direction.getMinX(), direction.getMinY());
//				this.setPosition(initialPosition);
//			} 
//		}
//		if (this.getPosition() == null){
//			this.setPosition(position);
//		}
//	}
	
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
