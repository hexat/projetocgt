package br.edu.ifce.cgt.application.controller.ui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * Created by jrocha on 14/10/2015.
 */
public class SpritePicker extends ComboBox<Pair<String, Image>> {


    public SpritePicker(Image spritesheet, int rows, int columns) {

        this.setCellFactory(new Callback<ListView<Pair<String, Image>>, ListCell<Pair<String, Image>>>() {
            @Override
            public ListCell<Pair<String, Image>> call(ListView<Pair<String, Image>> param) {
                return new ListCell<Pair<String, Image>>() {

                    @Override
                    public void updateIndex(int i) {

                    }
                };
            }
        });
    }
}
