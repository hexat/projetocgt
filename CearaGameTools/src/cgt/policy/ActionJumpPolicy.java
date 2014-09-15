package cgt.policy;

public enum ActionJumpPolicy implements ActionPolicy {
	JUMP;

	@Override
	public String getName() {
		return name();
	}
}
 
