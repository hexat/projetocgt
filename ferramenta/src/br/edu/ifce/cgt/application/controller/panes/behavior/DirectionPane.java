package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import cgt.behaviors.Behavior;
import cgt.behaviors.Direction;
import cgt.policy.DirectionPolicy;
import com.badlogic.gdx.math.Vector2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class DirectionPane extends GridPane implements BehaviorPane {
    @FXML private ComboBox<DirectionPolicy> boxPolicies;
    @FXML private ComboBox<Direction.DirectionMode> boxModes;
    @FXML private TextField txtPosIniX;
    @FXML private TextField txtPosIniY;
    @FXML private TextField txtPosFimX;
    @FXML private TextField txtPosFimY;
    @FXML private CheckBox chkIntel;

    public DirectionPane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/DirectionBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boxModes.getItems().addAll(Direction.DirectionMode.values());
        boxModes.getSelectionModel().selectFirst();

        boxPolicies.getItems().addAll(DirectionPolicy.values());
        boxPolicies.getSelectionModel().selectFirst();

    }

    @Override
    public Behavior getBehavior() {
        Direction res = new Direction(boxPolicies.getValue());
        res.setDirectionMode(boxModes.getValue());
        res.setFinalPosition(new Vector2(Integer.parseInt(txtPosFimX.getText()),
                Integer.parseInt(txtPosFimY.getText())));
        res.setInitialPosition(new Vector2(Integer.parseInt(txtPosIniX.getText()),
                Integer.parseInt(txtPosIniY.getText())));
        res.setInteligenceMoviment(chkIntel.isSelected());

        return res;
    }
}
