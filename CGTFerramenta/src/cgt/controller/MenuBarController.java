package cgt.controller;

import java.io.IOException;

import application.Config;
import cgt.util.CGTSpriteSheet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuBarController {

	@FXML
	public void novo() {

	}

	@FXML
	public void abrir() {

	}

	@FXML
	public void salvar() {

	}

    @FXML public void export() {
        Config.export();
    }

	@FXML
	public void addSpriteSheet() {
		
		GridPane grid = new GridPane();
		
		try {
			grid = FXMLLoader.load(getClass().getResource("/view/SpriteConfig.fxml"));
			Stage stage = new Stage();
            stage.setTitle("Configuração Sprite Sheet");
            stage.setScene(new Scene(grid, 450, 450));
            stage.show();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	@FXML public void editSpriteSheet(){
		ListView listaSprites = new ListView<CGTSpriteSheet>();
		try {
			listaSprites = FXMLLoader.load(getClass().getResource("/view/listaSprites.fxml"));
			Stage stage = new Stage();
            stage.setTitle("Editar Sprite Sheet");
            stage.setScene(new Scene(listaSprites, 450, 450));
            stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
