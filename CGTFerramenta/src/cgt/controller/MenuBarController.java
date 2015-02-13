package cgt.controller;

import java.io.File;
import java.io.IOException;

import application.Main;
import cgt.screen.CGTWindow;
import javafx.scene.control.TabPane;
import util.DialogsUtil;
import application.Config;
import cgt.game.CGTSpriteSheet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.ScreenTab;

public class MenuBarController {

	@FXML
	public void novo() {

	}

	@FXML
	public void abrir() {
        TabPane tabFerramenta = (TabPane) Main.getApp().getScene().lookup("#tabFerramenta");

        File open = DialogsUtil.showOpenDialog("Abrir projeto", DialogsUtil.CGT_FILTER);
		if (open != null) {
			Config.unzip(open);
            for (CGTWindow w : Config.getGame().getWindows()) {
                ScreenTab tab = new ScreenTab(w);

                tabFerramenta.getTabs().add(tab);
                tabFerramenta.getSelectionModel().select(tab);
            }
		}
	}

	@FXML
	public void salvar() {
		File save = DialogsUtil.showSaveDialog("Salvar projeto");
		if (save != null) {
			Config.zip(save);
		}

	}

    @FXML public void export() {
        Config.export();
    }

	@FXML
	public void addSpriteSheet() {
		ConfigSpriteController dia =  new ConfigSpriteController(null);
        dia.show();
	}
	@FXML public void editSpriteSheet(){
		new ListSpriteController().show();
	}

}
