package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.DialogDialog;
import br.edu.ifce.cgt.application.controller.dialogs.LoseDialog;
import br.edu.ifce.cgt.application.controller.dialogs.WinDialog;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import cgt.game.CGTGameWorld;
import cgt.hud.CGTButtonScreen;
import cgt.lose.Lose;
import cgt.policy.LosePolicy;
import cgt.policy.WinPolicy;
import cgt.screen.CGTDialog;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
import cgt.util.CGTTexture;
import cgt.win.GetAllBonus;
import cgt.win.KillAllEnemies;
import cgt.win.Win;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
    @FXML
    private Button removeWinDialogButton;
    @FXML
    private CheckBox actorDeadCheckbox;
    @FXML
    private CheckBox targetTimeCheckbox;
    @FXML
    private Button removeLoseDialogButton;

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
                WinPolicy policy = WinPolicy.KILL_ENEMIES;
                if (newValue) {
                    world.addWinCriterion(new KillAllEnemies());
                } else {
                    removeWinCriteria(policy);
                }
            }
        });

        this.completeCrossingCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                WinPolicy policy = WinPolicy.COMPLETE_CROSSING;
                if (newValue) {
                    boolean result = addWinCriteria(policy);
                    completeCrossingCheckbox.selectedProperty().setValue(result);
                } else {
                    removeWinCriteria(policy);
                }
            }
        });

        this.getBonusCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                WinPolicy policy = WinPolicy.GET_ALL_BONUS;
                if (newValue) {
                    world.addWinCriterion(new GetAllBonus());
                } else {
                    removeWinCriteria(policy);
                }
            }
        });

        this.surviveCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                WinPolicy policy = WinPolicy.SURVIVE;
                if (newValue) {
                    boolean result = addWinCriteria(policy);
                    surviveCheckbox.selectedProperty().setValue(result);
                } else {
                    removeWinCriteria(policy);
                }
            }
        });

        this.targetScoreCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                WinPolicy policy = WinPolicy.TARGET_SCORE;
                if (newValue) {
                    boolean result = addWinCriteria(policy);
                    targetScoreCheckbox.selectedProperty().setValue(result);
                } else {
                    removeWinCriteria(policy);
                }
            }
        });

        this.actorDeadCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                LosePolicy policy = LosePolicy.ACTOR_DEAD;
                if (newValue) {
                    boolean result = addLoseCriteria(policy);
                    actorDeadCheckbox.selectedProperty().setValue(result);
                } else {
                    removeLoseCriteria(policy);
                }
            }
        });

        this.targetTimeCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                LosePolicy policy = LosePolicy.TARGET_TIME;
                if (newValue) {
                    boolean result = addLoseCriteria(policy);
                    targetTimeCheckbox.selectedProperty().setValue(result);
                } else {
                    removeLoseCriteria(policy);
                }
            }
        });

        removeWinDialogButton.setDisable(world.getWinDialog() == null);
        removeLoseDialogButton.setDisable(world.getLoseDialog() == null);
    }

    private boolean addWinCriteria(WinPolicy policy) {
        WinDialog win = new WinDialog(world, policy);
        win.showAndWait();
        return win.getResult();
    }

    private void removeWinCriteria(WinPolicy policy) {
        for (Win w : world.getWinCriteria()) {
            if (w.getPolicy() == policy) {
                world.removeWinCriteria(w);
                break;
            }
        }
    }

    private boolean addLoseCriteria(LosePolicy policy) {
        LoseDialog loseDialog = new LoseDialog(world, policy);
        loseDialog.showAndWait();
        return loseDialog.getResult();
    }

    private void removeLoseCriteria(LosePolicy policy) {
        for (Lose lose : world.getLoseCriteria()) {
            if (lose.getPolicy() == policy) {
                world.removeLoseCriteria(lose);
                break;
            }
        }
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

    public void configWinDialog() {
        if (world.getWinDialog() == null) {
            world.setWinDialog(new CGTDialog());
            removeWinDialogButton.setDisable(false);
        }

        DialogDialog dialog = new DialogDialog(world.getWinDialog());
        dialog.showAndWait();
    }

    public void configLoseDialog() {
        if (world.getLoseDialog() == null) {
            world.setLoseDialog(new CGTDialog());
            removeLoseDialogButton.setDisable(false);
        }

        DialogDialog dialog = new DialogDialog(world.getLoseDialog());
        dialog.showAndWait();
    }

    public void removeWinDialog() {
        removeDialog(world.getWinDialog());
        removeWinDialogButton.setDisable(true);
    }

    public void removeLoseDialog () {
        removeDialog(world.getLoseDialog());
        removeLoseDialogButton.setDisable(true);
    }

    private void removeDialog(CGTDialog dialog) {
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

}
