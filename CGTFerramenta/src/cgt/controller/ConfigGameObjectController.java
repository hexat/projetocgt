package cgt.controller;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

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
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o Background");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.getExtensionFilters().add(extFilter2);
		//fileChooser.showOpenDialog(stage);
		
		//Choose the file
		File chosenFile = fileChooser.showOpenDialog(null);
		//Make sure a file was selected, if not return default
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		} else {
		    //default return value
		    path = null;
		}
		
		
		txtProcuraSom.setText(path);
		
	}
	@FXML public void btnProcurarSomColisao(){
		 tableColumn = new TableColumn();
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o Background");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.getExtensionFilters().add(extFilter2);

		
		//Choose the file
		File chosenFile = fileChooser.showOpenDialog(null);
		//Make sure a file was selected, if not return default
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		} else {
		    //default return value
		    path = null;
		}
		
		//tableColumn.setMinWidth(100);
		
		listaSomColisao.add(path);
		tableSomColisao.setEditable(true);
		tableSomColisao.setItems(listaSomColisao);
		
		
		
		
		
		
		
	}
	
}
