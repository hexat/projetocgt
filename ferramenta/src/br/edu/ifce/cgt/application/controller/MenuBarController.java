package br.edu.ifce.cgt.application.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.ExportDialog;
import br.edu.ifce.cgt.application.controller.dialogs.ListSpriteDialog;
import br.edu.ifce.cgt.application.controller.dialogs.SpriteSheetDialog;
import cgt.screen.CGTWindow;
import cgt.util.CGTError;
import javafx.scene.control.TabPane;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.Config;
import javafx.fxml.FXML;
import br.edu.ifce.cgt.application.controller.panes.ScreenTab;
import org.controlsfx.dialog.Dialogs;

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
            if (tabFerramenta.getTabs().size() > 1) {
                tabFerramenta.getTabs().remove(1, tabFerramenta.getTabs().size());
            }
            for (CGTWindow w : Config.getGame().getWindows()) {
                ScreenTab tab = new ScreenTab(w);

                tabFerramenta.getTabs().add(tab);
                tabFerramenta.getSelectionModel().select(tab);
            }
		}
	}

	@FXML
	public void salvar() {
        File save;
        if (Config.isLoaded()) {
            save = Config.getInputProjectFile();
        } else {
            save = DialogsUtil.showSaveDialog("Salvar projeto");
        }

        if (save != null) {
            try {
                Config.zip(save);
                Dialogs.create().owner(Main.getApp()).message(":)").title("Salvando Projeto").showInformation();
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
	}

    @FXML public void export() {
        List<CGTError> errors = Config.getGame().validate();
        if (errors.isEmpty()) {
            new ExportDialog().show();
        } else {
            System.out.println(errors);
        }
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
