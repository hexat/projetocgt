package cgt.controller;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.EnemyButton;
import application.OppositeButton;

public class FerramentaController {
	
	@FXML private Button btnGameObject;
	@FXML private Button btnPersonagem;
	@FXML private Button btnInimigo;
	@FXML private Button btnOposite;
	@FXML private GridPane gridMundo;
	private ArrayList<EnemyButton> listaInimigo = new ArrayList<EnemyButton>();
	private ArrayList<OppositeButton> listaOpposite = new ArrayList<OppositeButton>();
	
	
	
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
	
	

	
	


}
