package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.BonusTitledPane;
import cgt.core.CGTBonus;
import cgt.core.CGTGameObject;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Optional;

public class CGTGameBonusDrawable extends  CGTGameObjectDrawable {

    private CGTBonus bonus;
    private BonusTitledPane pane;

    public CGTGameBonusDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(this.pane);
    }

    public CGTGameBonusDrawable(CGTGameObject gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, worldName, drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(this.pane);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.bonus = (CGTBonus) super.getObject();
        this.pane = new BonusTitledPane(this.bonus);
    }

    @Override
    public void onCreate() {
        Optional<Pair<String, String>> result = showGameObjectDialog();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            setObject(new CGTBonus(id));
            setWorldName(worldName);
        }
    }

    @Override
    public Node getPane() {
        return this.pane;
    }

    public CGTBonus getBonus(){ return  this.bonus; }

    @Override
    public String toString() {
        return getBonus().getId() + " (Bônus)";
    }
}
