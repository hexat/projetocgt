package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.CGTScreen;
import cgt.util.CGTTexture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import util.DialogsUtil;

/**
 * Created by infolev on 05/02/15.
 */
public class ConfigScreenController implements Initializable {
    @FXML private TextField txtBackScreen;

    private CGTScreen screen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML public void btnChooseBackScreen() {
        File file = DialogsUtil.showOpenDialog("Escolha uma imagem", DialogsUtil.IMG_FILTER);
        if (file != null) {
            screen.setBackground(new CGTTexture(Config.createImg(file)));
            txtBackScreen.setText(file.getName());
        }
    }

    public void setScreen(CGTScreen screen) {
        this.screen = screen;
    }

    public CGTScreen getScreen() {
        return screen;
    }

    public static TitledPane getNode(CGTScreen screen) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigScreen.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            ConfigScreenController controller = xml.getController();
            controller.setScreen(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }
}
