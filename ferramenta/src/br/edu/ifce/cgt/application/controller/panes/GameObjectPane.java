package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.AnimationDialog;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.core.CGTAnimation;
import cgt.core.CGTGameObject;
import cgt.core.CGTProjectile;
import cgt.policy.StatePolicy;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameObjectPane extends StackPane {
    /**
     * Informações gerais do objeto. Limites, posições,
     * colisão e velocidade
     */
    @FXML
    private FloatTextField txtBoundsW;
    @FXML
    private FloatTextField txtBoundsH;
    @FXML
    private IntegerTextField txtPositionX;
    @FXML
    private IntegerTextField txtPositionY;
    @FXML
    private VBox boxPositions;
    @FXML
    private IntegerTextField txtColisionX;
    @FXML
    private IntegerTextField txtColisionY;
    @FXML
    private FloatTextField txtColisionW;
    @FXML
    private FloatTextField txtColisionH;
    @FXML
    private IntegerTextField txtVelocidade;

    /**
     * Controles para configuração do life inicial e
     * máximo do objeto
     */
    @FXML
    private Label labLife;
    @FXML
    private Label labMaxLife;
    @FXML
    private IntegerTextField txtLife;
    @FXML
    private IntegerTextField txtMaxLife;

    /**
     * Controles para configuração da animação do objeto
     */
    @FXML
    private ComboBox<String> spritesheetCombobox;
    @FXML
    private ComboBox initialFrameCombobox;
    @FXML
    private ComboBox finalFrameCombobox;
    @FXML
    private FloatTextField speedField;
    @FXML
    private ComboBox<EnumMap<Animation.PlayMode>> policyCombobox;
    @FXML
    private ComboBox<EnumMap<StatePolicy>> stateBox;
    @FXML
    private VBox states;
    @FXML
    private CheckBox vflipCheckBox;
    @FXML
    private CheckBox hflipCheckbox;
    @FXML
    private VBox animationsBox;

    private List<EnumMap<StatePolicy>> statePolicies;

    /**
     * Controles para configuração dos sons do objeto
     */
    @FXML
    private VBox boxSoundColision;
    @FXML
    private VBox boxSoundDie;
    @FXML
    private TextField txtSound;
    @FXML
    private TableView<String> tableSomColisao;

    private ObservableList<String> listaSomColisao;

    /**
     * Objeto core que é configurado a partir dessa classe
     */
    private CGTGameObject gameObject;

    /**
     * Runnable que deve ser chamado toda vez que o objeto for
     * alterado e deve ser atualizado a pré-vizualização dele
     */
    private Runnable onUpdateRunnable;

    public GameObjectPane(CGTGameObject object) {
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

    public GameObjectPane(CGTGameObject object, Runnable onUpdateRunnable) {
        this(object);
        this.onUpdateRunnable = onUpdateRunnable;
    }

    private void init() {
        txtBoundsH.setText(gameObject.getBounds().getHeight() + "");
        txtBoundsW.setText(gameObject.getBounds().getWidth() + "");
        txtColisionH.setText(gameObject.getCollision().getHeight() + "");
        txtColisionW.setText(gameObject.getCollision().getWidth() + "");
        txtColisionX.setText(((int) gameObject.getCollision().x) + "");
        txtColisionY.setText(((int) gameObject.getCollision().y) + "");
        txtLife.setText(gameObject.getLife() + "");
        txtMaxLife.setValue(gameObject.getMaxLife());
        txtVelocidade.setText(gameObject.getSpeed() + "");

        if (gameObject.getSound() != null) {
            txtSound.setText(gameObject.getSound().getFile().getFilename());
        }

        if (gameObject instanceof CGTProjectile) {
            labLife.setText("Munição");
            labMaxLife.setText("Munição máxima:");
        }


        this.statePolicies = new ArrayList<EnumMap<StatePolicy>>();
        this.stateBox.getItems().addAll(getStates());
        this.policyCombobox.getItems().addAll(getPolicies());



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

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
                }
            }
        });

        txtBoundsW.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getBounds().width = txtBoundsW.getValue();
                }

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
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

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
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

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
                }
            }
        });

        txtColisionW.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getCollision().width = txtColisionW.getValue();
                }

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
                }
            }
        });

        txtColisionH.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    gameObject.getCollision().height = txtColisionH.getValue();
                }

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
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

    @FXML
    public void btnProcurarSom() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

        File chosenFile = DialogsUtil.showOpenDialog("Som de Colisão", extFilter, extFilter2);
        String path;
        if (chosenFile != null) {
            path = chosenFile.getPath();
        } else {
            path = null;
        }
    }

    @FXML
    public void addInitialPosition() {
        if (!txtPositionX.getText().equals("") && !txtPositionY.getText().equals("")) {
            int x = Integer.parseInt(txtPositionX.getText());
            int y = Integer.parseInt(txtPositionY.getText());

            gameObject.getInitialPositions().add(new Vector2(x, y));

            txtPositionX.clear();
            txtPositionY.clear();
            updateBoxPositions();

            if (onUpdateRunnable != null) {
                onUpdateRunnable.run();
            }
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

    @FXML
    public void btnProcurarSomColisao() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o Background");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

        File chosenFile = DialogsUtil.showOpenDialog("Selecione o som de colisão", extFilter, extFilter2);
        String path;
        if (chosenFile != null) {
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
        animationsBox.getChildren().clear();
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
                animationsBox.getChildren().add(pane);
            }
        } else {
            animationsBox.getChildren().add(new Label("Nenhuma Animação"));
        }
    }

    @FXML
    public void addSoundDie() {
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

    @FXML
    public void addSoundCollision() {
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

    @FXML
    public void addObjAnimation() {

    }

    @FXML
    public void addState() {
        if (!stateBox.getSelectionModel().isEmpty()) {
            EnumMap<StatePolicy> el = stateBox.getSelectionModel().getSelectedItem();
            statePolicies.add(el);
            stateBox.getItems().remove(el);
            updateStates();
        }
    }

    private void updateStates() {
        states.getChildren().clear();
        if (statePolicies.size() > 0) {
            for (final EnumMap<StatePolicy> s : statePolicies) {
                ItemViewPane pane = new ItemViewPane(s.getValue());
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        statePolicies.remove(s);
                        stateBox.getItems().add(s);
                        updateStates();

                    }
                });
                states.getChildren().add(pane);
            }
        } else {
            states.getChildren().add(new Label("Adicione pelo menos um estado."));
        }
    }

    public CGTGameObject getGameObject() {
        return gameObject;
    }

    public List<EnumMap<StatePolicy>> getStates() {
        ResourceBundle bundle = Pref.load().getBundle();

        List<EnumMap<StatePolicy>> list = new ArrayList<EnumMap<StatePolicy>>();

        for (StatePolicy s : StatePolicy.values()) {
            list.add(new EnumMap<StatePolicy>(s, bundle.getString(s.name())));
        }
        return list;
    }

    public List<EnumMap<Animation.PlayMode>> getPolicies() {
        ResourceBundle bundle = Pref.load().getBundle();

        List<EnumMap<Animation.PlayMode>> listModes = new ArrayList<EnumMap<Animation.PlayMode>>();

        for (Animation.PlayMode p : Animation.PlayMode.values()) {
            listModes.add(new EnumMap<Animation.PlayMode>(p, bundle.getString(p.name())));
        }

        return listModes;
    }
}
