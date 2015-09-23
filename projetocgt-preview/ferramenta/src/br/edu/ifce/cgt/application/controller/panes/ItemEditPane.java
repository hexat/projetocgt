package br.edu.ifce.cgt.application.controller.panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by Luan on 16/02/2015.
 */
public class ItemEditPane extends BorderPane {
    private final Button btnEdit;
    private Button btnExcluir;

    public ItemEditPane(String text) {
        super();


        setCenter(new Label(text));

        HBox buttons = new HBox();

        btnEdit = new Button("Editar");
        btnExcluir = new Button("Excluir");

        buttons.getChildren().setAll(btnEdit, btnExcluir);
        setRight(buttons);
    }

    public Button getDeleteButton() {
        return btnExcluir;
    }

    public Button getEditButton() {
        return btnEdit;
    }
}
