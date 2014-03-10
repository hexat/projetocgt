package core.petri.entity;

import java.util.ArrayList;

import core.helper.MyInt;

public class Place {

	private String id;
	private String text;
	private int x;
	private int y;
	private ArrayList<Place> saida;
	private ArrayList<MyInt> pass;
	private ArrayList<String> tipo;

	ArrayList<Ficha> fichas;
	ArrayList<Ficha> initmark;

	public Place(String id, String text, /* String tipoDeFicha, */
			String initmark, String tipo, int x, int y) {
		fichas = new ArrayList<Ficha>();
		this.initmark = new ArrayList<Ficha>();
		this.tipo = new ArrayList<String>();

		setId(id);
		setText(text);
		setInitmark(initmark);
		setTipo(tipo);
		setX(x);
		setY(y);
	}

	public void setTipo(String tipo) {
		this.tipo.clear();
		String[] t = tipo.split("x");
		for (String tadd : t) {
			this.tipo.add(tadd);
		}
	}

	// teste inicial com fichas coloridas
	public void addFichas(Ficha ficha) {
		for (int i = 0; i < fichas.size(); i++) {
			if (fichas.get(i).compararCor(ficha)) {
				int s = ficha.getFichas();
				fichas.get(i).addFicha(s);
				return;
			}
		}

		fichas.add(ficha.clone());
	}

	// teste inicial com fichas coloridas
	public void addFichas(ArrayList<Ficha> f) {
		for (int i = 0; i < f.size(); i++)
			fichas.add(f.get(i));
	}

	// teste inicial com fichas coloridas
	// public void addFichas(String tipo, String nome) {
	// Ficha f = new Ficha(tipo, nome);
	// fichas.add(f);
	// }

	// teste inicial com fichas coloridas
	public ArrayList<Ficha> getTodasAsFichas() {
		return fichas;
	}

	// teste inicial com fichas coloridas
	public Ficha getFichaNaPosicaoX(int X) {
		return fichas.get(X);
	}

	// teste inicial com fichas coloridas
	public ArrayList<Ficha> getFichasDoTipoX(String X) {
		ArrayList<Ficha> fichasDoMesmoTipo = new ArrayList<Ficha>();

		for (int i = 0; i < fichas.size(); i++)
			fichasDoMesmoTipo.add(fichas.get(i));

		if (fichasDoMesmoTipo.size() == 0)
			return null;
		else
			return fichasDoMesmoTipo;
	}

	public ArrayList<MyInt> getPass() {
		return pass;
	}

	public void setPass(ArrayList<MyInt> pass) {
		this.pass = pass;
	}

	public ArrayList<Place> getSaida() {
		return saida;
	}

	public void setSaida(ArrayList<Place> saida) {
		this.saida = saida;
	}

	private int relativeTop;

	private int relativeLeft;

	public int getRelativeTop() {
		return relativeTop;
	}

	public void setRelativeTop(int relativeTop) {
		this.relativeTop = relativeTop;
	}

	public int getRelativeLeft() {
		return relativeLeft;
	}

	public void setRelativeLeft(int relativeLeft) {
		this.relativeLeft = relativeLeft;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public ArrayList<Ficha> getFichas() {
		return fichas;
	}

	public void setFichas(ArrayList<Ficha> fichas) {
		this.fichas = fichas;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ArrayList<Ficha> getInitmark() {
		return initmark;
	}

	public void setInitmark(String initmark) {
		Ficha ficha;
		if (initmark == null)
			return;

		initmark = initmark.replace("\n", "");
		String part[], fichas[] = initmark.split("\\+\\+");

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
			if (ficha.getFichas() > 0)
				this.initmark.add(ficha);
		}

		for (Ficha f : this.initmark) {
			this.fichas.add(f.clone());
		}

	}

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

	public boolean contem(Ficha ficha) {
		if (fichas.isEmpty())
			return false;

		for (Ficha f : fichas) {
			if (f.menorIgual(ficha) == true)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Place [id =" + getId() + ", " + "text = " + getText() + ", "
				+ "mark = " + getFichas() + ", " + "type = " + getType() + ", "
				+ "x = " + getX() + ", " + "y = " + getY() + "]";
	}

	public ArrayList<String> getType() {
		return tipo;
	}

	public void remFicha(Ficha peso) {
		Ficha nova = null;
		for (int i = 0; i < fichas.size(); i++) {
			if (fichas.get(i).compararCor(peso) == true) {
				nova = fichas.get(i).diff(peso);
				if (nova == null) {
					fichas.remove(i);
				} else {
					fichas.get(i).setFichas(nova.getFichas());
				}
			}
		}
	}

	public int getTotalFichas() {
		int res = 0;
		for (Ficha f : fichas) {
			res += f.getFichas();
		}
		return res;
	}

}
