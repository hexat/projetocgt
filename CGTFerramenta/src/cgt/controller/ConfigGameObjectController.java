package cgt.controller;

import java.io.File;
import java.io.IOException;

import application.Config;
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

public class ConfigGameObjectController {
	@FXML private TextField txtProcuraSom;
	@FXML private TableView<String> tableSomColisao;
	private ObservableList<String> listaSomColisao;
	@FXML TableColumn tableColumn;
	
	public ConfigGameObjectController() {
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
            try {
                Config.getWorld().setSoundLose(new CGTSound(Config.createAudio(chosenFile)));
            } catch (IOException e) {
                Dialogs.showErrorDialog();
                e.printStackTrace();
            }
        } else {
		    path = null;
		}

		txtProcuraSom.setText(path);
	}
	@FXML public void btnProcurarSomColisao(){
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
		tableSomColisao.setItems(listaSomColisao);
	}
	
}
