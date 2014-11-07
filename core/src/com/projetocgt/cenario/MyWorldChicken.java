package com.projetocgt.cenario;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.behaviors.Direction;
import cgt.behaviors.Direction.DirectionMode;
import cgt.behaviors.Sine;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.core.CGTOpposite;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.hud.CGTButtonStartGame;
import cgt.lose.LifeDepleted;
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

		personagemCGTActor.setLife(1);
		personagemCGTActor.setSpeed(100);

		personagemCGTActor.setSpriteSheet(new CGTSpriteSheet("data/chicken/galinha2.png"));
		personagemCGTActor.getSpriteSheet().setRows(8);
		personagemCGTActor.getSpriteSheet().setColumns(4);

		personagemCGTActor.getSoundsDie().add(new CGTSound("data/AudioDaPexe/derrota.ogg",0.3f));

	}

	public void configuracaoActionActor(CGTActor personagemCGTActor) {

		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor);
		moveUp.setInitialFrame(new Vector2(0, 0));
		moveUp.setEndingFrame(new Vector2(1, 7));
		moveUp.addStatePolicy(StatePolicy.IDLE);
		moveUp.addStatePolicy(StatePolicy.DYING);
		moveUp.setFlipHorizontal(false);
		moveUp.setSpriteVelocity(0.13f);

//		CGTAnimation animationDamege = new CGTAnimation(personagemCGTActor);
//		animationDamege.setSpriteLine(5);
//		animationDamege.addStatePolicy(StatePolicy.DAMAGE);
//		animationDamege.setSpriteVelocity(0.2f);
//		animationDamege.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		personagemCGTActor.getAnimarions().add(animationDamege);
		personagemCGTActor.getAnimarions().add(moveUp);
		
	}

	public void configuracaoVacas() {
		// inicializando o carro no cenario
		
		Direction movimentacaoVaca = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoVaca.setInitialPosition(new Vector2(100,100));
		movimentacaoVaca.setFinalPosition(new Vector2(800,600));
		movimentacaoVaca.setInteligenceMoviment(false);
		
		Direction movimentacaoVaca2 = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoVaca2.setInitialPosition(new Vector2(200,600));
		movimentacaoVaca2.setFinalPosition(new Vector2(900,100));
		movimentacaoVaca2.setInteligenceMoviment(false);
		

		CGTEnemy vaca = new CGTEnemy();		

		Rectangle coliderVaca = new Rectangle(0, 0, 252, 252);
		vaca.setCollision(coliderVaca);

		Rectangle tamanhoVaca = new Rectangle(0, 0, 100, 100);
		vaca.setBounds(tamanhoVaca);
		
		vaca.addBehavior(movimentacaoVaca);
		vaca.setBlock(true);
		vaca.setDestroyable(false);
		vaca.setDamage(50);		
		vaca.setSpeed(100);

		vaca.setSpriteSheet(new CGTSpriteSheet("data/chicken/vaca_spritesheet.png"));
		vaca.getSpriteSheet().setRows(2);
		vaca.getSpriteSheet().setColumns(1);

		CGTSound soundCar = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.2f);
		vaca.setSound(soundCar);

		// Action
		CGTAnimation moveVacaUp = new CGTAnimation(vaca);
		moveVacaUp.setSpriteLine(1);
		moveVacaUp.addStatePolicy(StatePolicy.LOOKUP);
		moveVacaUp.setSpriteVelocity(0.08f);
		moveVacaUp.setAnimationPolicy(PlayMode.LOOP);
		vaca.getAnimarions().add(moveVacaUp);

		CGTAnimation moveVacaDown = new CGTAnimation(vaca);
		moveVacaDown.setSpriteLine(1);
		moveVacaDown.addStatePolicy(StatePolicy.LOOKDOWN);
		moveVacaDown.setSpriteVelocity(0.08f);
		moveVacaDown.setAnimationPolicy(PlayMode.LOOP);
		vaca.getAnimarions().add(moveVacaDown);

		world.getEnemies().add(vaca);
		
		

		CGTEnemy vaca2 = new CGTEnemy();		

		Rectangle coliderCarro2 = new Rectangle(0, 0, 252, 252);
		vaca2.setCollision(coliderCarro2);

		Rectangle tamanhoCarro2 = new Rectangle(0, 0, 252, 252);
		vaca2.setBounds(tamanhoCarro2);

		vaca2.setBlock(true);
		vaca2.setDestroyable(false);
		vaca2.setDamage(50);
		vaca2.addBehavior(movimentacaoVaca2);
		vaca2.setSpeed(100);

		vaca2.setSpriteSheet(new CGTSpriteSheet("data/chicken/vaca_spritesheet.png"));
		vaca2.getSpriteSheet().setRows(2);
		vaca2.getSpriteSheet().setColumns(1);

		CGTSound soundCar2 = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.2f);
		vaca2.setSound(soundCar2);

		// Action

		CGTAnimation moveVacaUp2 = new CGTAnimation(vaca2);
		moveVacaUp2.setSpriteLine(2);
		moveVacaUp2.addStatePolicy(StatePolicy.LOOKUP);
		moveVacaUp2.setSpriteVelocity(0.08f);
		moveVacaUp2.setAnimationPolicy(PlayMode.LOOP);
		vaca2.getAnimarions().add(moveVacaUp2);
		
		CGTAnimation moveVacaDown2 = new CGTAnimation(vaca2);
		moveVacaDown2.setSpriteLine(2);
		moveVacaDown2.addStatePolicy(StatePolicy.LOOKDOWN);
		moveVacaDown2.setSpriteVelocity(0.08f);
		moveVacaDown2.setAnimationPolicy(PlayMode.LOOP);
		vaca2.getAnimarions().add(moveVacaDown2);		

		// Add na lista de enemy
		world.getEnemies().add(vaca2);

	}

	public void configuracaoPeixes(){
		Direction movimentacaoPeixe = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoPeixe.setInitialPosition(new Vector2(200,0));
		movimentacaoPeixe.setFinalPosition(new Vector2(200,200));
		movimentacaoPeixe.setInteligenceMoviment(false);
		
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
		peixe.addBehavior(movimentacaoPeixe);
		peixe.setSpeed(100);

		peixe.setSpriteSheet(new CGTSpriteSheet(
				"data/chicken/peixe_spritesheet.png"));
		peixe.getSpriteSheet().setRows(4);
		peixe.getSpriteSheet().setColumns(4);
		
		// Action
		CGTAnimation moveCarroDown = new CGTAnimation(peixe);
		moveCarroDown.setInitialFrame(new Vector2(0, 0));
		moveCarroDown.setEndingFrame(new Vector2(1, 2));

		moveCarroDown.setSpriteVelocity(0.08f);
		moveCarroDown.setAnimationPolicy(PlayMode.LOOP);
		peixe.getAnimarions().add(moveCarroDown);

		CGTAnimation moveCarroUp = new CGTAnimation(peixe);
		moveCarroUp.setInitialFrame(new Vector2(2, 2));
		moveCarroUp.setEndingFrame(new Vector2(1, 3));
		moveCarroUp.addStatePolicy(StatePolicy.DAMAGE);

		moveCarroUp.setSpriteVelocity(0.08f);
		moveCarroUp.setAnimationPolicy(PlayMode.LOOP);
		peixe.getAnimarions().add(moveCarroUp);

		world.getEnemies().add(peixe);
	}
	
	public void configuracaoNuvens(){
		Direction movimentacaoNuvem1 = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoNuvem1.setInitialPosition(new Vector2(-300,400));
		movimentacaoNuvem1.setFinalPosition(new Vector2(1110,400));
		movimentacaoNuvem1.setInteligenceMoviment(false);
		
		Direction movimentacaoNuvem2 = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoNuvem2.setInitialPosition(new Vector2(-200,200));
		movimentacaoNuvem2.setFinalPosition(new Vector2(1050,200));
		movimentacaoNuvem2.setInteligenceMoviment(false);
		
		Direction movimentacaoNuvem3 = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoNuvem3.setInitialPosition(new Vector2(1140,500));
		movimentacaoNuvem3.setFinalPosition(new Vector2(-280,500));
		movimentacaoNuvem3.setInteligenceMoviment(false);	
				
		CGTEnemy nuvem = new CGTEnemy();

		Rectangle coliderNuvem = new Rectangle(22, 66, 250, 150);
		nuvem.setCollision(coliderNuvem);

		Rectangle tamanhoNuvem = new Rectangle(0, 0, 300, 300);
		nuvem.setBounds(tamanhoNuvem);
		
		nuvem.setBlock(false);
		nuvem.setDestroyable(false);
		nuvem.setState(StatePolicy.IDLEDOWN);
		nuvem.addBehavior(movimentacaoNuvem1);
		nuvem.setSpeed(50);
		
		nuvem.setSpriteSheet(new CGTSpriteSheet("data/chicken/nuvem_0000_nuvem10_vectorized.png"));
		nuvem.getSpriteSheet().setRows(1);
		nuvem.getSpriteSheet().setColumns(1);		
		
		CGTAnimation moveCarroRight = new CGTAnimation(nuvem);
		moveCarroRight.setSpriteLine(1);
		moveCarroRight.addStatePolicy(StatePolicy.LOOKLEFT);
		moveCarroRight.setSpriteVelocity(0.08f);
		moveCarroRight.setAnimationPolicy(PlayMode.LOOP);
		nuvem.getAnimarions().add(moveCarroRight);

		CGTAnimation moveCarroLeft = new CGTAnimation(nuvem);
		moveCarroLeft.setSpriteLine(1);
		moveCarroLeft.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveCarroLeft.setSpriteVelocity(0.08f);
		moveCarroLeft.setAnimationPolicy(PlayMode.LOOP);
		nuvem.getAnimarions().add(moveCarroLeft);
		
		world.getEnemies().add(nuvem);
		
		
		CGTEnemy nuvem2 = new CGTEnemy();
		
		Vector2 positionNuvem2 = new Vector2(500, 300);
		nuvem2.setPosition(positionNuvem2);

		Rectangle coliderNuvem2 = new Rectangle(100, 100, 100, 100);
		nuvem2.setCollision(coliderNuvem2);

		Rectangle tamanhoNuvem2 = new Rectangle(0, 0, 300, 300);
		nuvem2.setBounds(tamanhoNuvem2);
		
		nuvem2.setBlock(false);
		nuvem2.setDestroyable(false);
		nuvem2.setState(StatePolicy.IDLEDOWN);
		nuvem2.addBehavior(movimentacaoNuvem2);
		nuvem2.setSpeed(50);
		
		nuvem2.setSpriteSheet(new CGTSpriteSheet("data/chicken/nuvem_0001_007_vectorized.png"));
		nuvem2.getSpriteSheet().setRows(1);
		nuvem2.getSpriteSheet().setColumns(1);
				
		CGTAnimation moveNuvemRight2 = new CGTAnimation(nuvem2);
		moveNuvemRight2.setSpriteLine(1);
		moveNuvemRight2.addStatePolicy(StatePolicy.LOOKLEFT);
		moveNuvemRight2.setSpriteVelocity(0.08f);
		moveNuvemRight2.setAnimationPolicy(PlayMode.LOOP);
		nuvem2.getAnimarions().add(moveNuvemRight2);

		CGTAnimation moveNuvemLeft2 = new CGTAnimation(nuvem2);
		moveNuvemLeft2.setSpriteLine(1);
		moveNuvemLeft2.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveNuvemLeft2.setSpriteVelocity(0.08f);
		moveNuvemLeft2.setAnimationPolicy(PlayMode.LOOP);
		nuvem2.getAnimarions().add(moveNuvemLeft2);
		
		world.getEnemies().add(nuvem2);
		
		CGTEnemy nuvem3 = new CGTEnemy();
		
		Vector2 positionNuvem3 = new Vector2(200, 300);
		nuvem3.setPosition(positionNuvem3);

		Rectangle coliderNuvem3 = new Rectangle(22, 66, 250, 150);
		nuvem3.setCollision(coliderNuvem3);

		Rectangle tamanhoNuvem3 = new Rectangle(0, 0, 300, 300);
		nuvem3.setBounds(tamanhoNuvem3);
		
		nuvem3.setBlock(false);
		nuvem3.setDestroyable(false);
		nuvem3.setState(StatePolicy.IDLEDOWN);
		nuvem3.addBehavior(movimentacaoNuvem3);
		nuvem3.setSpeed(50);
		
		nuvem3.setSpriteSheet(new CGTSpriteSheet("data/chicken/nuvem_0000_nuvem10_vectorized.png"));
		nuvem3.getSpriteSheet().setRows(1);
		nuvem3.getSpriteSheet().setColumns(1);		
		
		CGTAnimation moveNuvemRight3 = new CGTAnimation(nuvem3);
		moveNuvemRight3.setSpriteLine(1);
		moveNuvemRight3.addStatePolicy(StatePolicy.LOOKLEFT);
		moveNuvemRight3.setSpriteVelocity(0.08f);
		moveNuvemRight3.setAnimationPolicy(PlayMode.LOOP);
		nuvem3.getAnimarions().add(moveNuvemRight3);

		CGTAnimation moveNuvemLeft3 = new CGTAnimation(nuvem3);
		moveNuvemLeft3.setSpriteLine(1);
		moveNuvemLeft3.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveNuvemLeft3.setSpriteVelocity(0.08f);
		moveNuvemLeft3.setAnimationPolicy(PlayMode.LOOP);
		nuvem3.getAnimarions().add(moveNuvemLeft3);
		
		world.getEnemies().add(nuvem3);		

	}
	
	public void configuracaoJumentos(){
		Direction movimentacaoJumento = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoJumento.setInitialPosition(new Vector2(100,720));
		movimentacaoJumento.setFinalPosition(new Vector2(100,-100));
		movimentacaoJumento.setInteligenceMoviment(false);
		
		Direction movimentacaoJumento2 = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		movimentacaoJumento2.setInitialPosition(new Vector2(500,720));
		movimentacaoJumento2.setFinalPosition(new Vector2(500,0));
		movimentacaoJumento2.setInteligenceMoviment(false);
		
		CGTEnemy jumento = new CGTEnemy();

		Rectangle coliderJumento = new Rectangle(22, 0, 60, 94);
		jumento.setCollision(coliderJumento);

		Rectangle tamanhoJumento = new Rectangle(0, 0, 98, 90);
		jumento.setBounds(tamanhoJumento);
		
		jumento.setLife(50);
		jumento.setBlock(true);
		jumento.setDestroyable(false);
		jumento.setDamage(50);
		jumento.addBehavior(movimentacaoJumento);
		jumento.setSpeed(100);

		jumento.setSpriteSheet(new CGTSpriteSheet(
				"data/chicken/jumento_spritesheet.png"));
		jumento.getSpriteSheet().setRows(2);
		jumento.getSpriteSheet().setColumns(4);
		
		// Action
		CGTAnimation moveJumentoDown = new CGTAnimation(jumento);
		moveJumentoDown.setInitialFrame(new Vector2(0, 0));
		moveJumentoDown.setEndingFrame(new Vector2(1, 1));
		moveJumentoDown.setSpriteVelocity(0.08f);
		moveJumentoDown.setAnimationPolicy(PlayMode.LOOP);
		jumento.getAnimarions().add(moveJumentoDown);

		world.getEnemies().add(jumento);
		
		
		CGTEnemy jumento2 = new CGTEnemy();

		Rectangle coliderJumento2 = new Rectangle(22, 0, 60, 94);
		jumento2.setCollision(coliderJumento2);

		Rectangle tamanhoJumento2 = new Rectangle(0, 0, 98, 90);
		jumento2.setBounds(tamanhoJumento2);
		
		jumento2.setLife(50);
		jumento2.setBlock(true);
		jumento2.setDestroyable(false);
		jumento2.setDamage(50);
		jumento2.addBehavior(movimentacaoJumento2);
		jumento2.setSpeed(100);

		jumento2.setSpriteSheet(new CGTSpriteSheet(
				"data/chicken/jumento_spritesheet.png")); 
		jumento2.getSpriteSheet().setRows(2);
		jumento2.getSpriteSheet().setColumns(4);
		
		// Action
		CGTAnimation moveJumentoDown2 = new CGTAnimation(jumento2);
		moveJumentoDown2.setInitialFrame(new Vector2(0, 0));
		moveJumentoDown2.setEndingFrame(new Vector2(1, 1));
		moveJumentoDown2.setSpriteVelocity(0.08f);
		moveJumentoDown2.setAnimationPolicy(PlayMode.LOOP);
		jumento2.getAnimarions().add(moveJumentoDown2);

		world.getEnemies().add(jumento2);
	}
	
	public void opositeCalcada(){
		CGTOpposite calcada = new CGTOpposite();

		Vector2 position = new Vector2(0, 100);
		calcada.setPosition(position);
		Rectangle bounds = new Rectangle(0, 0, 76, 65);
		calcada.setBounds(bounds);
		calcada.setCollision(bounds);

		calcada.setBlock(true);
		calcada.setDestroyable(false);
		calcada.setLife(0);
		
		world.getOpposites().add(calcada);
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
		backGround = new CGTTexture("data/chicken/background_final2.png");
		world.setBackground(backGround);
//		world.setMusic(new CGTSound("data/AudioDaPexe/derrota.ogg",0.3f));
		world.getCamera().setGameMode(GameModePolicy.TOUCH);
		
		CGTActor personagemCGTActor = new CGTActor();
		
		configuracaoActor(personagemCGTActor);
		
		configuracaoActionActor(personagemCGTActor);
		
		configuracaoVacas();
		
		configuracaoNuvens();
		
		configuracaoPeixes();
		
		configuracaoJumentos();
		
		opositeCalcada();
		
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
		world.getCamera().setScale(2);;
		
		world.getLoseCriteria().add(new LifeDepleted(personagemCGTActor));
		
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
