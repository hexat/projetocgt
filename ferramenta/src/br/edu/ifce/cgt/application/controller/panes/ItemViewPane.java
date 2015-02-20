package br.edu.ifce.cgt.application.controller.panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by Luan on 16/02/2015.
 */
public class ItemViewPane extends GridPane {
    private Button btnExcluir;

    public ItemViewPane(String text) {
        super();

        add(new Label(text), 1, 1);

        this.btnExcluir = new Button("Excluir");

        add(btnExcluir, 2, 1);
    }

    public Button getBtnExcluir() {
        return btnExcluir;
    }
}
