package com.projetocgt;

import cgt.util.CGTButton;
import cgt.screen.CGTScreen;
import cgt.util.CGTTexture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GeneralScreen extends Stage implements Screen {

	private StarAssault game;
	private CGTScreen screen;
    private Texture splsh;
    private SpriteBatch spriteBatch;

	
	public GeneralScreen(StarAssault game) {
		super(new ScreenViewport());
		this.game = game;
//	    stage = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(this);
	    
		CGTTexture t = new CGTTexture("data/menu/back_gui.png");
		CGTButton btn = new CGTButton();
		btn.setRelativeX(0.33f);
		btn.setRelativeY(0.5f);
		btn.setRelativeWidth(0.25f);
		btn.setRelativeHeight(0.3f);
		Texture texture = new Texture("data/menu/iniciar_gui.png");
		btn.setTextureDown(texture);
		btn.setTextureUp(texture);
		btn.setBounds(0, 0, texture.getWidth(), texture.getHeight());
		btn.setScreenToGo(new GameScreen());
//		CGTButton btn = new CGTButton("data/menu/_gui.png");
//		btn.setBounds(new Rectangle(400, 400, 200, 100));
		screen = new CGTScreen(t);
		screen.getButtons().add(btn);
		
	}
	
	@Override
	public void render(float delta) {

       // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.act();
        
        getSpriteBatch().begin();
        getSpriteBatch().draw(new Texture("data/menu/back_gui.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getSpriteBatch().end();
        
        this.draw();
        
        for (CGTButton b : screen.getButtons()) {
        	if(b.isActive()){
        		this.dispose();
        		b.setTouchable(Touchable.disabled);
        		game.setScreen(b.getScreenToGo());
        	}
        }
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height);
	}

	@Override
	public void show() {
        splsh = new Texture(Gdx.files.internal(screen.getBackground().getFile().getPath()));
        setSpriteBatch(new SpriteBatch());
        for (CGTButton b : screen.getButtons()) {
        	this.addActor(b);
        }
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
		splsh.dispose();
		spriteBatch.dispose();

	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}


	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

}
