package application;

import application.controller.titleds.BonusTitledPane;
import application.controller.titleds.GameObjectTitledPane;
import application.controller.titleds.EnemyTitledPane;
import application.controller.titleds.OppositeTitledPane;
import application.controller.titleds.ActorTitledPane;
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
                    configAccordion.getPanes().add(EnemyTitledPane.getNode((CGTEnemy) object));
                } else if (object instanceof CGTOpposite) {
                    configAccordion.getPanes().add(OppositeTitledPane.getNode((CGTOpposite) object));
                } else if (object instanceof CGTBonus) {
                    configAccordion.getPanes().add(BonusTitledPane.getNode((CGTBonus) object));
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
