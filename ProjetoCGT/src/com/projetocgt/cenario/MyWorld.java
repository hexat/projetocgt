package com.projetocgt.cenario;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.projetocgt.Projectile;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.SpriteSheet;

public class MyWorld {
	/** The blocks making up the world **/
	Array<Personagem> listaPersonagens = new Array<Personagem>();
	ArrayList<Projectile> listaDeProjectili = new ArrayList<Projectile>();
	/** Our player controlled hero **/
	private Personagem personagem;
	private Texture backGround;
	private float numBlocosH;
	private float numBlocosV;
	
	public Personagem getPersonagem() {
		return personagem;
	}
	
	public MyWorld() {
		//this.numBlocosH = cpn.getNumMaxPlaceX();
		//this.numBlocosV = cpn.getNumMaxPlaceY();
		this.numBlocosH = 3;
		this.numBlocosV = 3;
		createWorld();
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
		personagem.setSprite(new SpriteSheet());
		
		Personagem opositor = new Personagem(new Vector2(185f, 105f),3,true,0, 0.3f,0.3f,0,0);
		opositor.setTexturePersonagem(new  Texture(Gdx.files.internal("data/Carros/carro.png")));
		listaPersonagens.add(opositor);
		
		Personagem opositorFogo = new Personagem(new Vector2(400,100), 3, true, 0, 150,150f,0,0);
		//opositorFogo.setTexturePersonagem();
		//listaPersonagens.add(opositorFogo);
		
		//Adiniona uma aniamcao 
		
		//Constroe o cenario com agua
		Personagem agua = new Personagem(new Vector2(225f, 225f), 0, false, 0, 150, 150f, 0, 0); 
		agua.setTexturePersonagem(new Texture("data/piscina.png"));
		listaPersonagens.add(agua);
		
		//Constroe a casa
		Personagem casa = new Personagem(new Vector2(450f, 400f), 0,false, 0, 300, 240f,25f,40f);
		casa.setTexturePersonagem(new Texture("data/Cenario/casa_sprite_sheet.png"));
		listaPersonagens.add(casa);
		
		//
		Projectile projetilAgua = new Projectile(new Vector2(400f, 400f),100);
		projetilAgua.setTextura(new Texture("data/Sprites/SpriteSheet_agua.png"));
		listaDeProjectili.add(projetilAgua);
	}

	
	/**
	 * @return the rigido
	 */
	public Array<Personagem> getListaPersonagens() {
		return listaPersonagens;
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

	/**
	 * @return the listaDeProjectili
	 */
	public ArrayList<Projectile> getListaDeProjectili() {
		return listaDeProjectili;
	}

	/**
	 * @param listaDeProjectili the listaDeProjectili to set
	 */
	public void setListaDeProjectili(ArrayList<Projectile> listaDeProjectili) {
		this.listaDeProjectili = listaDeProjectili;
	}
	
}
