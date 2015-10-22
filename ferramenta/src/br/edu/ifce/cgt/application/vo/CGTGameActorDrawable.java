package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.ActorTitledPane;
import cgt.core.CGTActor;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class CGTGameActorDrawable extends CGTGameObjectDrawable {

    private CGTActor actor;
    private ActorTitledPane actorTitledPane;

    public CGTGameActorDrawable(CGTActor actor, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(actor, drawableObjectPane, drawableConfigurationsPane);
        this.actor = actor;
        this.actorTitledPane = new ActorTitledPane(actor);
    }

    @Override
    public Node getPane() {
        return this.actorTitledPane;
    }

    @Override
    public void drawObject() {
        super.drawObject();
    }

    @Override
    public void drawConfigurationPanel() {
        Pane paneObject = (Pane) super.getPane();
        if (!paneObject.getChildren().isEmpty()) {
            Accordion accordion = (Accordion) paneObject.getChildren().get(0);
            TitledPane titledPaneActor = (TitledPane) this.getPane();
            accordion.getPanes().add(titledPaneActor);
            super.updateConfigPane(accordion);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (Ator)";
    }
}
