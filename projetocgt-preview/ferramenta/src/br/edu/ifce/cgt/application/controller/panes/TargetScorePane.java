package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.game.WinCriteria;
import cgt.win.TargetScore;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class TargetScorePane extends GridPane implements WinCriteriaPane {

    private final IntegerTextField txtScore;
    private TargetScore targetScore;

    public TargetScorePane() {
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(60);
        getColumnConstraints().setAll(c1, c2);

        add(new Label("Score alvo:"), 0, 0);

        txtScore = new IntegerTextField();
        add(txtScore, 1, 0);
        setTargetScore(new TargetScore());
    }

    public void setTargetScore(TargetScore targetScore) {
        this.targetScore = targetScore;
    }

    @Override
    public WinCriteria getCriteria() {
        targetScore.setScore(txtScore.getValue());
        return targetScore;
    }
}
