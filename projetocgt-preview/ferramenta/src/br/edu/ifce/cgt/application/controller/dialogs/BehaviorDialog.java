package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.behavior.*;
import cgt.core.CGTEnemy;
import cgt.policy.BehaviorPolicy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class BehaviorDialog extends BorderPane {

    private CGTEnemy enemy;
    private BehaviorPolicy policy;
    private Stage stage;
    private boolean result;

    public BehaviorDialog(CGTEnemy enemy, BehaviorPolicy policy) {
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
        this.policy = policy;

        this.setContent(policy);
    }

    private void setContent(BehaviorPolicy policy) {
        switch (policy) {
            case FADE:
                setCenter(new FadePane());
                break;
            case DIRECTION:
                setCenter(new DirectionPane());
                break;
            case SINE:
                setCenter(new SinePane());
                break;
            case WAVE:
                setCenter(new WavePane());
        }

        stage.sizeToScene();
    }

    public void addCriteria() {
        enemy.addBehavior(((BehaviorPane) getCenter()).getBehavior());
        result = true;
        stage.close();
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
