package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.titleds.EnemyTitledPane;
import cgt.core.CGTEnemy;

import cgt.core.CGTGameObject;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

public class CGTGameEnemyDrawable extends CGTGameObjectDrawable {

    private CGTEnemy enemy;
    private EnemyTitledPane enemyTitledPane;

    public CGTGameEnemyDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(new CGTEnemy(), drawableObjectPane, drawableConfigurationsPane);
        super.setObject(this.enemy);
        this.setObject(this.enemy);
        this.enemyTitledPane = new EnemyTitledPane(enemy);
    }

    @Override
    public CGTGameObject getObject() {
        return enemy;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof CGTEnemy)
            this.enemy  = (CGTEnemy) obj;
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
    public String toString() {
        return enemy.getId();
    }

    @Override
    public Node getPane() {
        return this.enemyTitledPane;
    }

    @Override
    public void onCreate() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o inimigo")
                .message("Digite um nome para o inimigo:")
                .showTextInput("Inimigo");

        if (response.isPresent()) {
            String id = response.get().trim();
            this.enemy = new CGTEnemy(id);
        }
    }
}
