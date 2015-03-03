package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.controller.dialogs.ActionDialog;
import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import cgt.core.CGTActor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;

public class ActorTitledPane extends TitledPane {

	@FXML private VBox panActions;

	@FXML public void addAction() {
		ActionDialog dialog = new ActionDialog(actor);
		dialog.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				updateActions();
			}
		});
		dialog.show();
	}

	private void updateActions() {
		panActions.getChildren().clear();
		final Map<InputPolicy, ActionMovePolicy> actions = actor.getActions();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.String", new Locale("pt", "PT"));
		if (actions.size() > 0 ) {
			for (final InputPolicy input : actions.keySet()) {
				ItemViewPane pane = new ItemViewPane(bundle.getString(input.name()) + " -> " +
                        bundle.getString(actions.get(input).name()));
				pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						actor.removeInputFromAction(input);
						updateActions();
					}
				});
				panActions.getChildren().add(pane);
			}
		} else {
			panActions.getChildren().add(new Label("Vazio"));
		}
	}

	private CGTActor actor;

    public void setActor(CGTActor actor) {
        this.actor = actor;
    }

    public CGTActor getActor() {
        return actor;
    }

    public ActorTitledPane(CGTActor object) {
        this.actor = object;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigPersonagem.fxml"));
        xml.setRoot(this);
        xml.setController(this);
        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        updateActions();
    }
}
