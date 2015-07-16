package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigProjectPane;
import cgt.core.CGTProject;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CGTProjectDrawable extends AbstractDrawableObject {

    private CGTProject cgtProject;

    public CGTProjectDrawable(String projectName, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.cgtProject = new CGTProject(projectName);
    }

    @Override
    public Object getObject() {
        return this.cgtProject;
    }

    @Override
    public void drawObject() {
        Rectangle bounds = new Rectangle(this.cgtProject.getCanvasWidth(), this.cgtProject.getCanvasHeight());
        bounds.setFill(null);
        bounds.setStroke(Color.RED);
        bounds.setStrokeWidth(1);
        getDrawableObjectPane().getChildren().add(bounds);
    }

    @Override
    public void drawConfigurationPanel() {
        ConfigProjectPane projectPane = new ConfigProjectPane(this.cgtProject, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });

        super.addConfigurationPane(projectPane);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public String toString() {
        return this.cgtProject.getProjectName();
    }
}
