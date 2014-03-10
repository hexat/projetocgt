package gui;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	public static final int PADDING_WIDTH = 0;
	// espacamento na altura
	public static final int PADDING_HEIGHT = 0;

	// informacoes na tela enquando o jogo e' executado
	public final boolean DEBUGGER = false;

	private volatile boolean running = false; // stops the animation
	private volatile boolean gameOver = false; // for game termination

	public static int SCREENWIDTH = 640 + PADDING_WIDTH;
	public static int SCREENHEIGHT = 480 + PADDING_HEIGHT;

	public GamePanel() {
		super();

		SCREENWIDTH = 0;
	}

}
