package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.game.WinCriteria;
import cgt.win.Survive;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class SurvivePane extends GridPane implements WinCriteriaPane {
    private final IntegerTextField txtTime;

    private Survive survive;

    public SurvivePane() {
        add(new Label("Tempo:"), 0, 0);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(40);

        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(60);


        getColumnConstraints().setAll(cc, cc2);

        txtTime = new IntegerTextField();
        add(txtTime, 1, 0);

        setSurvive(new Survive());
    }

    public void setSurvive(Survive survive) {
        this.survive = survive;
    }

    @Override
    public WinCriteria getCriteria() {
        survive.setTimer(txtTime.getValue());
        return survive;
    }
}
