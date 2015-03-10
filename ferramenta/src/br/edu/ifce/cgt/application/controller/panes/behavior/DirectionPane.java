package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.behaviors.Behavior;
import cgt.behaviors.Direction;
import cgt.core.AbstractBehavior;
import cgt.policy.DirectionPolicy;
import com.badlogic.gdx.math.Vector2;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
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
public class DirectionPane extends GridPane implements BehaviorPane {
    @FXML private ComboBox<EnumMap<DirectionPolicy>> boxPolicies;
    @FXML private ComboBox<EnumMap<Direction.DirectionMode>> boxModes;
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
        boxPolicies.getSelectionModel().selectFirst();

    }

    @Override
    public AbstractBehavior getBehavior() {
        Direction res = new Direction(boxPolicies.getValue().getKey());
        res.setDirectionMode(boxModes.getValue().getKey());
        res.setFinalPosition(new Vector2(Integer.parseInt(txtPosFimX.getText()),
                Integer.parseInt(txtPosFimY.getText())));
        res.setInitialPosition(new Vector2(Integer.parseInt(txtPosIniX.getText()),
                Integer.parseInt(txtPosIniY.getText())));
        res.setInteligenceMoviment(chkIntel.isSelected());

        return res;
    }
}
