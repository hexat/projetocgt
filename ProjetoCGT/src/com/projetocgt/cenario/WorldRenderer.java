package com.projetocgt.cenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.SpritePersonagem;

public class WorldRenderer   {
	
	private MyWorld world;			//Declara a variavel do tipo World que sera passada de parametro no renderer 
	private OrthographicCamera cam;	//Declara a variavel da camera
	private Personagem personagem;
	private Personagem opositor;
	private Personagem fogo;
	// Inicializa uma constante relacionado a quantidade de blocos na horizontal que sera visto pela camera
	private float CAMERA_WIDTH;
	// Inicializa uma constante relacionado a quantidade de blocos na vertical que sera visto pela camera
	private float CAMERA_HEIGHT;
	private boolean bonus=false;
		
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private Texture opositorTexture;
	private Texture texturaSetaBaixo;
	private Texture setaDireita;
	private Texture setaEsquerda;
	private Texture setaCima;
	
	private Joystick joystick;
	
	private SpriteBatch spriteBatch;	// 
	private boolean debug = false; 		// Variavel que ira ativar o debug
	private int width;					//
	private int height;					//
	private float ppuX;					// Pixels per unit on the X axis
	private float ppuY;					// Pixels per unit on the Y axis
	SpritePersonagem spriteBob = new SpritePersonagem();
	private Vector2 pos=new Vector2();
	private boolean col=false;
	
	
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
		ppuX = 1;
		ppuY = 1;
	}
	
	public WorldRenderer(MyWorld world, boolean debug) {
		this.world = world;

		CAMERA_HEIGHT = world.getNumBlocosV();
		CAMERA_WIDTH = world.getNumBlocosH();
		this.width=Gdx.graphics.getWidth();
		this.height=Gdx.graphics.getHeight();
		//Inicializa a variavel de camera passando os parametros de quantos blocos ela vai ver na horizontal e vertical
		this.cam = new OrthographicCamera(width, height);
		this.cam.position.set(width/2, height/2 , 0); //Faz um set da posicao da camera no mundo do jogo		
		this.debug = debug;												 
		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	
	private void loadTextures() {
		personagem = world.getPersonagem();
		opositor = world.getOpositor();
		spriteBob = world.getSprite();
		fogo = world.getOpositorFogo();
		//Carrega as texturas que serao paresentadas na cena
		
		//bobTexture = new  Texture(Gdx.files.internal("data/Bob.png"));
		spriteBob.loadSpiteAniBonus("data/SpriteBob/SpriteSheet_bombeiro.png");
		
		//Carrega o sprite do fogo do cenario
		spriteBob.loadingSpriteFogo("data/sprites/sprite_car_char_fumaca_agua_fogo_.png", 8, 5);
		
		//Textura do opositor
		opositorTexture = new  Texture(Gdx.files.internal("data/Carros/carro.png"));
		
		//Carrega Joystick
		setaDireita = new  Texture(Gdx.files.internal("data/Joystick/setaDireita.png"));
		texturaSetaBaixo = new  Texture(Gdx.files.internal("data/Joystick/setaBaixo.png"));
		setaCima = new  Texture(Gdx.files.internal("data/Joystick/setaCima.png"));
		setaEsquerda = new  Texture(Gdx.files.internal("data/Joystick/setaEsquerda.png"));
		
		world.getJoystickBaixo().getPosition().x= ((world.getNumBlocosH()-2.5f));
		world.getJoystickBaixo().getPosition().y= ((world.getNumBlocosV()-3.0f));
		
		world.getJoystickDireita().getPosition().x=world.getNumBlocosH()-2.0f;
		world.getJoystickDireita().getPosition().y=world.getNumBlocosV()-2.7f;
		
		world.getJoystickEsquerda().getPosition().x=world.getNumBlocosH()-3.0f;
		world.getJoystickEsquerda().getPosition().y=world.getNumBlocosV()-2.7f;
		
		world.getJoystickCima().getPosition().x=world.getNumBlocosH()-2.5f;
		world.getJoystickCima().getPosition().y=world.getNumBlocosV()-2.4f;
		
		
	}
	
	//Sera chamado a todo instante na cena
	public void render( ) {
		this.cam.update(); 			//Atualiza a tela
		spriteBatch.begin();
			spriteBatch.setProjectionMatrix(cam.combined);
			//Desenha os blocos
			drawBlocks();
			//Dsenha o personagem
			drawPersonagem();
			//Desenha o texto com a posicao do personagem
			//Desenha o joystick
			//drawJoystick();
		//Verifica colisao
		colisaoOpositor(personagem,opositor,true);
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	public void dispose(){
		opositorTexture.dispose();
		spriteBatch.dispose();
		for(int i =0;i<world.getListaPersonagens().size;i++){
			world.listaPersonagens.get(i).getTexturePersonagem().dispose();
		}
	}
	private void drawJoystick() {
		spriteBatch.draw(texturaSetaBaixo,((world.getNumBlocosH()-2.5f)* ppuX), ((world.getNumBlocosV()-3.0f)*ppuY), joystick.SIZE * ppuX, joystick.SIZE * ppuY);
		spriteBatch.draw(setaDireita, (float) ((world.getNumBlocosH()-2.0f) * ppuX), (float)(world.getNumBlocosV()-2.7f) * ppuY, joystick.SIZE * ppuX, joystick.SIZE * ppuY);
		
		spriteBatch.draw(setaEsquerda, (float) ((world.getNumBlocosH()-3.0f) * ppuX), (float)(world.getNumBlocosV()-2.7) * ppuY, joystick.SIZE * ppuX, joystick.SIZE * ppuY);
		spriteBatch.draw(setaCima, (float) ((world.getNumBlocosH()-2.5f) * ppuX), (float)(world.getNumBlocosV()-2.4) * ppuY, joystick.SIZE * ppuX, joystick.SIZE * ppuY);
	}

	private void drawBlocks() {
		/*for (Block block : world.getBlocks()) {
				spriteBatch.draw(block.getTexture(), block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}*/
		spriteBatch.draw(world.getBackGround(),0,0);
		for(int i =0;i<world.getListaPersonagens().size;i++){
			spriteBatch.draw(world.listaPersonagens.get(i).getTexturePersonagem(), world.listaPersonagens.get(i).getPosition().x * ppuX, world.listaPersonagens.get(i).getPosition().y * ppuY, world.listaPersonagens.get(i).getBounds().width * ppuX, world.listaPersonagens.get(i).getBounds().height * ppuY);
		}
		
	}

	private void drawPersonagem() {
		//A textura que ela vai desenhar "bobTexture" Posicao inicial
		//Posicao inicial "bob.getPosition().x * ppuX, bob.getPosition().y * ppuY"
		// Tamanho do desenho "Personagem.SIZE * ppuX, Personagem.SIZE * ppuY"
		//Verifica se o life do fogo ainda esta ativo
		if(fogo.getLife() >= 0)
			spriteBatch.draw(spriteBob.Cenario(personagem), fogo.getPosition().x , fogo.getPosition().y, fogo.getBounds().width, fogo.getBounds().height);
		spriteBatch.draw(spriteBob.aniNormal(world.getPersonagem()), personagem.getPosition().x * ppuX, personagem.getPosition().y * ppuY, personagem.getBounds().width * ppuX, personagem.getBounds().height * ppuY);
		spriteBatch.draw(opositorTexture, opositor.getPosition().x * ppuX, opositor.getPosition().y * ppuY, opositor.getBounds().width * ppuX, opositor.getBounds().height * ppuY);
		
	}
	
	/***
	 * Funcao utilizada para fazer o debug
	 */
	private void drawDebug(){
		// render blocks	
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);

		// Recebe a posicao e o tamanho do personagem e o desenha na tela
		//Personagem bob = world.getPersonagem();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(personagem.getRectPer().x, personagem.getRectPer().y, personagem.getRectPer().getWidth(), personagem.getRectPer().getHeight());
			
		
		for(int i=0;i<world.getListaPersonagens().size;i++){				
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(world.getListaPersonagens().get(i).getRectPer().x, world.getListaPersonagens().get(i).getRectPer().y, world.getListaPersonagens().get(i).getRectPer().getWidth(), world.getListaPersonagens().get(i).getRectPer().getHeight());
		}
		debugRenderer.end();
	}
	
	
	/***
	 * Verifica se o persanagem colidiu com algumn opositor
	 * @param Personagem personagem, Personagem opositor, boolean dano
	 */
	private void colisaoOpositor(Personagem personagem,Personagem opositor,boolean dano){
		boolean flag = false;
		boolean flagFogo=false;
		personagem.setColidiu(false);
		
		for(int i=0; i < world.getListaPersonagens().size; i++){
			if(world.getListaPersonagens().get(i).getRectPer().overlaps(personagem.getRectPer()))
				col=true;
		}
		
		//colidiu cmo o fogo
		//Verifca se o life do fogo esta ativo
		if(fogo.getRectPer().overlaps(personagem.getRectPer()) && fogo.getLife()>=0){
			personagem.setColidiu(true);
			personagem.setBonus(0);
			//Colidiu com o fogo
			//col=true;
			flagFogo = true;
			//Evita o personagem ficar carregando varias imagens
			//So ira carregar as imagens quando o personagem estiver sem bonus
			if(flagFogo && bonus){
			fogo.tiraUmDoLife();
			//Muda de animacao
			spriteBob.setLinhaDoSpriteUp(3);
			spriteBob.setLinhaDoSpriteDown(2);
			spriteBob.setLinhaDoSpriteLeft(1);
			spriteBob.setLinhaDoSpriteRight(1);
			//Carrega as imagens para o personagem com fogo
			spriteBob.loadSpiteAniBonus("data/SpriteBob/SpriteSheet_bombeiro.png");
			flagFogo = false;
			}
			bonus=false;
		}
		
		//Colidiu com a agua
		if(world.getAgua().getRectPer().overlaps(personagem.getRectPer())){
			personagem.setColidiu(true);
			personagem.setBonus(1);
			flag = true;
			//col=true;
			if(flag  && !bonus){
			spriteBob.setLinhaDoSpriteUp(5);
			spriteBob.setLinhaDoSpriteDown(7);
			spriteBob.setLinhaDoSpriteLeft(8);
			spriteBob.setLinhaDoSpriteRight(6);
			//Carrega as imagens para o personagem com agua
			spriteBob.loadSpiteAniBonus("data/sprites/sprites_bomb.png");
			flag = false;
			}
			bonus=true;
		}
		//Colidiu com o carro
		if(opositor.getRectPer().overlaps(personagem.getRectPer())){
			personagem.setColidiu(true);
			//col=true;
			//Se a opcao dano for habilitado
			if(dano){
				personagem.tiraUmDoLife();
				if(personagem.getLife()==0){
					System.out.println("Game over");
				}
			}	
		}
		
		if(!col){
			pos.x = personagem.getPosition().x;
			pos.y = personagem.getPosition().y;
		}else{
			personagem.getVelocity().x=0;
			personagem.getVelocity().y=0;
			personagem.setPosition(pos);
			col=false;
		}
	}

	/**
	 * @return the cam
	 */
	public OrthographicCamera getCam() {
		return cam;
	}

	/**
	 * @param cam the cam to set
	 */
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

	/**
	 * @return the col
	 */
	public boolean isCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(boolean col) {
		this.col = col;
	}
}
