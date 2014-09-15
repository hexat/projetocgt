package cgt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import cgt.core.CGTActor;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import cgt.core.CGTOpposite;
import cgt.lose.Lose;
import cgt.screen.CGTDialog;
import cgt.screen.CGTWindow;
import cgt.util.CGTButton;
import cgt.util.Camera;
import cgt.util.LifeBar;
import cgt.win.Win;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class CGTGameWorld extends CGTWindow implements Serializable {
	private static final long serialVersionUID = -4678582357160030528L;
	private CGTActor actor;
	private ArrayList<CGTOpposite> opposites;
	private ArrayList<CGTEnemy> enemies;
	private ArrayList<CGTBonus> bonus;
	private ArrayList<CGTButton> buttons;
	private ArrayList<LifeBar> lifeBars;
	private CGTDialog pauseDialog;
	private CGTDialog winDialog;
	private Camera camera;
	private Texture background;
	private ArrayList<Win> winCriteria;
	private ArrayList<Lose> loseCriteria;
	private int countdown;
	private int scoreTarget;
//	private int gridLines;
//	private int gridColumn;
	
	public CGTGameWorld(){
		opposites = new ArrayList<CGTOpposite>();
		bonus = new ArrayList<CGTBonus>();
		enemies = new ArrayList<CGTEnemy>();
		buttons = new ArrayList<CGTButton>();
		winCriteria = new ArrayList<Win>();
		loseCriteria = new ArrayList<Lose>();
		lifeBars = new ArrayList<LifeBar>();
		
	}

	public void salvaStream(String file){
		try
        {
            //Gera o arquivo para armazenar o objeto

            FileOutputStream arquivoGrav = new FileOutputStream(file+".cgt");

            //Classe responsavel por inserir os objetos

            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);

            //Grava o objeto cliente no arquivo

            objGravar.writeObject(this);

            objGravar.flush();

            objGravar.close();

            arquivoGrav.flush();

            arquivoGrav.close();

            System.out.println("Objeto gravado com sucesso!");

        }
        catch( Exception e ){
                e.printStackTrace( );
        }
	}

	public CGTGameWorld lerStream(String file){
		CGTGameWorld cgtGameWorld = new CGTGameWorld();
		try {
            FileInputStream arquivoLeitura = new FileInputStream(file + ".cgt");

            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);

            cgtGameWorld = (CGTGameWorld) objLeitura.readObject();
            
            objLeitura.close();

            arquivoLeitura.close();
		} catch (Exception e){
			e.printStackTrace( );
        }
		return cgtGameWorld; 
	}

	public void setActor(CGTActor actor) {
		this.actor = actor;
	}
	 
	public CGTActor getActor() {
		return actor;
	}
	 
	public void addOpposite(CGTOpposite opposite) {
		opposites.add(opposite);
	}
	 
	public boolean removeOpposite(CGTOpposite opposite) {
		return opposites.remove(opposite);
	}
	 
	public CGTOpposite removeOpposite(int index) {
		return opposites.remove(index);
	}
	 
	public void addBonus(CGTBonus bonus) {
		this.bonus.add(bonus);
	}
	 
	public boolean removeBonus(CGTBonus bonus) {
		return this.bonus.remove(bonus);
	}
	
	public CGTBonus removeBonus(int index) {
		return this.bonus.remove(index);
	}
	
	public ArrayList<CGTBonus> getBonus() {
		return bonus;
	}
	 
	public void setCountdown(int time) {
		this.countdown = time;
	}
	 
	public int getCountdown() {
		return countdown;
	}
	 
	public void setScoreTarget(int score) {
		this.scoreTarget  = score;
	}
	 
	public int getScoreTarget() {
		return scoreTarget;
	}
	 
//	public int getGridLines() {
//		return gridLines;
//	}
//	 
//	public int getGridColumn() {
//		return gridColumn;
//	}
//	 
//	public void setGridLines(int lines) {
//		this.gridLines = lines;
//	}
//	 
//	public void setGridColumn(int column) {
//		this.gridColumn = column;
//	}

	public Texture getBackground() {
		return background;
	}

	public void setBackground(Texture background) {
		this.background = background;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public ArrayList<CGTOpposite> getOpposites() {
		return opposites;
	}
	
	public ArrayList<CGTEnemy> getEnemies() {
		return enemies;
	}
	
	public void addEnemy(CGTEnemy enemy) {
		this.enemies.add(enemy);
	}
	 
	public boolean removeEnemy(CGTEnemy enemy) {
		return this.enemies.remove(enemy);
	}

	@Override
	public String toString() {
		return "CGTGameWorld [actor=" + actor + "\nopposites=" + opposites
				+ "\nenemies=" + enemies + "\nbonus=" + bonus + "\ncamera="
				+ camera + ", background=" + background + ", countdown="
				+ countdown + ", scoreTarget=" + scoreTarget + "]";
	}
	
	public CGTEnemy removeEnemy(int index){
		return this.enemies.remove(index);
	}
	
	public static void main(String[] args) {
		System.out.println("teste");
		String s = "data/a.png";
		File f = new FileHandle(s).file();
		
		System.out.println(f.getAbsolutePath());
	}

	public void addButton(CGTButton button){
		buttons.add(button);
	}
	
	public ArrayList<CGTButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<CGTButton> buttons) {
		this.buttons = buttons;
	}
	
	public void removeButton(int index){
		buttons.remove(index);
	}
	
	public void removeButton(CGTButton button){
		buttons.remove(button);
	}

	public ArrayList<Win> getWinCriteria() {
		return winCriteria;
	}

	public void setWinCriteria(ArrayList<Win> winCriteria) {
		this.winCriteria = winCriteria;
	}
	
	public void addWinCriterion(Win winCriterion){
		this.winCriteria.add(winCriterion);
	}

	public ArrayList<Lose> getLoseCriteria() {
		return loseCriteria;
	}

	public void setLoseCriteria(ArrayList<Lose> loseCriteria) {
		this.loseCriteria = loseCriteria;
	}
	
	public void addLoseCriterion(Lose loseCriterion){
		this.loseCriteria.add(loseCriterion);
	}

	public ArrayList<LifeBar> getLifeBars() {
		return lifeBars;
	}

	public void setLifeBars(ArrayList<LifeBar> lifeBars) {
		this.lifeBars = lifeBars;
	}
	
	public void addLifeBar(LifeBar lifeBar){
		lifeBars.add(lifeBar);
	}

	public CGTDialog getPauseDialog() {
		return pauseDialog;
	}

	public void setPauseDialog(CGTDialog dialog) {
		this.pauseDialog = dialog;
	}

	public CGTDialog getWinDialog() {
		return winDialog;
	}

	public void setWinDialog(CGTDialog winDialog) {
		this.winDialog = winDialog;
	}
}
 
