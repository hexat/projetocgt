package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.core.CGTEnemy;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class ConfigInimigoController implements Initializable {

    private CGTEnemy enemy;

    public static Parent getNode(CGTEnemy object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigInimigo.fxml"));
        Parent el = null;
        try {
            el = xml.load();
            ConfigInimigoController controller = xml.getController();
            controller.setEnemy(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEnemy(CGTEnemy enemy) {
        this.enemy = enemy;
    }

    public CGTEnemy getEnemy() {
        return enemy;
    }
}
