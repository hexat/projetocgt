package cgt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * Created by infolev on 06/02/15.
 */
public class ListSpriteController implements Initializable {
    @FXML private ListView listViewSprites;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> a = FXCollections.observableArrayList();
        System.out.println(Config.getGame().getSpriteDB());
        System.out.println(Config.getGame().getSpriteDB().findAllId());
        for (String s : Config.getGame().getSpriteDB().findAllId()) {
            a.add(s);
        }

        listViewSprites.setItems(a);
    }
}
