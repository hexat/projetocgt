package cgt.core;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import cgt.policy.StatePolicy;
import cgt.unit.LabelID;
import cgt.util.CGTAnimation;
import cgt.util.AnimationHandle;
import cgt.util.CGTSound;
import cgt.util.CGTSpriteSheet;

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
	private int Speed;
	private Vector2 velocity;		//Vetor que informa a velocidade do personagem
	private int life;
	private CGTSpriteSheet spriteSheet;
	private ArrayList<CGTAnimation> animations;
	private AnimationHandle animation;
	private LabelID labelID;
	private StatePolicy state;
	private float stateTime = 0;
	private float posXColider, posYColider; 
	
	public CGTGameObject(LabelID labelID){
		setLabelID(labelID);
	}
	
	/**
	 * Utilizada para ficar atualizando a posicao do GameObject
	 * @param delta
	 */
	public void update(float delta) {
		setStateTime(getStateTime() + delta);
		getPosition().add(velocity.cpy().scl(delta));
		
		//System.out.println(velocity.y);
		
		//System.out.println("posYColider: " + posYColider);
		collision.x=this.getPosition().x+posXColider;
		collision.y=this.getPosition().y+posYColider;
		
		bounds.x=this.getPosition().x;
		bounds.y=this.getPosition().y;
	}
	
	public CGTGameObject(){
		position= null;
		velocity = new Vector2();
		bounds= new Rectangle();
		collision = new Rectangle();
		setState(StatePolicy.IDLE);
		animations = new ArrayList<CGTAnimation>();
		animation=null;
		soundsDie = new ArrayList<CGTSound>();
		soundCollision = new ArrayList<CGTSound>();
		initialPositions = new ArrayList<>();
	}
	
	public CGTGameObject(Vector2 position, Rectangle bounds, Rectangle collision){
		this.position = position;
		this.collision = collision;
		posXColider = collision.x;
		posYColider = collision.y;
		this.bounds = bounds;
		animations = new ArrayList<CGTAnimation>();
		setState(StatePolicy.IDLE);
		animation=null;
		initialPositions = new ArrayList<>();
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
			return sound.getMusic().isPlaying();
		}
	}

	public void setSound(CGTSound sound) {
		this.sound = sound;
	}
	
	public void playSound(){
		if (sound != null){
			sound.getMusic().play();
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

		collision.x=this.getPosition().x+posXColider;
		collision.y=this.getPosition().y+posYColider;
		
		bounds.x=this.getPosition().x;
		bounds.y=this.getPosition().y;
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
				+ ", cGTSpriteSheet=" + animation + ", labelID=" + labelID + "]";
	}

	public int getSpeed() {
		return Speed;
	}

	public void setSpeed(int speed) {
		Speed = speed;
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
		if(animation==null){
			this.animation = new AnimationHandle(spriteSheet);
		}
		return animation.getAnimationFrame();
	}

	public AnimationHandle getCGTAnimation() {
		if(animation==null){
			this.animation = new AnimationHandle(spriteSheet);
		}
		return animation;
	}


	/**
	 * @return the spriteSheet
	 */
	public CGTSpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	/**
	 * @param spriteSheet the spriteSheet to set
	 */
	public void setSpriteSheet(CGTSpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
		this.spriteSheet.setOwner(this);
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

	public ArrayList<CGTAnimation> getAnimarions() {
		return animations;
	}

	public void setAnimarions(ArrayList<CGTAnimation> animarions) {
		this.animations = animarions;
	}
	
}
 
