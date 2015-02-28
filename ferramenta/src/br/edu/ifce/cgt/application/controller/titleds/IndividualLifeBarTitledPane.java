package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.controller.panes.LifebarPane;
import cgt.hud.IndividualLifeBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Created by Luan on 21/02/2015.
 */
public class IndividualLifeBarTitledPane extends TitledPane {
    private final LifebarPane lifeBarPane;
    private final IndividualLifeBar individualLifeBar;
    private final ComboBox<String> boxLabelObjects;

    public IndividualLifeBarTitledPane(final IndividualLifeBar individualLifeBar) {
        this.individualLifeBar = individualLifeBar;
        VBox layout = new VBox();

        GridPane gridPane = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        gridPane.getColumnConstraints().addAll(col1,col2,col3);

        Label label = new Label("Objeto");
        gridPane.add(label, 0, 0);

        boxLabelObjects = new ComboBox<String>();
        boxLabelObjects.getItems().addAll(individualLifeBar.getWorld().getObjectIds());

        if (individualLifeBar.getOwnerId() != null) {
            boxLabelObjects.getSelectionModel().select(individualLifeBar.getOwnerId());
        }

        boxLabelObjects.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (String label : individualLifeBar.getWorld().getObjectIds()) {
                    if (!boxLabelObjects.getItems().contains(label)) {
                        boxLabelObjects.getItems().add(label);
                    }
                }
            }
        });

        boxLabelObjects.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                individualLifeBar.setOwner(boxLabelObjects.getValue());
            }
        });

        gridPane.add(boxLabelObjects, 1, 0);
        layout.getChildren().add(gridPane);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(5, 0, 5, 0));
        layout.getChildren().add(separator);
        layout.setPadding(new Insets(5));

        lifeBarPane = new LifebarPane(individualLifeBar);
        layout.getChildren().add(lifeBarPane);
        ScrollPane scroll = new ScrollPane(layout);
        scroll.setFitToWidth(true);
        setContent(scroll);
    }
}
