package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.Personagem;

public class WorldRenderer {
	
	private World world;			//Declara a variavel do tipo World que sera passada de parametro no renderer 
	private OrthographicCamera cam;	//Declara a variavel da camera
	private Personagem bob;
	// Inicializa uma constante relacionado a quantidade de blocos na horizontal que sera visto pela camera
	private float CAMERA_WIDTH;
	// Inicializa uma constante relacionado a quantidade de blocos na vertical que sera visto pela camera
	private float CAMERA_HEIGHT;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	/** Textures **/
	private Texture bobTexture;

	private SpriteBatch spriteBatch;	// 
	private boolean debug = false; 		// Variavel que ira ativar o debug
	private int width;					//
	private int height;					//
	private float ppuX;					// Pixels per unit on the X axis
	private float ppuY;					// Pixels per unit on the Y axis
	
	
	private BitmapFont currentFont;
	private Vector2 posiI;
	boolean flag=true;
	
	public float getCAMERA_HEIGHT(){
		return this.CAMERA_HEIGHT;
	}
	
	public float getCAMERA_WIDTH(){
		return this.CAMERA_HEIGHT;
	}
	//Sera chamado cada vez que a tela é redimensionada e calcula as unidades em pixels.
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)(width) / CAMERA_WIDTH;
		ppuY = (float)(height) / CAMERA_HEIGHT;
	}
	
	public WorldRenderer(World world, boolean debug) {
		this.world = world;

		CAMERA_HEIGHT = world.getNumBlocosV();
		CAMERA_WIDTH = world.getNumBlocosH();
				
		//Inicializa a variavel de camera passando os parametros de quantos blocos ela vai ver na horizontal e vertical
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0); //Faz um set da posicao da camera no mundo do jogo
		this.cam.update();												 //Atualiza a tela			
		this.debug = debug;												 
		spriteBatch = new SpriteBatch();
		
		
		loadTextures();
	}
	
	private void loadTextures() {
		
		//Carrega as texturas que serao paresentadas na cena
		bobTexture = new  Texture(Gdx.files.internal("data/Bob.png"));
		//Texto utilizado para printar a posicao do personagem na tela
		currentFont = new BitmapFont();
	}

	public void render() {
		spriteBatch.begin();
		//Desenha os blocos
		drawBlocks();
		//Dsenha o personagem
		drawPersonagem();
		//Desenha o texto com a posicao do personagem
//		printTexto();
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	
	//Mostra um valor na tela.
	//Retorna o vetor que ela esta mostrando
	public Vector2 printTexto(){
		bob = world.getPersonagem();
		Vector2 vector2 = bob.getPosition();
		
		if(flag==true){
			posiI = new Vector2(bob.getPosition());
			flag=false;
		}
			
		//Verifica se o personagem esta olhando para a esquerda
		//Se ele estiver olhando para a esquerda printo e valor normal 
		//Caso contrario ele esta olhando para a direita, logo somo a base dela com a sua posi��o
		if (bob.isFacingLeft()) {
			//Valor normal, sem somar ser somado a base
			currentFont.draw(spriteBatch,  "( "+(int)vector2.x, 40, 60);
			
			//Analisa a posicao inicial com a posicao atual
			//Verifica se o personagem entrou no bloco
			if( (int)vector2.x != (int)posiI.x ){
				System.out.println("Entrou no bloco");
				//Recebe uma nova posicao inicial
				posiI.x=vector2.x;
			}
			
		} else {
			//Valor  somado com a base
			currentFont.draw(spriteBatch,  "( "+((int)(vector2.x+bob.getBounds().getWidth())), 40, 60);

			//Analisa a posicao inicial com a posicao atual
			//Verifica se o personagem entrou no bloco
			if((int)(vector2.x+bob.getBounds().getWidth())!=(int)posiI.x){
				System.out.println("Entrou no bloco");
				//Recebe uma nova posicao inicial
				posiI.x=vector2.x+bob.getBounds().getWidth();
			}
		}	
			
		//Verifica se o personagem esta olhando para a baixo
			//Se ele estiver olhando para a baixo printo e valor normal 
			//Caso contrario ele esta olhando para a cima, logo somo a altura dela com a sua posi��o na vertical
			if (bob.isFacingLeft()) {
				//Valor normal, sem somar ser somado a altura
				currentFont.draw(spriteBatch,  (int)vector2.y+ " )", 130, 60);
				//Analisa a posicao inicial com a posicao atua
				//Verifica se o personagem entrou no bloco
				if((int)vector2.y!=(int)posiI.y){
					System.out.println("Entrou no bloco");
					//Recebe uma nova posicao inicial
					posiI.y=vector2.y;
				}
				
			}else {
				//Valor  somado com a altura
				currentFont.draw(spriteBatch,  (int)(vector2.y+bob.getBounds().getHeight())+ " )", 130, 60);
				//Analisa a posicao inicial com a posicao atual
				//Verifica se o personagem entrou no bloco
				if((int)(vector2.y+bob.getBounds().getHeight())!=(int)posiI.y){
					System.out.println("Entrou no bloco");
					//Recebe uma nova posicao inicial
					posiI.y=vector2.y+bob.getBounds().getHeight();
				}
			}
			currentFont.draw(spriteBatch," Posicao do Personagem", 200, 60);
			return vector2;
		}
		
	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
				spriteBatch.draw(block.getTexture(), block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}

	private void drawPersonagem() {
		Personagem bob = world.getPersonagem();
		
		//A textura que ela vai desenhar "bobTexture" Posicao inicial
		//Posicao inicial "bob.getPosition().x * ppuX, bob.getPosition().y * ppuY"
		// Tamanho do desenho "Personagem.SIZE * ppuX, Personagem.SIZE * ppuY"
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
				// Recebe a posicao e o tamanho do personagem e o desenha na tela
				Personagem bob = world.getPersonagem();
				Rectangle rect = bob.getBounds();
				float x1 = bob.getPosition().x + rect.x;
				float y1 = bob.getPosition().y + rect.y;
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(x1, y1, rect.width, rect.height);
				debugRenderer.end();
	}
}
