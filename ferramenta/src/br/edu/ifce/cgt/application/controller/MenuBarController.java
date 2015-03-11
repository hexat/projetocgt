package br.edu.ifce.cgt.application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.ExportDialog;
import br.edu.ifce.cgt.application.controller.dialogs.ListSpriteDialog;
import br.edu.ifce.cgt.application.controller.dialogs.SpriteSheetDialog;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.screen.CGTWindow;
import cgt.util.CGTError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.Config;
import javafx.fxml.FXML;
import br.edu.ifce.cgt.application.controller.panes.ScreenTab;
import org.controlsfx.dialog.Dialogs;

public class MenuBarController implements Initializable {

    public Menu menuRecent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateRecent();
    }

	@FXML
	public void novo() {

	}

	@FXML
	public void abrir() {
        File file = DialogsUtil.showOpenDialog("Abrir projeto", DialogsUtil.CGT_FILTER);
		if (file != null) {
            Pref.load().addRecentProject(file.getAbsolutePath());
            Pref.load().save();

            updateRecent();

            open(file);
        }
	}

    private void updateRecent() {
        MenuItem item;
        menuRecent.getItems().clear();
        for (String path : Pref.load().getRecentProjects()) {
            final File file = new File(path);
            item = new MenuItem(file.getName());
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    open(file);
                }
            });
            menuRecent.getItems().add(item);
        }
        if (menuRecent.getItems().size() > 0) {
            item = new MenuItem("Limpar");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Pref.load().getRecentProjects().clear();
                    Pref.load().save();
                    updateRecent();
                }
            });
            menuRecent.getItems().add(new SeparatorMenuItem());
            menuRecent.getItems().add(item);
        } else {
            item = new MenuItem("Vazio");
            item.setDisable(true);
            menuRecent.getItems().add(item);
        }
    }

    private void open(File open) {
        TabPane tabFerramenta = (TabPane) Main.getApp().getScene().lookup("#tabFerramenta");

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

    public void exit(ActionEvent actionEvent) {
        Main.getApp().close();
    }

    public void saveAs(ActionEvent actionEvent) {
        File save = DialogsUtil.showSaveDialog("Salvar projeto");
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
}
