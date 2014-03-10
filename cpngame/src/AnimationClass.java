import gui.AnimatedSprite;
import gui.util.Point2D;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.helper.Lista;
import core.petri.ElementosCPN;
import core.petri.entity.Ficha;
import core.petri.entity.Place;

//Comentário de teste de commit e push a partir do eclipse.
//Agora parece que está ok.

public class AnimationClass extends Applet implements Runnable, KeyListener {
	// espacamento na largura
	public static final int PADDING_WIDTH = 0;
	// espacamento na altura
	public static final int PADDING_HEIGHT = 0;
	public String BASE_DIR = "";
	// informacoes na tela enquando o jogo e' executado
	public final boolean DEBUGGER = false;
	// informacoes no terminal enquando o jogo e' executado
	private static final boolean LOG = false;

	private static int SCREENWIDTH = 640 + PADDING_WIDTH;
	private static int SCREENHEIGHT = 480 + PADDING_HEIGHT;

	private Thread gameloop;

	private Rectangle rectPlace[];

	private boolean emTransicao;

	public final float VELOCIDADE = (float) 2;

//	 public static final String spriteResource = "carro";
//	 public static final int spriteColumns = 1;
//	 public static final int spriteRows = 1;
//	 public static final int spriteWidth = 24;
//	 public static final int spriteHeigth = 32;

	// public static final String spriteResource = "cafe";
	// public static final int spriteColumns = 1;
	// public static final int spriteRows = 1;
	// public static final int spriteWidth = 24;
	// public static final int spriteHeigth = 32;

//	public static final String spriteResource = "bombeiro";
//	public static final int spriteColumns = 3;
//	public static final int spriteRows = 1;
//	public static final int spriteWidth = 70;
//	public static final int spriteHeigth = 70;

	 public static final String spriteResource = "in_on_at";
	 public static final int spriteColumns = 1;
	 public static final int spriteRows = 1;
	 public static final int spriteWidth = 72;
	 public static final int spriteHeigth = 72;

	// aaaa
	private int inPlace = 0;
	private int edgePlace = 0;

	private Image imagePlace[];
	private ArrayList<Place> places;
	private ElementosCPN eCPN;

	private int keyCode;

	// double buffer objects
	private BufferedImage backbuffer;
	private Graphics2D g2d;

	// sprite variables
	private AnimatedSprite ball;

	public void init() {
		BASE_DIR = System.getProperty("user.dir");
		if (BASE_DIR.endsWith("bin")) {
			BASE_DIR = new java.io.File(BASE_DIR).getParent() + "/src/";
		}

		emTransicao = false;
		InputStream is = null;
		try {
			is = new FileInputStream("res/cpn/GameTesteBombeiro.cpn");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		eCPN = new ElementosCPN(is); // /////referenciar aqui
		places = eCPN.getPlaces();
		imagePlace = new Image[places.size()];
		rectPlace = new Rectangle[places.size()];

		int x = 0, y = 0;
		double xIni = 0, yIni = 0;

		Place currentPlace = null;
		int altura = 0, largura = 0;
		
		Place iniPlace = places.get(0);
		for (int i = 0; i < imagePlace.length; i++) {
			currentPlace = places.get(i);

			imprimir("(" + i + ") url: " + currentPlace.getText() + ".png");

			URL url = getURL("res/img/casa/" + currentPlace.getText() + ".png");
			
			imprimir("res/img/casa/" + currentPlace.getText() + ".png");
			
			imagePlace[i] = this.getImage(url);

			// calculando a altura e largura da janela
			try {
				altura = ImageIO.read(url).getHeight();
				largura = ImageIO.read(url).getWidth();

				x = currentPlace.getRelativeLeft() * altura + PADDING_WIDTH;
				y = currentPlace.getRelativeTop() * largura + PADDING_HEIGHT;

				rectPlace[i] = new Rectangle(x, y, largura, altura);
				
				// percorre em todas as fichas do local em busca do sprite
				for (Ficha f : currentPlace.getInitmark()) {
					if (f.getFichas() > 0
							&& Integer.parseInt(f.getCor().get(0)) == 1) {
						iniPlace = currentPlace;
						xIni = x - PADDING_WIDTH;
						yIni = y - PADDING_HEIGHT;

						imprimir(x + " " + y);
						imprimir(xIni + " " + yIni);
						
					}
				}
				
				imprimir(currentPlace);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int maiorX = 0;
		int maiorY = 0;
		for (Rectangle r : rectPlace) {
			if (r.x + r.width > maiorX)
				maiorX = r.x + r.width;
			if (r.y + r.height > maiorY)
				maiorY = r.y + r.height;
		}

		SCREENWIDTH = maiorX + PADDING_WIDTH;
		SCREENHEIGHT = maiorY + PADDING_HEIGHT;

		addKeyListener(this);

		backbuffer = new BufferedImage(SCREENWIDTH, SCREENHEIGHT,
				BufferedImage.TYPE_INT_RGB);

		g2d = backbuffer.createGraphics();

		setSize(SCREENWIDTH, SCREENHEIGHT);

		// load the ball animation strip
		ball = new AnimatedSprite(this, g2d);

		URL url;
		String animName = "";
		boolean sprite = false;
		for (Place p : places) {
			if (!p.getFichas().isEmpty()) {
				for (Ficha f : p.getFichas()) {
					animName = "";
					sprite = false;
					for (int i = 0; i < f.getCor().size(); i++) {
						if (Integer.parseInt(f.getCor().get(i)) == 1) {
							if (i == 0) {
								sprite = true;
								x = p.getRelativeLeft() * altura;
								y = p.getRelativeTop() * largura;
							}
							animName += p.getType().get(i);
						}
					}
					url = getURL("/res/img/" + spriteResource + "/" + animName
							+ ".png");
					
					imprimir("/res/img/" + spriteResource + "/" + animName
							+ ".png");
					
					// die();
					
					if (sprite && url != null) {
						ball.load("/res/img/" + spriteResource + "/" + animName
								+ ".png", spriteColumns, spriteRows,
								spriteWidth, spriteHeigth);
					}
				}
			}
		}

		// System.out.println(x + " " + y);
		// die();
		ball.setPosition(new Point2D(PADDING_WIDTH, PADDING_HEIGHT));
		ball.setFrameDelay(25);

		ball.setVelocity(new Point2D(x, y)); // posicao inicial do
		// bombeiro
		ball.setRotationRate(0.0);
	}

	private void die() {
		System.exit(0);
	}

	/*
	 * class ImageLabel extends JLabel {
	 * 
	 * public ImageLabel(String img) { this(new ImageIcon(img)); }
	 * 
	 * public ImageLabel(ImageIcon icon) { setIcon(icon); // setMargin(new
	 * Insets(0,0,0,0)); setIconTextGap(0); // setBorderPainted(false);
	 * setBorder(null); setText(null); setSize(icon.getImage().getWidth(null),
	 * icon.getImage().getHeight(null)); }
	 * 
	 * }
	 */
	public void start() {
		gameloop = new Thread(this);
		gameloop.start();
		setFocusable(true);
		requestFocus();
	}

	public void stop() {
		gameloop = null;
	}

	public void run() {
		Thread t = Thread.currentThread();
		while (t == gameloop) {
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			gameUpdate();
			repaint();
		}
	}

	public void gameUpdate() {
		// see if it's time to animate
		ball.updateAnimation();

		// update the ball position
		ball.updatePosition();

		// lastly, update the rotation
		ball.updateRotation();

		double x = 0;
		double y = 0;
		double angle = ball.faceAngle();

		if (keyCode == KeyEvent.VK_LEFT) {
			x = -VELOCIDADE;
			y = 0;
			angle = 90;
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			x = VELOCIDADE;
			y = 0;
			angle = 270;
		}

		if (keyCode == KeyEvent.VK_UP) {
			x = 0;
			y = -VELOCIDADE;
			angle = 180;
		}

		if (keyCode == KeyEvent.VK_DOWN) {
			x = 0;
			y = VELOCIDADE;
			angle = 0;
		}

		// ball.setVelocity(new Point2D(x, y));
		// ball.setFaceAngle(angle);

		Place currentPlaceUpdate = places.get(0);
		Rectangle rec = ball.getBounds();
		imprimir(rec.x + ": " + rec.y);
		rec.x += x * 2;
		rec.y += y * 2;
		boolean permitido = true; // passar de um retângulo para outro
		boolean ok = false; // 
		boolean flag; //
		for (int i = 0; i < rectPlace.length; i++) { // percorre todos os retângulos
			flag = false;
			currentPlaceUpdate = places.get(i);
			if (rectPlace[i].contains(rec)) {

				imprimir("inPlace Place = " + currentPlaceUpdate.getText());

				inPlace = i;
			}

			if (rectPlace[i].intersects(rec)) {
				imprimir("edgePlace Place = " + currentPlaceUpdate.getText());

				edgePlace = i;
			}

			if (inPlace != edgePlace) {
				ok = true;
				if (!emTransicao) { //em transição significa que o sprite está ainda se deslocando
					Lista[][] placeToPlace = eCPN.getPlaceToPlace();
					for (int k = 0; k < placeToPlace[inPlace].length; k++)
						if (placeToPlace[inPlace][edgePlace] != null) {
							imprimir("Permitido de " + inPlace + " Para "
									+ edgePlace);

							permitido = eCPN
									.dispararTransicao(placeToPlace[inPlace][edgePlace]);

							emTransicao = permitido;

							if (LOG) {
								eCPN.showPlaces();
							}

							break;
						} else {
							permitido = false;
						}

					imprimir(currentPlaceUpdate);

					String mark = "", filename = "";
					for (Ficha f : currentPlaceUpdate.getFichas()) {
						mark = "";
						filename = "";
						for (int j = 0; j < f.getCor().size(); j++) {
							if (Integer.parseInt(f.getCor().get(0)) == 1
									&& Integer.parseInt(f.getCor().get(j)) == 1)
								mark += currentPlaceUpdate.getType().get(j);
						}
						if (!mark.equals("")) {

							String outro = mark.substring(1);
							// System.out.println(outro);
							int mak = 0;
							flag = false;
							for (Place c : eCPN.getPlaces()) {
								for (Ficha fi : c.getFichas()) {
									flag = false;
									for (int k = 0; k < fi.getCor().size(); k++) {
										if (outro.contains(c.getType().get(k))
												&& Integer.parseInt(fi.getCor()
														.get(k)) == 1) {
											flag = true;
										}
									}
									if (flag)
										mak += fi.getFichas();
								}
							}

							filename = "/res/img/" + spriteResource + "/"
									+ mark + "_" + mak + ".png";
							if (getURL(filename) != null) {
								ball.load(filename, spriteColumns, spriteRows,
										spriteWidth, spriteHeigth);
							} else {
								filename = "/res/img/" + spriteResource + "/"
										+ mark + ".png";
								if (getURL(filename) != null) {
									ball.load(filename, spriteColumns,
											spriteRows, spriteWidth,
											spriteHeigth);
								}
							}
						}
					}
				}
			}
		}

		if (!ok && emTransicao) {
			emTransicao = false;
		}

		if (!permitido && !emTransicao) {
			keyCode = 0;
			x = 0;
			y = 0;
			angle = ball.faceAngle();
		}

		if ((rec.x < 0 + PADDING_WIDTH)
				|| (rec.x > SCREENWIDTH - PADDING_WIDTH
						- ball.getBounds().width)) {
			keyCode = 0;
			x = 0;
			y = 0;
			angle = ball.faceAngle();
			/*
			 * x = ball.velocity().X() * -1; ball.setVelocity(new Point2D(x,0)
			 * ); // ball.velocity().Y()) x = ball.position().X();
			 * ball.setPosition(new Point2D(x, ball.position().Y()));
			 */
		}

		if ((rec.y < 0 + PADDING_HEIGHT)
				|| (rec.y > SCREENHEIGHT - PADDING_HEIGHT
						- ball.getBounds().height)) {
			keyCode = 0;
			x = 0;
			y = 0;
			angle = ball.faceAngle();
			/*
			 * y = ball.velocity().Y() * -1; ball.setVelocity(new
			 * Point2D(ball.velocity().X(), y)); y = ball.position().Y() +
			 * ball.velocity().Y(); ball.setPosition(new
			 * Point2D(ball.position().X(), y));
			 */
		}

		ball.setVelocity(new Point2D(x, y));
		ball.setFaceAngle(angle);
	}

	public void update(Graphics g) {
		// draw the background old
		// g2d.drawImage(background, 0, 0, SCREENWIDTH-1, SCREENHEIGHT-1, this);

		// draw the current frame of animation
		ball.draw();

		if (DEBUGGER) {
			// draw the background
			g2d.setColor(Color.WHITE);
			g2d.drawString("Position: " + ball.position().X() + ","
					+ ball.position().Y(), 5, 10);
			g2d.drawString("Velocity: " + ball.velocity().X() + ","
					+ ball.velocity().Y(), 5, 25);
			g2d.drawString("Animation: " + ball.currentFrame(), 5, 40);

			g2d.drawString("Press a key...", 100, 10);
			g2d.drawString("Key code: " + keyCode, 100, 25);
		}
		paint(g);
	}

	public void paint(Graphics g) {
		// draw the back buffer to the screen
		g.drawImage(backbuffer, 0, 0, this);

		g2d.setColor(Color.BLACK);

		g2d.fillRect(0, 0, getSize().width, getSize().height);

		Place currentPlacePaint = null;
		// Place currentPlace = null;
		int x = 0, y = 0;
		URL url;
		String animName;
		boolean cena = true;
		for (int i = 0; i < imagePlace.length; i++) {
			currentPlacePaint = places.get(i);
			x = currentPlacePaint.getRelativeLeft() * 150 + PADDING_WIDTH;
			y = currentPlacePaint.getRelativeTop() * 150 + PADDING_HEIGHT;
			g2d.drawImage(imagePlace[i], x, y, this);
			if (!places.get(i).getFichas().isEmpty()) {
				for (Ficha f : places.get(i).getFichas()) {
					animName = "";
					cena = true;
					for (int j = 0; j < f.getCor().size(); j++) {
						if (Integer.parseInt(f.getCor().get(j)) > 0) {
							if (j == 0) {
								cena = false;
							}
							animName += places.get(i).getType().get(j);
						}
					}
					if (!animName.equals("") && cena) {
						for (int m = 0; m < f.getFichas(); m++) {
							url = getURL("/res/img/" + spriteResource
									+ "/cenario/" + animName + "_" + m + ".png");
							if (url != null) {
								g2d.drawImage(this.getImage(url), x, y, this);
							} else {
								url = getURL("/res/img/" + spriteResource
										+ "/cenario/" + animName + ".png");
								if (url != null) {
									g2d.drawImage(this.getImage(url), x, y,
											this);
								}
							}
						}
					}
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (!emTransicao) {
			keyCode = e.getKeyCode();
		}
		// repaint();
		imprimir("Key Presed" + e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}

	private URL getURL(String filename) {
		try {

			File f = new File(BASE_DIR + filename);
			if (f.exists()) {
				return f.toURI().toURL();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
		// try {
		// url = this.getClass().getResource(filename);
		// } catch (Exception e) {
		// }
		// return url;
	}

	public void imprimir(Object text) {
		if (LOG) {
			System.out.println(text.toString());
		}
	}

}
