package br.edu.ifce.cgt.application.controller.titleds;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;

/**
 * Created by infolev on 06/02/15.
 */
public class GameTitledPane extends TitledPane {

    @FXML private ComboBox<String> boxWindows;

    public GameTitledPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGame.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boxWindows.getItems().clear();
        for (String s : Config.getGame().getIds()) {
            boxWindows.getItems().add(s);
        }
    }

    @FXML public void setWindowDefault() {
        String id = boxWindows.getSelectionModel().getSelectedItem();
        System.out.println(id);
        if (id != null) {
            Config.getGame().setStartWindowId(id);
        }
    }
}
