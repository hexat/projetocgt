package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import cgt.core.CGTActor;
import cgt.core.CGTProjectile;
import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;


public class ActorTitledPane extends TitledPane {

    @FXML
    public ComboBox<String> boxProjectiles;
    @FXML
    public IntegerTextField txtTimeToRecovery;
    @FXML
    private VBox panActions;
    @FXML
    private ComboBox<EnumMap<InputPolicy>> boxInputs;
    @FXML
    private ComboBox<EnumMap<ActionMovePolicy>> boxMoves;

    @FXML
    public void addAction() {
        actor.addAction(boxInputs.getValue().getKey(), boxMoves.getValue().getKey());
        updateActions();
    }

    private void updateActions() {
        panActions.getChildren().clear();
        final Map<InputPolicy, ActionMovePolicy> actions = actor.getActions();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.String", new Locale("pt", "PT"));
        if (actions.size() > 0) {
            for (final InputPolicy input : actions.keySet()) {
                ItemViewPane pane = new ItemViewPane(bundle.getString(input.name()) + " -> " +
                        bundle.getString(actions.get(input).name()));
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
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
        setActions();
    }

    private void setActions() {
        boxProjectiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                List<String> list = new ArrayList<String>();
                for (CGTProjectile p : actor.getProjectiles()) {
                    list.add(p.getId());
                }
                boxProjectiles.getItems().setAll(list);
            }
        });

        boxProjectiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actor.setFireDefault(actor.getProjectiles().indexOf(actor.findProjectile(boxProjectiles.getValue())));
            }
        });
    }

    private void init() {

        List<EnumMap<InputPolicy>> inputs = new ArrayList<EnumMap<InputPolicy>>();

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.String", new Locale("pt", "PT"));
        for (InputPolicy policy : actor.getAvailableInputs()) {
            inputs.add(new EnumMap<InputPolicy>(policy, bundle.getString(policy.name())));
        }

        boxInputs.getItems().setAll(inputs);
        boxInputs.getSelectionModel().selectFirst();

        ArrayList<EnumMap<ActionMovePolicy>> moves = new ArrayList<EnumMap<ActionMovePolicy>>();
        for (ActionMovePolicy policy : actor.getAvailableActionsMove()) {
            moves.add(new EnumMap<ActionMovePolicy>(policy, bundle.getString(policy.name())));
        }
        boxMoves.getItems().addAll(moves);
        boxMoves.getSelectionModel().selectFirst();

        txtTimeToRecovery.setValue(actor.getTimeToRecovery());
        txtTimeToRecovery.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    actor.setTimeToRecovery(txtTimeToRecovery.getValue());
                }
            }
        });
        updateActions();
        if (actor.getProjectileDefault() != null) {
            boxProjectiles.getSelectionModel().select(actor.getProjectileDefault().getId());
        }
    }
}
