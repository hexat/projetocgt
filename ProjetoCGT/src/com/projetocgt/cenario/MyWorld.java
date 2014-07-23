package com.projetocgt.cenario;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.UnsupportedAudioFileException;

import cgt.behaviors.Direction;
import cgt.behaviors.Fade;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.core.CGTOpposite;
import cgt.core.CGTProjectile;
import cgt.policy.ActionMovePolicy;
import cgt.policy.AnimationPolicy;
import cgt.policy.DirectionPolicy;
import cgt.policy.FadePolicy;
import cgt.policy.InputPolicy;
import cgt.policy.StatePolicy;
import cgt.unit.Action;
import cgt.unit.ActionCreator;
import cgt.unit.ActionMove;
import cgt.util.CGTSpriteSheet;
import cgt.util.CGTTexture;
import cgt.util.Collision;
import cgt.util.Position;
import cgt.util.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.ActorCGT;
import com.projetocgt.personagens.Bonus;
import com.projetocgt.personagens.Enemy;
import com.projetocgt.personagens.Opposite;
import com.projetocgt.personagens.Projectile;
import com.projetocgt.util.ProjectileOrientation;

/**
 * Responsavel por construir o jogo
 * @author Bruno Roberto
 *
 */
public class MyWorld {

	ArrayList<Opposite> listaDeOpposite = new ArrayList<Opposite>();
	ArrayList<Bonus> listaDeBonus = new ArrayList<Bonus>();
	ArrayList<Enemy> listaDeEnemy = new ArrayList<Enemy>();
	ArrayList<Action> listaDeAction = new ArrayList<Action>();
	private ActorCGT personagemActorLIB;
	private Texture backGround;
	
	public MyWorld() {
		createWorld();
	}
	/**
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		backGround = new Texture(Gdx.files.internal("data/Cenario/asfalto_grama_sprite_sheet.png"));
		//backGround = new CGTTexture(Gdx.files.internal("data/Cenario/pista1280.png"));
		
		
		CGTActor personagemCGTActor = new CGTActor();
		personagemCGTActor.setPosition(new Position(800f,900f));
		
		Collision coliderPersonagem = new Collision(60f, 60f);
		personagemCGTActor.setCollision(coliderPersonagem);
		
		Rectangle tamanhoPersonagem = new Rectangle(80, 80);
		personagemCGTActor.setBounds(tamanhoPersonagem);
		
		personagemCGTActor.setLife(4);
		personagemCGTActor.setSpeed(180);
		
		try {
			personagemCGTActor.setSpriteSheet(new CGTSpriteSheet(new CGTTexture(Gdx.files.internal("data/SpriteCGTActor/SpriteSheet_bombeiro.png").file())));
			personagemCGTActor.getSpriteSheet().setRows(5);
			personagemCGTActor.getSpriteSheet().setColumns(3);
		} catch (FileNotFoundException e) {
			System.out.println("Caminho errado");
		}
		
		Sound somDamegePersonagem;
		try {
			somDamegePersonagem = new Sound("data/AudioBombeiro/colisao.wav");
			personagemCGTActor.setSoundCollision(somDamegePersonagem);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		Sound somDiePersonagem;
		try {
			somDiePersonagem = new Sound("data/AudioBombeiro/colisao.wav");
			personagemCGTActor.setSoundDie(somDiePersonagem);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Action
		ActionMove moveLEft = ActionCreator.getInstance().newActionMove(ActionMovePolicy.WALK_LEFT, personagemCGTActor);
		moveLEft.setSpriteLine(1);
		moveLEft.setStatePolicy(StatePolicy.LOOKLEFT);
		moveLEft.setNumberOfColumns(3);
		moveLEft.addInput(InputPolicy.ACEL_LEFT);
		moveLEft.setSpriteVelocity(0.08f);
		moveLEft.setFlip(true);
		moveLEft.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		ActionMove moveRight = ActionCreator.getInstance().newActionMove(ActionMovePolicy.WALK_RIGHT, personagemCGTActor);
		moveRight.setSpriteLine(1);
		moveRight.setStatePolicy(StatePolicy.LOOKRIGHT);
		moveRight.setNumberOfColumns(3);
		moveRight.addInput(InputPolicy.ACEL_RIGHT);
		moveRight.setSpriteVelocity(0.2f);
		moveRight.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		ActionMove moveUp = ActionCreator.getInstance().newActionMove(ActionMovePolicy.WALK_UP, personagemCGTActor);
		moveUp.setSpriteLine(3);
		moveUp.setStatePolicy(StatePolicy.LOOKUP);
		moveUp.setNumberOfColumns(3);
		moveUp.addInput(InputPolicy.ACEL_UP);
		moveUp.setSpriteVelocity(0.2f);
		moveUp.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		ActionMove moveDown = ActionCreator.getInstance().newActionMove(ActionMovePolicy.WALK_DOWN, personagemCGTActor);
		moveDown.setSpriteLine(2);
		moveDown.setStatePolicy(StatePolicy.LOOKDOWN);
		moveDown.setNumberOfColumns(3);
		moveDown.addInput(InputPolicy.ACEL_DOWN);
		moveDown.setSpriteVelocity(0.2f);
		moveDown.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		//Adicionando o personagem na libGDX
		personagemActorLIB = new ActorCGT(personagemCGTActor);
		
		//-------------------------------------------------------------------------------//
//		personagemActorLIB = new ActorCGT(new Vector2(800,900), 100f, 100f, 80f, 10f, 10f);
//		personagemActorLIB.setSpeed(180);
//		personagemActorLIB.setLife(3);
//		SpriteSheet spriteSheetActor = new SpriteSheet();
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
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				CGTOpposite opositorCasa = new CGTOpposite();
				

				ActionMove teste = ActionCreator.getInstance().newActionMove(opositorCasa);
				teste.setSpriteLine(1);
				teste.setStatePolicy(StatePolicy.IDLE);
				teste.setNumberOfColumns(1);
				teste.setAnimationPolicy(AnimationPolicy.LOOP);
				
				//new Vector2(450*(i+1)+i*20,400*(j+1)+j*90), 300, 300, 300, 0, 0
				
				Rectangle bounds = new Rectangle(300, 300);
				opositorCasa.setBounds(bounds);
				opositorCasa.setCollision(new Collision(bounds));
				
				Position position = new Position(450*(i+1)+i*20, 400*(j+1)+j*90);
				opositorCasa.setPosition(position);
				
				//opositorCasa.setTexture(new CGTTexture("data/Cenario/casa_sprite_sheet.png"));
				opositorCasa.setBlock(true);
				opositorCasa.setDestroyable(false);
				opositorCasa.setLife(0);
				try {
					opositorCasa.setSpriteSheet(new CGTSpriteSheet(new CGTTexture(new File("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png"))));
					opositorCasa.getSpriteSheet().setRows(1);
					opositorCasa.getSpriteSheet().setColumns(1);
				} catch (FileNotFoundException e) {
					System.out.println("Caminho errado");
				}
				
				//Indica que a minha animacao e' um por um
				//opositorCasa.getSpriteSheet().loadingSpriteSheet("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png", 1, 1);
				Opposite opositorCasaLib = new Opposite(opositorCasa);
				listaDeOpposite.add(opositorCasaLib);				
			}
		}
		Fade fade = new Fade(FadePolicy.FADE_IN);
		fade.setFadeInTime(1);
//		
//		Sine sine = new Sine(MovementPolicy.HEIGHT);
//		sine.setMax(100);
//		sine.setMin(50);
//		sine.setAtFirstStep(true);
//		
		CGTEnemy enemyFogoCGT = new CGTEnemy();
		
		Position positionEnemy = new Position(480,850);
		enemyFogoCGT.setPosition(positionEnemy);
		
		Collision coliderEnemy = new Collision(50, 50);
		enemyFogoCGT.setCollision(coliderEnemy);
		
		Rectangle tamanhoEnemy = new Rectangle(50, 50);
		enemyFogoCGT.setBounds(tamanhoEnemy);
		
		enemyFogoCGT.setBlock(false);
		enemyFogoCGT.setDamage(1);
		//enemyFogoCGT.setSpeed(2);
		enemyFogoCGT.setDestroyable(true);
		enemyFogoCGT.addBehavior(fade);
		//enemyFogoCGT.addBehavior(sine);
		enemyFogoCGT.setLife(50);
		enemyFogoCGT.setInterval(4);
		
		try {
			enemyFogoCGT.setSpriteSheet(new CGTSpriteSheet(new CGTTexture(Gdx.files.internal("data/CGTOpposite/SpriteSheet_fogo.png").file())));
			enemyFogoCGT.getSpriteSheet().setRows(2);
			enemyFogoCGT.getSpriteSheet().setColumns(2);
		} catch (FileNotFoundException e) {
			System.out.println("Caminho errado");
		}
		
		//Action
		ActionMove moveEnemy = ActionCreator.getInstance().newActionMove(enemyFogoCGT);
		moveEnemy.setSpriteLine(1);
		moveEnemy.setStatePolicy(StatePolicy.IDLE);
		moveEnemy.setNumberOfColumns(2);
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveEnemy.setSpriteVelocity(0.08f);
		moveEnemy.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		Enemy enemyFogoLIB = new Enemy(enemyFogoCGT);
		enemyFogoLIB.getSpriteSheet().setLoop(true);
		//Add na lista de enemy
		listaDeEnemy.add(enemyFogoLIB);
		
//		//Instancia o opposite fogo
//		Enemy enemyFogo = new Enemy(new Vector2(400,850), 50, 50, 50, 0, 0);
//		enemyFogo.setBlock(false);
//		enemyFogo.setDamage(1);
//		enemyFogo.setSpeed(2);
//		enemyFogo.setDestroyable(true);
//		enemyFogo.addBehavior(fade);
//		enemyFogo.addBehavior(sine);
//		enemyFogo.setLife(50);
//		enemyFogo.setSpriteSheet(new SpriteSheet());
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
//		enemyFogo2.setSpriteSheet(new SpriteSheet());
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
//		enemyFogo3.setSpriteSheet(new SpriteSheet());
////		enemyFogo3.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
//		listaDeEnemy.add(enemyFogo3);
//		
//		Direction direction = new Direction(DirectionPolicy.LEFT_AND_RIGHT);
//		direction.setMaxX(800);
//		direction.setMinX(330);
//		
		Direction directionUp = new Direction(DirectionPolicy.UP_AND_DOWN);
		directionUp.setMaxY(2000);
		directionUp.setMinY(200);
//		
//
//		Direction directionFour = new Direction(DirectionPolicy.FOUR_DIRECTION);
//		directionFour.setMaxY(600);
//		directionFour.setMinY(400);
//		directionFour.setMaxX(1600);
//		directionFour.setMinX(1130);
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
		fadeCar.setFadeInTime(2);
//		
		CGTEnemy carroCGT = new CGTEnemy();
		
		Position positionCarro = new Position(800,700);
		carroCGT.setPosition(positionCarro);
		
		Collision coliderCarro = new Collision(80, 80);
		carroCGT.setCollision(coliderCarro);
		
		Rectangle tamanhoCarro = new Rectangle(80, 80);
		carroCGT.setBounds(tamanhoCarro);
		
		carroCGT.setBlock(true);
		carroCGT.setDestroyable(true);
		carroCGT.setDamage(10);
		carroCGT.addBehavior(fadeCar);
		carroCGT.addBehavior(directionUp);
		carroCGT.setSpeed(200);
		
		try {
			carroCGT.setSpriteSheet(new CGTSpriteSheet(new CGTTexture(Gdx.files.internal("data/Enemy/Carro.png").file())));
			carroCGT.getSpriteSheet().setRows(1);
			carroCGT.getSpriteSheet().setColumns(1);
		} catch (FileNotFoundException e) {
			System.out.println("Caminho errado");
		}
		
		//Action
		ActionMove moveCarro = ActionCreator.getInstance().newActionMove(carroCGT);
		moveCarro.setSpriteLine(1);
		moveCarro.setStatePolicy(StatePolicy.IDLE);
		moveCarro.setNumberOfColumns(1);
		//moveEnemy.addInput(InputPolicy.ACEL_LEFT);
		moveCarro.setSpriteVelocity(0.08f);
		moveCarro.setAnimationPolicy(AnimationPolicy.LOOP_PINGPONG);
		
		Enemy enemyCarroLIB = new Enemy(carroCGT);
		enemyCarroLIB.getSpriteSheet().setLoop(true);
		//Add na lista de enemy
		listaDeEnemy.add(enemyCarroLIB);
		
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
//		carro.setSpriteSheet(new SpriteSheet());
////		carro.getSpriteSheet().loadingSpriteSheet("data/Enemy/Carro.png", 1, 1);
//		listaDeEnemy.add(carro);
		
		Bonus hidrate = new Bonus(new Vector2(1000,800),50, 50, 50, 0, 0);
		hidrate.setTexture(new Texture("data/CGTBonus/SpriteSheet_tubo.png"));
		listaDeBonus.add(hidrate);
		
		CGTProjectile projetilAguaCGT = new CGTProjectile();
		Position position = new Position(100f,200f);
		projetilAguaCGT.setPosition(position);
		projetilAguaCGT.setBounds(new Rectangle(30, 30));
		Collision coliderProjectile = new Collision(30, 30);
		Position positionRelative = new Position(0,0);
		coliderProjectile.setPositionRelativeToObject(positionRelative);
		projetilAguaCGT.setCollision(coliderProjectile);
		projetilAguaCGT.setInterval(1);

		CGTSpriteSheet css = new CGTSpriteSheet(new CGTTexture("data/CGTProjectile/SpriteSheet_agua.png"));
		css.setRows(2);
		css.setColumns(2);
		projetilAguaCGT.setSpriteSheet(css);
		projetilAguaCGT.setAmmo(4);
		
		ActionMove m = ActionCreator.getInstance().newActionMove(projetilAguaCGT);
		m.setSpriteLine(1);
		m.setFlip(true);
		m.setStatePolicy(StatePolicy.LOOKLEFT);
		m.setNumberOfColumns(2);
		m.setSpriteVelocity(0.1f);
		m.setAnimationPolicy(AnimationPolicy.LOOP);
		
		ActionMove a = ActionCreator.getInstance().newActionMove(projetilAguaCGT);
		a.setSpriteLine(1);
		a.setFlip(false);
		a.setStatePolicy(StatePolicy.LOOKRIGHT);
		a.setNumberOfColumns(2);
		a.setSpriteVelocity(0.08f);
		a.setAnimationPolicy(AnimationPolicy.LOOP);
		
		ActionMove down = ActionCreator.getInstance().newActionMove(projetilAguaCGT);
		down.setSpriteLine(2);
		down.setFlip(false);
		down.setStatePolicy(StatePolicy.LOOKDOWN);
		down.setNumberOfColumns(2);
		down.setSpriteVelocity(0.08f);
		down.setAnimationPolicy(AnimationPolicy.LOOP);
		
		ActionMove up = ActionCreator.getInstance().newActionMove(projetilAguaCGT);
		up.setSpriteLine(2);
		up.setFlip(true);
		up.setStatePolicy(StatePolicy.LOOKUP);
		up.setNumberOfColumns(2);
		up.setSpriteVelocity(0.08f);
		up.setAnimationPolicy(AnimationPolicy.LOOP);
		Projectile projetilAgua = new Projectile(projetilAguaCGT);
		projetilAgua.getSpriteSheet().setLoop(true);
//		projetilAgua.setSpriteSheet(new SpriteSheet());
		//Indica que a minha animacao e' um por um
		//projetilAgua.setActionFire(ActionCreator.getInstance().newActionFire(ActionFirePolicy.FIRE));
		//projetilAgua.getActionFire().addInput(InputPolicy.GO_TAP);
//		projetilAgua.getVelocityInitial().x= 100f;
		//projetilAgua.setInterval(1);
		//projetilAgua.setAmmo(100);
		//projetilAgua.setAmmo(4);
//		projetilAgua.getSpriteSheet().loadingSpriteSheet("data/CGTProjectile/SpriteSheet_agua.png", 2, 2);
		
		ProjectileOrientation direcaoRight = new ProjectileOrientation();
		direcaoRight.setPositionRetativeToGameObject(new Vector2(60f,15f));
		direcaoRight.setSpriteLine(1);
		direcaoRight.setSpriteNumberOfColumns(2);
		direcaoRight.setSpriteVelocity(2);
		direcaoRight.setState(StatePolicy.LOOKRIGHT);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoRight);
		
		ProjectileOrientation direcaoLeft = new ProjectileOrientation();
		direcaoLeft.setPositionRetativeToGameObject(new Vector2(-20f, 15f));
		direcaoLeft.setSpriteLine(1);
		direcaoLeft.setSpriteNumberOfColumns(2);
		direcaoLeft.setSpriteVelocity(2);
		direcaoLeft.setState(StatePolicy.LOOKLEFT);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoLeft);
		
		ProjectileOrientation direcaoUp = new ProjectileOrientation();
		direcaoUp.setPositionRetativeToGameObject(new Vector2(40f, 80f));
		direcaoUp.setSpriteLine(2);
		direcaoUp.setSpriteNumberOfColumns(2);
		direcaoUp.setSpriteVelocity(2);
		direcaoUp.setState(StatePolicy.LOOKUP);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoUp);
		
		ProjectileOrientation direcaoDown = new ProjectileOrientation();
		direcaoDown.setPositionRetativeToGameObject(new Vector2(40f, -10f));
		direcaoDown.setSpriteLine(2);
		direcaoDown.setSpriteNumberOfColumns(2);
		direcaoDown.setSpriteVelocity(2);
		direcaoDown.setState(StatePolicy.LOOKDOWN);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoDown);
		
		personagemActorLIB.getListaDeProjectiles().add(projetilAgua);
	}

	/**
	 * @return the backGround
	 */
	public Texture getBackGround() {
		return backGround;
	}
	/**
	 * @param backGround the backGround to set
	 */
	public void setBackGround(Texture backGround) {
		this.backGround = backGround;
	}
	
	/**
	 * @return the listaDeOpposite
	 */
	public ArrayList<Opposite> getListaDeOpposite() {
		return listaDeOpposite;
	}

	/**
	 * @param listaDeOpposite the listaDeOpposite to set
	 */
	public void setListaDeOpposite(ArrayList<Opposite> listaDeOpposite) {
		this.listaDeOpposite = listaDeOpposite;
	}
	/**
	 * @return the personagem
	 */
	public ActorCGT getPersonagem() {
		return personagemActorLIB;
	}
	
	/**
	 * @return the listaDeBonus
	 */
	public ArrayList<Bonus> getListaDeBonus() {
		return listaDeBonus;
	}
	/**
	 * @param listaDeBonus the listaDeBonus to set
	 */
	public void setListaDeBonus(ArrayList<Bonus> listaDeBonus) {
		this.listaDeBonus = listaDeBonus;
	}
	/**
	 * @return the listaDeEnemy
	 */
	public ArrayList<Enemy> getListaDeEnemy() {
		return listaDeEnemy;
	}
	/**
	 * @param listaDeEnemy the listaDeEnemy to set
	 */
	public void setListaDeEnemy(ArrayList<Enemy> listaDeEnemy) {
		this.listaDeEnemy = listaDeEnemy;
	}
}
