package br.edu.ifce.cgt.application.controller.titleds;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemEditPane;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.behaviors.Behavior;
import br.edu.ifce.cgt.application.controller.dialogs.BehaviorDialog;
import cgt.core.AbstractBehavior;
import cgt.core.CGTEnemy;
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
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;

public class EnemyTitledPane extends TitledPane {

    @FXML public VBox panBehaviors;
    @FXML public IntegerTextField txtDamage;
    @FXML public CheckBox chkBlock;
    @FXML public VBox boxContent;
    @FXML public ComboBox<String> boxGroup;

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
        txtDamage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    enemy.setDamage(txtDamage.getValue());
                }
            }
        });

        listGroups = boxGroup.getItems();

        boxGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listGroups.setAll(Config.get().getGame().getEnemiesGroup());
            }
        });

        boxGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (boxGroup.getValue() != null && !boxGroup.getValue().trim().isEmpty()) {
                    enemy.setGroup(boxGroup.getValue().trim());
                } else {
                    boxGroup.getSelectionModel().select(enemy.getGroup());
                }
            }
        });
        boxGroup.getSelectionModel().select(enemy.getGroup());
    }

    public void updateBehaviors() {
        panBehaviors.getChildren().clear();
        if (enemy.getBehaviorsSize() > 0) {
            ResourceBundle bundle = Pref.load().getBundle();
            for (int i = 0; i < enemy.getBehaviorsSize(); i++) {
                final AbstractBehavior behavior = enemy.getBehavior(i);
                ItemEditPane pane = new ItemEditPane(bundle.getString(behavior.getBehaviorPolicy()));
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        enemy.removeBehavior(behavior);
                        updateBehaviors();
                    }
                });
                pane.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        BehaviorDialog dialog = new BehaviorDialog(enemy, behavior);
                        dialog.showAndWait();
                    }
                });
                panBehaviors.getChildren().add(pane);
            }
        } else {
            panBehaviors.getChildren().add(new Label("Vazio"));
        }
    }

    public void setEnemy(CGTEnemy enemy) {
        this.enemy = enemy;

        if (enemy.getDamage() > 0) {
            txtDamage.setValue(enemy.getDamage());
        }

        updateBehaviors();
    }

    public CGTEnemy getEnemy() {
        return enemy;
    }

    public void addBehavior(ActionEvent event) {
        BehaviorDialog dialog = new BehaviorDialog(enemy);
        dialog.showAndWait();
        updateBehaviors();
    }
}
