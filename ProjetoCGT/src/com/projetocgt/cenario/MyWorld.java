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
	ArrayList<CGTOpposite> listaDeOpposite = new ArrayList<CGTOpposite>();
	ArrayList<CGTBonus> listaDeBonus = new ArrayList<CGTBonus>();
	private CGTActor personagemActor;
	private Texture backGround;
	
	public MyWorld() {
		createWorld();
	}
	/**
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		
		backGround = new Texture(Gdx.files.internal("data/Cenario/asfalto_grama_sprite_sheet.png"));
		
		personagemActor = new CGTActor(new Vector2(330, 400), 100f, 100f, 80f, 10f, 10f);
		personagemActor.setSpeed(180);
		personagemActor.setLife(3);
		SpriteSheet spriteSheetActor = new SpriteSheet();
		personagemActor.setSpriteSheet(spriteSheetActor );
		spriteSheetActor.setLinhaDoSpriteUp(3);
		spriteSheetActor.setLinhaDoSpriteDown(2);
		spriteSheetActor.setLinhaDoSpriteLeft(1);
		spriteSheetActor.setLinhaDoSpriteRight(1);
		spriteSheetActor.loadSpiteCGTACtor("data/SpriteCGTActor/SpriteSheet_bombeiro.png",5,3);
		
		/* Esse opposite nao tem animacao, seria melhor adicionar uma textura do que uma animacao 
		 * um por um.
		 */
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				CGTOpposite opositorCasa = new CGTOpposite(new Vector2(450*(i+1)+i*20,400*(j+1)+j*90), 300, 300, 300, 0, 0);
				//opositorCasa.setTexture(new Texture("data/Cenario/casa_sprite_sheet.png"));
				opositorCasa.setBlock(true);
				opositorCasa.setDestroyable(false);
				opositorCasa.setLife(0);
				opositorCasa.setSpriteSheet(new SpriteSheet());
				//Indica que a minha animacao e' um por um
				opositorCasa.getSpriteSheet().loadingSpriteSheet("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png", 1, 1);
				listaDeOpposite.add(opositorCasa);				
			}
		}
		//Instancia o opposite fogo
		CGTOpposite opositorFogo = new CGTOpposite(new Vector2(200,850), 50, 50, 50, 0, 0);
		//opositorFogo.setTexture(new Texture("data/CGTOpposite/SpriteSheet_fogo.png"));
		opositorFogo.setBlock(true);
		opositorFogo.setDamage(1);
		opositorFogo.setLife(50);
		opositorFogo.setSpriteSheet(new SpriteSheet());
		opositorFogo.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		listaDeOpposite.add(opositorFogo);
		
		CGTBonus hidrate = new CGTBonus(new Vector2(800,800),50, 50, 50, 0, 0);
		hidrate.setTexture(new Texture("data/CGTBonus/SpriteSheet_tubo.png"));
		listaDeBonus.add(hidrate);
		
		CGTProjectile projetilAgua = new CGTProjectile(new Vector2(900f, 900f), 30, 30, 30, 0, 0);
		//projetilAgua.setTexture(new Texture("data/CGTProjectile/SpriteSheet_agua.png"));
		projetilAgua.setSpriteSheet(new SpriteSheet());
		//Indica que a minha animacao e' um por um
		projetilAgua.setActionFire("A");
		projetilAgua.getSpriteSheet().loadingSpriteSheet("data/CGTProjectile/SpriteSheet_agua.png", 2, 2);
		
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
