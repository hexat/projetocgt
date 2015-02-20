package application.controller.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Created by Luan on 20/02/2015.
 */
public class IntegerTextField extends TextField {
    public IntegerTextField() {
        textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    IntegerTextField.this.setText(oldValue);
                }
            }
        });
    }
}
