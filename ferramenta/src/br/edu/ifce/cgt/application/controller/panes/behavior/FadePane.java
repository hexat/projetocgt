package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Luan on 18/02/2015.
 */
public class FadePane extends GridPane implements BehaviorPane {
    @FXML public ChoiceBox<EnumMap<FadePolicy>> boxFadePolice;
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
        ResourceBundle bundle = Pref.load().getBundle();
        List<EnumMap<FadePolicy>> list = new ArrayList<EnumMap<FadePolicy>>();

        for (FadePolicy p : FadePolicy.values()) {
            list.add(new EnumMap<FadePolicy>(p, bundle.getString(p.name())));
        }

        boxFadePolice.getItems().setAll(list);
        boxFadePolice.getSelectionModel().selectFirst();
    }

    @Override
    public AbstractBehavior getBehavior() {
        Fade res = new Fade(boxFadePolice.getValue().getKey());
        res.setFadeInTime(Integer.parseInt(txtFadeInTime.getText()));
        res.setFadeOutTime(Integer.parseInt(txtFadeOutTime.getText()));

        return res;
    }
}

