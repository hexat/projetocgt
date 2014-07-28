package com.projetocgt.cenario;

import java.util.Random;

import cgt.CGTGameWorld;
import cgt.behaviors.*;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.core.CGTProjectile;
import cgt.policy.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Class utilizada para renderizar imagens do jogo na tela.Isso inclui Actor,
 * Opposites, Bonus... Inicializa a camera. O metodo render desenha da tela as
 * imagens.
 * 
 * @author Bruno
 * 
 */
public class WorldRenderer {

	private CGTGameWorld world; // Declara a variavel do tipo World que sera
								// passada
	// de parametro no renderer
	private OrthographicCamera camera; // Declara a variavel da camera
	private CGTActor personagem;
	private int interval;
	private int ammo;
	private boolean colisao;
	private boolean colisaoEnemy;
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	private SpriteBatch spriteBatch;
	private boolean flagDebug;
	private int width;
	private int height;
	private Vector2 posAnterior = new Vector2();

	public WorldRenderer(CGTGameWorld world, boolean debug) {
		this.world = world;
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(width, height);

		// this.camera.position.set(width/2, height/2 , 0);
		this.camera.position.set(world.getActor().getPosition().x, world
				.getActor().getPosition().y, 0);
		this.flagDebug = debug;
		// System.out.println(personagem.getLife());
		spriteBatch = new SpriteBatch();
		personagem = world.getActor();
	}

	/**
	 * Esta funcao e' chamada no metodo render da class GameScreen. Responsavel
	 * por desenhar todos os objetos na tela.
	 */
	public void render() {
		isColision(); // ATENCAO
		
		if (world.getActor().getPosition().x-camera.viewportWidth/2>0 &&
				world.getActor().getPosition().x+camera.viewportWidth/2<world.getBackground().getWidth())
			camera.position.x=world.getActor().getPosition().x;
		
		if (world.getActor().getPosition().y-camera.viewportHeight/2>0 &&
				world.getActor().getPosition().y + camera.viewportHeight/2<world.getBackground().getHeight())
			camera.position.y=world.getActor().getPosition().y;
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined); // Possibilita a
		// camera acompanhar
		// o personagem
		spriteBatch.begin();
		drawGameObjects();
		drawCGTActor();
		System.out.println(personagem.getLife());
		System.out.println(personagem.isInvincible());
		spriteBatch.end();
		if (flagDebug)
			drawDebug();
		
		 // Atualiza a tela

	}

	/**
	 * Utilizado para limpar o desenho da tela
	 */
	public void dispose() {
		world.getBackground().dispose();
		/*
		 * for(int i =0;i<world.getOpposites().size();i++){
		 * //world.getOpposites().get(i).getTexture().dispose();
		 * //world.getListaDeProjectili
		 * ().get(i).getSpriteSheet().CGTAnimation(personagem); }
		 */
		// for (int i = 0; i < world.getBonus().size(); i++) {
		// world.getBonus().get(i).getTexture().dispose();
		// }
		// for (int i = 0; i < world.getActor().getProjectiles()
		// .size(); i++) {
		// world.getActor().getProjectiles().get(i).getTexture()
		// .dispose();
		// }
		spriteBatch.dispose();
	}

	/***
	 * Desenha os obejtos na cena, backGroud, CGTOpposite, CGTBonus e
	 * CGTProjectle
	 */
	private void drawGameObjects() {
		spriteBatch.draw(world.getBackground(), 0, 0);
		// Desenha todos os Opposite
		for (int i = 0; i < world.getOpposites().size(); i++) {
			if (world.getOpposites().get(i).getLife() >= 0)
				spriteBatch.draw(world.getOpposites().get(i).getAnimation(),
						world.getOpposites().get(i).getPosition().x, world
								.getOpposites().get(i).getPosition().y, world
								.getOpposites().get(i).getBounds().width, world
								.getOpposites().get(i).getBounds().height);
		}

		// Desenha todos os Enemy
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getLife() >= 0) {
				configBehavior(world.getEnemies().get(i));
				spriteBatch.setColor(1.0f, 1.0f, 1.0f, world.getEnemies()
						.get(i).getAlpha());
				spriteBatch.draw(world.getEnemies().get(i).getAnimation(),
						world.getEnemies().get(i).getPosition().x, world
								.getEnemies().get(i).getPosition().y, world
								.getEnemies().get(i).getBounds().width, world
								.getEnemies().get(i).getBounds().height);
			}

			// spriteBatch.draw(world.getEnemies().get(i).getSpriteSheet().CGTAnimation(personagem),
			// world.getEnemies().get(i).getPosition().x,
			// world.getEnemies().get(i).getPosition().y,
			// world.getEnemies().get(i).getBounds().width,
			// world.getEnemies().get(i).getBounds().height);
			// TODO Verifica qual a Direction Policy
			// if(world.getEnemies().get(i).getPosition().y >= 400){
			// world.getEnemies().get(i).getVelocity().y=-world.getEnemies().get(i).getSpeed();
			// System.out.print(world.getEnemies().get(i).getPosition().y+"\n");
			// }
			// else{
			// world.getEnemies().get(i).getVelocity().y=0;
			// }

			spriteBatch.draw(world.getEnemies().get(i).getAnimation(), world
					.getEnemies().get(i).getPosition().x, world.getEnemies()
					.get(i).getPosition().y, world.getEnemies().get(i)
					.getBounds().width,
					world.getEnemies().get(i).getBounds().height);
			// TODO Verifica qual a Direction Policy
			// if(world.getEnemies().get(i).getPosition().y >= 400){
			// world.getEnemies().get(i).getVelocity().y=-world.getEnemies().get(i).getSpeed();
			// System.out.print(world.getEnemies().get(i).getPosition().y+"\n");
			// }
			// else{
			// world.getEnemies().get(i).getVelocity().y=0;
			// }

		}

		// Desenha todos os Bonus
		for (int i = 0; i < world.getBonus().size(); i++) {
			spriteBatch.draw(world.getBonus().get(i).getAnimation(), world
					.getBonus().get(i).getPosition().x, world.getBonus().get(i)
					.getPosition().y,
					world.getBonus().get(i).getBounds().width, world.getBonus()
							.get(i).getBounds().height);
			// spriteBatch.draw(world.listaDeBonus.get(i).getSpriteSheet().CGTAnimation(personagem),
			// world.getBonus().get(i).getPosition().x,
			// world.getBonus().get(i).getPosition().y,
			// world.getBonus().get(i).getBounds().width,
			// world.getBonus().get(i).getBounds().height);
		}

		// Desenha todos os Projectile
		// for (int i = 0; i < world.getActor().getProjectiles().size(); i++) {
		// Verifica se tem alguem ativo
		// if (world.getActor().getProjectiles().get(i).isFlagAtivar()
		// && world.getActor().getProjectiles().get(i).getAmmo() > 0) {
		if (world.getActor().getFireDefault() != -1
				&& world.getActor().getProjectiles()
						.get(world.getActor().getFireDefault()).getAmmo() > 0) {
			CGTProjectile pro = world.getActor().getProjectiles()
					.get(world.getActor().getFireDefault());
			// Verifica o intervalo
			interval = pro.getInterval();
			// world.getListaDeProjectili().get(i).ammoDown();

			// TODO aqui sera as variacoes do projectile
			// verifica se dos ativados qual a posicao que sera' desenhado
			for (int w = 0; w < pro.getOrientations().size(); w++) {
				if (pro.getOrientations().get(w).getStates()
						.contains(personagem.getState())) {
					pro.setPosition(personagem.getPosition());
					// faz um movimento do projectile
					// world.getListaDeProjectili().get(i).getPosition().x=world.getListaDeProjectili().get(i).getVelocityInitial().x;
					pro.getBounds().x += pro.getOrientations().get(w)
							.getPositionRelativeToGameObject().x;

					pro.getBounds().y += pro.getOrientations().get(w)
							.getPositionRelativeToGameObject().y;

					spriteBatch
							.draw(pro.getAnimation(),
									pro.getPosition().x
											+ pro.getOrientations()
													.get(w)
													.getPositionRelativeToGameObject().x,
									pro.getPosition().y
											+ pro.getOrientations()
													.get(w)
													.getPositionRelativeToGameObject().y,
									pro.getBounds().width,
									pro.getBounds().height);
				}
			}

			// Percorre a lista de Enemy
			for (int j = 0; j < world.getEnemies().size(); j++) {
				// verifica se algum Enemy destrutivel esta colindindo com
				// algum Projectile
				if (world.getEnemies().get(j).getCollision()
						.overlaps(pro.getCollision())
						&& world.getEnemies().get(j).isDestroyable()) {
					world.getEnemies().get(j)
							.setLife(world.getEnemies().get(j).getLife() - 1);
					// world.getOpposites().get(j).setBlock(false);
					if (world.getEnemies().get(j).getLife() <= 0)// Se
						// o
						// life
						// for
						// zero
						// remove
						// da
						// cena
						world.getEnemies().remove(j);
					boolean ganhou = true;
					for (int index = 0; index < world.getEnemies().size()
							&& ganhou; index++) {
						if (world.getEnemies().get(index).isDestroyable()
								&& world.getEnemies().get(index).getLife() > 0)
							ganhou = false;
					}
					if (ganhou)// Verifica se tem algum inimigo na cena
						System.out.println("Ganhou");
				}
			}
			// }
		}
	}

	/***
	 * Desenha o Actor na cena
	 */
	private void drawCGTActor() {
		// Desenha o Actor na cena
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		spriteBatch.draw(personagem.getAnimation(), personagem.getPosition().x,
				personagem.getPosition().y, personagem.getBounds().width,
				personagem.getBounds().height);
	}

	/***
	 * Metodo utilizada para fazer o debug
	 */
	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(personagem.getCollision().x, personagem
				.getCollision().y, personagem.getCollision().getWidth(),
				personagem.getCollision().getHeight());

		// Carrega o debug para todos os Opposite
		for (int i = 0; i < world.getOpposites().size(); i++) {
			// if(world.getOpposites().get(i).getLife()>=0){
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getOpposites().get(i).getCollision().x,
					world.getOpposites().get(i).getCollision().y, world
							.getOpposites().get(i).getCollision().getWidth(),
					world.getOpposites().get(i).getCollision().getHeight());
			// }
		}

		// Carrega o debug para todos os Enemy
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getLife() >= 0) {
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(world.getEnemies().get(i).getCollision().x,
						world.getEnemies().get(i).getCollision().y, world
								.getEnemies().get(i).getCollision().getWidth(),
						world.getEnemies().get(i).getCollision().getHeight());
			}
		}

		// Carrega o debug para todos os Actor
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(world.getActor().getCollision().x, world.getActor()
				.getCollision().y, world.getActor().getCollision().getWidth(),
				world.getActor().getCollision().getHeight());
		// Carrega o debug para todos os Bonus
		for (int i = 0; i < world.getBonus().size(); i++) {
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getBonus().get(i).getCollision().x, world
					.getBonus().get(i).getCollision().y, world.getBonus()
					.get(i).getCollision().getWidth(), world.getBonus().get(i)
					.getCollision().getHeight());
		}
		CGTProjectile pro = null;
		// Carrega o debug para todos os Projectile
		// for (int i = 0; i < world.getActor().getProjectiles()
		// .size(); i++) {
		if (world.getActor().getFireDefault() != -1
				&& world.getActor().getProjectiles()
						.get(world.getActor().getFireDefault()).getAmmo() > 0)
			// verifica se dos ativos qual a posicao que sera' desenhado
			pro = world.getActor().getProjectiles()
					.get(world.getActor().getFireDefault());
		for (int w = 0; w < pro.getOrientations().size(); w++) {
			if (pro.getOrientations().get(w).getStates()
					.contains(personagem.getState()))
				debugRenderer.rect(pro.getCollision().x, pro.getCollision().y,
						pro.getCollision().getWidth(), pro.getCollision()
								.getHeight());
		}
		// }
		debugRenderer.end();
	}

	void damageActorCGT(CGTActor personagem) {
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getCollision()
					.overlaps(personagem.getCollision())) {
				animationDamage(personagem, world.getEnemies().get(i));
				System.out.println(personagem.getLife());

				if (personagem.getLife() < 0) {
					System.out.println("Game Over");
					// TODO musica do game over
					// personagem.getSoundDie().play();
				}
			}
		}
	}

	/**
	 * Verifica e executa o comportamento da lista de behaviors de um Enemy
	 */
	private void configBehavior(CGTEnemy enemy) {
		for (int indice = 0; indice < enemy.getBehaviors().size(); indice++) {

			Behavior behavior = enemy.getBehaviors().get(indice);

			// Sine - movimento de sino, "vai e vem"
			if (behavior.getBehaviorPolicy().equals("VERTICAL")) {
				Sine sine = (Sine) behavior;

				if (sine.isAtFirstStep())
					enemy.getVelocity().y = enemy.getSpeed();
				else
					enemy.getVelocity().y = -enemy.getSpeed();

				if (enemy.getPosition().y < sine.getMin()) {
					sine.setAtFirstStep(true);
					enemy.setState(StatePolicy.LOOKUP);
				}
				if (enemy.getPosition().y > sine.getMax()) {
					sine.setAtFirstStep(false);
					enemy.setState(StatePolicy.LOOKDOWN);
				}
			}

			else if (behavior.getBehaviorPolicy().equals("HORIZONTAL")) {
				Sine sine = (Sine) behavior;

				if (sine.isAtFirstStep())
					enemy.getVelocity().x = -enemy.getSpeed();
				else
					enemy.getVelocity().x = enemy.getSpeed();

				if (enemy.getPosition().x < sine.getMin()) {
					sine.setAtFirstStep(false);
					enemy.setState(StatePolicy.LOOKRIGHT);
				}
				if (enemy.getPosition().x > sine.getMax()) {
					sine.setAtFirstStep(true);
					enemy.setState(StatePolicy.LOOKLEFT);
				}
			}

			// WIDTH e HEIGHT "esticam" o sprite
			else if (behavior.getBehaviorPolicy().equals("WIDTH")) {
				Sine sine = (Sine) behavior;

				if (sine.isAtFirstStep()) {
					enemy.getCollision().width += enemy.getSpeed();
					enemy.getBounds().width += enemy.getSpeed();
				}

				else {
					enemy.getBounds().width -= enemy.getSpeed();
					enemy.getCollision().width -= enemy.getSpeed();
				}

				if (enemy.getCollision().width < sine.getMin())
					sine.setAtFirstStep(true);
				else if (enemy.getCollision().width > sine.getMax())
					sine.setAtFirstStep(false);
			}

			else if (behavior.getBehaviorPolicy().equals("HEIGHT")) {
				Sine sine = (Sine) behavior;

				if (sine.isAtFirstStep()) {
					enemy.getCollision().height += enemy.getSpeed();
					enemy.getBounds().height += enemy.getSpeed();
				}

				else {
					enemy.getBounds().height -= enemy.getSpeed();
					enemy.getCollision().height -= enemy.getSpeed();
				}

				if (enemy.getCollision().height < sine.getMin())
					sine.setAtFirstStep(true);
				else if (enemy.getCollision().height > sine.getMax())
					sine.setAtFirstStep(false);
			}

			// Direction - Padrão de movimentos dentro de uma area; Muda de
			// direcao randomicamente;
			// Direcoes descritas pelas policys
			else if (behavior.getBehaviorPolicy().equals("LEFT_AND_RIGHT")) {
				Direction direction = (Direction) behavior;
				int[] angulos = { 0, 180 };

				Random random = new Random();
				if (random.nextFloat() < 0.0001 * enemy.getSpeed())
					scheduleDirection(angulos, enemy);

				if (enemy.getPosition().x < direction.getMinX())
					enemy.getVelocity().x = enemy.getSpeed();
				if (enemy.getPosition().x > direction.getMaxX())
					enemy.getVelocity().x = -enemy.getSpeed();
			}

			else if (behavior.getBehaviorPolicy().equals("UP_AND_DOWN")) {
				Direction direction = (Direction) behavior;
				int[] angulos = { 90, 270 };

				Random random = new Random();
				if (random.nextFloat() < 0.0001 * enemy.getSpeed())
					scheduleDirection(angulos, enemy);

				if (enemy.getPosition().y < direction.getMinY())
					enemy.getVelocity().y = enemy.getSpeed();
				if (enemy.getPosition().y > direction.getMaxY())
					enemy.getVelocity().y = -enemy.getSpeed();
			}

			else if (behavior.getBehaviorPolicy().equals("FOUR_DIRECTION")) {
				Direction direction = (Direction) behavior;
				int[] angulos = { 0, 90, 180, 270 };

				Random random = new Random();
				if (random.nextFloat() < 0.00005 * enemy.getSpeed())
					scheduleDirection(angulos, enemy);

				if (enemy.getPosition().x < direction.getMinX())
					enemy.getVelocity().x = enemy.getSpeed();
				if (enemy.getPosition().x > direction.getMaxX())
					enemy.getVelocity().x = -enemy.getSpeed();

				if (enemy.getPosition().y < direction.getMinY())
					enemy.getVelocity().y = enemy.getSpeed();
				if (enemy.getPosition().y > direction.getMaxY())
					enemy.getVelocity().y = -enemy.getSpeed();
			}

			else if (behavior.getBehaviorPolicy().equals("EIGHT_DIRECTION")) {
				Direction direction = (Direction) behavior;
				int[] angulos = { 0, 45, 90, 135, 180, 225, 270, 315 };

				Random random = new Random();
				if (random.nextFloat() < 0.0001 * enemy.getSpeed())
					scheduleDirection(angulos, enemy);

				if (enemy.getPosition().x < direction.getMinX())
					enemy.getVelocity().x = enemy.getSpeed();
				if (enemy.getPosition().x > direction.getMaxX())
					enemy.getVelocity().x = -enemy.getSpeed();

				if (enemy.getPosition().y < direction.getMinY())
					enemy.getVelocity().y = enemy.getSpeed();
				if (enemy.getPosition().y > direction.getMaxY())
					enemy.getVelocity().y = -enemy.getSpeed();
			}

			// Fade - Usado para se "apagar" ou fazer um sprite "surgir"
			else if (behavior.getBehaviorPolicy().equals("FADE_IN")) {
				Fade fade = (Fade) behavior;
				scheduleFadeIn(enemy, fade);
			}

			stateUpdater(enemy);

		}
		// world.getEnemies().get(i).getVelocity().x=-world.getEnemies().get(i).getSpeed();

	}

	public void stateUpdater(CGTEnemy enemy) {
		if (enemy.getVelocity().x > 0 & enemy.getVelocity().y == 0)
			enemy.setState(StatePolicy.LOOKRIGHT);

		else if (enemy.getVelocity().x < 0 & enemy.getVelocity().y == 0)
			enemy.setState(StatePolicy.LOOKLEFT);

		else if (enemy.getVelocity().y > 0 & enemy.getVelocity().x == 0)
			enemy.setState(StatePolicy.LOOKUP);

		else if (enemy.getVelocity().y < 0 & enemy.getVelocity().x == 0)
			enemy.setState(StatePolicy.LOOKDOWN);
	}

	/** Implementação do comportamento descrito por um behavior Fade */
	private void scheduleFadeIn(final CGTEnemy enemy, Fade fade) {
		if (!fade.isStarted()) {

			// Garante que este metodo sera executado somente uma vez
			fade.setStarted(true);

			// Desativa-se as interacoes do enemy com o actor
			final boolean destroyable = enemy.isDestroyable();
			enemy.setDestroyable(false);
			final int damage = enemy.getDamage();
			enemy.setDamage(0);
			enemy.setAlpha(0f);

			Timer.schedule(new Task() {
				public void run() {
					for (float alpha = 0f; alpha <= 1f; alpha += 0.01f) {
						enemy.setAlpha(alpha);
					}

					// Recupera-se a interacao definida ao enemy
					enemy.setDestroyable(destroyable);
					enemy.setDamage(damage);
				}
			}, fade.getFadeInTime());
		}
	}

	// Implementação do comportamento descrito por um behavior Direction
	private void scheduleDirection(int[] angulos, CGTEnemy enemy) {
		enemy.getVelocity().x = 0;
		enemy.getVelocity().y = 0;
		Random random = new Random();
		int indice = random.nextInt(angulos.length);

		// Velocidade X no 1º e 4º quadrantes
		if ((angulos[indice] >= 0 && angulos[indice] < 90)
				|| (angulos[indice] > 270 && angulos[indice] < 360)) {
			enemy.getVelocity().x = enemy.getSpeed();

		}

		// Velocidade X no 2º e 3º quadrantes
		if (angulos[indice] > 90 && angulos[indice] < 270) {
			enemy.getVelocity().x = -enemy.getSpeed();
			enemy.setState(StatePolicy.LOOKLEFT);
		}

		// Velocidade Y no 1º e 2º quadrantes
		if (angulos[indice] > 0 && angulos[indice] < 180) {
			enemy.getVelocity().y = enemy.getSpeed();

		}

		// Velocidade Y no 3º e 4º quadrantes
		if (angulos[indice] > 180 && angulos[indice] < 360) {
			enemy.getVelocity().y = -enemy.getSpeed();

		}

	}

	/**
	 * Utilizado para verificar se o CGTActor colidiu com algum Bloqueante
	 * 
	 * @return the colisao
	 */
	public boolean isColision() {
		// colisao = false;
		damageActorCGT(personagem);
		// Verifica se colidiu com algum Opposite
		for (int i = 0; i < world.getOpposites().size(); i++) {
			if (world.getOpposites().get(i).getCollision()
					.overlaps(personagem.getCollision())
					&& world.getOpposites().get(i).isBlock())
				colisao = true;
		}

		// Verifica se colidiu com algum Enemy
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getCollision()
					.overlaps(personagem.getCollision())
					&& world.getEnemies().get(i).isBlock()) {
				colisaoEnemy = true;
				// animationDamege(personagem);
				colisao = true;
			}
		}

		// Verifica se colidiu com algum Bonus
		for (int i = 0; i < world.getBonus().size(); i++) {
			if (world.getBonus().get(i).getCollision()
					.overlaps(personagem.getCollision())) {
				for (int j = 0; j < world.getActor().getProjectiles().size(); j++) {
					world.getActor().getProjectiles().get(j).setAmmo(4);
				}
				colisao = true;
			}
		}

		if (!colisao) {
			posAnterior.x = personagem.getPosition().x;
			posAnterior.y = personagem.getPosition().y;
		} else {
			personagem.getVelocity().x = 0;
			personagem.getVelocity().y = 0;
			personagem.setPosition(posAnterior.cpy());
			// return colisao;
			colisao = false;
		}
		return colisao;
	}

	/**
	 * 
	 * @param personagem
	 */
	public void animationDamage(CGTActor boy, CGTEnemy enemy) {

		if (!personagem.isInvincible()) {
			personagem.setInvincible(true);
			personagem.setState(StatePolicy.DAMAGE);
			personagem.setLife(personagem.getLife() - enemy.getDamage());
			// personagem.getSoundDamage().play();
			Timer.schedule(new Task() {
				@Override
				public void run() {
					personagem.setInvincible(false);

				}
			}, enemy.getInterval());
		}
	}

	/**
	 * @return the camera
	 */
	public OrthographicCamera getCam() {
		return camera;
	}

	/**
	 * @param camera
	 *            the cam to set
	 */
	public void setCam(OrthographicCamera cam) {
		this.camera = cam;
	}

	/**
	 * @return the posAnterior
	 */
	public Vector2 getPosAnterior() {
		return posAnterior;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @return the ammo
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * @param ammo
	 *            the ammo to set
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the world
	 */
	public CGTGameWorld getWorld() {
		return world;
	}

	/**
	 * @return the colisao
	 */
	public boolean isColisao() {
		return colisao;
	}

	/**
	 * @param colisao
	 *            the colisao to set
	 */
	public void setColisao(boolean colisao) {
		this.colisao = colisao;
	}

	/**
	 * @return the colisaoEnemy
	 */
	public boolean isColisaoEnemy() {
		return colisaoEnemy;
	}

	/**
	 * @param colisaoEnemy
	 *            the colisaoEnemy to set
	 */
	public void setColisaoEnemy(boolean colisaoEnemy) {
		this.colisaoEnemy = colisaoEnemy;
	}
}
