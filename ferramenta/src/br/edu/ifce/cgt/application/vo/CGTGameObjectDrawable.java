package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.GameObjectPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import cgt.game.CGTSpriteSheet;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public class CGTGameObjectDrawable extends AbstractDrawableObject {

    private CGTGameObject gameObject;
    private GameObjectPane gameObjectTitledPane;
    private String worldName;
    private String gameObjectId;
    private Rectangle bounds;
    private Rectangle collision;
    private ImageView preview;

    public CGTGameObjectDrawable(CGTGameObject gameObject, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.gameObject = gameObject;
        this.gameObject.setId(this.getGameObjectId());
        this.gameObjectTitledPane = new GameObjectPane(this.gameObject, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
        this.bounds = new Rectangle(this.gameObject.getBounds().getWidth(), this.gameObject.getBounds().getHeight());
        this.collision = new Rectangle(this.gameObject.getCollision().getWidth(), this.gameObject.getCollision().getHeight());
    }

    @Override
    public CGTGameObject getObject() {
        return gameObject;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof CGTGameObject)
            this.gameObject = (CGTGameObject) obj;
    }

    @Override
    public Node getPane() {
        return this.gameObjectTitledPane;
    }

    @Override
    public void drawObject() {
        bounds.setWidth(this.gameObject.getBounds().getWidth());
        bounds.setHeight(this.gameObject.getBounds().getHeight());
        bounds.setFill(null);
        bounds.setStroke(Color.RED);
        bounds.setStrokeWidth(0.8);

        collision.setWidth(this.gameObject.getCollision().getWidth());
        collision.setHeight(this.gameObject.getCollision().getHeight());
        collision.setFill(null);
        collision.setStroke(Color.YELLOW);
        collision.setStrokeWidth(0.8);

        if (this.gameObject.getPosition() != null) {
            bounds.setX(this.gameObject.getPosition().x);
            bounds.setY(this.gameObject.getPosition().y);
            collision.setX(this.gameObject.getPosition().x);
            collision.setY(this.gameObject.getPosition().y);
        }

        super.updateDrawPane(bounds);
        super.updateDrawPane(collision);

        int actorWidth = (int) this.gameObject.getBounds().getWidth();
        int actorHeight = (int) this.gameObject.getBounds().getHeight();

        if (!this.gameObject.getAnimations().isEmpty() && actorWidth > 0 && actorHeight > 0) {
            CGTSpriteSheet cgtSpriteSheet = this.gameObject.getAnimations().get(0).getSpriteSheet();
            String urlToFile = cgtSpriteSheet.getTexture().getFile().getFile().getName();
            Image img = Config.get().getImage(urlToFile);
            Image[] images = Config.get().splitImage(img, cgtSpriteSheet.getColumns(), cgtSpriteSheet.getRows(), actorWidth, actorHeight);
            preview = new ImageView(images[images.length / 2]);
            preview.setX(this.gameObject.getPosition().x);
            preview.setY(this.gameObject.getPosition().y);
            super.updateDrawPane(preview);
        }
    }

    @Override
    public void drawConfigurationPanel() {
    }

    @Override
    public void onCreate() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Criar objeto");
        dialog.setHeaderText("Criação de um objeto do jogo");

        ButtonType createButtonType = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Nome do objeto");
        ComboBox<String> worldCombobox = new ComboBox<>();
        List<CGTGameWorld> worlds = Config.get().getGame().getWorlds();
        worlds.stream().forEach(w -> worldCombobox.getItems().add(w.getId()));

        grid.add(new Label("Nome do objeto:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Mundo:"), 0, 1);
        grid.add(worldCombobox, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(createButtonType);
        loginButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> name.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(name.getText(), worldCombobox.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            this.gameObjectId = id;
            this.worldName = worldName;
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public String getGameObjectId() {
        return gameObjectId;
    }

    @Override
    public String toString() {
        return this.gameObjectId;
    }
}
