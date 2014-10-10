package cgt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cgt.screen.CGTScreen;

public class CGTGame implements Serializable{
	private CGTScreen menu;

	public CGTGame() {
		setMenu(null);
	}
	
	public CGTGame(CGTScreen menu) {
		this.setMenu(menu);
	}

	public CGTScreen getMenu() {
		return menu;
	}

	public void setMenu(CGTScreen menu) {
		this.menu = menu;
	}
	public  void saveGame(){
		try {

			FileOutputStream saveConfig = new FileOutputStream("gameConfig.txt");
			ObjectOutputStream obj = new ObjectOutputStream(saveConfig);
			obj.writeObject(this);
			obj.close();
			System.out.println("Obejto Salvo com Sucesso!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	public static CGTGame getSavedGame() {
		CGTGame cgtGame = new CGTGame();

		try {
			FileInputStream arquivoLeitura = new FileInputStream(
					"gameConfig.txt");
			ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
			cgtGame = (CGTGame) objLeitura.readObject();
			objLeitura.close();
			arquivoLeitura.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cgtGame;
		
		
	}
	
}


