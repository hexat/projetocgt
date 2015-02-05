package cgt.controller;

import java.io.File;

import application.Config;
import cgt.util.CGTTexture;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import util.DialogsUtil;

public class ConfigWorldController {
	@FXML private Button btnPesquisaBack;
	@FXML private TextField txtProcuraBack;

	public String getTextTxtProcurarBack(){
		return txtProcuraBack.getText();
	}
	
	public void pesquisarBackground(){
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");

		File chosenFile = DialogsUtil.showOpenDialog("Selecionar background", extFilter);

		String path = null;

		if(chosenFile != null) {
		    path = chosenFile.getPath();
		}

		txtProcuraBack.setText(path);
	}

}
