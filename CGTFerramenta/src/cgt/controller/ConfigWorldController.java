package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.controller.dialogs.DialogPopUp;
import cgt.controller.dialogs.LoseDialog;
import cgt.controller.dialogs.WinDialog;
import cgt.game.CGTGameWorld;
import cgt.lose.Lose;
import cgt.util.CGTTexture;
import cgt.win.Win;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import util.DialogsUtil;
import util.ui.ItemViewPane;

public class ConfigWorldController implements Initializable {
	@FXML private Button btnPesquisaBack;
	@FXML private TextField txtProcuraBack;

    @FXML private VBox panWins;
    @FXML private VBox panLoses;

    private CGTGameWorld world;

    public void setWorld(CGTGameWorld world) {
        this.world = world;

        updateWin();
        updateLose();
        if (world.getBackground() != null) {
            txtProcuraBack.setText(world.getBackground().getFile().getFilename());
        }
    }

    public CGTGameWorld getWorld() {
        return world;
    }

    public static TitledPane getNode(CGTGameWorld world) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigWorld.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            ConfigWorldController controller = xml.getController();
            controller.setWorld(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }

	public String getTextTxtProcurarBack(){
		return txtProcuraBack.getText();
	}
	
	public void pesquisarBackground(){
		File chosenFile = DialogsUtil.showOpenDialog("Selecionar background", DialogsUtil.IMG_FILTER);

		String path = "";

		if(chosenFile != null) {
            world.setBackground(new CGTTexture(Config.createImg(chosenFile)));
            path = chosenFile.getName();
		}

		txtProcuraBack.setText(path);
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addWin(ActionEvent actionEvent) {
        WinDialog win = new WinDialog(world);
        win.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateWin();
            }
        });
        win.show();
    }

    public void addLose(ActionEvent event) {
        LoseDialog loseDialog = new LoseDialog(world);
        loseDialog.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateLose();
            }
        });
        loseDialog.show();

    }

    private void updateLose() {
        panLoses.getChildren().clear();
        if (world.getLoseCriteria().size() > 0) {
            for (final Lose lose : world.getLoseCriteria()) {
                ItemViewPane pane = new ItemViewPane(lose.getPolicy().toString());
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        world.removeLoseCriteria(lose);
                        updateLose();
                    }
                });
                panLoses.getChildren().add(pane);
            }
        } else {
            panLoses.getChildren().add(new Label("Vazio"));
        }
    }

    private void updateWin() {
        panWins.getChildren().clear();
        if (world.getWinCriteria().size() > 0) {
            for (final Win win : world.getWinCriteria()) {
                ItemViewPane pane = new ItemViewPane(win.getPolicy().toString());
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        world.removeWinCriteria(win);
                        updateWin();
                    }
                });
                panWins.getChildren().add(pane);
            }
        } else {
            panWins.getChildren().add(new Label("Vazio"));
        }
    }

    public void configPauseDialog() {
        DialogPopUp dialog = new DialogPopUp(world.getPauseDialog());
        dialog.showAndWait();
    }

    public void configWinDialog() {
        DialogPopUp dialog = new DialogPopUp(world.getWinDialog());
        dialog.showAndWait();
    }

    public void configInitialDialog() {
        DialogPopUp dialog = new DialogPopUp(world.getInitialDialog());
        dialog.showAndWait();
    }

    public void configLoseDialog() {
        DialogPopUp dialog = new DialogPopUp(world.getLoseDialog());
        dialog.showAndWait();
    }
}
