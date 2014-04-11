package com.projetocgt.cenario;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projetocgt.core.petri.ElementosCPN;
import com.projetocgt.personagens.Personagem;

public class MyWorld {
	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** Our player controlled hero **/
	private Personagem personagem;
	private Personagem opositor;
	private Personagem opositor2;
	private Personagem agua;
	private Joystick setaBaixo;
	private Joystick setaDireita;
	private Joystick setaCima;
	private Joystick setaEsquerda;
	private float numBlocosH;
	private float numBlocosV;
	private ElementosCPN cpn;
	// Getters -----------
	public Array<Block> getBlocks() {
		return blocks;
	}

	public Block getBlock(int x, int y) {
		for (Block b : blocks) {
			if (((int)b.getPosition().x) == x && ((int) b.getPosition().y) == y) {
				return b;
			}
		}
		return null;
	}

	public Personagem getPersonagem() {
		return personagem;
	}
	
	public Personagem getOpositor() {
		return this.opositor;
	}
	
	public Joystick getJoystickBaixo() {
		return this.setaBaixo;
	}
	
	public Joystick getJoystickDireita() {
		return this.setaDireita;
	}
	
	public Joystick getJoystickCima() {
		return this.setaCima;
	}
	
	public Joystick getJoystickEsquerda() {
		return this.setaEsquerda;
	}

	public MyWorld(ElementosCPN cpn) {
		this.cpn = cpn;

		//this.numBlocosH = cpn.getNumMaxPlaceX();
		//this.numBlocosV = cpn.getNumMaxPlaceY();
		this.numBlocosH = 3;
		this.numBlocosV = 3;
		createWorld();
	}
	
	public ElementosCPN getElementosCPN() {
		return cpn;
	}

	public float getNumBlocosH() {
		return numBlocosH;
	}
	
	public float getNumBlocosV() {
		return numBlocosV;
	}

	private void createWorld() {
		//Posicao inicial do personagem 
		personagem = new Personagem(new Vector2(0, 0),3,false,0);
		//Posicao inicial do opositor
		//a posicao do y aumenta na subida 
		opositor = new Personagem(new Vector2(this.numBlocosH-2.15f,this.numBlocosV-0.5f),3,true,0);
		setOpositor2(new Personagem(new Vector2(this.numBlocosH-1.1f,this.numBlocosV - 0.1f),3,true,0));
		//Cria os Joysticks
		setaBaixo = new Joystick(new Vector2(2, 2));
		setaDireita = new Joystick(new Vector2(1, 1));
		setaCima = new Joystick(new Vector2(2, 2));
		setaEsquerda = new Joystick(new Vector2(0, 0));
		//Constroe o cenario com agua
		agua = new Personagem(new Vector2(0, 2), 0,false, 0); 
		//Constroe o cenario,preenche uma matriz.
		//"i" colunas.
		//"j" linhas.
		for (int i = 0; i < numBlocosH; i++) {
			for(int j = 0;j < numBlocosV;j++){
				blocks.add(new Block(new Vector2(i, j), cpn.getPlaceByPos(i, j)));
			} 			 			
		}
	}

	public Personagem getOpositor2() {
		return opositor2;
	}
	
	public void setOpositor2(Personagem opositor2) {
		this.opositor2 = opositor2;
	}

	public Personagem getAgua() {
		return agua;
	}

	public void setAgua(Personagem agua) {
		this.agua = agua;
	}

	
}
