package br.edu.ifce.cgt.application;

import br.edu.ifce.cgt.application.controller.WorldController;
import br.edu.ifce.cgt.application.controller.panes.GameObjectPane;
import br.edu.ifce.cgt.application.controller.titleds.*;
import cgt.core.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

/**
 * Created by infolev on 02/02/15.
 */
public class ObjectButton extends Button {
    private final Accordion configAccordion;
    private final WorldController worldController;
    private CGTGameObject object;

    public ObjectButton(WorldController controller, CGTGameObject o) {
        super(o.getId());
        this.worldController = controller;
        this.object = o;
        configAccordion = (Accordion) Main.getApp().getScene().lookup("#configAccordion");

        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearAndAddGameObject();

                if (object instanceof CGTEnemy) {
                    configAccordion.getPanes().add(new EnemyTitledPane((CGTEnemy) object));
                } else if (object instanceof CGTOpposite) {
                    configAccordion.getPanes().add(new OppositeTitledPane((CGTOpposite) object));
                } else if (object instanceof CGTBonus) {
                    configAccordion.getPanes().add(new BonusTitledPane((CGTBonus) object));
                } else if (object instanceof CGTActor) {
                    configAccordion.getPanes().add(new ActorTitledPane((CGTActor) object));
                } else if (object instanceof CGTProjectile) {
                    configAccordion.getPanes().add(new ProjectileTitledPane((CGTProjectile) object));
                }
                configAccordion.getPanes().get(1).setExpanded(true);
            }
        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (object instanceof CGTEnemy) {
                        CGTEnemy e = new CGTEnemy((CGTEnemy) object);

                        worldController.getWorld().addEnemy(e);
                        worldController.updatePanes();
                    } else if (object instanceof CGTOpposite) {
                        CGTOpposite e = new CGTOpposite((CGTOpposite) object);

                        worldController.getWorld().addOpposite(e);
                        worldController.updatePanes();
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Janela de Confirmação");
                    alert.setHeaderText("Atenção, confime sua opção");
                    alert.setContentText("Tem certeza que deseja remover?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        if (worldController.getWorld().removeObject(object)) {
                            alert.setHeaderText(":)");
                            alert.setContentText("Objeto removido com sucesso!");
                            alert.showAndWait();
                            worldController.updatePanes();
                        } else {
                            alert.setHeaderText(":(");
                            alert.setContentText("Não foi possível encontrar este objeto");
                            alert.showAndWait();
                        }
                    }
                }
            }
        });
    }

    private void clearAndAddGameObject() {
        configAccordion.getPanes().clear();
        GameObjectPane pane = new GameObjectPane(object);
        Accordion accordion = (Accordion) pane.getChildren().get(0);
        for (TitledPane titledPane : accordion.getPanes()) {
            configAccordion.getPanes().add(titledPane);
        }
    }
}
