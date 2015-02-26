package cgt.game;

import cgt.core.*;
import cgt.hud.*;
import cgt.policy.ErrorValidate;
import cgt.util.*;
import com.badlogic.gdx.audio.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cgt.lose.Lose;
import cgt.policy.GameModePolicy;
import cgt.policy.InputPolicy;
import cgt.screen.CGTDialog;
import cgt.screen.CGTWindow;
import cgt.unit.Action;
import cgt.win.Win;

public class CGTGameWorld extends CGTWindow implements Serializable {
	private CGTActor actor;
	private ArrayList<Action> actions;
	private ArrayList<CGTOpposite> opposites;
	private ArrayList<CGTEnemy> enemies;
	private ArrayList<CGTBonus> bonus;
	private ArrayList<CGTProjectile> projectiles;
	private ArrayList<HUDComponent> hud;
	private CGTDialog pauseDialog;
	private CGTDialog winDialog;
	private CGTDialog loseDialog;
	private CGTDialog initialDialog;
	private Camera camera;
	private CGTTexture background;
	private ArrayList<Win> winCriteria;
	private ArrayList<Lose> loseCriteria;
	private int countdown;
	private int scoreTarget;
	private CGTLabel label;
	private CGTSound music;
	private CGTSound soundWin;
	private CGTSound soundLose;
	private CGTButtonStartGame startGame;

	protected CGTGameWorld(){
        actor = new CGTActor("Actor");
		opposites = new ArrayList<CGTOpposite>();
		bonus = new ArrayList<CGTBonus>();
		enemies = new ArrayList<CGTEnemy>();
		hud = new ArrayList<HUDComponent>();
		winCriteria = new ArrayList<Win>();
		loseCriteria = new ArrayList<Lose>();
		actions = new ArrayList<Action>();
		camera = new Camera(GameModePolicy.TOUCH);
		startGame = null;
	}

	public void addButtonPad(ButtonPad buttonPad){
		this.addButton(buttonPad.getButtonBase());
		this.addButton(buttonPad.getButtonUp());
		this.addButton(buttonPad.getButtonDown());
		this.addButton(buttonPad.getButtonLeft());
		this.addButton(buttonPad.getButtonRight());
	}

	public Music getMusic() {
		if (music != null){
			return music.getMusic();
		} else {
			return null;
		}
	}

	public void setMusic(CGTSound music) {
		this.music = music;
	}

	public CGTSound getSoundWin() {
		return soundWin;
	}

	public void setSoundWin(CGTSound soundWin) {
		this.soundWin = soundWin;
	}
	
	public void playSoundWin(){
		if (soundWin != null){
			soundWin.getMusic().play();
		}
	}
	
	public void stopSound(CGTSound sound){
		if (sound != null){
			sound.getMusic().stop();
		}
	}

	public CGTSound getSoundLose() {
		return soundLose;
	}

	public void setSoundLose(CGTSound soundLose) {
		this.soundLose = soundLose;
	}
	
	public void playSoundLose(){
		if (soundLose != null){
			soundLose.getMusic().play();
		}
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

	public CGTTexture getBackground() {
		return background;
	}

	public void setBackground(CGTTexture background) {
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

	public void addButton(CGTButton button){
		hud.add(button);
	}
	
	public void removeButton(CGTButton button){
		hud.remove(button);
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
	
	public void addLifeBar(LifeBar lifeBar){
		hud.add(lifeBar);
	}
	
	public void removeLifeBar(LifeBar lifeBar){
		hud.remove(lifeBar);
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

	public CGTLabel getLabel() {
		return label;
	}

	public void setLabel(CGTLabel label) {
		this.label = label;
	}
	
	public ArrayList<HUDComponent> getHUD() {
		return hud;
	}
	
	public void addHUDComponent(HUDComponent component){
		hud.add(component);
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}
	
	public void addAction(Action... action){
		for(int i = 0; i<action.length; i++){
			actions.add(action[i]);
		}
	}
	
	public Action getActionFromInput(InputPolicy policy){
		for(int i =0; i<actions.size(); i++){
			if(actions.get(i).hasInput(policy))
				return actions.get(i);
		}
		return null;
	}

	public CGTDialog getLoseDialog() {
		return loseDialog;
	}

	public void setLoseDialog(CGTDialog loseDialog) {
		this.loseDialog = loseDialog;
	}

	public CGTButtonStartGame getStartGame() {
		return startGame;
	}

	public void setStartGame(CGTButtonStartGame startGame) {
		this.startGame = startGame;
	}

	public CGTDialog getInitialDialog() {
		return initialDialog;
	}

	public void setInitialDialog(CGTDialog initialDialog) {
		this.initialDialog = initialDialog;
	}

	public CGTGameObject getObjectByLabel(String label) {
		for (CGTOpposite o : opposites) {
			if (o.getLabel() != null && o.getLabel().equals(label)) {
				return o;
			}
		}
		return null;
	}

	public List<String> getLabels() {
		List<String> res = new ArrayList<String>();
		if (getActor() != null) {
			res.add(getActor().getLabel());
		}
		for (CGTGameObject o : enemies) {
			res.add(o.getLabel());
		}

		for (CGTGameObject o : opposites) {
			res.add(o.getLabel());
		}
		return res;
	}

    @Override
    public List<CGTError> validate() {
        List<CGTError> res = new ArrayList<CGTError>();
        if (getBackground() == null) {
            res.add(new CGTError(ErrorValidate.SET_BACKGROUND, getId()));
        }
        if (getActor() == null) {
            res.add(new CGTError(ErrorValidate.SET_ACTOR, getId()));
        }
        if (getActor().getPosition() == null) {
            res.add(new CGTError(ErrorValidate.SET_ACTOR_POSITION, getId()));
        }
        if (!getActor().hasAnimation()) {
            res.add(new CGTError(ErrorValidate.ADD_ACTOR_ANIMATION, getId()));
        }
        if (getActor().getBounds().width <= 0) {
            res.add(new CGTError(ErrorValidate.SET_ACTOR_BOUNDS, getId()));
        }
        return res;
    }

	public boolean removeWinCriteria(Win win) {
		return winCriteria.remove(win);
	}

	public boolean removeLoseCriteria(Lose lose) {
		return loseCriteria.remove(lose);
	}

	public CGTProjectile findProjectile(String id) {
		for (CGTProjectile p : getActor().getProjectiles()) {
			if (p.getLabel().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public List<String> getProjectilesLabels() {
		List<String> res = new ArrayList<String>();
		for (CGTProjectile p : getActor().getProjectiles()) {
			res.add(p.getLabel());
		}
		return res;
	}

	public CGTEnemy findEnemy(String id) {
		for (CGTEnemy e : enemies) {
			if (e.getLabel().equals(id)) {
				return e;
			}
		}
		return null;
	}

	public List<String> getEnemyLabels() {
		List<String> res = new ArrayList<String>();
		for (CGTEnemy e : enemies) {
			res.add(e.getLabel());
		}
		return res;
	}
}
 
