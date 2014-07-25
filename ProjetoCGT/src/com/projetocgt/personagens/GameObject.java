package com.projetocgt.personagens;

import cgt.core.CGTGameObject;
import cgt.policy.StatePolicy;
//import cgt.util.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	
	private CGTGameObject cgtGameObject;
	
	//private Sound sound;
	private StatePolicy state = StatePolicy.IDLE;
	private float stateTime = 0;	
	private Music soundDie;
	private Music soundDamage;
	private Vector2 position; //Vetor que informa a posicao do personagem
	private CGTAnimation cGTAnimation;
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
		
		//soundDie = Gdx.audio.newMusic(Gdx.files.internal(gameObject.getSoundDie().getSoundFile().getPath()));
		
		setCgtGameObject(gameObject);
		setPosition(gameObject.getPosition());

		bounds.setHeight(gameObject.getBounds().height);
		bounds.setWidth(gameObject.getBounds().width);
		
		rectangle = gameObject.getCollision();
		//rectangle.setHeight(gameObject.getCollision().getHeight());
		//rectangle.setWidth(gameObject.getCollision().getWidth());
		
		rectangle.setPosition(	position.x + gameObject.getCollision().getX(),
								position.y + gameObject.getCollision().getY());
		
		this.posXColider=gameObject.getCollision().getX();
		this.posYColider=gameObject.getCollision().getY();
		
		cGTAnimation = new CGTAnimation(gameObject);
		cGTAnimation.loadSprite(	gameObject.getSpriteSheet().getTexture().getFile().getPath(),
								gameObject.getSpriteSheet().getRows(), gameObject.getSpriteSheet().getColumns());
		
	}

	/**
	 * Utilizada para ficar atualizando a posicao do GameObject
	 * @param delta
	 */
	public void update(float delta) {
		setStateTime(getStateTime() + delta);
		position.add(velocity.cpy().scl(delta));
		
		rectangle.x=this.position.x+posXColider;
		rectangle.y=this.position.y+posYColider;
		
		bounds.x=this.position.x;
		bounds.y=this.position.y;
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
	 * @return the cGTAnimation
	 */
	public CGTAnimation getSpriteSheet() {
		return cGTAnimation;
	}

	/**
	 * @param cGTAnimation the cGTAnimation to set
	 */
	public void setSpriteSheet(CGTAnimation cGTAnimation) {
		this.cGTAnimation = cGTAnimation;
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

	/**
	 * @return the state
	 */
	public StatePolicy getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(StatePolicy state) {
		this.state = state;
	}

	/**
	 * @return the stateTime
	 */
	public float getStateTime() {
		return stateTime;
	}

	/**
	 * @param stateTime the stateTime to set
	 */
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	public TextureRegion getAnimation() {
		return cGTAnimation.CGTActorAnimation(this);
	}
}
