package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.behaviors.Behavior;
import cgt.behaviors.Sine;
import cgt.core.AbstractBehavior;
import cgt.policy.MovementPolicy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Luan on 18/02/2015.
 */
public class SinePane extends GridPane implements BehaviorPane {
    @FXML public IntegerTextField txtMin;
    @FXML public IntegerTextField txtMax;
    @FXML public ComboBox<EnumMap<MovementPolicy>> boxMove;
    private Sine result;

    public SinePane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/SineBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourceBundle bundle = Pref.load().getBundle();
        List<EnumMap<MovementPolicy>> list = new ArrayList<EnumMap<MovementPolicy>>();
        for (MovementPolicy p : MovementPolicy.values()) {
            list.add(new EnumMap<MovementPolicy>(p, bundle.getString(p.name())));
        }
        boxMove.getItems().setAll(list);
        boxMove.getSelectionModel().selectFirst();
        result = null;
    }

    @Override
    public AbstractBehavior getBehavior() {
        if (result == null) {
            result = new Sine();
        }
        result.setMovementPolicy(boxMove.getValue().getKey());
        result.setMax(txtMax.getValue());
        result.setMin(txtMin.getValue());

        return result;
    }

    @Override
    public void setBehavior(AbstractBehavior behavior) {
        result = (Sine) behavior;

        boolean found = false;
        for (int i = 0; i < boxMove.getItems().size() && !found; i++) {
            if (boxMove.getItems().get(i).getKey() == result.getMovementPolicy()) {
                found = true;
                boxMove.getSelectionModel().select(i);
            }
        }

        txtMax.setValue(result.getMax());
        txtMin.setValue(result.getMin());
    }
}
