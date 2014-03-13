package com.projetocgt.cenario;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projetocgt.personagens.Personagem;

import core.petri.ElementosCPN;

public class World {
	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** Our player controlled hero **/
	Personagem personagem;
	private float width;
	private float height;
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
		this.width = cpn.getNumMaxPlaceX();
		this.height = cpn.getNumMaxPlaceY();
		createDemoWorld();
	}

	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}

	private void createDemoWorld() {
		//Posicao inicial do personagem 
		personagem = new Personagem(new Vector2(0, 0));
		//Constroe o cenario,preenche uma matriz.
		//"i" colunas.
		//"j" linhas.
		for (int i = 0; i < width; i++) {
			for(int j = 0;j< height;j++){
				blocks.add(new Block(new Vector2(i, j), cpn.getPlaceByPos(i, i)));
			} 			 			
		}
	}
}
