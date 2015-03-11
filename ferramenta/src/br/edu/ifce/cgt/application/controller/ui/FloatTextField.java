package br.edu.ifce.cgt.application.controller.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Created by Luan on 20/02/2015.
 */
public class FloatTextField extends TextField {
    private boolean isMaxMin;
    private float min;
    private float max;

    public FloatTextField() {
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("")) {
                    try {
                        Float.parseFloat(getText());
                    } catch (Exception e) {
                        setText(oldValue);
                    }
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

    public void setMaxMin(float min, float max) {
        isMaxMin = true;
        this.min = min;
        this.max = max;
    }

    public void setMax(float max) {
        isMaxMin = true;
        this.max = max;
    }

    public void setMin(float min) {
        isMaxMin = true;
        this.min = min;
    }

    public float getValue() {
        if (getText().equals("")) return 0;

        return Float.parseFloat(getText());
    }


    public void enableError() {
        setStyle(
                "  -fx-text-box-border: red ;" +
                        "  -fx-focus-color: red ;"
        );
    }

    public void disableError() {
        setStyle(null);
    }

    public void setValue(float value) {
        setText(value+"");
    }

    public void setValue(double value) {
        setText(value+"");
    }
}
