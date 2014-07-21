package com.projetocgt.personagens;

import cgt.core.CGTGameObject;
import cgt.util.Position;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	
	private CGTGameObject cgtGameObject;
	
	//private Sound sound;
	private Music soundDie;
	private Music soundDamage;
	private Vector2 position; //Vetor que informa a posicao do personagem
	private SpriteSheet spriteSheet;
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
		this.position= new Vector2();
		velocity = new Vector2();
		bounds= new Rectangle();
		rectangle = new Rectangle();
		
		setCgtGameObject(gameObject);
		setPosition(gameObject.getPosition());

		bounds.setHeight(gameObject.getBounds().getHeight());
		bounds.setWidth(gameObject.getBounds().getWidth());
		
		rectangle.setHeight(gameObject.getCollision().getHeight());
		rectangle.setWidth(gameObject.getCollision().getWidth());
		
		rectangle.setPosition(position.x + gameObject.getCollision().getPositionRelativeToObject().x,
									position.y + gameObject.getCollision().getPositionRelativeToObject().y);
		
		spriteSheet = new SpriteSheet();
		spriteSheet.loadingSpriteSheet(gameObject.getSpriteSheet().getTexture().getFile().getPath(),
											gameObject.getSpriteSheet().getRows(), gameObject.getSpriteSheet().getColumns());
		
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
		return cgtGameObject.getLife();
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life){
		cgtGameObject.setLife(life);
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
	
	public void setPosition(Position position) {
		this.position.x = position.getX();
		this.position.y = position.getY();
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
		return cgtGameObject.getSpeed();
	}

	/**
	 * @param sPEED the sPEED to set
	 */
	public void setSpeed(int sPEED) {
		cgtGameObject.setSpeed(sPEED);
	}

	/**
	 * @return the cgtGameObject
	 */
	public CGTGameObject getCgtGameObject() {
		return cgtGameObject;
	}

	/**
	 * @param cgtGameObject the cgtGameObject to set
	 */
	public void setCgtGameObject(CGTGameObject cgtGameObject) {
		this.cgtGameObject = cgtGameObject;
	}
}
