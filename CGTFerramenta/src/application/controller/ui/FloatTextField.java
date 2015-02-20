package application.controller.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Created by Luan on 20/02/2015.
 */
public class FloatTextField extends TextField {
    public FloatTextField() {
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("-?[0-9]+(\\.[0-9]+)?")) {
                    FloatTextField.this.setText(oldValue);
                }
            }
        });
    }
}
