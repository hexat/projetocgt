package com.projetocgt.cenario;

import java.util.ArrayList;

import cgt.behaviors.Direction;
import cgt.policy.DirectionPolicy;
import cgt.policy.StatePolicy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.progetocgt.util.ProjectileOrientation;
import com.projetocgt.personagens.ActorCGT;
import com.projetocgt.personagens.Bonus;
import com.projetocgt.personagens.Enemy;
import com.projetocgt.personagens.Opposite;
import com.projetocgt.personagens.Projectile;
import com.projetocgt.personagens.SpriteSheet;
/**
 * Responsavel por construir o jogo
 * @author Bruno Roberto
 *
 */
public class MyWorld {
	
	ArrayList<ActorCGT> listaActor = new ArrayList<ActorCGT>();
	ArrayList<Projectile> listaDeProjectile = new ArrayList<Projectile>();
	ArrayList<Opposite> listaDeOpposite = new ArrayList<Opposite>();
	ArrayList<Bonus> listaDeBonus = new ArrayList<Bonus>();
	ArrayList<Enemy> listaDeEnemy = new ArrayList<Enemy>();
	private ActorCGT personagemActor;
	private Texture backGround;
	
	public MyWorld() {
		createWorld();
	}
	/**
	 * Recebe os paramentros do jogos
	 */
	private void createWorld() {
		
		backGround = new Texture(Gdx.files.internal("data/Cenario/asfalto_grama_sprite_sheet.png"));
		
		personagemActor = new ActorCGT(new Vector2(330, 400), 100f, 100f, 80f, 10f, 10f);
		personagemActor.setSpeed(180);
		personagemActor.setLife(3);
		SpriteSheet spriteSheetActor = new SpriteSheet();
		personagemActor.setSpriteSheet(spriteSheetActor );
		spriteSheetActor.setLinhaDoSpriteUp(3);
		spriteSheetActor.setLinhaDoSpriteDown(2);
		spriteSheetActor.setLinhaDoSpriteLeft(1);
		spriteSheetActor.setLinhaDoSpriteRight(1);
		spriteSheetActor.loadSpriteActorCGT("data/SpriteCGTActor/SpriteSheet_bombeiro.png",5,3);
		
		/* Esse opposite nao tem animacao, seria melhor adicionar uma textura do que uma animacao 
		 * um por um.
		 */
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Opposite opositorCasa = new Opposite(new Vector2(450*(i+1)+i*20,400*(j+1)+j*90), 300, 300, 300, 0, 0);
				//opositorCasa.setTexture(new Texture("data/Cenario/casa_sprite_sheet.png"));
				opositorCasa.setBlock(true);
				opositorCasa.setDestroyable(false);
				opositorCasa.setLife(0);
				opositorCasa.setSpriteSheet(new SpriteSheet());
				//Indica que a minha animacao e' um por um
				opositorCasa.getSpriteSheet().loadingSpriteSheet("data/Cenario/casas/casa_sprite_sheet"+i+""+j+".png", 1, 1);
				listaDeOpposite.add(opositorCasa);				
			}
		}
		//Instancia o opposite fogo
		Enemy enemyFogo = new Enemy(new Vector2(200,850), 50, 50, 50, 0, 0);
		//opositorFogo.setTexture(new Texture("data/CGTOpposite/SpriteSheet_fogo.png"));
		enemyFogo.setBlock(false);
		enemyFogo.setDamage(1);
		enemyFogo.setLife(50);
		enemyFogo.setSpriteSheet(new SpriteSheet());
		enemyFogo.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		listaDeEnemy.add(enemyFogo);
		
		//Instancia o opposite fogo
		Enemy enemyFogo2 = new Enemy(new Vector2(200,1050), 50, 50, 50, 0, 0);
		//opositorFogo.setTexture(new Texture("data/CGTOpposite/SpriteSheet_fogo.png"));
		enemyFogo2.setBlock(true);
		enemyFogo2.setDamage(1);
		enemyFogo2.setLife(200);
		enemyFogo2.setSpriteSheet(new SpriteSheet());
		enemyFogo2.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		listaDeEnemy.add(enemyFogo2);
		
		//Instancia o opposite fogo
		Enemy enemyFogo3 = new Enemy(new Vector2(200,1500), 50, 50, 50, 0, 0);
		//opositorFogo.setTexture(new Texture("data/CGTOpposite/SpriteSheet_fogo.png"));
		enemyFogo3.setBlock(true);
		enemyFogo3.setDamage(2);
		enemyFogo3.setLife(100);
		enemyFogo3.setSpriteSheet(new SpriteSheet());
		enemyFogo3.getSpriteSheet().loadingSpriteSheet("data/CGTOpposite/SpriteSheet_fogo.png", 2, 2);
		listaDeEnemy.add(enemyFogo3);
		
		
		Direction direction = new Direction(DirectionPolicy.UP_AND_DOWN);
		direction.setMaxSpeed(2);
		
		//Instancia o opposite fogo
		Enemy carro = new Enemy(new Vector2(800,1050), 50, 50, 50, 0, 0);
		carro.setBlock(true);
		carro.setDestroyable(false);
		carro.setDamage(10);
		carro.addBehavior(direction);
		//carro.setLife(200);
		carro.setSpriteSheet(new SpriteSheet());
		carro.getSpriteSheet().loadingSpriteSheet("data/Enemy/Carro.png", 1, 1);
		listaDeEnemy.add(carro);
		
		Bonus hidrate = new Bonus(new Vector2(800,800),50, 50, 50, 0, 0);
		hidrate.setTexture(new Texture("data/CGTBonus/SpriteSheet_tubo.png"));
		listaDeBonus.add(hidrate);
		
		Projectile projetilAgua = new Projectile(new Vector2(100f, 200f), 30, 30, 30, 0, 0);
		//projetilAgua.setTexture(new Texture("data/CGTProjectile/SpriteSheet_agua.png"));
		projetilAgua.setSpriteSheet(new SpriteSheet());
		//Indica que a minha animacao e' um por um
		//projetilAgua.setActionFire(ActionCreator.getInstance().newActionFire(ActionFirePolicy.FIRE));
		//projetilAgua.getActionFire().addInput(InputPolicy.GO_TAP);
		projetilAgua.getVelocityInitial().x= 100f;
		projetilAgua.setInterval(1);
		projetilAgua.setAmmo(4);
		projetilAgua.getSpriteSheet().loadingSpriteSheet("data/CGTProjectile/SpriteSheet_agua.png", 2, 2);
		
		ProjectileOrientation direcaoRight = new ProjectileOrientation();
		//direcaoRight.setPosition(new Vector2(900f, 900f));
		direcaoRight.setPositionRetativeToGameObject(new Vector2(90,33));
		direcaoRight.setSpriteLine(2);
		direcaoRight.setSpriteNumberOfColumns(2);
		direcaoRight.setSpriteVelocity(2);
		direcaoRight.setState(StatePolicy.LOOKRIGHT);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoRight);
		
		ProjectileOrientation direcaoLeft = new ProjectileOrientation();
		//direcaoLeft.setPosition(new Vector2(920f, 900f));
		direcaoLeft.setPositionRetativeToGameObject(new Vector2(-20f, 33f));
		direcaoLeft.setSpriteLine(2);
		direcaoLeft.setSpriteNumberOfColumns(2);
		direcaoLeft.setSpriteVelocity(2);
		direcaoLeft.setState(StatePolicy.LOOKLEFT);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoLeft);
		
		ProjectileOrientation direcaoUp = new ProjectileOrientation();
		//direcaoLeft.setPosition(new Vector2(920f, 900f));
		direcaoUp.setPositionRetativeToGameObject(new Vector2(40f, 80f));
		direcaoUp.setSpriteLine(2);
		direcaoUp.setSpriteNumberOfColumns(2);
		direcaoUp.setSpriteVelocity(2);
		direcaoUp.setState(StatePolicy.LOOKUP);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoUp);
		
		ProjectileOrientation direcaoDown = new ProjectileOrientation();
		//direcaoLeft.setPosition(new Vector2(920f, 900f));
		direcaoDown.setPositionRetativeToGameObject(new Vector2(40f, -10f));
		direcaoDown.setSpriteLine(2);
		direcaoDown.setSpriteNumberOfColumns(2);
		direcaoDown.setSpriteVelocity(2);
		direcaoDown.setState(StatePolicy.LOOKDOWN);
		projetilAgua.getListaDeProjectileOrientation().add(direcaoDown);
		
		listaDeProjectile.add(projetilAgua);
	}

	private void createWorld(String fileCGT){
		//CGTGameWorld world = CGTGameWorld.lerStream(fileCGT);
		//personagemActor = new ActorCGT(world.getSprite());
		//for
		//inimigo = new inimigo()
		
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
	 * @return the listaDeProjectili
	 */
	public ArrayList<Projectile> getListaDeProjectili() {
		return listaDeProjectile;
	}

	/**
	 * @param listaDeProjectili the listaDeProjectili to set
	 */
	public void setListaDeProjectili(ArrayList<Projectile> listaDeProjectili) {
		this.listaDeProjectile = listaDeProjectili;
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
		return personagemActor;
	}
	/**
	 * @return the listaActor
	 */
	public ArrayList<ActorCGT> getListaActor() {
		return listaActor;
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
