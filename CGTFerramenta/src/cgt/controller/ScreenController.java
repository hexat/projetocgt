package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.game.CGTScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;

/**
 * Created by infolev on 05/02/15.
 */
public class ScreenController implements Initializable {
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

    @FXML public void btnConfigScreen() {
        Accordion configAccordion = (Accordion) Main.getApp().getScene().lookup("#configAccordion");
        configAccordion.getPanes().clear();
        configAccordion.getPanes().add(ConfigScreenController.getNode(screen));

    }

    public static Node getNode(CGTScreen screen) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/Screen.fxml"));
        Node el = null;
        try {
            el = xml.load();
            ScreenController controller = xml.getController();
            controller.setScreen(screen);
            controller.btnConfigScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }
}
