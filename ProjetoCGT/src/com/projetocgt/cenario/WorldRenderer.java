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
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
	// Inicializa uma constante relacionado a quantidade de blocos na horizontal que sera visto pela camera
	private float CAMERA_WIDTH;
	// Inicializa uma constante relacionado a quantidade de blocos na vertical que sera visto pela camera
	private float CAMERA_HEIGHT;
	
		
	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private Texture opositorTexture;
	private Texture texturaSetaBaixo;
	private Texture setaDireita;
	private Texture setaEsquerda;
	private Texture setaCima;
	
	
	
	private Joystick joystick;
	private  Vector2 posFogo;
	
	private SpriteBatch spriteBatch;	// 
	private boolean debug = false; 		// Variavel que ira ativar o debug
	private int width;					//
	private int height;					//
	private float ppuX;					// Pixels per unit on the X axis
	private float ppuY;					// Pixels per unit on the Y axis
	SpritePersonagem spriteBob = new SpritePersonagem();
	float posI;
	float posF;
	
	private BitmapFont currentFont;
	private Vector2 posiI;
	boolean flag=true;
	
	private World mundo;
	private BodyDef bodyPer; 	
	
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
		
		//mundo = new World(posFogo, debug);
		//mundo.createBody(bodyPer);
		//bodyPer = new BodyDef();
		//bodyPer.type = BodyType.DynamicBody;
		
		personagem = world.getPersonagem();
		opositor = world.getOpositor();
		opositor2 = world.getOpositor2();
		//Carrega as texturas que serao paresentadas na cena
		
		//bobTexture = new  Texture(Gdx.files.internal("data/Bob.png"));
		spriteBob.AniRL2("data/sprites/sprites_bomb.png");
		//
		spriteBob.AnimaCenario("data/sprites/sprite_fogo.png", 2, 3);
		posFogo = new Vector2(2,2);
		
		//Textura do opositor
		opositorTexture = new  Texture(Gdx.files.internal("data/Carros/carro.png"));
		//Texto utilizado para printar a posicao do personagem na tela
		currentFont = new BitmapFont();
		
		//Carrega Joystick
		setaDireita = new  Texture(Gdx.files.internal("data/Joystick/setaDireita.png"));
		texturaSetaBaixo = new  Texture(Gdx.files.internal("data/Joystick/setaBaixo.png"));
		setaCima = new  Texture(Gdx.files.internal("data/Joystick/setaCima.png"));
		setaEsquerda = new  Texture(Gdx.files.internal("data/Joystick/setaEsquerda.png"));
		
		//setaBaixo.setTextura(texturaSetaBaixo);
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
		//Desenha os blocos
		drawBlocks();
		//Dsenha o personagem
		drawPersonagem();
		//Desenha o texto com a posicao do personagem
		//printTexto();
		//Desenha o joystick
		drawJoystick();
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
		
		//world.getJoystickBaixo().getPosition().x= (world.getNumBlocosH()-3f) * ppuX;
		//world.getJoystickBaixo().getPosition().y= (world.getNumBlocosV()-3)*ppuY;
	}

	//Mostra um valor na tela.
	//Retorna o vetor que ela esta mostrando
	public Vector2 printTexto(){
		
		Vector2 vector2 = personagem.getPosition();
		
		if(flag==true){
			posiI = new Vector2(personagem.getPosition());
			flag=false;
		}
			
		//Verifica se o personagem esta olhando para a esquerda
		//Se ele estiver olhando para a esquerda printo e valor normal 
		//Caso contrario ele esta olhando para a direita, logo somo a base dela com a sua posi��o
		if (personagem.isFacingLeft()) {
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
			currentFont.draw(spriteBatch,  "( "+((int)(vector2.x+personagem.getBounds().getWidth())), 40, 60);

			//Analisa a posicao inicial com a posicao atual
			//Verifica se o personagem entrou no bloco
			if((int)(vector2.x+personagem.getBounds().getWidth())!=(int)posiI.x){
				System.out.println("Entrou no bloco");
				//Recebe uma nova posicao inicial
				posiI.x=vector2.x+personagem.getBounds().getWidth();
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
		//Personagem bob = world.getPersonagem();
		
		//A textura que ela vai desenhar "bobTexture" Posicao inicial
		//Posicao inicial "bob.getPosition().x * ppuX, bob.getPosition().y * ppuY"
		// Tamanho do desenho "Personagem.SIZE * ppuX, Personagem.SIZE * ppuY"
		//spriteBatch.draw(bobTexture, bob.getPosition().x * ppuX, bob.getPosition().y * ppuY, Personagem.SIZE * ppuX, Personagem.SIZE * ppuY);
		
		spriteBatch.draw(spriteBob.Cenario(personagem), posFogo.x * ppuX, posFogo.y * ppuY, Personagem.SIZE * ppuX, Personagem.SIZE * ppuY);
		
		spriteBatch.draw(spriteBob.AniCreCorrendo(world.getPersonagem()), personagem.getPosition().x * ppuX, personagem.getPosition().y * ppuY, Personagem.SIZE * ppuX, Personagem.SIZE * ppuY);
		//bodyPer.position.set(personagem.getPosition().x * ppuX, personagem.getPosition().y * ppuY);
		
		spriteBatch.draw(opositorTexture, opositor.getPosition().x * ppuX, opositor.getPosition().y * ppuY, Personagem.SIZE * ppuX, Personagem.SIZE * ppuY);
		
		//spriteBatch.draw(opositorTexture, opositor2.getPosition().x * ppuX, opositor2.getPosition().y * ppuY, Personagem.SIZE * ppuX, Personagem.SIZE * ppuY);
	
		
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
				Rectangle rectB = bob.getBounds();
				float x1 = bob.getPosition().x + rectB.x;
				float y1 = bob.getPosition().y + rectB.y;
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				debugRenderer.rect(x1, y1, rectB.getWidth(), rectB.getHeight());
				
				Personagem opositor = world.getOpositor();
				Rectangle recta = opositor.getBounds();
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

		BoundingBox box = new BoundingBox();
		//
		Rectangle rectPer = rectPer= new Rectangle(personagem.getPosition().x* ppuX , personagem.getPosition().y* ppuY, personagem.getBounds().width* ppuX, personagem.getBounds().height* ppuY);
		//
		Rectangle rectOpo =  new Rectangle(opositor.getPosition().x* ppuX , opositor.getPosition().y* ppuY , opositor.getBounds().width* ppuX, opositor.getBounds().height* ppuY);
		//
		if(rectOpo.overlaps(rectPer)){
			
			personagem.acaoAtropelamento(opositor,0.05f);
			//Se a opcao dano for habilitado
			if(dano){
				int live=personagem.getLife();
				live--;
				personagem.setLife(live);
				System.out.println(personagem.getLife());
				if(live==0){
					System.out.println("Game over");
				}
			}	
		}		
	}
}
