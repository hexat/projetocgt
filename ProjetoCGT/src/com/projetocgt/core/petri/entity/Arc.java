package com.projetocgt.core.petri.entity;

public class Arc {

	public static int P_TO_T = 0;
	public static int T_TO_P = 1;

	private String id;
	private int orientation;
	private String transend; // idRef
	private String placeend; // idRef
	private Ficha peso;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.substring(2); // retira a substring ID
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public String getTransend() {
		return transend;
	}

	public void setTransend(String transend) {
		this.transend = transend.substring(2); // retira a substring ID
	}

	public String getPlaceend() {
		return placeend;
	}

	public void setPlaceend(String placeend) {
		this.placeend = placeend.substring(2); // retira a substring ID
	}

	public Ficha getPeso() {
		return peso;
	}

	public void setPeso(String text) {
		Ficha ficha = null;
		text = text.replace("\n", "");
		String part[], fichas[] = text.split("\\+\\+");

		for (String f : fichas) {
			ficha = new Ficha();
			part = f.split("`");

			if (part.length == 1) {
				part = new String[] { "1", part[0] };
			}

			ficha.setFichas(Integer.parseInt(part[0]));
			part[1] = part[1].replace("(", "").replace(")", "");
			for (String s : part[1].split(",")) {
				ficha.addCor(s);
			}

		}
		peso = ficha;
	}

	@Override
	public String toString() {
		return "Arc [id=" + id + ", transend=" + transend + ", placeend="
				+ placeend + ", peso=" + peso + ", orientation=" + orientation
				+ "]";
	}

	public Arc(String id, int orientation, String transend, String placeend,
			String peso) {
		setId(id);
		setOrientation(orientation);
		setTransend(transend);
		setPlaceend(placeend);
		setPeso(peso);
	}

}
