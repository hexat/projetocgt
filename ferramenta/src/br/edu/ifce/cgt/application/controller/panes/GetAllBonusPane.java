package br.edu.ifce.cgt.application.controller.panes;

import cgt.game.CGTGameWorld;
import cgt.game.WinCriteria;
import cgt.win.GetAllBonus;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class GetAllBonusPane extends GridPane implements WinCriteriaPane {
    private GetAllBonus getAllBonus;

    public GetAllBonusPane(CGTGameWorld world) {
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(60);
        getColumnConstraints().setAll(c1, c2);

        add(new Label("Mundo:"), 0, 0);

        TextField txtWorld = new TextField();
        txtWorld.setText(world.getId());
        txtWorld.setDisable(true);
        txtWorld.setEditable(false);
        add(txtWorld, 1, 0);

        setGetAllBonus(new GetAllBonus());
    }

    public void setGetAllBonus(GetAllBonus getAllBonus) {
        this.getAllBonus = getAllBonus;
    }

    @Override
    public WinCriteria getCriteria() {
        return getAllBonus;
    }
}
