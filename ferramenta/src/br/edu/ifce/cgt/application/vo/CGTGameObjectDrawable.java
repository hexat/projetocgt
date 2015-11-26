package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.GameObjectPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Draggable;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import cgt.game.CGTSpriteSheet;
import cgt.util.CGTTexture;
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
    private Rectangle bounds;
    private Rectangle collision;
    private Draggable preview = new Draggable();

    public CGTGameObjectDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.preview = new Draggable(gameObjectTitledPane.getPositionX(), gameObjectTitledPane.getPositionY(),
                bounds, collision, gameObjectTitledPane.getColisionX(), gameObjectTitledPane.getColisionY());
    }

    public CGTGameObjectDrawable(T gameObject, String worldName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(gameObject, drawableObjectPane, drawableConfigurationsPane);
        setWorldName(worldName);
        this.preview = new Draggable(gameObjectTitledPane.getPositionX(), gameObjectTitledPane.getPositionY(),
                bounds, collision, gameObjectTitledPane.getColisionX(), gameObjectTitledPane.getColisionY());
    }

    @Override
    public void onStart() {
        gameObjectTitledPane = new GameObjectPane(getObject(), this, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
        if(getObject() != null) {
            this.bounds = new Rectangle(getObject().getBounds().getWidth(), getObject().getBounds().getHeight());
            this.collision = new Rectangle(getObject().getCollision().getWidth(), getObject().getCollision().getHeight());
        }
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
//        bounds.setWidth(getObject().getBounds().getWidth());
//        bounds.setHeight(getObject().getBounds().getHeight());
        bounds.setFill(null);
        bounds.setStroke(Color.RED);
        bounds.setStrokeWidth(0.8);

//        collision.setWidth(getObject().getCollision().getWidth());
//        collision.setHeight(getObject().getCollision().getHeight());
        collision.setFill(null);
        collision.setStroke(Color.YELLOW);
        collision.setStrokeWidth(0.8);

        super.updateDrawPane(bounds);
        super.updateDrawPane(collision);

        int actorWidth = (int) getObject().getBounds().getWidth();
        int actorHeight = (int) getObject().getBounds().getHeight();

        if (!getObject().getAnimations().isEmpty() && actorWidth > 0 && actorHeight > 0) {
            preview.setImage(getObjectPane().getInitialTile().getImage());

            if(getObjectPane().getGameObject().getInitialPositions() != null &&
                    getObjectPane().getGameObject().getInitialPositions().size() > 0 &&
                    getObjectPane().getPositionX().getText().equals("") &&
                    getObjectPane().getPositionY().getText().equals("")) {
                int total = getObjectPane().getGameObject().getInitialPositions().size();
                float x = getObjectPane().getGameObject().getInitialPositions().get(total - 1).x;
                float y = getObjectPane().getGameObject().getInitialPositions().get(total - 1).y;
                preview.setX(x * (preview.getWidthBCKG()/preview.getWI()));
                preview.setY(preview.getHeightBCKG() - ((y) * preview.getHeightBCKG()/preview.getHI()) - preview.getFitHeight());
                //(preview.getHI() - y) * preview.getHeightBCKG()/preview.getHI());// - preview.getFitHeight());
                System.out.printf("%.2f  %.2f\n",preview.getX(),preview.getY());
                collision.setX(x);
                collision.setY(preview.getHeightBCKG() - y - preview.getFitHeight());
            }

            setSizeObject();
            super.updateDrawPane(preview);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(gameObjectTitledPane.getAccordionRoot());
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getWorldName() {
        return worldName;
    }

    public Draggable getDraggable() {
        return this.preview;
    }

    public void setSizeObject() {
        int x = Config.get().getGame().getWorld(getWorldName()).getWidthP();
        int y = Config.get().getGame().getWorld(getWorldName()).getHeightP();
        CGTTexture bkg = Config.get().getGame().getWorld(getWorldName()).getBackground();
        double X = Config.get().getImage(bkg.getFile().getFile().getName()).getWidth();
        double Y = Config.get().getImage(bkg.getFile().getFile().getName()).getHeight();
        if(x == 0 && bkg != null) {
            preview.setWidthBCKG(X);
            preview.setHeightBCKG(Y);
        }
        else {
            preview.setWidthBCKG(x);
            preview.setHeightBCKG(y);
        }
        preview.setWI(X);
        preview.setHI(Y);
        preview.setFitWidth(gameObjectTitledPane.getBoundsW().getValue() * x / X);
        preview.setFitHeight(gameObjectTitledPane.getBoundsH().getValue() * y / Y);
        bounds.setWidth(getObject().getBounds().getWidth() * x / X);
        bounds.setHeight(getObject().getBounds().getHeight() * y / Y);
        collision.setWidth(getObject().getCollision().getWidth() * x / X);
        collision.setHeight(getObject().getCollision().getHeight() * y / Y);
    }

    @Override
    public boolean destroy() {
        return Config.get().getGame().getWorld(worldName).removeObject(getObject());
    }

    public Optional<Pair<String, String>> showGameObjectDialog() {
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
        name.setPromptText("Nome");
        ComboBox<String> worldCombobox = new ComboBox<>();
        List<CGTGameWorld> worlds = Config.get().getGame().getWorlds();
        worlds.stream().forEach(w -> worldCombobox.getItems().add(w.getId()));
        worldCombobox.getSelectionModel().select(0);

        grid.add(new Label("Nome:"), 0, 0);
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

        return dialog.showAndWait();
    }
}