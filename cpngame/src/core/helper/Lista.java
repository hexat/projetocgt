package core.helper;

import java.util.ArrayList;

// teste

public class Lista {

	private ArrayList<Integer> lista;

	public Lista() {
		super();
		lista = new ArrayList<Integer>();
	}

	public void add(int a) {
		lista.add(a);
	}
	
	public int get(int index) {
		return lista.get(index);
	}
	
	public int size() {
		return lista.size();
	}
	
}
