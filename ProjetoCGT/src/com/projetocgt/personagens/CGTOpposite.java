package com.projetocgt.personagens;

public class CGTOpposite {
	//private AIPolicy move;
	private boolean block;
	private boolean destroyable;
	private int damage;
	private CGTProjectile fire;
	//private ArrayList behaviors;
	/**
	 * @return the block
	 */
	public boolean isBlock() {
		return block;
	}
	/**
	 * @param block the block to set
	 */
	public void setBlock(boolean block) {
		this.block = block;
	}
	/**
	 * @return the destroyable
	 */
	public boolean isDestroyable() {
		return destroyable;
	}
	/**
	 * @param destroyable the destroyable to set
	 */
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	/**
	 * @return the fire
	 */
	public CGTProjectile getFire() {
		return fire;
	}
	/**
	 * @param fire the fire to set
	 */
	public void setFire(CGTProjectile fire) {
		this.fire = fire;
	}
}
