package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.core.CGTGameObject;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import util.FileUtils;
import cgt.util.CGTSound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import util.Dialogs;

public class ConfigGameObjectController implements Initializable {
	@FXML private TextField txtProcuraSom;
	@FXML private TableView<String> tableSomColisao;
	private ObservableList<String> listaSomColisao;
	
	private CGTGameObject gameObject;

    public static Parent getNode(CGTGameObject object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGameObject.fxml"));
        Parent el = null;
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

		File chosenFile = FileUtils.showOpenDialog("Som de Colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
//            try {
//                Config.getWorld().setSoundLose(new CGTSound(Config.createAudio(chosenFile)));
//            } catch (IOException e) {
//                Dialogs.showErrorDialog();
//                e.printStackTrace();
//            }
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

		File chosenFile = FileUtils.showOpenDialog("Selecione o som de colisão", extFilter, extFilter2);
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

    public void setGameObject(CGTGameObject gameObject) {
        this.gameObject = gameObject;
    }

    public CGTGameObject getGameObject() {
        return gameObject;
    }

}
