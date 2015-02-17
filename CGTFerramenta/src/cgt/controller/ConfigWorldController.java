package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.game.CGTGameWorld;
import cgt.util.CGTTexture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import util.DialogsUtil;

public class ConfigWorldController implements Initializable {
	@FXML private Button btnPesquisaBack;
	@FXML private TextField txtProcuraBack;

    private CGTGameWorld world;

    public void setWorld(CGTGameWorld world) {
        this.world = world;

        if (world.getBackground() != null) {
            txtProcuraBack.setText(world.getBackground().getFile().getFilename());
        }
    }

    public CGTGameWorld getWorld() {
        return world;
    }

    public static TitledPane getNode(CGTGameWorld world) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigWorld.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            ConfigWorldController controller = xml.getController();
            controller.setWorld(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }

	public String getTextTxtProcurarBack(){
		return txtProcuraBack.getText();
	}
	
	public void pesquisarBackground(){
		File chosenFile = DialogsUtil.showOpenDialog("Selecionar background", DialogsUtil.IMG_FILTER);

		String path = "";

		if(chosenFile != null) {
            world.setBackground(new CGTTexture(Config.createImg(chosenFile)));
            path = chosenFile.getName();
		}

		txtProcuraBack.setText(path);
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
