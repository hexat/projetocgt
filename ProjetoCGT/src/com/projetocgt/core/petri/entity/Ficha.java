package com.projetocgt.core.petri.entity;

import java.util.ArrayList;

public class Ficha {

	private int fichas;
	private ArrayList<String> cor; // nome da ficha
			
	public Ficha() {
		super();
		fichas = 0;
		cor = new ArrayList<String>();
	}

	public Ficha(int fichas, ArrayList<String> cor) {
		super();
		this.fichas = fichas;
		this.cor = cor;
	}

	public int getFichas() {
		return fichas;
	}

	public void setFichas(int fichas) {
		this.fichas = fichas;
	}

	public ArrayList<String> getCor() {
		return cor;
	}

	public void setCor(ArrayList<String> cor) {
		this.cor = cor;
	}

	public void addCor(String c) {
		this.cor.add(c.trim());
	}

	@Override
	public String toString() {
		return "Ficha [fichas=" + fichas + ", cor=" + cor + "]";
	}

	public Ficha diff(Ficha ficha) {

		if (compararCor(ficha.getCor())) {
			int diff = fichas - ficha.getFichas();
			if (diff > 0) {
				return new Ficha(diff, cor);
			}
		}

		return null;
	}

	public boolean compararCor(ArrayList<String> corp) {
		if (cor.size() == corp.size()) {
			for (int i = 0; i < cor.size(); i++) {
				if (!cor.get(i).equals(corp.get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean compararCor(Ficha ficha) {
		return compararCor(ficha.getCor());
	}

	public Ficha clone() {
		Ficha res = new Ficha();

		res.setFichas(fichas);
		for (String c : cor)
			res.addCor(c);

		return res;
	}

	public boolean menorIgual(Ficha ficha) {

		if (compararCor(ficha.getCor())) {
			int diff = fichas - ficha.getFichas();
			if (diff >= 0) {
				return true;
			}
		}

		return false;
	}

	public void addFicha(int ficha) {
		fichas += ficha;
	}

}