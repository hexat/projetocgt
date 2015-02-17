package cgt.controller;

import cgt.controller.dialogs.ActionDialog;
import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;
import cgt.unit.Action;
import com.sun.javafx.collections.SetListenerHelper;

import java.io.IOException;
import java.util.Map;

import application.Main;
import cgt.core.CGTActor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import util.ui.ItemViewPane;

public class ConfigPersonagemController {

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
		if (actions.size() > 0 ) {
			for (final InputPolicy input : actions.keySet()) {
				ItemViewPane pane = new ItemViewPane(input.toString() + " -> " + actions.get(input).toString());
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

    public static TitledPane getNode(CGTActor object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigPersonagem.fxml"));
        TitledPane el = null;
        try {
            el = xml.load();
            ConfigPersonagemController controller = xml.getController();
            controller.setActor(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return el;
    }
}
