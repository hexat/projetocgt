package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.ActorTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTActor;
import cgt.core.CGTEnemy;
import cgt.game.CGTGameWorld;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class CGTGameActorDrawable extends CGTGameObjectDrawable<CGTActor> {
    private ActorTitledPane actorTitledPane;

    public CGTGameActorDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
    }

    public CGTGameActorDrawable(CGTActor gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, worldName, drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(actorTitledPane);
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
        Optional<Pair<String, String>> result = showGameObjectDialog();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            setObject(new CGTActor(id));
            setWorldName(worldName);
        }
    }

    @Override
    public String toString() {
        return getObject().getId();// + " (Ator)";
    }
}
