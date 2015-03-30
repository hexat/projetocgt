package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.core.CGTBonus;
import cgt.policy.BonusPolicy;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by infolev on 02/02/15.
 */
public class BonusTitledPane extends TitledPane {
    @FXML public IntegerTextField txtScore;
    @FXML public IntegerTextField txtLifetime;
    @FXML public ComboBox<EnumMap<BonusPolicy>> boxPolicies;
    @FXML public CheckBox txtDestroyable;
    @FXML public VBox panPolicies;

    private CGTBonus bonus;

    public BonusTitledPane(CGTBonus object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigBonus.fxml"));
        xml.setRoot(this);
        xml.setController(this);
        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<EnumMap<BonusPolicy>> list = new ArrayList<EnumMap<BonusPolicy>>();
        ResourceBundle bundle = Pref.load().getBundle();
        for (BonusPolicy b : BonusPolicy.values()) {
            list.add(new EnumMap<BonusPolicy>(b, bundle.getString(b.name())));
        }

        boxPolicies.getItems().setAll(list);

        setBonus(object);

        txtDestroyable.setSelected(bonus.isDestroyable());
        txtLifetime.setValue(bonus.getLifeTime());
        txtScore.setValue(bonus.getScore());


        txtDestroyable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bonus.setDestroyable(txtDestroyable.isSelected());
            }
        });
        txtScore.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    bonus.setScore(txtScore.getValue());
                }
            }
        });

        txtLifetime.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    bonus.setLifeTime(txtLifetime.getValue());
                }
            }
        });
    }

    public void addPolicy() {
        if (!boxPolicies.getSelectionModel().isEmpty() && !bonus.getPolicies().contains(boxPolicies.getValue().getKey())) {
            bonus.addPolicy(boxPolicies.getValue().getKey());
            addPolicyOnPane(boxPolicies.getValue().getKey());
        }
    }

    public void addPolicyOnPane(final BonusPolicy policy) {
        boolean found = false;
        String item = policy.name();
        for (int i = 0; i < boxPolicies.getItems().size() && !found; i++) {
            if (boxPolicies.getItems().get(i).getKey() == policy) {
                found = true;
                item = boxPolicies.getItems().get(i).getValue();
            }
        }
        ItemViewPane pane = new ItemViewPane(item);
        pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bonus.removePolicy(policy);
                updatePolicies();
            }
        });
        panPolicies.getChildren().add(pane);
    }

    private void updatePolicies() {
        panPolicies.getChildren().clear();
        for (BonusPolicy policy : bonus.getPolicies()) {
            addPolicyOnPane(policy);
        }
    }

    public void setBonus(CGTBonus bonus) {
        this.bonus = bonus;

        if (bonus != null) {
            updatePolicies();
        }
    }

    public CGTBonus getBonus() {
        return bonus;
    }
}
