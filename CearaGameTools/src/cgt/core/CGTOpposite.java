package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.behaviors.Behavior;
import cgt.unit.LabelID;

public class CGTOpposite extends CGTGameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4153583529054815594L;
	private boolean block;
	private boolean destroyable;
	private ArrayList<Behavior> behaviors;
	private String label;
	
	
	public CGTOpposite(){
		super();
		label = null;
		behaviors = new ArrayList<Behavior>();
	}
	
	public CGTOpposite(LabelID id){
		super(id);
		behaviors = new ArrayList<Behavior>();
	}
	
	public void setBlock(boolean block) {
		this.block = block;
	}
	 
	public boolean isBlock() {
		return block;
	}
	 
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	 
	public boolean isDestroyable() {
		return destroyable;
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

	@Override
	public String toString() {
		return super.toString() + "CGTOpposite [block=" + block + ", destroyable=" + destroyable
				+ ", behaviors=" + behaviors + "]";
	}

	public void setLabel(String string) {
		this.label = string;
	}
	
	public String getLabel() {
		return label;
	}
}
 
