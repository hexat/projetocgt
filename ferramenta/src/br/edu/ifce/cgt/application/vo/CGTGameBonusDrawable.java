package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.BonusTitledPane;
import cgt.core.CGTBonus;
import cgt.core.CGTGameObject;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CGTGameBonusDrawable extends  CGTGameObjectDrawable {

    private CGTBonus bonus;
    private BonusTitledPane pane;

    public CGTGameBonusDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
    }

    public CGTGameBonusDrawable(CGTGameObject gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, worldName, drawableObjectPane, drawableConfigurationsPane);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.bonus = (CGTBonus) super.getObject();
        this.pane = new BonusTitledPane(this.bonus);
    }

    @Override
    public void drawObject() {
        super.drawObject();
    }

    @Override
    public void drawConfigurationPanel() {
        Pane paneObject = (Pane) super.getPane();
        Accordion accordion = (Accordion) paneObject.getChildren().get(0);
        TitledPane titledPaneActor = (TitledPane) this.getPane();
        accordion.getPanes().add(titledPaneActor);
        super.updateConfigPane(accordion);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public Node getPane() {
        return this.pane;
    }
}
