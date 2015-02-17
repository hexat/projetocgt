package util.ui;

import cgt.core.CGTAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Created by Luan on 15/02/2015.
 */
public class AnimationViewPane extends GridPane {
    private Button btnExcluir;

    public AnimationViewPane(CGTAnimation animation) {
        super();

        add(new Label(animation.getSpriteSheet().getTexture().getFile().getFilename()), 1, 1);
        add(new Label(animation.getEndingFrame().toString()), 2, 1);
        btnExcluir = new Button("Excluir");
        add(btnExcluir, 3, 1);

        setStyle("-fx-border-width: 2; " +
                "-fx-border-color: black;");
    }

    public Button getBtnExcluir() {
        return btnExcluir;
    }
}
