package com.projetocgt.cenario;

import java.util.ArrayList;



import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.hud.AmmoDisplay;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.hud.CGTControllerButton;
import cgt.hud.CGTLabel;
import cgt.hud.EnemyGroupLifeBar;
import cgt.hud.IndividualLifeBar;
import cgt.behaviors.Direction;
import cgt.behaviors.Fade;
import cgt.behaviors.Sine;
import cgt.core.CGTActor;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import cgt.core.CGTOpposite;
import cgt.core.CGTProjectile;
import cgt.lose.LifeDepleted;
import cgt.lose.TargetTime;
import cgt.policy.ActionMovePolicy;
import cgt.policy.BonusPolicy;
import cgt.policy.DirectionPolicy;
import cgt.policy.FadePolicy;
import cgt.policy.GameModePolicy;
import cgt.policy.InputPolicy;
import cgt.policy.MovementPolicy;
import cgt.policy.StatePolicy;
import cgt.screen.CGTDialog;
import cgt.screen.CGTScreen;
import cgt.unit.Action;
import cgt.util.ButtonPad;
import cgt.util.CGTAnimation;
import cgt.util.CGTSound;
import cgt.util.CGTSpriteSheet;
import cgt.util.CGTTexture;
import cgt.util.ProjectileOrientation;
import cgt.win.KillAllEnemies;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * Responsavel por construir o jogo
 * 
 * @author Bruno Roberto
 * 
 */
public class MyWorldPexe {

	private CGTTexture backGround;
	private CGTGameWorld world;
	private CGTScreen screen;
	private CGTGame game;
	public MyWorldPexe() {
		
		createWorld();
	}

	public void configuracaoPauseDialog() {
		CGTDialog pauseDialog = new CGTDialog();
		pauseDialog.setActive(false);
		pauseDialog.setWindow(new CGTTexture("data/dapexe/pause.png"));
		pauseDialog.setHorizontalBorderTexture(new CGTTexture(
				"data/dapexe/borda.png"));
		pauseDialog
				.setRightBottomCorner(new CGTTexture("data/dapexe/canto.png"));
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

	public void configuracaoLifeBar(CGTActor personagemCGTActor){
		IndividualLifeBar actorLifeBar = new IndividualLifeBar(personagemCGTActor);
		CGTTexture lifeBar = new CGTTexture("data/lifeBar/lifeBar.png");
		CGTTexture lifeBarBack = new CGTTexture("data/lifeBar/actorLifeBarBack.png");
		actorLifeBar.setOffsetX(0.25f);
		actorLifeBar.setBar(lifeBar);
		actorLifeBar.setBackgroundBar(lifeBarBack);
		actorLifeBar.setRelativeX(0f);
		actorLifeBar.setRelativeY(0.94f);
		actorLifeBar.setRelativeHeight(0.05f);
		actorLifeBar.setRelativeWidth(0.2f);

		actorLifeBar.setMaxLife(personagemCGTActor.getLife());
		actorLifeBar.setOwner(personagemCGTActor);

		world.addLifeBar(actorLifeBar);
	}

	public void configuracaoActor(CGTActor personagemCGTActor) {
		personagemCGTActor.setFireDefault(-1);
		personagemCGTActor.setPosition(new Vector2(800f, 900f));
		personagemCGTActor.setTimeToRecovery(4);
		personagemCGTActor.setCollision(new Rectangle(10, 10, 40, 45));

		Rectangle tamanhoPersonagem = new Rectangle(0, 0, 60, 60);
		personagemCGTActor.setBounds(tamanhoPersonagem);

		personagemCGTActor.setLife(30);
		personagemCGTActor.setSpeed(300);

		CGTSound somDamagePersonagem = new CGTSound(
				"data/AudioDaPexe/voz_1.wav");
		CGTSound somDamagePersonagem1 = new CGTSound(
				"data/AudioDaPexe/voz_4.wav");
		personagemCGTActor.setSoundCollision(somDamagePersonagem);
		personagemCGTActor.setSoundCollision(somDamagePersonagem1);

		CGTSound somDiePersonagem = new CGTSound("data/AudioDaPexe/voz_2.wav");
		CGTSound somDiePersonagem1 = new CGTSound("data/AudioDaPexe/voz_3.wav");

		personagemCGTActor.setSoundDie(somDiePersonagem);
		personagemCGTActor.setSoundDie(somDiePersonagem1);

	}

	public void configuracaoActionActor(CGTActor personagemCGTActor) {

		CGTSpriteSheet spriteGaroto = new CGTSpriteSheet(
				"data/dapexe/sprite_garoto.png");
		spriteGaroto.setRows(5);
		spriteGaroto.setColumns(3);

		CGTAnimation moveLEft = new CGTAnimation(personagemCGTActor, spriteGaroto);
		moveLEft.setSpriteLine(2);
//		moveLEft.addStatePolicy(StatePolicy.LOOKLEFT);
		moveLEft.setSpriteVelocity(0.2f);
		moveLEft.setFlipHorizontal(true);
		moveLEft.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation moveRight = new CGTAnimation(personagemCGTActor, spriteGaroto);
		moveRight.setSpriteLine(2);
//		moveRight.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveRight.setFlipHorizontal(false);
		moveRight.setSpriteVelocity(0.2f);
		moveRight.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor, spriteGaroto);
		moveUp.setSpriteLine(1);
//		moveUp.addStatePolicy(StatePolicy.LOOKUP);
		moveUp.setSpriteVelocity(0.2f);
		moveUp.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation moveDown = new CGTAnimation(personagemCGTActor, spriteGaroto);
		moveDown.setSpriteLine(3);
//		moveDown.addStatePolicy(StatePolicy.LOOKDOWN);
		moveDown.setSpriteVelocity(0.2f);
		moveDown.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation animationDamege = new CGTAnimation(personagemCGTActor, spriteGaroto);
		animationDamege.setSpriteLine(5);
//		animationDamege.addStatePolicy(StatePolicy.DAMAGE);
		animationDamege.setSpriteVelocity(0.2f);
		animationDamege.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		personagemCGTActor.addAnimation(StatePolicy.DAMAGE, animationDamege);
		personagemCGTActor.addAnimation(StatePolicy.LOOKDOWN, moveDown);
		personagemCGTActor.addAnimation(StatePolicy.LOOKLEFT, moveLEft);
		personagemCGTActor.addAnimation(StatePolicy.LOOKRIGHT, moveRight);
		personagemCGTActor.addAnimation(StatePolicy.LOOKUP, moveUp);
	}

	public void configuracaoCasasCenario() {
		for (int y = 0; y < 3; y++) {
			int x;
			for (x = 0; x < 4; x++) {
				CGTOpposite opositorCasa = new CGTOpposite();

				Vector2 position = new Vector2(90 + x * 260, 880 - y * 250);
				opositorCasa.setPosition(position);
				Rectangle bounds = new Rectangle(0, 0, 178, 165);
				opositorCasa.setBounds(bounds);
				opositorCasa.setCollision(bounds);

				opositorCasa.setBlock(true);
				opositorCasa.setDestroyable(false);
				opositorCasa.setLife(0);

				CGTSpriteSheet casa = new CGTSpriteSheet("data/dapexe/casa" + y + x + "-corte.png");

				casa.setRows(1);
				casa.setColumns(1);

				CGTAnimation teste = new CGTAnimation(opositorCasa, casa );
				teste.setSpriteLine(1);
				teste.setAnimationPolicy(PlayMode.LOOP);
				opositorCasa.addAnimation(StatePolicy.IDLEDOWN, teste);

				world.getOpposites().add(opositorCasa);
			}
			x = 0;
		}

		// ajuste da posicao do lago na tela
//		world.getOpposites()
//				.get(6)
//				.setSpriteSheet(
//						new CGTSpriteSheet("data/dapexe/casa233-corte.png"));
		world.getOpposites().get(6).setPosition(new Vector2(590, 620));
		world.getOpposites().get(6).setBounds(new Rectangle(0, 0, 204, 188));
		world.getOpposites().get(6).setCollision(new Rectangle(0, 0, 204, 188));

		// ajuste das colisões das casas
		world.getOpposites().get(0)
				.setCollision(new Rectangle(23, 0, 125, 140));
		world.getOpposites().get(1)
				.setCollision(new Rectangle(30, 0, 125, 140));
		world.getOpposites().get(2)
				.setCollision(new Rectangle(0, 15, 178, 130));
		world.getOpposites().get(4)
				.setCollision(new Rectangle(0, 20, 178, 120));
		world.getOpposites().get(5)
				.setCollision(new Rectangle(0, 20, 178, 120));
		world.getOpposites().get(7).setCollision(new Rectangle(0, 0, 178, 140));
		world.getOpposites().get(9).setCollision(new Rectangle(0, 0, 178, 140));
		world.getOpposites().get(10)
				.setCollision(new Rectangle(23, 0, 125, 140));
		world.getOpposites().get(11)
				.setCollision(new Rectangle(0, 20, 178, 120));
	}

	public void configuracaoMar() {
		CGTOpposite mar = new CGTOpposite();
		mar.setPosition(new Vector2(0, 0));
		Rectangle bounds = new Rectangle(0, 0, 1200, 240);
		mar.setBounds(bounds);

		Rectangle collision = new Rectangle(0, 0, 1200, 120);
		mar.setCollision(collision);

		mar.setBlock(true);

		CGTSpriteSheet marSprite = new CGTSpriteSheet("data/dapexe/mar4.png");

		marSprite.setRows(10);
		marSprite.setColumns(5);

		CGTSound marSound = new CGTSound("data/AudioDaPexe/mar.wav", 1);
		mar.setSound(marSound);

		CGTAnimation marAnimation = new CGTAnimation(mar, marSprite);
		marAnimation.setSpriteVelocity(0.08f);
		marAnimation.setInitialFrame(new Vector2(0,0));
		marAnimation.setEndingFrame(new Vector2(4,9));
		marAnimation.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		mar.addAnimation(StatePolicy.IDLE, marAnimation);
		world.addOpposite(mar);
	}

	public void configuracaoCarros() {
		// inicializando o carro no cenario

		Direction direction = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		direction.setInitialPosition(new Vector2(700, 560));
		direction.setFinalPosition(new Vector2(20, 560));

		Direction directionUp = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
		directionUp.setFinalPosition(new Vector2(780, 900));
		directionUp.setInitialPosition(new Vector2(780, 200));

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

		CGTSpriteSheet carroSprite = new CGTSpriteSheet(
				"data/dapexe/SpriteSheet_carro_jeep.png");
		carroSprite.setRows(3);
		carroSprite.setColumns(2);

		CGTSound soundCar = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.2f);
		carroCGT.setSound(soundCar);

		// Action
		CGTAnimation moveCarroDown = new CGTAnimation(carroCGT, carroSprite);
		moveCarroDown.setSpriteLine(2);

		moveCarroDown.setSpriteVelocity(0.08f);
		moveCarroDown.setAnimationPolicy(PlayMode.LOOP);
		carroCGT.addAnimation(StatePolicy.LOOKDOWN, moveCarroDown);

		CGTAnimation moveCarroUp = new CGTAnimation(carroCGT, carroSprite);
		moveCarroUp.setSpriteLine(3);
		moveCarroUp.setSpriteVelocity(0.08f);
		moveCarroUp.setAnimationPolicy(PlayMode.LOOP);
		carroCGT.addAnimation(StatePolicy.LOOKUP, moveCarroUp);

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
		carroCGT2.setDamage(10);
		carroCGT2.addBehavior(direction);

		carroCGT2.setSpeed(200);

		CGTSound soundCar2 = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.2f);

		carroCGT2.setSound(soundCar2);

		// Action

		CGTAnimation moveCarro = new CGTAnimation(carroCGT2, carroSprite);
		moveCarro.setSpriteLine(1);
		moveCarro.setSpriteVelocity(0.08f);
		moveCarro.setAnimationPolicy(PlayMode.LOOP);
		carroCGT2.addAnimation(StatePolicy.LOOKRIGHT, moveCarro);

		CGTAnimation moveCarroLeft = new CGTAnimation(carroCGT2, carroSprite);
		moveCarroLeft.setFlipHorizontal(true);
		moveCarroLeft.setSpriteLine(1);
		moveCarroLeft.setSpriteVelocity(0.08f);
		moveCarroLeft.setAnimationPolicy(PlayMode.LOOP);
		carroCGT2.addAnimation(StatePolicy.LOOKLEFT, moveCarroLeft);

		// Add na lista de enemy
		world.getEnemies().add(carroCGT2);

	}

	public void configuracaoPeixes() {
		Fade fade1 = new Fade(FadePolicy.FADE_IN);
		fade1.setFadeInTime(0);

		Fade fade5 = new Fade(FadePolicy.FADE_IN);
		fade5.setFadeInTime(5);

		Sine sine = new Sine(MovementPolicy.HEIGHT);
		sine.setMax(187);
		sine.setMin(177);
		sine.setAtFirstStep(true);

		CGTEnemy alertaPeixe = new CGTEnemy();

		ArrayList<Vector2> initialPositions = new ArrayList<Vector2>();

		initialPositions.add(world.getOpposites().get(0).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(1).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(2).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(3).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(4).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(5).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(7).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(8).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(9).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(10).getPosition().cpy());
		initialPositions.add(world.getOpposites().get(11).getPosition().cpy());

		alertaPeixe.setInitialPositions(initialPositions);

		alertaPeixe.setPosition(alertaPeixe.getPosition());

		// System.exit(0);
		Rectangle coliderEnemy = new Rectangle(0, 0, 269, 177);
		alertaPeixe.setCollision(coliderEnemy);

		Rectangle tamanhoEnemy = new Rectangle(0, 0, 269, 177);
		alertaPeixe.setBounds(tamanhoEnemy);

		alertaPeixe.setState(StatePolicy.IDLEDOWN);
		alertaPeixe.setBlock(false);
		alertaPeixe.setDamage(0);
		alertaPeixe.setSpeed(1);
		alertaPeixe.setDestroyable(true);
		alertaPeixe.addBehavior(sine);
		alertaPeixe.setLife(50);

		CGTSpriteSheet alertaPeixeSprite = new CGTSpriteSheet(
				"data/dapexe/alert_peixe.png");
		alertaPeixeSprite.setRows(1);
		alertaPeixeSprite.setColumns(1);
		CGTSound somPexeDie = new CGTSound(
				"data/AudioDaPexe/caixa_registradora.wav");
		alertaPeixe.setSoundDie(somPexeDie);

		// Action
		CGTAnimation moveEnemy = new CGTAnimation(alertaPeixe, alertaPeixeSprite);
		moveEnemy.setSpriteLine(1);
		moveEnemy.setSpriteVelocity(0.08f);
		moveEnemy.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		alertaPeixe.addAnimation(StatePolicy.IDLEDOWN, moveEnemy);
		world.getEnemies().add(alertaPeixe);

		CGTEnemy alertaPeixe2 = new CGTEnemy();

		alertaPeixe2.setInitialPositions(initialPositions);
		alertaPeixe2.setPosition(alertaPeixe2.getPosition());

		Rectangle coliderEnemy2 = new Rectangle(0, 0, 269, 177);
		alertaPeixe2.setCollision(coliderEnemy2);

		Rectangle tamanhoEnemy2 = new Rectangle(0, 0, 269, 177);
		alertaPeixe2.setBounds(tamanhoEnemy2);

		Sine sine2 = new Sine(MovementPolicy.HEIGHT);
		sine2.setMax(187);
		sine2.setMin(177);
		sine2.setAtFirstStep(true);

		alertaPeixe2.setState(StatePolicy.IDLEDOWN);
		alertaPeixe2.setBlock(false);
		alertaPeixe2.setDamage(0);
		alertaPeixe2.setSpeed(1);
		alertaPeixe2.setDestroyable(true);
		alertaPeixe2.addBehavior(fade5);
		alertaPeixe2.addBehavior(sine2);
		alertaPeixe2.setLife(50);

		CGTSound somPexeDie2 = new CGTSound(
				"data/AudioDaPexe/caixa_registradora.wav");
		alertaPeixe2.setSoundDie(somPexeDie2);

		// Action
		CGTAnimation moveEnemy2 = new CGTAnimation(alertaPeixe2, alertaPeixeSprite);
		moveEnemy2.setSpriteLine(1);
		moveEnemy2.setSpriteVelocity(0.08f);
		moveEnemy2.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		alertaPeixe2.addAnimation(StatePolicy.IDLEDOWN, moveEnemy2);
		world.getEnemies().add(alertaPeixe2);

		CGTEnemy alertaPeixe3 = new CGTEnemy();

		alertaPeixe3.setInitialPositions(initialPositions);

		alertaPeixe3.setPosition(alertaPeixe3.getPosition());

		Rectangle coliderEnemy3 = new Rectangle(0, 0, 269, 177);
		alertaPeixe3.setCollision(coliderEnemy3);

		Rectangle tamanhoEnemy3 = new Rectangle(0, 0, 269, 177);
		alertaPeixe3.setBounds(tamanhoEnemy3);

		Sine sine3 = new Sine(MovementPolicy.HEIGHT);
		sine3.setMax(187);
		sine3.setMin(177);
		sine3.setAtFirstStep(true);

		alertaPeixe3.setState(StatePolicy.IDLEDOWN);
		alertaPeixe3.setBlock(false);
		alertaPeixe3.setDamage(0);
		alertaPeixe3.setSpeed(1);
		alertaPeixe3.setDestroyable(true);
		alertaPeixe3.addBehavior(sine3);
		alertaPeixe3.setLife(50);

		CGTSound somPexeDie3 = new CGTSound(
				"data/AudioDaPexe/caixa_registradora.wav");
		alertaPeixe3.setSoundDie(somPexeDie3);

		CGTAnimation moveEnemy3 = new CGTAnimation(alertaPeixe3, alertaPeixeSprite);
		moveEnemy3.setSpriteLine(1);
		moveEnemy3.setSpriteVelocity(0.08f);
		moveEnemy3.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		alertaPeixe3.addAnimation(StatePolicy.IDLEDOWN, moveEnemy3);
		world.getEnemies().add(alertaPeixe3);

		EnemyGroupLifeBar alerts = new EnemyGroupLifeBar(world.getEnemies());
		CGTTexture lifeBar = new CGTTexture("data/lifeBar/lifeBar.png");
		CGTTexture lifeBarBack = new CGTTexture(
				"data/lifeBar/enemyLifeBarBack.png");
		alerts.setBar(lifeBar);
		alerts.setOffsetX(0.25f);
		alerts.setBackgroundBar(lifeBarBack);
		alerts.setRelativeX(0.78f);
		alerts.setRelativeY(0.94f);
		alerts.setRelativeHeight(0.05f);
		alerts.setRelativeWidth(0.2f);
		world.addLifeBar(alerts);
	}

	public void configuracaoJangada() {
		CGTBonus jangada = new CGTBonus();

		jangada.setPosition(new Vector2(920, 180));
		jangada.setBounds(new Rectangle(0, 0, 255, 224));
		jangada.setCollision(new Rectangle(0, 0, 255, 80));
		jangada.addPolicy(BonusPolicy.ADD_AMMO);
		jangada.setLife(4);
		jangada.setScore(4);

		CGTSpriteSheet spriteSheet = new CGTSpriteSheet(
				"data/dapexe/jangada.png");
		spriteSheet.setColumns(1);
		spriteSheet.setRows(2);

		CGTAnimation idle = new CGTAnimation(jangada, spriteSheet);
		idle.setSpriteLine(1);

		CGTAnimation die = new CGTAnimation(jangada, spriteSheet);
		die.setSpriteLine(2);

		CGTSound somCollisionJangada = new CGTSound(
				"data/AudioDaPexe/splash.wav");
		jangada.setSoundCollision(somCollisionJangada);

		jangada.addAnimation(StatePolicy.IDLE, idle);
		jangada.addAnimation(StatePolicy.DIE, die);
		world.getBonus().add(jangada);
	}

	public void configuracaoProjetil(CGTActor personagemCGTActor) {
		CGTProjectile projetilPeixe = new CGTProjectile();

		Vector2 position = new Vector2(100f, 200f);
		projetilPeixe.setPosition(position);

		projetilPeixe.setBounds(new Rectangle(0, 0, 30, 30));
		Rectangle coliderProjectile = new Rectangle(0, 0, 30, 30);

		projetilPeixe.setCollision(coliderProjectile);
		projetilPeixe.setInterval(1);

		CGTSpriteSheet css = new CGTSpriteSheet(
				("data/dapexe/peixe_entrega.png"));
		css.setRows(1);
		css.setColumns(2);
		projetilPeixe.setMaxAmmo(8);
		projetilPeixe.setAmmo(8);

		// Action dos projectiles
		CGTAnimation m = new CGTAnimation(projetilPeixe, css);
		m.setSpriteLine(1);
		m.setFlipHorizontal(true);
		m.setSpriteVelocity(1f);
		m.setAnimationPolicy(PlayMode.LOOP);

		CGTAnimation a = new CGTAnimation(projetilPeixe, css);
		a.setSpriteLine(1);
		a.setFlipHorizontal(false);
		a.setSpriteVelocity(1);
		a.setAnimationPolicy(PlayMode.LOOP);

		CGTAnimation down = new CGTAnimation(projetilPeixe, css);
		down.setSpriteLine(1);
		down.setFlipHorizontal(false);
		down.setSpriteVelocity(1f);
		down.setAnimationPolicy(PlayMode.LOOP);

		CGTAnimation up = new CGTAnimation(projetilPeixe, css);
		up.setSpriteLine(1);
		up.setFlipHorizontal(true);
		up.setSpriteVelocity(1f);
		up.setAnimationPolicy(PlayMode.LOOP);
		
		projetilPeixe.addAnimation(StatePolicy.IDLEUP, up);
		projetilPeixe.addAnimation(StatePolicy.LOOKUP, up);
		projetilPeixe.addAnimation(StatePolicy.LOOKDOWN, down);
		projetilPeixe.addAnimation(StatePolicy.IDLEDOWN, down);
		projetilPeixe.addAnimation(StatePolicy.LOOKRIGHT, a);
		projetilPeixe.addAnimation(StatePolicy.IDLERIGHT, a);
		projetilPeixe.addAnimation(StatePolicy.LOOKLEFT, m);
		projetilPeixe.addAnimation(StatePolicy.IDLELEFT, m);

		// Projectile orientation
		ProjectileOrientation direcaoRight = new ProjectileOrientation();
		direcaoRight.setPositionRelativeToGameObject(new Vector2(60f, 15f));
		direcaoRight.addState(StatePolicy.LOOKRIGHT);
		direcaoRight.addState(StatePolicy.IDLERIGHT);
		projetilPeixe.getOrientations().add(direcaoRight);

		ProjectileOrientation direcaoLeft = new ProjectileOrientation();
		direcaoLeft.setPositionRelativeToGameObject(new Vector2(-13f, 15f));
		direcaoLeft.addState(StatePolicy.LOOKLEFT);
		direcaoLeft.addState(StatePolicy.IDLELEFT);
		projetilPeixe.getOrientations().add(direcaoLeft);

		ProjectileOrientation direcaoUp = new ProjectileOrientation();
		direcaoUp.setPositionRelativeToGameObject(new Vector2(30f, 60f));
		direcaoUp.addState(StatePolicy.LOOKUP);
		direcaoUp.addState(StatePolicy.IDLEUP);
		projetilPeixe.getOrientations().add(direcaoUp);

		ProjectileOrientation direcaoDown = new ProjectileOrientation();
		direcaoDown.setPositionRelativeToGameObject(new Vector2(25f, -20f));
		direcaoDown.addState(StatePolicy.LOOKDOWN);
		direcaoDown.addState(StatePolicy.IDLEDOWN);
		projetilPeixe.getOrientations().add(direcaoDown);
		personagemCGTActor.getProjectiles().add(projetilPeixe);

		personagemCGTActor.addProjectile(projetilPeixe);
		configuraAmmoCounter(projetilPeixe);
		world.setActor(personagemCGTActor);
	}

	public void configuracaoInputs() {
		Action walkUp = new Action(ActionMovePolicy.WALK_DOWN,
				InputPolicy.BTN_DOWN);
		Action walkDown = new Action(ActionMovePolicy.WALK_UP,
				InputPolicy.BTN_UP);
		Action walkLeft = new Action(ActionMovePolicy.WALK_RIGHT,
				InputPolicy.BTN_RIGHT);
		Action walkRight = new Action(ActionMovePolicy.WALK_LEFT,
				InputPolicy.BTN_LEFT);
		Action fire = new Action(ActionMovePolicy.FIRE, InputPolicy.BTN_1);

		world.addAction(walkUp, walkDown, walkLeft, walkRight, fire);
	}

	public void configuracaoButtonPad() {
		CGTButton buttonPad = new CGTButton();

		CGTTexture textureUp = new CGTTexture("data/buttons/base.png");
		buttonPad.setTextureUp(textureUp);
		CGTTexture textureDown = new CGTTexture("data/buttons/base.png");
		buttonPad.setTextureDown(textureDown);

		buttonPad.setRelativeX(0);
		buttonPad.setRelativeY(0);
		buttonPad.setRelativeWidth(0.29f);
		buttonPad.setRelativeHeight(0.29f);
		buttonPad.setBounds(0, 0, textureUp.getWidth() / 3,
				textureUp.getHeight() / 3);

		CGTControllerButton button = new CGTControllerButton();
		button.setInput(InputPolicy.BTN_UP);

		System.out.println(button.getInput());

		textureUp = new CGTTexture("data/buttons/bt_up_up.png");
		button.setTextureUp(textureUp);

		textureDown = new CGTTexture("data/buttons/bt_up_press.png");
		button.setTextureDown(textureDown);

		button.setRelativeX(0.095f);
		button.setRelativeY(0.145f);
		button.setRelativeWidth(0.1f);
		button.setRelativeHeight(0.1f);
		button.setBounds(137 / 3, 184.7f / 3, textureUp.getWidth() / 3,
				textureUp.getHeight() / 3);

		CGTControllerButton buttonDown = new CGTControllerButton();
		buttonDown.setInput(InputPolicy.BTN_DOWN);

		textureUp = new CGTTexture("data/buttons/bt_down_up.png");
		buttonDown.setTextureUp(textureUp);
		textureDown = new CGTTexture("data/buttons/bt_down_press.png");
		buttonDown.setTextureDown(textureDown);

		buttonDown.setRelativeX(0.095f);
		buttonDown.setRelativeY(0.028f);
		buttonDown.setRelativeWidth(0.1f);
		buttonDown.setRelativeHeight(0.1f);
		buttonDown.setBounds(137 / 3, 36 / 3, textureUp.getWidth() / 3,
				textureUp.getHeight() / 3);

		CGTControllerButton buttonLeft = new CGTControllerButton();
		buttonLeft.setInput(InputPolicy.BTN_LEFT);

		textureUp = new CGTTexture("data/buttons/bt_left_up.png");
		buttonLeft.setTextureUp(textureUp);

		textureDown = new CGTTexture("data/buttons/bt_left_press.png");
		buttonLeft.setTextureDown(textureDown);

		buttonLeft.setRelativeX(0.048f);
		buttonLeft.setRelativeY(0.09f);
		buttonLeft.setRelativeWidth(0.1f);
		buttonLeft.setRelativeHeight(0.1f);
		buttonLeft.setBounds(64 / 3, 126 / 3, textureUp.getWidth() / 3,
				textureUp.getHeight() / 3);

		CGTControllerButton buttonRight = new CGTControllerButton();
		buttonRight.setInput(InputPolicy.BTN_RIGHT);

		textureUp = new CGTTexture("data/buttons/bt_right_up.png");
		buttonRight.setTextureUp(textureUp);
		textureDown = new CGTTexture("data/buttons/bt_right_press.png");
		buttonRight.setTextureDown(textureDown);

		buttonRight.setRelativeX(0.142f);
		buttonRight.setRelativeY(0.09f);
		buttonRight.setRelativeWidth(0.1f);
		buttonRight.setRelativeHeight(0.1f);
		buttonRight.setBounds(183 / 3, 126 / 3, textureUp.getWidth() / 3,
				textureUp.getHeight() / 3);

		CGTControllerButton button1 = new CGTControllerButton();
		button1.setInput(InputPolicy.BTN_1);

		textureUp = new CGTTexture("data/buttons/bt_agua_up.png");
		button1.setTextureUp(textureUp);
		textureDown = new CGTTexture("data/buttons/bt_agua_down.png");
		button1.setTextureDown(textureDown);

		button1.setRelativeX(0.9f);
		button1.setRelativeY(0.1f);
		button1.setRelativeWidth(0.1f);
		button1.setRelativeHeight(0.1f);
		button1.setBounds(0, 0, textureUp.getWidth() / 2,
				textureUp.getHeight() / 2);

		world.addButton(buttonPad);
		world.addButton(button);
		world.addButton(buttonDown);
		world.addButton(buttonLeft);
		world.addButton(buttonRight);
		world.addButton(button1);
	}

	public void configuracaoButtonFire(){
		CGTControllerButton button1 = new CGTControllerButton();
		button1.setInput(InputPolicy.BTN_1);

		CGTTexture textureUp = new CGTTexture("data/buttons/bt_agua_up.png");
		button1.setTextureUp(textureUp);
		CGTTexture textureDown = new CGTTexture("data/buttons/bt_agua_down.png");
		button1.setTextureDown(textureDown);

		button1.setRelativeX(0.9f);
		button1.setRelativeY(0.1f);
		button1.setRelativeWidth(0.1f);
		button1.setRelativeHeight(0.1f);
		button1.setBounds(0, 0, textureUp.getWidth() / 2,
				textureUp.getHeight() / 2);

		world.addButton(button1);

	}
	
	private void configuraTimer() {
		CGTLabel labelHUD = new CGTLabel("fonts/myfont.TTF","x");
		labelHUD.setRelativeHeight(0.05f);
		labelHUD.setRelativeX(0.45f);
		labelHUD.setRelativeY(0.9f);

		world.addHUDComponent(labelHUD);

		world.addLoseCriterion(new TargetTime(20, labelHUD));
		world.addLoseCriterion(new LifeDepleted(world.getActor()));
		world.addWinCriterion(new KillAllEnemies(world.getEnemies()));

	}

	private void configuraAmmoCounter(CGTProjectile projectile) {
		CGTLabel label = new CGTLabel("fonts/myfont.TTF", "x",20);

		CGTTexture texture = new CGTTexture("data/dapexe/ammo.png");

		AmmoDisplay counter = new AmmoDisplay(texture, projectile, label);
		counter.setRelativeHeight(0.05f);
		counter.setRelativeWidth(0.05f);
		counter.setRelativeX(0.01f);
		counter.setRelativeY(0.85f);
		world.addHUDComponent(counter);
	}

	public void oppositeFalesia() {
		CGTOpposite falesia = new CGTOpposite();

		Vector2 position = new Vector2(0, 1100);
		falesia.setPosition(position);
		Rectangle bounds = new Rectangle(0, 0, 1200, 100);
		falesia.setBounds(bounds);
		falesia.setCollision(bounds);

		falesia.setBlock(true);
		falesia.setDestroyable(false);
		falesia.setLife(0);

		// CGTAnimation animacao = new CGTAnimation(falesia);
		// animacao.addStatePolicy(StatePolicy.IDLEDOWN);
		// animacao.setAnimationPolicy(PlayMode.LOOP);
		// falesia.getAnimarions().add(animacao);

		world.getOpposites().add(falesia);
	}

	/**
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		world = new CGTGameWorld();
		backGround = new CGTTexture("data/dapexe/casas_ceara_cenario.png");
		world.setBackground(backGround);
		world.setMusic(new CGTSound("data/AudioDaPexe/temaDaPexe.ogg",0.3f));
		world.setSoundWin(new CGTSound("data/AudioDaPexe/vitoria.ogg", 0.8f));
		world.setSoundLose(new CGTSound("data/AudioDaPexe/derrota.ogg",0.8f));
		world.getCamera().setGameMode(GameModePolicy.JOYSTICK);
		world.getCamera().setInitialHeight(0.6f);
		world.getCamera().setInitialWidth(0.6f);
		

		CGTActor personagemCGTActor = new CGTActor();

		configuracaoActor(personagemCGTActor);

		configuracaoLifeBar(personagemCGTActor);

		configuracaoActionActor(personagemCGTActor);

		configuracaoCasasCenario();

		configuracaoMar();

		configuracaoPeixes();

		configuracaoCarros();

		configuracaoJangada();

		configuracaoProjetil(personagemCGTActor);

		
		ButtonPad buttonPad = new ButtonPad(1.2f);
		world.addButtonPad(buttonPad);
		
		configuracaoButtonFire();

		oppositeFalesia();

		//configuracaoButtonPad();

		configuraTimer();

		configuracaoInputs();

		// Button iniciar
		CGTTexture t = new CGTTexture("data/dapexe/menuInicial.png");
		CGTButtonScreen btn = new CGTButtonScreen();
		btn.setRelativeX(0.39f);
		btn.setRelativeY(0.78f);
		btn.setRelativeWidth(0.20f);
		btn.setRelativeHeight(0.1f);
		CGTTexture texture = new CGTTexture("data/dapexe/iniciar1.png");
		btn.setTextureDown(texture);
		btn.setTextureUp(texture);
		btn.setBounds(0, 0, texture.getWidth(), texture.getHeight());	
		btn.setScreenToGo(world);
		screen = new CGTScreen(t);
		screen.getButtons().add(btn);
		
		// Button tutorial
		CGTTexture textureTutorial = new CGTTexture("data/dapexe/menuTutorial.png");
		CGTButtonScreen buttonTutorial = new CGTButtonScreen();
		buttonTutorial.setRelativeX(0.39f);
		buttonTutorial.setRelativeY(0.45f);
		buttonTutorial.setRelativeWidth(0.20f);
		buttonTutorial.setRelativeHeight(0.1f);
		CGTTexture textureButtonTutorial = new CGTTexture("data/dapexe/tutorial.png");
		buttonTutorial.setTextureDown(textureButtonTutorial);
		buttonTutorial.setTextureUp(textureButtonTutorial);
		buttonTutorial.setBounds(0, 0, textureButtonTutorial.getWidth(), textureButtonTutorial.getHeight());
		CGTScreen screenTutorial = new CGTScreen(textureTutorial);
		buttonTutorial.setScreenToGo(screenTutorial);
		screen.getButtons().add(buttonTutorial);
		
		// Button voltar na tela do tutorial
		CGTButtonScreen buttonTutorialBack = new CGTButtonScreen();
		buttonTutorialBack.setRelativeX(0.8f);
		buttonTutorialBack.setRelativeY(0.8f);
		buttonTutorialBack.setRelativeWidth(0.17f);
		buttonTutorialBack.setRelativeHeight(0.1f);
		CGTTexture textureButtonTutorialBack = new CGTTexture("data/dapexe/back_btn.png");
		buttonTutorialBack.setTextureDown(textureButtonTutorialBack);
		buttonTutorialBack.setTextureUp(textureButtonTutorialBack);
		buttonTutorialBack.setBounds(0, 0, textureButtonTutorialBack.getWidth(), textureButtonTutorialBack.getHeight());
		buttonTutorialBack.setScreenToGo(screen);
		screenTutorial.getButtons().add(buttonTutorialBack);
		
		// Button Creditos
		CGTTexture textureCreditos = new CGTTexture("data/dapexe/menuCreditos.png");
		CGTButtonScreen buttonCreditos = new CGTButtonScreen();
		buttonCreditos.setRelativeX(0.39f);
		buttonCreditos.setRelativeY(0.34f);
		buttonCreditos.setRelativeWidth(0.20f);
		buttonCreditos.setRelativeHeight(0.1f);
		CGTTexture textureCreditosButton = new CGTTexture("data/dapexe/creditos.png");
		buttonCreditos.setTextureDown(textureCreditosButton);
		buttonCreditos.setTextureUp(textureCreditosButton);
		buttonCreditos.setBounds(0, 0, textureCreditosButton.getWidth(), textureCreditosButton.getHeight());
		CGTScreen screenCreditos = new CGTScreen(textureCreditos);
		buttonCreditos.setScreenToGo(screenCreditos);
		screen.getButtons().add(buttonCreditos);
		
		// Button voltar na tela do tutorial
		CGTButtonScreen buttonCreditosBack = new CGTButtonScreen();
		buttonCreditosBack.setRelativeX(0.8f);
		buttonCreditosBack.setRelativeY(0.8f);
		buttonCreditosBack.setRelativeWidth(0.20f);
		buttonCreditosBack.setRelativeHeight(0.1f);
		CGTTexture textureButtonCreditosBack = new CGTTexture("data/dapexe/back_btn.png");
		buttonCreditosBack.setTextureDown(textureButtonCreditosBack);
		buttonCreditosBack.setTextureUp(textureButtonCreditosBack);
		buttonCreditosBack.setBounds(0, 0, textureButtonCreditosBack.getWidth(), textureButtonCreditosBack.getHeight());
		buttonCreditosBack.setScreenToGo(screen);
		screenCreditos.getButtons().add(buttonCreditosBack);
		
		game = new CGTGame();
		game.setMenu(screen);
		configuracaoPauseDialog();
		configuracaoWinDialog();
		
		configuracaoLDialog();
		game.saveGame();
	 }

	

	public CGTGameWorld getWorld() {
		return world;
	}

	public CGTGame getGame() {
		
		return game;
	// return CGTGame.getSavedGame();
	}
}
