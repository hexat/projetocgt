package cgt.core;

import java.io.Serializable;

public class CGTOpposite extends CGTGameObject implements Serializable {
	private boolean block;
	private boolean destroyable;
	private boolean collide;

    public CGTOpposite() {
        this("Opositor");
    }
    public CGTOpposite(CGTOpposite opposite) {
        super(opposite);
        block = opposite.isBlock();
        destroyable = opposite.isDestroyable();
    }

    public CGTOpposite(String label) {
        setId(label);
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
	
	

	public boolean isCollide() {
		return collide;
	}

	public void setCollide(boolean collide) {
		this.collide = collide;
	}

	@Override
	public String toString() {
		return super.toString() + "CGTOpposite [block=" + block + ", destroyable=" + destroyable
				+ "]";
	}
}
 
