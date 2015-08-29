package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ConfigWorldPreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTGameWorld;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

public class CGTGameWorldDrawable extends AbstractDrawableObject {

    private CGTGameWorld gameWorld;
    private ConfigWorldPreviewPane worldPane;

    public CGTGameWorldDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);

        this.worldPane = new ConfigWorldPreviewPane(gameWorld, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public CGTGameWorld getObject() {
        return gameWorld;
    }

    @Override
    public Node getPane() {
        return this.worldPane;
    }

    @Override
    public void drawObject() {
        if (this.gameWorld.getBackground() != null) {
            String backFilename = this.gameWorld.getBackground().getFile().getFilename();
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
            this.gameWorld = Config.get().getGame().createWorld(id);
        }
    }

    @Override
    public String toString() {
        return this.gameWorld.getId();
    }
}
