package br.edu.ifce.cgt.application.controller.titleds;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import cgt.behaviors.Behavior;
import br.edu.ifce.cgt.application.controller.dialogs.BehaviorDialog;
import cgt.core.CGTEnemy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;

public class EnemyTitledPane extends TitledPane {

    @FXML public VBox panBehaviors;
    @FXML public TextField txtDamage;
    @FXML public CheckBox chkBlock;
    @FXML public TextField txtAlpha;
    @FXML public TextField txtTimeRec;
    @FXML public VBox boxContent;

    public CheckBox chkDestroyable;
    public CheckBox chkVulnerable;

    private CGTEnemy enemy;

    public EnemyTitledPane(CGTEnemy object) {
        enemy = object;
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

        init();
    }

    public void init() {
        txtDamage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!txtDamage.getText().equals("")) {
                    enemy.setDamage(Integer.parseInt(txtDamage.getText()));
                }
            }
        });

        txtAlpha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemy.setAlpha(Float.parseFloat(txtAlpha.getText()));
            }
        });

        txtTimeRec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemy.setTimeToRecovery(Float.parseFloat(txtTimeRec.getText()));
            }
        });

        chkVulnerable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemy.setVulnerable(chkVulnerable.isSelected());
            }
        });
    }

    public void updateBehaviors() {
        panBehaviors.getChildren().clear();
        if (enemy.getBehaviorsSize() > 0) {
            for (int i = 0; i < enemy.getBehaviorsSize(); i++) {
                final Behavior behavior = enemy.getBehavior(i);
                ItemViewPane pane = new ItemViewPane(behavior.getBehaviorPolicy());
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        enemy.removeBehavior(behavior);
                        updateBehaviors();
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

        chkVulnerable.setSelected(enemy.isVulnerable());

        txtAlpha.setText(enemy.getAlpha()+"");
        if (enemy.getDamage() > 0) {
            txtDamage.setText(enemy.getDamage() + "");
        }

        if (enemy.getTimeToRecovery() > 0) {
            txtTimeRec.setText(enemy.getTimeToRecovery()+"");
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
