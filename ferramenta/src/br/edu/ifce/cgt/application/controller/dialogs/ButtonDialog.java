package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ButtonPane;
import br.edu.ifce.cgt.application.controller.panes.HudPane;
import cgt.hud.CGTButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Created by infolev on 19/02/15.
 */
public class ButtonDialog extends BorderPane {

    private final Stage stage;

    @FXML private ButtonPane buttonControl;
    @FXML private HudPane hudControl;

    private CGTButton button;

    public ButtonDialog(CGTButton button, Window owner) {
        this.button = button;

        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/CGTButtonDialog.fxml"));
        view.setRoot(this);
        view.setController(this);


        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        if (button == null) {
            this.button = new CGTButton();
        }

        init();
    }

    private void init() {
        hudControl.setHud(button);
        buttonControl.setButton(button);
    }

    public void add() {
        stage.close();
    }

    public CGTButton getButton() {
        return button;
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
