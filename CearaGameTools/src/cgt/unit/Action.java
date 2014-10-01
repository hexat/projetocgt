package cgt.unit;

import java.io.Serializable;
import java.util.ArrayList;

import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;

public class Action implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6677105553970300995L;
	private ArrayList<InputPolicy> inputs;
	private ActionMovePolicy actionPolicy;

	public Action(ActionMovePolicy action, InputPolicy... inputPolicies) {
		this.actionPolicy = action;
		inputs = new ArrayList<InputPolicy>();
		for(int i =0; i<inputPolicies.length; i++){
			inputs.add(inputPolicies[i]);
		}
		
	}
	
	public boolean hasInput(InputPolicy input) {
		for (InputPolicy i : inputs) {
			if (i == input){
				return true;
			}
		}
		return false;
	}

	public ActionMovePolicy getActionPolicy() {
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
 
