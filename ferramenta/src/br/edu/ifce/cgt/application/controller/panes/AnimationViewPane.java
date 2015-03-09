package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.dialogs.AnimationDialog;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.core.CGTAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

/**
 * Created by Luan on 15/02/2015.
 */
public class AnimationViewPane extends GridPane {
    private final CGTAnimation animation;
    private Button btnExcluir;

    public AnimationViewPane(final CGTAnimation animation) {
        this.animation = animation;

        ResourceBundle bundle = Pref.load().getBundle();
        add(new Label(bundle.getString(animation.getActorStage().name())), 0, 0);
        add(new Label(animation.getInitialFrame().toString() + " - " + animation.getEndingFrame().toString()), 1, 0);

        Button btnEditar = new Button("Editar");
        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnimationDialog dialog = new AnimationDialog(animation);
                dialog.showAndWait();
            }
        });

        add(btnEditar, 2, 0);
        btnExcluir = new Button("Excluir");
        add(btnExcluir, 3, 0);

        setStyle("-fx-border-width: 2; " +
                "-fx-border-color: black;");
    }

    public Button getBtnExcluir() {
        return btnExcluir;
    }
}
