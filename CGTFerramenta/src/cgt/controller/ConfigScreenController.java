package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.game.CGTScreen;
import cgt.util.CGTTexture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import util.DialogsUtil;

/**
 * Created by infolev on 05/02/15.
 */
public class ConfigScreenController implements Initializable {
    @FXML private TextField txtBackScreen;
    @FXML private TextField txtScreenId;

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
        txtScreenId.setText(screen.getId());
        if (screen.getBackground() != null) {
            txtBackScreen.setText(screen.getBackground().getFile().getFilename());
        }
    }

    @FXML public void teste() {
        String newId = txtScreenId.getText();
        if (newId != null && !newId.equals("")&& !Config.getGame().getIds().contains(newId)) {
            screen.setId(newId);
            ((TabPane)Main.getApp().getScene().lookup("#tabFerramenta")).getSelectionModel().getSelectedItem().setText(newId);
        } else {
            txtScreenId.setText(screen.getId());
        }

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
