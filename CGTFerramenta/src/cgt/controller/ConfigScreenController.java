package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.CGTScreen;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;

/**
 * Created by infolev on 05/02/15.
 */
public class ConfigScreenController implements Initializable {
    private CGTScreen screen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setScreen(CGTScreen screen) {
        this.screen = screen;
    }

    public CGTScreen getScreen() {
        return screen;
    }

    public static TitledPane getNode(CGTScreen screen) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigScreen.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            ConfigScreenController controller = xml.getController();
            controller.setScreen(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }
}
