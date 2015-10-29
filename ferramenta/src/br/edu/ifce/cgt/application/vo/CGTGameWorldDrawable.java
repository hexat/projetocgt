package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ConfigWorldPreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTGameWorld;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class CGTGameWorldDrawable extends AbstractDrawableObject<CGTGameWorld> {
    private ConfigWorldPreviewPane worldPane;

    public CGTGameWorldDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
    }

    public CGTGameWorldDrawable(CGTGameWorld object, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(object, drawableObjectPane, drawableConfigurationsPane);
    }

    @Override
    public void onStart() {
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
            ImageView img = new ImageView(Config.get().getImage(getObject().getBackground().getFile()));
            getDrawableObjectPane().getChildren().add(img);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(worldPane);
    }

    @Override
    public void onCreate() {
        TextInputDialog dialog = new TextInputDialog("Mundo");
        dialog.setTitle("Nome para o mundo");
        dialog.setContentText("Digite um nome para o mundo:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String id = result.get().trim();
            setObject(Config.get().getGame().createWorld(id));
        }
    }

    @Override
    public String toString() {
        return getObject().getId();
    }

    @Override
    public boolean destroy() {
        return Config.get().getGame().removeWorld(getObject());
    }
}
