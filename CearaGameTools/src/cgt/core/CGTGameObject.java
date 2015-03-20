package cgt.core;

import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cgt.policy.StatePolicy;
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
	private Vector2 position; // use in gdx mode
	private Rectangle bounds; // Size of the game object
	private Rectangle collision; // Size of the box collider
	private int speed;
	private Vector2 velocity;		// use in gdx mode
    private int life;
    private int maxLife;
	private ArrayList<CGTAnimation> animations;
	private String id;
	private StatePolicy state;
	private float stateTime = 0;  //used in gdx mode
	private float posXColider, posYColider; // used in gdx mode
	private boolean isPlayingSound;
	private int delayPlaySound;
	private TextureRegion lastAnimation;
	
	private CGTAddOn whenCollide;
	private ArrayList<CGTGameObject> objectsToCollide;
	
	public CGTGameObject(String id){
		this();
		setId(id);
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
		animations = new ArrayList<CGTAnimation>();
		for (CGTAnimation a : clone.animations) {
            CGTAnimation novo = a.clone();
            novo.setOwner(this);
            animations.add(novo);
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
		animations = new ArrayList<CGTAnimation>();
		soundsDie = new ArrayList<CGTSound>();
		soundCollision = new ArrayList<CGTSound>();
		initialPositions = new ArrayList<Vector2>();
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		String newId = id;
		int count = 1;
		while (CGTGame.get().objectIds().contains(newId)) {
			newId = id + count++;
		}
		this.id = newId;
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
			if(!soundsDie.get(numeroAleatorio).getMusic().isPlaying()){
			soundsDie.get(numeroAleatorio).getMusic().play();
			}
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
			if(!soundCollision.get(numeroAleatorio).getMusic().isPlaying()){
				soundCollision.get(numeroAleatorio).getMusic().play();
			}
		}
	}	

	public ArrayList<Vector2> getInitialPositions() {
		return initialPositions;
	}

	public void setInitialPositions(ArrayList<Vector2> initialPositions) {
		this.initialPositions = initialPositions;
	}

	public Vector2 getPosition() {
		if (position == null){
            if (initialPositions.size() > 0) {
                Random random = new Random();
                position = initialPositions.get(random.nextInt(initialPositions.size()));
            }
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
		if(this.life < life){
			this.life = 0;
		} else {
			this.life -= life;
		}
		
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
				+ ", labelID=" + id + "]";
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
		for (CGTAnimation a : animations) {
			if (a.containsState(state)) {
				lastAnimation = a.getAnimation();
				return lastAnimation;
			}
		}
		if (lastAnimation == null && animations.size() > 0) {		
			lastAnimation = animations.get(0).getAnimation();
		}
		if (lastAnimation != null) {	
			return lastAnimation;
			
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

	public void addAnimation(CGTAnimation ani) {
        ani.setOwner(this);
		animations.add(ani);
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
	
//	public boolean isDrawing() {
//		return lastAnimation != null && lastAnimation.getCGTAnimation().isDrawing();
//	}
	
	public boolean hasAnimation() {
		return animations.size() > 0;
	}
	
	public boolean hasAnimation(StatePolicy statePolicy){
		for (CGTAnimation animation : animations) {
			if(animation.containsState(statePolicy)){
				return true;
			}
		}
		return false;
	}

	public List<CGTAnimation> getAnimations() {
		return animations;
	}

	public boolean removeAnimation(CGTAnimation animation) {
        return animations.remove(animation);
	}

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void addLife(int life) {
        if (getLife() + life > getMaxLife()) {
            setLife(getMaxLife());
        } else {
            setLife(getLife() + life);
        }
    }
}
 
