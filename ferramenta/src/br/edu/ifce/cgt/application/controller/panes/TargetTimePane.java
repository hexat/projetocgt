package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.game.LoseCriteria;
import cgt.lose.TargetTime;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class TargetTimePane extends GridPane implements LoseCriteriaPane {
    private final IntegerTextField txtTime;
    private TargetTime targetTime;

    public TargetTimePane() {
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(60);
        getColumnConstraints().setAll(c1, c2);

        add(new Label("Tempo"), 0, 0);
        txtTime = new IntegerTextField();
        add(txtTime, 1, 0);
        setTargetTime(new TargetTime());

        txtTime.setValue(targetTime.getTimer());
    }

    public void setTargetTime(TargetTime targetTime) {
        this.targetTime = targetTime;
    }

    @Override
    public LoseCriteria getCriteria() {
        targetTime.setTimer(txtTime.getValue());
        return targetTime;
    }
}
