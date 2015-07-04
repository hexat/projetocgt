package br.edu.ifce.cgt.application.vo;

import javafx.scene.layout.AnchorPane;

public abstract class AbstractDrawableObject implements DrawableObject {

    private AnchorPane drawableObjectPane;

    private AnchorPane drawableConfigurationsPane;

    public AbstractDrawableObject(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        this.drawableObjectPane = drawableObjectPane;
        this.drawableConfigurationsPane = drawableConfigurationsPane;
    }

    public AnchorPane getDrawableObjectPane() {
        return drawableObjectPane;
    }

    public AnchorPane getDrawableConfigurationsPane() {
        return drawableConfigurationsPane;
    }

}
