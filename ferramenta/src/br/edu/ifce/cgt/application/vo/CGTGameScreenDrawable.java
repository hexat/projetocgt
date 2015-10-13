package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ConfigScreenPreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTScreen;
import javafx.collections.ObservableArray;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

/**
 * Created by Edy Junior on 14/09/2015.
 */
public class CGTGameScreenDrawable extends AbstractDrawableObject {
    private CGTScreen screen;
    private ConfigScreenPreviewPane screenPane;

    public CGTGameScreenDrawable(AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);

        this.screenPane = new ConfigScreenPreviewPane(screen, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public CGTScreen getObject() {
        return screen;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof CGTScreen)
            this.screen = (CGTScreen) obj;
    }

    @Override
    public Node getPane() {
        return this.screenPane;
    }

    @Override
    public void drawObject() {
        if (this.screen.getBackground() != null) {
            String backFilename = this.screen.getBackground().getFile().getFilename();
            ImageView img = new ImageView(Config.get().getImage(backFilename));
            getDrawableObjectPane().getChildren().clear();
            getDrawableObjectPane().getChildren().add(img);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(screenPane);
    }

    @Override
    public void onCreate() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para a tela")
                .message("Digite um nome para a tela:")
                .showTextInput("Tela");

        if (response.isPresent()) {
            String id = response.get().trim();
            this.screen = (Config.get().getGame().createScreen(id));
            //screenPane.getTxtScreenId().setText(id);
        }
    }

    @Override
    public String toString() {
        return this.screen.getId();
    }
}
