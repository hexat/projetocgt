package br.edu.ifce.cgt.application.controller.panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Created by Luan on 16/02/2015.
 */
public class ItemViewPane extends BorderPane {
    private Button btnDelete;

    public ItemViewPane(String text) {
        super();

        setCenter(new Label(text));
        this.btnDelete = new Button("Excluir");
        setRight(btnDelete);
    }

    public Button getDeleteButton() {
        return btnDelete;
    }
}
