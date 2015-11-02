package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.GameObjectPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Draggable;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import cgt.game.CGTSpriteSheet;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public abstract class CGTGameObjectDrawable<T extends CGTGameObject> extends AbstractDrawableObject<T> {
    private GameObjectPane gameObjectTitledPane;
    private String worldName;
    private String gameObjectId;
    private Rectangle bounds;
    private Rectangle collision;
    private Draggable preview = new Draggable();

    public CGTGameObjectDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.preview = new Draggable(gameObjectTitledPane.getPositionX(), gameObjectTitledPane.getPositionY(), bounds, collision);
    }

    public CGTGameObjectDrawable(T gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, drawableObjectPane, drawableConfigurationsPane);
        setWorldName(worldName);
        this.preview = new Draggable(gameObjectTitledPane.getPositionX(), gameObjectTitledPane.getPositionY(), bounds, collision);
    }

    @Override
    public void onStart() {
        gameObjectTitledPane = new GameObjectPane(getObject(), this, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
        this.bounds = new Rectangle(getObject().getBounds().getWidth(), getObject().getBounds().getHeight());
        this.collision = new Rectangle(getObject().getCollision().getWidth(), getObject().getCollision().getHeight());
    }

    @Override
    public Node getPane() {
        return gameObjectTitledPane;
    }

    public GameObjectPane getObjectPane(){
        return gameObjectTitledPane;
    }

    @Override
    public void drawObject() {
        bounds.setWidth(getObject().getBounds().getWidth());
        bounds.setHeight(getObject().getBounds().getHeight());
        bounds.setFill(null);
        bounds.setStroke(Color.RED);
        bounds.setStrokeWidth(0.8);

        collision.setWidth(getObject().getCollision().getWidth());
        collision.setHeight(getObject().getCollision().getHeight());
        collision.setFill(null);
        collision.setStroke(Color.YELLOW);
        collision.setStrokeWidth(0.8);

        super.updateDrawPane(bounds);
        super.updateDrawPane(collision);

        int actorWidth = (int) getObject().getBounds().getWidth();
        int actorHeight = (int) getObject().getBounds().getHeight();

        if (!getObject().getAnimations().isEmpty() && actorWidth > 0 && actorHeight > 0) {
            CGTSpriteSheet cgtSpriteSheet = getObject().getAnimations().get(0).getSpriteSheet();
            String urlToFile = cgtSpriteSheet.getTexture().getFile().getFile().getName();
            Image img = Config.get().getImage(urlToFile);
            Image[] images = Config.get().splitImage(img, cgtSpriteSheet.getColumns(), cgtSpriteSheet.getRows(), actorWidth, actorHeight);
            preview.setImage(images[images.length / 2]);
            if(getObjectPane().getGameObject().getInitialPositions() != null &&
                    getObjectPane().getPositionX().getText().equals("") &&
                    getObjectPane().getPositionY().getText().equals("")) {
                float x = getObjectPane().getGameObject().getInitialPositions().get(0).x;
                float y = getObjectPane().getGameObject().getInitialPositions().get(0).y;
                preview.setX(x);
                preview.setY(preview.getHeightBCKG() - y);
            }
            setSizeObject();
            super.updateDrawPane(preview);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(gameObjectTitledPane.getAccordionRoot());
    }

    @Override
    public abstract void onCreate();

    public String showInputIdDialog() {
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
        worldCombobox.getSelectionModel().select(0);

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
            String objName = name.getText();
            String worldName = worldCombobox.getSelectionModel().getSelectedItem();

            if (dialogButton == createButtonType && !"".equals(objName) && !"".equals(worldName)) {
                return new Pair<>(objName, worldName);
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            worldName = result.get().getValue();
            return result.get().getKey();
        }
        return null;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getGameObjectId() {
        return gameObjectId;
    }

    public Draggable getDraggable() {
        return this.preview;
    }

    @Override
    public String toString() {
        return getObject().getId();
    }

    public void setSizeObject() {
        preview.setWidthBCKG(
                Config.get().getImage(Config.get().getGame().getWorld(getWorldName()).
                        getBackground().getFile()).getWidth()
        );
        preview.setHeightBCKG(
                Config.get().getImage(Config.get().getGame().getWorld(getWorldName()).
                        getBackground().getFile()).getHeight()
        );
        preview.setFitWidth(gameObjectTitledPane.getBoundsW().getValue());
        preview.setFitHeight(gameObjectTitledPane.getBoundsH().getValue());
    }

    @Override
    public boolean destroy() {
        return Config.get().getGame().getWorld(worldName).removeObject(getObject());
    }
}