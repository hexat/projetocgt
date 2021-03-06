package cgt.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;
import cgt.unit.Action;
import cgt.util.Jump;


public class CGTActor extends CGTGameObject {
	private ArrayList<Jump> jumps;
	private ArrayList<Action> actions;
	private ArrayList<CGTProjectile> projectiles;
	private int jumpDefault;
	private int fireDefault;
	private int timeToRecovery;
	private int timeToEnableInputs;
	private boolean invincible;
	private boolean inputsEnabled;
	private boolean fireActivate;

    public CGTActor(){
		super();
		jumps = new ArrayList<Jump>();
		projectiles = new ArrayList<CGTProjectile>();
		actions = new ArrayList<Action>();
        fireDefault = -1;
		timeToRecovery = 1;
		timeToEnableInputs = 1;
		this.invincible=false;
		fireActivate = false;
        inputsEnabled = true;
	}

    public CGTActor(String label) {
        this();
        setId(label);
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

			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasAction(ActionMovePolicy action) {
		for (Action a : actions) {
			if (a.getActionPolicy() == action) {
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

    public List<InputPolicy> getAvailableInputs() {
        List<InputPolicy> res = new ArrayList<InputPolicy>();

        for (InputPolicy inputPolicy : InputPolicy.values()) {
            if (!hasInput(inputPolicy)) {
                res.add(inputPolicy);
            }
        }
        return res;
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
		projectiles.add(projectile);
	}

	public boolean removeFire(CGTProjectile fire) {
		return projectiles.remove(fire);
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
	
	

	public boolean isFireActivate() {
		return fireActivate;
	}

	public void setFireActivate(boolean fireActivate) {
		this.fireActivate = fireActivate;
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

	public Map<InputPolicy, ActionMovePolicy> getActions() {
		Map<InputPolicy, ActionMovePolicy> res = new HashMap<InputPolicy, ActionMovePolicy>();

		for (Action a : actions) {
			for (InputPolicy i : a.getInputs()) {
				res.put(i, a.getActionPolicy());
			}
		}

		return  res;
	}

	public void removeInputFromAction(InputPolicy inputPolicy) {
		int i = 0;
		while (i < actions.size()) {
			actions.get(i).removeInput(inputPolicy);
			if (actions.get(i).getInputs().isEmpty()) {
				actions.remove(i);
				i = 0;
			} else {
				i++;
			}
		}
	}

	public Action getActionFromInput(InputPolicy policy) {
		for (Action action : actions) {
			if (action.hasInput(policy)) {
				return action;
			}
		}
		return null;
	}

    public CGTProjectile findProjectile(String value) {
        for (CGTProjectile p : projectiles) {
            if (p.getId().equals(value)) {
                return p;
            }
        }
        return null;
    }

    public CGTProjectile getProjectileDefault() {
        if (fireDefault >= 0 && fireDefault < projectiles.size()) {
            return projectiles.get(fireDefault);
        }
        return null;
    }

    public List<ActionMovePolicy> getAvailableActionsMove() {
        List<ActionMovePolicy> res = new ArrayList<ActionMovePolicy>();

        for (ActionMovePolicy a : ActionMovePolicy.values()) {
            if (!hasAction(a)) {
                res.add(a);
            }
        }

        return res;
    }
}
 
