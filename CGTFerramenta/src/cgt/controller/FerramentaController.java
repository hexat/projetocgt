package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FerramentaController implements Initializable {
	
	@FXML private Button btnGameObject;
	@FXML private Button btnMyWorld;
	@FXML private AnchorPane anchorConfig;
    @FXML private MenuItem menuExportar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuExportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Teste");
            }
        });
    }

	@FXML
	public void clickMyWorld() {
		anchorConfig.getChildren().clear();
        Accordion accordion = null;
		try {
            accordion =  FXMLLoader.load(getClass().getResource("/view/ConfigWorld.fxml"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (accordion != null) {
            anchorConfig.getChildren().add(accordion);
        }
	
	
	}

    @FXML public void menuExportarAction() {
        System.out.println("ok");
    }

	@FXML
	public void click(){
		
		
	}

}
