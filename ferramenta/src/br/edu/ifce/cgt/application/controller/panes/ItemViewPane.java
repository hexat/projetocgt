package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.util.AwesomeIcons;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.io.InputStream;

/**
 * Created by Luan on 16/02/2015.
 */
public class ItemViewPane extends BorderPane {
    private Button btnDelete;

    public ItemViewPane(String text) {
        super();
        InputStream font = this.getClass().getResourceAsStream("/font/fontawesome-webfont.ttf");
        Font fontAwesome = Font.loadFont(font, 12);

        this.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));

        setCenter(new Label(text));

        this.btnDelete = new Button();
        this.btnDelete.setFont(fontAwesome);
        this.btnDelete.setText(AwesomeIcons.TRASH.toString());

        setRight(btnDelete);
    }

    public Button getDeleteButton() {
        return btnDelete;
    }
}
