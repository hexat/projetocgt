package br.edu.ifce.cgt.application.vo;

import cgt.hud.HUDComponent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class HUDComponetDrawable extends AbstractDrawableObject {

    private HUDComponent hudComponent;

    public HUDComponetDrawable(HUDComponent hudComponent, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.hudComponent = hudComponent;
    }

    @Override
    public Object getObject() {
        return hudComponent;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof HUDComponent )
            this.hudComponent = (HUDComponent) obj;
    }

    @Override
    public Node getPane() {
        return null;
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
}
