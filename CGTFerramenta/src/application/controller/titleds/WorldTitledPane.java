package application.controller.titleds;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import application.controller.dialogs.DialogDialog;
import application.controller.dialogs.LoseDialog;
import application.controller.dialogs.WinDialog;
import cgt.game.CGTGameWorld;
import cgt.hud.CGTButton;
import cgt.hud.CGTButtonScreen;
import cgt.lose.Lose;
import cgt.screen.CGTDialog;
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
import javafx.stage.WindowEvent;
import application.util.DialogsUtil;
import application.controller.panes.ItemViewPane;

public class WorldTitledPane implements Initializable {
	@FXML private Button btnPesquisaBack;
	@FXML private TextField txtProcuraBack;

    @FXML private Button btnRemPauseDialog;
    @FXML private Button btnRemInitialDialog;
    @FXML private Button btnRemWinDialog;
    @FXML private Button btnRemLoseDialog;

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

        btnRemInitialDialog.setDisable(world.getLoseDialog() == null);
        btnRemWinDialog.setDisable(world.getLoseDialog() == null);
        btnRemLoseDialog.setDisable(world.getLoseDialog() == null);
        btnRemPauseDialog.setDisable(world.getPauseDialog() == null);
    }

    public CGTGameWorld getWorld() {
        return world;
    }

    public static TitledPane getNode(CGTGameWorld world) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigWorld.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            WorldTitledPane controller = xml.getController();
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
        btnRemLoseDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getLoseDialog());
            }
        });

        btnRemPauseDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getPauseDialog());
            }
        });

        btnRemWinDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getWinDialog());
            }
        });

        btnRemInitialDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getInitialDialog());
            }
        });
    }

    private void removerDialog(CGTDialog dialog) {
        if (dialog.getHorizontalBorderTexture() != null) {
            Config.destroy(dialog.getHorizontalBorderTexture().getFile());
        }
        if (dialog.getRightBottomCorner() != null) {
            Config.destroy(dialog.getRightBottomCorner().getFile());
        }
        if (dialog.getWindow() != null) {
            Config.destroy(dialog.getWindow().getFile());
        }
        for (CGTButtonScreen bs : dialog.getButtons()) {
            if (bs.getTextureUp() != null) {
                Config.destroy(bs.getTextureUp().getFile());
            }
            if (bs.getTextureDown() != null) {
                Config.destroy(bs.getTextureDown().getFile());
            }
            bs.setTextureDown(null);
            bs.setTextureUp(null);
        }
        dialog.getButtons().clear();

        if (dialog.getCloseButton() != null) {
            if (dialog.getCloseButton().getTextureDown() != null) {
                Config.destroy(dialog.getCloseButton().getTextureDown().getFile());
                dialog.getCloseButton().setTextureDown(null);
            }

            if (dialog.getCloseButton().getTextureUp() != null) {
                Config.destroy(dialog.getCloseButton().getTextureUp().getFile());
                dialog.getCloseButton().setTextureUp(null);
            }
        }
        dialog.setCloseButton(null);

        if (world.getLoseDialog() == dialog) {
            world.setLoseDialog(null);
        } else if (world.getWinDialog() == dialog) {
            world.setWinDialog(null);
        } else if (world.getInitialDialog() == dialog) {
            world.setInitialDialog(null);
        } else if (world.getPauseDialog() == dialog) {
            world.setPauseDialog(null);
        }
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
        if (world.getPauseDialog() == null) {
            world.setPauseDialog(new CGTDialog());
        }

        DialogDialog dialog = new DialogDialog(world.getPauseDialog());
        dialog.showAndWait();
    }

    public void configWinDialog() {
        if (world.getWinDialog() == null) {
            world.setWinDialog(new CGTDialog());
        }

        DialogDialog dialog = new DialogDialog(world.getWinDialog());
        dialog.showAndWait();
    }

    public void configInitialDialog() {
        if (world.getInitialDialog() == null) {
            world.setInitialDialog(new CGTDialog());
        }

        DialogDialog dialog = new DialogDialog(world.getInitialDialog());
        dialog.showAndWait();
    }

    public void configLoseDialog() {
        if (world.getLoseDialog() != null) {
            world.setLoseDialog(new CGTDialog());
        }
        DialogDialog dialog = new DialogDialog(world.getLoseDialog());
        dialog.showAndWait();
    }
}