package com.projetocgt;

import cgt.screen.CGTButton;
import cgt.screen.CGTScreen;
import cgt.util.CGTTexture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	    
		CGTTexture t = new CGTTexture("data/menu/back_gui.png");
		CGTButton btn = new CGTButton("data/menu/iniciar_gui.png");
		btn.setBounds(new Rectangle(400, 400, 200, 100));
		btn.setNextScene(null);
//		CGTButton btn = new CGTButton("data/menu/_gui.png");
//		btn.setBounds(new Rectangle(400, 400, 200, 100));
		screen = new CGTScreen(t);
		screen.getButtons().add(btn);
		
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
        for (CGTButton b : screen.getButtons()) {
        	Button tb = new Button((new TextureRegionDrawable(new TextureRegion(new Texture(b.getFilepath())))));
//        	tb.setBackground((new TextureRegionDrawable(new TextureRegion(new Texture(b.getFilepath())))));
//        	tb.set
        	tb.setX(b.getBounds().x);
        	tb.setY(b.getBounds().y);
        	tb.setWidth(b.getBounds().width);
        	tb.setHeight(b.getBounds().getHeight());
        	tb.addListener(new EventListener() {
				
				@Override
				public boolean handle(Event event) {
					game.setScreen(new GameScreen());
					return true;
				}
			});
        	addActor(tb);
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
		// TODO Auto-generated method stub

	}

}
