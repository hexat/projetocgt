package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ConfigWorldPreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

public class CGTGameWorldDrawable extends AbstractDrawableObject<CGTGameWorld> {
    private ConfigWorldPreviewPane worldPane;

    public CGTGameWorldDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);

        init();
    }

    public CGTGameWorldDrawable(CGTGameWorld object, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(object, drawableObjectPane, drawableConfigurationsPane);

        init();
    }

    public void init() {
        this.worldPane = new ConfigWorldPreviewPane(getObject(), new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public Node getPane() {
        return this.worldPane;
    }

    @Override
    public void drawObject() {
        if (getObject().getBackground() != null) {
            String backFilename = getObject().getBackground().getFile().getFilename();
            ImageView img = new ImageView(Config.get().getImage(backFilename));
            getDrawableObjectPane().getChildren().add(img);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(worldPane);
    }

    @Override
    public void onCreate() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o mundo")
                .message("Digite um nome para o mundo:")
                .showTextInput("Mundo");

        if (response.isPresent()) {
            String id = response.get().trim();
            setObject(Config.get().getGame().createWorld(id));
        }
    }

    @Override
    public String toString() {
        return getObject().getId();
    }
}
