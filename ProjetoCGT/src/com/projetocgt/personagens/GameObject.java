package com.projetocgt.personagens;

import cgt.core.CGTGameObject;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	//private Sound sound;
	private Music soundDie;
	private Music soundDamage;
	private Vector2 position; //Vetor que informa a posicao do personagem
	private int life;
	private SpriteSheet spriteSheet;
	private float speed; //Velocidade do personagem
	private Vector2 velocity;		//Vetor que informa a velocidade do personagem
	private Rectangle bounds;		// Area que sera' desenhado o personagem
	private Rectangle rectangle;
	private float posXColider, posYColider; 

	/**
	 * 
	 * @param position posicao na tela
	 * @param width largura do objeto
	 * @param height altura do objeto
	 * @param colider 
	 * @param posXColider
	 * @param posYColider
	 */
	protected GameObject(Vector2 position, float width, float height, float colider, float posXColider, float posYColider ){
		this.position= new Vector2();
		velocity = new Vector2();
		bounds= new Rectangle();
		rectangle = new Rectangle();

		setPosition(position); 		//Posicao inicial
		bounds.setHeight(height);	//Altura do personagem (Altura da area onde o personagem sera desenhado)
		bounds.setWidth(width);		//Largura do personagem (Largura da area onde o personagem sera desenhado)

		this.posXColider=posXColider;
		this.posYColider=posYColider;

		setRectangle(new Rectangle(position.x+this.posXColider,position.y+this.posYColider,colider,colider));
	}

	protected GameObject(CGTGameObject gameObject){
		Vector2 cgtPosition = new Vector2(gameObject.getPosition().getX(), gameObject.getPosition().getY());
		this.setPosition(cgtPosition);

		bounds.setHeight(gameObject.getCollision().getHeight());
		bounds.setWidth(gameObject.getCollision().getWidth());

	}

	/**
	 * Utilizada para ficar atualizando a posicao do GameObject
	 * @param delta
	 */
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
		rectangle.x=this.position.x+posXColider;
		rectangle.y=this.position.y+posYColider;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
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
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position.x = position.x;
		this.position.y = position.y;
	}
	
	/**
	 * @return the soundDie
	 */
	public Music getSoundDie() {
		return soundDie;
	}
	
	/**
	 * @param soundDie the soundDie to set
	 */
	public void setSoundDie(Music soundDie) {
		this.soundDie = soundDie;
	}
	
	/**
	 * @return the soundDamage
	 */
	public Music getSoundDamage() {
		return soundDamage;
	}
	
	/**
	 * @param soundDamage the soundDamage to set
	 */
	public void setSoundDamage(Music soundDamage) {
		this.soundDamage = soundDamage;
	}

	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
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
	 * @return the sPEED
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param sPEED the sPEED to set
	 */
	public void setSpeed(float sPEED) {
		speed = sPEED;
	}
}
