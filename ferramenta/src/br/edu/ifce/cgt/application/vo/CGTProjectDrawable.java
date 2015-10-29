package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigProjectPane;
import cgt.core.CGTProject;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CGTProjectDrawable extends AbstractDrawableObject<CGTProject> {
    private Rectangle size;
    private ConfigProjectPane projectPane;

    public CGTProjectDrawable(String projectName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(new CGTProject(projectName), drawableObjectPane, drawableConfigurationsPane);
    }

    @Override
    public void onStart() {
        this.size = new Rectangle(getObject().getCanvasWidth(), getObject().getCanvasHeight());
        this.projectPane = new ConfigProjectPane(getObject(), new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public Node getPane() {
        return this.projectPane;
    }

    public  ConfigProjectPane getConfig(){
        return this.projectPane;
    }

    @Override
    public void drawObject() {
        size.setWidth(getObject().getCanvasWidth());
        size.setHeight(getObject().getCanvasHeight());
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
        return getObject().getProjectName();
    }
}
