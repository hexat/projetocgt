package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.hud.CGTLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        txtColor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    font.setColor(txtColor.getValue());
                }
            }
        });

        txtSize.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    font.setSize(txtSize.getValue());
                }
            }
        });

        txtText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    font.setText(txtText.getText());
                }
            }
        });
    }

    public void init() {
        if (font != null) {
            txtText.setText(font.getText());
            txtColor.setValue(font.getColorValue());
            txtSize.setValue(font.getSize());
        }
    }
}
