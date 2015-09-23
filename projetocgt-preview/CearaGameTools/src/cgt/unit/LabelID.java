package cgt.unit;

import java.io.Serializable;

public class LabelID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853227723476365553L;
	private String id;

	protected LabelID(String id) {
		this.id = id;
	}
	 
	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		String o = null;
		
		if (obj instanceof String) {
			o = (String) obj;
		} else if (obj instanceof LabelID) {
			o = ((LabelID) obj).getId();
		}
		
		return id.equals(o);
	}

	@Override
	public String toString() {
		return "LabelID [id=" + id + "]";
	}
	
}
 
