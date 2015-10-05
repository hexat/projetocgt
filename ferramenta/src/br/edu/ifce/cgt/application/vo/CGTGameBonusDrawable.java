package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.BonusTitledPane;
import cgt.core.CGTBonus;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class CGTGameBonusDrawable extends  CGTGameObjectDrawable{

    private CGTBonus bonus;
    private BonusTitledPane pane;

    public CGTGameBonusDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(new CGTBonus(), drawableObjectPane, drawableConfigurationsPane);
        this.bonus = (CGTBonus) super.getObject();
        this.pane = new BonusTitledPane(this.bonus);
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
    public Node getPane() {
        return this.pane;
    }
}
