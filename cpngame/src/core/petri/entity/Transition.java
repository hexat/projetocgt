package core.petri.entity;

public class Transition {

	private String id;
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.substring(2); // retira a substring ID
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Transition(String id, String text) {
		setId(id);
		setText(text);
	}

	@Override
	public String toString() {
		return "Transition [id=" + id + ", text=" + text + "]";
	}

}
