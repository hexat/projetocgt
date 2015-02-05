package cgt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import cgt.CGTGameWorld;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import application.Config;
import application.ObjectButton;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.core.CGTOpposite;

public class WorldController implements Initializable {
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

    private CGTGameWorld world;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
//            CGTEnemy e = new CGTEnemy("Inimigo");
//            Config.getWorld().addEnemy(e);
//            ObjectButton<CGTEnemy> btn = new ObjectButton<CGTEnemy>(e);
//            listaInimigo.add(btn);
//            gridMundo.add(btn, 1, listaInimigo.size() - 1);
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

    public void setWorld(CGTGameWorld world) {
        this.world = world;
    }

    public static Node getNode(CGTGameWorld world) {
        Node res = null;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/World.fxml"));
        try {
            res = xml.load();
            WorldController controller = xml.getController();
            controller.setWorld(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

}
