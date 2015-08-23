package br.edu.ifce.cgt.application.vo;

import cgt.core.CGTGameObject;
import javafx.scene.layout.AnchorPane;

public class CGTGameObjectDrawable extends AbstractDrawableObject {

    private CGTGameObject gameObject;

    public CGTGameObjectDrawable(CGTGameObject gameObject, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.gameObject = gameObject;
    }

    @Override
    public Object getObject() {
        return gameObject;
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
