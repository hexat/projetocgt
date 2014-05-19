package com.projetocgt.cenario;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.CGTActor;
import com.projetocgt.personagens.CGTOpposite;
import com.projetocgt.personagens.CGTProjectile;
import com.projetocgt.personagens.SpriteSheet;

public class MyWorld {
	
	ArrayList<CGTActor> listaActor = new ArrayList<CGTActor>();
	ArrayList<CGTProjectile> listaDeProjectile = new ArrayList<CGTProjectile>();
	ArrayList<CGTOpposite>listaDeOpposite= new ArrayList<CGTOpposite>();
	private CGTActor personagem;
	private Texture backGround;
	
	public MyWorld() {
		createWorld();
	}
	/***
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		backGround = new Texture(Gdx.files.internal("data/Cenario/asfalto_grama_sprite_sheet.png"));
		
		personagem = new CGTActor(new Vector2(400, 400),3,0, 100f,50f,25f,25f);
		personagem.setSpeed(180);
		personagem.setSprite(new SpriteSheet());
		//listaActor.add(personagem);
		
		//CGTOpposite opositor = new CGTOpposite(new Vector2(500,500));
		//opositor.setTexture(new  Texture(Gdx.files.internal("data/Carros/carro.png")));
		//listaDeOpposite.add(opositor);
		
		CGTOpposite opositorFogo = new CGTOpposite(new Vector2(500,500),100,100,0,0);
		opositorFogo.setTexture(new Texture("data/piscina.png"));
		listaDeOpposite.add(opositorFogo);
		
		//Adiniona uma aniamcao 
		
		//Constroe o cenario com agua
		CGTOpposite agua = new CGTOpposite(new Vector2(650,650),100,100,0,0); 
		agua.setTexture(new Texture("data/piscina.png"));
		listaDeOpposite.add(agua);
		
		//Constroe a casa
		//CGTActor casa = new CGTActor(new Vector2(450f, 400f), 0, 0, 300, 240f,25f,40f);
		//casa.setTexturePersonagem(new Texture("data/Cenario/casa_sprite_sheet.png"));
		//listaActor.add(casa);
		
		//
		/*CGTProjectile projetilAgua = new CGTProjectile(new Vector2(400f, 400f),100);
		projetilAgua.setTextura(new Texture("data/Sprites/SpriteSheet_agua.png"));
		listaDeProjectile.add(projetilAgua);*/
	}

	
	/**
	 * @return the rigido
	 */
	public ArrayList<CGTActor> getListaActor() {
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

	/**
	 * @return the listaDeOpposite
	 */
	public ArrayList<CGTOpposite> getListaDeOpposite() {
		return listaDeOpposite;
	}

	/**
	 * @param listaDeOpposite the listaDeOpposite to set
	 */
	public void setListaDeOpposite(ArrayList<CGTOpposite> listaDeOpposite) {
		this.listaDeOpposite = listaDeOpposite;
	}
	/**
	 * @return the personagem
	 */
	public CGTActor getPersonagem() {
		return personagem;
	}
}
