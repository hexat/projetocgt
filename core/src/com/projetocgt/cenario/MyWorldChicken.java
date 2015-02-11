//package com.projetocgt.cenario;
//
//import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//
//import cgt.game.CGTGame;
//import cgt.game.CGTGameWorld;
//import cgt.behaviors.Direction;
//import cgt.behaviors.Direction.DirectionMode;
//import cgt.behaviors.SineWave;
//import cgt.core.CGTActor;
//import cgt.core.CGTAddOn;
//import cgt.core.CGTEnemy;
//import cgt.core.CGTOpposite;
//import cgt.hud.CGTButton;
//import cgt.hud.CGTButtonScreen;
//import cgt.hud.CGTButtonStartGame;
//import cgt.hud.CGTLabel;
//import cgt.lose.LifeDepleted;
//import cgt.policy.ActionMovePolicy;
//import cgt.policy.DirectionPolicy;
//import cgt.policy.GameModePolicy;
//import cgt.policy.InputPolicy;
//import cgt.policy.StatePolicy;
//import cgt.screen.CGTDialog;
//import cgt.game.CGTScreen;
//import cgt.unit.Action;
//import cgt.core.CGTAnimation;
//import cgt.util.CGTFile;
//import cgt.util.CGTSound;
//import cgt.game.CGTSpriteSheet;
//import cgt.util.CGTTexture;
//import cgt.win.CompleteCrossing;
//
//public class MyWorldChicken {
//	private CGTTexture backGround;
//	private CGTGameWorld world;
//	private CGTScreen screen;
//	private CGTGame game;
//
//	public void configuracaoActor(CGTActor personagemCGTActor) {
//		// personagemCGTActor.setFireDefault(-1);
//		personagemCGTActor.setPosition(new Vector2(1f, 143f)); // obrigatorio
//		// personagemCGTActor.setTimeToRecovery(4);
//		personagemCGTActor.setCollision(new Rectangle(10, 10, 40, 45));
//
//		personagemCGTActor.setBounds(new Rectangle(0, 0, 60, 60)); // Obrigatorio
//		personagemCGTActor.setLife(1);
//		personagemCGTActor.setSpeed(100);
//
//		personagemCGTActor.getSoundsDie().add(
//				new CGTSound("data/AudioChicken/galinha_derrota_2.wav", 0.7f));
//
//	}
//
//	public void configuracaoActionActor(CGTActor personagemCGTActor) {
//
//		CGTSpriteSheet galinhaSpriteSheet = new CGTSpriteSheet(
//				"data/chicken/galinha_spritesheet.png");
//		galinhaSpriteSheet.setRows(8);
//		galinhaSpriteSheet.setColumns(4);
//
//		CGTAnimation moveUp = new CGTAnimation(personagemCGTActor,
//				galinhaSpriteSheet);
//		moveUp.setInitialFrame(new Vector2(0, 0));
//		moveUp.setEndingFrame(new Vector2(1, 7));
//		// moveUp.addStatePolicy(StatePolicy.IDLE);
//		// moveUp.addStatePolicy(StatePolicy.DYING);
//		moveUp.setFlipHorizontal(false);
//		moveUp.setSpriteVelocity(0.13f);
//
//		personagemCGTActor.addAnimation(StatePolicy.IDLE, moveUp);
//
//		CGTAnimation moveVuando = new CGTAnimation(personagemCGTActor,
//				galinhaSpriteSheet);
//		moveVuando.setInitialFrame(new Vector2(2, 6));
//		moveVuando.setEndingFrame(new Vector2(1, 7));
//		// moveUp.addStatePolicy(StatePolicy.IDLE);
//		// moveUp.addStatePolicy(StatePolicy.DYING);
//		moveVuando.setFlipHorizontal(false);
//		moveVuando.setSpriteVelocity(0.03f);
//
//		personagemCGTActor.addAnimation(StatePolicy.LOOKRIGHT, moveVuando);
//		personagemCGTActor.addAnimation(StatePolicy.IDLERIGHT, moveVuando);
//		personagemCGTActor.addAnimation(StatePolicy.IDLEUP, moveVuando);
//		personagemCGTActor.addAnimation(StatePolicy.IDLEDOWN, moveVuando);
//		personagemCGTActor.addAnimation(StatePolicy.LOOKUP, moveVuando);
//		personagemCGTActor.addAnimation(StatePolicy.LOOKDOWN, moveVuando);
//
//		CGTSpriteSheet penasSpriteSheet = new CGTSpriteSheet(
//				"data/chicken/penas_spritesheet.png");
//		penasSpriteSheet.setRows(9);
//		penasSpriteSheet.setColumns(5);
//
//		CGTAnimation died = new CGTAnimation(personagemCGTActor,
//				penasSpriteSheet);
//		died.setInitialFrame(new Vector2(0, 0));
//		died.setEndingFrame(new Vector2(4, 8));
//		died.setSpriteVelocity(0.035f);
//
//		personagemCGTActor.addAnimation(StatePolicy.DYING, died);
//	}
//
//	public void configuracaoVacas() {
//		// inicializando o carro no cenario
//
//		Direction movimentacaoVaca = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoVaca.setInitialPosition(new Vector2(100, 100));
//		movimentacaoVaca.setFinalPosition(new Vector2(900, 730));
//		movimentacaoVaca.setInteligenceMoviment(false);
//		movimentacaoVaca.setDirectionMode(DirectionMode.PINGPONG);
//
//		Direction movimentacaoVaca2 = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoVaca2.setInitialPosition(new Vector2(200, 730));
//		movimentacaoVaca2.setFinalPosition(new Vector2(900, 100));
//		movimentacaoVaca2.setInteligenceMoviment(false);
//		movimentacaoVaca2.setDirectionMode(DirectionMode.PINGPONG);
//
//		Direction movimentacaoVaca3 = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoVaca3.setInitialPosition(new Vector2(700, 100));
//		movimentacaoVaca3.setFinalPosition(new Vector2(130, 500));
//		movimentacaoVaca3.setInteligenceMoviment(false);
//		movimentacaoVaca3.setDirectionMode(DirectionMode.PINGPONG);
//
//		CGTEnemy vaca = new CGTEnemy();
//
//		Rectangle coliderVaca = new Rectangle(25, 26, 80, 96);
//		vaca.setCollision(coliderVaca);
//
//		Rectangle tamanhoVaca = new Rectangle(0, 0, 126, 126);
//		vaca.setBounds(tamanhoVaca);
//
//		vaca.addBehavior(movimentacaoVaca);
//		vaca.setBlock(true);
//		vaca.setDestroyable(false);
//		vaca.setDamage(50);
//		vaca.setSpeed(100);
//		vaca.setState(StatePolicy.LOOK_RIGHT_AND_UP);
//
//		CGTSound soundVaca = new CGTSound("data/AudioChicken/vaca_foguete.wav",
//				1f);
//		vaca.setSound(soundVaca);
//
//		CGTSpriteSheet vacaSpriteSheet = new CGTSpriteSheet(
//				"data/chicken/vaca_1_spritesheet.png");
//		vacaSpriteSheet.setRows(3);
//		vacaSpriteSheet.setColumns(4);
//
//		// Action
//		CGTAnimation moveVacaUp = new CGTAnimation(vaca, vacaSpriteSheet);
//		moveVacaUp.setInitialFrame(new Vector2(0, 0));
//		moveVacaUp.setEndingFrame(new Vector2(1, 1));
//		moveVacaUp.setSpriteLine(1);
//		moveVacaUp.setSpriteVelocity(0.08f);
//		moveVacaUp.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation moveVacaDown = new CGTAnimation(vaca, vacaSpriteSheet);
//		moveVacaDown.setInitialFrame(new Vector2(2, 1));
//		moveVacaDown.setEndingFrame(new Vector2(3, 2));
//		moveVacaDown.setSpriteVelocity(0.08f);
//		moveVacaDown.setFlipHorizontal(true);
//		moveVacaDown.setAnimationPolicy(PlayMode.LOOP);
//
//		vaca.addAnimation(StatePolicy.LOOK_RIGHT_AND_UP, moveVacaUp);
//		vaca.addAnimation(StatePolicy.LOOK_LEFT_AND_DOWN, moveVacaDown);
//
//		world.getEnemies().add(vaca);
//
//		CGTEnemy vaca2 = new CGTEnemy();
//
//		vaca2.setSound(soundVaca);
//
//		Rectangle coliderVaca2 = new Rectangle(25, 26, 80, 96);
//		vaca2.setCollision(coliderVaca2);
//
//		Rectangle tamanhoVaca2 = new Rectangle(0, 0, 126, 126);
//		vaca2.setBounds(tamanhoVaca2);
//
//		vaca2.setBlock(true);
//		vaca2.setDestroyable(false);
//		vaca2.setDamage(50);
//		vaca2.addBehavior(movimentacaoVaca2);
//		vaca2.setState(StatePolicy.LOOK_RIGHT_AND_DOWN);
//		vaca2.setSpeed(200);
//
//		CGTSpriteSheet vacaSpriteSheet2 = new CGTSpriteSheet(
//				"data/chicken/vaca_2_spritesheet.png");
//		vacaSpriteSheet2.setRows(3);
//		vacaSpriteSheet2.setColumns(4);
//
//		CGTAnimation moveVacaUp2 = new CGTAnimation(vaca2, vacaSpriteSheet2);
//		moveVacaUp2.setInitialFrame(new Vector2(0, 0));
//		moveVacaUp2.setEndingFrame(new Vector2(1, 1));
//		moveVacaUp2.setSpriteVelocity(0.08f);
//		moveVacaUp2.setAnimationPolicy(PlayMode.LOOP);
//		moveVacaUp2.setFlipHorizontal(true);
//
//		CGTAnimation moveVacaDown2 = new CGTAnimation(vaca2, vacaSpriteSheet2);
//		moveVacaDown2.setInitialFrame(new Vector2(2, 1));
//		moveVacaDown2.setEndingFrame(new Vector2(3, 2));
//		moveVacaDown2.setSpriteVelocity(0.08f);
//		moveVacaDown2.setAnimationPolicy(PlayMode.LOOP);
//
//		vaca2.addAnimation(StatePolicy.LOOK_LEFT_AND_UP, moveVacaUp2);
//		vaca2.addAnimation(StatePolicy.LOOK_RIGHT_AND_DOWN, moveVacaDown2);
//
//		// Add na lista de enemy
//		world.getEnemies().add(vaca2);
//
//		CGTEnemy vaca3 = new CGTEnemy();
//
//		vaca3.setSound(soundVaca);
//
//		Rectangle coliderVaca3 = new Rectangle(25, 26, 80, 96);
//		vaca3.setCollision(coliderVaca3);
//
//		Rectangle tamanhoVaca3 = new Rectangle(0, 0, 126, 126);
//		vaca3.setBounds(tamanhoVaca3);
//
//		vaca3.setBlock(true);
//		vaca3.setDestroyable(false);
//		vaca3.setDamage(50);
//		vaca3.addBehavior(movimentacaoVaca3);
//		vaca3.setState(StatePolicy.LOOK_RIGHT_AND_DOWN);
//		vaca3.setSpeed(400);
//
//		CGTSpriteSheet vacaSpriteSheet3 = new CGTSpriteSheet(
//				"data/chicken/vaca_3_spritesheet.png");
//		vacaSpriteSheet3.setRows(3);
//		vacaSpriteSheet3.setColumns(4);
//
//		CGTAnimation moveVacaUp3 = new CGTAnimation(vaca3, vacaSpriteSheet3);
//		moveVacaUp3.setInitialFrame(new Vector2(0, 0));
//		moveVacaUp3.setEndingFrame(new Vector2(1, 1));
//		moveVacaUp3.setSpriteVelocity(0.08f);
//		moveVacaUp3.setAnimationPolicy(PlayMode.LOOP);
//		moveVacaUp3.setFlipHorizontal(true);
//
//		CGTAnimation moveVacaDown3 = new CGTAnimation(vaca3, vacaSpriteSheet3);
//		moveVacaDown3.setInitialFrame(new Vector2(2, 1));
//		moveVacaDown3.setEndingFrame(new Vector2(3, 2));
//		moveVacaDown3.setSpriteVelocity(0.08f);
//		moveVacaDown3.setAnimationPolicy(PlayMode.LOOP);
//
//		vaca3.addAnimation(StatePolicy.LOOK_LEFT_AND_UP, moveVacaUp3);
//		vaca3.addAnimation(StatePolicy.LOOK_RIGHT_AND_DOWN, moveVacaDown3);
//
//		// Add na lista de enemy
//		world.getEnemies().add(vaca3);
//
//	}
//
//	public void configuracaoPeixes() {
//		// Direction movimentacaoPeixe = new
//		// Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
//		// movimentacaoPeixe.setInitialPosition(new Vector2(200,0));
//		// movimentacaoPeixe.setFinalPosition(new Vector2(200,200));
//		// movimentacaoPeixe.setInteligenceMoviment(false);
//		// movimentacaoPeixe.setDirectionMode(DirectionMode.PINGPONG);
//		//
//		// Direction movimentacaoPeixe2 = new
//		// Direction(DirectionPolicy.TWO_POINTS_DIRECTION);
//		// movimentacaoPeixe2.setInitialPosition(new Vector2(500,0));
//		// movimentacaoPeixe2.setFinalPosition(new Vector2(500,200));
//		// movimentacaoPeixe2.setInteligenceMoviment(false);
//		// movimentacaoPeixe2.setDirectionMode(DirectionMode.PINGPONG);
//
//		SineWave sineWave = new SineWave(200, 0.005, 2);
//		sineWave.setMaxX(70);
//
//		CGTEnemy peixe = new CGTEnemy();
//
//		Vector2 positionPeixe = new Vector2(800, 60);
//		peixe.setPosition(positionPeixe);
//
//		Rectangle coliderPeixe = new Rectangle(22, 0, 60, 75);
//		peixe.setCollision(coliderPeixe);
//
//		Rectangle tamanhoPeixe = new Rectangle(0, 0, 98, 90);
//		peixe.setBounds(tamanhoPeixe);
//
//		peixe.setState(StatePolicy.IDLEDOWN);
//		peixe.setLife(50);
//		peixe.setBlock(true);
//		peixe.setDestroyable(false);
//		peixe.setDamage(50);
//		peixe.addBehavior(sineWave);
//		peixe.setSpeed(-100);
//		peixe.setTimeToRecovery(0.5f);
//
//		peixe.setColideObject(criarAguaEfeito());
//		peixe.getObjectsToCollide().add(world.getObjectByLabel("rio"));
//
//		CGTSpriteSheet peixeSpriteSheet = new CGTSpriteSheet(
//				"data/chicken/peixe_spritesheet1.png");
//		peixeSpriteSheet.setRows(3);
//		peixeSpriteSheet.setColumns(5);
//
//		// Action
//		CGTAnimation movePeixeUp = new CGTAnimation(peixe, peixeSpriteSheet);
//		movePeixeUp.setInitialFrame(new Vector2(0, 0));
//		movePeixeUp.setEndingFrame(new Vector2(1, 1));
//		movePeixeUp.setSpriteVelocity(0.08f);
//		movePeixeUp.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation movePeixeDown = new CGTAnimation(peixe, peixeSpriteSheet);
//		movePeixeDown.setInitialFrame(new Vector2(0, 0));
//		movePeixeDown.setEndingFrame(new Vector2(1, 1));
//		movePeixeDown.setSpriteVelocity(0.08f);
//		movePeixeDown.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation damage = new CGTAnimation(peixe, peixeSpriteSheet);
//		damage.setInitialFrame(new Vector2(1, 2));
//		damage.setEndingFrame(new Vector2(3, 1));
//		damage.setSpriteVelocity(0.08f);
//		damage.setAnimationPolicy(PlayMode.LOOP);
//
//		peixe.addAnimation(StatePolicy.LOOKUP, movePeixeUp);
//		peixe.addAnimation(StatePolicy.LOOKDOWN, movePeixeDown);
//		peixe.addAnimation(StatePolicy.DAMAGE, damage);
//
//		world.getEnemies().add(peixe);
//
//		CGTEnemy peixe2 = new CGTEnemy();
//
//		Rectangle coliderPeixe2 = new Rectangle(22, 0, 60, 75);
//		peixe2.setCollision(coliderPeixe2);
//
//		Rectangle tamanhoPeixe2 = new Rectangle(0, 0, 98, 90);
//		peixe2.setBounds(tamanhoPeixe2);
//
//		peixe2.setState(StatePolicy.IDLEDOWN);
//		peixe2.setLife(50);
//		peixe2.setBlock(true);
//		peixe2.setDestroyable(false);
//		peixe2.setDamage(50);
//		peixe2.setPosition(new Vector2(800, 50));
//		SineWave sineWave2 = new SineWave(300, 0.005, 2);
//		sineWave.setMaxX(70);
//		peixe2.addBehavior(sineWave2);
//		peixe2.setSpeed(500);
//		peixe2.setTimeToRecovery(0.5f);
//		peixe2.setColideObject(criarAguaEfeito());
//
//		CGTSpriteSheet peixeSpriteSheet2 = new CGTSpriteSheet(
//				"data/chicken/peixe_spritesheet2.png");
//		peixeSpriteSheet2.setRows(3);
//		peixeSpriteSheet2.setColumns(5);
//
//		// Action
//		CGTAnimation movePeixeUp2 = new CGTAnimation(peixe2, peixeSpriteSheet2);
//		movePeixeUp2.setInitialFrame(new Vector2(0, 0));
//		movePeixeUp2.setEndingFrame(new Vector2(1, 1));
//		movePeixeUp2.setSpriteVelocity(0.08f);
//		movePeixeUp2.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation movePeixeDown2 = new CGTAnimation(peixe2,
//				peixeSpriteSheet2);
//		movePeixeDown2.setInitialFrame(new Vector2(0, 0));
//		movePeixeDown2.setEndingFrame(new Vector2(1, 1));
//		movePeixeDown2.setSpriteVelocity(0.08f);
//		movePeixeDown2.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation damage2 = new CGTAnimation(peixe2, peixeSpriteSheet2);
//		damage2.setInitialFrame(new Vector2(1, 2));
//		damage2.setEndingFrame(new Vector2(3, 1));
//		damage2.setSpriteVelocity(0.08f);
//		damage2.setAnimationPolicy(PlayMode.LOOP);
//
//		peixe2.addAnimation(StatePolicy.LOOKUP, movePeixeUp2);
//		peixe2.addAnimation(StatePolicy.LOOKDOWN, movePeixeDown2);
//		peixe2.addAnimation(StatePolicy.DAMAGE, damage2);
//
//		// world.getEnemies().add(peixe2);
//	}
//
//	public void configuracaoNuvens() {
//		Direction movimentacaoNuvem1 = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoNuvem1.setInitialPosition(new Vector2(-300, 400));
//		movimentacaoNuvem1.setFinalPosition(new Vector2(1110, 400));
//		movimentacaoNuvem1.setInteligenceMoviment(false);
//
//		Direction movimentacaoNuvem2 = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoNuvem2.setInitialPosition(new Vector2(1000, 200));
//		movimentacaoNuvem2.setFinalPosition(new Vector2(-200, 200));
//		movimentacaoNuvem2.setInteligenceMoviment(false);
//
//		Direction movimentacaoNuvem3 = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoNuvem3.setInitialPosition(new Vector2(1140, 500));
//		movimentacaoNuvem3.setFinalPosition(new Vector2(-280, 500));
//		movimentacaoNuvem3.setInteligenceMoviment(false);
//
//		CGTEnemy nuvem = new CGTEnemy();
//
//		Rectangle coliderNuvem = new Rectangle(22, 66, 250, 150);
//		nuvem.setCollision(coliderNuvem);
//
//		Rectangle tamanhoNuvem = new Rectangle(0, 0, 300, 300);
//		nuvem.setBounds(tamanhoNuvem);
//
//		nuvem.setBlock(false);
//		nuvem.setDestroyable(false);
//		nuvem.setState(StatePolicy.IDLEDOWN);
//		nuvem.addBehavior(movimentacaoNuvem1);
//		nuvem.setSpeed(10);
//
//		CGTSpriteSheet nuvemSpriteSheet = new CGTSpriteSheet(
//				"data/chicken/nuvem_0000_nuvem10_vectorized.png");
//		nuvemSpriteSheet.setRows(1);
//		nuvemSpriteSheet.setColumns(1);
//
//		CGTAnimation moveNuvemLeft = new CGTAnimation(nuvem, nuvemSpriteSheet);
//		moveNuvemLeft.setSpriteLine(1);
//		moveNuvemLeft.setSpriteVelocity(0.08f);
//		moveNuvemLeft.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation moveNuvemRight = new CGTAnimation(nuvem, nuvemSpriteSheet);
//		moveNuvemRight.setSpriteLine(1);
//		moveNuvemRight.setSpriteVelocity(0.08f);
//		moveNuvemRight.setAnimationPolicy(PlayMode.LOOP);
//
//		nuvem.addAnimation(StatePolicy.LOOKLEFT, moveNuvemLeft);
//		nuvem.addAnimation(StatePolicy.LOOKRIGHT, moveNuvemRight);
//
//		world.getEnemies().add(nuvem);
//
//		CGTEnemy nuvem2 = new CGTEnemy();
//
//		Vector2 positionNuvem2 = new Vector2(500, 300);
//		nuvem2.setPosition(positionNuvem2);
//
//		Rectangle coliderNuvem2 = new Rectangle(100, 100, 100, 100);
//		nuvem2.setCollision(coliderNuvem2);
//
//		Rectangle tamanhoNuvem2 = new Rectangle(0, 0, 300, 300);
//		nuvem2.setBounds(tamanhoNuvem2);
//
//		nuvem2.setBlock(false);
//		nuvem2.setDestroyable(false);
//		nuvem2.setState(StatePolicy.IDLEDOWN);
//		nuvem2.addBehavior(movimentacaoNuvem2);
//		nuvem2.setSpeed(20);
//
//		CGTSpriteSheet nuvemSpriteSheet2 = new CGTSpriteSheet(
//				"data/chicken/nuvem_0001_007_vectorized.png");
//		nuvemSpriteSheet2.setRows(1);
//		nuvemSpriteSheet2.setColumns(1);
//
//		CGTAnimation moveNuvemLeft2 = new CGTAnimation(nuvem2,
//				nuvemSpriteSheet2);
//		moveNuvemLeft2.setSpriteLine(1);
//		moveNuvemLeft2.setSpriteVelocity(0.08f);
//		moveNuvemLeft2.setAnimationPolicy(PlayMode.LOOP);
//
//		CGTAnimation moveNuvemRight2 = new CGTAnimation(nuvem2,
//				nuvemSpriteSheet2);
//		moveNuvemRight2.setSpriteLine(1);
//		moveNuvemRight2.setSpriteVelocity(0.08f);
//		moveNuvemRight2.setAnimationPolicy(PlayMode.LOOP);
//
//		nuvem2.addAnimation(StatePolicy.LOOKLEFT, moveNuvemLeft2);
//		nuvem2.addAnimation(StatePolicy.LOOKRIGHT, moveNuvemRight2);
//
//		world.getEnemies().add(nuvem2);
//
//		// CGTEnemy nuvem3 = new CGTEnemy();
//		//
//		// Vector2 positionNuvem3 = new Vector2(200, 300);
//		// nuvem3.setPosition(positionNuvem3);
//		//
//		// Rectangle coliderNuvem3 = new Rectangle(22, 66, 250, 150);
//		// nuvem3.setCollision(coliderNuvem3);
//		//
//		// Rectangle tamanhoNuvem3 = new Rectangle(0, 0, 300, 300);
//		// nuvem3.setBounds(tamanhoNuvem3);
//		//
//		// nuvem3.setBlock(false);
//		// nuvem3.setDestroyable(false);
//		// nuvem3.setState(StatePolicy.IDLEDOWN);
//		// nuvem3.addBehavior(movimentacaoNuvem3);
//		// nuvem3.setSpeed(30);
//		//
//		// nuvem3.setSpriteSheet(new
//		// CGTSpriteSheet("data/chicken/nuvem_0000_nuvem10_vectorized.png"));
//		// nuvem3.getSpriteSheet().setRows(1);
//		// nuvem3.getSpriteSheet().setColumns(1);
//		//
//		// CGTAnimation moveNuvemRight3 = new CGTAnimation(nuvem3);
//		// moveNuvemRight3.setSpriteLine(1);
//		// moveNuvemRight3.addStatePolicy(StatePolicy.LOOKLEFT);
//		// moveNuvemRight3.setSpriteVelocity(0.08f);
//		// moveNuvemRight3.setAnimationPolicy(PlayMode.LOOP);
//		// nuvem3.getAnimarions().add(moveNuvemRight3);
//		//
//		// CGTAnimation moveNuvemLeft3 = new CGTAnimation(nuvem3);
//		// moveNuvemLeft3.setSpriteLine(1);
//		// moveNuvemLeft3.addStatePolicy(StatePolicy.LOOKRIGHT);
//		// moveNuvemLeft3.setSpriteVelocity(0.08f);
//		// moveNuvemLeft3.setAnimationPolicy(PlayMode.LOOP);
//		// nuvem3.getAnimarions().add(moveNuvemLeft3);
//		//
//		// //world.getEnemies().add(nuvem3);
//
//	}
//
//	public void configuracaoJumentos() {
//		Direction movimentacaoJumento = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoJumento.setInitialPosition(new Vector2(100, 720));
//		movimentacaoJumento.setFinalPosition(new Vector2(100, -100));
//		movimentacaoJumento.setInteligenceMoviment(false);
//
//		Direction movimentacaoJumento2 = new Direction(
//				DirectionPolicy.TWO_POINTS_DIRECTION);
//		movimentacaoJumento2.setInitialPosition(new Vector2(500, 720));
//		movimentacaoJumento2.setFinalPosition(new Vector2(500, 0));
//		movimentacaoJumento2.setInteligenceMoviment(false);
//
//		CGTEnemy jumento = new CGTEnemy();
//
//		Rectangle coliderJumento = new Rectangle(22, 0, 60, 70);
//		jumento.setCollision(coliderJumento);
//
//		Rectangle tamanhoJumento = new Rectangle(0, 0, 98, 90);
//		jumento.setBounds(tamanhoJumento);
//
//		jumento.setLife(50);
//		jumento.setBlock(true);
//		jumento.setDestroyable(false);
//		jumento.setDamage(50);
//		jumento.addBehavior(movimentacaoJumento);
//		jumento.setSpeed(100);
//		jumento.setColideObject(criarAguaEfeito());
//
//		CGTSpriteSheet jumentoSheet = new CGTSpriteSheet(
//				"data/chicken/jegue_1_sprite_sheet.png");
//		jumentoSheet.setRows(2);
//		jumentoSheet.setColumns(4);
//
//		// Action
//		CGTAnimation moveJumentoDown = new CGTAnimation(jumento, jumentoSheet);
//		moveJumentoDown.setInitialFrame(new Vector2(0, 0));
//		moveJumentoDown.setEndingFrame(new Vector2(1, 1));
//		moveJumentoDown.setSpriteVelocity(0.08f);
//		moveJumentoDown.setAnimationPolicy(PlayMode.LOOP);
//
//		jumento.addAnimation(StatePolicy.LOOKDOWN, moveJumentoDown);
//
//		world.getEnemies().add(jumento);
//
//		CGTEnemy jumento2 = new CGTEnemy();
//
//		Rectangle coliderJumento2 = new Rectangle(22, 0, 60, 70);
//		jumento2.setCollision(coliderJumento2);
//
//		Rectangle tamanhoJumento2 = new Rectangle(0, 0, 98, 90);
//		jumento2.setBounds(tamanhoJumento2);
//
//		jumento2.setLife(50);
//		jumento2.setBlock(true);
//		jumento2.setDestroyable(false);
//		jumento2.setDamage(50);
//		jumento2.addBehavior(movimentacaoJumento2);
//		jumento2.setSpeed(200);
//
//		jumento2.setColideObject(criarAguaEfeito());
//		CGTSpriteSheet jumentoSheet2 = new CGTSpriteSheet(
//				"data/chicken/jegue_2_sprite_sheet.png");
//		jumentoSheet2.setRows(2);
//		jumentoSheet2.setColumns(4);
//
//		// Action
//		CGTAnimation moveJumentoDown2 = new CGTAnimation(jumento2,
//				jumentoSheet2);
//		moveJumentoDown2.setInitialFrame(new Vector2(0, 0));
//		moveJumentoDown2.setEndingFrame(new Vector2(1, 1));
//		moveJumentoDown2.setSpriteVelocity(0.08f);
//		moveJumentoDown2.setAnimationPolicy(PlayMode.LOOP);
//
//		jumento2.addAnimation(StatePolicy.LOOKDOWN, moveJumentoDown2);
//
//		world.getEnemies().add(jumento2);
//	}
//
//	public void configuracaoPorcentagem() {
//		CGTLabel labelHUD = new CGTLabel("fonts/myfont.TTF", "x");
//		labelHUD.setRelativeWidth(0.05f);
//		labelHUD.setRelativeHeight(0.05f);
//		labelHUD.setRelativeX(0.45f);
//		labelHUD.setRelativeY(0.9f);
//
//		world.addHUDComponent(labelHUD);
//
//		world.addWinCriterion(new CompleteCrossing(world.getId(), new Rectangle(world
//				.getBackground().getTextureGDX().getWidth()
//				- 1f / 100f * world.getBackground().getTextureGDX().getWidth(),
//				1,
//				1f / 100f * world.getBackground().getTextureGDX().getWidth(),
//				world.getBackground().getTextureGDX().getHeight()), labelHUD));
//
//	}
//
//	public void opositeCalcada() {
//
//		CGTOpposite calcada = new CGTOpposite();
//
//		Vector2 position = new Vector2(0, 100);
//		calcada.setPosition(position);
//		Rectangle bounds = new Rectangle(0, 0, 76, 52);
//		calcada.setBounds(bounds);
//		calcada.setCollision(bounds);
//
//		calcada.setBlock(true);
//		calcada.setDestroyable(false);
//		calcada.setLife(0);
//
//		world.getOpposites().add(calcada);
//	}
//
//	public void opositeRio() {
//		CGTOpposite rio = new CGTOpposite();
//
//		Vector2 position = new Vector2(0, 80);
//		rio.setPosition(position);
//		Rectangle bounds = new Rectangle(0, 0, 1140, 1);
//		rio.setBounds(bounds);
//		rio.setCollision(bounds);
//
//		rio.setBlock(true);
//		rio.setDestroyable(false);
//		rio.setLife(0);
//		rio.setLabel("rio");
//
//		world.getOpposites().add(rio);
//
//	}
//
//	public CGTAddOn criarAguaEfeito() {
//
//		CGTAddOn agua = new CGTAddOn();
//
//		Vector2 positionagua = new Vector2(100, 80);
//		agua.setPosition(positionagua);
//		Rectangle boundsagua = new Rectangle(0, 0, 245, 169);
//		agua.setBounds(boundsagua);
//		agua.setCollision(boundsagua);
//		agua.setPositionRelativeToParent(new Vector2(-100, 0));
//
//		agua.setLife(0);
//
//		CGTSpriteSheet aguaSheet = new CGTSpriteSheet(
//				"data/chicken/agua_spritesheet.png");
//		aguaSheet.setRows(5);
//		aguaSheet.setColumns(4);
//
//		// Action
//		CGTAnimation moveJumentoDown = new CGTAnimation(agua, aguaSheet);
//		moveJumentoDown.setInitialFrame(new Vector2(0, 0));
//		moveJumentoDown.setEndingFrame(new Vector2(1, 4));
//		moveJumentoDown.setSpriteVelocity(0.08f);
//		moveJumentoDown.setAnimationPolicy(PlayMode.NORMAL);
//
//		agua.addAnimation(StatePolicy.IDLE, moveJumentoDown);
//
//		return agua;
//	}
//
//	public void opositePinosCalcada() {
//		CGTOpposite pinos = new CGTOpposite();
//		pinos.setPosition(new Vector2(0, 0));
//		pinos.setBounds(new Rectangle(0, 0, 1140, 720));
//		CGTSpriteSheet spritePino = new CGTSpriteSheet(new CGTFile(
//				"data/chicken/parte_cima.png"));
//		spritePino.setRows(1);
//		spritePino.setColumns(1);
//		CGTAnimation animation = new CGTAnimation(pinos, spritePino);
//		animation.setSpriteLine(1);
//		pinos.addAnimation(StatePolicy.IDLE, animation);
//		world.addOpposite(pinos);
//	}
//
//	public void configuracaoPauseDialog() {
//		CGTDialog pauseDialog = new CGTDialog();
//		pauseDialog.setActive(false);
//		// pauseDialog.setWindow(new CGTTexture("data/dapexe/pause.png"));
//		// pauseDialog.setHorizontalBorderTexture(new
//		// CGTTexture("data/dapexe/borda.png"));
//		pauseDialog.setRightBottomCorner(new CGTTexture(
//				"data/chicken/base_di_ba.png"));
//		pauseDialog.setRelativeX(0.20f);
//		pauseDialog.setRelativeY(-0.60f);
//		pauseDialog.setRelativeWidth(0.6f);
//		pauseDialog.setRelativeHeight(0.6f);
//
//		CGTButton fechar = new CGTButtonScreen();
//		fechar.setTextureUp(new CGTTexture("data/chicken/play.png"));
//		fechar.setTextureDown(new CGTTexture("data/chicken/play.png"));
//		fechar.setRelativeX(0.15f);
//		fechar.setRelativeY(0.2f);
//		fechar.setRelativeWidth(0.2f);
//		fechar.setRelativeHeight(0.2f);
//
//		CGTButtonScreen menu = new CGTButtonScreen();
//		menu.setTextureUp(new CGTTexture("data/chicken/menu.png"));
//		menu.setTextureDown(new CGTTexture("data/chicken/menu.png"));
//		menu.setRelativeX(0.55f);
//		menu.setRelativeY(0.2f);
//		menu.setRelativeWidth(0.2f);
//		menu.setRelativeHeight(0.2f);
//
//		CGTButtonScreen reiniciar = new CGTButtonScreen();
//		reiniciar.setScreenToGo(world.getId());
//		reiniciar.setTextureUp(new CGTTexture("data/chicken/backButton.png"));
//		reiniciar.setTextureDown(new CGTTexture("data/chicken/backButton.png"));
//		reiniciar.setRelativeX(0.35f);
//		reiniciar.setRelativeY(0.2f);
//		reiniciar.setRelativeWidth(0.2f);
//		reiniciar.setRelativeHeight(0.2f);
//
//		pauseDialog.addButton(reiniciar);
//		pauseDialog.setCloseButton(fechar);
//		pauseDialog.addButton(menu);
//
//		world.setPauseDialog(pauseDialog);
//	}
//
//	public void configuracaoWinDialog() {
//		CGTDialog winDialog = new CGTDialog();
//		winDialog.setActive(false);
//		// pauseDialog.setWindow(new CGTTexture("data/dapexe/win_dialog.png"));
//		// pauseDialog.setHorizontalBorderTexture(new
//		// CGTTexture("data/dapexe/borda.png"));
//		winDialog.setRightBottomCorner(new CGTTexture(
//				"data/chicken/base_di_ba.png"));
//		winDialog.setRelativeX(0.20f);
//		winDialog.setRelativeY(-0.60f);
//		winDialog.setRelativeWidth(0.6f);
//		winDialog.setRelativeHeight(0.6f);
//
//		CGTButtonScreen tryAgain = new CGTButtonScreen();
//		tryAgain.setScreenToGo(world.getId());
//		tryAgain.setTextureUp(new CGTTexture(
//				"data/chicken/layout_perda_0000_Layer-2.png"));
//		tryAgain.setTextureDown(new CGTTexture(
//				"data/chicken/layout_perda_0000_Layer-2.png"));
//		tryAgain.setRelativeX(0.35f);
//		tryAgain.setRelativeY(0.55f);
//		tryAgain.setRelativeWidth(0.3f);
//		tryAgain.setRelativeHeight(0.15f);
//		winDialog.addButton(tryAgain);
//		world.setLoseDialog(winDialog);
//
//		CGTButtonScreen sair = new CGTButtonScreen();
//		sair.setScreenToGo(world.getId());
//		sair.setTextureUp(new CGTTexture(
//				"data/chicken/layout_perda_0002_Tentar-novamente-Menu-Sair.png"));
//		sair.setTextureDown(new CGTTexture(
//				"data/chicken/layout_perda_0002_Tentar-novamente-Menu-Sair.png"));
//		sair.setRelativeX(0.4f);
//		sair.setRelativeY(0.30f);
//		sair.setRelativeWidth(0.15f);
//		sair.setRelativeHeight(0.1f);
//		winDialog.addButton(sair);
//		world.setLoseDialog(winDialog);
//
//		world.setWinDialog(winDialog);
//	}
//
//	public void configuracaoLDialog() {
//		CGTDialog loseDialog = new CGTDialog();
//		loseDialog.setActive(false);
//		// loseDialog.setWindow(new CGTTexture("data/dapexe/lose_dialog.png"));
//		// loseDialog.setHorizontalBorderTexture(new
//		// CGTTexture("data/dapexe/borda.png"));
//		loseDialog.setRightBottomCorner(new CGTTexture(
//				"data/chicken/base_di_ba.png"));
//		loseDialog.setRelativeX(0.20f);
//		loseDialog.setRelativeY(-0.60f);
//		loseDialog.setRelativeWidth(0.6f);
//		loseDialog.setRelativeHeight(0.6f);
//
//		CGTButtonScreen tryAgain = new CGTButtonScreen();
//		tryAgain.setScreenToGo(world.getId());
//		tryAgain.setTextureUp(new CGTTexture(
//				"data/chicken/layout_perda_0000_Layer-2.png"));
//		tryAgain.setTextureDown(new CGTTexture(
//				"data/chicken/layout_perda_0000_Layer-2.png"));
//		tryAgain.setRelativeX(0.35f);
//		tryAgain.setRelativeY(0.55f);
//		tryAgain.setRelativeWidth(0.3f);
//		tryAgain.setRelativeHeight(0.15f);
//		loseDialog.addButton(tryAgain);
//		world.setLoseDialog(loseDialog);
//
//		CGTButtonScreen sair = new CGTButtonScreen();
//		sair.setScreenToGo("mundoGalinha");
//		sair.setTextureUp(new CGTTexture(
//				"data/chicken/layout_perda_0001_Layer-3.png"));
//		sair.setTextureDown(new CGTTexture(
//				"data/chicken/layout_perda_0001_Layer-3.png"));
//		sair.setRelativeX(0.4f);
//		sair.setRelativeY(0.30f);
//		sair.setRelativeWidth(0.15f);
//		sair.setRelativeHeight(0.1f);
//		loseDialog.addButton(sair);
//
//		world.setLoseDialog(loseDialog);
//
//	}
//
//	public void configuracaoDialogInicial() {
//		CGTDialog inicialDialog = new CGTDialog();
//		inicialDialog.setActive(false);
//		inicialDialog.setWindow(new CGTTexture("data/chicken/teste.png"));
//		// loseDialog.setHorizontalBorderTexture(new
//		// CGTTexture("data/dapexe/borda.png"));
//		inicialDialog.setRightBottomCorner(new CGTTexture(
//				"data/chicken/base_di_ba.png"));
//		inicialDialog.setRelativeX(0.1f);
//		inicialDialog.setRelativeY(0.1f);
//		inicialDialog.setRelativeWidth(0.8f);
//		inicialDialog.setRelativeHeight(0.8f);
//
//		// Button iniciar
//		CGTButton btn = new CGTButtonScreen();
//		btn.setRelativeX(0.39f);
//		btn.setRelativeY(0.50f);
//		btn.setRelativeWidth(0.20f);
//		btn.setRelativeHeight(0.1f);
//		CGTTexture texture = new CGTTexture(
//				"data/chicken/layout_principal_0001_Layer-4.png");
//		btn.setTextureDown(texture);
//		btn.setTextureUp(texture);
//		btn.setBounds(0, 0, texture.getWidth(), texture.getHeight());
//		// btn.setScreenToGo(world);
//		inicialDialog.setCloseButton(btn);
//
//		// Button tutorial
//		CGTButtonScreen buttonTutorial = new CGTButtonScreen();
//		buttonTutorial.setRelativeX(0.39f);
//		buttonTutorial.setRelativeY(0.35f);
//		buttonTutorial.setRelativeWidth(0.20f);
//		buttonTutorial.setRelativeHeight(0.1f);
//		CGTTexture textureButtonTutorial = new CGTTexture(
//				"data/chicken/layout_principal_0003_Layer-6.png");
//		buttonTutorial.setTextureDown(textureButtonTutorial);
//		buttonTutorial.setTextureUp(textureButtonTutorial);
//		// buttonTutorial.setScreenToGo(screenTutorial);
//		inicialDialog.addButton(buttonTutorial);
//
//		world.setInitialDialog(inicialDialog);
//
//	}
//
//	public void configuracaoInputs() {
//		Action walkUp = new Action(ActionMovePolicy.WALK_RIGHT, InputPolicy.TAP);
//		Action slideUp = new Action(ActionMovePolicy.WALK_UP,
//				InputPolicy.SLIDE_UP);
//		Action slideDown = new Action(ActionMovePolicy.WALK_DOWN,
//				InputPolicy.SLIDE_DOWN);
//
//		world.addAction(walkUp);
//	}
//
//	public MyWorldChicken() {
//
//		createWorld();
//	}
//
//	private void createWorld() {
//        game = CGTGame.get();
//		world = game.createWorld("mundoGalinha");
//		backGround = new CGTTexture("data/chicken/back.png");
//		world.setBackground(backGround);
//		world.setMusic(new CGTSound("data/AudioChicken/tema.ogg", 1f));
//		world.getCamera().setGameMode(GameModePolicy.TOUCH);
//
//		CGTActor personagemCGTActor = new CGTActor();
//
//		configuracaoActor(personagemCGTActor);
//
//		configuracaoActionActor(personagemCGTActor);
//
//		configuracaoVacas();
//
//		configuracaoNuvens();
//
//		opositeRio();
//
//		configuracaoPeixes();
//
//		configuracaoJumentos();
//
//		opositeCalcada();
//
//		opositePinosCalcada();
//		configuracaoPorcentagem();
//
//		world.setActor(personagemCGTActor);
//
//		configuracaoInputs();
//
//		CGTButtonStartGame a = new CGTButtonStartGame(0.1f, 0.1f, 0.9f, 0.9f);
//		CGTTexture t = new CGTTexture("data/chicken/teste.png");
//
//		a.setTextureUp(t);
//		a.setTextureDown(t);
//		world.setStartGame(a);
//		world.getCamera().setInitialHeight(0.5f);
//		world.getCamera().setVolumeOnFullCamera(0.1f);
//		world.getCamera().setInitialWidth(0.5f);
//		world.getCamera().setScale(2);
//
//		world.getLoseCriteria().add(new LifeDepleted(personagemCGTActor));
//
//		//
//		// // Button iniciar
//		// CGTTexture menuInicial = new
//		// CGTTexture("data/dapexe/menuInicial.png");
//		// CGTButtonScreen btn = new CGTButtonScreen();
//		// btn.setRelativeX(0.39f);
//		// btn.setRelativeY(0.50f);
//		// btn.setRelativeWidth(0.20f);
//		// btn.setRelativeHeight(0.1f);
//		// CGTTexture texture = new
//		// CGTTexture("data/chicken/layout_principal_0001_Layer-4.png");
//		// btn.setTextureDown(texture);
//		// btn.setTextureUp(texture);
//		// btn.setBounds(0, 0, texture.getWidth(), texture.getHeight());
//		// //btn.setScreenToGo(world);
//		// screen = new CGTScreen(menuInicial);
//		// screen.getButtons().add(btn);
//		//
//		// // Button tutorial
//		// CGTTexture textureTutorial = new
//		// CGTTexture("data/dapexe/menuTutorial.png");
//		// CGTButtonScreen buttonTutorial = new CGTButtonScreen();
//		// buttonTutorial.setRelativeX(0.39f);
//		// buttonTutorial.setRelativeY(0.45f);
//		// buttonTutorial.setRelativeWidth(0.20f);
//		// buttonTutorial.setRelativeHeight(0.1f);
//		// CGTTexture textureButtonTutorial = new
//		// CGTTexture("data/chicken/layout_principal_0003_Layer-6.png");
//		// buttonTutorial.setTextureDown(textureButtonTutorial);
//		// buttonTutorial.setTextureUp(textureButtonTutorial);
//		// buttonTutorial.setBounds(0, 0, textureButtonTutorial.getWidth(),
//		// textureButtonTutorial.getHeight());
//		// CGTScreen screenTutorial = new CGTScreen(textureTutorial);
//		// buttonTutorial.setScreenToGo(screenTutorial);
//		// screen.getButtons().add(buttonTutorial);
//		//
//		// // Label
//		// CGTLabel label = new
//		// CGTLabel("data/chicken/layout_principal_0000_Layer-3", "");
//		// label.setRelativeX(0.39f);
//		// label.setRelativeY(0.78f);
//		// label.setRelativeWidth(0.20f);
//		// label.setRelativeHeight(0.1f);
//		// screen.getLabels().add(label);
//
//		game.setStartWindowId(world.getId());
//		configuracaoPauseDialog();
//		configuracaoWinDialog();
//		configuracaoLDialog();
//		configuracaoDialogInicial();
//	}
//
//	public CGTGameWorld getWorld() {
//		return world;
//	}
//
//	public CGTGame getGame() {
//
//		return game;
//		// return CGTGame.getSavedGame();
//
//	}
//
//}
