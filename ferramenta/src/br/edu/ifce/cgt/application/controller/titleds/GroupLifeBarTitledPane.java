package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.panes.LifebarPane;
import cgt.hud.EnemyGroupLifeBar;
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
public class GroupLifeBarTitledPane extends TitledPane {
    private final LifebarPane lifeBarPane;
    private final EnemyGroupLifeBar enemyGroupLifeBar;
    private final ComboBox<String> boxLabelObjects;
    private final VBox panEnemies;

    public GroupLifeBarTitledPane(final EnemyGroupLifeBar enemyGroupLifeBar) {
        this.enemyGroupLifeBar = enemyGroupLifeBar;

        setText("Configurações LifeBar de Inimigos");
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
        boxLabelObjects.getItems().addAll(enemyGroupLifeBar.getWorld().getEnemyLabels());

        panEnemies = new VBox();

        boxLabelObjects.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (String label : enemyGroupLifeBar.getWorld().getEnemyLabels()) {
                    if (!boxLabelObjects.getItems().contains(label) && !enemyGroupLifeBar.contains(label)) {
                        boxLabelObjects.getItems().add(label);
                    }
                }
            }
        });

        boxLabelObjects.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final String id = boxLabelObjects.getSelectionModel().getSelectedItem();
                if (enemyGroupLifeBar.contains(id)) {
                    enemyGroupLifeBar.addEnemy(id);
                    final ItemViewPane pane = new ItemViewPane(id);
                    pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            enemyGroupLifeBar.removeEnemy(id);
                            panEnemies.getChildren().remove(pane);
                        }
                    });
                    panEnemies.getChildren().add(pane);
                }
            }
        });

        gridPane.add(boxLabelObjects, 1, 0);
        layout.getChildren().add(gridPane);
        layout.getChildren().add(panEnemies);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(5, 0, 5, 0));
        layout.getChildren().add(separator);
        layout.setPadding(new Insets(5));

        lifeBarPane = new LifebarPane(enemyGroupLifeBar);
        layout.getChildren().add(lifeBarPane);
        ScrollPane scroll = new ScrollPane(layout);
        scroll.setFitToWidth(true);
        setContent(scroll);
    }
}
