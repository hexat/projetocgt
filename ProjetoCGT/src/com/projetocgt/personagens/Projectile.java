package com.projetocgt.personagens;

import java.util.ArrayList;

import cgt.core.CGTGameObject;
import cgt.core.CGTProjectile;
import cgt.unit.ActionFire;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.util.ProjectileOrientation;
import com.sun.org.apache.bcel.internal.generic.CPInstruction;
/**
 * Classe reponsavel pela construcao do CGTProjectile que tera as informacoes sobre 
 * os inimigos 
 * @author bruno
 *
 */
public class Projectile extends GameObject {
	private CGTProjectile cgtProjectile;
	private boolean flagAtivar;
//	private ActionFire actionFire;
//	private int numFiresForOneInput;
//	private int damage;
//	private int interval;
//	private int ammo;
	private Vector2 positionRetativeToGameObject;
//	private float angle;
	//private LabelID labelID;
//	private Texture texture;
//	private float posXColider, posYColider;
//	private Rectangle bounds= new Rectangle();
//	private Rectangle rectangle;
//	private SpriteSheet spriteSheet;
	ArrayList<ProjectileOrientation> listaDeProjectileOrientation = new ArrayList<ProjectileOrientation>();
//	private CGTGameObject owner;
	
	/***
	 * Recebe uma posicao inicial
	 * @param position
	 */
//	public Projectile(Vector2 position ,float width, float height, float colider, float posXColider, float posYColider){
//		super(position, width, height, colider, posXColider, posYColider);
//	}
	
	public Projectile(CGTProjectile projectile) {
		super(projectile);
		cgtProjectile = projectile;
	}
	
	/**
	 * Diminui um do valor do ammo
	 */
	public void ammoDown(){
		cgtProjectile.setAmmo(getAmmo()-1);
	}

	/**
	 * @return the numFiresForOneInput
	 */
	public int getNumFiresForOneInput() {
		return cgtProjectile.getNumFiresForOneInput();
	}
	/**
	 * @param numFiresForOneInput the numFiresForOneInput to set
	 */
	public void setNumFiresForOneInput(int numFiresForOneInput) {
		cgtProjectile.setNumFiresForOneInput(numFiresForOneInput);
	}
	/**
	 * @return the damage
	 */
	public int getDamage() {
		return cgtProjectile.getDamage();
	}
	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		cgtProjectile.setDamage(damage);
	}
	/**
	 * @return the interval
	 */
	public int getInterval() {
		return cgtProjectile.getInterval();
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		cgtProjectile.setInterval(interval);
	}
	/**
	 * @return the ammo
	 */
	public int getAmmo() {
		return cgtProjectile.getAmmo();
	}
	/**
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {
		cgtProjectile.setAmmo(ammo);
	}
	
	/**
	 * @return the angle
	 */
	public float getAngle() {
		return cgtProjectile.getAngle();
	}
	/**
	 * @param angle the angle to set
	 */
	public void setAngle(float angle) {
		cgtProjectile.setAngle(angle);
	}

	
	/**
	 * @return the positionRetativeToGameObject
	 */
	public Vector2 getPositionRetativeToGameObject() {
		return positionRetativeToGameObject;
	}
	/**
	 * @param positionRetativeToGameObject the positionRetativeToGameObject to set
	 */
	public void setPositionRetativeToGameObject(
			Vector2 positionRetativeToGameObject) {
		this.positionRetativeToGameObject = positionRetativeToGameObject;
	}
	/**
	 * @return the actionFire
	 */
	public ActionFire getActionFire() {
		return cgtProjectile.getAction();
	}
	/**
	 * @param actionFire the actionFire to set
	 */
	public void setActionFire(ActionFire actionFire) {
		cgtProjectile.setAction(actionFire);
	}
	/**
	 * @return the flagAtivar
	 */
	public boolean isFlagAtivar() {
		return flagAtivar;
	}
	/**
	 * @param flagAtivar the flagAtivar to set
	 */
	public void setFlagAtivar(boolean flagAtivar) {
		this.flagAtivar = flagAtivar;
	}
	/**
	 * @return the listaDeProjectileOrientation
	 */
	public ArrayList<ProjectileOrientation> getListaDeProjectileOrientation() {
		return listaDeProjectileOrientation;
	}
	/**
	 * @param listaDeProjectileOrientation the listaDeProjectileOrientation to set
	 */
	public void setListaDeProjectileOrientation(
			ArrayList<ProjectileOrientation> listaDeProjectileOrientation) {
		this.listaDeProjectileOrientation = listaDeProjectileOrientation;
	}
	
}
