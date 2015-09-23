package cgt.core;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class CGTAddOn extends CGTGameObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9137532188758222763L;
	private Vector2 positionRelativeToParent;
	
	private CGTGameObject objectA;
	private CGTGameObject objectB;
	
	private boolean active;

	public CGTAddOn(){
		super();
		active = false;
		objectA = null;
		objectB = null;
	}
	
	public CGTAddOn(CGTAddOn clone){
		super(clone);
		active = false;
		positionRelativeToParent = clone.getPositionRelativeToParent().cpy();
	}
	
	/**
	 * @return the positionReLativeToGameObject
	 */
	public Vector2 getPositionRelativeToParent() {
		return positionRelativeToParent;
	}

	/**
	 * @param positionReLativeToGameObject the positionReLativeToGameObject to set
	 */
	public void setPositionRelativeToParent(
			Vector2 position) {
		this.positionRelativeToParent = position;
	}
	
	public void setActive(boolean f) {
//		if (f) {
			setPosition(null);
//		}
		active ^= f;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public CGTAddOn clone() {
		CGTAddOn res = new CGTAddOn(this);
		return res;
	}

	public CGTGameObject getObjectB() {
		return objectB;
	}

	public void setObjectB(CGTGameObject objectB) {
		this.objectB = objectB;
	}

	public CGTGameObject getObjectA() {
		return objectA;
	}

	public void setObjectA(CGTGameObject objectA) {
		this.objectA = objectA;
	}
}
 
