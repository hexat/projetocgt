package cgt.util;

import java.io.Serializable;

public class Jump implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139898365201290195L;
	private int maxJumpSeq;
	private CGTSound soundImpact;
	private CGTSound soundOut;

	public Jump(){
		maxJumpSeq = 1;
		soundImpact = null;
		soundOut = null;
	}

	public void setMaxJumpSeq(int max) {
		this.maxJumpSeq = max;
	}
	 
	public int getMaxJumpSeq() {
		return maxJumpSeq;
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
		return "Jump [maxJumpSeq=" + maxJumpSeq
				+ ", soundImpact=" + soundImpact + ", soundOut=" + soundOut + "]";
	}
	
}
 
