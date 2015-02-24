package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Mapa;
import cgt.core.CGTActor;
import cgt.policy.ActionMovePolicy;
import cgt.policy.InputPolicy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.util.*;

/**
 * Created by Luan on 16/02/2015.
 */
public class ActionDialog extends VBox {

    private final Stage stage;
    private final CGTActor actor;

    @FXML private ComboBox<Mapa<InputPolicy, String>> boxInputs;
    @FXML private ComboBox<Mapa<ActionMovePolicy, String>> boxMoves;
    @FXML private VBox panInputs;

    public ActionDialog(CGTActor actor) {
        this.actor = actor;
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/Action.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());

        List<Mapa<InputPolicy, String>> inputs = new ArrayList<Mapa<InputPolicy, String>>();

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.String", new Locale("pt", "PT"));
        for (InputPolicy policy : actor.getAvailableInputs()) {
            inputs.add(new Mapa<InputPolicy, String>(policy, bundle.getString(policy.name())));
        }

        boxInputs.getItems().setAll(inputs);
        boxInputs.getSelectionModel().selectFirst();

        ArrayList<Mapa<ActionMovePolicy, String>> moves = new ArrayList<Mapa<ActionMovePolicy, String>>();
        for (ActionMovePolicy policy : ActionMovePolicy.values()) {
            moves.add(new Mapa<ActionMovePolicy, String>(policy, bundle.getString(policy.name())));
        }
        boxMoves.getItems().addAll(moves);
        boxMoves.getSelectionModel().selectFirst();
    }

//@FXML public void addInput() {
//        final InputPolicy input = boxInputs.getSelectionModel().getSelectedItem();
//        if (input != null && !actor.hasInput(input) && !entradas.contains(input)) {
//            final ItemViewPane pane = new ItemViewPane(input.toString());
//            pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    entradas.remove(input);
//                    panInputs.getChildren().remove(pane);
//                }
//            });
//            panInputs.getChildren().add(pane);
//        } else {
//            Dialogs.create().message("Você já adicionou esta entrada.").owner(stage).showWarning();
//        }
    //}

    @FXML public void addAction() {
        //if (!entradas.isEmpty() && actor.hasAction(boxMoves.getSelectionModel().getSelectedItem())) {
        if (actor.addAction(boxInputs.getValue().getKey(), boxMoves.getValue().getKey())) {
            stage.getOnCloseRequest().handle(null);
            stage.close();
        } else {
            Dialogs.create().message("Verifique se você adicionou uma entrada ou " +
                    "se este  movimento já foi adicionado à este ator.").owner(stage).showWarning();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }

}
