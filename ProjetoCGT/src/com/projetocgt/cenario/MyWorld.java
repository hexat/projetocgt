package com.projetocgt.cenario;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.CGTActor;
import com.projetocgt.personagens.CGTBonus;
import com.projetocgt.personagens.CGTOpposite;
import com.projetocgt.personagens.CGTProjectile;
import com.projetocgt.personagens.SpriteSheet;
/**
 * Responsavel por construir o jogo
 * @author Bruno Roberto
 *
 */
public class MyWorld {
	
	ArrayList<CGTActor> listaActor = new ArrayList<CGTActor>();
	ArrayList<CGTProjectile> listaDeProjectile = new ArrayList<CGTProjectile>();
	ArrayList<CGTOpposite>listaDeOpposite= new ArrayList<CGTOpposite>();
	ArrayList<CGTBonus> listaDeBonus=new ArrayList<CGTBonus>();
	private CGTActor personagemActor;
	private SpriteSheet spriteSheetActor = new SpriteSheet();
	private SpriteSheet spriteSheetOppositeFogo = new SpriteSheet();
	private SpriteSheet spriteSheetOppositeCasa = new SpriteSheet();
	private SpriteSheet spriteSheetProjectileAgua = new SpriteSheet();
	private Texture backGround;
	
	public MyWorld() {
		createWorld();
	}
	/***
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		
		backGround = new Texture(Gdx.files.internal("data/Cenario/asfalto_grama_sprite_sheet.png"));
		
		personagemActor = new CGTActor(new Vector2(330, 400),3,0, 100f,50f,25f,25f);
		personagemActor.setSpeed(180);
		personagemActor.setSpriteSheet(spriteSheetActor);
		spriteSheetActor.loadSpiteCGTACtor("data/SpriteCGTActor/SpriteSheet_bombeiro.png");
		
		/* Esse opposite nao tem animacao, seria melhor adicionar uma textura do que uma animacao 
		 * um por um.
		 */
		CGTOpposite opositorCasa = new CGTOpposite(new Vector2(450,400), 300, 230, 35, 50);
		//opositorCasa.setTexture(new Texture("data/Cenario/casa_sprite_sheet.png"));
		opositorCasa.setBlock(true);
		opositorCasa.setSpriteSheet(spriteSheetOppositeCasa);
		//Indica que a minha animacao e' um por um
		spriteSheetOppositeCasa.loadingSpriteSheet("data/Cenario/casa_sprite_sheet.png", 1, 1);
		listaDeOpposite.add(opositorCasa);
		
		
		//Instancia o opposite fogo
		CGTOpposite opositorFogo = new CGTOpposite(new Vector2(450,850), 100, 100, 0, 0);
		opositorFogo.setTexture(new Texture("data/CGTOpposite/SpriteSheet_fogo.png"));
		opositorFogo.setBlock(false);
		opositorFogo.setSpriteSheet(spriteSheetOppositeFogo);
		spriteSheetOppositeFogo.loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		listaDeOpposite.add(opositorFogo);
		
		CGTBonus hidrate = new CGTBonus(new Vector2(800,800),50, 50, 0, 0);
		hidrate.setTexture(new Texture("data/CGTBonus/SpriteSheet_tubo.png"));
		listaDeBonus.add(hidrate);
		
		CGTProjectile projetilAgua = new CGTProjectile(new Vector2(900f, 900f), 100, 100, 0, 0);
		//projetilAgua.setTexture(new Texture("data/CGTProjectile/SpriteSheet_agua.png"));
		projetilAgua.setSpriteSheet(spriteSheetProjectileAgua);
		//Indica que a minha animacao e' um por um
		spriteSheetProjectileAgua.loadingSpriteSheet("data/CGTProjectile/SpriteSheet_agua.png", 2, 2);
		listaDeProjectile.add(projetilAgua);
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
		return personagemActor;
	}
	/**
	 * @return the listaActor
	 */
	public ArrayList<CGTActor> getListaActor() {
		return listaActor;
	}
	/**
	 * @return the listaDeBonus
	 */
	public ArrayList<CGTBonus> getListaDeBonus() {
		return listaDeBonus;
	}
	/**
	 * @param listaDeBonus the listaDeBonus to set
	 */
	public void setListaDeBonus(ArrayList<CGTBonus> listaDeBonus) {
		this.listaDeBonus = listaDeBonus;
	}
}
