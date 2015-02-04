package cgt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cgt.screen.CGTScreen;
import cgt.screen.CGTWindow;
import cgt.util.CGTFile;


public class CGTGame implements Serializable{
	private CGTWindow menu;

	public CGTGame() {
		setMenu(null);
	}
	
	public CGTGame(CGTScreen menu) {
		this.setMenu(menu);
	}

	public CGTWindow getGame() {
		return menu;
	}
	
	public boolean startWithGame() {
		return menu instanceof CGTGameWorld;
	}

	public void setMenu(CGTWindow menu) {
		this.menu = menu;
	}

	public  void saveGame(File file){
		try {

			FileOutputStream saveConfig = new FileOutputStream(file);
			ObjectOutputStream obj = new ObjectOutputStream(saveConfig);
			obj.writeObject(this);
			obj.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static CGTGame getSavedGame(InputStream io) {
		CGTGame cgtGame = new CGTGame();

		try {
			ObjectInputStream objLeitura = new ObjectInputStream(io);
			cgtGame = (CGTGame) objLeitura.readObject();
			objLeitura.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cgtGame;
	}
}


