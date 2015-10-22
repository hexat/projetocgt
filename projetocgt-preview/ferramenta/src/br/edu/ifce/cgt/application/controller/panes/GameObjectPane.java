package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.AnimationDialog;
import br.edu.ifce.cgt.application.controller.dialogs.SpriteSheetDialog;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import br.edu.ifce.cgt.application.vo.CGTGameObjectDrawable;
import cgt.core.CGTAnimation;
import cgt.core.CGTGameObject;
import cgt.core.CGTProjectile;
import cgt.game.CGTSpriteSheet;
import cgt.policy.StatePolicy;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
import cgt.util.SpriteSheetTile;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class GameObjectPane extends StackPane {

    @FXML
    private Accordion accordionRoot;

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
    private StackPane spritePreview;
    @FXML
    private ToggleButton initialFrameButton;
    @FXML
    private ToggleButton finalFrameButton;
    @FXML
    private Label initialFrameLabel;
    @FXML
    private Label finalFrameLabel;
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
    private CGTGameObjectDrawable objectDrawable;

    /**
     * Runnable que deve ser chamado toda vez que o objeto for
     * alterado e deve ser atualizado a pré-vizualização dele
     */
    private Runnable onUpdateRunnable;

    private SpriteSheetTile initialTile;

    private SpriteSheetTile finalTile;

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

    public GameObjectPane(CGTGameObject object, CGTGameObjectDrawable drawable, Runnable onUpdateRunnable) {
        this(object);
        this.objectDrawable = drawable;
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
        this.spritesheetCombobox.getItems().setAll(Config.get().getGame().getSpriteDB().findAllId());

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

        this.spritesheetCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateSpriteSheetPreview(newValue);

                if (onUpdateRunnable != null) {
                    onUpdateRunnable.run();
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
            if(x >= 0 && y >= 0){/*objectDrawable.getDraggable().getFitHeight() &&
                    x <= objectDrawable.getDraggable().getWidthBCKG() - objectDrawable.getDraggable().getFitWidth() &&
                    y <= objectDrawable.getDraggable().getHeightBCKG()) {*/
                gameObject.getInitialPositions().add(new Vector2(x, y - (int)objectDrawable.getDraggable().getFitHeight()));
                txtPositionX.clear();
                txtPositionY.clear();
                //System.out.printf("%d %d\n",objectDrawable.getDraggable().getFitWidth(),
                        //objectDrawable.getDraggable().getFitHeight());
                updateBoxPositions();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Coordenadas fora da tela!");
                alert.show();
            }
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
        List<CGTAnimation> animationList = gameObject.getAnimations();
        if (animationList.size() > 0) {
            ResourceBundle bundle = Pref.load().getBundle();
            for (final CGTAnimation animation : animationList) {
                String name = bundle.getString(animation.getStatesIterator().next().name());
                if (animation.getStatesActorSize() > 1) {
                    name += "...";
                }

                ItemEditPane pane = new ItemEditPane(name);

                pane.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        openAnimation(animation);
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
        CGTAnimation animation = new CGTAnimation();
        animation.cleanActorStates();

        for (EnumMap<StatePolicy> p : statePolicies) {
            animation.addActorState(p.getKey());
        }

        animation.setSpriteSheet(spritesheetCombobox.getSelectionModel().getSelectedItem());
        animation.setAnimationPolicy(policyCombobox.getSelectionModel().getSelectedItem().getKey());
        animation.setFlipHorizontal(hflipCheckbox.isSelected());
        animation.setFlipVertical(vflipCheckBox.isSelected());

        animation.setSpriteVelocity(speedField.getValue());
        animation.setInitialFrame(new Vector2(initialTile.getRow(),
                initialTile.getCol()));
        animation.setEndingFrame(new Vector2(finalTile.getRow(),
                finalTile.getCol()));

        this.gameObject.addAnimation(animation);
        this.updateBoxAnimation();
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

    @FXML
    public void addSpriteSheet() {
        new SpriteSheetDialog(null).showAndWait();
        this.spritesheetCombobox.getItems().setAll(Config.get().getGame().getSpriteDB().findAllId());
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

    public void updateSpriteSheetPreview(String spritesheetName) {

        if (!"".equals(spritesheetName)) {
            CGTSpriteSheet cgtSpriteSheet = Config.get().getGame().getSpriteDB().find(spritesheetName);
            String urlToFile = cgtSpriteSheet.getTexture().getFile().getFile().getName();
            Image img = Config.get().getImage(urlToFile);
            List<SpriteSheetTile> tiles = Config.get().splitImage(img, cgtSpriteSheet.getColumns(), cgtSpriteSheet.getRows());

            if (!tiles.isEmpty()) {
                this.txtBoundsH.setValue(tiles.get(0).getHeight());
                this.txtBoundsW.setValue(tiles.get(0).getWidth());

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);

                for (SpriteSheetTile tile : tiles) {
                    ImageView imgView = new ImageView(tile.getImage());
                    Button b = new Button();
                    b.setGraphic(imgView);

                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            fillSelectFrameValue(tile);
                        }
                    });

                    grid.add(b, tile.getCol(), tile.getRow());
                }

                this.spritePreview.getChildren().setAll(grid);
            }
        }
    }

    private void fillSelectFrameValue(SpriteSheetTile tile) {
        if (this.initialFrameButton.isSelected()) {
            this.initialTile = tile;
            this.initialFrameLabel.setText(this.spritesheetCombobox.getValue() + " (" + tile.getRow() + ", " + tile.getCol() + ")");
            this.initialFrameButton.setSelected(false);
        }

        if (this.finalFrameButton.isSelected()) {
            this.finalTile = tile;
            this.finalFrameLabel.setText(this.spritesheetCombobox.getValue() + " (" + tile.getRow() + ", " + tile.getCol() + ")");
            this.finalFrameButton.setSelected(false);
        }
    }

    private void openAnimation(CGTAnimation animation) {

        this.spritesheetCombobox.getSelectionModel().select(animation.getSpriteSheet().getId());
        this.initialFrameLabel.setText(this.spritesheetCombobox.getValue() + " (" + animation.getInitialFrame().x + ", " + animation.getInitialFrame().y + ")");
        this.finalFrameLabel.setText(this.spritesheetCombobox.getValue() + " (" + animation.getEndingFrame().x + ", " + animation.getEndingFrame().y + ")");
        this.updateSpriteSheetPreview(animation.getSpriteSheet().getId());
        this.speedField.setValue(animation.getSpriteVelocity());

        int i = 0;
        while (i < policyCombobox.getItems().size() &&
                policyCombobox.getItems().get(i).getKey() != animation.getAnimationPolicy()) i++;
        if (i < policyCombobox.getItems().size()) {
            policyCombobox.getSelectionModel().select(i);
        }

        this.hflipCheckbox.setSelected(animation.isFlipHorizontal());
        this.vflipCheckBox.setSelected(animation.isFlipVertical());

        Iterator<StatePolicy> itr = animation.getStatesIterator();
        boolean found;

        while (itr.hasNext()) {
            StatePolicy item = itr.next();
            found = false;

            for (i = 0; i < this.stateBox.getItems().size() && !found; i++) {

                if (this.stateBox.getItems().get(i).getKey() == item) {
                    found = true;
                    statePolicies.add(this.stateBox.getItems().get(i));
                    this.stateBox.getItems().remove(i);
                }
            }
        }

        this.updateStates();
    }

    public IntegerTextField getPositionX(){
        return txtPositionX;
    }
    public IntegerTextField getPositionY(){
        return txtPositionY;
    }
    public FloatTextField getBoundsW(){
        return txtBoundsW;
    }
    public FloatTextField getBoundsH() {
        return txtBoundsH;
    }

    public Accordion getAccordionRoot() {
        return accordionRoot;
    }
}
