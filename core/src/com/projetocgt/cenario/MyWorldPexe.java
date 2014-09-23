package com.projetocgt.cenario;


import cgt.CGTGame;
import cgt.CGTGameWorld;
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
import cgt.policy.BonusPolicy;
import cgt.policy.DirectionPolicy;
import cgt.policy.FadePolicy;
import cgt.policy.InputPolicy;
import cgt.policy.MovementPolicy;
import cgt.policy.StatePolicy;
import cgt.screen.CGTButtonScreen;
import cgt.screen.CGTDialog;
import cgt.screen.CGTScreen;
import cgt.util.CGTAnimation;
import cgt.util.CGTButton;
import cgt.util.CGTLabel;
import cgt.util.CGTSound;
import cgt.util.CGTSpriteSheet;
import cgt.util.CGTTexture;
import cgt.util.EnemyGroupLifeBar;
import cgt.util.IndividualLifeBar;
import cgt.util.LifeBar;
import cgt.util.ProjectileOrientation;
import cgt.win.KillAllEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;


/**
 * Responsavel por construir o jogo
 * @author Bruno Roberto
 *
 */
public class MyWorldPexe {

	private Texture backGround;
	private CGTGameWorld world;
	private CGTScreen screen;
	private CGTGame game;

	public MyWorldPexe() {
		createWorld();
	}

	public void configuracaoPauseDialog(){
		CGTDialog pauseDialog = new CGTDialog();
		pauseDialog.setActive(false);
		pauseDialog.setWindow(new Texture(Gdx.files.internal("data/dapexe/pause.png")));
		pauseDialog.setHorizontalBorderTexture(new Texture(Gdx.files.internal("data/dapexe/borda.png")));
		pauseDialog.setRightBottomCorner(new Texture(Gdx.files.internal("data/dapexe/canto.png")));
		pauseDialog.setRelativeX(0.20f);
		pauseDialog.setRelativeY(0.20f);
		pauseDialog.setRelativeWidth(0.6f);
		pauseDialog.setRelativeHeight(0.6f);

		CGTButton voltar = new CGTButtonScreen();
		voltar.setTextureUp(new Texture(Gdx.files.internal("data/dapexe/close_btn.png")));
		voltar.setTextureDown(new Texture(Gdx.files.internal("data/dapexe/close_btn.png")));
		voltar.setRelativeX(0.65f);
		voltar.setRelativeY(0.65f);
		voltar.setRelativeWidth(0.1f);
		voltar.setRelativeHeight(0.1f);


		CGTButtonScreen voltarMenu = new CGTButtonScreen();
		voltarMenu.setScreenToGo(game.getMenu());
		voltarMenu.setTextureUp(new Texture(Gdx.files.internal("data/dapexe/back_btn.png")));
		voltarMenu.setTextureDown(new Texture(Gdx.files.internal("data/dapexe/back_btn.png")));
		voltarMenu.setRelativeX(0.25f);
		voltarMenu.setRelativeY(0.65f);
		voltarMenu.setRelativeWidth(0.1f);
		voltarMenu.setRelativeHeight(0.1f);

		pauseDialog.addButton(voltarMenu);
		pauseDialog.setCloseButton(voltar);
		world.setPauseDialog(pauseDialog);
	}

	public void configuracaoWinDialog(){
		CGTDialog pauseDialog = new CGTDialog();
		pauseDialog.setActive(false);
		pauseDialog.setWindow(new Texture(Gdx.files.internal("data/dapexe/Ganhou.png")));
		pauseDialog.setHorizontalBorderTexture(new Texture(Gdx.files.internal("data/dapexe/borda.png")));
		pauseDialog.setRightBottomCorner(new Texture(Gdx.files.internal("data/dapexe/canto.png")));
		pauseDialog.setRelativeX(0.20f);
		pauseDialog.setRelativeY(0.20f);
		pauseDialog.setRelativeWidth(0.6f);
		pauseDialog.setRelativeHeight(0.6f);

		CGTButton voltar = new CGTButtonScreen();
		voltar.setTextureUp(new Texture(Gdx.files.internal("data/dapexe/close_btn.png")));
		voltar.setTextureDown(new Texture(Gdx.files.internal("data/dapexe/close_btn.png")));
		voltar.setRelativeX(0.65f);
		voltar.setRelativeY(0.65f);
		voltar.setRelativeWidth(0.1f);
		voltar.setRelativeHeight(0.1f);


		CGTButtonScreen voltarMenu = new CGTButtonScreen();
		voltarMenu.setScreenToGo(game.getMenu());
		voltarMenu.setTextureUp(new Texture(Gdx.files.internal("data/dapexe/back_btn.png")));
		voltarMenu.setTextureDown(new Texture(Gdx.files.internal("data/dapexe/back_btn.png")));
		voltarMenu.setRelativeX(0.45f);
		voltarMenu.setRelativeY(0.25f);
		voltarMenu.setRelativeWidth(0.1f);
		voltarMenu.setRelativeHeight(0.1f);

		pauseDialog.addButton(voltarMenu);
		world.setWinDialog(pauseDialog);
	}

	public void configuracaoLifeBar(CGTActor personagemCGTActor){
		IndividualLifeBar actorLifeBar = new IndividualLifeBar(personagemCGTActor);
		Texture lifeBar = new Texture(Gdx.files.internal("data/lifeBar/lifeBar.png"));
		Texture lifeBarBack = new Texture(Gdx.files.internal("data/lifeBar/actorLifeBarBack.png"));
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

	public void configuracaoActor(CGTActor personagemCGTActor){
		personagemCGTActor.setFireDefault(-1);
		personagemCGTActor.setPosition(new Vector2(800f,900f));
		personagemCGTActor.setTimeToRecovery(4);
		personagemCGTActor.setCollision(new Rectangle(10, 10, 40, 45));

		Rectangle tamanhoPersonagem = new Rectangle(0, 0, 60, 60);
		personagemCGTActor.setBounds(tamanhoPersonagem);

		personagemCGTActor.setLife(30);
		personagemCGTActor.setSpeed(400);
		
		personagemCGTActor.setSpriteSheet(new CGTSpriteSheet("data/dapexe/sprite_garoto.png"));
		personagemCGTActor.getSpriteSheet().setRows(5);
		personagemCGTActor.getSpriteSheet().setColumns(3);

		CGTSound somDamagePersonagem =  new CGTSound("data/AudioDaPexe/voz_1.wav");
		CGTSound somDamagePersonagem1 =  new CGTSound("data/AudioDaPexe/voz_4.wav");
		personagemCGTActor.setSoundCollision(somDamagePersonagem);
		personagemCGTActor.setSoundCollision(somDamagePersonagem1);


		CGTSound somDiePersonagem = new CGTSound("data/AudioDaPexe/voz_2.wav");
		CGTSound somDiePersonagem1 =  new CGTSound("data/AudioDaPexe/voz_3.wav");

		personagemCGTActor.setSoundDie(somDiePersonagem);
		personagemCGTActor.setSoundDie(somDiePersonagem1);

	}

	public void configuracaoActionActor(CGTActor personagemCGTActor){
		CGTAnimation moveLEft = new CGTAnimation(personagemCGTActor);
		moveLEft.setSpriteLine(2);
		moveLEft.addStatePolicy(StatePolicy.LOOKLEFT);		
		moveLEft.setSpriteVelocity(0.2f);
		moveLEft.setFlipHorizontal(true);
		moveLEft.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation moveRight = new CGTAnimation(personagemCGTActor);
		moveRight.setSpriteLine(2);
		moveRight.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveRight.setFlipHorizontal(false);
		moveRight.setSpriteVelocity(0.2f);
		moveRight.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor);
		moveUp.setSpriteLine(1);
		moveUp.addStatePolicy(StatePolicy.LOOKUP);
		moveUp.setSpriteVelocity(0.2f);
		moveUp.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation moveDown = new CGTAnimation(personagemCGTActor);
		moveDown.setSpriteLine(3);
		moveDown.addStatePolicy(StatePolicy.LOOKDOWN);
		moveDown.setSpriteVelocity(0.2f);
		moveDown.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		CGTAnimation animationDamege = new CGTAnimation(personagemCGTActor);
		animationDamege.setSpriteLine(5);
		animationDamege.addStatePolicy(StatePolicy.DAMAGE);
		animationDamege.setSpriteVelocity(0.2f);
		animationDamege.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		personagemCGTActor.getAnimarions().add(animationDamege);
		personagemCGTActor.getAnimarions().add(moveDown);
		personagemCGTActor.getAnimarions().add(moveLEft);
		personagemCGTActor.getAnimarions().add(moveRight);
		personagemCGTActor.getAnimarions().add(moveUp);
	}

	public void configuracaoCasasCenario(){
		for (int y =0; y < 3; y++) {
			int x;
			for (x = 0; x < 4; x++) {
				CGTOpposite opositorCasa = new CGTOpposite();



				Vector2 position = new Vector2(90 + x*260, 880 - y*250);
				opositorCasa.setPosition(position);
				Rectangle bounds = new Rectangle(0,0,178, 165);
				opositorCasa.setBounds(bounds);
				opositorCasa.setCollision(bounds);

				opositorCasa.setBlock(true);
				opositorCasa.setDestroyable(false);
				opositorCasa.setLife(0);

				opositorCasa.setSpriteSheet(new CGTSpriteSheet("data/dapexe/casa"+y+x+"-corte.png"));
				opositorCasa.getSpriteSheet().setRows(1);
				opositorCasa.getSpriteSheet().setColumns(1);


				CGTAnimation teste = new CGTAnimation(opositorCasa);
				teste.setSpriteLine(1);
				teste.addStatePolicy(StatePolicy.IDLEDOWN);
				teste.setAnimationPolicy(PlayMode.LOOP);
				opositorCasa.getAnimarions().add(teste);


				world.getOpposites().add(opositorCasa);	
			}
			x=0;
		}

		// ajuste da posicao do lago na tela
		world.getOpposites().get(6).setSpriteSheet(new CGTSpriteSheet("data/dapexe/casa233-corte.png"));
		world.getOpposites().get(6).setPosition(new Vector2(590,620));
		world.getOpposites().get(6).setBounds(new Rectangle(0,0,204,188 ));
		world.getOpposites().get(6).setCollision(new Rectangle(0,0,204,188 ));

		// ajuste das colisões das casas
		world.getOpposites().get(0).setCollision(new Rectangle(23,0,125,140));
		world.getOpposites().get(1).setCollision(new Rectangle(30,0,125,140));
		world.getOpposites().get(2).setCollision(new Rectangle(0,15,178,130));
		world.getOpposites().get(4).setCollision(new Rectangle(0,20,178,120));
		world.getOpposites().get(5).setCollision(new Rectangle(0,20,178,120));
		world.getOpposites().get(7).setCollision(new Rectangle(0,0,178,140));
		world.getOpposites().get(9).setCollision(new Rectangle(0,0,178,140));
		world.getOpposites().get(10).setCollision(new Rectangle(23,0,125,140));
		world.getOpposites().get(11).setCollision(new Rectangle(0,20,178,120));
	}

	public void configuracaoMar(){
		CGTOpposite mar = new CGTOpposite();
		mar.setPosition(new Vector2(0, 0));
		Rectangle bounds = new Rectangle(0,0,1200, 240);
		mar.setBounds(bounds);
		mar.setCollision(bounds);

		mar.setSpriteSheet(new CGTSpriteSheet("data/dapexe/mar2.png"));

		mar.getSpriteSheet().setRows(10);
		mar.getSpriteSheet().setColumns(5);

		CGTSound marSound = new CGTSound("data/AudioDaPexe/mar.wav");
		mar.setSound(marSound);


		CGTAnimation marAnimation= new CGTAnimation(mar);
		marAnimation.setSpriteVelocity(0.08f);
		marAnimation.setInitialFrame(new Vector2(0,0));
		marAnimation.setEndingFrame(new Vector2(4,9));
		marAnimation.addStatePolicy(StatePolicy.IDLEDOWN);
		marAnimation.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		mar.getAnimarions().add(marAnimation);
		world.addOpposite(mar);
	}

	public void configuracaoCarros(){
		// inicializando o carro no cenario	

		Direction direction = new Direction(DirectionPolicy.LEFT_AND_RIGHT);
		direction.setMaxX(700);
		direction.setMinX(20);

		Direction directionUp = new Direction(DirectionPolicy.UP_AND_DOWN);
		directionUp.setMaxY(900);
		directionUp.setMinY(200);

		Direction directionFour = new Direction(DirectionPolicy.FOUR_DIRECTION);
		directionFour.setMaxY(600);
		directionFour.setMinY(400);
		directionFour.setMaxX(1600);
		directionFour.setMinX(1130);
		CGTEnemy carroCGT = new CGTEnemy();

		Vector2 positionCarro = new Vector2(780,600);
		carroCGT.setPosition(positionCarro);

		Rectangle coliderCarro = new Rectangle(22,0,60, 94);
		carroCGT.setCollision(coliderCarro);

		Rectangle tamanhoCarro = new Rectangle(0,0,98, 90);
		carroCGT.setBounds(tamanhoCarro);

		carroCGT.setBlock(true);
		carroCGT.setDestroyable(false);
		carroCGT.setDamage(10);
		carroCGT.addBehavior(directionUp);

		carroCGT.setSpeed(200);

		carroCGT.setSpriteSheet(new CGTSpriteSheet("data/dapexe/SpriteSheet_carro_jeep.png"));
		carroCGT.getSpriteSheet().setRows(3);
		carroCGT.getSpriteSheet().setColumns(2);

		CGTSound soundCar = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.5f);
		carroCGT.setSound(soundCar);


		//Action
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

		Vector2 positionCarro2 = new Vector2(600,560);
		carroCGT2.setPosition(positionCarro2);

		Rectangle coliderCarro2 = new Rectangle(0,0,90, 80);
		carroCGT2.setCollision(coliderCarro2);

		Rectangle tamanhoCarro2 = new Rectangle(0,0,98, 90);
		carroCGT2.setBounds(tamanhoCarro2);

		carroCGT2.setBlock(true);
		carroCGT2.setDestroyable(false);
		carroCGT2.setDamage(10);
		carroCGT2.addBehavior(direction);

		carroCGT2.setSpeed(200);

		carroCGT2.setSpriteSheet(new CGTSpriteSheet("data/dapexe/SpriteSheet_carro_jeep.png"));
		carroCGT2.getSpriteSheet().setRows(3);
		carroCGT2.getSpriteSheet().setColumns(2);

		CGTSound soundCar2 = new CGTSound("data/AudioDaPexe/carro_1.wav", 0.5f);
		carroCGT2.setSound(soundCar2);

		//Action

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

		//Add na lista de enemy
		world.getEnemies().add(carroCGT2);

	}

	public void configuracaoPeixes(){
		Fade fade1 = new Fade(FadePolicy.FADE_IN);
		fade1.setFadeInTime(0);

		Fade fade5 = new Fade(FadePolicy.FADE_IN);
		fade5.setFadeInTime(5);

		Sine sine = new Sine(MovementPolicy.HEIGHT);
		sine.setMax(187);
		sine.setMin(177);
		sine.setAtFirstStep(true);

		CGTEnemy alertaPeixe = new CGTEnemy();

		Vector2 positionEnemy = world.getOpposites().get(1).getPosition();
		alertaPeixe.setPosition(positionEnemy);

		Rectangle coliderEnemy = new Rectangle(0, 0, 269, 177);
		alertaPeixe.setCollision(coliderEnemy);

		Rectangle tamanhoEnemy = new Rectangle(0, 0, 269, 177);
		alertaPeixe.setBounds(tamanhoEnemy);


		alertaPeixe.setState(StatePolicy.IDLEDOWN);
		alertaPeixe.setBlock(false);
		alertaPeixe.setDamage(0);
		alertaPeixe.setSpeed(1);
		alertaPeixe.setDestroyable(true);
		//alertaPeixe.addBehavior(fade1);
		alertaPeixe.addBehavior(sine);
		alertaPeixe.setLife(50);

		alertaPeixe.setSpriteSheet(new CGTSpriteSheet("data/dapexe/alert_peixe.png"));
		alertaPeixe.getSpriteSheet().setRows(1);
		alertaPeixe.getSpriteSheet().setColumns(1);
		CGTSound somPexeDie = new CGTSound("data/AudioDaPexe/caixa_registradora.wav");
		alertaPeixe.setSoundDie(somPexeDie);


		//Action
		CGTAnimation moveEnemy = new CGTAnimation(alertaPeixe);
		moveEnemy.setSpriteLine(1);
		moveEnemy.addStatePolicy(StatePolicy.IDLEDOWN);

		moveEnemy.setSpriteVelocity(0.08f);
		moveEnemy.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		alertaPeixe.getAnimarions().add(moveEnemy);
		world.getEnemies().add(alertaPeixe);

		CGTEnemy alertaPeixe2 = new CGTEnemy();

		Vector2 positionEnemy2 = world.getOpposites().get(3).getPosition();
		alertaPeixe2.setPosition(positionEnemy2);

		Rectangle coliderEnemy2 = new Rectangle(0, 0, 269, 177);
		alertaPeixe2.setCollision(coliderEnemy2);

		Rectangle tamanhoEnemy2 = new Rectangle(0,0,269, 177);
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

		alertaPeixe2.setSpriteSheet(new CGTSpriteSheet("data/dapexe/alert_peixe.png"));
		alertaPeixe2.getSpriteSheet().setRows(1);
		alertaPeixe2.getSpriteSheet().setColumns(1);


		CGTSound somPexeDie2 = new CGTSound("data/AudioDaPexe/caixa_registradora.wav");
		alertaPeixe2.setSoundDie(somPexeDie2);


		//Action
		CGTAnimation moveEnemy2 = new CGTAnimation(alertaPeixe2);
		moveEnemy2.setSpriteLine(1);
		moveEnemy2.addStatePolicy(StatePolicy.IDLEDOWN);

		moveEnemy2.setSpriteVelocity(0.08f);
		moveEnemy2.setAnimationPolicy(PlayMode.LOOP_PINGPONG);

		alertaPeixe2.getAnimarions().add(moveEnemy2);
		world.getEnemies().add(alertaPeixe2);

		EnemyGroupLifeBar alerts = new EnemyGroupLifeBar(world.getEnemies());
		Texture lifeBar = new Texture(Gdx.files.internal("data/lifeBar/lifeBar.png"));
		Texture lifeBarBack = new Texture(Gdx.files.internal("data/lifeBar/enemyLifeBarBack.png"));
		alerts.setBar(lifeBar);
		alerts.setOffsetX(0.25f);
		alerts.setBackgroundBar(lifeBarBack);
		alerts.setRelativeX(0.78f);
		alerts.setRelativeY(0.94f);
		alerts.setRelativeHeight(0.05f);
		alerts.setRelativeWidth(0.2f);
		world.addLifeBar(alerts);
	}

	public void configuracaoJangada(){
		CGTBonus jangada = new CGTBonus();

		jangada.setPosition(new Vector2(920, 180));
		jangada.setBounds(new Rectangle(0,0,255,224));
		jangada.setCollision(new Rectangle(0,0,255,80));
		jangada.addPolicy(BonusPolicy.ADD_AMMO);
		jangada.setScore(4);


		jangada.setSpriteSheet(new CGTSpriteSheet("data/dapexe/jangada-corte.png"));
		CGTAnimation animacaoJangada = new CGTAnimation(jangada);
		animacaoJangada.setSpriteLine(1);

		CGTSound somCollisionJangada = new CGTSound("data/AudioDaPexe/splash.wav");
		jangada.setSoundCollision(somCollisionJangada);


		jangada.getAnimarions().add(animacaoJangada);
		world.getBonus().add(jangada);
	}

	public void configuracaoProjetil(CGTActor personagemCGTActor){
		CGTProjectile projetilPeixe = new CGTProjectile();

		Vector2 position = new Vector2(100f,200f);
		projetilPeixe.setPosition(position);

		projetilPeixe.setBounds(new Rectangle(0,0,30, 30));
		Rectangle coliderProjectile = new Rectangle(0,0,30, 30);

		projetilPeixe.setCollision(coliderProjectile);
		projetilPeixe.setInterval(1);

		CGTSpriteSheet css = new CGTSpriteSheet(("data/dapexe/peixe_entrega.png"));
		css.setRows(1);
		css.setColumns(2);
		projetilPeixe.setSpriteSheet(css);
		projetilPeixe.setMaxAmmo(4);
		projetilPeixe.setAmmo(4);

		//Action dos projectiles
		CGTAnimation m = new CGTAnimation(projetilPeixe);
		m.setSpriteLine(1);
		m.setFlipHorizontal(true);
		m.addStatePolicy(StatePolicy.LOOKLEFT);
		m.addStatePolicy(StatePolicy.IDLELEFT);		
		m.setSpriteVelocity(1f);
		m.setAnimationPolicy(PlayMode.LOOP);

		CGTAnimation a = new CGTAnimation(projetilPeixe);
		a.setSpriteLine(1);
		a.setFlipHorizontal(false);
		a.addStatePolicy(StatePolicy.LOOKRIGHT);
		a.addStatePolicy(StatePolicy.IDLERIGHT);	
		a.setSpriteVelocity(1);
		a.setAnimationPolicy(PlayMode.LOOP);

		CGTAnimation down = new CGTAnimation(projetilPeixe);
		down.setSpriteLine(1);
		down.setFlipHorizontal(false);
		down.addStatePolicy(StatePolicy.LOOKDOWN);
		down.addStatePolicy(StatePolicy.IDLEDOWN);	
		down.setSpriteVelocity(1f);
		down.setAnimationPolicy(PlayMode.LOOP);

		CGTAnimation up = new CGTAnimation(projetilPeixe);
		up.setSpriteLine(1);
		up.setFlipHorizontal(true);
		up.addStatePolicy(StatePolicy.LOOKUP);
		up.addStatePolicy(StatePolicy.IDLEUP);
		up.setSpriteVelocity(1f);
		up.setAnimationPolicy(PlayMode.LOOP);
		projetilPeixe.getAnimarions().add(up);
		projetilPeixe.getAnimarions().add(down);
		projetilPeixe.getAnimarions().add(a);
		projetilPeixe.getAnimarions().add(m);


		//Projectile orientation
		ProjectileOrientation direcaoRight = new ProjectileOrientation();
		direcaoRight.setPositionRelativeToGameObject(new Vector2(60f,15f));		
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
		world.setActor(personagemCGTActor);
	}

	public void configuracaoButtonPad(){
		CGTButton buttonPad = new CGTButton();

		Texture textureUp = new Texture("data/buttons/base.png");
		buttonPad.setTextureUp(textureUp);
		Texture textureDown = new Texture("data/buttons/base.png");
		buttonPad.setTextureDown(textureDown);

		buttonPad.setRelativeX(0);
		buttonPad.setRelativeY(0);
		buttonPad.setRelativeWidth(0.29f);
		buttonPad.setRelativeHeight(0.29f);
		buttonPad.setBounds(0, 0, textureUp.getWidth()/3, textureUp.getHeight()/3);

		CGTButton button = new CGTButton();
		button.setInput(InputPolicy.BTN_UP);

		textureUp = new Texture("data/buttons/bt_up_up.png");
		button.setTextureUp(textureUp);

		textureDown = new Texture("data/buttons/bt_up_press.png");
		button.setTextureDown(textureDown);

		button.setRelativeX(0.095f);
		button.setRelativeY(0.145f);
		button.setRelativeWidth(0.1f);
		button.setRelativeHeight(0.1f);
		button.setBounds(137/3, 184.7f/3, textureUp.getWidth()/3, textureUp.getHeight()/3);


		CGTButton buttonDown = new CGTButton();
		buttonDown.setInput(InputPolicy.BTN_DOWN);

		textureUp = new Texture("data/buttons/bt_down_up.png");
		buttonDown.setTextureUp(textureUp);
		textureDown = new Texture("data/buttons/bt_down_press.png");
		buttonDown.setTextureDown(textureDown);

		buttonDown.setRelativeX(0.095f);
		buttonDown.setRelativeY(0.028f);
		buttonDown.setRelativeWidth(0.1f);
		buttonDown.setRelativeHeight(0.1f);
		buttonDown.setBounds(137/3, 36/3, textureUp.getWidth()/3, textureUp.getHeight()/3);


		CGTButton buttonLeft = new CGTButton();
		buttonLeft.setInput(InputPolicy.BTN_LEFT);

		textureUp = new Texture("data/buttons/bt_left_up.png");
		buttonLeft.setTextureUp(textureUp);

		textureDown = new Texture("data/buttons/bt_left_press.png");
		buttonLeft.setTextureDown(textureDown);

		buttonLeft.setRelativeX(0.048f);
		buttonLeft.setRelativeY(0.09f);
		buttonLeft.setRelativeWidth(0.1f);
		buttonLeft.setRelativeHeight(0.1f);
		buttonLeft.setBounds(64/3, 126/3, textureUp.getWidth()/3, textureUp.getHeight()/3);


		CGTButton buttonRight = new CGTButton();
		buttonRight.setInput(InputPolicy.BTN_RIGHT);

		textureUp = new Texture("data/buttons/bt_right_up.png");
		buttonRight.setTextureUp(textureUp);
		textureDown = new Texture("data/buttons/bt_right_press.png");
		buttonRight.setTextureDown(textureDown);

		buttonRight.setRelativeX(0.142f);
		buttonRight.setRelativeY(0.09f);
		buttonRight.setRelativeWidth(0.1f);
		buttonRight.setRelativeHeight(0.1f);
		buttonRight.setBounds(183/3, 126/3, textureUp.getWidth()/3, textureUp.getHeight()/3);

		CGTButton button1 = new CGTButton();
		button1.setInput(InputPolicy.BTN_1);

		textureUp = new Texture("data/buttons/bt_agua_up.png");
		button1.setTextureUp(textureUp);
		textureDown = new Texture("data/buttons/bt_agua_down.png");
		button1.setTextureDown(textureDown);

		button1.setRelativeX(0.9f);
		button1.setRelativeY(0.1f);
		button1.setRelativeWidth(0.1f);
		button1.setRelativeHeight(0.1f);
		button1.setBounds(0, 0, textureUp.getWidth()/2, textureUp.getHeight()/2);

		world.addButton(buttonPad);
		world.addButton(button);
		world.addButton(buttonDown);
		world.addButton(buttonLeft);
		world.addButton(buttonRight);
		world.addButton(button1);
	}
	
	private void configuraTimer(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 100;
		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		LabelStyle style = new LabelStyle(font12, Color.BLACK);
		CGTLabel label = new CGTLabel("x", style);
		label.setRelativeX(0.5f);
		label.setRelativeY(0.95f);


		world.addLoseCriterion(new TargetTime(20, label));
		world.addLoseCriterion(new LifeDepleted(world.getActor()));
		world.addWinCriterion(new KillAllEnemies(world.getEnemies()));

	}





	/**
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {

		world = new CGTGameWorld();		
		backGround = new Texture(Gdx.files.internal("data/dapexe/casas_ceara_cenario.png"));
		world.setBackground(backGround);

		// instancias criada no método principal pois é compartilhada por mais de um objeto

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

		configuracaoButtonPad();
		
		configuraTimer();
		

		CGTTexture t = new CGTTexture("data/dapexe/menuInicial.png");
		CGTButtonScreen btn = new CGTButtonScreen();
		btn.setRelativeX(0.33f);
		btn.setRelativeY(0.5f);
		btn.setRelativeWidth(0.25f);
		btn.setRelativeHeight(0.3f);
		Texture texture = new Texture("data/dapexe/iniciar.png");
		btn.setTextureDown(texture);
		btn.setTextureUp(texture);
		btn.setBounds(0, 0, texture.getWidth(), texture.getHeight());
		//btn.setScreenToGo(new MyWorld().getCGT());
		btn.setScreenToGo(world);
		//		CGTButton btn = new CGTButton("data/menu/_gui.png");
		//		btn.setBounds(new Rectangle(400, 400, 200, 100));
		screen = new CGTScreen(t);
		screen.getButtons().add(btn);

		game = new CGTGame();
		game.setMenu(screen);
		configuracaoPauseDialog();
		configuracaoWinDialog();
	}

	public CGTGameWorld getWorld() {
		return world;
	}

	public CGTGame getGame() {
		return game;
	}

}
