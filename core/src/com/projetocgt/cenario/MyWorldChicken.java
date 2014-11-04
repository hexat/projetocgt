package com.projetocgt.cenario;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.behaviors.Direction;
import cgt.behaviors.Sine;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.hud.CGTButtonStartGame;
import cgt.policy.ActionMovePolicy;
import cgt.policy.DirectionPolicy;
import cgt.policy.GameModePolicy;
import cgt.policy.InputPolicy;
import cgt.policy.MovementPolicy;
import cgt.policy.StatePolicy;
import cgt.screen.CGTDialog;
import cgt.screen.CGTScreen;
import cgt.unit.Action;
import cgt.util.CGTAnimation;
import cgt.util.CGTSound;
import cgt.util.CGTSpriteSheet;
import cgt.util.CGTTexture;
import cgt.win.CompleteCrossing;

public class MyWorldChicken {
	private CGTTexture backGround;
	private CGTGameWorld world;
	private CGTScreen screen;
	private CGTGame game;
	
	public void configuracaoActor(CGTActor personagemCGTActor) {
		//personagemCGTActor.setFireDefault(-1);
		personagemCGTActor.setPosition(new Vector2(1f, 600f)); // obrigatorio
		//personagemCGTActor.setTimeToRecovery(4);
		personagemCGTActor.setCollision(new Rectangle(10, 10, 40, 45)); 

		personagemCGTActor.setBounds(new Rectangle(0,0,60,60)); // Obrigatorio

		personagemCGTActor.setLife(30);
		personagemCGTActor.setSpeed(100);

		personagemCGTActor.setSpriteSheet(new CGTSpriteSheet("data/dapexe/sprite_garoto.png"));
		personagemCGTActor.getSpriteSheet().setRows(5);
		personagemCGTActor.getSpriteSheet().setColumns(3);

	}

	public void configuracaoActionActor(CGTActor personagemCGTActor) {

		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor);
		moveUp.setSpriteLine(2);
		moveUp.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveUp.setFlipHorizontal(false);
		moveUp.setSpriteVelocity(0.2f);
		moveUp.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation animationDamege = new CGTAnimation(personagemCGTActor);
		animationDamege.setSpriteLine(5);
		animationDamege.addStatePolicy(StatePolicy.DAMAGE);
		animationDamege.setSpriteVelocity(0.2f);
		animationDamege.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		personagemCGTActor.getAnimarions().add(animationDamege);
		personagemCGTActor.getAnimarions().add(moveUp);
		
	}

	public void configuracaoCarros() {
		// inicializando o carro no cenario

		Direction direction = new Direction(DirectionPolicy.LEFT_AND_RIGHT);
		direction.setMaxX(1200);
		direction.setMinX(0);
		direction.setInteligenceMoviment(false);

		Direction directionUp = new Direction(DirectionPolicy.UP_AND_DOWN);
		directionUp.setMaxY(1200);
		directionUp.setMinY(0);
		directionUp.setInteligenceMoviment(false);

		Direction directionFour = new Direction(DirectionPolicy.FOUR_DIRECTION);
		directionFour.setMaxY(600);
		directionFour.setMinY(400);
		directionFour.setMaxX(1600);
		directionFour.setMinX(1130);

		CGTEnemy carroCGT = new CGTEnemy();

		Vector2 positionCarro = new Vector2(780, 600);
		carroCGT.setPosition(positionCarro);

		Rectangle coliderCarro = new Rectangle(22, 0, 60, 94);
		carroCGT.setCollision(coliderCarro);

		Rectangle tamanhoCarro = new Rectangle(0, 0, 98, 90);
		carroCGT.setBounds(tamanhoCarro);

		carroCGT.setBlock(true);
		carroCGT.setDestroyable(false);
		carroCGT.setDamage(50);
		carroCGT.addBehavior(directionUp);
		carroCGT.setSpeed(200);

		carroCGT.setSpriteSheet(new CGTSpriteSheet("data/dapexe/SpriteSheet_carro_jeep.png"));
		carroCGT.getSpriteSheet().setRows(3);
		carroCGT.getSpriteSheet().setColumns(2);

		CGTSound soundCar = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.2f);
		carroCGT.setSound(soundCar);

		// Action
		CGTAnimation moveCarroDown = new CGTAnimation(carroCGT);
		moveCarroDown.setSpriteLine(2);
		moveCarroDown.addStatePolicy(StatePolicy.LOOKDOWN);

		moveCarroDown.setSpriteVelocity(0.08f);
		moveCarroDown.setAnimationPolicy(PlayMode.LOOP);
		carroCGT.getAnimarions().add(moveCarroDown);

		CGTAnimation moveCarroUp = new CGTAnimation(carroCGT);
		moveCarroUp.setSpriteLine(3);
		moveCarroUp.addStatePolicy(StatePolicy.LOOKUP);

		moveCarroUp.setSpriteVelocity(0.08f);
		moveCarroUp.setAnimationPolicy(PlayMode.LOOP);
		carroCGT.getAnimarions().add(moveCarroUp);

		world.getEnemies().add(carroCGT);

		CGTEnemy carroCGT2 = new CGTEnemy();

		Vector2 positionCarro2 = new Vector2(600, 560);
		carroCGT2.setPosition(positionCarro2);

		Rectangle coliderCarro2 = new Rectangle(0, 0, 90, 80);
		carroCGT2.setCollision(coliderCarro2);

		Rectangle tamanhoCarro2 = new Rectangle(0, 0, 98, 90);
		carroCGT2.setBounds(tamanhoCarro2);

		carroCGT2.setBlock(true);
		carroCGT2.setDestroyable(false);
		carroCGT2.setDamage(50);
		carroCGT2.addBehavior(direction);
		carroCGT2.setSpeed(200);

		carroCGT2.setSpriteSheet(new CGTSpriteSheet("data/dapexe/SpriteSheet_carro_jeep.png"));
		carroCGT2.getSpriteSheet().setRows(3);
		carroCGT2.getSpriteSheet().setColumns(2);

		CGTSound soundCar2 = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.2f);

		carroCGT2.setSound(soundCar2);

		// Action

		CGTAnimation moveCarro = new CGTAnimation(carroCGT2);
		moveCarro.setSpriteLine(1);
		moveCarro.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveCarro.setSpriteVelocity(0.08f);
		moveCarro.setAnimationPolicy(PlayMode.LOOP);
		carroCGT2.getAnimarions().add(moveCarro);

		CGTAnimation moveCarroLeft = new CGTAnimation(carroCGT2);
		moveCarroLeft.setFlipHorizontal(true);
		moveCarroLeft.setSpriteLine(1);
		moveCarroLeft.addStatePolicy(StatePolicy.LOOKLEFT);
		moveCarroLeft.setSpriteVelocity(0.08f);
		moveCarroLeft.setAnimationPolicy(PlayMode.LOOP);
		carroCGT2.getAnimarions().add(moveCarroLeft);

		// Add na lista de enemy
		world.getEnemies().add(carroCGT2);

	}

	public void configuracaoPeixes(){
		Direction directionUp = new Direction(DirectionPolicy.UP_AND_DOWN);
		directionUp.setMaxY(200);
		directionUp.setMinY(0);
		directionUp.setInteligenceMoviment(false);
		
		CGTEnemy peixe = new CGTEnemy();
		
		Vector2 positionPeixe = new Vector2(200, 0);
		peixe.setPosition(positionPeixe);

		Rectangle coliderPeixe = new Rectangle(22, 0, 60, 94);
		peixe.setCollision(coliderPeixe);

		Rectangle tamanhoPeixe = new Rectangle(0, 0, 98, 90);
		peixe.setBounds(tamanhoPeixe);
		
		peixe.setState(StatePolicy.IDLEDOWN);
		peixe.setLife(50);
		peixe.setBlock(true);
		peixe.setDestroyable(false);
		peixe.setDamage(50);
		peixe.addBehavior(directionUp);
		peixe.setSpeed(100);

		peixe.setSpriteSheet(new CGTSpriteSheet(
				"data/dapexe/SpriteSheet_carro_jeep.png"));
		peixe.getSpriteSheet().setRows(3);
		peixe.getSpriteSheet().setColumns(2);
		
		// Action
		CGTAnimation moveCarroDown = new CGTAnimation(peixe);
		moveCarroDown.setSpriteLine(2);
		moveCarroDown.addStatePolicy(StatePolicy.LOOKDOWN);

		moveCarroDown.setSpriteVelocity(0.08f);
		moveCarroDown.setAnimationPolicy(PlayMode.LOOP);
		peixe.getAnimarions().add(moveCarroDown);

		CGTAnimation moveCarroUp = new CGTAnimation(peixe);
		moveCarroUp.setSpriteLine(3);
		moveCarroUp.addStatePolicy(StatePolicy.LOOKUP);

		moveCarroUp.setSpriteVelocity(0.08f);
		moveCarroUp.setAnimationPolicy(PlayMode.LOOP);
		peixe.getAnimarions().add(moveCarroUp);

		peixe.getAnimarions().add(moveCarroUp);
		world.getEnemies().add(peixe);
	}
	
	public void configuracaoPauseDialog() {
		CGTDialog pauseDialog = new CGTDialog();
		pauseDialog.setActive(false);
		pauseDialog.setWindow(new CGTTexture("data/dapexe/pause.png"));
		pauseDialog.setHorizontalBorderTexture(new CGTTexture("data/dapexe/borda.png"));
		pauseDialog.setRightBottomCorner(new CGTTexture("data/dapexe/canto.png"));
		pauseDialog.setRelativeX(0.20f);
		pauseDialog.setRelativeY(0.20f);
		pauseDialog.setRelativeWidth(0.6f);
		pauseDialog.setRelativeHeight(0.6f);

		CGTButton voltar = new CGTButtonScreen();
		voltar.setTextureUp(new CGTTexture("data/dapexe/close_btn.png"));
		voltar.setTextureDown(new CGTTexture("data/dapexe/close_btn.png"));
		voltar.setRelativeX(0.65f);
		voltar.setRelativeY(0.65f);
		voltar.setRelativeWidth(0.1f);
		voltar.setRelativeHeight(0.1f);

		CGTButtonScreen voltarMenu = new CGTButtonScreen();
		voltarMenu.setScreenToGo(game.getGame());
		voltarMenu.setTextureUp(new CGTTexture("data/dapexe/back_btn.png"));
		voltarMenu.setTextureDown(new CGTTexture("data/dapexe/back_btn.png"));
		voltarMenu.setRelativeX(0.25f);
		voltarMenu.setRelativeY(0.65f);
		voltarMenu.setRelativeWidth(0.1f);
		voltarMenu.setRelativeHeight(0.1f);

		pauseDialog.addButton(voltarMenu);
		pauseDialog.setCloseButton(voltar);
		world.setPauseDialog(pauseDialog);

	}

	public void configuracaoWinDialog() {
		CGTDialog pauseDialog = new CGTDialog();
		pauseDialog.setActive(false);
		pauseDialog.setWindow(new CGTTexture("data/dapexe/win_dialog.png"));
		pauseDialog.setHorizontalBorderTexture(new CGTTexture("data/dapexe/borda.png"));
		pauseDialog.setRightBottomCorner(new CGTTexture("data/dapexe/canto.png"));
		pauseDialog.setRelativeX(0.20f);
		pauseDialog.setRelativeY(0.20f);
		pauseDialog.setRelativeWidth(0.6f);
		pauseDialog.setRelativeHeight(0.6f);

		CGTButton voltar = new CGTButtonScreen();
		voltar.setTextureUp(new CGTTexture("data/dapexe/close_btn.png"));
		voltar.setTextureDown(new CGTTexture("data/dapexe/close_btn.png"));
		voltar.setRelativeX(0.65f);
		voltar.setRelativeY(0.65f);
		voltar.setRelativeWidth(0.1f);
		voltar.setRelativeHeight(0.1f);

		CGTButtonScreen voltarMenu = new CGTButtonScreen();
		voltarMenu.setScreenToGo(game.getGame());
		voltarMenu.setTextureUp(new CGTTexture("data/dapexe/back_btn.png"));
		voltarMenu.setTextureDown(new CGTTexture("data/dapexe/back_btn.png"));
		voltarMenu.setRelativeX(0.45f);
		voltarMenu.setRelativeY(0.25f);
		voltarMenu.setRelativeWidth(0.1f);
		voltarMenu.setRelativeHeight(0.1f);

		pauseDialog.addButton(voltarMenu);
		world.setWinDialog(pauseDialog);
	}

	public void configuracaoLDialog(){
		CGTDialog loseDialog = new CGTDialog();
		loseDialog.setActive(false);
		loseDialog.setWindow(new CGTTexture("data/dapexe/lose_dialog.png"));
		loseDialog.setHorizontalBorderTexture(new CGTTexture("data/dapexe/borda.png"));
		loseDialog.setRightBottomCorner(new CGTTexture("data/dapexe/canto.png"));
		loseDialog.setRelativeX(0.20f);
		loseDialog.setRelativeY(0.20f);
		loseDialog.setRelativeWidth(0.6f);
		loseDialog.setRelativeHeight(0.6f);

		CGTButtonScreen voltarMenu = new CGTButtonScreen();
		voltarMenu.setScreenToGo(game.getGame());
		voltarMenu.setTextureUp(new CGTTexture("data/dapexe/back_btn.png"));
		voltarMenu.setTextureDown(new CGTTexture("data/dapexe/back_btn.png"));
		voltarMenu.setRelativeX(0.45f);
		voltarMenu.setRelativeY(0.25f);
		voltarMenu.setRelativeWidth(0.1f);
		voltarMenu.setRelativeHeight(0.1f);

		loseDialog.addButton(voltarMenu);
		world.setLoseDialog(loseDialog);
	}
	
	public void configuracaoInputs() {
		Action walkUp = new Action(ActionMovePolicy.WALK_RIGHT, InputPolicy.TAP);
		Action slideUp = new Action(ActionMovePolicy.WALK_UP, InputPolicy.SLIDE_UP);
		Action slideDown = new Action(ActionMovePolicy.WALK_DOWN, InputPolicy.SLIDE_DOWN);
	

		world.addAction(walkUp,slideUp,slideDown);
	}
	

	public MyWorldChicken() {
		
		createWorld();
	}
	
	private void createWorld(){
		world = new CGTGameWorld();
		backGround = new CGTTexture("data/dapexe/casas_ceara_cenario.png");
		world.setBackground(backGround);
		//world.setMusic(new CGTSound("data/AudioDaPexe/temaDaPexe.ogg",0.3f));
		world.getCamera().setGameMode(GameModePolicy.TOUCH);
		
		CGTActor personagemCGTActor = new CGTActor();
		
		configuracaoActor(personagemCGTActor);
		
		configuracaoActionActor(personagemCGTActor);
		
		configuracaoCarros();
		
		configuracaoPeixes();
		
		world.setActor(personagemCGTActor);
		
		configuracaoInputs();
		
		world.addWinCriterion(new CompleteCrossing(world, new Rectangle(world.getBackground().getTextureGDX().getWidth()- 1f/100f*world.getBackground().getTextureGDX().getWidth(), 1, 1f/100f*world.getBackground().getTextureGDX().getWidth(), world.getBackground().getTextureGDX().getHeight())));
		CGTButtonStartGame a = new CGTButtonStartGame(0.1f , 0.1f, 0.9f, 0.9f);
		CGTTexture t = new CGTTexture("data/dapexe/menuInicial.png");
//		a.setTextureUp(t);
//		a.setTextureDown(t);
		world.setStartGame(a);
		world.getCamera().setInitialHeight(0.5f);
		world.getCamera().setInitialWidth(0.5f);
		world.getCamera().setScale(25);;
		CGTButtonScreen btn = new CGTButtonScreen();
		btn.setRelativeX(0.39f);
		btn.setRelativeY(0.7f);
		btn.setRelativeWidth(0.20f);
		btn.setRelativeHeight(0.1f);
		CGTTexture texture = new CGTTexture("data/dapexe/iniciar1.png");
		btn.setTextureDown(texture);
		btn.setTextureUp(texture);
		btn.setBounds(0, 0, texture.getWidth(), texture.getHeight());	
		btn.setScreenToGo(world);
		screen = new CGTScreen(t);
		screen.getButtons().add(btn);
		
		game = new CGTGame();
		game.setMenu(world);
		configuracaoPauseDialog();
		configuracaoWinDialog();
		configuracaoLDialog();
	}
	
	public CGTGameWorld getWorld() {
		return world;
	}

	public CGTGame getGame() {
		
		return game;
	//return CGTGame.getSavedGame();
		
	
	}

}
