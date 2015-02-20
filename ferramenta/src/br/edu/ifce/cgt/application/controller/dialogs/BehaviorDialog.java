package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.behavior.*;
import cgt.core.CGTEnemy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class BehaviorDialog extends BorderPane {

    private final CGTEnemy enemy;
    private final Stage stage;

    private BehaviorPane currentPane;

    @FXML private ComboBox<String> boxCriteria;
    public BehaviorDialog(CGTEnemy enemy) {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/Win.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());

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
                setCenter(new FadePane());
                break;
            case 1:
                setCenter(new DirectionPane());
                break;
            case 2:
                setCenter(new SinePane());
                break;
            case 3:
                setCenter(new WavePane());
        }
    }

    public void addWin() {
        currentPane = (BehaviorPane) getCenter();

        enemy.addBehavior(currentPane.getBehavior());

        stage.close();
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
