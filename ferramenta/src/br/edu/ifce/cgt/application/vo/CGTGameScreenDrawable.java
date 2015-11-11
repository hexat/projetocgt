package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigScreenPreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTScreen;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Optional;

/**
 * Created by Edy Junior on 14/09/2015.
 */
public class CGTGameScreenDrawable extends AbstractDrawableObject<CGTScreen> {
    private ConfigScreenPreviewPane screenPane;

    public CGTGameScreenDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
    }

    public CGTGameScreenDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane, double h, double w) {
        super(drawableObjectPane, drawableConfigurationsPane);
        screenPane.getScreen().setHeightAndWidth(h, w);
    }

    public CGTGameScreenDrawable(CGTScreen object, Pane drawableObjectPane, Pane drawableConfigurationsPane, double h, double w) {
        super(object, drawableObjectPane, drawableConfigurationsPane);
        screenPane.getScreen().setHeightAndWidth(h, w);
    }

    public CGTGameScreenDrawable(CGTScreen object, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(object, drawableObjectPane, drawableConfigurationsPane);
    }

    @Override
    public void onStart() {
        this.screenPane = new ConfigScreenPreviewPane(getObject(), new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public Node getPane() {
        return this.screenPane;
    }

    @Override
    public void drawObject() {
        if (getObject().getBackground() != null) {
            ImageView img = new ImageView(Config.get().getImage(getObject().getBackground().getFile()));
            if(screenPane.getHeight() != 0 && screenPane.getWidth() != 0) {
                img.setFitHeight(screenPane.getScreen().getHeight());
                img.setFitWidth(screenPane.getScreen().getWidth());
            }
            super.updateDrawPane(img);
        }
    }

    @Override
    public void drawConfigurationPanel() {
        super.updateConfigPane(screenPane);
    }

    @Override
    public void onCreate() {
        TextInputDialog dialog = new TextInputDialog("Tela");
        dialog.setTitle("Nome para a tela");
        dialog.setContentText("Digite um nome para a tela:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String id = result.get().trim();
            setObject(Config.get().getGame().createScreen(id));
            //screenPane.getTxtScreenId().setText(id);
        }
    }

    @Override
    public String toString() {
        return getObject().getId();
    }

    @Override
    public boolean destroy() {
        return Config.get().getGame().removeScreen(getObject());
    }
}
