package cgt.behaviors;

import java.io.Serializable;

import cgt.core.AbstractBehavior;
import cgt.policy.FadePolicy;
import com.badlogic.gdx.utils.Timer;

public class Fade extends AbstractBehavior {
    private boolean started;
    private int time;
    private FadePolicy fadePolicy;

    public Fade() {}

    public Fade(FadePolicy fadePolicy){
        setFadePolicy(fadePolicy);
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public FadePolicy getFadePolicy() {
        return fadePolicy;
    }

    public void setFadePolicy(FadePolicy fadePolicy) {
        this.fadePolicy = fadePolicy;
    }

    @Override
    public String getBehaviorPolicy() {
        return fadePolicy.name();
    }

    @Override
    public void act() {
        if (fadePolicy ==  FadePolicy.FADE_IN) {
        	
            getOwner().setAlpha(0);
            getOwner().setVulnerable(false);
            remove();

            Timer.schedule(new Timer.Task() {
                int tempo = 0;

                public void run() {
                    tempo++;
                    if (tempo >= getTime()) {
                        getOwner().setAlpha(1);
                        getOwner().setVulnerable(true);
                        this.cancel();
                    }
                }
            }, 1, 1);

        } else if (fadePolicy ==  FadePolicy.FADE_AND_DIE) {
            Timer.schedule(new Timer.Task() {
                int tempo = 0;
                public void run() {
                    tempo++;
                    System.out.println(tempo);
                    if (tempo >= getTime()) {
                        getOwner().setAlpha(0);
                        getOwner().setVulnerable(false);
                        remove();
                        this.cancel();
                    }
                }
            }, 1, 1);


        }
    }

    @Override
    public void start() {

    }

    @Override
    public String toString() {
        return "Fade [fadeInTIme=" + time + "fadePolicy=" + fadePolicy + "]";
    }


    /**
     * @return the started
     */

    public boolean isStarted() {
        return started;
    }


    /**
     * @param started the started to set
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

}
 
