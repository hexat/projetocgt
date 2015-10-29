package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.ActorTitledPane;
import cgt.core.CGTActor;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class CGTGameActorDrawable extends CGTGameObjectDrawable<CGTActor> {
    private ActorTitledPane actorTitledPane;

    public CGTGameActorDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
    }

    public CGTGameActorDrawable(CGTActor gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, worldName, drawableObjectPane, drawableConfigurationsPane);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.actorTitledPane = new ActorTitledPane(getObject());
    }

    @Override
    public Node getPane() {
        return this.actorTitledPane;
    }

    @Override
    public void onCreate() {
        String pair = showInputIdDialog();
        if (pair != null) {
            setObject(new CGTActor(pair));
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (Ator)";
    }
}
