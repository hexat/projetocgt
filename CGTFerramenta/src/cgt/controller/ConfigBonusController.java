package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.core.CGTBonus;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

/**
 * Created by infolev on 02/02/15.
 */
public class ConfigBonusController implements Initializable {
    private CGTBonus bonus;

    public static Parent getNode(CGTBonus object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigBonus.fxml"));
        Parent el = null;
        try {
            el = xml.load();
            ConfigBonusController controller = xml.getController();
            controller.setBonus(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setBonus(CGTBonus bonus) {
        this.bonus = bonus;
    }

    public CGTBonus getBonus() {
        return bonus;
    }
}
