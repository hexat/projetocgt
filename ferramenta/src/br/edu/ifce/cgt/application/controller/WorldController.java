package br.edu.ifce.cgt.application.controller;

import br.edu.ifce.cgt.application.controller.titleds.*;
import cgt.core.*;
import cgt.hud.AmmoDisplay;
import cgt.hud.EnemyGroupLifeBar;
import cgt.hud.HUDComponent;
import cgt.hud.IndividualLifeBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

import br.edu.ifce.cgt.application.Main;
import cgt.game.CGTGameWorld;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import br.edu.ifce.cgt.application.ObjectButton;

public class WorldController extends BorderPane {
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
    private VBox boxHud;
    @FXML
    private VBox boxBonus;

    private ArrayList<ObjectButton> listaInimigo = new ArrayList<ObjectButton>();
    private ArrayList<ObjectButton> listaOpposite = new ArrayList<ObjectButton>();

    private CGTGameWorld world;
    private Accordion configAccordion;

    public WorldController(CGTGameWorld world) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/World.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        configAccordion = (Accordion) Main.getApp().getScene().lookup("#configAccordion");

        setWorld(world);
    }

    @FXML public void btnConfigWorld() {
        configAccordion.getPanes().clear();
        configAccordion.getPanes().add(WorldTitledPane.getNode(world));
        configAccordion.getPanes().get(0).setExpanded(true);
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
            world.addOpposite(o);
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

    public void addAmmoDisplay() {
        AmmoDisplay ammoDisplay = new AmmoDisplay(world.getId());

        world.getHUD().add(ammoDisplay);
        boxHud.getChildren().add(new ButtonHud(ammoDisplay));
    }


    public void addEnemiesLifeBar() {
        EnemyGroupLifeBar bar = new EnemyGroupLifeBar();
        world.addEnemyGroupLifeBar(bar);
        boxHud.getChildren().add(new ButtonHud(bar));
    }

    public void addObjectLifeBar() {
        IndividualLifeBar lifeBar = new IndividualLifeBar();
        world.addLifeBar(lifeBar);
        boxHud.getChildren().add(new ButtonHud(lifeBar));
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

        boxHud.getChildren().clear();
        for (HUDComponent hud : world.getHUD()) {
            boxHud.getChildren().add(new ButtonHud(hud));
        }
    }

    public void addBonus(ActionEvent event) {
        CGTBonus bonus = new CGTBonus("Bonus");
        world.addBonus(bonus);

        ObjectButton button = new ObjectButton(bonus);

        boxBonus.getChildren().add(button);
    }

    public void addProjectile(ActionEvent event) {
        final CGTProjectile projectile = new CGTProjectile();

        world.getActor().addProjectile(projectile);
        boxActor.getChildren().add(new ObjectButton(projectile));
    }

    public void teste(ActionEvent event) {
        System.out.println("clikei");
    }

    private class ButtonHud extends Button {
        private HUDComponent hud;
        public ButtonHud(HUDComponent hud) {
            super(hud.getName());
            this.hud = hud;
            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addPane();
                }
            });
        }

        private void addPane() {
            configAccordion.getPanes().clear();
            if (hud instanceof IndividualLifeBar) {
                configAccordion.getPanes().add(new IndividualLifeBarTitledPane((IndividualLifeBar) hud));
            } else if (hud instanceof AmmoDisplay) {
                configAccordion.getPanes().add(new AmmoDisplayTitledPane((AmmoDisplay) hud));
            } else if (hud instanceof EnemyGroupLifeBar) {
                configAccordion.getPanes().add(new GroupLifeBarTitledPane((EnemyGroupLifeBar) hud));
            }
            configAccordion.getPanes().get(0).setExpanded(true);
        }
    }
}
