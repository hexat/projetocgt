package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.controller.titleds.ButtonScreenTitledPane;
import cgt.hud.CGTButtonScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by infolev on 19/02/15.
 */
public class ButtonScreenDialog {
    private final Stage stage;
    private final ButtonScreenTitledPane controller;

    public CGTButtonScreen buttonScreen;

    public ButtonScreenDialog(CGTButtonScreen buttonScreen, Window parent) {
        this.buttonScreen = buttonScreen;

        controller = new ButtonScreenTitledPane(buttonScreen);
        stage = new Stage();

        VBox box = (VBox) controller.getContent();
        box.setPadding(new Insets(10,10,10,10));
        box.setAlignment(Pos.CENTER_RIGHT);
        Button add = new Button("Adicionar");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controller.getButtonScreen().validate()) {
                    stage.close();
                }
            }
        });
        add.setDefaultButton(true);
        box.getChildren().add(add);

        stage.setScene(new Scene(box));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parent);
        stage.sizeToScene();

        if (buttonScreen != null && buttonScreen.getScreenToGo() != null) {
            controller.getBoxWindows().getSelectionModel().select(buttonScreen.getScreenToGo().getId());
        }
    }

    public CGTButtonScreen getButtonScreen() {
        return buttonScreen;
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
