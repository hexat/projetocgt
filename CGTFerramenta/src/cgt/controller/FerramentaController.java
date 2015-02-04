package cgt.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Config;
import application.ObjectButton;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.core.CGTOpposite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FerramentaController //implements Initializable 
{
  
   
    @FXML
    private Button btnMyWorld;
    @FXML
    private VBox anchorConfig;
    @FXML
    private MenuItem menuExportar;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        menuExportar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Config.export();
//                System.out.println("Tetstet ");
//            }
//        });
//        gridMundo.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                clickMyWorld();
//            }
//        });
//    }

    @FXML
    public void clickMyWorld() {
        anchorConfig.getChildren().clear();
        Accordion accordion = null;

        try {
            accordion = FXMLLoader.load(getClass().getResource("/view/ConfigWorld.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (accordion != null) {
            anchorConfig.getChildren().add(accordion);
        }
    }

    
    @FXML
    public void menuExportarAction() {
        System.out.println("ok");
    }

    @FXML
    public void click() {

    }
}
