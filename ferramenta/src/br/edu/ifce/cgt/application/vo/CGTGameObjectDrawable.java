package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.GameObjectTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTGameObject;
import cgt.game.CGTSpriteSheet;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CGTGameObjectDrawable extends AbstractDrawableObject {

    private CGTGameObject gameObject;
    private GameObjectTitledPane gameObjectTitledPane;
    private Rectangle bounds;
    private Rectangle collision;
    private ImageView preview;

    public CGTGameObjectDrawable(CGTGameObject gameObject, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.gameObject = gameObject;
        this.gameObjectTitledPane = new GameObjectTitledPane(this.gameObject, new Runnable() {
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
    public void drawConfigurationPanel() {}

    @Override
    public void onCreate() {}

    @Override
    public String toString() {
        return gameObject.getId();
    }
}
