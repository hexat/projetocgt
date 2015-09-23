package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigProjectPane;
import cgt.core.CGTProject;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CGTProjectDrawable extends AbstractDrawableObject {

    private CGTProject cgtProject;
    private Rectangle size;
    private ConfigProjectPane projectPane;

    public CGTProjectDrawable(String projectName, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.cgtProject = new CGTProject(projectName);
        this.size = new Rectangle(this.cgtProject.getCanvasWidth(), this.cgtProject.getCanvasHeight());
        this.projectPane = new ConfigProjectPane(this.cgtProject, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public Object getObject() {
        return this.cgtProject;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof CGTProject)
            this.cgtProject = (CGTProject) obj;
    }

    @Override
    public Node getPane() {
        return this.projectPane;
    }

    @Override
    public void drawObject() {
        size.setWidth(this.cgtProject.getCanvasWidth());
        size.setHeight(this.cgtProject.getCanvasHeight());
        size.setFill(null);
        size.setStroke(Color.RED);
        size.setStrokeWidth(0.5);
        super.updateDrawPane(size);
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(projectPane);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public String toString() {
        return this.cgtProject.getProjectName();
    }
}
