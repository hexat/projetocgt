package cgt.win;

import cgt.policy.WinPolicy;

public interface Win {
	public boolean achieved();
	public WinPolicy getPolicy();
	public void start();
}
