package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.behaviors.Behavior;
import cgt.unit.LabelID;

public class CGTOpposite extends CGTGameObject implements Serializable {
	private boolean block;
	private boolean destroyable;

    public CGTOpposite(String label) {
        setLabel(label);
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

	@Override
	public String toString() {
		return super.toString() + "CGTOpposite [block=" + block + ", destroyable=" + destroyable
				+ "]";
	}
}
 
