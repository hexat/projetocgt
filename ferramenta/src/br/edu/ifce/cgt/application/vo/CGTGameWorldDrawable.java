package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ConfigWorldPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTGameWorld;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

public class CGTGameWorldDrawable extends AbstractDrawableObject {

    private CGTGameWorld cgtGameWorld;

    public CGTGameWorldDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
    }

    @Override
    public Object getObject() {
        return cgtGameWorld;
    }

    @Override
    public void drawObject() {
        if (this.cgtGameWorld.getBackground() != null) {
            String backFilename = this.cgtGameWorld.getBackground().getFile().getFilename();
            ImageView img = new ImageView(Config.get().getImage(backFilename));
            getDrawableObjectPane().getChildren().add(img);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        ConfigWorldPane pane = new ConfigWorldPane(cgtGameWorld, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });

        super.addConfigurationPane(pane);
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
            this.cgtGameWorld = Config.get().getGame().createWorld(id);
        }
    }

    @Override
    public String toString() {
        return this.cgtGameWorld.getId();
    }
}
