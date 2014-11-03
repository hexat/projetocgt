package com.projetocgt.cenario;

import java.util.Random;


import cgt.CGTGameWorld;
import cgt.behaviors.Behavior;
import cgt.behaviors.Direction;
import cgt.behaviors.Fade;
import cgt.behaviors.Sine;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.core.CGTProjectile;
import cgt.policy.BonusPolicy;
import cgt.policy.GameModePolicy;
import cgt.policy.StatePolicy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.projetocgt.GameScreen;

/**
 * Class utilizada para renderizar imagens do jogo na tela.Isso inclui Actor,
 * Opposites, Bonus... Inicializa a camera. O metodo render desenha da tela as
 * imagens.
 * 
 * @author Bruno
 * 
 */
public class WorldRenderer {

	private CGTGameWorld world;
	private OrthographicCamera camera;
	private CGTActor personagem;
	private ShapeRenderer debugRenderer;
	private SpriteBatch spriteBatch;
	private int width;
	private int height;
	private StretchViewport viewport;
	private Rectangle rectangleCamera;
	private Random random;
	private Vector2 lastActorPosition;

	public WorldRenderer(CGTGameWorld world) {
		this.world = world;
		this.width =  Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(width, height);
		if(world.getModePolicy().equals(GameModePolicy.ONE_SCREEN)){
			this.viewport = new StretchViewport(800, 480, camera);
			this.camera.position.set(world.getActor().getPosition().x, world
					.getActor().getPosition().y, 0);
		} else if(world.getModePolicy().equals(GameModePolicy.ONE_SCREEN_WITHOUT_CAMERA)) {
			this.viewport = new StretchViewport(world.getBackground().getTextureGDX().getWidth(), world.getBackground().getTextureGDX().getHeight(), camera);
			this.camera.position.set(world.getBackground().getTextureGDX().getWidth()/2, world.getBackground().getTextureGDX().getHeight()/2, 0);
		}
		this.debugRenderer = new ShapeRenderer();
		
		this.lastActorPosition = new Vector2();
		this.spriteBatch = new SpriteBatch();
		this.personagem = world.getActor();
		rectangleCamera = new Rectangle(camera.position.x - camera.viewportWidth/2, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
		random = new Random();
		
		
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	/**
	 * Esta funcao e' chamada no metodo render da class GameScreen. Responsavel
	 * por desenhar todos os objetos na tela.
	 */
	public void render() {		
		isColision();

		verifyObjectsOnCamera();
		
		
		
		updateCamera();

		if (!verifyLose()) {
			
			if (verifyWin()) 
				System.out.println("ganhou");
				
			spriteBatch.begin();
			draw();
			spriteBatch.end();
		}

		else {
			world.getActor().playSoundDie();	
			Timer.instance().clear();
			
			for(int index = 0; index < world.getOpposites().size(); index ++){
				if(world.getOpposites().get(index).isPlayingSound()){
					world.getOpposites().get(index).stopMusic();
				}
			}
			for(int index = 0; index < world.getEnemies().size(); index ++){
				if(world.getEnemies().get(index).isPlayingSound()){
					world.getEnemies().get(index).stopMusic();
				}
			}
			
//			instance = StarAssault.getInstance();
//			instance.setScreen(new GeneralScreen(instance.getMenu()));
		}



	}


	// executa música caso o objeto esteja visível na camera
	private void verifyObjectsOnCamera(){		
		rectangleCamera.set(camera.position.x - camera.viewportWidth/2, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);

		// verifica Opposites
		for(int i = 0; i < world.getOpposites().size(); i++){
			if (world.getOpposites().get(i).getSound() != null && rectangleCamera.overlaps(world.getOpposites().get(i).getCollision())){
				if (!world.getOpposites().get(i).isPlayingSound()){
					world.getOpposites().get(i).playSound();					
				}

				float distanciaObjeto = world.getActor().getPosition().dst(world.getOpposites().get(i).getPosition());				
				float distanciaMaxima = (float) Math.sqrt((double) (Math.pow((double) camera.viewportHeight, 2)) + Math.pow((double) camera.viewportWidth, 2));
				float volume = (1 - distanciaObjeto/distanciaMaxima)* world.getOpposites().get(i).getSound().getVolume();
				if (volume <= 0){
					volume = 0;
				}
				world.getOpposites().get(i).getSound().getMusic().setVolume(volume);


			} else {
				if (world.getOpposites().get(i).isPlayingSound()){
					world.getOpposites().get(i).stopMusic();
				}
			}
		}
		// verifica Enemies
		for(int i = 0; i < world.getEnemies().size(); i++){
			if (world.getEnemies().get(i).getSound() != null && rectangleCamera.overlaps(world.getEnemies().get(i).getCollision())){
				if (!world.getEnemies().get(i).isPlayingSound()){
					world.getEnemies().get(i).playSound();
				}

				float distanciaObjeto = world.getActor().getPosition().dst(world.getEnemies().get(i).getPosition());
				float distanciaMaxima = (float) Math.sqrt((double) (Math.pow((double) camera.viewportHeight, 2)) + Math.pow((double) camera.viewportWidth, 2));
				float volume = (1 - distanciaObjeto/distanciaMaxima)* world.getEnemies().get(i).getSound().getVolume();
				if (volume <= 0){
					volume = 0;
				}
				world.getEnemies().get(i).getSound().getMusic().setVolume(volume);


			} else {
				if (world.getEnemies().get(i).isPlayingSound()){
					world.getEnemies().get(i).stopMusic();
				}
			}
		}		

	}

	private void updateCamera() {
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined); // Possibilita a camera acompanhar o personagem

		if (world.getActor().getPosition().x - camera.viewportWidth / 2 > 0
				&& world.getActor().getPosition().x + camera.viewportWidth / 2 < world
				.getBackground().getTextureGDX().getWidth()) {
			camera.position.x = world.getActor().getPosition().x;
			
		}

		if (world.getActor().getPosition().y - camera.viewportHeight / 2 > 0
				&& world.getActor().getPosition().y + camera.viewportHeight / 2 < world
				.getBackground().getTextureGDX().getHeight()) {
			camera.position.y = world.getActor().getPosition().y;
			
		}
	}

	public boolean verifyLose(){
		boolean lose = false;
		for (int index = 0; index<world.getLoseCriteria().size() && !lose; index++) {
			lose = world.getLoseCriteria().get(index).lost();
		}
		
		return lose;
		
	}

	public boolean verifyWin(){
		boolean win = true;

		for(int index = 0; index<world.getWinCriteria().size() && win; index++){
			win = world.getWinCriteria().get(index).achieved();
		}

		return win;
	}

	/**
	 * Utilizado para limpar o desenho da tela
	 */
	public void dispose() {
		spriteBatch.dispose();
	}

	private void drawBackground(){
		spriteBatch.draw(world.getBackground().getTextureGDX(), 0, 0);
	}

	public void draw() {

		drawBackground();
		drawCGTActor();
		drawOpposites();
		drawProjectiles(); // Chamada de projectiles precisa ser feita antes de Enemies
		drawEnemies();
		drawBonus();

		drawDamageActor();

		if (GameScreen.DEBUG) {
			drawDebug();
		}

	}

	private void drawBonus() {
		for (int i = 0; i < world.getBonus().size(); i++) {
			spriteBatch.draw(world.getBonus().get(i).getAnimation(), world
					.getBonus().get(i).getPosition().x, world.getBonus().get(i)
					.getPosition().y,
					world.getBonus().get(i).getBounds().width, world.getBonus()
					.get(i).getBounds().height);
		}
	}

	private void drawEnemies() {
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

				// verifica se algum Enemy destrutivel esta colindindo com
				// algum Projectile
				if (world.getActor().getFireDefault() != -1
						&& world.getActor().getProjectiles().get(world.getActor().getFireDefault()).getAmmo() > 0
						&& world.getEnemies().get(i).getCollision().overlaps(getCurrentActorProjectile().getCollision())
						&& world.getEnemies().get(i).isDestroyable()
						&& world.getEnemies().get(i).isVulnerable()) {
					world.getEnemies().get(i).setLife(world.getEnemies().get(i).getLife() - 1);
					world.getEnemies().get(i).playSoundCollision();
					if (world.getEnemies().get(i).getLife() <= 0){
						world.getEnemies().get(i).playSoundDie();
						world.getEnemies().remove(i);
					}
				}
			}
		}

	}

	private void drawProjectiles() {
		// Desenha todos os Projectile
		// Verifica se tem alguem ativo pelo fireDefault
		if (world.getActor().getFireDefault() != -1
				&& world.getActor().getProjectiles().get(world.getActor().getFireDefault()).getAmmo() > 0) {
			CGTProjectile pro = getCurrentActorProjectile();

			for (int w = 0; w < pro.getOrientations().size(); w++) {
				if (pro.getOrientations().get(w).getStates().contains(personagem.getState())) {
					pro.setPosition(personagem.getPosition());
					//Os desenhos sao feitos de acordo com os States. 
					pro.setState(personagem.getState());

					// faz um movimento do projectile
					pro.getBounds().x += pro.getOrientations().get(w).getPositionRelativeToGameObject().x;
					pro.getBounds().y += pro.getOrientations().get(w).getPositionRelativeToGameObject().y;
					pro.getCollision().x += pro.getOrientations().get(w).getPositionRelativeToGameObject().x;
					pro.getCollision().y += pro.getOrientations().get(w).getPositionRelativeToGameObject().y;
					spriteBatch.draw(pro.getAnimation(),pro.getPosition().x + 
							pro.getOrientations().get(w).getPositionRelativeToGameObject().x,
							pro.getPosition().y+ pro.getOrientations().get(w).getPositionRelativeToGameObject().y,
							pro.getBounds().width,pro.getBounds().height);
				}
			}

		}
	}

	private void drawOpposites() {
		for (int i = 0; i < world.getOpposites().size(); i++) {
			if (world.getOpposites().get(i).getLife() >= 0) {
				if (world.getOpposites().get(i).getSpriteSheet() != null){
				spriteBatch.draw(world.getOpposites().get(i).getAnimation(),
						world.getOpposites().get(i).getPosition().x, world
						.getOpposites().get(i).getPosition().y, world
						.getOpposites().get(i).getBounds().width, world
						.getOpposites().get(i).getBounds().height);
				}
			}
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
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(personagem.getCollision().x, personagem
				.getCollision().y, personagem.getCollision().getWidth(),
				personagem.getCollision().getHeight());

		// Carrega o debug para todos os Opposite
		for (int i = 0; i < world.getOpposites().size(); i++) {
			//debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getOpposites().get(i).getCollision().x,
					world.getOpposites().get(i).getCollision().y, world
					.getOpposites().get(i).getCollision().getWidth(),
					world.getOpposites().get(i).getCollision().getHeight());
		}

		// Carrega o debug para todos os Enemy
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getLife() >= 0) {
				//debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(world.getEnemies().get(i).getCollision().x,
						world.getEnemies().get(i).getCollision().y, world
						.getEnemies().get(i).getCollision().getWidth(),
						world.getEnemies().get(i).getCollision().getHeight());
			}
		}

		// Carrega o debug para todos os Actor
		//debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(world.getActor().getCollision().x, world.getActor()
				.getCollision().y, world.getActor().getCollision().getWidth(),
				world.getActor().getCollision().getHeight());
		// Carrega o debug para todos os Bonus
		for (int i = 0; i < world.getBonus().size(); i++) {
			//debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getBonus().get(i).getCollision().x, world
					.getBonus().get(i).getCollision().y, world.getBonus()
					.get(i).getCollision().getWidth(), world.getBonus().get(i)
					.getCollision().getHeight());
		}
		CGTProjectile pro = null;
		// Carrega o debug para todos os Projectile
		if (world.getActor().getFireDefault() != -1
				&& world.getActor().getProjectiles().get(world.getActor().getFireDefault()).getAmmo() > 0){
			// verifica se dos ativos qual a posicao que sera' desenhado
			pro = world.getActor().getProjectiles().get(world.getActor().getFireDefault());
			for (int w = 0; w < pro.getOrientations().size(); w++) {
				if (pro.getOrientations().get(w).getStates().contains(personagem.getState()))
					debugRenderer.rect(pro.getCollision().x,
							pro.getCollision().y,
							pro.getCollision().getWidth(), pro.getCollision().getHeight());
			}
		}
		debugRenderer.end();
	}

	private void drawDamageActor() {
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getCollision().overlaps(personagem.getCollision())) {
				animationDamage(world.getEnemies().get(i));
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
					enemy.getBounds().height += enemy.getSpeed();
					enemy.getCollision().height += enemy.getSpeed();

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

			// Direction - Padrao de movimentos dentro de uma area; Muda de
			// direcao randomicamente;
			// Direcoes descritas pelas policys
			else if (behavior.getBehaviorPolicy().equals("LEFT_AND_RIGHT")) {
				Direction direction = (Direction) behavior;
				int[] angulos = { 0, 180 };


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

	/**
	 * Implementação do comportamento descrito por um behavior Fade
	 * @param enemy
	 * @param fade
	 */
	private void scheduleFadeIn(final CGTEnemy enemy, final Fade fade) {
		// Desativa-se as interacoes do enemy com o actor e retira-se o behavior
		// para que ocorra apenas uma vez
		enemy.setAlpha(0);
		enemy.setVulnerable(false);
		enemy.removeBehavior(fade);

		Timer.schedule(new Timer.Task() {
			int tempo = 0;
			public void run() {
				tempo++;
				if(tempo >= fade.getFadeInTime()){
					System.out.println(enemy.getGroup()+": FADE ATIVADO");
					enemy.setAlpha(1);
					enemy.setVulnerable(true);
					this.cancel();
				}
				System.out.println("Fade : "+ tempo);
			}
		}, 1, 1);
	}

	// Implementação do comportamento descrito por um behavior Direction
	private void scheduleDirection(int[] angulos, CGTEnemy enemy) {
		enemy.getVelocity().x = 0;
		enemy.getVelocity().y = 0;

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
		boolean colision = false;

		//System.out.println(personagem.getState().name());
		// Verifica se colidiu com algum Opposite
		for (int i = 0; i < world.getOpposites().size(); i++) {
			if (world.getOpposites().get(i).getCollision()
					.overlaps(personagem.getCollision())
					&& world.getOpposites().get(i).isBlock())
				colision = true;
		}

		// Verifica se colidiu com algum Enemy
		for (int i = 0; i < world.getEnemies().size(); i++) {
			if (world.getEnemies().get(i).getCollision().overlaps(personagem.getCollision())
					&& world.getEnemies().get(i).isBlock()) {
				//animationDamege(personagem);
				colision = true;
			}
		}

		// Verifica se colidiu com algum Bonus
		for (int i = 0; i < world.getBonus().size(); i++) {
			if (world.getBonus().get(i).getCollision()
					.overlaps(personagem.getCollision())) {
				if(world.getBonus().get(i).getLife() > 0){
					if (world.getBonus().get(i).getPolicies().contains(BonusPolicy.ADD_AMMO)){
						int ammoCurrent =  world.getActor().getProjectiles().get(0).getAmmo();
						int maxAmmo = world.getActor().getProjectiles().get(0).getMaxAmmo();
						if (ammoCurrent < maxAmmo) {
							int recharge = world.getActor().getProjectiles().get(0).addAmmo(world.getBonus().get(i).getScore());
							world.getBonus().get(i).reduceLife(recharge);
							System.out.println(recharge);
							world.getBonus().get(0).playSoundCollision();
						}
	
					}
				} else {
					System.out.println("ENTREI AQUI");
					world.getBonus().get(i).setState(StatePolicy.DIE);
				}
				colision = true;
			}
		}

		if (!colision) {
			lastActorPosition.x = personagem.getPosition().x;
			lastActorPosition.y = personagem.getPosition().y;
		} else {
			personagem.getVelocity().x = 0;
			personagem.getVelocity().y = 0;
			personagem.setPosition(lastActorPosition.cpy());
			// return colisao;
			colision = false;
		}
		return colision;
	}

	/**
	 * 
	 * @param personagem
	 */
	public void animationDamage(CGTEnemy enemy) {

		if (!personagem.isInvincible() && enemy.isVulnerable() && enemy.getDamage()>0) {

			personagem.setInvincible(true);
			personagem.setState(StatePolicy.DAMAGE);
			personagem.setInputsEnabled(true);

			Timer.schedule(new Task() {
				@Override
				public void run() {
					personagem.setInputsEnabled(false);
				}
			}, personagem.getTimeToEnableInputs());

			personagem.playSoundCollision();
			personagem.setLife(personagem.getLife() - enemy.getDamage());
			Timer.schedule(new Task() {
				@Override
				public void run() {
					personagem.setInvincible(false);
				}
			}, personagem.getTimeToRecovery());
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
		return lastActorPosition;
	}

	/**
	 * @return the interval
	 */
	public CGTProjectile getCurrentActorProjectile() {
		return world.getActor().getProjectiles().get(world.getActor().getFireDefault());
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

	public StretchViewport getViewport() {
		return viewport;
	}
}
