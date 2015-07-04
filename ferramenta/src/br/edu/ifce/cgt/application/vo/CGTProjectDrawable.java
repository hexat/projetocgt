package br.edu.ifce.cgt.application.vo;

import javafx.scene.layout.AnchorPane;

public class CGTProjectDrawable extends AbstractDrawableObject {

    private String projectName;

    public CGTProjectDrawable(String projectName, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.projectName = projectName;
    }

    @Override
    public Object getObject() {
        return projectName;
    }

    @Override
    public void drawObject() {
    }

    @Override
    public void drawConfigurationPanel() {
    }

    @Override
    public String toString() {
        return this.projectName;
    }
}
