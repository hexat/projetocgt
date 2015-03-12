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
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;

/**
 * Created by infolev on 06/02/15.
 */
public class GameTitledPane extends TitledPane {
    @FXML private TextField txtVersion;
    @FXML private TextField txtGameName;
    @FXML private TextField txtGameId;
    @FXML private TextField txtVersionCode;
    @FXML private ImageView img32;
    @FXML private ImageView img48;
    @FXML private ImageView img72;
    @FXML private ImageView img96;
    @FXML private ImageView img144;

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

        init();
    }

    private void init() {
        boxWindows.getItems().clear();
        for (String s : Config.getGame().getIds()) {
            boxWindows.getItems().add(s);
        }
        if (Config.getGame().getStartWindow() != null) {
            boxWindows.getSelectionModel().select(Config.getGame().getStartWindow().getId());
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
