package cgt.core;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;
import cgt.policy.StatePolicy;
import cgt.unit.Action;
import cgt.unit.LabelID;
import cgt.util.Jump;

public class CGTActor extends CGTGameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1967794799880980169L;

	private ArrayList<Jump> jumps;
	private ArrayList<Action> actions;
	private ArrayList<CGTProjectile> projectiles;
	private int jumpDefault;
	private int fireDefault;
	private int timeToRecovery;
	private int timeToEnableInputs;
	private boolean invincible;
	private boolean inputsEnabled;

    public CGTActor(){
		super();
		jumps = new ArrayList<Jump>();
		projectiles = new ArrayList<CGTProjectile>();
		timeToRecovery = 1;
		timeToEnableInputs = 1;
		this.invincible=false;
	}

    public CGTActor(String label) {
        this();
        setLabel(label);
    }
	
	public boolean addAction(InputPolicy inputPolicy, ActionMovePolicy movePolicy) {
		boolean hasInput = false;
		boolean hasAction = false;
		for (int i = 0; i < actions.size() && (!hasInput || !hasAction); i++) {
			if (actions.get(i).hasInput(inputPolicy)) {
				hasInput = true;
			}
			
			if (actions.get(i).getActionPolicy() == movePolicy) {
				hasAction = true;
			}
		}

		if (!hasAction && !hasInput) {
			actions.add(new Action(movePolicy, inputPolicy));
		}
		
		return false;
	}
	
	public boolean hasAction(String action) {
		for (Action a : actions) {
			if (a.getActionPolicy().equals(action)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasInput(InputPolicy input) {
		for (Action a : actions) {
			if (a.hasInput(input)) {
				return true;
			}
		}
		return false;
	}
	
	public void updateProjectiles(){
		for(int i=0; i < projectiles.size(); i++){
			projectiles.get(i).setPosition(getPosition());
		}
		
	}

	public ArrayList<Jump> getJumps(){
		return jumps;
	}
	public boolean addJump(Jump jump) {
		return jumps.add(jump);
	}
	 
	public boolean removeJump(Jump jump) {
		return jumps.remove(jump);
	}
	 
	public Jump removeJump(int index) {
		return jumps.remove(index);
	}
	 
	public void setJumpDefault(int jumpDefault) {
		this.jumpDefault = jumpDefault;
	}
	 
	public int getJumpDefault() {
		return jumpDefault;
	}
	 
	public void setFireDefault(int fireDefault) {
		this.fireDefault = fireDefault;
	}
	 
	public int getFireDefault() {
		return fireDefault;
	}
	
	public ArrayList<CGTProjectile> getProjectiles(){
		return projectiles;
	}
	public void addProjectile(CGTProjectile projectile) {
		projectile.setOwner(this);
		projectiles.add(projectile);
	}

	public CGTProjectile removeFire(int index) {
		CGTProjectile projectile = projectiles.remove(index);
		projectile.setOwner(null);
		return projectile;
	}
	 
	public boolean removeFire(CGTProjectile fire) {
		if (projectiles.remove(fire)) {
			fire.setOwner(null);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + "CGTActor [jumps="
				+ jumps + ", projectiles=" + projectiles + ", jumpDefault="
				+ jumpDefault + ", fireDefault=" + fireDefault + "]";
	}

	/**
	 * @return the invincible
	 */
	public boolean isInvincible() {
		return invincible;
	}

	/**
	 * @param invincible the invincible to set
	 */
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	/**
	 * @return the commands
	 */
	public boolean isInputsEnabled() {
		return inputsEnabled;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setInputsEnabled(boolean commands) {
		this.inputsEnabled = commands;
	}

	public int getTimeToRecovery() {
		return timeToRecovery;
	}

	public void setTimeToRecovery(int timeToRecovery) {
		this.timeToRecovery = timeToRecovery;
	}

	public int getTimeToEnableInputs() {
		return timeToEnableInputs;
	}

	public void setTimeToEnableInputs(int timeToEnableInputs) {
		this.timeToEnableInputs = timeToEnableInputs;
	}
	
}
 
