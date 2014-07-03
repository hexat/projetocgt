package com.projetocgt.cenario;

import java.util.Random;

import cgt.behaviors.Behavior;
import cgt.behaviors.Direction;
import cgt.behaviors.Fade;
import cgt.behaviors.Sine;
import cgt.policy.DirectionPolicy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.projetocgt.personagens.ActorCGT;
import com.projetocgt.personagens.Enemy;

/**
 * Class utilizada para renderizar imagens do jogo na tela.Isso inclui Actor, Opposites, Bonus...
 * Inicializa a camera. O metodo render desenha da tela as imagens.
 * @author Bruno
 *
 */
public class WorldRenderer   {

	private MyWorld world;			//Declara a variavel do tipo World que sera passada de parametro no renderer 
	private OrthographicCamera camera;	//Declara a variavel da camera
	private ActorCGT personagem;
	private int interval; 
	private int ammo;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	private SpriteBatch spriteBatch;
	private boolean flagDebug = false; 		
	private int width;					
	private int height;					
	private Vector2 posAnterior=new Vector2();

	public WorldRenderer(MyWorld world, boolean debug) {
		this.world = world;
		this.width=Gdx.graphics.getWidth();
		this.height=Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(width, height);
		//this.camera.position.set(width/2, height/2 , 0); 
		this.camera.position.set(world.getPersonagem().getPosition().x, world.getPersonagem().getPosition().y , 0);
		this.flagDebug = debug;												 
		spriteBatch = new SpriteBatch();
		personagem = world.getPersonagem();
	}
	void creat(){}
	/**
	 * Esta funcao e' chamada no metodo render da class GameScreen.
	 * Responsavel por desenhar todos os objetos na tela.
	 */
	public void render( ) {
		//isColision(); // ATENCAO
		this.camera.update(); 			//Atualiza a tela
		spriteBatch.setProjectionMatrix(camera.combined); //Possibilita a camera acompanhar o personagem
		spriteBatch.begin();
		drawGameObjects();
		drawCGTActor();
		spriteBatch.end();
		if (flagDebug)
			drawDebug();
	}
	/**
	 * Utilizado para limpar o desenho da tela
	 */
	public void dispose(){
		world.getBackGround().dispose();
		/*for(int i =0;i<world.getListaDeOpposite().size();i++){
			//world.getListaDeOpposite().get(i).getTexture().dispose();
			//world.getListaDeProjectili().get(i).getSpriteSheet().CGTAnimation(personagem);
		}*/
		for(int i =0;i<world.getListaDeBonus().size();i++){
			world.getListaDeBonus().get(i).getTexture().dispose();
		}
		for(int i =0;i<world.getListaDeProjectili().size();i++){
			world.getListaDeProjectili().get(i).getTexture().dispose();
		}
		spriteBatch.dispose();
	}
	/***
	 * Desenha os obejtos na cena, backGroud, CGTOpposite, CGTBonus e
	 * CGTProjectle
	 */
	private void drawGameObjects() {
		spriteBatch.draw(world.getBackGround(), 0, 0);
		//Desenha todos os Opposite
		for(int i =0;i<world.getListaDeOpposite().size();i++){
			if(world.getListaDeOpposite().get(i).getLife()>=0)
				spriteBatch.draw(world.getListaDeOpposite().get(i).getSpriteSheet().CGTAnimation(personagem), world.listaDeOpposite.get(i).getPosition().x, world.listaDeOpposite.get(i).getPosition().y, world.listaDeOpposite.get(i).getBounds().width, world.listaDeOpposite.get(i).getBounds().height);
		}

		//Desenha todos os Enemy
		for(int i =0;i<world.getListaDeEnemy().size();i++){
			if(world.getListaDeEnemy().get(i).getLife()>=0){
<<<<<<< HEAD
				configBehavior(world.getListaDeEnemy().get(i));
				spriteBatch.setColor(1.0f, 1.0f, 1.0f, world.getListaDeEnemy().get(i).getAlpha());
				spriteBatch.draw(world.getListaDeEnemy().get(i).getSpriteSheet().CGTAnimation(personagem), world.getListaDeEnemy().get(i).getPosition().x, world.getListaDeEnemy().get(i).getPosition().y, world.getListaDeEnemy().get(i).getBounds().width, world.getListaDeEnemy().get(i).getBounds().height);
			}
=======
				spriteBatch.draw(world.getListaDeEnemy().get(i).getSpriteSheet().CGTAnimation(personagem), world.getListaDeEnemy().get(i).getPosition().x, world.getListaDeEnemy().get(i).getPosition().y, world.getListaDeEnemy().get(i).getBounds().width, world.getListaDeEnemy().get(i).getBounds().height);
				//TODO Verifica qual a Direction Policy
				//if(world.getListaDeEnemy().get(i).getPosition().y >= 400){
					//world.getListaDeEnemy().get(i).getVelocity().y=-world.getListaDeEnemy().get(i).getSpeed();
					//System.out.print(world.getListaDeEnemy().get(i).getPosition().y+"\n");
				//}
				//else{
					//world.getListaDeEnemy().get(i).getVelocity().y=0;
					//}
				}
>>>>>>> 4c7bdb14a95e4ef9daac6ca24e0e8a5f491907bb
		}

		//Desenha todos os Bonus
		for(int i =0;i<world.getListaDeBonus().size();i++){
			spriteBatch.draw(world.getListaDeBonus().get(i).getTexture(), world.getListaDeBonus().get(i).getPosition().x, world.getListaDeBonus().get(i).getPosition().y, world.getListaDeBonus().get(i).getBounds().width, world.getListaDeBonus().get(i).getBounds().height);
			//spriteBatch.draw(world.listaDeBonus.get(i).getSpriteSheet().CGTAnimation(personagem), world.getListaDeBonus().get(i).getPosition().x, world.getListaDeBonus().get(i).getPosition().y, world.getListaDeBonus().get(i).getBounds().width, world.getListaDeBonus().get(i).getBounds().height);
		}

		//Desenha todos os Projectile
		for(int i =0;i<world.getListaDeProjectili().size();i++){

			//Verifica se tem alguem ativo
			if(world.getListaDeProjectili().get(i).isFlagAtivar() && world.getListaDeProjectili().get(i).getAmmo()>0){

				//Verifica o intervalo
				interval=world.getListaDeProjectili().get(i).getInterval();
				//world.getListaDeProjectili().get(i).ammoDown();

				//TODO aqui sera as variacoes do projectile
				//verifica se dos ativados qual a posicao que sera' desenhado
				for(int w =0; w<world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().size(); w++){
					if(personagem.getState()==world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().get(w).getState()){
						//faz um movimento do projectile
						//world.getListaDeProjectili().get(i).getPosition().x=world.getListaDeProjectili().get(i).getVelocityInitial().x;
						world.getListaDeProjectili().get(i).getRectangle().x += world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().get(w).getPositionRetativeToGameObject().x;
						world.getListaDeProjectili().get(i).getRectangle().y += world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().get(w).getPositionRetativeToGameObject().y;						

						spriteBatch.draw(world.getListaDeProjectili().get(i).getSpriteSheet().CGTAnimation(personagem),
								world.getListaDeProjectili().get(i).getPosition().x+world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().get(w).getPositionRetativeToGameObject().x, 
								world.getListaDeProjectili().get(i).getPosition().y+world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().get(w).getPositionRetativeToGameObject().y,
								world.getListaDeProjectili().get(i).getBounds().width, world.getListaDeProjectili().get(i).getBounds().height);		
					}
				}

				//Percorre a lista de Enemy
				for(int j=0; j < world.getListaDeEnemy().size(); j++){
					//verifica se algum Enemy destrutivel esta colindindo com algum Projectile
					if(world.getListaDeEnemy().get(j).getRectangle().overlaps(world.getListaDeProjectili().get(i).getRectangle()) && world.getListaDeEnemy().get(j).isDestroyable()){
						world.getListaDeEnemy().get(j).setLife(world.getListaDeEnemy().get(j).getLife()-1);
						//world.getListaDeOpposite().get(j).setBlock(false);
						if(world.getListaDeEnemy().get(j).getLife()==0)//Se o life for zero remove da cena
							world.getListaDeEnemy().remove(j);
						if(world.getListaDeEnemy().size()==0)//Verifica se tem algum inimigo na cena
							System.out.println("Ganhou");	
					}
				}
			}
		}
	}

	/***
	 * Desenha o Actor na cena
	 */
	private void drawCGTActor() {
		//Desenha o Actor na cena
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		spriteBatch.draw(personagem.getSpriteSheet().CGTActorAnimation(personagem), personagem.getPosition().x, personagem.getPosition().y, personagem.getBounds().width, personagem.getBounds().height);
	}

	/***
	 * Metodo utilizada para fazer o debug
	 */
	private void drawDebug(){
		// render blocks	
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(personagem.getRectPer().x, personagem.getRectPer().y, personagem.getRectPer().getWidth(), personagem.getRectPer().getHeight());

		//Carrega o debug para todos os Opposite
		for(int i=0;i<world.getListaDeOpposite().size();i++){				
			//if(world.getListaDeOpposite().get(i).getLife()>=0){
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getListaDeOpposite().get(i).getRectangle().x, world.getListaDeOpposite().get(i).getRectangle().y, world.getListaDeOpposite().get(i).getRectangle().getWidth(), world.getListaDeOpposite().get(i).getRectangle().getHeight());
			//}
		}

		//Carrega o debug para todos os Enemy
		for(int i=0;i<world.getListaDeEnemy().size();i++){				
			if(world.getListaDeEnemy().get(i).getLife()>=0){
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(world.getListaDeEnemy().get(i).getRectangle().x, world.getListaDeEnemy().get(i).getRectangle().y, world.getListaDeEnemy().get(i).getRectangle().getWidth(), world.getListaDeEnemy().get(i).getRectangle().getHeight());
			}
		}

		//Carrega o debug para todos os Actor
		for(int i=0;i<world.getListaActor().size();i++){				
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getListaActor().get(i).getRectPer().x, world.getListaActor().get(i).getRectPer().y, world.getListaActor().get(i).getRectPer().getWidth(), world.getListaActor().get(i).getRectPer().getHeight());
		}
		//Carrega o debug para todos os Bonus
		for(int i=0;i<world.getListaDeBonus().size();i++){				
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getListaDeBonus().get(i).getRectangle().x, world.getListaDeBonus().get(i).getRectangle().y, world.getListaDeBonus().get(i).getRectangle().getWidth(), world.getListaDeBonus().get(i).getRectangle().getHeight());
		}
		//Carrega o debug para todos os Projectile
		for(int i =0;i<world.getListaDeProjectili().size();i++){
			if(world.getListaDeProjectili().get(i).isFlagAtivar() && world.getListaDeProjectili().get(i).getAmmo()>0)
				//verifica se dos ativos qual a posicao que sera' desenhado
				for(int w =0; w<world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().size(); w++){
					if(personagem.getState()==world.getListaDeProjectili().get(i).getListaDeProjectileOrientation().get(w).getState())
						debugRenderer.rect(	world.getListaDeProjectili().get(i).getRectangle().x,
								world.getListaDeProjectili().get(i).getRectangle().y, 
								world.getListaDeProjectili().get(i).getRectangle().getWidth(), world.getListaDeProjectili().get(i).getRectangle().getHeight());
				}
		}
		debugRenderer.end();
	}
	void damageCGTActor(ActorCGT personagem){
		for(int i=0; i < world.getListaDeEnemy().size(); i++){
			if(world.getListaDeEnemy().get(i).getRectangle().overlaps(personagem.getRectPer())){
				personagem.setLife(personagem.getLife()-world.getListaDeEnemy().get(i).getDamage());
				System.out.println(personagem.getLife());
				if(personagem.getLife()<0)
					System.out.println("Game Over");
			}		
		}
	}

	private void configBehavior(Enemy enemy){
		for(int indice=0; indice<enemy.getBehaviors().size(); indice++){

			Behavior behavior = enemy.getBehaviors().get(indice);

			//Sine - movimento de sino, "vai e vem"
			if(behavior.getBehaviorPolicy().equals("VERTICAL")){
				Sine sine = (Sine)behavior;

				if(sine.isAtFirstStep())
					enemy.getVelocity().y=enemy.getSpeed();
				else
					enemy.getVelocity().y=-enemy.getSpeed();

				if(enemy.getPosition().y<sine.getMin())
					sine.setAtFirstStep(true);
				if(enemy.getPosition().y>sine.getMax())
					sine.setAtFirstStep(false);
			}

			else if(behavior.getBehaviorPolicy().equals("HORIZONTAL")){
				Sine sine = (Sine)behavior;

				if(sine.isAtFirstStep())
					enemy.getVelocity().x=-enemy.getSpeed();
				else
					enemy.getVelocity().x=enemy.getSpeed();

				if(enemy.getPosition().x<sine.getMin())
					sine.setAtFirstStep(false);
				if(enemy.getPosition().x>sine.getMax())
					sine.setAtFirstStep(true);
			}

			else if(behavior.getBehaviorPolicy().equals("WIDTH")){
				Sine sine = (Sine)behavior;

				if(sine.isAtFirstStep()){
					enemy.getRectangle().width+=1;
					enemy.getBounds().width+=1;
				}

				else{
					enemy.getBounds().width-=1;
					enemy.getRectangle().width-=1;
				}

				if(enemy.getRectangle().width<sine.getMin())
					sine.setAtFirstStep(true);
				else if(enemy.getRectangle().width>sine.getMax())
					sine.setAtFirstStep(false);
			}

			else if(behavior.getBehaviorPolicy().equals("HEIGHT")){
				Sine sine = (Sine)behavior;

				if(sine.isAtFirstStep()){
					enemy.getRectangle().height+=1;
					enemy.getBounds().height+=1;
				}

				else{
					enemy.getBounds().height-=1;
					enemy.getRectangle().height-=1;
				}

				if(enemy.getRectangle().height<sine.getMin())
					sine.setAtFirstStep(true);
				else if(enemy.getRectangle().height>sine.getMax())
					sine.setAtFirstStep(false);
			}

			else if(behavior.getBehaviorPolicy().equals("LEFT_AND_RIGHT")){
				final Direction direction = (Direction)behavior;
				int[] angulos = {0, 180};

				Random random = new Random();
				if(random.nextFloat()<0.0001*enemy.getSpeed())
					scheduleDirection(angulos, enemy, 1);

				if(enemy.getPosition().x<direction.getMinX())
					direction.setLeft(false);
				if(enemy.getPosition().x>direction.getMaxX())
					direction.setLeft(true);
			}

			else if(behavior.getBehaviorPolicy().equals("UP_AND_DOWN")){
				final Direction direction = (Direction)behavior;
				int[] angulos = {90, 270};

				Random random = new Random();
				if(random.nextFloat()<0.0001*enemy.getSpeed())
					scheduleDirection(angulos, enemy, 1);

				if(enemy.getPosition().y<direction.getMinY())
					enemy.getVelocity().y=enemy.getSpeed();
				if(enemy.getPosition().y>direction.getMaxY())
					enemy.getVelocity().y=-enemy.getSpeed();
			}

			else if(behavior.getBehaviorPolicy().equals("EIGHT_DIRECTION")){
				final Direction direction = (Direction)behavior;
				int[] angulos = {0, 45, 90, 135, 180, 225, 270, 315};

				Random random = new Random();
				if(random.nextFloat()<0.0001*enemy.getSpeed())
					scheduleDirection(angulos, enemy, 1);

				if(enemy.getPosition().x<direction.getMinX())
					enemy.getVelocity().x=enemy.getSpeed();
				if(enemy.getPosition().x>direction.getMaxX())
					enemy.getVelocity().x=-enemy.getSpeed();

				if(enemy.getPosition().y<direction.getMinY())
					enemy.getVelocity().y=enemy.getSpeed();
				if(enemy.getPosition().y>direction.getMaxY())
					enemy.getVelocity().y=-enemy.getSpeed();
			}

			else if(behavior.getBehaviorPolicy().equals("FADE_IN")){
				Fade fade = (Fade)behavior;
				scheduleFadeIn(enemy, fade);
			}

		}
		//world.getListaDeEnemy().get(i).getVelocity().x=-world.getListaDeEnemy().get(i).getSpeed();


	}
	private void scheduleFadeIn(final Enemy enemy, final Fade fade){
		if(!fade.isStarted()){
			fade.setStarted(true);
			Timer.schedule(new Task(){
				@Override
				public void run(){
					for(float alpha = 0f; alpha<=1f;alpha+=0.01f){
						enemy.setAlpha(alpha);
					}
				}
			}, fade.getFadeInTime());
		}
	}
	private void scheduleDirection(final int[] angulos, final Enemy enemy, final int time){
		Timer.schedule(new Task(){
			@Override
			public void run(){
				Random random = new Random();
				int indice = random.nextInt(angulos.length);

				if(angulos[indice]==0){
					enemy.getVelocity().x=enemy.getSpeed();
					enemy.getVelocity().y=0;
				}
				
				if(angulos[indice]==45){
					enemy.getVelocity().x=enemy.getSpeed();
					enemy.getVelocity().y=enemy.getSpeed();
				}

				if(angulos[indice]==90){
					enemy.getVelocity().y=enemy.getSpeed();
					enemy.getVelocity().x=0;
				}
				
				if(angulos[indice]==135){
					enemy.getVelocity().x=-enemy.getSpeed();
					enemy.getVelocity().y=enemy.getSpeed();
				}

				if(angulos[indice]==180){
					enemy.getVelocity().x=-enemy.getSpeed();
					enemy.getVelocity().y=0;
				}
				
				if(angulos[indice]==225){
					enemy.getVelocity().x=-enemy.getSpeed();
					enemy.getVelocity().y=-enemy.getSpeed();
				}

				if(angulos[indice]==270){
					enemy.getVelocity().y=-enemy.getSpeed();
					enemy.getVelocity().x=0;
				}

				if(angulos[indice]==315){
					enemy.getVelocity().x=enemy.getSpeed();
					enemy.getVelocity().y=-enemy.getSpeed();
				}
			}
		}, time);
	}

	/**
	 * Utilizado para verificar se o CGTACtor colidiu com algum Bloqueante
	 * @return the colisao
	 */
	public boolean isColision() {
		boolean colisao = false;
		damageCGTActor(personagem);
		//Verifica se colidiu com algum Opposite
		for(int i=0; i < world.getListaDeOpposite().size(); i++){
			if(world.getListaDeOpposite().get(i).getRectangle().overlaps(personagem.getRectPer()) && world.getListaDeOpposite().get(i).isBlock())
				colisao=true;

		}

		//Verifica se colidiu com algum Opposite
		for(int i=0; i < world.getListaDeEnemy().size(); i++){
			if(world.getListaDeEnemy().get(i).getRectangle().overlaps(personagem.getRectPer()) && world.getListaDeEnemy().get(i).isBlock())
				colisao=true;
		}

		//Verifica se colidiu com algum Bonus
		for(int i=0; i < world.getListaDeBonus().size(); i++){
			if(world.getListaDeBonus().get(i).getRectangle().overlaps(personagem.getRectPer()))
				colisao=true;
		}


		if(!colisao){
			posAnterior.x = personagem.getPosition().x;
			posAnterior.y = personagem.getPosition().y;
		}else{
			personagem.getVelocity().x=0;
			personagem.getVelocity().y=0;
			personagem.setPosition(posAnterior);
			colisao = false;
		}
		return colisao;
	}


	/**
	 * @return the camera
	 */
	public OrthographicCamera getCam() {
		return camera;
	}

	/**
	 * @param camera the cam to set
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
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
}
