package application.controller.titleds;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.behaviors.Behavior;
import application.controller.dialogs.BehaviorDialog;
import cgt.core.CGTEnemy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import application.controller.panes.ItemViewPane;

public class EnemyTitledPane implements Initializable {

    public VBox panBehaviors;
    public TextField txtDamage;
    public CheckBox chkBlock;
    public CheckBox chkDestroyable;
    public CheckBox chkVulnerable;
    public TextField txtAlpha;
    public TextField txtTimeRec;

    private CGTEnemy enemy;

    public static TitledPane getNode(CGTEnemy object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigInimigo.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            EnemyTitledPane controller = xml.getController();
            controller.setEnemy(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        chkBlock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemy.setBlock(chkBlock.isSelected());
            }
        });

        chkDestroyable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enemy.setDestroyable(chkDestroyable.isSelected());
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

        chkDestroyable.setSelected(enemy.isDestroyable());
        chkVulnerable.setSelected(enemy.isVulnerable());
        chkBlock.setSelected(enemy.isBlock());

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