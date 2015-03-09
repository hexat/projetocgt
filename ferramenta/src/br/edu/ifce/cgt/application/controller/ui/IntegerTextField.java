package br.edu.ifce.cgt.application.controller.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.text.ParseException;

/**
 * Created by Luan on 20/02/2015.
 */
public class IntegerTextField extends TextField {
    private boolean isMaxMin = false;
    private int max;
    private int min;

    public IntegerTextField() {
        textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d*")) {
//                    try {
//                        Integer.parseInt(newValue);
//                    } catch (Exception e) {}
                } else {
                    setText(oldValue);
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (isMaxMin && !getText().equals("")) {
                    if (getValue() < min) {
                        setText(min+"");
                    }
                    if (getValue() > max) {
                        setText(max+"");
                    }
                }
            }
        });
    }

    public void setMaxMin(int min, int max) {
        isMaxMin = true;
        this.max = max;
        this.min = min;
    }

    public int getValue() {
        if (getText().equals("")) return 0;

        return Integer.parseInt(getText());
    }


    public void enableError() {
        setStyle(
                "  -fx-text-box-border: red ;" +
                "  -fx-focus-color: red ;"
        );
    }

    public void setValue(float value) {
        setValue(((int)value));
    }

    public void setValue(int value) {
        setText((value)+"");
    }

    public void disableError() {
        setStyle(null);
    }
}
