//package com.projetocgt.cenario;
//
//
//import java.application.util.ArrayList;
//
//import cgt.behaviors.Direction;
//import cgt.behaviors.Fade;
//import cgt.behaviors.Sine;
//import cgt.core.CGTActor;
//import cgt.core.CGTBonus;
//import cgt.core.CGTEnemy;
//import cgt.core.CGTOpposite;
//import cgt.core.CGTProjectile;
//import cgt.hud.CGTButton;
//import cgt.hud.CGTControllerButton;
//import cgt.hud.EnemyGroupLifeBar;
//import cgt.hud.IndividualLifeBar;
//import cgt.lose.LifeDepleted;
//import cgt.policy.DirectionPolicy;
//import cgt.policy.FadePolicy;
//import cgt.policy.InputPolicy;
//import cgt.policy.MovementPolicy;
//import cgt.policy.StatePolicy;
//import cgt.core.CGTAnimation;
//import cgt.application.util.CGTSound;
//import cgt.application.util.CGTTexture;
//import cgt.application.util.ProjectileOrientation;
//import cgt.win.KillAllEnemies;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//
///**
// * Responsavel por construir o jogo
// * @author Bruno Roberto
// *
// */
//public class MyWorld {
//
//	//	ArrayList<Action> listaDeAction = new ArrayList<Action>();
//	//	private CGTActor personagemActorLIB;
//	private CGTTexture backGround;
//	//private CGTTexture lifeBar;
//	//private CGTTexture lifeBarCGTEnemy;
//	//	private WinPolicy winPolicy;
//	//	private LosePolicy losePolicy;
//	//	private int countdown;
//	//	private int scoreTarget;
//
//	private CGTGameWorld world;
//
//	public MyWorld() {
//		createWorld();
//	}
//
//	public CGTGameWorld getCGT() {
//		return world;
//	}
//	/**
//	 * Recebe os paramentros do jogos
//	 */
//	private void createWorld() {
//		world = new CGTGameWorld();
//
//		//world = new CGTGameWorld();
//		backGround = new CGTTexture("data/Cenario/asfalto_grama_sprite_sheet.png");
//		world.setBackground(backGround);
//
//		
//
//		//world.setPosRelativaLifeBarX(-500);
//		//		world.setPosRelativaLifeBarY(300);
//		//		world.setLifeBar(lifeBar);
//
//		//Texture lifeBarCGTEnemy = new CGTTexture("data/lifeBar/lifeBar.png"));
//		//		world.setPosRelativaLifeBarCGTEnemyX(500);
//		//		world.setPosRelativaLifeBarCGTEnemyY(300);
//		//		world.setLifeBarCGTEnemy(lifeBarCGTEnemy);
//		//		world.setNumDeCGTEnemyDetroyble(2);
//
//
//
//		CGTActor personagemCGTActor = new CGTActor();
//		personagemCGTActor.setFireDefault(-1);
//		personagemCGTActor.setPosition(new Vector2(1800f,900f));
//		personagemCGTActor.setTimeToRecovery(3);
//
//		personagemCGTActor.setCollision(new Rectangle(10, 10, 60, 60));
//
//		Rectangle tamanhoPersonagem = new Rectangle(0, 0, 80, 80);
//		personagemCGTActor.setBounds(tamanhoPersonagem);
//		personagemCGTActor.setLife(3);
//
//		CGTTexture lifeBar = new CGTTexture("data/lifeBar/lifeBar.png");
//		CGTTexture lifeBarBack = new CGTTexture("data/lifeBar/lifeBarBack.png");
//		IndividualLifeBar actorLifeBar = new IndividualLifeBar(personagemCGTActor);
//		actorLifeBar.setBar(lifeBar);
//		actorLifeBar.setBackgroundBar(lifeBarBack);
//		actorLifeBar.setRelativeX(0.1f);
//		actorLifeBar.setRelativeY(0.9f);
//		actorLifeBar.setRelativeHeight(0.1f);
//		actorLifeBar.setRelativeWidth(0.25f);
//
//
//		world.addLifeBar(actorLifeBar);
//		personagemCGTActor.setSpeed(280);
//
//		personagemCGTActor.setSpriteSheet(new CGTSpriteSheet("data/SpriteCGTActor/SpriteSheet_bombeiro2.png"));
//		personagemCGTActor.getSpriteSheet().setRows(5);
//		personagemCGTActor.getSpriteSheet().setColumns(3);
//
//
//		CGTSound somDamegePersonagem = new CGTSound("data/AudioBombeiro/colisao.wav");
//		personagemCGTActor.setSoundCollision(somDamegePersonagem);
//
//		ArrayList<CGTSound> listaSomDie = new ArrayList<CGTSound>();
//		CGTSound somDiePersonagem = new CGTSound("data/AudioBombeiro/colisao.wav");
//		listaSomDie.add(somDiePersonagem);
//		personagemCGTActor.setSoundDie(listaSomDie);
//
//
//		//Action
//		CGTAnimation moveLEft = new CGTAnimation(personagemCGTActor);
//		moveLEft.setSpriteLine(1);
//		moveLEft.addStatePolicy(StatePolicy.LOOKLEFT);
//		//moveLEft.addInput(InputPolicy.ACEL_LEFT);
//		moveLEft.setSpriteVelocity(0.2f);
//		moveLEft.setFlipHorizontal(true);
//		moveLEft.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		CGTAnimation moveRight = new CGTAnimation(personagemCGTActor);
//		moveRight.setSpriteLine(1);
//		moveRight.addStatePolicy(StatePolicy.LOOKRIGHT);
//		//moveRight.addInput(InputPolicy.ACEL_RIGHT);
//		moveRight.setSpriteVelocity(0.2f);
//		moveRight.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor);
//		moveUp.setSpriteLine(3);
//		moveUp.addStatePolicy(StatePolicy.LOOKUP);
//		//moveUp.addInput(InputPolicy.ACEL_UP);
//		moveUp.setSpriteVelocity(0.2f);
//		moveUp.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		CGTAnimation moveDown = new CGTAnimation(personagemCGTActor);
//		moveDown.setSpriteLine(2);
//		moveDown.addStatePolicy(StatePolicy.LOOKDOWN);
//		//moveDown.addInput(InputPolicy.ACEL_DOWN);
//		moveDown.setSpriteVelocity(0.2f);
//		moveDown.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		CGTAnimation animationDamege = new CGTAnimation(personagemCGTActor);
//		animationDamege.setSpriteLine(5);
//		animationDamege.addStatePolicy(StatePolicy.DAMAGE);
//		//moveDown.addInput(InputPolicy.ACEL_DOWN);
//		animationDamege.setSpriteVelocity(0.2f);
//		animationDamege.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		personagemCGTActor.getAnimarions().add(animationDamege);
//		personagemCGTActor.getAnimarions().add(moveDown);
//		personagemCGTActor.getAnimarions().add(moveLEft);
//		personagemCGTActor.getAnimarions().add(moveRight);
//		personagemCGTActor.getAnimarions().add(moveUp);
//
//
//		//Adicionando o personagem na libGDX
//		//personagemActorLIB = new ActorCGT(personagemCGTActor);
//
//		//-------------------------------------------------------------------------------//
//		//		personagemActorLIB = new CGTActor(new Vector2(800,900), 100f, 100f, 80f, 10f, 10f);
//		//		personagemActorLIB.setSpeed(180);
//		//		personagemActorLIB.setLife(3);
//		//		CGTAnimation spriteSheetActor = new CGTAnimation();
//		//		personagemActorLIB.setSpriteSheet(spriteSheetActor );
//		//		//Adicionando o audio de colisao
//		//		Music soundDamage = Gdx.audio.newMusic("data/AudioBombeiro/colisao.wav"));
//		//		personagemActorLIB.setSoundDamage(soundDamage);
//		//		
//		//		Music soundDie = Gdx.audio.newMusic("data/AudioBombeiro/gameOver.wav"));
//		//		personagemActorLIB.setSoundDie(soundDie);
//		//		
//		//		spriteSheetActor.setLinhaDoSpriteUp(3);
//		//		spriteSheetActor.setLinhaDoSpriteDown(2);
//		//		spriteSheetActor.setLinhaDoSpriteLeft(1);
//		//		spriteSheetActor.setLinhaDoSpriteRight(1);
//		//		spriteSheetActor.setLinhaDoSpriteDamege(5);
//		//		spriteSheetActor.loadSpriteActorCGT("data/SpriteCGTActor/SpriteSheet_bombeiro.png",5,3);
//
//
//		/* Esse opposite nao tem animacao, seria melhor adicionar uma textura do que uma animacao 
//		 * um por um.
//		 */
//
//		// esse for serve para iniciar as casas no cen�rio
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				CGTOpposite opositorCasa = new CGTOpposite();
//
//
//
//
//				//new Vector2(450*(i+1)+i*20,400*(j+1)+j*90), 300, 300, 300, 0, 0
//
//				opositorCasa.setBounds(new Rectangle(0,0,300, 300) );
//				if ((i == 0 && j== 3) || (i==1 && (j==1 || j==2)) || (i==2 &&( j==2 || j==3)) || (i==3 && j==3) ){
//					opositorCasa.setCollision(new Rectangle(30,60,190, 200));
//				}
//				else if((i == 0 && (j== 0 || j==2)) || (i==1 && (j==0 || j==3)) || (i==2 && (j==0 || j==1))){
//					opositorCasa.setCollision(new Rectangle(30,70,190, 210));
//				}
//				else if((i == 0 && j==1) || (i==3 && j==0) ){
//					opositorCasa.setCollision(new Rectangle(30,0,190, 290));
//				}
//				else if(i==3 && j==1){
//					opositorCasa.setCollision(new Rectangle(0,60,300, 210));
//				}
//				else{
//					opositorCasa.setCollision(new Rectangle(25,60,200, 230));
//				}
//
//
//				Vector2 position = new Vector2(450*(i+1)+ i*20, 400*(j+1)+j*90);
//				opositorCasa.setPosition(position);
//
//				//opositorCasa.setTexture(new CGTTexture("data/Cenario/casa_sprite_sheet.png"));
//				opositorCasa.setBlock(true);
//				opositorCasa.setDestroyable(false);
//				opositorCasa.setLife(0);
//
//				opositorCasa.setSpriteSheet(new CGTSpriteSheet("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png"));
//				opositorCasa.getSpriteSheet().setRows(1);
//				opositorCasa.getSpriteSheet().setColumns(1);
//
//
//				CGTAnimation teste = new CGTAnimation(opositorCasa);
//				teste.setSpriteLine(1);
//				teste.addStatePolicy(StatePolicy.IDLEDOWN);
//				teste.setAnimationPolicy(PlayMode.LOOP);
//				opositorCasa.getAnimarions().add(teste);
//
//				//Indica que a minha animacao e' um por um
//				//opositorCasa.getSpriteSheet().loadingSpriteSheet("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png", 1, 1);
//				//Opposite opositorCasaLib = new Opposite(opositorCasa);
//				world.getOpposites().add(opositorCasa);				
//			}
//		}
//
//		// ajuste da posi��o da casa do bombeiro na tela
//		world.getOpposites().get(13).setPosition(new Vector2(1880,890));
//
//
//		Fade fade = new Fade(FadePolicy.FADE_IN);
//		fade.setFadeInTime(1);
//
//		Sine sine = new Sine(MovementPolicy.HEIGHT);
//		sine.setMax(100);
//		sine.setMin(50);
//		sine.setAtFirstStep(true);
//
//		CGTEnemy enemyFogoCGT = new CGTEnemy();
//
//		Vector2 positionEnemy = new Vector2(480,850);
//		enemyFogoCGT.setPosition(positionEnemy);
//
//		Rectangle coliderEnemy = new Rectangle(0, 0, 56, 98);
//		enemyFogoCGT.setCollision(coliderEnemy);
//
//		Rectangle tamanhoEnemy = new Rectangle(0,0,56, 98);
//		enemyFogoCGT.setBounds(tamanhoEnemy);
//
//		enemyFogoCGT.setState(StatePolicy.IDLEDOWN);
//		enemyFogoCGT.setBlock(false);
//		enemyFogoCGT.setDamage(1);
//		//enemyFogoCGT.setSpeed(2);
//		enemyFogoCGT.setDestroyable(true);
//		enemyFogoCGT.addBehavior(fade);
//		enemyFogoCGT.addBehavior(sine);
//		enemyFogoCGT.setLife(50);
//
//		enemyFogoCGT.setSpriteSheet(new CGTSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png"));
//		enemyFogoCGT.getSpriteSheet().setRows(2);
//		enemyFogoCGT.getSpriteSheet().setColumns(2);
//
//
//		//Action
//		CGTAnimation moveEnemy = new CGTAnimation(enemyFogoCGT);
//		moveEnemy.setInitialFrame(new Vector2(0,0));
//		moveEnemy.setEndingFrame(new Vector2(1,1));
//		moveEnemy.addStatePolicy(StatePolicy.IDLEDOWN);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveEnemy.setSpriteVelocity(0.2f);
//		moveEnemy.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		enemyFogoCGT.setGroup("fogo1");
//		enemyFogoCGT.getAnimarions().add(moveEnemy);
//		world.getEnemies().add(enemyFogoCGT);
//
//		CGTEnemy enemyFogoCGT2 = new CGTEnemy();
//
//		Vector2 positionEnemy2 = new Vector2(680,850);
//		enemyFogoCGT2.setPosition(positionEnemy2);
//
//		Rectangle coliderEnemy2 = new Rectangle(0, 0, 56, 98);
//		enemyFogoCGT2.setCollision(coliderEnemy2);
//
//		Rectangle tamanhoEnemy2 = new Rectangle(0,0,56, 98);
//		enemyFogoCGT2.setBounds(tamanhoEnemy2);
//
//		enemyFogoCGT2.setState(StatePolicy.IDLEDOWN);
//		enemyFogoCGT2.setBlock(false);
//		enemyFogoCGT2.setDamage(1);
//		//enemyFogoCGT.setSpeed(2);
//		enemyFogoCGT2.setDestroyable(true);
//		enemyFogoCGT2.addBehavior(fade);
//		enemyFogoCGT2.addBehavior(sine);
//		enemyFogoCGT2.setLife(50);
//
//		enemyFogoCGT2.setSpriteSheet(new CGTSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png"));
//		enemyFogoCGT2.getSpriteSheet().setRows(2);
//		enemyFogoCGT2.getSpriteSheet().setColumns(2);
//
//
//		//Action
//		CGTAnimation moveEnemy2 = new CGTAnimation(enemyFogoCGT2);
//		moveEnemy2.setInitialFrame(new Vector2(0,0));
//		moveEnemy2.setEndingFrame(new Vector2(1,1));
//		moveEnemy2.addStatePolicy(StatePolicy.IDLEDOWN);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveEnemy2.setSpriteVelocity(1f);
//		moveEnemy2.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		enemyFogoCGT2.getAnimarions().add(moveEnemy2);
//		world.getEnemies().add(enemyFogoCGT2);
//
//		//		//Instancia o opposite fogo
//		//		Enemy enemyFogo = new Enemy(new Vector2(400,850), 50, 50, 50, 0, 0);
//		//		enemyFogo.setBlock(false);
//		//		enemyFogo.setDamage(1);
//		//		enemyFogo.setSpeed(2);
//		//		enemyFogo.setDestroyable(true);
//		//		enemyFogo.addBehavior(fade);
//		//		enemyFogo.addBehavior(sine);
//		//		enemyFogo.setLife(50);
//		//		enemyFogo.setSpriteSheet(new CGTAnimation());
//		//		enemyFogo.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
//		//		listaDeEnemy.add(enemyFogo);
//		//		
//		//		Fade fade2 = new Fade(FadePolicy.FADE_IN);
//		//		fade2.setFadeInTime(1);
//		//		
//		//		//Instancia o opposite fogo
//		//		Enemy enemyFogo2 = new Enemy(new Vector2(200,1050), 50, 50, 50, 0, 0);
//		//		enemyFogo2.setBlock(true);
//		//		enemyFogo2.setDestroyable(true);
//		//		enemyFogo2.setDamage(1);
//		//		enemyFogo2.addBehavior(fade2);
//		//		enemyFogo2.setLife(200);
//		//		enemyFogo2.setSpriteSheet(new CGTAnimation());
//		////		enemyFogo2.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
//		//		listaDeEnemy.add(enemyFogo2);
//		//		
//		//		Fade fade3 = new Fade(FadePolicy.FADE_IN);
//		//		fade3.setFadeInTime(1);
//		//		
//		//		//Instancia o opposite fogo
//		//		Enemy enemyFogo3 = new Enemy(new Vector2(200,1500), 50, 50, 50, 0, 0);
//		//		enemyFogo3.setBlock(true);
//		//		enemyFogo3.setDestroyable(true);
//		//		enemyFogo3.setDamage(2);
//		//		enemyFogo3.setLife(100);
//		//		enemyFogo3.addBehavior(fade3);
//		//		enemyFogo3.setSpriteSheet(new CGTAnimation());
//		////		enemyFogo3.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
//		//		listaDeEnemy.add(enemyFogo3);
//		//		
////		Direction direction = new Direction(DirectionPolicy.LEFT_AND_RIGHT);
////		direction.setMaxX(800);
////		direction.setMinX(330);
//		//		
//		Direction directionUp = new Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
//		directionUp.setInitialPosition(new Vector2(800, 2000));
//		directionUp.setFinalPosition(new Vector2(800, 200));
//		//		
//		//
//
//		//
//		//
//		//		Direction directionEight = new Direction(DirectionPolicy.EIGHT_DIRECTION);
//		//		directionEight.setMaxY(600);
//		//		directionEight.setMinY(400);
//		//		directionEight.setMaxX(800);
//		//		directionEight.setMinX(330);
//		//		
//		//		
//		Fade fadeCar = new Fade(FadePolicy.FADE_IN);
//		fadeCar.setFadeInTime(0);
//		//		
//		CGTEnemy carroCGT = new CGTEnemy();
//
//		Vector2 positionCarro = new Vector2(800,600);
//		carroCGT.setPosition(positionCarro);
//
//		Rectangle coliderCarro = new Rectangle(0,0,90, 98);
//		carroCGT.setCollision(coliderCarro);
//
//		Rectangle tamanhoCarro = new Rectangle(0,0,98, 90);
//		carroCGT.setBounds(tamanhoCarro);
//
//		carroCGT.setBlock(false);
//		carroCGT.setDestroyable(false);
//		carroCGT.setDamage(10);
//		carroCGT.addBehavior(fadeCar);
//		carroCGT.addBehavior(directionUp);
//
//		carroCGT.setSpeed(200);
//
//		carroCGT.setSpriteSheet(new CGTSpriteSheet("data/Enemy/SpriteSheet_carro.png"));
//		carroCGT.getSpriteSheet().setRows(3);
//		carroCGT.getSpriteSheet().setColumns(2);
//		//Action
//		CGTAnimation moveCarro = new CGTAnimation(carroCGT);
//		moveCarro.setSpriteLine(1);
//		moveCarro.addStatePolicy(StatePolicy.LOOKRIGHT);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveCarro.setSpriteVelocity(0.08f);
//		moveCarro.setAnimationPolicy(PlayMode.LOOP);
//		carroCGT.getAnimarions().add(moveCarro);
//
//		CGTAnimation moveCarroLeft = new CGTAnimation(carroCGT);
//		moveCarroLeft.setFlipHorizontal(true);
//		moveCarroLeft.setSpriteLine(1);
//		moveCarroLeft.addStatePolicy(StatePolicy.LOOKLEFT);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveCarroLeft.setSpriteVelocity(0.08f);
//		moveCarroLeft.setAnimationPolicy(PlayMode.LOOP);
//		carroCGT.getAnimarions().add(moveCarroLeft);
//
//		CGTAnimation moveCarroDown = new CGTAnimation(carroCGT);
//		moveCarroDown.setSpriteLine(2);
//		moveCarroDown.addStatePolicy(StatePolicy.LOOKDOWN);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveCarroDown.setSpriteVelocity(0.08f);
//		moveCarroDown.setAnimationPolicy(PlayMode.LOOP);
//		carroCGT.getAnimarions().add(moveCarroDown);
//
//		CGTAnimation moveCarroUp = new CGTAnimation(carroCGT);
//		moveCarroUp.setSpriteLine(3);
//		moveCarroUp.addStatePolicy(StatePolicy.LOOKUP);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveCarroUp.setSpriteVelocity(0.08f);
//		moveCarroUp.setAnimationPolicy(PlayMode.LOOP);
//		carroCGT.getAnimarions().add(moveCarroUp);
//
//		//Enemy enemyCarroLIB = new Enemy(carroCGT);
//		//enemyCarroLIB.getSpriteSheet().setLoop(true);
//		//Add na lista de enemy
//		world.getEnemies().add(carroCGT);
//
//		//		//Instancia o opposite carro
//		//		Enemy carro = new Enemy(new Vector2(800,700), 50, 50, 50, 0, 0);
//		//		carro.setLife(10);
//		//		carro.setBlock(true);
//		//		carro.setDestroyable(false);
//		//		carro.setDamage(10);
//		//		carro.addBehavior(fadeCar);
//		//		carro.addBehavior(directionUp);
//		//		carro.setSpeed(200);
//		//		//carro.setLife(200);
//		//		carro.setSpriteSheet(new CGTAnimation());
//		////		carro.getSpriteSheet().loadingSpriteSheet("data/Enemy/Carro.png", 1, 1);
//		//		listaDeEnemy.add(carro);
//
//		CGTBonus hidrate = new CGTBonus();
//
//		hidrate.setPosition(new Vector2(1880, 880));
//		hidrate.setBounds(new Rectangle(0,0,50,50));
//		hidrate.setCollision(new Rectangle(0,0,50,50));
//
//		hidrate.setSpriteSheet(new CGTSpriteSheet("data/CGTBonus/SpriteSheet_tubo.png"));
//
//		CGTAnimation aniHidrante = new CGTAnimation(hidrate);
//		aniHidrante.setSpriteLine(1);
//		hidrate.getAnimarions().add(aniHidrante);
//		//hidrate.setTexture(new CGTTexture("data/CGTBonus/SpriteSheet_tubo.png"));
//		world.getBonus().add(hidrate);
//
//		CGTProjectile projetilAguaCGT = new CGTProjectile();
//
//		Vector2 position = new Vector2(100f,200f);
//		projetilAguaCGT.setPosition(position);
//
//		projetilAguaCGT.setBounds(new Rectangle(0,0,30, 30));
//		Rectangle coliderProjectile = new Rectangle(0,0,30, 30);
//
//		//Vector2 positionRelative = new Vector2(0,0);
//		projetilAguaCGT.setCollision(coliderProjectile);
//		projetilAguaCGT.setInterval(1);
//
//		CGTSpriteSheet css = new CGTSpriteSheet("data/CGTProjectile/SpriteSheet_agua.png");
//		css.setRows(2);
//		css.setColumns(2);
//		projetilAguaCGT.setSpriteSheet(css);
//		projetilAguaCGT.setAmmo(100);
//
//		//Action dos projectiles
//		CGTAnimation m = new CGTAnimation(projetilAguaCGT);
//		m.setSpriteLine(1);
//		m.setFlipHorizontal(true);
//		m.addStatePolicy(StatePolicy.LOOKLEFT);
//		m.addStatePolicy(StatePolicy.IDLELEFT);
//		m.setSpriteVelocity(0.08f);
//		m.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation a = new CGTAnimation(projetilAguaCGT);
//		a.setSpriteLine(1);
//		a.setFlipHorizontal(false);
//		a.addStatePolicy(StatePolicy.LOOKRIGHT);
//		a.addStatePolicy(StatePolicy.IDLERIGHT);
//		a.setSpriteVelocity(0.08f);
//		a.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation down = new CGTAnimation(projetilAguaCGT);
//		down.setSpriteLine(2);
//		down.setFlipHorizontal(false);
//		down.addStatePolicy(StatePolicy.LOOKDOWN);
//		down.addStatePolicy(StatePolicy.IDLEDOWN);
//		down.setSpriteVelocity(0.08f);
//		down.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation up = new CGTAnimation(projetilAguaCGT);
//		up.setSpriteLine(2);
//		up.setFlipHorizontal(true);
//		up.addStatePolicy(StatePolicy.LOOKUP);
//		up.addStatePolicy(StatePolicy.IDLEUP);
//		up.setSpriteVelocity(0.08f);
//		up.setAnimationPolicy(PlayMode.LOOP);
//		projetilAguaCGT.getAnimarions().add(up);
//		projetilAguaCGT.getAnimarions().add(down);
//		projetilAguaCGT.getAnimarions().add(a);
//		projetilAguaCGT.getAnimarions().add(m);
//
//
//		//Projectile orientation
//		ProjectileOrientation direcaoRight = new ProjectileOrientation();
//		direcaoRight.setPositionRelativeToGameObject(new Vector2(60f,15f));
//		//direcaoRight.setSpriteLine(1);
//		//direcaoRight.setSpriteNumberOfColumns(2);
//		//direcaoRight.setSpriteVelocity(2);
//		direcaoRight.addState(StatePolicy.LOOKRIGHT);
//		direcaoRight.addState(StatePolicy.IDLERIGHT);
//		projetilAguaCGT.getOrientations().add(direcaoRight);
//
//		ProjectileOrientation direcaoLeft = new ProjectileOrientation();
//		direcaoLeft.setPositionRelativeToGameObject(new Vector2(-13f, 15f));
//		//direcaoLeft.setSpriteLine(1);
//		//direcaoLeft.setSpriteNumberOfColumns(2);
//		//direcaoLeft.setSpriteVelocity(2);
//		direcaoLeft.addState(StatePolicy.LOOKLEFT);
//		direcaoLeft.addState(StatePolicy.IDLELEFT);
//		projetilAguaCGT.getOrientations().add(direcaoLeft);
//
//		ProjectileOrientation direcaoUp = new ProjectileOrientation();
//		direcaoUp.setPositionRelativeToGameObject(new Vector2(30f, 60f));
//		//direcaoUp.setSpriteLine(2);
//		//direcaoUp.setSpriteNumberOfColumns(2);
//		//direcaoUp.setSpriteVelocity(2);
//		direcaoUp.addState(StatePolicy.LOOKUP);
//		direcaoUp.addState(StatePolicy.IDLEUP);
//		projetilAguaCGT.getOrientations().add(direcaoUp);
//
//		ProjectileOrientation direcaoDown = new ProjectileOrientation();
//		direcaoDown.setPositionRelativeToGameObject(new Vector2(25f, -20f));
//		//direcaoDown.setSpriteLine(2);
//		//direcaoDown.setSpriteNumberOfColumns(2);
//		//direcaoDown.setSpriteVelocity(2);
//		direcaoDown.addState(StatePolicy.LOOKDOWN);
//		direcaoDown.addState(StatePolicy.IDLEDOWN);
//		projetilAguaCGT.getOrientations().add(direcaoDown);
//		personagemCGTActor.getProjectiles().add(projetilAguaCGT);
//
//		personagemCGTActor.addProjectile(projetilAguaCGT);
//		world.setActor(personagemCGTActor);
//
//		CGTButton buttonPad = new CGTButton();
//
//		CGTTexture textureUp = new CGTTexture("data/buttons/base.png");
//		buttonPad.setTextureUp(textureUp);
//		CGTTexture textureDown = new CGTTexture("data/buttons/base.png");
//		buttonPad.setTextureDown(textureDown);
//
//		buttonPad.setRelativeX(0);
//		buttonPad.setRelativeY(0);
//		buttonPad.setRelativeWidth(0.29f);
//		buttonPad.setRelativeHeight(0.29f);
//		buttonPad.setBounds(0, 0, textureUp.getWidth()/3, textureUp.getHeight()/3);
//
//		CGTControllerButton button = new CGTControllerButton();
//		button.setInput(InputPolicy.BTN_UP);
//
//		textureUp = new CGTTexture("data/buttons/bt_up_up.png");
//		button.setTextureUp(textureUp);
//
//		textureDown = new CGTTexture("data/buttons/bt_up_press.png");
//		button.setTextureDown(textureDown);
//
//		button.setRelativeX(0.095f);
//		button.setRelativeY(0.145f);
//		button.setRelativeWidth(0.1f);
//		button.setRelativeHeight(0.1f);
//		button.setBounds(137/3, 184.7f/3, textureUp.getWidth()/3, textureUp.getHeight()/3);
//
//
//		CGTControllerButton buttonDown = new CGTControllerButton();
//		buttonDown.setInput(InputPolicy.BTN_DOWN);
//
//		textureUp = new CGTTexture("data/buttons/bt_down_up.png");
//		buttonDown.setTextureUp(textureUp);
//		textureDown = new CGTTexture("data/buttons/bt_down_press.png");
//		buttonDown.setTextureDown(textureDown);
//
//		buttonDown.setRelativeX(0.095f);
//		buttonDown.setRelativeY(0.028f);
//		buttonDown.setRelativeWidth(0.1f);
//		buttonDown.setRelativeHeight(0.1f);
//		buttonDown.setBounds(137/3, 36/3, textureUp.getWidth()/3, textureUp.getHeight()/3);
//
//
//		CGTControllerButton buttonLeft = new CGTControllerButton();
//		buttonLeft.setInput(InputPolicy.BTN_LEFT);
//
//		textureUp = new CGTTexture("data/buttons/bt_left_up.png");
//		buttonLeft.setTextureUp(textureUp);
//
//		textureDown = new CGTTexture("data/buttons/bt_left_press.png");
//		buttonLeft.setTextureDown(textureDown);
//
//		buttonLeft.setRelativeX(0.048f);
//		buttonLeft.setRelativeY(0.09f);
//		buttonLeft.setRelativeWidth(0.1f);
//		buttonLeft.setRelativeHeight(0.1f);
//		buttonLeft.setBounds(64/3, 126/3, textureUp.getWidth()/3, textureUp.getHeight()/3);
//
//
//		CGTControllerButton buttonRight = new CGTControllerButton();
//		buttonRight.setInput(InputPolicy.BTN_RIGHT);
//
//		textureUp = new CGTTexture("data/buttons/bt_right_up.png");
//		buttonRight.setTextureUp(textureUp);
//		textureDown = new CGTTexture("data/buttons/bt_right_press.png");
//		buttonRight.setTextureDown(textureDown);
//
//		buttonRight.setRelativeX(0.142f);
//		buttonRight.setRelativeY(0.09f);
//		buttonRight.setRelativeWidth(0.1f);
//		buttonRight.setRelativeHeight(0.1f);
//		buttonRight.setBounds(183/3, 126/3, textureUp.getWidth()/3, textureUp.getHeight()/3);
//
//		CGTControllerButton button1 = new CGTControllerButton();
//		button1.setInput(InputPolicy.BTN_1);
//
//		textureUp = new CGTTexture("data/buttons/bt_agua_up.png");
//		button1.setTextureUp(textureUp);
//		textureDown = new CGTTexture("data/buttons/bt_agua_down.png");
//		button1.setTextureDown(textureDown);
//
//		button1.setRelativeX(0.9f);
//		button1.setRelativeY(0.1f);
//		button1.setRelativeWidth(0.1f);
//		button1.setRelativeHeight(0.1f);
//		button1.setBounds(0, 0, textureUp.getWidth()/2, textureUp.getHeight()/2);
//
//
//		/*CGTAnimation moveButton = new CGTAnimation(button);
//		moveButton.setSpriteLine(1);
//		moveButton.addStatePolicy(StatePolicy.IDLEDOWN);
//		moveButton.setNumberOfColumns(2);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveButton.setSpriteVelocity(0.08f);
//		moveButton.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		CGTAnimation moveButton2 = new CGTAnimation(button);
//		moveButton2.setSpriteLine(2);
//		moveButton2.addStatePolicy(StatePolicy.IDLEUP);
//		moveButton2.setNumberOfColumns(2);
//		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
//		moveButton2.setSpriteVelocity(0.08f);
//		moveButton2.setAnimationPolicy(PlayMode.LOOP_PINGPONG);
//
//		button.getAnimarions().add(moveButton);
//		button.getAnimarions().add(moveButton2);
//
//
//
//		//button.draw(batch, parentAlpha)
//		 */
//		world.addButton(buttonPad);
//		world.addButton(button);
//		world.addButton(buttonDown);
//		world.addButton(buttonLeft);
//		world.addButton(buttonRight);
//		world.addButton(button1);
//
//		
//		EnemyGroupLifeBar alerts = new EnemyGroupLifeBar(world.getEnemies());
//		CGTTexture enemyLifeBar = new CGTTexture("data/lifeBar/lifeBar.png");
//		CGTTexture enemyLifeBarBack = new CGTTexture("data/lifeBar/lifeBarBack.png");
//		alerts.setBar(enemyLifeBar);
//		alerts.setBackgroundBar(enemyLifeBarBack);
//		alerts.setRelativeX(0.7f);
//		alerts.setRelativeY(0.9f);
//		alerts.setRelativeHeight(0.1f);
//		alerts.setRelativeWidth(0.25f);
//		world.addLifeBar(alerts);
//		
//		//world.addLoseCriterion(new TargetTime(5));
//		world.addLoseCriterion(new LifeDepleted(world.getActor()));
//		world.addWinCriterion(new KillAllEnemies(world.getEnemies()));
//	}
//
//}
