package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.EnemyTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class CGTGameEnemyDrawable extends CGTGameObjectDrawable {

    private CGTEnemy enemy;
    private String name;
    private String worldName;
    private EnemyTitledPane enemyTitledPane;

    public CGTGameEnemyDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(new CGTEnemy(), drawableObjectPane, drawableConfigurationsPane);
        this.setObject(super.getObject());
        this.enemy.setId(name);
        this.enemyTitledPane = new EnemyTitledPane(enemy);
    }

    @Override
    public CGTGameObject getObject() {
        return enemy;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof CGTEnemy)
            this.enemy  = (CGTEnemy) obj;
    }

    @Override
    public void drawObject() {
        super.drawObject();
    }

    @Override
    public void drawConfigurationPanel() {
        Pane paneObject = (Pane) super.getPane();
        Accordion accordion = (Accordion) paneObject.getChildren().get(0);
        TitledPane titledPaneEnemy = (TitledPane) this.getPane();
        accordion.getPanes().add(titledPaneEnemy);
        super.updateConfigPane(accordion);
    }

    @Override
    public String toString() {
        return enemy.getId();
    }

    @Override
    public Node getPane() {
        return this.enemyTitledPane;
    }

    @Override
    public void onCreate() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Inimigo");
        dialog.setHeaderText("Criação de um inimigo");

        ButtonType createButtonType = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField enemyName = new TextField();
        enemyName.setPromptText("Nome do inimigo");
        ComboBox<String> worldCombobox = new ComboBox<>();
        List<CGTGameWorld> worlds = Config.get().getGame().getWorlds();
        worlds.stream().forEach(w -> worldCombobox.getItems().add(w.getId()));

        grid.add(new Label("Nome do inimigo:"), 0, 0);
        grid.add(enemyName, 1, 0);
        grid.add(new Label("Mundo:"), 0, 1);
        grid.add(worldCombobox, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(createButtonType);
        loginButton.setDisable(true);

        enemyName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> enemyName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(enemyName.getText(), worldCombobox.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            this.name = id;
            this.worldName = worldName;
        }
    }

    public String getWorldName() {
        return worldName;
    }

}
