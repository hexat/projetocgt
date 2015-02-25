package com.projetocgt;

import cgt.game.CGTGameWorld;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.game.CGTScreen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GeneralScreen extends Stage implements Screen {

	private StarAssault game;
	private CGTScreen screen;
    private Texture splsh;
    private SpriteBatch spriteBatch;

	
	public GeneralScreen(CGTScreen screen) {
		super(new ScreenViewport());
		this.screen = screen;
		splsh = screen.getBackground().getTextureGDX();
        setSpriteBatch(new SpriteBatch());
        for (CGTButton b : screen.getButtons()) {
        	b.setInputListener();
        	this.addActor(b);
        	b.setup();
        }
		this.game = StarAssault.getInstance();
	    Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
        this.act();
        
        getSpriteBatch().begin();
        getSpriteBatch().draw(splsh,
        		0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getSpriteBatch().end();
        
        this.draw();
        
        for (CGTButtonScreen b : screen.getButtons()) {
        	
        	if(b.isActive()){
        		b.setActive(false);
                System.out.println(b.getScreenToGo());
                if (b.getScreenToGo() != null) {
                    System.out.println(b.getScreenToGo().getId());
                    if (b.getScreenToGo() instanceof CGTGameWorld) {
                        game.setScreen(new GameScreen((CGTGameWorld) b.getScreenToGo()));
                    } else {
                        game.setScreen(new GeneralScreen((CGTScreen) b.getScreenToGo()));
                    }
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
