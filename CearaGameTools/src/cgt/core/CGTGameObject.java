package cgt.core;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import cgt.policy.StatePolicy;
import cgt.unit.LabelID;
import cgt.util.AnimationMap;
import cgt.util.CGTAnimation;
import cgt.util.AnimationHandle;
import cgt.util.CGTSound;

import com.badlogic.gdx.math.Vector2;


/**
 * @author ProjetoCGT
 *
 */
public abstract class CGTGameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1528830629331653234L;
	private CGTSound sound;
	private ArrayList<CGTSound> soundsDie;
	private ArrayList<CGTSound> soundCollision;
	private ArrayList<Vector2> initialPositions;
	private Vector2 position;
	private Rectangle bounds; // Size of the game object
	private Rectangle collision; // Size of the box collider
	private int speed;
	private Vector2 velocity;		//Vetor que informa a velocidade do personagem
	private int life;
	private ArrayList<AnimationMap> animations;
	private LabelID labelID;
	private StatePolicy state;
	private float stateTime = 0;
	private float posXColider, posYColider;
	private boolean isPlayingSound;
	private int delayPlaySound;
	private CGTAnimation lastAnimation; 
	
	private CGTAddOn whenCollide;
	private ArrayList<CGTGameObject> objectsToCollide;
	
	public CGTGameObject(LabelID labelID){
		setLabelID(labelID);
	}
	
	public CGTGameObject(CGTGameObject clone) {
		if (clone.getSound() != null) {
			sound = clone.getSound().clone();
		} else {
			sound = null;
		}
		soundsDie = new ArrayList<CGTSound>();
		for (CGTSound s : clone.getSoundsDie()) {
			soundsDie.add(s.clone());
		}
		soundCollision = new ArrayList<CGTSound>();
		for (CGTSound s : clone.getSoundCollision()) {
			soundCollision.add(s.clone());
		}
		initialPositions = new ArrayList<Vector2>();
		for (Vector2 v : clone.getInitialPositions()) {
			initialPositions.add(v.cpy());
		}
		if (clone.getPosition() != null) {
			position = clone.getPosition().cpy();
		} else {
			position = null;
		}
		bounds = new Rectangle();
		bounds.height = clone.getBounds().height;
		bounds.width = clone.getBounds().width;
		bounds.x = clone.getBounds().x;
		bounds.y = clone.getBounds().y;
		
		collision = new Rectangle();
		collision.height = clone.getCollision().height;
		collision.width = clone.getCollision().width;
		collision.x = clone.getCollision().x;
		collision.y = clone.getCollision().y;
		
		speed = clone.getSpeed();
		velocity = clone.getVelocity().cpy();
		life = clone.getLife();
		animations = new ArrayList<AnimationMap>();
		for (AnimationMap a : clone.animations) {
			CGTAnimation newAnis[] = new CGTAnimation[a.getAnimations().size()];
			for (int i = 0; i < a.getAnimations().size(); i++) {
				CGTAnimation novo = new CGTAnimation(this, a.getAnimations().get(i).getSpriteSheet());
				novo.setAnimationPolicy(a.getAnimations().get(i).getAnimationPolicy());
				novo.setEndingFrame(a.getAnimations().get(i).getEndingFrame());
				novo.setFlipHorizontal(a.getAnimations().get(i).isFlipHorizontal());
				novo.setFlipVertical(a.getAnimations().get(i).isFlipVertical());
				novo.setInitialFrame(a.getAnimations().get(i).getInitialFrame());
				novo.setSpriteSheet(a.getAnimations().get(i).getSpriteSheet());
				novo.setSpriteVelocity(a.getAnimations().get(i).getSpriteVelocity());
				newAnis[i] = novo;
			}
			animations.add(new AnimationMap(a.getStatePolicy(), newAnis));
		}
		state = clone.getState();
		stateTime = 0;
		delayPlaySound = clone.getDelayPlaySound();
		whenCollide = null; //TODO
		objectsToCollide = new ArrayList<CGTGameObject>();
	}
	
	/**
	 * Utilizada para ficar atualizando a posicao do GameObject
	 * @param delta
	 */
	public void update(float delta) {
		setStateTime(getStateTime() + delta);
		
		if (getPosition() != null) {
			getPosition().add(velocity.cpy().scl(delta));
			
			collision.x=this.getPosition().x+posXColider;
			collision.y=this.getPosition().y+posYColider;
			
			bounds.x=this.getPosition().x;
			bounds.y=this.getPosition().y;
		}
	}
	
	public CGTGameObject(){
		position= null;
		lastAnimation = null;
		velocity = new Vector2();
		bounds= new Rectangle();
		collision = new Rectangle();
		setState(StatePolicy.IDLE);
		animations = new ArrayList<AnimationMap>();
		soundsDie = new ArrayList<CGTSound>();
		soundCollision = new ArrayList<CGTSound>();
		initialPositions = new ArrayList<>();
		objectsToCollide =  new ArrayList<CGTGameObject>();
		isPlayingSound = false;
		setDelayPlaySound(0);
		whenCollide = null;
	}
	
	public CGTGameObject(Vector2 position, Rectangle bounds, Rectangle collision){
		this();
		this.position = position;
		this.collision = collision;
		posXColider = collision.x;
		posYColider = collision.y;
		this.bounds = bounds;
	}
	
	public LabelID getLabelID() {
		return labelID;
	}

	public void setLabelID(LabelID labelID) {
		this.labelID = labelID;
	}

	public CGTSound getSound() {
		return sound;
	}
	
	public boolean isPlayingSound(){
		if (sound == null){
			return false;
		} else{
			if (sound.getMusic().isPlaying()) {
				return true;
			} else {
				return isPlayingSound;
			}
		}
	}

	public void setSound(CGTSound sound) {
		this.sound = sound;
	}
	
	public void playSound(){
		if (sound != null){
			sound.getMusic().play();
			isPlayingSound = true;
		}
	}

	public ArrayList<CGTSound> getSoundsDie(){
		return soundsDie;
	}
	
	public void loopMusic(){
		if (sound != null){
			sound.getMusic().play();
			sound.getMusic().setLooping(true);
		}
	}
	
	public void stopMusic(){
		if (sound != null){
			sound.getMusic().stop();
		}
	}
	
	public void setSoundDie(ArrayList<CGTSound> listaSoundDie){
		soundsDie = listaSoundDie;
	}
	
	public void setSoundDie(CGTSound music){
		soundsDie.add(music);
	}
	
	public void playSoundDie(){
		if (soundsDie.size() > 0){
			Random r = new Random();
			int numeroAleatorio = r.nextInt(getSoundsDie().size());
			soundsDie.get(numeroAleatorio).getMusic().play();
		}
	}
	
	public Music getSoundDie(){
		if (soundsDie.size() > 0){
			Random r = new Random();
			int numeroAleatorio = r.nextInt(getSoundsDie().size());
			return soundsDie.get(numeroAleatorio).getMusic();
		}
		return null;
	}

	public ArrayList<CGTSound> getSoundCollision() {
		return soundCollision;
	}
	
	public void setSoundCollision(ArrayList<CGTSound> soundCollision){
		this.soundCollision = soundCollision;
	}

	public void setSoundCollision(CGTSound soundCollision) {
		this.soundCollision.add(soundCollision);
	}
	
	public void playSoundCollision(){
		if (soundCollision.size() > 0){
			Random r = new Random();
			int numeroAleatorio = r.nextInt(getSoundCollision().size());
			soundCollision.get(numeroAleatorio).getMusic().play();
		}
	}	

	public ArrayList<Vector2> getInitialPositions() {
		return initialPositions;
	}

	public void setInitialPositions(ArrayList<Vector2> initialPositions) {
		this.initialPositions = initialPositions;
	}

	public Vector2 getPosition() {
		if (position == null && initialPositions.size() > 0){
			Random random = new Random();
			position = initialPositions.get(random.nextInt(initialPositions.size()));
		}
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;

//		collision.x=this.getPosition().x+posXColider;
//		collision.y=this.getPosition().y+posYColider;
//		
//		bounds.x=this.getPosition().x;
//		bounds.y=this.getPosition().y;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public void reduceLife(int life){
		this.life -= life;
	}


	public Rectangle getCollision() {
		return collision;
	}

	public void setCollision(Rectangle collision) {
		this.posXColider = collision.getX();
		this.posYColider = collision.getY();
		this.collision = collision;
	}

	@Override
	public String toString() {
		return "CGTGameObject [sound=" + sound + ", soundDie=" + soundsDie
				+ ", soundCollision=" + soundCollision + ", position="
				+ position + ", collision=" + collision + ", life=" + life
				+ ", labelID=" + labelID + "]";
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param rectangle the bounds to set
	 */
	public void setBounds(Rectangle rectangle) {
		this.bounds = rectangle;
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
		for (AnimationMap a : animations) {
			if (a.getStatePolicy() == state) {
				lastAnimation = a.getRandomAnimation();
				return lastAnimation.getAnimation();
			}
		}
		if (lastAnimation == null && animations.size() > 0) {
			lastAnimation = animations.get(0).getRandomAnimation();
		}
		if (lastAnimation != null) {
			return lastAnimation.getAnimation();
		}
		return null;
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

	public void addAnimation(StatePolicy state, CGTAnimation ani) {
		for (AnimationMap a : animations) {
			if (a.getStatePolicy() == state) {
				a.addAnimation(ani);
				return;
			}
		}
		animations.add(new AnimationMap(state, ani));
	}

	public int getDelayPlaySound() {
		return delayPlaySound;
	}

	public void setDelayPlaySound(int delayPlaySound) {
		this.delayPlaySound = delayPlaySound;
	}

	public void canPlaySaund() {
		isPlayingSound = false;
	}
	
	public ArrayList<CGTGameObject> getObjectsToCollide() {
		return objectsToCollide;
	}
	
	public void setColideObject(CGTAddOn obj) {
		this.whenCollide = obj;
	}
	
	public CGTAddOn getCollideAnimation() {
		return whenCollide;
	}
	
	public boolean isDrawing() {
		return lastAnimation != null && lastAnimation.getCGTAnimation().isDrawing();
	}
}
 
