package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.game.WinCriteria;
import cgt.win.CompleteCrossing;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class CompleteCrossingPane extends GridPane implements WinCriteriaPane {

    private final IntegerTextField txtX;
    private final IntegerTextField txtY;
    private final IntegerTextField txtWidth;
    private final IntegerTextField txtHeight;

    private CompleteCrossing completeCrossing;

    public CompleteCrossingPane() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(40);

        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(60);

        getColumnConstraints().setAll(cc, cc2);

        add(new Label("X"), 0, 0);
        txtX = new IntegerTextField();
        add(txtX, 1, 0);

        add(new Label("Y"), 0, 1);
        txtY = new IntegerTextField();
        add(txtY, 1, 1);

        add(new Label("Largura"), 0, 2);
        txtWidth = new IntegerTextField();
        add(txtWidth, 1, 2);

        add(new Label("Altura"), 0, 3);
        txtHeight = new IntegerTextField();
        add(txtHeight, 1, 3);

        setCompleteCrossing(new CompleteCrossing());
    }

    public void setCompleteCrossing(CompleteCrossing completeCrossing) {
        this.completeCrossing = completeCrossing;
    }

    @Override
    public WinCriteria getCriteria() {
        completeCrossing.getRectangle().x = txtX.getValue();
        completeCrossing.getRectangle().y = txtY.getValue();
        completeCrossing.getRectangle().width = txtWidth.getValue();
        completeCrossing.getRectangle().height = txtHeight.getValue();

        return completeCrossing;
    }
}
