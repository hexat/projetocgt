package br.edu.ifce.cgt.application.util;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by Luan on 15/02/2015.
 */
public class VectorPane extends GridPane {
    private TextField x;
    private TextField y;
    public VectorPane() {
        super();
        x = new TextField();
        y = new TextField();
        add(x, 1, 1);
        add(y, 2, 1);
    }
}
