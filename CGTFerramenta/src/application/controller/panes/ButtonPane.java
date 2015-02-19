package application.controller.panes;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by infolev on 19/02/15.
 */
public class ButtonPane extends GridPane {

    @FXML private TextField txtTextureUp;
    @FXML private TextField txtTextureDown;

    @FXML private Button btnTextureUp;
    @FXML private Button btnTextureDown;


    public ButtonPane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/CGTButton.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextField getTxtTextureDown() {
        return txtTextureDown;
    }

    public TextField getTxtTextureUp() {
        return txtTextureUp;
    }

    public Button getBtnTextureDown() {
        return btnTextureDown;
    }

    public Button getBtnTextureUp() {
        return btnTextureUp;
    }
}
