package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import cgt.behaviors.Behavior;
import cgt.behaviors.Fade;
import cgt.core.AbstractBehavior;
import cgt.policy.FadePolicy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class FadePane extends GridPane implements BehaviorPane {
    @FXML public ChoiceBox<FadePolicy> boxFadePolice;
    @FXML public TextField txtFadeInTime;
    @FXML public TextField txtFadeOutTime;

    public FadePane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/FadeBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }



        boxFadePolice.getItems().addAll(FadePolicy.values());
        boxFadePolice.getSelectionModel().selectFirst();
    }

    @Override
    public AbstractBehavior getBehavior() {
        Fade res = new Fade(boxFadePolice.getValue());
        res.setFadeInTime(Integer.parseInt(txtFadeInTime.getText()));
        res.setFadeOutTime(Integer.parseInt(txtFadeOutTime.getText()));

        return res;
    }
}

