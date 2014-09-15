package cgt.util;

import java.io.Serializable;

import cgt.policy.ActionJumpPolicy;
import cgt.unit.LabelID;

import com.badlogic.gdx.audio.Music;

public class Jump implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139898365201290195L;
	private ActionJumpPolicy action;
	private int maxJumpSeq;
	private CGTSound soundImpact;
	private CGTSound soundOut;

	public Jump(ActionJumpPolicy action, LabelID labelID){
		setAction(action);
	}

	public void setMaxJumpSeq(int max) {
		this.maxJumpSeq = max;
	}
	 
	public int getMaxJumpSeq() {
		return maxJumpSeq;
	}
	 
	public ActionJumpPolicy getAction() {
		return action;
	}
	 
	public void setAction(ActionJumpPolicy action) {
		this.action = action;
	}
	 
	public void setSoundOut(CGTSound soundOut) {
		this.soundOut = soundOut;
	}
	 
	public CGTSound getSoundOut() {
		return soundOut;
	}
	 
	public void setSoundImpact(CGTSound soundImpact) {
		this.soundImpact = soundImpact;
	}
	 
	public CGTSound getSoundImpact() {
		return soundImpact;
	}

	@Override
	public String toString() {
		return "Jump [action=" + action + ", maxJumpSeq=" + maxJumpSeq
				+ ", soundImpact=" + soundImpact + ", soundOut=" + soundOut + "]";
	}
	
}
 
