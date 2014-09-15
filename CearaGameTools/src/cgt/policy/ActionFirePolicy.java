package cgt.policy;

public enum ActionFirePolicy implements ActionPolicy {
	FIRE;

	@Override
	public String getName() {
		return name();
	}
} 
