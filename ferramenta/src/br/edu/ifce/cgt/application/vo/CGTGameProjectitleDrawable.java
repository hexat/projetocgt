package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.ProjectileTitledPane;
import cgt.core.CGTProjectile;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Joel on 08/11/2015.
 */
public class CGTGameProjectitleDrawable extends CGTGameObjectDrawable<CGTProjectile> {

    private ProjectileTitledPane pane;

    public CGTGameProjectitleDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(this.pane);
    }

    public CGTGameProjectitleDrawable(CGTProjectile gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, worldName, drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(this.pane);
    }

    @Override
    public void onCreate() {
        CGTProjectile projectile = new CGTProjectile();
        setObject(projectile);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.pane = new ProjectileTitledPane(getObject());
    }

    @Override
    public Node getPane() {
        return this.pane;
    }
}
