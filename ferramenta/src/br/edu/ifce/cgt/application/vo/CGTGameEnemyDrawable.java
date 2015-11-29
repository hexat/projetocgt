package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.EnemyTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class CGTGameEnemyDrawable extends CGTGameObjectDrawable<CGTEnemy> {
    private EnemyTitledPane enemyTitledPane;

    public CGTGameEnemyDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(enemyTitledPane);
    }

    public CGTGameEnemyDrawable(CGTEnemy gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, worldName, drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(enemyTitledPane);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.enemyTitledPane = new EnemyTitledPane(getObject());
    }

    @Override
    public Node getPane() {
        return this.enemyTitledPane;
    }

    @Override
    public void onCreate() {
        Optional<Pair<String, String>> result = showGameObjectDialog();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            setObject(new CGTEnemy(id));
            setWorldName(worldName);
        }
    }

    @Override
    public String toString() {
        return getObject().getId();
    }
}
