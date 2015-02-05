package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.core.CGTOpposite;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;

/**
 * Created by infolev on 02/02/15.
 */
public class ConfigOppositeController implements Initializable {
    private CGTOpposite opposite;

    public static TitledPane getNode(CGTOpposite object) {
        TitledPane res = null;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigOpposite.fxml"));
        try {
            res = xml.load();
            ConfigOppositeController controller = xml.getController();
            controller.setOpposite(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setOpposite(CGTOpposite opposite) {
        this.opposite = opposite;
    }

    public CGTOpposite getOpposite() {
        return opposite;
    }
}
