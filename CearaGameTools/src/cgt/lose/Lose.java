package cgt.lose;

import cgt.policy.LosePolicy;

public interface Lose {
	public boolean lost();
	public void start();
	public LosePolicy getPolicy();
}
