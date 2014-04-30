package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/***
 * Classe responsavel pelo menu.
 * @author roberto.bruno007@gmail.com
 *
 */
public class Menu {
	private Texture menuInicial; //Textura para o menu inicial
	
	/***
	 * Construtor padrao que recebe como prametro o caminho inicial do menu inicial
	 * @param caminho
	 */
	public Menu(String caminho){
		setMenuInicial(new Texture(Gdx.files.internal(caminho)));
	}
	
	/**
	 * @return the menuInicial
	 */
	public Texture getMenuInicial() {
		return menuInicial;
	}

	/**
	 * @param menuInicial the menuInicial to set
	 */
	public void setMenuInicial(Texture menuInicial) {
		this.menuInicial = menuInicial;
	}
}	
