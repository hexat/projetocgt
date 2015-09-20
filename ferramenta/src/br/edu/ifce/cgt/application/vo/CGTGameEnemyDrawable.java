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
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class CGTGameEnemyDrawable extends CGTGameObjectDrawable {

    private CGTEnemy enemy;
    private EnemyTitledPane enemyTitledPane;

    public CGTGameEnemyDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(new CGTEnemy(), drawableObjectPane, drawableConfigurationsPane);
        this.enemy = (CGTEnemy) super.getObject();
        this.enemyTitledPane = new EnemyTitledPane(enemy);
    }

    @Override
    public void drawObject() {
        super.drawObject();
    }

    @Override
    public void drawConfigurationPanel() {
        TitledPane titledPaneObject = (TitledPane) super.getPane();
        TitledPane titledPaneEnemy = (TitledPane) this.getPane();
        Accordion accordion = new Accordion();
        accordion.getPanes().add(titledPaneObject);
        accordion.getPanes().add(titledPaneEnemy);
        super.updateConfigPane(accordion);
    }

    @Override
    public Node getPane() {
        return this.enemyTitledPane;
    }
}
