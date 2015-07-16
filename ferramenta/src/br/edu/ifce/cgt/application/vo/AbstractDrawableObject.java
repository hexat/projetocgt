package br.edu.ifce.cgt.application.vo;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public abstract class AbstractDrawableObject implements DrawableObject {

    private AnchorPane drawableObjectPane;

    private AnchorPane drawableConfigurationsPane;

    public AbstractDrawableObject(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        this.drawableObjectPane = drawableObjectPane;
        this.drawableConfigurationsPane = drawableConfigurationsPane;
        this.onCreate();
    }

    public AnchorPane getDrawableObjectPane() {
        return drawableObjectPane;
    }

    public AnchorPane getDrawableConfigurationsPane() {
        return drawableConfigurationsPane;
    }

    public void addConfigurationPane(Pane pane) {
        getDrawableConfigurationsPane().getChildren().removeAll(getDrawableConfigurationsPane().getChildren());
        getDrawableConfigurationsPane().getChildren().add(pane);
    }

}
