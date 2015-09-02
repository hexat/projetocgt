package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.ActorTitledPane;
import cgt.core.CGTActor;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class CGTGameActorDrawable extends CGTGameObjectDrawable {

    private CGTActor actor;
    private ActorTitledPane actorTitledPane;

    public CGTGameActorDrawable(CGTActor actor, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
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
        TitledPane titledPaneObject = (TitledPane) super.getPane();
        TitledPane titledPaneActor = (TitledPane) this.getPane();
        Accordion accordion = new Accordion();
        accordion.getPanes().add(titledPaneObject);
        accordion.getPanes().add(titledPaneActor);
        super.updateConfigPane(accordion);
    }

    @Override
    public String toString() {
        return this.actor.getId();
    }
}
