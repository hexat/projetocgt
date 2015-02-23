package br.edu.ifce.cgt.application.controller.titleds;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.core.CGTBonus;
import cgt.hud.CGTButton;
import cgt.policy.BonusPolicy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import sun.reflect.Reflection;

/**
 * Created by infolev on 02/02/15.
 */
public class BonusTitledPane extends TitledPane {
    @FXML public IntegerTextField txtScore;
    @FXML public IntegerTextField txtLifetime;
    @FXML public ComboBox<BonusPolicy> boxPolicies;
    @FXML public CheckBox txtDestroyable;
    @FXML public VBox panPolicies;

    private CGTBonus bonus;

    public BonusTitledPane(CGTBonus object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigBonus.fxml"));
        xml.setRoot(this);
        xml.setController(this);
        try {
            xml.load();
            BonusTitledPane controller = xml.getController();
            controller.setBonus(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boxPolicies.getItems().setAll(BonusPolicy.values());
    }

    public void addPolicy() {
        if (!boxPolicies.getSelectionModel().isEmpty() && !bonus.getPolicies().contains(boxPolicies.getValue())) {
            bonus.addPolicy(boxPolicies.getValue());
            addPolicyOnPane(boxPolicies.getValue());
        }
    }

    public void addPolicyOnPane(final BonusPolicy policy) {
        ItemViewPane pane = new ItemViewPane(policy.toString());
        pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
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
