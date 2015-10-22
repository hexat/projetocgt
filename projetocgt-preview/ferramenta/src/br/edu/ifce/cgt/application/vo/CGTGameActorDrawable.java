package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.GameObjectPane;
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

        GameObjectPane paneObject = (GameObjectPane) super.getPane();
        Accordion accordion = paneObject.getAccordionRoot();
        accordion.getPanes().add(this.actorTitledPane);
    }

    @Override
    public Node getPane() {
        return this.actorTitledPane;
    }

    @Override
    public String toString() {
        return super.toString() + " (Ator)";
    }
}
