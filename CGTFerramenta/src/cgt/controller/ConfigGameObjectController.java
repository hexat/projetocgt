package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import cgt.core.CGTGameObject;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.DialogsUtil;

public class ConfigGameObjectController implements Initializable {
	@FXML private TextField txtProcuraSom;
	@FXML private TableView<String> tableSomColisao;
	private ObservableList<String> listaSomColisao;
	
	private CGTGameObject gameObject;

    public static TitledPane getNode(CGTGameObject object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGameObject.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            ConfigGameObjectController controller = xml.getController();
            controller.setGameObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listaSomColisao = FXCollections.observableArrayList();
        tableSomColisao = new TableView<String>();
    }
	
	@FXML public void btnProcurarSom(){
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

		File chosenFile = DialogsUtil.showOpenDialog("Som de Colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
        } else {
		    path = null;
		}

		txtProcuraSom.setText(path);
	}
	@FXML public void btnProcurarSomColisao(){

		
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o Background");

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

		File chosenFile = DialogsUtil.showOpenDialog("Selecione o som de colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		} else {
		    //default return value
		    path = null;
		}
		
		listaSomColisao.add(path);
		tableSomColisao.setEditable(true);
		tableSomColisao.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableSomColisao.setItems(listaSomColisao);
	}

    public void addAnimation() {
        ConfigAnimationController dialog = new ConfigAnimationController(gameObject);

        dialog.show();
    }

    public void setGameObject(CGTGameObject gameObject) {
        this.gameObject = gameObject;
    }

    public CGTGameObject getGameObject() {
        return gameObject;
    }

}
