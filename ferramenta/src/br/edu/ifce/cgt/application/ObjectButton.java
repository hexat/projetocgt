package br.edu.ifce.cgt.application;

import br.edu.ifce.cgt.application.controller.titleds.BonusTitledPane;
import br.edu.ifce.cgt.application.controller.titleds.GameObjectTitledPane;
import br.edu.ifce.cgt.application.controller.titleds.EnemyTitledPane;
import br.edu.ifce.cgt.application.controller.titleds.OppositeTitledPane;
import br.edu.ifce.cgt.application.controller.titleds.ActorTitledPane;
import cgt.core.CGTActor;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.core.CGTOpposite;
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
                }
            }
        });
    }

    private void clearAndAddGameObject() {
        configAccordion.getPanes().clear();

        TitledPane e = GameObjectTitledPane.getNode(object);
        if (e != null) {
            configAccordion.getPanes().add(e);
        }
    }
}
