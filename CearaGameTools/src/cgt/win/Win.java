package cgt.win;

import cgt.game.CGTGameWorld;
import cgt.policy.WinPolicy;

public interface Win {
	public boolean achieved();
	public WinPolicy getPolicy();
	public void start();
	public void remove();
}
