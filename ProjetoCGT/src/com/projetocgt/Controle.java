package com.projetocgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.projetocgt.cenario.WorldController;

public class Controle{
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private BitmapFont black;
	private TextButton seta;
	//private WorldController	controller;
	public Controle(){
		
	}
	
	public void Acao(final WorldController	controller){
		controller.rightReleased();
	}
	
	//Render
	public void InicializaStage(float delta){
		Table.drawDebug(stage);
		stage.act(delta);
		stage.draw();
	}
	//Show
	public void Inicializa(final WorldController	controller){
		black =new BitmapFont();
		//Inicializa o stage
		stage= new Stage();
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("data/Joystick2/button.pack");
		skin = new Skin(atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButtonStyle estilo = new TextButtonStyle();
		estilo.up = skin.getDrawable("setaBaixoUp");
		estilo.down = skin.getDrawable("setaBaixo");
		estilo.pressedOffsetX = 1;
		estilo.pressedOffsetY = -1;
		estilo.font = black;
		
		
		seta = new TextButton("Bruno ", estilo);
		
		seta.addListener(new ClickListener(){
			public void clicked(InputEvent event , float x,float y){
				controller.rightPressed();
				System.out.println("Deu certo");
			}
		});
		seta.pad(20);
		
		table.add(seta);
		//table.rotate(90);
		table.debug();
		stage.addActor(table);
	}
}
