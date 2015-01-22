package cgt.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class ConfigWorldController {
	@FXML private Button btnPesquisaBack;
	@FXML private TextField txtProcuraBack;
	
	
	
	public String getTextTxtProcurarBack(){
		return txtProcuraBack.getText();
	}
	
	public void pesquisarBackground(){
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o Background");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
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
		txtProcuraBack.setText(path);
		
	}

}
