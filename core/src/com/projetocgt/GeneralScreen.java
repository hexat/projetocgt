package com.projetocgt;

import cgt.game.CGTGameWorld;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.game.CGTScreen;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GeneralScreen extends Stage implements Screen {
	private StarAssault game;
	private CGTScreen screen;
    private Texture splsh;
    private SpriteBatch spriteBatch;
    private Music music;

	
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
        music = null;
        if (screen.getSound() != null) {
            this.music = screen.getSound().getMusic();
        }
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
                if (b.getScreenToGo() != null) {
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
        if (music != null) {
        	System.out.println(music);
            music.play();
            music.setLooping(true);
        }
	}

	@Override
	public void hide() {
        if (music != null) {
            music.stop();
            music = null;
        }
	}

	@Override
	public void pause() {
        if (music != null) {
            music.pause();
        }
	}

	@Override
	public void resume() {
        if (music != null) {
            music.play();
        }
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
