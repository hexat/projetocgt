package cgt.unit;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.policy.ActionFirePolicy;
import cgt.policy.ActionJumpPolicy;
import cgt.policy.ActionMovePolicy;
import cgt.policy.ActionPolicy;
import cgt.policy.InputPolicy;

public class Action implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6677105553970300995L;
	private ArrayList<InputPolicy> inputs;
	private String actionPolicy;

	private Action(String action, InputPolicy... inputPolicies) {
		this.actionPolicy = action;
		inputs = new ArrayList<InputPolicy>();
		for(int i =0; i<inputPolicies.length; i++){
			inputs.add(inputPolicies[i]);
		}
		
	}

	public Action(ActionPolicy policy, InputPolicy... inputPolicies) {
		this(policy.getName(), inputPolicies);
	}
	
	public boolean hasInput(InputPolicy input) {
		for (InputPolicy i : inputs) {
			if (i == input){
				return true;
			}
		}
		return false;
	}

	public void setActionPolicy(ActionMovePolicy movePolicy) {
		actionPolicy = movePolicy.name();
	}

	public void setActionPolicy(ActionJumpPolicy jumpPolicy) {
		actionPolicy = jumpPolicy.name();
	}

	public void setActionPolicy(ActionFirePolicy movePolicy) {
		actionPolicy = movePolicy.name();
	}
	
	public String getActionPolicy() {
		return actionPolicy;
	}

	public boolean removeInput(InputPolicy input) {
		return inputs.remove(input);
	}
	 
	public InputPolicy removeInput(int index) {
		return inputs.remove(index);
	}
	 
	public ArrayList<InputPolicy> getInputs() {
		return inputs;
	}

	@Override
	public String toString() {
		return "Action [inputs=" + inputs + "]";
	}
	
}
 
