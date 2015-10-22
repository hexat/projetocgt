package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.util.AwesomeIcons;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.InputStream;

/**
 * Created by Luan on 16/02/2015.
 */
public class ItemEditPane extends BorderPane {
    private Button btnEdit;
    private Button btnExcluir;

    public ItemEditPane(String text) {
        super();
        InputStream font = this.getClass().getResourceAsStream("/font/fontawesome-webfont.ttf");
        Font fontAwesome = Font.loadFont(font, 12);

        setCenter(new Label(text));

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(5.0,5.0,5.0,5.0));

        btnEdit = new Button();
        btnEdit.setFont(fontAwesome);
        btnEdit.setText(AwesomeIcons.ICON_EDIT);

        btnExcluir = new Button();
        btnExcluir.setFont(fontAwesome);
        btnExcluir.setText(AwesomeIcons.ICON_REMOVE);

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
