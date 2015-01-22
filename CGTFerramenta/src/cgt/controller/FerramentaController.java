package cgt.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class FerramentaController {
	
	@FXML private Button btnGameObject;
	@FXML private Button btnMyWorld;
	@FXML private AnchorPane anchorConfig;
	
	
	@FXML
	public void clickMyWorld() {
		anchorConfig.getChildren().clear();
		
		try {
			anchorConfig = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ConfigWorld.fxml"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	
	}
	
	@FXML
	public void click(){
		
		
	}

}
