package com.projetocgt;

import java.io.File;

import cgt.screen.CGTScreen;
import cgt.util.CGTTexture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GeneralScreen extends Stage implements Screen {

	private StarAssault game;
	private CGTScreen screen;
    private Texture splsh;

	
	public GeneralScreen(StarAssault game) {
		super(new ScreenViewport());
		this.game = game;
//	    stage = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(this);
	    
		CGTTexture t = new CGTTexture(new File("data/menu/back_gui.png"));
		screen = new CGTScreen(t);
		
	}
	
	@Override
	public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getSpriteBatch().begin();
        getSpriteBatch().draw(splsh, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getSpriteBatch().end();

        draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
        splsh = new Texture(Gdx.files.internal(screen.getBackground().getFile().getPath()));
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
