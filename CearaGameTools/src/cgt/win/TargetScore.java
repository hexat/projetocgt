package cgt.win;

import cgt.game.WinCriteria;
import cgt.policy.WinPolicy;

/**
 * Created by luanjames on 09/03/15.
 */
public class TargetScore extends WinCriteria {
    public int score;

    public TargetScore() {
        score = 10;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean achieved() {
        return getWorld().getScore() >= score;
    }

    @Override
    public WinPolicy getPolicy() {
        return WinPolicy.TARGET_SCORE;
    }

    @Override
    public void start() {

    }
}
