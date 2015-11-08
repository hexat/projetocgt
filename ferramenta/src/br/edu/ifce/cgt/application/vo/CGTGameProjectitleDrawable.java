package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.ProjectileTitledPane;
import cgt.core.CGTProjectile;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Optional;

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

        Optional<Pair<String, String>> result = showGameObjectDialog();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            CGTProjectile projectile = new CGTProjectile();
            projectile.setId(id);
            setObject(projectile);
            setWorldName(worldName);
        }
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
