package cgt.unit;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelIDSingleton implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7410811987173585101L;

	private static LabelIDSingleton instance = null;
	
	private ArrayList<LabelID> ids;
	 
	private LabelIDSingleton() {
		ids = new ArrayList<LabelID>();
	}
	
	public static LabelIDSingleton getInstance() {
		if (instance == null) {
			instance = new LabelIDSingleton();
		}
		return instance;
	}
	 
	public LabelID addId(String id) {
		for(LabelID label:ids){
			if(label.equals(id))
				return null;
		}
		
		LabelID i = new LabelID(id);
		ids.add(i);
		return i;
	}
	 
	public boolean hasId(String id) {
		return ids.contains(id);
	}
	
	public boolean removeID(LabelID id){
		return ids.remove(id);
	}

	@Override
	public String toString() {
		return "LabelIDSingleton [ids=" + ids + "]";
	}
	 
}
 
