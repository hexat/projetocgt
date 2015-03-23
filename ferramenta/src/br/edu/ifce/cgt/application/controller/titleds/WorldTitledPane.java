package br.edu.ifce.cgt.application.controller.titleds;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.DialogDialog;
import br.edu.ifce.cgt.application.controller.dialogs.LoseDialog;
import br.edu.ifce.cgt.application.controller.dialogs.WinDialog;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.game.CGTGameWorld;
import cgt.hud.CGTButtonScreen;
import cgt.lose.Lose;
import cgt.screen.CGTDialog;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;

public class WorldTitledPane extends TitledPane {
	@FXML private Button btnPesquisaBack;
    @FXML private TextField txtProcuraBack;
    @FXML private TextField txtMusic;

    @FXML private Button btnRemPauseDialog;
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

        if (world.getMusic() != null) {
            txtMusic.setText(world.getMusic().getFile().getFilename());
        }

        btnRemWinDialog.setDisable(world.getLoseDialog() == null);
        btnRemLoseDialog.setDisable(world.getLoseDialog() == null);
        btnRemPauseDialog.setDisable(world.getPauseDialog() == null);
    }

    public CGTGameWorld getWorld() {
        return world;
    }

    public WorldTitledPane(CGTGameWorld world) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigWorld.fxml"));
        xml.setController(this);
        xml.setRoot(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
        setWorld(world);
    }

    private void init() {
        txtMusic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                txtMusic.selectAll();
            }
        });
        txtMusic.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    txtMusic.setText("");
                } else {
                    event.consume();
                }
            }
        });
        txtMusic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txtMusic.getText().isEmpty()) {
                    Config.get().destroy(world.getMusic().getFile());
                    world.setMusic(null);
                }
            }
        });
        btnRemLoseDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getLoseDialog());
                btnRemLoseDialog.setDisable(true);
            }
        });

        btnRemPauseDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getPauseDialog());
                btnRemPauseDialog.setDisable(true);
            }
        });

        btnRemWinDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removerDialog(world.getWinDialog());
                btnRemWinDialog.setDisable(true);
            }
        });
    }

    public String getTextTxtProcurarBack(){
		return txtProcuraBack.getText();
	}
	
	public void pesquisarBackground(){
		File chosenFile = DialogsUtil.showOpenDialog("Selecionar background", DialogsUtil.IMG_FILTER);

		String path = "";

		if(chosenFile != null) {
            world.setBackground(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
		}

		txtProcuraBack.setText(path);
	}

    private void removerDialog(CGTDialog dialog) {
        if (dialog.getHorizontalBorderTexture() != null) {
            Config.get().destroy(dialog.getHorizontalBorderTexture().getFile());
        }
        if (dialog.getRightBottomCorner() != null) {
            Config.get().destroy(dialog.getRightBottomCorner().getFile());
        }
        if (dialog.getWindow() != null) {
            Config.get().destroy(dialog.getWindow().getFile());
        }
        for (CGTButtonScreen bs : dialog.getButtons()) {
            if (bs.getTextureUp() != null) {
                Config.get().destroy(bs.getTextureUp().getFile());
            }
            if (bs.getTextureDown() != null) {
                Config.get().destroy(bs.getTextureDown().getFile());
            }
            bs.setTextureDown(null);
            bs.setTextureUp(null);
        }
        dialog.getButtons().clear();

        if (dialog.getCloseButton() != null) {
            if (dialog.getCloseButton().getTextureDown() != null) {
                Config.get().destroy(dialog.getCloseButton().getTextureDown().getFile());
                dialog.getCloseButton().setTextureDown(null);
            }

            if (dialog.getCloseButton().getTextureUp() != null) {
                Config.get().destroy(dialog.getCloseButton().getTextureUp().getFile());
                dialog.getCloseButton().setTextureUp(null);
            }
        }
        dialog.setCloseButton(null);

        if (world.getLoseDialog() == dialog) {
            world.setLoseDialog(null);
        } else if (world.getWinDialog() == dialog) {
            world.setWinDialog(null);
        } else if (world.getPauseDialog() == dialog) {
            world.setPauseDialog(null);
        }
    }

    public void addWin(ActionEvent actionEvent) {
        WinDialog win = new WinDialog(world);
        win.showAndWait();
        updateWin();
    }

    public void addLose(ActionEvent event) {
        LoseDialog loseDialog = new LoseDialog(world);
        loseDialog.showAndWait();
        updateLose();

    }

    private void updateLose() {
        panLoses.getChildren().clear();
        if (world.getLoseCriteria().size() > 0) {
            ResourceBundle bundle = Pref.load().getBundle();
            for (final Lose lose : world.getLoseCriteria()) {
                ItemViewPane pane = new ItemViewPane(bundle.getString(lose.getPolicy().name()));
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
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
            ResourceBundle bundle = Pref.load().getBundle();
            for (final Win win : world.getWinCriteria()) {
                ItemViewPane pane = new ItemViewPane(bundle.getString(win.getPolicy().name()));
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
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
            btnRemPauseDialog.setDisable(false);
        }

        DialogDialog dialog = new DialogDialog(world.getPauseDialog());
        dialog.showAndWait();
    }

    public void configWinDialog() {
        if (world.getWinDialog() == null) {
            world.setWinDialog(new CGTDialog());
            btnRemWinDialog.setDisable(false);
        }

        DialogDialog dialog = new DialogDialog(world.getWinDialog());
        dialog.showAndWait();
    }

    public void configLoseDialog() {
        if (world.getLoseDialog() == null) {
            world.setLoseDialog(new CGTDialog());
            btnRemLoseDialog.setDisable(false);
        }
        DialogDialog dialog = new DialogDialog(world.getLoseDialog());
        dialog.showAndWait();
    }

    public void setMusic() {
        File file = DialogsUtil.showOpenDialog("Selecionar Musica", DialogsUtil.WAV_FILTER);

        if (file != null) {
            if (world.getMusic() != null) {
                Config.get().destroy(world.getMusic().getFile());
                world.setMusic(null);
            }
            try {
                CGTFile music = Config.get().createAudio(file);

                world.setMusic(new CGTSound(music));

                txtMusic.setText(music.getFilename());

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
