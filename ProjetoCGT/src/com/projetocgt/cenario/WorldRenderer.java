package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.CGTActor;
import com.projetocgt.personagens.SpriteSheet;

/**
 * Class utilizada para renderizar imagens do jogo na tela.Isso inclui Actor, Opposites, Bonus...
 * Inicializa a camera. O metodo render desenha da tela as imagens.
 * @author Bruno
 *
 */
public class WorldRenderer   {
	
	private MyWorld world;			//Declara a variavel do tipo World que sera passada de parametro no renderer 
	private OrthographicCamera camera;	//Declara a variavel da camera
	private CGTActor personagem;
	// Inicializa uma constante relacionado a quantidade de blocos na horizontal que sera visto pela camera
	private float CAMERA_WIDTH;
	// Inicializa uma constante relacionado a quantidade de blocos na vertical que sera visto pela camera
	private float CAMERA_HEIGHT;
		
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	private SpriteBatch spriteBatch;	 
	private boolean debug = false; 		// Variavel que ira ativar o debug
	private int width;					
	private int height;					
	SpriteSheet spriteBob = new SpriteSheet();
	private Vector2 pos=new Vector2();
	
	
	public float getCAMERA_HEIGHT(){
		return this.CAMERA_HEIGHT;
	}
	
	public float getCAMERA_WIDTH(){
		return this.CAMERA_HEIGHT;
	}
	
	//Sera chamado cada vez que a tela é redimensionada e calcula as unidades em pixels.
	public void setSize (int w, int h) {
		//this.width = w;
		//this.height = h;
	}
	
	public WorldRenderer(MyWorld world, boolean debug) {
		this.world = world;
		this.width=Gdx.graphics.getWidth();
		this.height=Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(width, height);
		this.camera.position.set(width/2, height/2 , 0); 		
		this.debug = debug;												 
		spriteBatch = new SpriteBatch();
		loadSpriteSheet();
	}
	
	private void loadSpriteSheet() {
		personagem = world.getPersonagem();
	}

	/**
	 * Esta funcao e' chamada no metodo render da class GameScreen.
	 * Responsavel por desenhar todos os objetos na tela.
	 */
	public void render( ) {
		this.camera.update(); 			//Atualiza a tela
		spriteBatch.setProjectionMatrix(camera.combined); // movimentacao da camera
		spriteBatch.begin();
		drawGameObjects();
		drawCGTActor();
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	public void dispose(){
		world.getBackGround().dispose();
		spriteBatch.dispose();
		for(int i =0;i<world.getListaDeOpposite().size();i++){
			world.getListaDeOpposite().get(i).getTexture().dispose();
		}
		for(int i =0;i<world.getListaDeBonus().size();i++){
			world.getListaDeBonus().get(i).getTexture().dispose();
		}
		for(int i =0;i<world.getListaDeProjectili().size();i++){
			world.getListaDeProjectili().get(i).getTexture().dispose();
		}
	}

	private void drawGameObjects() {
		spriteBatch.draw(world.getBackGround(), 0, 0);
		//Desenha todos os Opposite
		for(int i =0;i<world.getListaDeOpposite().size();i++){
			spriteBatch.draw(world.listaDeOpposite.get(i).getSpriteSheet().CGTAnimation(personagem), world.listaDeOpposite.get(i).getPosition().x, world.listaDeOpposite.get(i).getPosition().y, world.listaDeOpposite.get(i).getBounds().width, world.listaDeOpposite.get(i).getBounds().height);
		}
		//Desenha todos os Bonus
		for(int i =0;i<world.getListaDeBonus().size();i++){
			spriteBatch.draw(world.getListaDeBonus().get(i).getTexture(), world.getListaDeBonus().get(i).getPosition().x, world.getListaDeBonus().get(i).getPosition().y, world.getListaDeBonus().get(i).getBounds().width, world.getListaDeBonus().get(i).getBounds().height);
		}
		//Desenha todos os Projectile
		for(int i =0;i<world.getListaDeProjectili().size();i++){
			spriteBatch.draw(world.getListaDeProjectili().get(i).getTexture(), world.getListaDeProjectili().get(i).getPosition().x, world.getListaDeProjectili().get(i).getPosition().y, world.getListaDeProjectili().get(i).getBounds().width, world.getListaDeProjectili().get(i).getBounds().height);
		}
	}

	/***
	 * Desenha o Actor na cena
	 */
	private void drawCGTActor() {
		//Desenha o Actor na cena
		//spriteBatch.draw(spriteBob.CGTActorAnimation(personagem), personagem.getPosition().x, personagem.getPosition().y, personagem.getBounds().width, personagem.getBounds().height);
		spriteBatch.draw(personagem.getSpriteSheet().CGTActorAnimation(personagem), personagem.getPosition().x, personagem.getPosition().y, personagem.getBounds().width, personagem.getBounds().height);
	}
	
	/***
	 * Metodo utilizada para fazer o debug
	 */
	private void drawDebug(){
		// render blocks	
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		// Recebe a posicao e o tamanho do personagem e o desenha na tela
		//Personagem bob = world.getPersonagem();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(personagem.getRectPer().x, personagem.getRectPer().y, personagem.getRectPer().getWidth(), personagem.getRectPer().getHeight());
			
		//Carrega o debug para todos os Opposite
		for(int i=0;i<world.getListaDeOpposite().size();i++){				
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getListaDeOpposite().get(i).getRectangle().x, world.getListaDeOpposite().get(i).getRectangle().y, world.getListaDeOpposite().get(i).getRectangle().getWidth(), world.getListaDeOpposite().get(i).getRectangle().getHeight());
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
			debugRenderer.rect(world.getListaDeProjectili().get(i).getRectangle().x, world.getListaDeProjectili().get(i).getRectangle().y, world.getListaDeProjectili().get(i).getRectangle().getWidth(), world.getListaDeProjectili().get(i).getRectangle().getHeight());
		}
		debugRenderer.end();
	}

	/**
	 * @return the cam
	 */
	public OrthographicCamera getCam() {
		return camera;
	}

	/**
	 * @param cam the cam to set
	 */
	public void setCam(OrthographicCamera cam) {
		this.camera = cam;
	}

	/**
	 * @return the col
	 */
	public boolean isColision() {
		boolean colisao = false;
		//Verifica se colidiu com algum Opposite
		for(int i=0; i < world.getListaDeOpposite().size(); i++){
			if(world.getListaDeOpposite().get(i).getRectangle().overlaps(personagem.getRectPer()) && world.getListaDeOpposite().get(i).isBlock())
				colisao=true;
		}
		
		//Verifica se colidiu com algum Bonus
		for(int i=0; i < world.getListaDeBonus().size(); i++){
			if(world.getListaDeBonus().get(i).getRectangle().overlaps(personagem.getRectPer()))
				colisao=true;
		}
		//Verifica se colidiu com algum Projectile
		for(int i=0; i < world.getListaDeProjectili().size(); i++){
			if(world.getListaDeProjectili().get(i).getRectangle().overlaps(personagem.getRectPer()))
				colisao=true;
		}
		
		if(!colisao){
			pos.x = personagem.getPosition().x;
			pos.y = personagem.getPosition().y;
		}else{
			personagem.getVelocity().x=0;
			personagem.getVelocity().y=0;
			personagem.setPosition(pos);
			colisao = false;
		}
		
		return colisao;
	}
}
