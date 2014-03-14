package core.petri;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import core.helper.Lista;
import core.helper.MyInt;
import core.petri.entity.Arc;
import core.petri.entity.Place;
import core.petri.entity.Transition;

public class ElementosCPN {

	ArrayList<Place> places;
	ArrayList<Transition> transitions;
	ArrayList<Arc> arcs;

	private Place matrizPre[][];
	private Place matrizPost[][];
	private Lista matrizToPlace[][];
	private int numMaxPlaceX;
	private int numMaxPlaceY;

	// private int placeToPlace[][];

	// elementos da rede
	Arc arc;
	Arc currentArc;
	Transition transitionShow;
	Transition currentTransitionShowMatrizesPost;
	Transition currentTransitionShowMatrizesPre;
	Transition currentTransitionFillM;

	MyInt val1;
	MyInt val2;

	CpnXmlReader reader;

	public Lista[][] getPlaceToPlace() {
		return matrizToPlace;
	}

	private void fillSaidaPlace() {
		for (int i = 0; i < places.size(); i++) {
			for (int j = 0; j < transitions.size(); j++) {
				if (matrizPre[i][j] != null) {
					for (int k = 0; k < places.size(); k++) {
						if (matrizPost[k][j] != null) {
							if (matrizToPlace[i][k] == null) {
								matrizToPlace[i][k] = new Lista();
							}
							matrizToPlace[i][k].add(j);
						}
					}
				}
			}
		}

		// for (int p = 0; p < matrizToPlace.length; p++) {
		// Place currentPlace = (Place) places.get(p);
		// ArrayList<Place> saida = new ArrayList<Place>();
		// ArrayList<MyInt> pass = new ArrayList<MyInt>();
		// for (int t = 0; t < matrizToPlace[p].length; t++) {
		// // if (matrizC[p][t] < 0)
		// if (matrizToPlace[p][t] == null)
		// for (int k = 0; k < matrizToPlace.length; k++)
		// // if (matrizC[k][t] > 0) {
		// if (matrizToPlace[k][t] != null) {
		// saida.add(places.get(k));
		// val1 = new MyInt(k);
		// pass.add(val1);
		// }
		//
		// }
		// currentPlace.setSaida(saida);
		// currentPlace.setPass(pass);
		// }
		//
		// placeToPlace = new int[places.size()][];
		// for (int i = 0; i < places.size(); i++) {
		// Place currentPlace = (Place) places.get(i);
		// placeToPlace[i] = new int[currentPlace.getPass().size()];
		//
		// System.out.println("Principal -> " + currentPlace.getText());
		// for (int j = 0; j < currentPlace.getPass().size(); j++) {
		// val2 = (MyInt) currentPlace.getPass().get(j);
		// Place saidaPlace = (Place) places.get(val2.getX());
		// placeToPlace[i][j] = val2.getX();
		// System.out.println("Saida -> " + saidaPlace.getText());
		// }
		// }

	}

	public ArrayList<Transition> transicoesHabilitadas() {
		ArrayList<Transition> res = new ArrayList<Transition>();

		for (int index = 0; index < transitions.size(); index++) {
			boolean flag = true;

			ArrayList<Place> lugares = new ArrayList<Place>();
			ArrayList<Arc> arcos = new ArrayList<Arc>();

			for (int i = 0; i < places.size(); i++) {
				if (matrizPre[i][index] != null) {
					lugares.add(matrizPre[i][index]);
					// System.out.println(matrizPre[i][index].hashCode());
					// System.out.println(lugares.get(lugares.size()-1).hashCode());
				}
			}

			for (int i = 0; i < lugares.size(); i++) {
				for (int j = 0; j < arcs.size(); j++) {
					if (arcs.get(j).getPlaceend()
							.equals(lugares.get(i).getId())
							&& arcs.get(j).getTransend()
									.equals(transitions.get(index).getId())
							&& arcs.get(j).getOrientation() == Arc.P_TO_T) {
						arcos.add(arcs.get(j));
						if (!lugares.get(i).contem(arcs.get(j).getPeso())) {
							flag = false;
						}
					}
				}
			}

			if (flag) {
				res.add(transitions.get(index));
			}
		}
		return res;
	}

	public int getNumMaxPlaceX() {
		ArrayList<Integer> s = new ArrayList<Integer>();
		for (Place p : places) {
			if (!s.contains(p.getRelativeLeft())) {
				s.add(p.getRelativeLeft());
				
			}
		}
		return s.size();
	}

	public int getNumMaxPlaceY() {
		ArrayList<Integer> s = new ArrayList<Integer>();
		for (Place p : places) {
			if (!s.contains(p.getRelativeTop())) {
				s.add(p.getRelativeTop());
				
			}
		}
		return s.size();
	}
	
	public Place getPlaceByPos(int x, int y) {
		for (Place p : places) {
			if (p.getRelativeLeft() == x && p.getRelativeTop() == y) {
				return p;
			}
		}
		return null;
		
	}

	public boolean dispararTransicao(Lista lista) {
		
		boolean achei = false;
		Transition trans;
		
		ArrayList<Place> lugares = new ArrayList<Place>();
		ArrayList<Arc> arcos = new ArrayList<Arc>();
		int index = -1;
		int c = 0;
		while (c < lista.size() && !achei) {
			achei = true;
			index = lista.get(c);
			trans = transitions.get(index);
	
			lugares.clear();
			arcos.clear();
	
			for (int i = 0; i < places.size(); i++) {
				if (matrizPre[i][index] != null) {
					lugares.add(matrizPre[i][index]);
					// System.out.println(matrizPre[i][index].hashCode());
					// System.out.println(lugares.get(lugares.size()-1).hashCode());
				}
			}
	
			for (int i = 0; i < lugares.size(); i++) {
				for (int j = 0; j < arcs.size(); j++) {
					if (arcs.get(j).getPlaceend().equals(lugares.get(i).getId())
							&& arcs.get(j).getTransend().equals(trans.getId())
							&& arcs.get(j).getOrientation() == Arc.P_TO_T) {
						arcos.add(arcs.get(j));
						if (!lugares.get(i).contem(arcs.get(j).getPeso())) {
							achei = false;
						}
					}
				}
			}
			c++;
		}
		
		if (achei) {
			trans = transitions.get(index);
			for (int i = 0; i < lugares.size(); i++) {
				for (int j = 0; j < arcos.size(); j++) {
					if (arcos.get(j).getPlaceend().equals(lugares.get(i).getId())) {
						lugares.get(i).remFicha(arcos.get(j).getPeso());
					}
				}
			}
	
			lugares.clear();
			for (int i = 0; i < places.size(); i++) {
				if (matrizPost[i][index] != null) {
					lugares.add(matrizPost[i][index]);
					// System.out.println(matrizPre[i][index].hashCode());
					// System.out.println(lugares.get(lugares.size()-1).hashCode());
				}
			}
			for (int i = 0; i < lugares.size(); i++) {
				for (int j = 0; j < arcs.size(); j++) {
					if (arcs.get(j).getTransend().equals(trans.getId())
							&& lugares.get(i).getId()
									.equals(arcs.get(j).getPlaceend())
							&& arcs.get(j).getOrientation() == Arc.T_TO_P) {
						lugares.get(i).addFichas(arcs.get(j).getPeso());
					}
				}
			}
			return true;
		}
		return false;
	}

	private void ordCrescente(int vet[]) {
		int aux = 0;
		for (int i = vet.length - 1; i >= 0; i--)
			for (int j = 0; j < i; j++)
				if (vet[j] > vet[j + 1]) {
					aux = vet[j + 1];
					vet[j + 1] = vet[j];
					vet[j] = aux;
				}
	}

	private void ordDecrescente(int vet[]) {
		int aux = 0;
		for (int i = vet.length - 1; i >= 0; i--)
			for (int j = 0; j < i; j++)
				if (vet[j] < vet[j + 1]) {
					aux = vet[j + 1];
					vet[j + 1] = vet[j];
					vet[j] = aux;
				}
	}

	private int[] vetXOrd() {
		Place currentPlace;
		int vet[] = new int[places.size()];
		for (int i = 0; i < places.size(); i++) {
			currentPlace = (Place) places.get(i);
			vet[i] = currentPlace.getX();
		}
		ordCrescente(vet);
		numMaxPlaceX = vet.length;
		return vet;
	}

	private int[] vetYOrd() {
		Place currentPlace;
		int vet[] = new int[places.size()];
		for (int i = 0; i < places.size(); i++) {
			currentPlace = (Place) places.get(i);
			vet[i] = currentPlace.getY();
		}
		ordDecrescente(vet);
		numMaxPlaceY = vet.length;
		return vet;
	}

	private void geraTopList() {
		Place currentPlace;
		int vetY[] = vetYOrd();

		for (int i = 0; i < places.size(); i++) {
			currentPlace = (Place) places.get(i);
			int index = 0;
			int anterior = vetY[0];
			for (int j = 0; j < vetY.length; j++) {
				if (anterior != vetY[j])
					index++;
				if (vetY[j] == currentPlace.getY()) {
					currentPlace.setRelativeTop(index);
					break;
				}
				anterior = vetY[j];
			}
		}
	}

	private void geraLeftList() {
		Place currentPlace;
		int vetX[] = vetXOrd();

		for (int i = 0; i < places.size(); i++) {
			currentPlace = (Place) places.get(i);
			int index = 0;
			int anterior = vetX[0];
			for (int j = 0; j < vetX.length; j++) {
				if (anterior != vetX[j])
					index++;
				if (vetX[j] == currentPlace.getX()) {
					currentPlace.setRelativeLeft(index);
					break;
				}
				anterior = vetX[j];
			}
		}
	}

	public void showRelativeTopLeft() {
		Place currentPlace = null;
		for (int i = 0; i < places.size(); i++) {
			currentPlace = (Place) places.get(i);
			System.out.println(currentPlace.getText() + ", " + "("
					+ currentPlace.getX() + ", " + currentPlace.getY() + ")  "
					+ "(" + currentPlace.getRelativeLeft() + ", "
					+ currentPlace.getRelativeTop() + ")");
		}
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public int indexTransition(String text) {
		int res = -1;

		for (int i = 0; i < transitions.size(); i++) {
			if (transitions.get(i).getText().equals(text))
				return i;
		}

		return res;
	}

	public ArrayList<Arc> getArcs() {
		return arcs;
	}

	public Place[][] getMatrizPre() {
		return matrizPre;
	}

	public Place[][] getMatrizPost() {
		return matrizPost;
	}

	public Lista[][] getMatrizC() {
		return matrizToPlace;
	}

	public ElementosCPN(InputStream xml) {
		try {

			// informe o caminho correto do seu arquivo xm cpn tools
//			reader = new CpnXmlReader("../src/res/cpn/GameTesteBombeiro.cpn"); // teste
//			reader = new CpnXmlReader("../src/res/cpn/GameTesteCarro.cpn"); // teste
//			reader = new CpnXmlReader("../src/res/cpn/GameTesteCafe.cpn"); // teste
			//reader = new CpnXmlReader("../src/res/cpn/IN_ON_AT.cpn"); // teste
			reader = new CpnXmlReader(xml); // teste

			/**
			 * quando tiver a interface de escolha da rede, colocar o caminho
			 * absoluto na variável "path"
			 */
			// String path = "";
			// CpnXmlReader reader = new CpnXmlReader( path );

			places = reader.lerPlaces();
			transitions = reader.lerTransitions();
			arcs = reader.lerArcs();

			geraLeftList();
			geraTopList();

			fillMatrizes();

			fillSaidaPlace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// (erro da leitura das fichas coloridas para preencher a matriz): erro
	// resolvido
	private void fillMatrizes() {
		matrizPost = new Place[places.size()][transitions.size()];
		matrizPre = new Place[places.size()][transitions.size()];
		matrizToPlace = new Lista[places.size()][places.size()];

		for (int i = 0; i < places.size(); i++) {
			for (int j = 0; j < places.size(); j++) {
				matrizToPlace[i][j] = null;
			}
		}
		/*
		 * System.out.println("imprimir os arcos: "); for(int v = 0;
		 * v<arcs.size();v++) System.out.println("arco "+v+": "+arcs.get(v));
		 * System.out.println("-----------");
		 */
		for (int p = 0; p < matrizPost.length; p++) {
			Place currentPlace = (Place) places.get(p);
			for (int t = 0; t < matrizPost[p].length; t++) {
				currentTransitionFillM = (Transition) transitions.get(t);
				String idPlace = currentPlace.getId();
				String idTrans = currentTransitionFillM.getId();

				matrizPost[p][t] = null;
				matrizPre[p][t] = null;
				for (int a = 0; a < arcs.size(); a++) {
					currentArc = (Arc) arcs.get(a);
					String transend = currentArc.getTransend();
					String placeend = currentArc.getPlaceend();
					int orientation = currentArc.getOrientation();

					// System.out.println("texto do current arc em "+a+": "+pesoArco);

					if (idTrans.equals(transend) && idPlace.equals(placeend)) {
						if (orientation == Arc.T_TO_P)
							matrizPost[p][t] = currentPlace;
						else if (orientation == Arc.P_TO_T) {
							// if ( textoArco )
							matrizPre[p][t] = currentPlace;
						}
					}
				}
			}
		}

	}

	public void showMatrizC() {
		System.out.println("\n*** Matriz C ***");
		for (int p = 0; p < matrizToPlace.length; p++) {
			System.out.println();
			Place currentPlace = (Place) places.get(p);
			String textPLace = currentPlace.getText();
			for (int t = 0; t < matrizToPlace[p].length; t++) {
				String textTrans = places.get(t).getText();
				System.out.print("[" + textPLace + "," + textTrans + "]="
						+ matrizToPlace[p][t] + "\t");
			}
		}
		System.out.println();
		System.out.println();
	}

	public void showMatrizPre() {
		System.out.println("\n*** Matriz Pre ***");
		for (int p = 0; p < matrizPre.length; p++) {
			System.out.println();
			Place currentPlace = (Place) places.get(p);
			String textPLace = currentPlace.getText();
			for (int t = 0; t < matrizPre[p].length; t++) {
				currentTransitionShowMatrizesPre = (Transition) transitions
						.get(t);
				String textTrans = currentTransitionShowMatrizesPre.getText();
				System.out.print("[" + textPLace + "," + textTrans + "]="
						+ matrizPre[p][t] + "\t");
			}
		}
		System.out.println();
		System.out.println();
	}

	public void showMatrizPost() {
		System.out.println("\n*** Matriz Post ***");
		for (int p = 0; p < matrizPost.length; p++) {
			System.out.println();
			Place currentPlace = (Place) places.get(p);
			String textPLace = currentPlace.getText();
			for (int t = 0; t < matrizPost[p].length; t++) {
				currentTransitionShowMatrizesPost = (Transition) transitions
						.get(t);
				String textTrans = currentTransitionShowMatrizesPost.getText();
				System.out.print("[" + textPLace + "," + textTrans + "]="
						+ matrizPost[p][t] + "\t");
			}
		}
		System.out.println();
		System.out.println();
	}

	// imprime na tela os places
	public void showPlaces() {
		System.out.println("Num Places " + places.size());
		for (int i = 0; i < places.size(); i++) {
			Place place = (Place) places.get(i);

			System.out.println(place.toString());
		}
	}

	// imprime na tela os arcs
	public void showArcs() {
		System.out.println("Num arcs " + arcs.size());
		for (int i = 0; i < arcs.size(); i++) {
			arc = (Arc) arcs.get(i);

			System.out.println(arc.toString());
		}
	}

	// imprime na tela os transitions
	public void showTransitions() {
		System.out.println("Num transitions " + transitions.size());
		for (int i = 0; i < transitions.size(); i++) {
			transitionShow = (Transition) transitions.get(i);

			System.out.println(transitionShow.toString());
		}

	}
	
}

/*
 * public Place down(Place p) { Vector downPlaces = new Vector();
 * 
 * Place currentPlace = null; for (int i=0; i<places.size(); i++) { currentPlace
 * = (Place) places.get(i); if (p.getY() > currentPlace.getY())
 * downPlaces.addElement(currentPlace); } if (downPlaces.size()>0) return
 * topLeft(downPlaces); else return null; }
 */

/*
 * public Place left(Place p) {
 * 
 * Vector leftPlaces = new Vector();
 * 
 * Place currentPlace = null; for (int i=0; i<places.size(); i++) { currentPlace
 * = (Place) places.get(i); if (p.getX() < currentPlace.getX())
 * leftPlaces.addElement(currentPlace); } if (leftPlaces.size()>0) return
 * topLeft(leftPlaces); else return null;
 * 
 * }
 */

/*
 * public Place topLeft(Vector places) { Place currentPlace = (Place)
 * places.get(0);
 * 
 * int top= currentPlace.getY(); int left= currentPlace.getX(); double distMaior
 * = Math.sqrt(top*top+left*left); int indexPlace=0;
 * 
 * for (int i = 1; i<places.size(); i++) { currentPlace = (Place) places.get(i);
 * top= currentPlace.getY(); left= currentPlace.getX();
 * 
 * //if (top>=0 && left<=0) {
 * 
 * double distCurrent = Math.sqrt(top*top+left*left);
 * 
 * if (distMaior<distCurrent) { indexPlace = i; distMaior=distCurrent; //} } }
 * 
 * return (Place) places.get(indexPlace); }
 */

