package application.controller;

import java.io.File;

import application.Main;
import application.controller.dialogs.ListSpriteDialog;
import application.controller.dialogs.SpriteSheetDialog;
import cgt.screen.CGTWindow;
import javafx.scene.control.TabPane;
import application.util.DialogsUtil;
import application.Config;
import javafx.fxml.FXML;
import application.controller.panes.ScreenTab;

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
		SpriteSheetDialog dia =  new SpriteSheetDialog(null);
        dia.show();
	}
	@FXML public void editSpriteSheet(){
		new ListSpriteDialog().show();
	}

}
