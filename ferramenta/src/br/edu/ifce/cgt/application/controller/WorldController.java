package br.edu.ifce.cgt.application.controller;

import br.edu.ifce.cgt.application.controller.titleds.WorldTitledPane;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import javafx.scene.layout.VBox;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import cgt.game.CGTGameWorld;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import br.edu.ifce.cgt.application.ObjectButton;
import cgt.core.CGTActor;
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
    private VBox boxActor;
    @FXML
    private VBox boxEnemies;
    @FXML
    private VBox boxOpposites;
    @FXML
    private VBox boxBonus;

    private ArrayList<ObjectButton> listaInimigo = new ArrayList<ObjectButton>();
    private ArrayList<ObjectButton> listaOpposite = new ArrayList<ObjectButton>();

    private CGTGameWorld world;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML public void btnConfigWorld() {
        Accordion configAccordion = (Accordion) Main.getApp().getScene().lookup("#configAccordion");
        configAccordion.getPanes().clear();
        configAccordion.getPanes().add(WorldTitledPane.getNode(world));
    }

    @FXML
    public void addActorInWorld() {
        if (world.getActor() != null) {
            Action response = Dialogs.create()
                    .owner(Main.getApp())
                    .title("Confirm Dialog")
                    .masthead("Look, a Confirm Dialog")
                    .message("Do you want to continue?")
                    .showConfirm();

            if (response != Dialog.ACTION_OK) {
                return;
            }
        }

        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o ator")
                .message("Digite um nome para o ator:")
                .showTextInput("Ator");

        if (response.isPresent()) {
            CGTActor actor = new CGTActor(response.get());
            world.setActor(actor);
            ObjectButton btn = new ObjectButton(actor);
            boxActor.getChildren().addAll(btn);
        }
    }

    @FXML
    public void addEnemyInWorld() {
        if (listaInimigo.size() < 6) {
            CGTEnemy e = new CGTEnemy("Inimigo");
            world.addEnemy(e);
            ObjectButton btn = new ObjectButton(e);
            listaInimigo.add(btn);
            boxEnemies.getChildren().addAll(btn);
        } else {
            System.out.println("Não pode add outro inimigo");
        }
    }

    @FXML
    public void addOpositeInWorld() {
        if (listaOpposite.size() < 6) {
            CGTOpposite o = new CGTOpposite("Opositor");
            ObjectButton btn = new ObjectButton(o);
            listaOpposite.add(btn);
            boxOpposites.getChildren().addAll(btn);
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
        if (world.getActor() != null) {
            boxActor.getChildren().clear();
            boxActor.getChildren().add(new ObjectButton(world.getActor()));
        }
        boxEnemies.getChildren().clear();
        for (CGTEnemy enemy : world.getEnemies()) {
            boxEnemies.getChildren().add(new ObjectButton(enemy));
        }

        boxOpposites.getChildren().clear();
        for (CGTOpposite opposite : world.getOpposites()) {
            boxOpposites.getChildren().add(new ObjectButton(opposite));
        }

        boxBonus.getChildren().clear();
        for (CGTBonus bonus : world.getBonus()) {
            boxBonus.getChildren().add(new ObjectButton(bonus));
        }
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
