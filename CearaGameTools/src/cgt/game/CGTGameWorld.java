package cgt.game;

import cgt.core.*;
import cgt.hud.*;
import cgt.policy.ErrorValidate;
import cgt.policy.WinPolicy;
import cgt.util.*;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.List;

import cgt.lose.Lose;
import cgt.policy.GameModePolicy;
import cgt.screen.CGTDialog;
import cgt.screen.CGTWindow;
import cgt.win.Win;

public class CGTGameWorld extends CGTWindow {
	private CGTActor actor;
	private ArrayList<CGTOpposite> opposites;
	private ArrayList<CGTEnemy> enemies;
	private ArrayList<CGTBonus> bonus;
	private ArrayList<HUDComponent> hud;
	private CGTDialog pauseDialog;
	private CGTDialog winDialog;
	private CGTDialog loseDialog;
	private CGTDialog initialDialog;
	private Camera camera;
	private CGTTexture background;
	private ArrayList<Win> winCriteria;
	private ArrayList<Lose> loseCriteria;
	private CGTSound music;
	private CGTSound soundWin;
	private CGTSound soundLose;
	private CGTButtonStartGame startGame;
    private int score;

	protected CGTGameWorld(){
        actor = new CGTActor("Actor");
		opposites = new ArrayList<CGTOpposite>();
		bonus = new ArrayList<CGTBonus>();
		enemies = new ArrayList<CGTEnemy>();
		hud = new ArrayList<HUDComponent>();
		winCriteria = new ArrayList<Win>();
		loseCriteria = new ArrayList<Lose>();
		camera = new Camera();
		startGame = null;
	}

	public void addButtonPad(ButtonPad buttonPad){
		this.addButton(buttonPad.getButtonBase());
		this.addButton(buttonPad.getButtonUp());
		this.addButton(buttonPad.getButtonDown());
		this.addButton(buttonPad.getButtonLeft());
		this.addButton(buttonPad.getButtonRight());
	}

	public Music getMusicGDX() {
		if (music != null){
			return music.getMusic();
		} else {
			return null;
		}
	}

    public CGTSound getMusic() {
        return music;
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
				+ camera + ", background=" + background + "]";
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

	public void addWinCriterion(WinCriteria criterion){
		criterion.remove();
		criterion.setWorld(this);
        this.winCriteria.add(criterion);
	}

	public ArrayList<Lose> getLoseCriteria() {
		return loseCriteria;
	}

	public void setLoseCriteria(ArrayList<Lose> loseCriteria) {
		this.loseCriteria = loseCriteria;
	}
	
	public void addLoseCriterion(LoseCriteria loseCriterion){
		loseCriterion.remove();
		loseCriterion.setWorld(this);
		this.loseCriteria.add(loseCriterion);
	}
	
	public void addLifeBar(LifeBar lifeBar){
		lifeBar.removeFromWorld();
		lifeBar.setWorld(this);
		hud.add(lifeBar);
	}
	public boolean removeHud(HUDComponent hudComponent){
		return hud.remove(hudComponent);
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

	public ArrayList<HUDComponent> getHUD() {
		return hud;
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

        if (getActor() != null && getActor().getId() != null && getActor().getId().equals(label)) {
            return getActor();
        }

        for (CGTGameObject o : enemies) {
            if (o.getId() != null && o.getId().equals(label)) {
                return o;
            }
        }

        for (CGTOpposite o : opposites) {
			if (o.getId() != null && o.getId().equals(label)) {
				return o;
			}
		}
		
		for (CGTGameObject o : bonus) {
			if (o.getId() != null && o.getId().equals(label)) {
				return o;
			}
		}
		
		for(CGTProjectile o: actor.getProjectiles()){
			if (o.getId() != null && o.getId().equals(label)) {
				return o;
			}
		}
		return null;
	}

	public List<String> getObjectIds() {
		List<String> res = new ArrayList<String>();
		if (getActor() != null) {
			res.add(getActor().getId());

            for (CGTProjectile p : getActor().getProjectiles()) {
                res.add(p.getId());
            }
		}
		for (CGTGameObject o : enemies) {
			res.add(o.getId());
		}

		for (CGTGameObject o : opposites) {
			res.add(o.getId());
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
        if (getActor().getInitialPositions().isEmpty()) {
            res.add(new CGTError(ErrorValidate.SET_ACTOR_POSITION, getId()));
        }
        boolean aux = false;
        for (int i = 0; i < enemies.size() && !aux; i++) {
            if (enemies.get(i).getInitialPositions().size() == 0) {
                aux = true;
                res.add(new CGTError(ErrorValidate.SET_OBJECT_POSITION, getId()));
            }
        }
        aux = false;
        for (int i = 0; i < opposites.size() && !aux; i++) {
            if (opposites.get(i).getInitialPositions().size() == 0) {
                aux = true;
                res.add(new CGTError(ErrorValidate.SET_OBJECT_POSITION, getId()));
            }
        }
        aux = false;
        for (int i = 0; i < bonus.size() && !aux; i++) {
            if (bonus.get(i).getInitialPositions().size() == 0) {
                aux = true;
                res.add(new CGTError(ErrorValidate.SET_OBJECT_POSITION, getId()));
            }
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
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public List<String> getProjectilesLabels() {
		List<String> res = new ArrayList<String>();
		for (CGTProjectile p : getActor().getProjectiles()) {
			res.add(p.getId());
		}
		return res;
	}

	public CGTEnemy findEnemy(String id) {
		for (CGTEnemy e : enemies) {
			if (e.getId().equals(id)) {
				return e;
			}
		}
		return null;
	}

	public List<String> getEnemyLabels() {
		List<String> res = new ArrayList<String>();
		for (CGTEnemy e : enemies) {
			res.add(e.getId());
		}
		return res;
	}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

	public void addScore(int score2) {
		this.score += score2;
		
	}

	public boolean removeObject(CGTGameObject object) {
		for (CGTGameObject o : getActor().getProjectiles()) {
			if (o == object) {
				getActor().getProjectiles().remove(o);
				return true;
			}
		}

		for (CGTGameObject o : opposites) {
			if (o == object) {
				opposites.remove(o);
				return true;
			}
		}
		for (CGTGameObject o : enemies) {
			if (o == object) {
				enemies.remove(o);
				return true;
			}
		}
		for (CGTGameObject o : bonus) {
			if (o == object) {
				bonus.remove(o);
				return true;
			}
		}
		return false;
	}
}
 
