package cgt.controller;

import com.sun.javafx.collections.SetListenerHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConfigPersonagemController {
	
	
	@FXML private ScrollBar scroll;
	@FXML private Label lblSomDeMorte;
	@FXML private TextField txtSomDeMorte;
	@FXML private Button btnProcurarSomColisao;
	@FXML private Button btnProcurarSomDeMorte;
	@FXML private Button btnAddSomDeMorte;
	@FXML private Button btnRemoveSomDeMorte;
	@FXML private TextField txtSomDeColisao;
	@FXML private Button btnExcluirSom;
	
	
	@FXML private Button btnNovoSomColisao;
	@FXML private AnchorPane anchorSom;


	
	@FXML public void addNovoSomColisao(){
		
		Button btnAdd = new Button("+");
		
		Button btnRemove = new Button("-");
		Button btnProcurar = new Button("Procurar");
		TextField txtNovoSom = new TextField();
		txtNovoSom.setLayoutX(txtSomDeColisao.getLayoutX());
		txtNovoSom.setLayoutY(txtSomDeColisao.getLayoutY() + 32.0);
		
		btnProcurar.setLayoutX(btnProcurarSomColisao.getLayoutX());
		btnProcurar.setLayoutY(btnProcurarSomColisao.getLayoutY() + 32.0);
		
		btnAdd.setLayoutX(btnNovoSomColisao.getLayoutX());
		btnAdd.setLayoutY(btnNovoSomColisao.getLayoutY() + 32.0);
		
		btnRemove.setLayoutX(btnExcluirSom.getLayoutX());
		btnRemove.setLayoutY(btnExcluirSom.getLayoutY() + 32.0);
		
//		anchorSom.setTopAnchor(txtNovoSom,60.0);
//	    anchorSom.setLeftAnchor(txtNovoSom, 10.0);
//	    anchorSom.setRightAnchor(txtNovoSom,10.0);
	     anchorSom.getChildren().addAll(btnAdd,btnRemove,txtNovoSom, btnProcurar);
	     //altera a posição dos controles abaixo
	     txtSomDeMorte.setLayoutY(txtSomDeMorte.getLayoutY() + 32.0);
	     lblSomDeMorte.setLayoutY(lblSomDeMorte.getLayoutY() + 32.0);
	     btnProcurarSomDeMorte.setLayoutY(btnProcurarSomDeMorte.getLayoutY() + 32.0);
	     btnAddSomDeMorte.setLayoutY(btnAddSomDeMorte.getLayoutY() +32.0);
	     btnRemoveSomDeMorte.setLayoutY(btnRemoveSomDeMorte.getLayoutY() + 32.0);		
		 anchorSom.setPrefHeight(anchorSom.getPrefHeight() + 30);
		
		
	}
	
	
	
	

}
