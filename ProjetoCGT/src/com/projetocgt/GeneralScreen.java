package com.projetocgt;

import cgt.CGTGameWorld;
import cgt.util.CGTButton;
import cgt.screen.CGTButtonScreen;
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

	
	public GeneralScreen(CGTScreen screen) {
		super(new ScreenViewport());
		this.screen = screen;
		this.game = StarAssault.getInstance();
	    Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
        this.act();
        
        getSpriteBatch().begin();
        getSpriteBatch().draw(new Texture(screen.getBackground().getFilepath()),
        		0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getSpriteBatch().end();
        
        this.draw();
        
        for (CGTButtonScreen b : screen.getButtons()) {
        	if(b.isActive()){
        		b.setTouchable(Touchable.disabled);
        		if (b.getScreenToGo() instanceof CGTGameWorld) {
        			game.setScreen(new GameScreen((CGTGameWorld) b.getScreenToGo()));
        		} else {
        			
        		}
        	}
        }
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height);
	}

	@Override
	public void show() {
        splsh = new Texture(screen.getBackground().getFile().getFileHandle());
        setSpriteBatch(new SpriteBatch());
        for (CGTButton b : screen.getButtons()) {
        	this.addActor(b);
        	b.autosize();
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
