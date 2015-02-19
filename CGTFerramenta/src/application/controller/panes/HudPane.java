package application.controller.panes;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class HudPane extends GridPane {
    @FXML private TextField txtRelX;
    @FXML private TextField txtRelY;
    @FXML private TextField txtWid;
    @FXML private TextField txtHei;

    public HudPane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigHudComponent.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TextField getTFRelativeX() {
        return txtRelX;
    }

    public TextField getTFRelativeY() {
        return txtRelY;

    }

    public TextField getTFRelativeWidth() {
        return txtWid;
    }

    public TextField getTFRelativeHeight() {
        return txtHei;
    }

    public float getRelativeX() {
        float res = 0;
        res = Float.parseFloat(txtRelX.getText());
        return res;
    }

    public float getRelativeY() {
        float res = 0;
        res = Float.parseFloat(txtRelY.getText());
        return res;

    }

    public float getRelativeWidth() {
        return Float.parseFloat(txtWid.getText());
    }

    public float getRelativeHeight() {
        return Float.parseFloat(txtHei.getText());
    }

}
