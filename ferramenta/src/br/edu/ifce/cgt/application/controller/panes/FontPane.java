package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.hud.CGTLabel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 22/02/2015.
 */
public class FontPane extends GridPane {
    private CGTLabel font;
    public TextField txtText;
    public IntegerTextField txtSize;
    public IntegerTextField txtColor;

    public FontPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigFont.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtColor.setMaxMin(0, 225);

        setActions();
    }

    public void setFont(CGTLabel font) {
        this.font = font;
        init();
    }

    public CGTLabel getFont() {
        return font;
    }

    private void setActions() {
        txtColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                font.setColor(txtColor.getValue());
            }
        });

        txtSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                font.setSize(txtSize.getValue());
            }
        });

        txtText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                font.setText(txtText.getText());
            }
        });
    }

    public void commit() {
        font.setSize(txtSize.getValue());
        font.setText(txtText.getText());
        font.setColor(txtColor.getValue());
    }

    public void init() {
        if (font != null) {
            txtText.setText(font.getText());
            txtColor.setText(font.getColorValue()+"");
            txtSize.setText(font.getSize()+"");
        }
    }
}
