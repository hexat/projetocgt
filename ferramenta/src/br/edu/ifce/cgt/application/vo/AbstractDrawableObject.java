package br.edu.ifce.cgt.application.vo;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class AbstractDrawableObject<T> implements DrawableObject<T> {

    private T object;

    private Pane drawableObjectPane;

    private Pane drawableConfigurationsPane;

    public AbstractDrawableObject(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        this.drawableObjectPane = drawableObjectPane;
        this.drawableConfigurationsPane = drawableConfigurationsPane;
        this.onCreate();
    }

    public AbstractDrawableObject(T object, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        this.object = object;
        this.drawableObjectPane = drawableObjectPane;
        this.drawableConfigurationsPane = drawableConfigurationsPane;
    }

    public Pane getDrawableObjectPane() {
        return drawableObjectPane;
    }

    public Pane getDrawableConfigurationsPane() {
        return drawableConfigurationsPane;
    }

    @Override
    public T getObject() {
        return object;
    }

    @Override
    public void setObject(T object) {
        this.object = object;
    }

    public void updateDrawPane(Node node) {
        getDrawableObjectPane().getChildren().remove(node);
        getDrawableObjectPane().getChildren().add(node);
    }

    public void updateConfigPane(Pane pane) {
        getDrawableConfigurationsPane().getChildren().removeAll(getDrawableConfigurationsPane().getChildren());
        getDrawableConfigurationsPane().getChildren().add(pane);
    }

    public void updateConfigPane(Node node) {
        getDrawableConfigurationsPane().getChildren().removeAll(getDrawableConfigurationsPane().getChildren());
        getDrawableConfigurationsPane().getChildren().add(node);
    }
}
