package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.behaviors.Direction;
import cgt.core.AbstractBehavior;
import cgt.policy.DirectionPolicy;
import com.badlogic.gdx.math.Vector2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Luan on 18/02/2015.
 */
public class DirectionPane extends GridPane implements BehaviorPane {
    @FXML public Label labFirst;
    @FXML public Label labLast;
    @FXML private ComboBox<EnumMap<DirectionPolicy>> boxPolicies;
    @FXML private ComboBox<EnumMap<Direction.DirectionMode>> boxModes;
    @FXML private IntegerTextField txtPosIniX;
    @FXML private IntegerTextField txtPosIniY;
    @FXML private IntegerTextField txtPosFimX;
    @FXML private IntegerTextField txtPosFimY;
    @FXML private CheckBox chkIntel;
    private Direction result;

    public DirectionPane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/DirectionBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResourceBundle bundle = Pref.load().getBundle();

        List<EnumMap<Direction.DirectionMode>> listMode = new ArrayList<EnumMap<Direction.DirectionMode>>();

        for (Direction.DirectionMode m : Direction.DirectionMode.values()) {
            listMode.add(new EnumMap<Direction.DirectionMode>(m, bundle.getString(m.name())));
        }

        boxModes.getItems().setAll(listMode);
        boxModes.getSelectionModel().selectFirst();

        List<EnumMap<DirectionPolicy>> list = new ArrayList<EnumMap<DirectionPolicy>>();
        for (DirectionPolicy p : DirectionPolicy.values()) {
            list.add(new EnumMap<DirectionPolicy>(p, bundle.getString(p.name())));
        }

        boxPolicies.getItems().setAll(list);
        boxPolicies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (boxPolicies.getSelectionModel().getSelectedItem().getKey() == DirectionPolicy.TWO_POINTS_DIRECTION) {
                    labFirst.setText("Posição inicial");
                    labLast.setText("Posição final");
                    txtPosIniX.setPromptText("X");
                    txtPosIniY.setPromptText("Y");
                    txtPosFimX.setPromptText("X");
                    txtPosFimY.setPromptText("Y");
                } else {
                    labFirst.setText("X");
                    labLast.setText("Y");
                    txtPosIniX.setPromptText("Mínimo");
                    txtPosIniY.setPromptText("Máximo");
                    txtPosFimX.setPromptText("Mínimo");
                    txtPosFimY.setPromptText("Máximo");
                }
            }
        });
        boxPolicies.getSelectionModel().selectFirst();
        result = null;
    }

    @Override
    public AbstractBehavior getBehavior() {
        if (result == null) {
            result = new Direction();
        }
        result.setDirectionPolicy(boxPolicies.getValue().getKey());
        result.setDirectionMode(boxModes.getValue().getKey());
        result.setFinalPosition(new Vector2(txtPosFimX.getValue(), txtPosFimY.getValue()));
        result.setInitialPosition(new Vector2(txtPosIniX.getValue(), txtPosIniY.getValue()));
        result.setInteligenceMoviment(chkIntel.isSelected());

        return result;
    }

    @Override
    public void setBehavior(AbstractBehavior behavior) {
        result = (Direction) behavior;
        boolean found = false;

        for (int i = 0; i < boxPolicies.getItems().size() && !found; i++) {
            if (boxPolicies.getItems().get(i).getKey() == result.getDirectionPolicy()) {
                found = true;
                boxPolicies.getSelectionModel().select(i);
            }
        }
        found = false;
        for (int i = 0; i < boxModes.getItems().size() && !found; i++) {
            if (boxModes.getItems().get(i).getKey() == result.getDirectionMode()) {
                found = true;
                boxModes.getSelectionModel().select(i);
            }
        }
        txtPosFimX.setValue(result.getFinalPosition().x);
        txtPosFimY.setValue(result.getFinalPosition().y);
        txtPosIniX.setValue(result.getInitialPosition().x);
        txtPosIniY.setValue(result.getInitialPosition().y);
        chkIntel.setSelected(result.isInteligenceMoviment());
    }
}
