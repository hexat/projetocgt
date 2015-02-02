package application;

import cgt.controller.ConfigBonusController;
import cgt.controller.ConfigGameObjectController;
import cgt.controller.ConfigInimigoController;
import cgt.controller.ConfigOppositeController;
import cgt.controller.ConfigPersonagemController;
import cgt.core.CGTActor;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.core.CGTOpposite;
import cgt.hud.CGTButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Created by infolev on 02/02/15.
 */
public class ObjectButton<T extends CGTGameObject> extends Button {
    private final VBox paneConfig;
    private T object;

    public ObjectButton(final T object) {
        super(object.getLabel());
        this.object = object;
        paneConfig = (VBox) Main.getApp().getScene().lookup("#anchorConfig");

        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearAndAddGameObject();

                if (object instanceof CGTEnemy) {
                    paneConfig.getChildren().add(ConfigInimigoController.getNode((CGTEnemy) object));
                } else if (object instanceof CGTOpposite) {
                    paneConfig.getChildren().add(ConfigOppositeController.getNode((CGTOpposite) object));
                } else if (object instanceof CGTBonus) {
                    paneConfig.getChildren().add(ConfigBonusController.getNode((CGTBonus) object));
                } else if (object instanceof CGTActor) {
                    paneConfig.getChildren().add(ConfigPersonagemController.getNode((CGTActor) object));
                }
            }
        });
    }

    private void clearAndAddGameObject() {
        paneConfig.getChildren().clear();

        Parent e = ConfigGameObjectController.getNode(object);
        if (e != null) {
            paneConfig.getChildren().add(e);
        }
    }

    public Class<?> getObjectClass() {
        return object.getClass();
    }
}
