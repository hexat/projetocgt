package com.projetocgt.cenario;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.CGTActor;
import com.projetocgt.personagens.CGTProjectile;
import com.projetocgt.personagens.SpriteSheet;

public class MyWorld {
	
	ArrayList<CGTActor> listaActor = new ArrayList<CGTActor>();
	ArrayList<CGTProjectile> listaDeProjectile = new ArrayList<CGTProjectile>();
	private CGTActor personagem;
	private Texture backGround;
	private float numBlocosH;
	private float numBlocosV;
	
	public CGTActor getPersonagem() {
		return personagem;
	}
	
	public MyWorld() {
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
		
		personagem = new CGTActor(new Vector2(400, 400),3,0, 100f,50f,25f,25f);
		personagem.setSpeed(180);
		personagem.setSprite(new SpriteSheet());
		
		CGTActor opositor = new CGTActor(new Vector2(185f, 105f),3,0, 0.3f,0.3f,0,0);
		opositor.setTexturePersonagem(new  Texture(Gdx.files.internal("data/Carros/carro.png")));
		listaActor.add(opositor);
		
		CGTActor opositorFogo = new CGTActor(new Vector2(400,100), 3, 0, 150,150f,0,0);
		//opositorFogo.setTexturePersonagem();
		//listaPersonagens.add(opositorFogo);
		
		//Adiniona uma aniamcao 
		
		//Constroe o cenario com agua
		CGTActor agua = new CGTActor(new Vector2(225f, 225f), 0, 0, 150, 150f, 0, 0); 
		agua.setTexturePersonagem(new Texture("data/piscina.png"));
		listaActor.add(agua);
		
		//Constroe a casa
		CGTActor casa = new CGTActor(new Vector2(450f, 400f), 0, 0, 300, 240f,25f,40f);
		casa.setTexturePersonagem(new Texture("data/Cenario/casa_sprite_sheet.png"));
		listaActor.add(casa);
		
		//
		CGTProjectile projetilAgua = new CGTProjectile(new Vector2(400f, 400f),100);
		projetilAgua.setTextura(new Texture("data/Sprites/SpriteSheet_agua.png"));
		listaDeProjectile.add(projetilAgua);
	}

	
	/**
	 * @return the rigido
	 */
	public ArrayList<CGTActor> getListaPersonagens() {
		return listaActor;
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
	public ArrayList<CGTProjectile> getListaDeProjectili() {
		return listaDeProjectile;
	}

	/**
	 * @param listaDeProjectili the listaDeProjectili to set
	 */
	public void setListaDeProjectili(ArrayList<CGTProjectile> listaDeProjectili) {
		this.listaDeProjectile = listaDeProjectili;
	}
	
}
