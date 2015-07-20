package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.WinDialog;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import cgt.game.CGTGameWorld;
import cgt.policy.WinPolicy;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
import cgt.util.CGTTexture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

public class ConfigWorldPreviewPane extends AnchorPane {

    @FXML
    private TextField worldTextField;
    @FXML
    private TextField backgroundTextField;
    @FXML
    private TextField musicTextField;
    @FXML
    private CheckBox killEnemiesCheckbox;
    @FXML
    private CheckBox surviveCheckbox;
    @FXML
    private CheckBox getBonusCheckbox;
    @FXML
    private CheckBox completeCrossingCheckbox;
    @FXML
    private CheckBox targetScoreCheckbox;

    private CGTGameWorld world;

    private Runnable onUpdateRunnable;

    public ConfigWorldPreviewPane(CGTGameWorld world) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigWorld2.fxml"));
        xml.setController(this);
        xml.setRoot(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setWorld(world);
        init();

    }

    public ConfigWorldPreviewPane(CGTGameWorld world, Runnable onUpdateRunnable) {
        this(world);
        this.onUpdateRunnable = onUpdateRunnable;
    }

    public void setWorld(CGTGameWorld world) {
        this.world = world;
    }

    public CGTGameWorld getWorld() {
        return world;
    }

    private void init() {
        musicTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                musicTextField.selectAll();
            }
        });

        musicTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    musicTextField.setText("");
                } else {
                    event.consume();
                }
            }
        });

        musicTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (musicTextField.getText().isEmpty()) {
                    Config.get().destroy(world.getMusic().getFile());
                    world.setMusic(null);
                }
            }
        });

        worldTextField.setText(this.world.getId());

        if (world.getBackground() != null) {
            backgroundTextField.setText(world.getBackground().getFile().getFilename());
        }

        if (world.getMusic() != null) {
            musicTextField.setText(world.getMusic().getFile().getFilename());
        }

        this.killEnemiesCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    WinDialog win = new WinDialog(world, WinPolicy.KILL_ENEMIES);
                    win.showAndWait();
                }
            }
        });

        this.completeCrossingCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    WinDialog win = new WinDialog(world, WinPolicy.COMPLETE_CROSSING);
                    win.showAndWait();
                }
            }
        });

        this.getBonusCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    WinDialog win = new WinDialog(world, WinPolicy.GET_ALL_BONUS);
                    win.showAndWait();
                }
            }
        });

        this.surviveCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    WinDialog win = new WinDialog(world, WinPolicy.SURVIVE);
                    win.showAndWait();
                }
            }
        });

        this.targetScoreCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    WinDialog win = new WinDialog(world, WinPolicy.TARGET_SCORE);
                    win.showAndWait();
                }
            }
        });

    }

    @FXML
    public void selectBackground() {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar background", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            world.setBackground(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
        }

        backgroundTextField.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }

    @FXML
    public void selectMusic() {
        File file = DialogsUtil.showOpenDialog("Selecionar Musica", DialogsUtil.WAV_FILTER);

        if (file != null) {
            if (world.getMusic() != null) {
                Config.get().destroy(world.getMusic().getFile());
                world.setMusic(null);
            }
            try {
                CGTFile music = Config.get().createAudio(file);
                world.setMusic(new CGTSound(music));
                musicTextField.setText(music.getFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateWorld(String name) {
        this.world.setId(name);
    }
}
