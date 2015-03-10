package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
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
    @FXML public TextField txtMin;
    @FXML public TextField txtMax;
    @FXML public ComboBox<EnumMap<MovementPolicy>> boxMove;

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
    }

    @Override
    public AbstractBehavior getBehavior() {
        Sine res = new Sine(boxMove.getValue().getKey());
        res.setMax(Integer.parseInt(txtMax.getText()));
        res.setMin(Integer.parseInt(txtMin.getText()));

        return res;
    }
}
