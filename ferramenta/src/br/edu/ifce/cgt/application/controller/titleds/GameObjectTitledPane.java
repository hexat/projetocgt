package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.AnimationDialog;
import br.edu.ifce.cgt.application.controller.panes.ItemEditPane;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.core.CGTAnimation;
import cgt.core.CGTGameObject;
import cgt.core.CGTProjectile;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
import com.badlogic.gdx.math.Vector2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class GameObjectTitledPane extends TitledPane {
    @FXML public VBox boxAnimations;
    @FXML public VBox boxPositions;
    @FXML public VBox boxSoundColision;
    @FXML public VBox boxSoundDie;
    @FXML public IntegerTextField txtMaxLife;
    @FXML public TextField txtSound;
    @FXML private FloatTextField txtBoundsW;
    @FXML private FloatTextField txtBoundsH;
    @FXML private IntegerTextField txtPositionX;
    @FXML private IntegerTextField txtPositionY;
    @FXML private IntegerTextField txtColisionX;
    @FXML private IntegerTextField txtColisionY;
    @FXML private FloatTextField txtColisionW;
    @FXML private FloatTextField txtColisionH;
    @FXML private IntegerTextField txtVelocidade;
    @FXML private IntegerTextField txtLife;
    @FXML private VBox listTeste;
	@FXML private TableView<String> tableSomColisao;
    @FXML private Label labLife;
    @FXML private Label labMaxLife;

	private ObservableList<String> listaSomColisao;
	
	private CGTGameObject gameObject;

    public GameObjectTitledPane(CGTGameObject object) {
        this.gameObject = object;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGameObject.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
        setActions();
    }

    private void init() {
        txtBoundsH.setText(gameObject.getBounds().getHeight() + "");
        txtBoundsW.setText(gameObject.getBounds().getWidth()+"");
        txtColisionH.setText(gameObject.getCollision().getHeight()+"");
        txtColisionW.setText(gameObject.getCollision().getWidth()+"");
        txtColisionX.setText(((int)gameObject.getCollision().x)+"");
        txtColisionY.setText(((int)gameObject.getCollision().y)+"");
        txtLife.setText(gameObject.getLife()+"");
        txtMaxLife.setValue(gameObject.getMaxLife());
        txtVelocidade.setText(gameObject.getSpeed()+"");
        if (gameObject.getSound() != null) {
            txtSound.setText(gameObject.getSound().getFile().getFilename());
        }

        if (gameObject instanceof CGTProjectile) {
            labLife.setText("Munição");
            labMaxLife.setText("Munição máxima:");
        }

        updateBoxAnimation();
        updateBoxPositions();
        updateBoxSoundCollision();
        updateBoxSoundDie();
    }

    public void setActions() {
        txtSound.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                txtSound.selectAll();
            }
        });
        txtSound.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    txtSound.setText("");
                } else {
                    event.consume();
                }
            }
        });
        txtSound.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txtSound.getText().isEmpty()) {
                    Config.get().destroy(gameObject.getSound().getFile());
                    gameObject.setSound(null);
                }
            }
        });
        txtLife.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (txtLife.getValue() > gameObject.getMaxLife()) {
                        gameObject.setLife(gameObject.getMaxLife());
                        txtLife.setValue(gameObject.getMaxLife());
                    } else {
                        gameObject.setLife(txtLife.getValue());
                    }
                }
            }
        });

        txtMaxLife.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.setMaxLife(txtMaxLife.getValue());
                }
            }
        });

        txtBoundsH.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getBounds().height = txtBoundsH.getValue();
                }
            }
        });

        txtBoundsW.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getBounds().width = txtBoundsW.getValue();
                }
            }
        });

        txtColisionX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getCollision().x = txtColisionX.getValue();
                    gameObject.updateCollisionXY();
                }
            }
        });

        txtColisionY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getCollision().y = txtColisionY.getValue();
                    gameObject.updateCollisionXY();
                }
            }
        });

        txtColisionW.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getCollision().width = txtColisionW.getValue();
                }

            }
        });

        txtColisionH.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getCollision().height = txtColisionH.getValue();
                }
            }
        });

        txtVelocidade.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.setSpeed(txtVelocidade.getValue());
                }
            }
        });
    }

    public void setSoundObject() {
        File audio = DialogsUtil.showOpenDialog("Selecione um audio", DialogsUtil.WAV_FILTER);
        if (audio != null) {
            try {
                CGTFile som = Config.get().createAudio(audio);
                gameObject.setSound(new CGTSound(som));
                txtSound.setText(som.getFilename());
            } catch (IOException e) {
                e.printStackTrace();
                DialogsUtil.showErrorDialog();
            }
        }
    }

	@FXML public void btnProcurarSom(){
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

		File chosenFile = DialogsUtil.showOpenDialog("Som de Colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
        } else {
		    path = null;
		}
	}

    @FXML public void addInitialPosition() {
        if (!txtPositionX.getText().equals("") && !txtPositionY.getText().equals("")) {
            int x = Integer.parseInt(txtPositionX.getText());
            int y = Integer.parseInt(txtPositionY.getText());

            gameObject.getInitialPositions().add(new Vector2(x, y));

            txtPositionX.clear();
            txtPositionY.clear();
            updateBoxPositions();
        }
    }

    private void updateBoxPositions() {
        boxPositions.getChildren().clear();
        if (gameObject.getInitialPositions().size() > 0) {
            for (final Vector2 v : gameObject.getInitialPositions()) {
                ItemViewPane pane = new ItemViewPane(v.toString());
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.getInitialPositions().remove(v);
                        updateBoxPositions();
                    }
                });
                boxPositions.getChildren().add(pane);
            }
        } else {
            boxPositions.getChildren().add(new Label("Vazio"));
        }
    }

    public void updateBoxSoundCollision() {
        boxSoundColision.getChildren().clear();
        if (gameObject.getSoundCollision().size() > 0) {
            for (final CGTSound s : gameObject.getSoundCollision()) {
                ItemViewPane pane = new ItemViewPane(s.getFile().getFilename());
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.getSoundCollision().remove(s);
                        Config.get().destroy(s.getFile());
                        updateBoxSoundCollision();
                    }
                });
                boxSoundColision.getChildren().add(pane);
            }
        } else {
            boxSoundColision.getChildren().add(new Label("Vazio"));
        }
    }

    public void updateBoxSoundDie() {
        boxSoundDie.getChildren().clear();
        if (gameObject.getSoundsDie().size() > 0) {
            for (final CGTSound s : gameObject.getSoundsDie()) {
                ItemViewPane pane = new ItemViewPane(s.getFile().getFilename());
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.getSoundsDie().remove(s);
                        Config.get().destroy(s.getFile());
                        updateBoxSoundDie();
                    }
                });
                boxSoundDie.getChildren().add(pane);
            }
        } else {
            boxSoundDie.getChildren().add(new Label("Vazio"));
        }
    }

    @FXML public void btnProcurarSomColisao(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione o Background");

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

		File chosenFile = DialogsUtil.showOpenDialog("Selecione o som de colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		} else {
		    //default return value
		    path = null;
		}
		
		listaSomColisao.add(path);
		tableSomColisao.setEditable(true);
		tableSomColisao.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableSomColisao.setItems(listaSomColisao);
	}

    public void addAnimation() {
        AnimationDialog dialog = new AnimationDialog(gameObject);
        dialog.showAndWait();
        updateBoxAnimation();
    }

    private void updateBoxAnimation() {
        boxAnimations.getChildren().clear();
        List<CGTAnimation> lista = gameObject.getAnimations();
        if (lista.size() > 0) {
            ResourceBundle bundle = Pref.load().getBundle();
            for (final CGTAnimation animation : lista) {
                String name = bundle.getString(animation.getStatesIterator().next().name());
                if (animation.getStatesActorSize() > 1) {
                    name += "...";
                }

                ItemEditPane pane = new ItemEditPane(name);
                pane.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        AnimationDialog dialog = new AnimationDialog(animation);
                        dialog.showAndWait();
                        updateBoxAnimation();
                    }
                });
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.removeAnimation(animation);
                        updateBoxAnimation();
                    }
                });
                boxAnimations.getChildren().add(pane);
            }
        } else {
            boxAnimations.getChildren().add(new Label("Nenhuma Animação"));
        }
    }

    @FXML public void addSoundDie() {
        File file = DialogsUtil.showOpenDialog("Selecionar som de morte", DialogsUtil.WAV_FILTER);

        if (file != null) {
            try {
                gameObject.getSoundsDie().add(new CGTSound(Config.get().createAudio(file)));
            } catch (IOException e) {
                e.printStackTrace();
                DialogsUtil.showErrorDialog();
            }

            updateBoxSoundDie();
        }
    }

    @FXML public void addSoundCollision() {
        File file = DialogsUtil.showOpenDialog("Selecionar som de colisão", DialogsUtil.WAV_FILTER);

        if (file != null) {
            try {
                gameObject.getSoundCollision().add(new CGTSound(Config.get().createAudio(file)));
            } catch (IOException e) {
                e.printStackTrace();
                DialogsUtil.showErrorDialog();
            }

            updateBoxSoundCollision();
        }
    }

    public void setGameObject(CGTGameObject gameObject) {
        this.gameObject = gameObject;

        updateBoxAnimation();
        updateBoxPositions();
        updateBoxSoundCollision();
        updateBoxSoundDie();
        if (gameObject.getSound() != null) {
            txtSound.setText(gameObject.getSound().getFile().getFilename());
        }
        if (gameObject.getBounds().width > 0) {
            txtBoundsW.setText(gameObject.getBounds().width + "");
        }
        if (gameObject.getBounds().height > 0) {
            txtBoundsH.setText(gameObject.getBounds().height + "");
        }

        if (gameObject.getCollision().height > 0) {
            txtColisionH.setText(gameObject.getCollision().height+"");
        }
        if (gameObject.getCollision().width  > 0) {
            txtColisionW.setText(gameObject.getCollision().width+"");
        }
        if (gameObject.getCollision().x > 0) {
            txtColisionX.setText(gameObject.getCollision().x+"");
        }
        if (gameObject.getCollision().y > 0) {
            txtColisionY.setText(gameObject.getCollision().y+"");
        }

        if (gameObject.getSpeed() > 0) {
            txtVelocidade.setText(gameObject.getSpeed()+"");
        }

        txtLife.setText(gameObject.getLife()+"");
    }

    public CGTGameObject getGameObject() {
        return gameObject;
    }

}
