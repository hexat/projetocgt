package com.projetocgt.cenario;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projetocgt.personagens.Personagem;

public class World {
	/** The blocks making up the world **/
	Array blocks = new Array();
	/** Our player controlled hero **/
	Personagem personagem;

	// Getters -----------
	public Array getBlocks() {
		return blocks;
	}
	public Personagem getPersonagem() {
		return personagem;
	}
	// --------------------

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		//Posiçao inicial do personagem 
		personagem = new Personagem(new Vector2(7, 2));
		//Constroe o cenario,preenche uma matriz.
		//"i" colunas.
		//"j" linhas.
		for (int i = 0; i < 10; i++) {
			for(int j = 0;j<7;j++){
				blocks.add(new Block(new Vector2(i, j)));
			} 			 			
		}
	}
}
