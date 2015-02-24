package br.edu.ifce.cgt.application.controller.panes;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by Luan on 16/02/2015.
 */
public class ItemViewPane extends GridPane {
    private Button btnExcluir;

    public ItemViewPane(String text) {
        super();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);

        getColumnConstraints().setAll(col1, col2);


        add(new Label(text), 0, 0);

        this.btnExcluir = new Button("Excluir");
        add(btnExcluir, 1, 0);
    }

    public Button getBtnExcluir() {
        return btnExcluir;
    }
}
