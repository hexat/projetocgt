package cgt.controller.dialogs;

import cgt.behaviors.Behavior;
import cgt.controller.dialogs.ctrl.*;
import cgt.core.CGTEnemy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

/**
 * Created by Luan on 18/02/2015.
 */
public class BehaviorDialog extends AbstractDialog<BorderPane> {

    private final CGTEnemy enemy;

    private BorderPane window;

    private BehaviorPane currentPane;

    @FXML private ComboBox<String> boxCriteria;
    public BehaviorDialog(CGTEnemy enemy) {
        super("Win", new BorderPane());
        window = getWindow();

        this.enemy = enemy;

        boxCriteria.getItems().clear();
        boxCriteria.getItems().add("Fade");
        boxCriteria.getItems().add("Movimentação");
        boxCriteria.getItems().add("Sino");
        boxCriteria.getItems().add("Onda");

        boxCriteria.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleCriteria();
            }
        });

        boxCriteria.getSelectionModel().selectFirst();
    }

    private void handleCriteria() {
        switch (boxCriteria.getSelectionModel().getSelectedIndex()) {
            case 0:
                window.setCenter(new FadePane());
                break;
            case 1:
                window.setCenter(new DirectionPane());
                break;
            case 2:
                window.setCenter(new SinePane());
                break;
            case 3:
                window.setCenter(new WavePane());
        }
    }

    public void addWin() {
        currentPane = (BehaviorPane) window.getCenter();

        enemy.addBehavior(currentPane.getBehavior());

        getStage().close();
    }
}
