package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ButtonPane;
import br.edu.ifce.cgt.application.controller.panes.HudPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.hud.CGTButtonScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class ButtonScreenTitledPane extends TitledPane {
    private final CGTButtonScreen buttonScreen;

    @FXML private ComboBox<String> boxWindows;

    @FXML private ButtonPane buttonControl;

    @FXML private HudPane hudControl;

    public ButtonScreenTitledPane(CGTButtonScreen button) {
        this.buttonScreen = button;

        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigButtonScreen.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        init();

        boxWindows.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonScreen.setScreenToGo(boxWindows.getValue());
            }
        });
    }

    private void init() {
        boxWindows.getItems().setAll(Config.get().getGame().getIds());

        hudControl.setHud(buttonScreen);

        if (buttonScreen.getScreenToGo() != null) {
            boxWindows.getSelectionModel().select(buttonScreen.getScreenToGo().getId());
        }

        buttonControl.setButton(buttonScreen);
    }

    public CGTButtonScreen getButtonScreen() {
        return buttonScreen;
    }

    public ComboBox<String> getBoxWindows() {
        return boxWindows;
    }
}
