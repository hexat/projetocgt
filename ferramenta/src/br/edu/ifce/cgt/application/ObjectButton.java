package br.edu.ifce.cgt.application;

import br.edu.ifce.cgt.application.controller.titleds.*;
import cgt.core.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

/**
 * Created by infolev on 02/02/15.
 */
public class ObjectButton extends Button {
    private final Accordion configAccordion;
    private CGTGameObject object;

    public ObjectButton(CGTGameObject o) {
        super(o.getLabel());
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
                    configAccordion.getPanes().add(ActorTitledPane.getNode((CGTActor) object));
                } else if (object instanceof CGTProjectile) {
                    configAccordion.getPanes().add(new ProjectileTitledPane((CGTProjectile) object));
                }
                configAccordion.getPanes().get(1).setExpanded(true);
            }
        });
    }

    private void clearAndAddGameObject() {
        configAccordion.getPanes().clear();
        configAccordion.getPanes().add(new GameObjectTitledPane(object));
    }
}
