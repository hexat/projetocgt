package cgt.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
import application.EnemyButton;
import application.OppositeButton;


public class FerramentaController implements Initializable {
	
	@FXML private Button btnGameObject;

	@FXML private Button btnPersonagem;
	@FXML private Button btnInimigo;
	@FXML private Button btnOposite;
	@FXML private GridPane gridMundo;
	private ArrayList<EnemyButton> listaInimigo = new ArrayList<EnemyButton>();
	private ArrayList<OppositeButton> listaOpposite = new ArrayList<OppositeButton>();
	
	
	
	
	
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
	
	@FXML public void addActorInWorld(){

		
		gridMundo.add(new Button("Personagem"), 0, 1);

	}
	@FXML public void addEnemyInWorld(){
		if(listaInimigo.size() < 6){
			EnemyButton enemy = new EnemyButton("Inimigo");
			listaInimigo.add(enemy);
			gridMundo.add(enemy, 1,listaInimigo.size()-1);
			
		}
		else{
			System.out.println("Não pode add outro inimigo");
		}

			
		
			
		
		
	}
	@FXML public void addOpositeInWorld(){
		
		if(listaOpposite.size() < 6){
			OppositeButton opposite = new OppositeButton("Opposite");
			listaOpposite.add(opposite);
			gridMundo.add(opposite, 2,listaInimigo.size()-1);
			
		}
		else{
//			Stage dialog = new Stage();
//			dialog.initStyle(StageStyle.UTILITY);
//			Scene scene = new Scene(new Group(new Text(50, 50, "Não pode add outro opposite")));
//			dialog.setScene(scene);
//			dialog.show();
			System.out.println("Não pode add outro opposite");

		}
			
		
		
		
     
	
	
	}

    @FXML public void menuExportarAction() {
        System.out.println("ok");
    }

	@FXML
	public void click(){

		
		
	}
	
	

	
	


}
