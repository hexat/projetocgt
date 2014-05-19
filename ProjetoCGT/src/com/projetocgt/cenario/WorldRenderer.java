package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	
	private Texture opositorTexture;
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

		CAMERA_HEIGHT = world.getNumBlocosV();
		CAMERA_WIDTH = world.getNumBlocosH();
		this.width=Gdx.graphics.getWidth();
		this.height=Gdx.graphics.getHeight();
		//Inicializa a variavel de camera passando os parametros de quantos blocos ela vai ver na horizontal e vertical
		this.camera = new OrthographicCamera(width, height);
		this.camera.position.set(width/2, height/2 , 0); //Faz um set da posicao da camera no mundo do jogo		
		this.debug = debug;												 
		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	
	private void loadTextures() {
		personagem = world.getPersonagem();
		spriteBob = world.getPersonagem().getSprite();
		//Carrega as texturas que serao paresentadas na cena
		
		//bobTexture = new  Texture(Gdx.files.internal("data/Bob.png"));
		spriteBob.loadSpiteAniBonus("data/SpriteBob/SpriteSheet_bombeiro.png");
		
		//Carrega o sprite do fogo do cenario
		spriteBob.loadingSpriteFogo("data/Sprites/SpriteSheet_fogo.png", 2, 2);
		
		//Textura do opositor
		opositorTexture = new  Texture(Gdx.files.internal("data/Carros/carro.png"));
	}

	/**
	 * Esta funcao e' chamada no metodo render da class GameScreen.
	 * Responsavel por desenhar todos os objetos na tela.
	 */
	public void render( ) {
		this.camera.update(); 			//Atualiza a tela
		spriteBatch.begin();
		
		spriteBatch.setProjectionMatrix(camera.combined); // movimentacao da camera
		
		drawGameObjects();
		
		drawCGTActor();
		
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	public void dispose(){
		opositorTexture.dispose();
		spriteBatch.dispose();
		for(int i =0;i<world.getListaPersonagens().size();i++){
			world.listaActor.get(i).getTexturePersonagem().dispose();
		}
	}

	private void drawGameObjects() {
		spriteBatch.draw(world.getBackGround(), 0, 0);
		for(int i =0;i<world.getListaPersonagens().size();i++){
			spriteBatch.draw(world.listaActor.get(i).getTexturePersonagem(), world.listaActor.get(i).getPosition().x, world.listaActor.get(i).getPosition().y, world.listaActor.get(i).getBounds().width, world.listaActor.get(i).getBounds().height);
		}
	}

	/**
	 * A textura que ela vai desenhar "bobTexture" Posicao inicial
	 * Posicao inicial "bob.getPosition().x , bob.getPosition().y "
	 * Tamanho do desenho "Personagem.SIZE , Personagem.SIZE "
	 * Verifica se o life do fogo ainda esta ativo
	 */
	private void drawCGTActor() {
		spriteBatch.draw(spriteBob.aniNormal(world.getPersonagem()), personagem.getPosition().x, personagem.getPosition().y, personagem.getBounds().width, personagem.getBounds().height);
	}
	
	/***
	 * Funcao utilizada para fazer o debug
	 */
	private void drawDebug(){
		// render blocks	
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		// Recebe a posicao e o tamanho do personagem e o desenha na tela
		//Personagem bob = world.getPersonagem();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(personagem.getRectPer().x, personagem.getRectPer().y, personagem.getRectPer().getWidth(), personagem.getRectPer().getHeight());
			
		
		for(int i=0;i<world.getListaPersonagens().size();i++){				
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getListaPersonagens().get(i).getRectPer().x, world.getListaPersonagens().get(i).getRectPer().y, world.getListaPersonagens().get(i).getRectPer().getWidth(), world.getListaPersonagens().get(i).getRectPer().getHeight());
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
		boolean col = false;
		for(int i=0; i < world.getListaPersonagens().size(); i++){
			if(world.getListaPersonagens().get(i).getRectPer().overlaps(personagem.getRectPer()))
				col=true;
		}

		if(!col){
			pos.x = personagem.getPosition().x;
			pos.y = personagem.getPosition().y;
		}else{
			personagem.getVelocity().x=0;
			personagem.getVelocity().y=0;
			personagem.setPosition(pos);
			col = false;
		}
		
		return col;
	}
}
