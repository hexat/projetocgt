package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.controller.dialogs.ButtonScreenDialog;
import application.controller.titleds.ScreenTitledPane;
import cgt.game.CGTScreen;
import cgt.hud.CGTButtonScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Created by infolev on 05/02/15.
 */
public class ScreenController implements Initializable {
    private CGTScreen screen;

    @FXML private VBox panButtons;
    private Accordion configAccordion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configAccordion = (Accordion) Main.getApp().getScene().lookup("#configAccordion");
    }

    public void setScreen(CGTScreen screen) {
        this.screen = screen;
        updateButtonScreen();
    }

    private void updateButtonScreen() {
        panButtons.getChildren().clear();
        for (CGTButtonScreen btn : screen.getButtons()) {
            panButtons.getChildren().add(new ButtonScreen(btn));
        }
    }

    public CGTScreen getScreen() {
        return screen;
    }

    @FXML public void btnConfigScreen() {
        configAccordion.getPanes().clear();
        configAccordion.getPanes().add(ScreenTitledPane.getNode(screen));

    }

    public void addButtonScreen() {
        CGTButtonScreen btnScreen = new CGTButtonScreen();

        screen.getButtons().add(btnScreen);

        panButtons.getChildren().add(new ButtonScreen(btnScreen));
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

    private class ButtonScreen extends Button {
        private final CGTButtonScreen btnScreen;

        public ButtonScreen(final CGTButtonScreen btnScreen) {
            this.btnScreen = btnScreen;

            setText("Button Screen");

            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    configAccordion.getPanes().clear();

                    configAccordion.getPanes().add(new ButtonScreenDialog(btnScreen));
                }
            });
        }
    }
}
