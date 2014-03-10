package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.GameScreen;
import com.projetocgt.personagens.Personagem;

public class WorldRenderer {
	
	private World world;			//Declara a variável do tipo World que será passada de parametro no renderer 
	private OrthographicCamera cam;	//Declara a variável da camera
	private GameScreen pos;
	
	// Inicializa uma constante relacionado a quantidade de blocos na horizontal que será visto pela camera
	private static final float CAMERA_WIDTH = 10f;
	// Inicializa uma constante relacionado a quantidade de blocos na vertical que será visto pela camera
	private static final float CAMERA_HEIGHT = 7f;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	/** Textures **/
	private Texture bobTexture;
	private Texture blockTexture;

	private SpriteBatch spriteBatch;	// 
	private boolean debug = false; 		// Variável que irá ativar o debug
	private int width;					//
	private int height;					//
	private float ppuX;					// Pixels per unit on the X axis
	private float ppuY;					// Pixels per unit on the Y axis
	
	private BitmapFont currentFont;
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		//Inicializa a variável de câmera passando os parametros de quantos blocos ela vai ver na horizontal e vertical
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0); //Faz um set da posição da camera no mundo do jogo
		this.cam.update();												 //Atualiza a tela			
		this.debug = debug;												 
		spriteBatch = new SpriteBatch();
		Texture.setEnforcePotImages(false);//desabilita a opção de potencia de dois.
		loadTextures();
	}
	
	private void loadTextures() {
		bobTexture = new  Texture(Gdx.files.internal("data/Bob.png"));
		blockTexture = new Texture(Gdx.files.internal("data/piscina.png"));
		currentFont = new BitmapFont();
	}

	public void render() {

		spriteBatch.begin();
		drawBlocks();
		drawBob();
		printTexto();
		//print(pos.getPosX(),pos.getPosY(), 140, 70);
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	
	//Mostra um valor na tela.
	//Retorna o vetor que ela esta mostrando
		public Vector2 printTexto(){

			Personagem bob = world.getPersonagem();
			Vector2 vector2 = bob.getPosition();
			float pos = (int)(vector2.x+bob.getBounds().getWidth());
			
			//Verifica se o personagem esta olhando para a esquerda
			//Se ele estiver olhando para a esquerda printo e valor normal 
			//Caso contrario ele esta olhando para a direita, logo somo a base dela com a sua posição
			if (bob.isFacingLeft()) {
				//Valor normal, sem somar ser somado a base
				currentFont.draw(spriteBatch,  "( "+vector2.x, 40, 60);} 
			else {
				//Valor  somado com a base
				currentFont.draw(spriteBatch,  "( "+(vector2.x+bob.getBounds().getWidth()), 40, 60);}
			
			//Verifica se o personagem esta olhando para a baixo
			//Se ele estiver olhando para a baixo printo e valor normal 
			//Caso contrario ele esta olhando para a cima, logo somo a altura dela com a sua posição na vertical
			if (bob.isFacingLeft()) {
				//Valor normal, sem somar ser somado a altura
				currentFont.draw(spriteBatch,  vector2.y+ " )", 130, 60);}
			else {
				//Valor  somado com a altura
				currentFont.draw(spriteBatch,  vector2.y+bob.getBounds().height+ " )", 130, 60);}
			currentFont.draw(spriteBatch," Posição do Personagem", 200, 60);
			return vector2;
		}
		public void print(int x,int y,int posX,int posY){
			currentFont.draw(spriteBatch,  "( "+x, posX, posY);
			currentFont.draw(spriteBatch,  y+ " )", posX, posY);
		}
	
	private void drawBlocks() {
		for (Object b : world.getBlocks()) {
			Block block = (Block) b;
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}

	private void drawBob() {
		Personagem bob = world.getPersonagem();
		spriteBatch.draw(bobTexture, bob.getPosition().x * ppuX, bob.getPosition().y * ppuY, Personagem.SIZE * ppuX, Personagem.SIZE * ppuY);
	}
	
	private void drawDebug()
	{
		// render blocks
				debugRenderer.setProjectionMatrix(cam.combined);
				debugRenderer.begin(ShapeType.Line);
				//Percorre um Array de blocos e os desenha na tela
				for (Object b : world.getBlocks()) {
					Block block = (Block) b;
					Rectangle rect = block.getBounds();
					float x1 = block.getPosition().x + rect.x;
					float y1 = block.getPosition().y + rect.y;
					debugRenderer.setColor(new Color(1, 0, 0, 1));
					debugRenderer.rect(x1, y1, rect.width, rect.height);
				}
				// Recebe a posição e o tamanho do personagem e o desenha na tela
				Personagem bob = world.getPersonagem();
				Rectangle rect = bob.getBounds();
				float x1 = bob.getPosition().x + rect.x;
				float y1 = bob.getPosition().y + rect.y;
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(x1, y1, rect.width, rect.height);
				debugRenderer.end();
	}
}
