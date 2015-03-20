package com.projetocgt.cenario;

import java.util.ArrayList;
import java.util.Random;

import cgt.game.CGTGameWorld;
import cgt.behaviors.Fade;
import cgt.core.CGTActor;
import cgt.core.CGTAddOn;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.core.CGTOpposite;
import cgt.core.CGTProjectile;
import cgt.policy.BonusPolicy;
import cgt.policy.StatePolicy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

	private StretchViewport viewport;
	private Rectangle rectangleCamera;
	private Random random;
	private Vector2 lastActorPosition;
	private enum CameraStage {INITIAL, FULL, CLOSE, IDLE};
	
	private CameraStage zoomCamera;
	private boolean isLose;
	private Music musicActorLose;
	private float fatorVolumeObjects;
	
	private CGTOpposite rio;
	private CGTOpposite aguaAnimation;
	
	private ArrayList<CGTAddOn> addons;

	public WorldRenderer(CGTGameWorld world) {
		this.world = world;
		addons = new  ArrayList<CGTAddOn>();
		fatorVolumeObjects = 1f;
		
		isLose = false;
		rio = (CGTOpposite) world.getObjectByLabel("rio");
		musicActorLose = null;
		zoomCamera = CameraStage.IDLE;
		float width = world.getBackground().getTextureGDX().getWidth() * world.getCamera().getInitialWidth();
		float height = world.getBackground().getTextureGDX().getHeight() * world.getCamera().getInitialHeight();
		this.camera = new OrthographicCamera(width, height);
		this.viewport = new StretchViewport(width, height, camera);

		setCameraPosition();
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
			if (GameScreen.DEBUG) {
				drawDebug();
			}
		}

		else {
			// jogo so vai pra tela de perca quando o som de morte do actor acabar
			if (musicActorLose == null) {
				musicActorLose = world.getActor().getSoundDie();
				if (musicActorLose == null) {
					isLose = true;
				} else {
					personagem.setState(StatePolicy.DYING);
					musicActorLose.setOnCompletionListener(new Music.OnCompletionListener() {
						@Override
						public void onCompletion(Music music) {
							personagem.setState(StatePolicy.DIE);
							isLose = true;
							Timer.instance().clear();
						}
					});
					musicActorLose.play();
				}
			}

			spriteBatch.begin();
			draw();
			spriteBatch.end();
			
			Gdx.input.setInputProcessor(null);

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
		}
	}

	
	// executa música caso o objeto esteja visível na camera
	private void verifyObjectsOnCamera(){
		rectangleCamera.set(camera.position.x - camera.viewportWidth/2, camera.position.y - camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
		
		// verifica Opposites
		for(int i = 0; i < world.getOpposites().size(); i++){
			if (world.getOpposites().get(i).getSound() != null && rectangleCamera.overlaps(world.getOpposites().get(i).getCollision())){
				if (!world.getOpposites().get(i).isPlayingSound()){
					final CGTGameObject cgt = world.getOpposites().get(i);
					cgt.playSound();
					if (cgt.getDelayPlaySound() > 0) {
						Timer.schedule(new Task() {
							@Override
							public void run() {
								cgt.canPlaySaund();
							}
						}, cgt.getDelayPlaySound());
					} else {
						cgt.canPlaySaund();
					}
				}

				float distanciaObjeto = world.getActor().getPosition().dst(world.getOpposites().get(i).getPosition());				
				float distanciaMaxima = (float) Math.sqrt((double) (Math.pow((double) camera.viewportHeight, 2)) + Math.pow((double) camera.viewportWidth, 2));
				float volume = (1 - distanciaObjeto/distanciaMaxima)* world.getOpposites().get(i).getSound().getVolume();
				volume *= fatorVolumeObjects;
				if (volume <= 0){
					volume = 0;
				}
				world.getOpposites().get(i).getSound().getMusic().setVolume(volume);


			} else {
				if (world.getOpposites().get(i).isPlayingSound()){
					world.getOpposites().get(i).stopMusic();
				}
				world.getOpposites().get(i).canPlaySaund();
			}
		}
		// verifica Enemies
		for(int i = 0; i < world.getEnemies().size(); i++){
			if (world.getEnemies().get(i).getSound() != null && rectangleCamera.overlaps(world.getEnemies().get(i).getCollision())){
				if (!world.getEnemies().get(i).isPlayingSound()){
					final CGTGameObject cgt = world.getEnemies().get(i);
					cgt.playSound();
					if (cgt.getDelayPlaySound() > 0) {
						Timer.schedule(new Task() {
							@Override
							public void run() {
								cgt.canPlaySaund();
							}
						}, cgt.getDelayPlaySound());
					} else {
						cgt.canPlaySaund();
					}
				}

				float distanciaObjeto = world.getActor().getPosition().dst(world.getEnemies().get(i).getPosition());
				float distanciaMaxima = (float) Math.sqrt((double) (Math.pow((double) camera.viewportHeight, 2)) + Math.pow((double) camera.viewportWidth, 2));
				float volume = (1 - distanciaObjeto/distanciaMaxima)* world.getEnemies().get(i).getSound().getVolume();
				volume *= fatorVolumeObjects;

				if (volume <= 0){
					volume = 0;
				}
				world.getEnemies().get(i).getSound().getMusic().setVolume(volume);


			} else {
				if (world.getEnemies().get(i).isPlayingSound()){
					world.getEnemies().get(i).stopMusic();
				}
				world.getEnemies().get(i).canPlaySaund();
			}
		}		

	}

	public void cameraCloseOnActor() {
		zoomCamera = CameraStage.CLOSE;
	}

	public void cameraFullScreen() {
		zoomCamera = CameraStage.FULL;
	}

	private void setCameraPosition() {
        float max = world.getBackground().getTextureGDX().getWidth() - camera.viewportWidth / 2;
		if (world.getActor().getPosition().x > max) {
			camera.position.x = max;
		} else {
			float a = world.getActor().getPosition().x - camera.viewportWidth/ 2;
			if (a < 0) a = 0;
			camera.position.x = camera.viewportWidth/ 2 + a;
		}

		float h = world.getBackground().getTextureGDX().getHeight() - camera.viewportHeight / 2;
		
		if (world.getActor().getPosition().y > h) {
			camera.position.y = h;
		} else {
			float a = world.getActor().getPosition().y - camera.viewportHeight/ 2;
			if (a < 0) a = 0;
			camera.position.y = camera.viewportHeight/ 2 + a;
		}
	}
	
	private void cameraClose() {
		if (camera.viewportHeight > world.getBackground().getTextureGDX().getHeight() * world.getCamera().getCloseHeight() 
				|| camera.viewportWidth > world.getBackground().getTextureGDX().getWidth() * world.getCamera().getCloseWidth()) {
			
			if (camera.viewportHeight > world.getBackground().getTextureGDX().getHeight() * world.getCamera().getCloseHeight() ) {
				camera.viewportHeight -= world.getCamera().getScale() * world.getBackground().getTextureGDX().getHeight();
			}
			if (camera.viewportWidth > world.getBackground().getTextureGDX().getWidth() * world.getCamera().getCloseWidth()){
				camera.viewportWidth -= world.getCamera().getScale() * world.getBackground().getTextureGDX().getWidth();
			}
			
			setCameraPosition();
		} else {
			zoomCamera = CameraStage.IDLE;
		}
	}

	private void cameraOpen() {
		if (camera.viewportHeight < world.getBackground().getTextureGDX().getHeight() * world.getCamera().getFullHeight()
				|| camera.viewportWidth < world.getBackground().getTextureGDX().getWidth() * world.getCamera().getFullWidth()) {
			
			if (camera.viewportHeight < world.getBackground().getTextureGDX().getHeight() * world.getCamera().getFullHeight()) {
				camera.viewportHeight += world.getCamera().getScale() * world.getBackground().getTextureGDX().getHeight();
			}
			
			if (camera.viewportWidth < world.getBackground().getTextureGDX().getWidth() * world.getCamera().getFullWidth()){
				camera.viewportWidth += world.getCamera().getScale() * world.getBackground().getTextureGDX().getWidth();
			}

			
			setCameraPosition();
//			max = world.getBackground().getTextureGDX().getHeight() - camera.viewportHeight/ 2;
//			if (world.getActor().getPosition().y > max) {
//				camera.position.y = max;
//			} else {
//				float a = camera.viewportHeight / 2 - world.getActor().getPosition().y;
//				if (a < 0) a = 0;
//				camera.position.y = camera.viewportHeight / 2 + a;
//			}
		} else {
			zoomCamera = CameraStage.IDLE;
			
		}
	}
	
	
	private void updateCamera() {
		if (zoomCamera == CameraStage.CLOSE) {
			fatorVolumeObjects = 1f;
			cameraClose();
		} else if (zoomCamera == CameraStage.FULL) {
			fatorVolumeObjects = world.getCamera().getVolumeOnFullCamera();
			cameraOpen();
		}
		
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
		if (world.getWinCriteria().isEmpty()) return false;
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
		drawProjectiles(); // Chamada de projectiles precisa ser feita antes de Enemies
		drawEnemies();
		drawOpposites();
		drawBonus();
		drawAddOn();
	}

	private void drawAddOn() {
		for (int i = 0; i < addons.size(); i++) {
				spriteBatch.draw(addons.get(i).getAnimation(),
						addons.get(i).getPosition().x,
						addons.get(i).getPosition().y,
						addons.get(i).getBounds().width, 
						addons.get(i).getBounds().height);
				
//				if (addons.get(i).isDrawing()) {
//					addons.get(i).setActive(false);
//					addons.remove(i);
//				}
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
				spriteBatch.setColor(1.0f, 1.0f, 1.0f, world.getEnemies().get(i).getAlpha());
//				for (CGTGameObject o : world.getEnemies().get(i).getObjectsToCollide()) {
//					world.getEnemies().get(i).getCollideAnimation().setActive(o.getCollision().overlaps(world.getEnemies().get(i).getCollision()));
//					if (o.getCollision().overlaps(world.getEnemies().get(i).getCollision())) {
						if (world.getEnemies().get(i).getCollideAnimation()!=null && world.getEnemies().get(i).getPosition().y > 90 && world.getEnemies().get(i).getPosition().y < 95) {
							world.getEnemies().get(i).getCollideAnimation().setActive(true);
							CGTAddOn a = world.getEnemies().get(i).getCollideAnimation().clone();
							a.setPosition(world.getEnemies().get(i).getPosition().cpy());
							a.getPosition().x += a.getPositionRelativeToParent().x;
							a.getPosition().y += a.getPositionRelativeToParent().y;
							a.getBounds().x += a.getPositionRelativeToParent().x;
							a.getBounds().y += a.getPositionRelativeToParent().y;
							a.getCollision().x += a.getPositionRelativeToParent().x;
							a.getCollision().y += a.getPositionRelativeToParent().y;
							addons.add(a);
						}
//					}
//				}

				spriteBatch.draw(world.getEnemies().get(i).getAnimation(),
						world.getEnemies().get(i).getPosition().x, world
						.getEnemies().get(i).getPosition().y, world
						.getEnemies().get(i).getBounds().width, world
						.getEnemies().get(i).getBounds().height);

				// verifica se algum Enemy destrutivel esta colindindo com
				// algum Projectile
                CGTProjectile pro = world.getActor().getProjectileDefault();
				if (pro != null
                        && world.getActor().isFireActivate()
						&& pro.getAmmo() > 0
						&& world.getEnemies().get(i).getCollision().overlaps(pro.getCollision())
						&& world.getEnemies().get(i).isDestroyable()
						&& world.getEnemies().get(i).isVulnerable()
                        && pro.containsGroup(world.getEnemies().get(i).getGroup())) {
					world.getEnemies().get(i).setLife(world.getEnemies().get(i).getLife() - world.getActor().getProjectiles().get(world.getActor().getFireDefault()).getDamage());
					world.getEnemies().get(i).playSoundCollision();
					if (world.getEnemies().get(i).getLife() <= 0){
						world.getEnemies().get(i).playSoundDie();
						world.getEnemies().remove(i);
					}
				}
			}
		}

		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1);
	}

	private void drawProjectiles() {
		// Desenha todos os Projectile
		// Verifica se tem alguem ativo pelo fireDefault
		if (world.getActor().isFireActivate()
				&& world.getActor().getProjectiles().get(world.getActor().getFireDefault()).getAmmo() > 0) {
			
			CGTProjectile pro = getCurrentActorProjectile();

			for (int w = 0; w < pro.getOrientations().size(); w++) {
				if (pro.getOrientations().get(w).getStates().contains(personagem.getState())) {
                    pro.setPosition(personagem.getPosition().cpy());
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
				if (world.getOpposites().get(i).getAnimation() != null){
				spriteBatch.draw(world.getOpposites().get(i).getAnimation(),
						world.getOpposites().get(i).getPosition().x, world
						.getOpposites().get(i).getPosition().y, world
						.getOpposites().get(i).getBounds().width, world
						.getOpposites().get(i).getBounds().height);
				}
			}
		}
	}

	private void drawCollisions() {
		// TODO Auto-generated method stub
		
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
		for (int indice = 0; indice < enemy.getBehaviorsSize(); indice++) {
			enemy.getBehavior(indice).act();
			stateUpdater(enemy);
		}
	}

	public void stateUpdater(CGTEnemy enemy) {
		if (!enemy.isAttacking()){
			if (enemy.getVelocity().x > 0 & enemy.getVelocity().y == 0)
				enemy.setState(StatePolicy.LOOKRIGHT);
	
			else if (enemy.getVelocity().x < 0 & enemy.getVelocity().y == 0)
				enemy.setState(StatePolicy.LOOKLEFT);
	
			else if (enemy.getVelocity().y > 0 & enemy.getVelocity().x == 0)
				enemy.setState(StatePolicy.LOOKUP);
	
			else if (enemy.getVelocity().y < 0 & enemy.getVelocity().x == 0)
				enemy.setState(StatePolicy.LOOKDOWN);
			else if(enemy.getVelocity().y > 0 && enemy.getVelocity().x > 0)
				enemy.setState(StatePolicy.LOOK_RIGHT_AND_UP);
			else if(enemy.getVelocity().y < 0 && enemy.getVelocity().x < 0)
				enemy.setState(StatePolicy.LOOK_LEFT_AND_DOWN);
			else if (enemy.getVelocity().y > 0 && enemy.getVelocity().x < 0)
				enemy.setState(StatePolicy.LOOK_LEFT_AND_UP);
			else if (enemy.getVelocity().y < 0 && enemy.getVelocity().x > 0)
				enemy.setState(StatePolicy.LOOK_RIGHT_AND_DOWN);
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
		for (CGTOpposite oposite : world.getOpposites()) {
			if (oposite.getCollision().overlaps(personagem.getCollision()) && oposite.isBlock()){
				if(!oposite.isCollide()){
					oposite.playSoundCollision();
					oposite.setCollide(true);
				}
				colision = true;
			} else {
				oposite.setCollide(false);
			}
		}
			
		

		// Verifica se colidiu com algum Enemy
		for (CGTEnemy enemy : world.getEnemies()) {
			if (enemy.getCollision().overlaps(personagem.getCollision())) {
				animationDamage(enemy);
				enemy.playSoundCollision();
				if (enemy.isBlock()) {
					colision = true;
				}
			}
		}
			
		

		// Verifica se colidiu com algum Bonus
		//TODO bonus so' pode setar colision true se ele for bloqueante
		//ver possibilidade se mudar esse trecho de codigo pois nao tem o mesmo objetivo da funcao
		for (CGTBonus bonus : world.getBonus()) {
			if (bonus.getCollision().overlaps(personagem.getCollision())){				
				if(bonus.getLife() > 0){
					if(!bonus.isCollide()){
						if (bonus.getPolicies().contains(BonusPolicy.ADD_AMMO)){
							world.getActor().getProjectileDefault().addAmmo(bonus.getScore());
						}
						if(bonus.getPolicies().contains(BonusPolicy.ADD_LIFE)){
							world.getActor().addLife(bonus.getScore());
						}
						if(bonus.getPolicies().contains(BonusPolicy.ADD_SCORE)){
							world.addScore(bonus.getScore());
						}
						bonus.reduceLife(bonus.getScore());
						bonus.playSoundCollision();
						bonus.setCollide(true);
					}
				} else {
					if(bonus.hasAnimation(StatePolicy.DIE)){
						bonus.setState(StatePolicy.DIE);
						bonus.playSoundDie();
					} else {	
						world.getBonus().remove(bonus);
					}
				}
				colision = true;
			} else {
//				System.out.println("DESATIVOU");
				bonus.setCollide(false);
			}
		}


//        System.out.println(colision);
        if (!colision) {
			lastActorPosition.x = personagem.getPosition().x;
			lastActorPosition.y = personagem.getPosition().y;
		} else {
			personagem.getVelocity().x = 0;
			personagem.getVelocity().y = 0;
            if (lastActorPosition.x > 0 || lastActorPosition.y > 0) {
			    personagem.setPosition(lastActorPosition.cpy());
            }
			// return colisao;
			colision = false;
		}
		return colision;
	}

	/**
	 * 
	 */
	public void animationDamage(final CGTEnemy enemy) {

		if (!personagem.isInvincible() && enemy.isVulnerable() && enemy.getDamage()>0) {

			personagem.setInvincible(true);
			personagem.setState(StatePolicy.DAMAGE);
			enemy.setState(StatePolicy.DAMAGE);
			enemy.setAttacking(true);

            if (enemy.isBlock()) {
                personagem.setInputsEnabled(true);

                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        personagem.setInputsEnabled(false);
                    }
                }, personagem.getTimeToEnableInputs());
            }
			personagem.playSoundCollision();

			personagem.setLife(personagem.getLife() - enemy.getDamage());
			Timer.schedule(new Task() {
				@Override
				public void run() {
					personagem.setInvincible(false);
				}
			}, personagem.getTimeToRecovery());

			Timer.schedule(new Task() {
				@Override
				public void run() {
					enemy.setAttacking(false);
				}
			}, enemy.getTimeToRecovery());
			
		}
	}

	/**
	 * @return the camera
	 */
	public OrthographicCamera getCam() {
		return camera;
	}

	/**
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
	 * @return the world
	 */
	public CGTGameWorld getWorld() {
		return world;
	}

	public StretchViewport getViewport() {
		return viewport;
	}

	public boolean lose() {
		return isLose;
	}

	public ArrayList<CGTAddOn> getAddOns() {
		return addons;
	}
}
