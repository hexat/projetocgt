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

public class FerramentaController implements Initializable {
    @FXML
    private Button btnGameObject;
    @FXML
    private Button btnPersonagem;
    @FXML
    private Button btnInimigo;
    @FXML
    private Button btnOposite;
    @FXML
    private GridPane gridMundo;
    private ArrayList<ObjectButton> listaInimigo = new ArrayList<ObjectButton>();
    private ArrayList<ObjectButton> listaOpposite = new ArrayList<ObjectButton>();
    @FXML
    private Button btnMyWorld;
    @FXML
    private VBox anchorConfig;
    @FXML
    private MenuItem menuExportar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuExportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Config.export();
                System.out.println("Tetstet ");
            }
        });
        gridMundo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clickMyWorld();
            }
        });
    }

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
    public void addActorInWorld() {
        CGTActor actor = new CGTActor("Actor");
        ObjectButton<CGTActor> btn = new ObjectButton<CGTActor>(actor);
        gridMundo.add(btn, 0, 1);
    }

    @FXML
    public void addEnemyInWorld() {
        if (listaInimigo.size() < 6) {
            CGTEnemy e = new CGTEnemy("Inimigo");
            Config.getWorld().addEnemy(e);
            ObjectButton<CGTEnemy> btn = new ObjectButton<CGTEnemy>(e);
            listaInimigo.add(btn);
            gridMundo.add(btn, 1, listaInimigo.size() - 1);
        } else {
            System.out.println("Não pode add outro inimigo");
        }
    }

    @FXML
    public void addOpositeInWorld() {
        if (listaOpposite.size() < 6) {
            CGTOpposite o = new CGTOpposite("Opositor");
            ObjectButton<CGTOpposite> btn = new ObjectButton<CGTOpposite>(o);
            listaOpposite.add(btn);
            gridMundo.add(btn, 2, listaInimigo.size() - 1);
        } else {
//			Stage dialog = new Stage();
//			dialog.initStyle(StageStyle.UTILITY);
//			Scene scene = new Scene(new Group(new Text(50, 50, "Não pode add outro opposite")));
//			dialog.setScene(scene);
//			dialog.show();
            System.out.println("Não pode add outro opposite");
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
