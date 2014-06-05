package com.projetocgt.personagens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.progetocgt.util.ProjectileOrientation;

public class CGTProjectile {
	private boolean flagAtivar;
	private String actionFire;
	private int numFiresForOneInput;
	private int damage;
	private int interval;
	private int ammo;
	private Vector2 positionRetativeToGameObject;
	private int velocityInitial;
	private float angle;
	//private LabelID labelID;
	private Texture texture;
	private Vector2 position;
	private float posXColider, posYColider;
	private Rectangle bounds= new Rectangle();
	private Rectangle rectangle;
	private SpriteSheet spriteSheet;
	ArrayList<ProjectileOrientation> listaDeProjectileOrientation = new ArrayList<ProjectileOrientation>();
	/***
	 * Recebe uma posicao inicial
	 * @param position
	 */
	public CGTProjectile(Vector2 position ,float width, float height, float colider, float posXColider, float posYColider){
		setPosition(position);
		this.bounds.height=width;
		this.bounds.width=height;
		this.posXColider=posXColider;
		this.posYColider=posYColider;
		this.setRectangle(new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider));
	}
	/**
	 * Utilizada para ficar atualizando a posicao do Game Object CGTProjectile
	 * @param delta
	 */
	public void update(float delta) {
		//for(int i=0;i<listaDeProjectileOrientation.size();i++){
		rectangle.x=this.position.x+posXColider;
		rectangle.y=this.position.y+posYColider;
		//}
	}
	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	/**
	 * @param textura the texture to set
	 */
	public void setTexture(Texture textura) {
		this.texture = textura;
	}
	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	/**
	 * @return the numFiresForOneInput
	 */
	public int getNumFiresForOneInput() {
		return numFiresForOneInput;
	}
	/**
	 * @param numFiresForOneInput the numFiresForOneInput to set
	 */
	public void setNumFiresForOneInput(int numFiresForOneInput) {
		this.numFiresForOneInput = numFiresForOneInput;
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
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
	/**
	 * @return the ammo
	 */
	public int getAmmo() {
		return ammo;
	}
	/**
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	/**
	 * @return the velocityInitial
	 */
	public int getVelocityInitial() {
		return velocityInitial;
	}
	/**
	 * @param velocityInitial the velocityInitial to set
	 */
	public void setVelocityInitial(int velocityInitial) {
		this.velocityInitial = velocityInitial;
	}
	/**
	 * @return the angle
	 */
	public float getAngle() {
		return angle;
	}
	/**
	 * @param angle the angle to set
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}
	/**
	 * @return the rectangle
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}
	/**
	 * @param rectangle the rectangle to set
	 */
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	/**
	 * @return the spriteSheet
	 */
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	/**
	 * @param spriteSheet the spriteSheet to set
	 */
	public void setSpriteSheet(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
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
	public String getActionFire() {
		return actionFire;
	}
	/**
	 * @param actionFire the actionFire to set
	 */
	public void setActionFire(String actionFire) {
		this.actionFire = actionFire;
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
