package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.CGTGame;
import cgt.screen.CGTWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;

/**
 * Created by infolev on 06/02/15.
 */
public class ConfigGameController implements Initializable {

    @FXML private ComboBox<String> boxWindows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public static TitledPane getNode() {
        TitledPane res = null;
        try {
            res = FXMLLoader.load(Main.class.getResource("/view/ConfigGame.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
