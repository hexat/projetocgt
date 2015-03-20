package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.game.LoseCriteria;
import cgt.hud.CGTLabel;
import cgt.lose.TargetTime;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Created by luanjames on 09/03/15.
 */
public class TargetTimePane extends VBox implements LoseCriteriaPane {
    private final IntegerTextField txtTime;
    private TargetTime targetTime;

    public TargetTimePane() {
        GridPane grid = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(60);
        grid.getColumnConstraints().setAll(c1, c2);

        grid.add(new Label("Tempo"), 0, 0);
        txtTime = new IntegerTextField();
        grid.add(txtTime, 1, 0);

        targetTime = new TargetTime();
        txtTime.setValue(targetTime.getTimer());

        getChildren().add(grid);
        FontPane pane = new FontPane();
        pane.setFont(targetTime.getLabel());
        HudPane hudPane = new HudPane();
        hudPane.setHud(targetTime.getLabel());
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(pane);
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(hudPane);
    }

    @Override
    public LoseCriteria getCriteria() {
        targetTime.setTimer(txtTime.getValue());
        return targetTime;
    }
}
