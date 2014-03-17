package com.projetocgt.cenario;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projetocgt.core.petri.ElementosCPN;
import com.projetocgt.personagens.Personagem;

public class World {
	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** Our player controlled hero **/
	Personagem personagem;
	private float numBlocosH;
	private float numBlocosV;
	private ElementosCPN cpn;
	// Getters -----------
	public Array<Block> getBlocks() {
		return blocks;
	}
	public Personagem getPersonagem() {
		return personagem;
	}
	// --------------------

	public World(ElementosCPN cpn) {
		this.cpn = cpn;
		this.numBlocosH = cpn.getNumMaxPlaceX();
		this.numBlocosV = cpn.getNumMaxPlaceY();

		createDemoWorld();
	}

	public float getNumBlocosH() {
		return numBlocosH;
	}
	public float getNumBlocosV() {
		return numBlocosV;
	}

	private void createDemoWorld() {
		//Posicao inicial do personagem 
		personagem = new Personagem(new Vector2(0, 0));
		//Constroe o cenario,preenche uma matriz.
		//"i" colunas.
		//"j" linhas.
		for (int i = 0; i < numBlocosH; i++) {
			for(int j = 0;j< numBlocosV;j++){
				blocks.add(new Block(new Vector2(i, j), cpn.getPlaceByPos(i, i)));
			} 			 			
		}
	}
}
