package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projetocgt.core.petri.ElementosCPN;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.SpritePersonagem;

public class MyWorld {
	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	Array<Personagem> listaPersonagens = new Array<Personagem>();
	/** Our player controlled hero **/
	private Personagem personagem;
	private Personagem opositor;
	private Personagem agua;
	private Personagem opositorFogo;
	private Personagem casa;
	private Texture backGround;
	private SpritePersonagem sprite;
	private Joystick setaBaixo;
	private Joystick setaDireita;
	private Joystick setaCima;
	private Joystick setaEsquerda;
	private float numBlocosH;
	private float numBlocosV;
	private ElementosCPN cpn;
	
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
	/***
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		backGround = new Texture(Gdx.files.internal("data/Cenario/asfalto_grama_sprite_sheet.png"));
		personagem = new Personagem(new Vector2(400, 400),3,false,0, 100f,50f,25f,25f);
		
		opositor = new Personagem(new Vector2(185f, 105f),3,true,0, 0.3f,0.3f,0,0);
		opositor.setTexturePersonagem(new  Texture(Gdx.files.internal("data/Carros/carro.png")));
		listaPersonagens.add(opositor);
		
		//Cria os Joysticks
		setaBaixo = new Joystick(new Vector2(2, 2));
		setaDireita = new Joystick(new Vector2(1, 1));
		setaCima = new Joystick(new Vector2(2, 2));
		setaEsquerda = new Joystick(new Vector2(0, 0));
		
		opositorFogo = new Personagem(new Vector2(400,100), 3, true, 0, 150,150f,0,0);
		//opositorFogo.setTexturePersonagem();
		//listaPersonagens.add(opositorFogo);
		
		//Adiniona uma aniamcao 
		sprite = new SpritePersonagem();
		
		//Constroe o cenario com agua
		agua = new Personagem(new Vector2(225f, 225f), 0,false, 0, 150,150f,0,0); 
		agua.setTexturePersonagem(new Texture("data/piscina.png"));
		listaPersonagens.add(agua);
		
		//Constroe a casa
		casa = new Personagem(new Vector2(450f, 400f), 0,false, 0, 300, 240f,25f,40f);
		casa.setTexturePersonagem(new Texture("data/Cenario/casa_sprite_sheet.png"));
		listaPersonagens.add(casa);
		
		//Constroe o cenario,preenche uma matriz.
		//"i" colunas.
		//"j" linhas.
		for (int i = 0; i < numBlocosH; i++) {
			for(int j = 0;j < numBlocosV;j++){
				blocks.add(new Block(new Vector2(i, j), cpn.getPlaceByPos(i, j)));
			} 			 			
		}
		
		
	}

	public Personagem getAgua() {
		return agua;
	}

	public void setAgua(Personagem agua) {
		this.agua = agua;
	}

	public SpritePersonagem getSprite() {
		return sprite;
	}

	public void setSprite(SpritePersonagem sprite) {
		this.sprite = sprite;
	}

	public Personagem getOpositorFogo() {
		return opositorFogo;
	}

	public void setOpositorFogo(Personagem opositorFogo) {
		this.opositorFogo = opositorFogo;
	}

	/**
	 * @return the rigido
	 */
	public Array<Personagem> getListaPersonagens() {
		return listaPersonagens;
	}

	/**
	 * @return the casa
	 */
	public Personagem getCasa() {
		return casa;
	}

	/**
	 * @param casa the casa to set
	 */
	public void setCasa(Personagem casa) {
		this.casa = casa;
	}

	/**
	 * @return the backGround
	 */
	public Texture getBackGround() {
		return backGround;
	}

	/**
	 * @param backGround the backGround to set
	 */
	public void setBackGround(Texture backGround) {
		this.backGround = backGround;
	}
	
}