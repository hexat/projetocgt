package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.policy.StatePolicy;
import cgt.util.ProjectileOrientation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by infolev on 23/02/15.
 */
public class ProjectileOrientationPane extends VBox {
    public VBox panStates;
    public IntegerTextField txtPosRelX;
    public IntegerTextField txtPosRelY;

    private ProjectileOrientation orientation;

    private final Stage stage;
    public ComboBox<StatePolicy> boxStates;
    private boolean confirmed = false;

    public ProjectileOrientationPane(ProjectileOrientation orientation) {
        this.orientation = orientation;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/panes/ProjectileOrientation.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());

        boxStates.getItems().setAll(StatePolicy.values());
    }

    public void addState() {
        if (!boxStates.getSelectionModel().isEmpty()) {
            orientation.addState(boxStates.getValue());
            addStateOnPanel(boxStates.getValue());
        }
    }

    private void addStateOnPanel(final StatePolicy state) {
        ItemViewPane pane = new ItemViewPane(state.name());
        pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orientation.getStates().remove(state);
                updateStates();
            }
        });
        panStates.getChildren().add(pane);
        stage.sizeToScene();
    }

    private void updateStates() {
        boxStates.getItems().clear();
        for (StatePolicy s : orientation.getStates()) {
            addStateOnPanel(s);
        }
    }

    public void confirm() {
        confirmed = true;
        orientation.getPositionRelativeToGameObject().set(txtPosRelX.getValue(), txtPosRelY.getValue());
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
