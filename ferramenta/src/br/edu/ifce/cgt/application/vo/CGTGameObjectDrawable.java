package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.GameObjectTitledPane;
import cgt.core.CGTGameObject;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class CGTGameObjectDrawable extends AbstractDrawableObject {

    private CGTGameObject gameObject;
    private GameObjectTitledPane gameObjectTitledPane;

    public CGTGameObjectDrawable(CGTGameObject gameObject, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.gameObject = gameObject;
        this.gameObjectTitledPane = new GameObjectTitledPane(this.gameObject);
    }

    @Override
    public Object getObject() {
        return gameObject;
    }

    @Override
    public Node getPane() {
        return this.gameObjectTitledPane;
    }

    @Override
    public void drawObject() {
    }

    @Override
    public void drawConfigurationPanel() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public String toString() {
        return gameObject.getId();
    }
}
