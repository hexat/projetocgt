package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import cgt.behaviors.Behavior;
import cgt.behaviors.Sine;
import cgt.policy.MovementPolicy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class SinePane extends GridPane implements BehaviorPane {
    @FXML public TextField txtMin;
    @FXML public TextField txtMax;
    @FXML public ComboBox<MovementPolicy> boxMove;

    public SinePane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/SineBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boxMove.getItems().addAll(MovementPolicy.values());
        boxMove.getSelectionModel().selectFirst();
    }

    @Override
    public Behavior getBehavior() {
        Sine res = new Sine(boxMove.getValue());
        res.setMax(Integer.parseInt(txtMax.getText()));
        res.setMin(Integer.parseInt(txtMin.getText()));

        return res;
    }
}
