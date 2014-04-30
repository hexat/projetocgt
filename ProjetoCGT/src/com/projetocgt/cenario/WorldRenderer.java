package com.projetocgt.cenario;

import java.awt.geom.RectangularShape;

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
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.projetocgt.personagens.Personagem;
import com.projetocgt.personagens.SpritePersonagem;

public class WorldRenderer   {
	
	private MyWorld world;			//Declara a variavel do tipo World que sera passada de parametro no renderer 
	private OrthographicCamera cam;	//Declara a variavel da camera
	private Personagem personagem;
	private Personagem opositor;
	private Personagem opositor2;
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
	private Texture texturaAgua;
	
	
	private Joystick joystick;
	//private  Vector2 posFogo;
	
	private SpriteBatch spriteBatch;	// 
	private boolean debug = false; 		// Variavel que ira ativar o debug
	private int width;					//
	private int height;					//
	private float ppuX;					// Pixels per unit on the X axis
	private float ppuY;					// Pixels per unit on the Y axis
	SpritePersonagem spriteBob = new SpritePersonagem();
	float posI;
	float posF;
	
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
	
	public WorldRenderer(MyWorld world, boolean debug) {
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
		personagem = world.getPersonagem();
		opositor = world.getOpositor();
		opositor2 = world.getOpositor2();
		spriteBob = world.getSprite();
		fogo = world.getOpositorFogo();
		//Carrega as texturas que serao paresentadas na cena
		
		//bobTexture = new  Texture(Gdx.files.internal("data/Bob.png"));
		spriteBob.loadSpiteAniBonus("data/sprites/sprites_bomb.png");
		
		//Carrega o sprite do fogo do cenario
		spriteBob.loadingSpriteCenario("data/sprites/sprite_fogo.png", 2, 3);
		
		//Textura do opositor
		opositorTexture = new  Texture(Gdx.files.internal("data/Carros/carro.png"));
		
		//Carrega Joystick
		setaDireita = new  Texture(Gdx.files.internal("data/Joystick/setaDireita.png"));
		texturaSetaBaixo = new  Texture(Gdx.files.internal("data/Joystick/setaBaixo.png"));
		setaCima = new  Texture(Gdx.files.internal("data/Joystick/setaCima.png"));
		setaEsquerda = new  Texture(Gdx.files.internal("data/Joystick/setaEsquerda.png"));
		
		//Carrega a textura da agua
		texturaAgua =  new Texture("data/piscina.png");
		
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
		spriteBatch.begin();
		//debugoRender2.render(world2, cam.combined);
		//Desenha os blocos
		drawBlocks();
		//Dsenha o personagem
		drawPersonagem();
		//Desenha o texto com a posicao do personagem
		//printTexto();
		//Desenha o joystick
		//drawJoystick();
		//Verifica colisao
		colisaoOpositor(personagem,opositor,true);
		spriteBatch.end();
		if (debug)
			drawDebug();
	}
	
	private void drawJoystick() {
		spriteBatch.draw(texturaSetaBaixo,((world.getNumBlocosH()-2.5f)* ppuX), ((world.getNumBlocosV()-3.0f)*ppuY), joystick.SIZE * ppuX, joystick.SIZE * ppuY);
		spriteBatch.draw(setaDireita, (float) ((world.getNumBlocosH()-2.0f) * ppuX), (float)(world.getNumBlocosV()-2.7f) * ppuY, joystick.SIZE * ppuX, joystick.SIZE * ppuY);
		
		spriteBatch.draw(setaEsquerda, (float) ((world.getNumBlocosH()-3.0f) * ppuX), (float)(world.getNumBlocosV()-2.7) * ppuY, joystick.SIZE * ppuX, joystick.SIZE * ppuY);
		spriteBatch.draw(setaCima, (float) ((world.getNumBlocosH()-2.5f) * ppuX), (float)(world.getNumBlocosV()-2.4) * ppuY, joystick.SIZE * ppuX, joystick.SIZE * ppuY);
	}

	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
				spriteBatch.draw(block.getTexture(), block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
		spriteBatch.draw(texturaAgua,world.getAgua().getPosition().x*ppuX,world.getAgua().getPosition().y*ppuY,world.getAgua().getBounds().width * ppuX, world.getAgua().getBounds().height * ppuY);
	}

	private void drawPersonagem() {
		//A textura que ela vai desenhar "bobTexture" Posicao inicial
		//Posicao inicial "bob.getPosition().x * ppuX, bob.getPosition().y * ppuY"
		// Tamanho do desenho "Personagem.SIZE * ppuX, Personagem.SIZE * ppuY"
		
		//Verifica se o life do fogo ainda esta ativo
		if(fogo.getLife() >= 0)
			spriteBatch.draw(spriteBob.Cenario(personagem), fogo.getPosition().x * ppuX, fogo.getPosition().y * ppuY, fogo.getBounds().width * ppuX, fogo.getBounds().height* ppuY);
		spriteBatch.draw(spriteBob.aniNormal(world.getPersonagem()), personagem.getPosition().x * ppuX, personagem.getPosition().y * ppuY, personagem.getBounds().width * ppuX, personagem.getBounds().height * ppuY);
		spriteBatch.draw(opositorTexture, opositor.getPosition().x * ppuX, opositor.getPosition().y * ppuY, opositor.getBounds().width * ppuX, opositor.getBounds().height * ppuY);
	}
	
	private Rectangle recta;
	private Rectangle rectB;
	
	private void drawDebug(){
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
				//Personagem bob = world.getPersonagem();
				rectB = personagem.getBounds();
				float x1 = personagem.getPosition().x + rectB.x;
				float y1 = personagem.getPosition().y + rectB.y;
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(x1+0.08f, y1+0.08f, rectB.getWidth()/2, rectB.getHeight()/2);
				
				//Personagem opositor = world.getOpositor();
				recta = opositor.getBounds();
				float x2 = opositor.getPosition().x + recta.x;
				float y2 = opositor.getPosition().y + recta.y;
				
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(x2, y2, recta.getWidth(), recta.getHeight());
				
				debugRenderer.end();
	}

	/***
	 * Verifica se o persanagem colidiu com algumn opositor
	 * @param Personagem personagem, Personagem opositor, boolean dano
	 */
	private void colisaoOpositor(Personagem personagem,Personagem opositor,boolean dano){
		boolean flag = false;
		boolean flagFogo=false;
		//
		Rectangle rectPer = rectPer= new Rectangle((personagem.getPosition().x+0.09f)* ppuX , (personagem.getPosition().y+0.09f)* ppuY, personagem.getBounds().width/2* ppuX, personagem.getBounds().height/2* ppuY);
		//
		Rectangle rectOpo =  new Rectangle(opositor.getPosition().x* ppuX , opositor.getPosition().y* ppuY , opositor.getBounds().width* ppuX, opositor.getBounds().height* ppuY);
		
		//Cria a colisao da agua
		Rectangle rectAgua =  new Rectangle(world.getAgua().getPosition().x* ppuX , world.getAgua().getPosition().y* ppuY , world.getAgua().getBounds().width* ppuX, world.getAgua().getBounds().height* ppuY);
		
		//Cria colisao no fogo
		Rectangle rectFogo =  new Rectangle(fogo.getPosition().x * ppuX, fogo.getPosition().y * ppuY, fogo.getBounds().width * ppuX, fogo.getBounds().height * ppuY);
		//rectFogo.merge(rectPer);
		//colidiu cmo o fogo
		//Verifca se o life do fogo esta ativo
		if(rectFogo.overlaps(rectPer) && fogo.getLife()>=0){
			personagem.setColidiu(true);
			personagem.setBonus(0);
			//Colidiu com o fogo
			flagFogo = true;
			//Evita o personagem ficar carregando varias imagens
			//So ira carregar as imagens quando o personagem estiver sem bonus
			if(flagFogo && bonus){
			fogo.tiraUmDoLife();
			//Muda de animacao
			spriteBob.setLinhaDoSpriteUp(2);
			spriteBob.setLinhaDoSpriteDown(1);
			spriteBob.setLinhaDoSpriteLeft(4);
			spriteBob.setLinhaDoSpriteRight(3);
			//Carrega as imagens para o personagem com fogo
			spriteBob.loadSpiteAniBonus("data/sprites/sprites_bomb.png");
			flagFogo = false;
			}
			bonus=false;
		}
		else
			personagem.setColidiu(false);
		
		//Colidiu com a agua
		if(rectAgua.overlaps(rectPer)){
			personagem.setColidiu(true);
			personagem.setBonus(1);
			flag = true;
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
		if(rectOpo.overlaps(rectPer)){
			personagem.acaoAtropelamento(opositor,0.2f);
			//Se a opcao dano for habilitado
			if(dano){
				personagem.tiraUmDoLife();
				if(personagem.getLife()==0){
					System.out.println("Game over");
				}
			}	
		}		
	}
}
