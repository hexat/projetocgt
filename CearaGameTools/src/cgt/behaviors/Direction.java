package cgt.behaviors;

import java.util.Random;
import cgt.core.AbstractBehavior;
import com.badlogic.gdx.math.Vector2;
import cgt.policy.DirectionPolicy;

public class Direction extends AbstractBehavior {

    private static Random random = new Random();
    
    

    public enum DirectionMode {
		LOOP,
		PINGPONG,
		ONCE
	}

	//Demarcadores da area de movimentacao
	private Vector2 initialPosition;
	private Vector2 finalPosition;
	private DirectionMode directionMode;
	private boolean inteligenceMoviment;
	private DirectionPolicy directionPolicy;

	// used in gdx mode
	private float distance;
	private Vector2 actorPosition;
	
	
	public Direction(){
		
	}
	public Direction(DirectionPolicy directionPolicy){
		setDirectionPolicy(directionPolicy);
		this.initialPosition = new Vector2();
		this.finalPosition = new Vector2();
		actorPosition = null;
		distance = 0;
		setDirectionMode(DirectionMode.LOOP);
	}
	 
	public boolean isInteligenceMoviment() {
		return inteligenceMoviment;
	}

	public void setInteligenceMoviment(boolean inteligenceMoviment) {
		this.inteligenceMoviment = inteligenceMoviment;
	}



	public DirectionPolicy getDirectionPolicy() {
		return directionPolicy;
	}
	 
	public void setDirectionPolicy(DirectionPolicy directionPolicy) {
		this.directionPolicy = directionPolicy;
	}

	@Override
	public String getBehaviorPolicy() {
		return directionPolicy.name();
	}

    @Override
    public void act() {
        if (directionPolicy == DirectionPolicy.FOUR_DIRECTION) {
            int[] angulos = { 0, 90, 180, 270 };


            if (random.nextFloat() < 0.00005 * getOwner().getSpeed())
                scheduleDirection(angulos);

//				if (enemy.getPosition().x < direction.getMinX())
//					enemy.getVelocity().x = enemy.getSpeed();
//				if (enemy.getPosition().x > direction.getMaxX())
//					enemy.getVelocity().x = -enemy.getSpeed();
//
//				if (enemy.getPosition().y < direction.getMinY())
//					enemy.getVelocity().y = enemy.getSpeed();
//				if (enemy.getPosition().y > direction.getMaxY())
//					enemy.getVelocity().y = -enemy.getSpeed();
        } else if (directionPolicy == DirectionPolicy.EIGHT_DIRECTION) {
            int[] angulos = { 0, 45, 90, 135, 180, 225, 270, 315 };

            if (random.nextFloat() < 0.0001 * getOwner().getSpeed())
                scheduleDirection(angulos);

//				if (enemy.getPosition().x < direction.getMinX())
//					enemy.getVelocity().x = enemy.getSpeed();
//				if (enemy.getPosition().x > direction.getMaxX())
//					enemy.getVelocity().x = -enemy.getSpeed();
//
//				if (enemy.getPosition().y < direction.getMinY())
//					enemy.getVelocity().y = enemy.getSpeed();
//				if (enemy.getPosition().y > direction.getMaxY())
//					enemy.getVelocity().y = -enemy.getSpeed();
        } else if(directionPolicy == DirectionPolicy.TWO_POINTS_DIRECTION) {
            if (getActorPosition() == null) {
                getOwner().getPosition().x = getInitialPosition().x;
                getOwner().getPosition().y = getInitialPosition().y;
                setActorPosition(getOwner().getPosition());
            }

            //TODO Movimento inteligente
            if (getInitialPosition().y < getFinalPosition().y) {
                if (getInitialPosition().x < getFinalPosition().x) {
                    // sentido nordeste
                    if (getOwner().getPosition().x >= getFinalPosition().x && getOwner().getPosition().y >= getFinalPosition().y) {
                        notifyEnd();
                    } else {
                        if (getOwner().getPosition().x < getFinalPosition().x) {
                            getOwner().getVelocity().x = getOwner().getSpeed()*(getFinalPosition().x - getInitialPosition().x)/ getDistance();
                        } else {
                            getOwner().getVelocity().x = 0;
                        }
                        if (getOwner().getPosition().y < getFinalPosition().y) {
                            getOwner().getVelocity().y = getOwner().getSpeed()*(getFinalPosition().y - getInitialPosition().y) / getDistance();
                        } else {
                            getOwner().getVelocity().y = 0;
                        }

                    }
                } else {
                    //  sentido noroeste
                    if (getOwner().getPosition().x <= getFinalPosition().x && getOwner().getPosition().y >= getFinalPosition().y) {
                        notifyEnd();
                    } else {
                        if (getOwner().getPosition().y < getFinalPosition().y) {
                            getOwner().getVelocity().y = getOwner().getSpeed()*(getFinalPosition().y - getInitialPosition().y) / getDistance();
                        } else {
                            getOwner().getVelocity().y = 0;
                        }

                        if (getOwner().getPosition().x > getFinalPosition().x) {
                            getOwner().getVelocity().x = getOwner().getSpeed()*(getFinalPosition().x - getInitialPosition().x)/ getDistance();
                        } else {
                            getOwner().getVelocity().x = 0;
                        }

                    }
                }
            } else {
                if (getInitialPosition().x < getFinalPosition().x) {
                    //  sentido sudeste
                    if (getOwner().getPosition().x >= getFinalPosition().x && getOwner().getPosition().y <= getFinalPosition().y) {
                        notifyEnd();
                    } else {
                        if (getOwner().getPosition().y > getFinalPosition().y) {
                            getOwner().getVelocity().y = getOwner().getSpeed()*(getFinalPosition().y - getInitialPosition().y) / getDistance();
                        } else {
                            getOwner().getVelocity().y = 0;
                        }
                        if (getOwner().getPosition().x < getFinalPosition().x) {
                            getOwner().getVelocity().x = getOwner().getSpeed()*(getFinalPosition().x - getInitialPosition().x)/ getDistance();
                        } else {
                            getOwner().getVelocity().x = 0;
                        }
                    }
                } else {
                    // sentido sudoeste
                    if (getOwner().getPosition().x <= getFinalPosition().x && getOwner().getPosition().y <= getFinalPosition().y) {
                        notifyEnd();
                    } else {
                        if (getOwner().getPosition().y > getFinalPosition().y) {
                            getOwner().getVelocity().y = getOwner().getSpeed()*(getFinalPosition().y - getInitialPosition().y) / getDistance();
                        } else {
                            getOwner().getVelocity().y = 0;
                        }
                        if (getOwner().getPosition().x > getFinalPosition().x) {
                            getOwner().getVelocity().x = getOwner().getSpeed()*(getFinalPosition().x - getInitialPosition().x)/ getDistance();
                        } else {
                            getOwner().getVelocity().x = 0;
                        }

                    }
                }
            }
        }
    }

    @Override
    public void start() {

    }

    @Override
	public String toString() {
		return "Direction [directionPolicy=" + directionPolicy + "]";
	}

	public Vector2 getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Vector2 initialPosition) {
		this.initialPosition = initialPosition;
	}

	public Vector2 getFinalPosition() {
		return finalPosition;
	}

	public void setFinalPosition(Vector2 finalPosition) {
		this.finalPosition = finalPosition;
	}
	
	public float getDistance() {
		if (distance <= 0 && getFinalPosition() != null && getInitialPosition() != null) {
			distance = getFinalPosition().dst(getInitialPosition());
		}
		
		return distance;
	}

	public Vector2 getActorPosition() {
		return actorPosition;
	}

	public void setActorPosition(Vector2 actorPosition) {
		if (this.actorPosition == null) {
			this.actorPosition = new Vector2();
		}
		this.actorPosition.y = actorPosition.y;
		this.actorPosition.x = actorPosition.x;
	}

	/**
	 * Metodo usado somente em GDX, serve para notificar que o movimento acabou,
	 * ou seja, que o inimigo chegou de um ponto ao outro. O metodo vai verificar 
	 * a politica de movimento e decidira o que fazer
	 */
	public void notifyEnd() {
		switch (directionMode) {
		case PINGPONG:
			Vector2 aux = initialPosition;
			initialPosition = finalPosition;
			finalPosition = aux;
			break;
		case LOOP:
			getOwner().setPosition(initialPosition.cpy());
		default:
			break;
		}
	}

	public DirectionMode getDirectionMode() {
		return directionMode;
	}

	public void setDirectionMode(DirectionMode directionMode) {
		this.directionMode = directionMode;
	}

    // Implementação do comportamento descrito por um behavior Direction
    private void scheduleDirection(int[] angulos) {
        getOwner().getVelocity().x = 0;
        getOwner().getVelocity().y = 0;

        int indice = random.nextInt(angulos.length);
        
        switch(angulos[indice]){
        case 0:
        	getOwner().getVelocity().x = getOwner().getSpeed();
        	break;
        case 45:
        	getOwner().getVelocity().x = getOwner().getSpeed();
        	getOwner().getVelocity().y = getOwner().getSpeed();
        	break;
        case 90:
        	getOwner().getVelocity().y = getOwner().getSpeed();
        	break;
        case 135:
        	getOwner().getVelocity().x = -getOwner().getSpeed();
        	getOwner().getVelocity().y = getOwner().getSpeed();
        	break;
        case 180:
        	getOwner().getVelocity().x = -getOwner().getSpeed();
        	break;
        case 225:
        	getOwner().getVelocity().x = -getOwner().getSpeed();
        	getOwner().getVelocity().y = -getOwner().getSpeed();
        	break;
        case 270:
        	getOwner().getVelocity().y = -getOwner().getSpeed();
        	break;
        case 315:
        	getOwner().getVelocity().x = getOwner().getSpeed();
        	getOwner().getVelocity().y = -getOwner().getSpeed();
        	break;
        	
        }

        if(getOwner().getPosition().x < initialPosition.x){
        	getOwner().getVelocity().x = getOwner().getSpeed();
        	getOwner().getVelocity().y = 0;
        } else if (getOwner().getPosition().x > initialPosition.y) {
        	getOwner().getVelocity().x = -getOwner().getSpeed();
        	getOwner().getVelocity().y = 0;
        }else if(getOwner().getPosition().y < finalPosition.x){
        	getOwner().getVelocity().y = getOwner().getSpeed();
        	getOwner().getVelocity().x = 0;
        } else if (getOwner().getPosition().y > finalPosition.y) {
        	getOwner().getVelocity().y = -getOwner().getSpeed();
        	getOwner().getVelocity().x = 0;
        }

    }
}
 
