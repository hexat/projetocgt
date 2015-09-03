package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.BehaviorDialog;
import br.edu.ifce.cgt.application.controller.panes.ItemEditPane;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.core.AbstractBehavior;
import cgt.core.CGTEnemy;
import cgt.policy.BehaviorPolicy;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ResourceBundle;

public class EnemyTitledPane extends TitledPane {

    @FXML public IntegerTextField txtDamage;
    @FXML public CheckBox chkBlock;
    @FXML public VBox boxContent;
    @FXML public ComboBox<String> boxGroup;
    @FXML public CheckBox fadeCheckbox;
    @FXML public CheckBox directionCheckbox;
    @FXML public CheckBox sineWaveCheckbox;
    @FXML public CheckBox sineCheckbox;

    public CheckBox chkDestroyable;
    private CGTEnemy enemy;
    private ObservableList<String> listGroups;

    public EnemyTitledPane(CGTEnemy object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigInimigo.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OppositeTitledPane pane = new OppositeTitledPane(object);

        boxContent.getChildren().add(0, pane.getGridOpposite());
        boxContent.getChildren().add(1, new Separator(Orientation.HORIZONTAL));

        chkBlock = pane.getCheckBlock();
        chkDestroyable = pane.getCheckDestroyable();

        setEnemy(object);
        init();
    }

    public void init() {
        this.txtDamage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    enemy.setDamage(txtDamage.getValue());
                }
            }
        });

        this.listGroups = this.boxGroup.getItems();

        this.boxGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listGroups.setAll(Config.get().getGame().getEnemiesGroup());
            }
        });

        this.boxGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (boxGroup.getValue() != null && !boxGroup.getValue().trim().isEmpty()) {
                    enemy.setGroup(boxGroup.getValue().trim());
                } else {
                    boxGroup.getSelectionModel().select(enemy.getGroup());
                }
            }
        });

        this.boxGroup.getSelectionModel().select(enemy.getGroup());

        this.fadeCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    BehaviorDialog dialog = new BehaviorDialog(enemy, BehaviorPolicy.FADE);
                    dialog.showAndWait();
                } else {
                    enemy.removeBehavior(BehaviorPolicy.FADE);
                }
            }
        });

        this.directionCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    BehaviorDialog dialog = new BehaviorDialog(enemy, BehaviorPolicy.DIRECTION);
                    dialog.showAndWait();
                } else {
                    enemy.removeBehavior(BehaviorPolicy.DIRECTION);
                }
            }
        });

        this.sineCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    BehaviorDialog dialog = new BehaviorDialog(enemy, BehaviorPolicy.SINE);
                    dialog.showAndWait();
                } else {
                    enemy.removeBehavior(BehaviorPolicy.SINE);
                }
            }
        });

        this.sineWaveCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    BehaviorDialog dialog = new BehaviorDialog(enemy, BehaviorPolicy.WAVE);
                    dialog.showAndWait();
                } else {
                    enemy.removeBehavior(BehaviorPolicy.WAVE);
                }
            }
        });
    }

    public void setEnemy(CGTEnemy enemy) {
        this.enemy = enemy;

        if (enemy.getDamage() > 0) {
            txtDamage.setValue(enemy.getDamage());
        }
    }

    public CGTEnemy getEnemy() {
        return enemy;
    }
}
