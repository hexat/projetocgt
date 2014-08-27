package com.projetocgt.cenario;


import cgt.CGTGameWorld;
import cgt.behaviors.*;
import cgt.core.*;
import cgt.lose.LifeDepleted;
import cgt.policy.*;
import cgt.util.*;
import cgt.win.KillAllEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Responsavel por construir o jogo
 * @author Bruno Roberto
 *
 */
public class MyWorldPexe {

//	ArrayList<Action> listaDeAction = new ArrayList<Action>();
//	private CGTActor personagemActorLIB;
	private Texture backGround;
	//private Texture lifeBar;
	//private Texture lifeBarCGTEnemy;
//	private WinPolicy winPolicy;
//	private LosePolicy losePolicy;
//	private int countdown;
//	private int scoreTarget;

	private CGTGameWorld world;
	
	public MyWorldPexe() {
		createWorld();
	}

	public CGTGameWorld getCGT() {
		return world;
	}
	/**
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		world = new CGTGameWorld();
		
		//world = new CGTGameWorld();
		backGround = new Texture(Gdx.files.internal("data/dapexe/casas_ceara_cenario.png"));
		world.setBackground(backGround);
		

		Texture lifeBar = new Texture(Gdx.files.internal("data/lifeBar/lifeBar.png"));
		Texture lifeBarBack = new Texture(Gdx.files.internal("data/lifeBar/lifeBarBack.png"));
		LifeBar actorLifeBar = new LifeBar();
		actorLifeBar.setBar(lifeBar);
		actorLifeBar.setBackgroundBar(lifeBarBack);
		actorLifeBar.setRelativeX(0.1f);
		actorLifeBar.setRelativeY(0.9f);
		actorLifeBar.setRelativeHeight(0.1f);
		actorLifeBar.setRelativeWidth(0.25f);
		
		//world.setPosRelativaLifeBarX(-500);
//		world.setPosRelativaLifeBarY(300);
//		world.setLifeBar(lifeBar);
		
		//Texture lifeBarCGTEnemy = new Texture(Gdx.files.internal("data/lifeBar/lifeBar.png"));
//		world.setPosRelativaLifeBarCGTEnemyX(500);
//		world.setPosRelativaLifeBarCGTEnemyY(300);
//		world.setLifeBarCGTEnemy(lifeBarCGTEnemy);
//		world.setNumDeCGTEnemyDetroyble(2);

		
		
		CGTActor personagemCGTActor = new CGTActor();
		personagemCGTActor.setFireDefault(-1);
		personagemCGTActor.setPosition(new Vector2(800f,900f));

		personagemCGTActor.setCollision(new Rectangle(10, 10, 40, 45));

		Rectangle tamanhoPersonagem = new Rectangle(0, 0, 60, 60);
		personagemCGTActor.setBounds(tamanhoPersonagem);

		personagemCGTActor.setLife(3);
		actorLifeBar.setMaxLife(3);
		actorLifeBar.setOwner(personagemCGTActor);
		world.addLifeBar(actorLifeBar);
		personagemCGTActor.setSpeed(280);

		personagemCGTActor.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/sprite_garoto.png").file()));
		personagemCGTActor.getSpriteSheet().setRows(5);
		personagemCGTActor.getSpriteSheet().setColumns(3);


		Music somDamegePersonagem =  Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/colisao.wav"));
		personagemCGTActor.setSoundCollision(somDamegePersonagem);

		Music somDiePersonagem;

		somDiePersonagem = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/colisao.wav"));
		personagemCGTActor.setSoundDie(somDiePersonagem);


		//Action
		CGTAnimation moveLEft = new CGTAnimation(personagemCGTActor);
		moveLEft.setSpriteLine(2);
		moveLEft.addStatePolicy(StatePolicy.LOOKLEFT);
		
		//moveLEft.addInput(InputPolicy.ACEL_LEFT);
		moveLEft.setSpriteVelocity(0.2f);
		moveLEft.setFlip(true);
		moveLEft.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);

		CGTAnimation moveRight = new CGTAnimation(personagemCGTActor);
		moveRight.setSpriteLine(2);
		moveRight.addStatePolicy(StatePolicy.LOOKRIGHT);
		moveRight.setFlip(false);
		//moveRight.addInput(InputPolicy.ACEL_RIGHT);
		moveRight.setSpriteVelocity(0.2f);
		moveRight.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);

		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor);
		moveUp.setSpriteLine(1);
		moveUp.addStatePolicy(StatePolicy.LOOKUP);
		
		//moveUp.addInput(InputPolicy.ACEL_UP);
		moveUp.setSpriteVelocity(0.2f);
		moveUp.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);

		CGTAnimation moveDown = new CGTAnimation(personagemCGTActor);
		moveDown.setSpriteLine(3);
		moveDown.addStatePolicy(StatePolicy.LOOKDOWN);
		
		//moveDown.addInput(InputPolicy.ACEL_DOWN);
		moveDown.setSpriteVelocity(0.2f);
		moveDown.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		CGTAnimation animationDamege = new CGTAnimation(personagemCGTActor);
		animationDamege.setSpriteLine(5);
		animationDamege.addStatePolicy(StatePolicy.DAMAGE);
		
		//moveDown.addInput(InputPolicy.ACEL_DOWN);
		animationDamege.setSpriteVelocity(0.2f);
		animationDamege.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);

		personagemCGTActor.getAnimarions().add(animationDamege);
		personagemCGTActor.getAnimarions().add(moveDown);
		personagemCGTActor.getAnimarions().add(moveLEft);
		personagemCGTActor.getAnimarions().add(moveRight);
		personagemCGTActor.getAnimarions().add(moveUp);
		
		
		//Adicionando o personagem na libGDX
		//personagemActorLIB = new ActorCGT(personagemCGTActor);

		//-------------------------------------------------------------------------------//
		//		personagemActorLIB = new CGTActor(new Vector2(800,900), 100f, 100f, 80f, 10f, 10f);
		//		personagemActorLIB.setSpeed(180);
		//		personagemActorLIB.setLife(3);
		//		CGTAnimation spriteSheetActor = new CGTAnimation();
		//		personagemActorLIB.setSpriteSheet(spriteSheetActor );
		//		//Adicionando o audio de colisao
		//		Music soundDamage = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/colisao.wav"));
		//		personagemActorLIB.setSoundDamage(soundDamage);
		//		
		//		Music soundDie = Gdx.audio.newMusic(Gdx.files.internal("data/AudioBombeiro/gameOver.wav"));
		//		personagemActorLIB.setSoundDie(soundDie);
		//		
		//		spriteSheetActor.setLinhaDoSpriteUp(3);
		//		spriteSheetActor.setLinhaDoSpriteDown(2);
		//		spriteSheetActor.setLinhaDoSpriteLeft(1);
		//		spriteSheetActor.setLinhaDoSpriteRight(1);
		//		spriteSheetActor.setLinhaDoSpriteDamege(5);
		//		spriteSheetActor.loadSpriteActorCGT("data/SpriteCGTActor/SpriteSheet_bombeiro.png",5,3);


		/* Esse opposite nao tem animacao, seria melhor adicionar uma textura do que uma animacao 
		 * um por um.
		 */
		
		// esse for serve para iniciar as casas no cenario
		for (int y =0; y < 3; y++) {
			int x;
			for (x = 0; x < 4; x++) {
				CGTOpposite opositorCasa = new CGTOpposite();
				
								
				
				Vector2 position = new Vector2(90 + x*260, 880 - y*250);
				opositorCasa.setPosition(position);
				Rectangle bounds = new Rectangle(0,0,178, 165);
				opositorCasa.setBounds(bounds);
				opositorCasa.setCollision(bounds);

				//Texture texture = new CGTTexture("data/Cenario/casa_sprite_sheet.png");
				opositorCasa.setBlock(true);
				opositorCasa.setDestroyable(false);
				opositorCasa.setLife(0);

				opositorCasa.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/casa"+y+x+"-corte.png").file()));
				opositorCasa.getSpriteSheet().setRows(1);
				opositorCasa.getSpriteSheet().setColumns(1);
				

				CGTAnimation teste = new CGTAnimation(opositorCasa);
				teste.setSpriteLine(1);
				teste.addStatePolicy(StatePolicy.IDLEDOWN);
				teste.setAnimationPolicy(AnimationPolicy.LOOP);
				opositorCasa.getAnimarions().add(teste);
				
				//Indica que a minha animacao e' um por um
				//opositorCasa.getSpriteSheet().loadingSpriteSheet("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png", 1, 1);
				//Opposite opositorCasaLib = new Opposite(opositorCasa);
				world.getOpposites().add(opositorCasa);				
			}
			x=0;
		}
		
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
		
		// ajuste da posicao do lago na tela
		world.getOpposites().get(6).setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/casa233-corte.png").file()));
		world.getOpposites().get(6).setPosition(new Vector2(590,620));
		world.getOpposites().get(6).setBounds(new Rectangle(0,0,204,188 ));
		world.getOpposites().get(6).setCollision(new Rectangle(0,0,204,188 ));
		
		CGTOpposite mar = new CGTOpposite();
		mar.setPosition(new Vector2(0, 0));
		Rectangle bounds = new Rectangle(0,0,1210, 422);
		mar.setBounds(bounds);
		mar.setCollision(bounds);
		
		mar.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/Spritesheet_mar-corte.png").file()));
		mar.getSpriteSheet().setRows(10);
		mar.getSpriteSheet().setColumns(5);
		
		CGTAnimation marAnimation= new CGTAnimation(mar);
		marAnimation.setSpriteVelocity(0.08f);
		marAnimation.setInitialFrame(new Vector2(0,0));
		marAnimation.setEndingFrame(new Vector2(4,9));
		marAnimation.addStatePolicy(StatePolicy.IDLEDOWN);
		marAnimation.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		mar.getAnimarions().add(marAnimation);
		world.addOpposite(mar);
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
		alertaPeixe.addBehavior(fade1);
		alertaPeixe.addBehavior(sine);
		alertaPeixe.setLife(50);
		alertaPeixe.setInterval(4);

		alertaPeixe.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/alert_peixe.png").file()));
		alertaPeixe.getSpriteSheet().setRows(1);
		alertaPeixe.getSpriteSheet().setColumns(1);


		//Action
		CGTAnimation moveEnemy = new CGTAnimation(alertaPeixe);
		moveEnemy.setSpriteLine(1);
		moveEnemy.addStatePolicy(StatePolicy.IDLEDOWN);
		
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveEnemy.setSpriteVelocity(0.08f);
		moveEnemy.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);

		alertaPeixe.getAnimarions().add(moveEnemy);
		world.getEnemies().add(alertaPeixe);
		
		CGTEnemy alertaPeixe2 = new CGTEnemy();

		Vector2 positionEnemy2 = world.getOpposites().get(8).getPosition();
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
		alertaPeixe2.setInterval(4);

		alertaPeixe2.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/alert_peixe.png").file()));
		alertaPeixe2.getSpriteSheet().setRows(1);
		alertaPeixe2.getSpriteSheet().setColumns(1);


		//Action
		CGTAnimation moveEnemy2 = new CGTAnimation(alertaPeixe2);
		moveEnemy2.setSpriteLine(1);
		moveEnemy2.addStatePolicy(StatePolicy.IDLEDOWN);
		
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveEnemy2.setSpriteVelocity(0.08f);
		moveEnemy2.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);

		alertaPeixe2.getAnimarions().add(moveEnemy2);
		world.getEnemies().add(alertaPeixe2);

		//		//Instancia o opposite fogo
		//		Enemy enemyFogo = new Enemy(new Vector2(400,850), 50, 50, 50, 0, 0);
		//		enemyFogo.setBlock(false);
		//		enemyFogo.setDamage(1);
		//		enemyFogo.setSpeed(2);
		//		enemyFogo.setDestroyable(true);
		//		enemyFogo.addBehavior(fade);
		//		enemyFogo.addBehavior(sine);
		//		enemyFogo.setLife(50);
		//		enemyFogo.setSpriteSheet(new CGTAnimation());
		//		enemyFogo.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		//		listaDeEnemy.add(enemyFogo);
		//		
		//		Fade fade2 = new Fade(FadePolicy.FADE_IN);
		//		fade2.setFadeInTime(1);
		//		
		//		//Instancia o opposite fogo
		//		Enemy enemyFogo2 = new Enemy(new Vector2(200,1050), 50, 50, 50, 0, 0);
		//		enemyFogo2.setBlock(true);
		//		enemyFogo2.setDestroyable(true);
		//		enemyFogo2.setDamage(1);
		//		enemyFogo2.addBehavior(fade2);
		//		enemyFogo2.setLife(200);
		//		enemyFogo2.setSpriteSheet(new CGTAnimation());
		////		enemyFogo2.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		//		listaDeEnemy.add(enemyFogo2);
		//		
		//		Fade fade3 = new Fade(FadePolicy.FADE_IN);
		//		fade3.setFadeInTime(1);
		//		
		//		//Instancia o opposite fogo
		//		Enemy enemyFogo3 = new Enemy(new Vector2(200,1500), 50, 50, 50, 0, 0);
		//		enemyFogo3.setBlock(true);
		//		enemyFogo3.setDestroyable(true);
		//		enemyFogo3.setDamage(2);
		//		enemyFogo3.setLife(100);
		//		enemyFogo3.addBehavior(fade3);
		//		enemyFogo3.setSpriteSheet(new CGTAnimation());
		////		enemyFogo3.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		//		listaDeEnemy.add(enemyFogo3);
		//		
		Direction direction = new Direction(DirectionPolicy.LEFT_AND_RIGHT);
		direction.setMaxX(700);
		direction.setMinX(20);
		//		
		Direction directionUp = new Direction(DirectionPolicy.UP_AND_DOWN);
		directionUp.setMaxY(900);
		directionUp.setMinY(200);
		//		
		//
		Direction directionFour = new Direction(DirectionPolicy.FOUR_DIRECTION);
		directionFour.setMaxY(600);
		directionFour.setMinY(400);
		directionFour.setMaxX(1600);
		directionFour.setMinX(1130);
		//
		//
		//		Direction directionEight = new Direction(DirectionPolicy.EIGHT_DIRECTION);
		//		directionEight.setMaxY(600);
		//		directionEight.setMinY(400);
		//		directionEight.setMaxX(800);
		//		directionEight.setMinX(330);
		//		
		//		
		Fade fadeCar = new Fade(FadePolicy.FADE_IN);
		fadeCar.setFadeInTime(0);
		
		// inicializando o carro no cen�rio		
		CGTEnemy carroCGT = new CGTEnemy();

		Vector2 positionCarro = new Vector2(780,600);
		carroCGT.setPosition(positionCarro);

		Rectangle coliderCarro = new Rectangle(22,0,60, 94);
		carroCGT.setCollision(coliderCarro);

		Rectangle tamanhoCarro = new Rectangle(0,0,98, 90);
		carroCGT.setBounds(tamanhoCarro);

		carroCGT.setInterval(3);
		carroCGT.setBlock(true);
		carroCGT.setDestroyable(false);
		carroCGT.setDamage(10);
		carroCGT.addBehavior(fadeCar);
		carroCGT.addBehavior(directionUp);

		carroCGT.setSpeed(200);

		carroCGT.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/SpriteSheet_carro_jeep.png").file()));
		carroCGT.getSpriteSheet().setRows(3);
		carroCGT.getSpriteSheet().setColumns(2);
		
		//Action
		/*
		CGTAnimation moveCarro = new CGTAnimation(carroCGT);
		moveCarro.setSpriteLine(1);
		moveCarro.addStatePolicy(StatePolicy.LOOKRIGHT);
<<<<<<< HEAD
		moveCarro.setNumberOfColumns(2);
=======
		
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
>>>>>>> e1b8bce29cead427cdf4703326a50bb9844ab4db
		moveCarro.setSpriteVelocity(0.08f);
		moveCarro.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT.getAnimarions().add(moveCarro);

		CGTAnimation moveCarroLeft = new CGTAnimation(carroCGT);
		moveCarroLeft.setFlip(true);
		moveCarroLeft.setSpriteLine(1);
		moveCarroLeft.addStatePolicy(StatePolicy.LOOKLEFT);
<<<<<<< HEAD
		moveCarroLeft.setNumberOfColumns(2);
=======
		
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
>>>>>>> e1b8bce29cead427cdf4703326a50bb9844ab4db
		moveCarroLeft.setSpriteVelocity(0.08f);
		moveCarroLeft.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT.getAnimarions().add(moveCarroLeft);
		*/

		CGTAnimation moveCarroDown = new CGTAnimation(carroCGT);
		moveCarroDown.setSpriteLine(2);
		moveCarroDown.addStatePolicy(StatePolicy.LOOKDOWN);
		
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveCarroDown.setSpriteVelocity(0.08f);
		moveCarroDown.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT.getAnimarions().add(moveCarroDown);

		CGTAnimation moveCarroUp = new CGTAnimation(carroCGT);
		moveCarroUp.setSpriteLine(3);
		moveCarroUp.addStatePolicy(StatePolicy.LOOKUP);
		
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveCarroUp.setSpriteVelocity(0.08f);
		moveCarroUp.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT.getAnimarions().add(moveCarroUp);

		//Enemy enemyCarroLIB = new Enemy(carroCGT);
		//enemyCarroLIB.getSpriteSheet().setLoop(true);
		//Add na lista de enemy
		world.getEnemies().add(carroCGT);
		
		Fade fadeCar1 = new Fade(FadePolicy.FADE_IN);
		fadeCar1.setFadeInTime(0);
		
		// inicializando o carro no cen�rio		
		CGTEnemy carroCGT2 = new CGTEnemy();

		Vector2 positionCarro2 = new Vector2(600,560);
		carroCGT2.setPosition(positionCarro2);

		Rectangle coliderCarro2 = new Rectangle(0,0,90, 80);
		carroCGT2.setCollision(coliderCarro2);

		Rectangle tamanhoCarro2 = new Rectangle(0,0,98, 90);
		carroCGT2.setBounds(tamanhoCarro2);

		carroCGT2.setInterval(3);
		carroCGT2.setBlock(true);
		carroCGT2.setDestroyable(false);
		carroCGT2.setDamage(10);
		carroCGT2.addBehavior(fadeCar1);
		carroCGT2.addBehavior(direction);

		carroCGT2.setSpeed(200);

		carroCGT2.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/SpriteSheet_carro_jeep.png").file()));
		carroCGT2.getSpriteSheet().setRows(3);
		carroCGT2.getSpriteSheet().setColumns(2);
		
		//Action
		
		CGTAnimation moveCarro = new CGTAnimation(carroCGT2);
		moveCarro.setSpriteLine(1);
		moveCarro.addStatePolicy(StatePolicy.LOOKRIGHT);
//		moveCarro.setNumberOfColumns(2);
		moveCarro.setSpriteVelocity(0.08f);
		moveCarro.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT2.getAnimarions().add(moveCarro);

		CGTAnimation moveCarroLeft = new CGTAnimation(carroCGT2);
		moveCarroLeft.setFlip(true);
		moveCarroLeft.setSpriteLine(1);
		moveCarroLeft.addStatePolicy(StatePolicy.LOOKLEFT);
//		moveCarroLeft.setNumberOfColumns(2);
		moveCarroLeft.setSpriteVelocity(0.08f);
		moveCarroLeft.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT2.getAnimarions().add(moveCarroLeft);
		
		/*
		CGTAnimation moveCarroDown = new CGTAnimation(carroCGT);
		moveCarroDown.setSpriteLine(2);
		moveCarroDown.addStatePolicy(StatePolicy.LOOKDOWN);
		moveCarroDown.setNumberOfColumns(2);
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveCarroDown.setSpriteVelocity(0.08f);
		moveCarroDown.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT.getAnimarions().add(moveCarroDown);

		CGTAnimation moveCarroUp = new CGTAnimation(carroCGT);
		moveCarroUp.setSpriteLine(3);
		moveCarroUp.addStatePolicy(StatePolicy.LOOKUP);
		moveCarroUp.setNumberOfColumns(2);
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveCarroUp.setSpriteVelocity(0.08f);
		moveCarroUp.setAnimationPolicy(AnimationPolicy.LOOP);
		carroCGT.getAnimarions().add(moveCarroUp);
		*/

		//Enemy enemyCarroLIB = new Enemy(carroCGT);
		//enemyCarroLIB.getSpriteSheet().setLoop(true);
		//Add na lista de enemy
		world.getEnemies().add(carroCGT2);
				

		//		//Instancia o opposite carro
		//		Enemy carro = new Enemy(new Vector2(800,700), 50, 50, 50, 0, 0);
		//		carro.setLife(10);
		//		carro.setBlock(true);
		//		carro.setDestroyable(false);
		//		carro.setDamage(10);
		//		carro.addBehavior(fadeCar);
		//		carro.addBehavior(directionUp);
		//		carro.setSpeed(200);
		//		//carro.setLife(200);
		//		carro.setSpriteSheet(new CGTAnimation());
		////		carro.getSpriteSheet().loadingSpriteSheet("data/Enemy/Carro.png", 1, 1);
		//		listaDeEnemy.add(carro);

		CGTBonus hidrate = new CGTBonus();

		hidrate.setPosition(new Vector2(920, 180));
		hidrate.setBounds(new Rectangle(0,0,255,224));
		hidrate.setCollision(new Rectangle(0,0,255,80));
	
		hidrate.setSpriteSheet(new CGTSpriteSheet(Gdx.files.internal("data/dapexe/jangada-corte.png").file()));
		CGTAnimation aniHidrante = new CGTAnimation(hidrate);
		aniHidrante.setSpriteLine(1);
		
		hidrate.getAnimarions().add(aniHidrante);
		//hidrate.setTexture(new Texture("data/CGTBonus/SpriteSheet_tubo.png"));
		world.getBonus().add(hidrate);

		CGTProjectile projetilPeixe = new CGTProjectile();
		
		Vector2 position = new Vector2(100f,200f);
		projetilPeixe.setPosition(position);
		
		projetilPeixe.setBounds(new Rectangle(0,0,30, 30));
		Rectangle coliderProjectile = new Rectangle(0,0,30, 30);
		
		//Vector2 positionRelative = new Vector2(0,0);
		projetilPeixe.setCollision(coliderProjectile);
		projetilPeixe.setInterval(1);

		CGTSpriteSheet css = new CGTSpriteSheet(Gdx.files.internal("data/dapexe/peixe_entrega.png").file());
		css.setRows(1);
		css.setColumns(2);
		projetilPeixe.setSpriteSheet(css);
		projetilPeixe.setAmmo(2);

		//Action dos projectiles
		CGTAnimation m = new CGTAnimation(projetilPeixe);
		m.setSpriteLine(1);
		m.setFlip(true);
		m.addStatePolicy(StatePolicy.LOOKLEFT);
		m.addStatePolicy(StatePolicy.IDLELEFT);
		
		m.setSpriteVelocity(1f);
		m.setAnimationPolicy(AnimationPolicy.LOOP);

		CGTAnimation a = new CGTAnimation(projetilPeixe);
		a.setSpriteLine(1);
		a.setFlip(false);
		a.addStatePolicy(StatePolicy.LOOKRIGHT);
		a.addStatePolicy(StatePolicy.IDLERIGHT);
		
		a.setSpriteVelocity(1);
		a.setAnimationPolicy(AnimationPolicy.LOOP);

		CGTAnimation down = new CGTAnimation(projetilPeixe);
		down.setSpriteLine(1);
		down.setFlip(false);
		down.addStatePolicy(StatePolicy.LOOKDOWN);
		down.addStatePolicy(StatePolicy.IDLEDOWN);
		
		down.setSpriteVelocity(1f);
		down.setAnimationPolicy(AnimationPolicy.LOOP);

		CGTAnimation up = new CGTAnimation(projetilPeixe);
		up.setSpriteLine(1);
		up.setFlip(true);
		up.addStatePolicy(StatePolicy.LOOKUP);
		up.addStatePolicy(StatePolicy.IDLEUP);
		up.setSpriteVelocity(1f);
		up.setAnimationPolicy(AnimationPolicy.LOOP);
		projetilPeixe.getAnimarions().add(up);
		projetilPeixe.getAnimarions().add(down);
		projetilPeixe.getAnimarions().add(a);
		projetilPeixe.getAnimarions().add(m);


		//Projectile orientation
		ProjectileOrientation direcaoRight = new ProjectileOrientation();
		direcaoRight.setPositionRelativeToGameObject(new Vector2(60f,15f));
		//direcaoRight.setSpriteNumberOfColumns(2);
		//direcaoRight.setSpriteLine(1);
		//direcaoRight.setSpriteVelocity(2);
		direcaoRight.addState(StatePolicy.LOOKRIGHT);
		direcaoRight.addState(StatePolicy.IDLERIGHT);
		projetilPeixe.getOrientations().add(direcaoRight);

		ProjectileOrientation direcaoLeft = new ProjectileOrientation();
		direcaoLeft.setPositionRelativeToGameObject(new Vector2(-13f, 15f));
		//direcaoLeft.setSpriteNumberOfColumns(2);
		//direcaoLeft.setSpriteLine(1);
		
		//direcaoLeft.setSpriteVelocity(2);
		direcaoLeft.addState(StatePolicy.LOOKLEFT);
		direcaoLeft.addState(StatePolicy.IDLELEFT);
		projetilPeixe.getOrientations().add(direcaoLeft);

		ProjectileOrientation direcaoUp = new ProjectileOrientation();
		direcaoUp.setPositionRelativeToGameObject(new Vector2(30f, 60f));
		//direcaoUp.setSpriteNumberOfColumns(2);
		//direcaoUp.setSpriteLine(1);
		
		//direcaoUp.setSpriteVelocity(2);
		direcaoUp.addState(StatePolicy.LOOKUP);
		direcaoUp.addState(StatePolicy.IDLEUP);
		projetilPeixe.getOrientations().add(direcaoUp);

		ProjectileOrientation direcaoDown = new ProjectileOrientation();
		direcaoDown.setPositionRelativeToGameObject(new Vector2(25f, -20f));
		//direcaoDown.setSpriteNumberOfColumns(2);
		//direcaoDown.setSpriteLine(1);
		
		//direcaoDown.setSpriteVelocity(2);
		direcaoDown.addState(StatePolicy.LOOKDOWN);
		direcaoDown.addState(StatePolicy.IDLEDOWN);
		projetilPeixe.getOrientations().add(direcaoDown);
		personagemCGTActor.getProjectiles().add(projetilPeixe);
		
		personagemCGTActor.addProjectile(projetilPeixe);
		world.setActor(personagemCGTActor);
		
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
		
		
		/*CGTAnimation moveButton = new CGTAnimation(button);
		moveButton.setSpriteLine(1);
		moveButton.addStatePolicy(StatePolicy.IDLEDOWN);
		moveButton.setNumberOfColumns(2);
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveButton.setSpriteVelocity(0.08f);
		moveButton.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		CGTAnimation moveButton2 = new CGTAnimation(button);
		moveButton2.setSpriteLine(2);
		moveButton2.addStatePolicy(StatePolicy.IDLEUP);
		moveButton2.setNumberOfColumns(2);
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveButton2.setSpriteVelocity(0.08f);
		moveButton2.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		button.getAnimarions().add(moveButton);
		button.getAnimarions().add(moveButton2);
		
		
		
		//button.draw(batch, parentAlpha)
		  */
		world.addButton(buttonPad);
		world.addButton(button);
		world.addButton(buttonDown);
		world.addButton(buttonLeft);
		world.addButton(buttonRight);
		world.addButton(button1);
		
		//world.addLoseCriterion(new TargetTime(5));
		world.addLoseCriterion(new LifeDepleted(world.getActor()));
		world.addWinCriterion(new KillAllEnemies(world.getEnemies()));
	}
	
}
