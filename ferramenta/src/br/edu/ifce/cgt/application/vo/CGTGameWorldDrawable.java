package br.edu.ifce.cgt.application.vo;

import cgt.game.CGTGameWorld;
import javafx.scene.layout.AnchorPane;

public class CGTGameWorldDrawable extends AbstractDrawableObject {

    private CGTGameWorld cgtGameWorld;

    public CGTGameWorldDrawable(CGTGameWorld world, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.cgtGameWorld = world;
    }

    @Override
    public Object getObject() {
        return cgtGameWorld;
    }

    @Override
    public void drawObject() {

    }

    @Override
    public void drawConfigurationPanel() {
    }


}
