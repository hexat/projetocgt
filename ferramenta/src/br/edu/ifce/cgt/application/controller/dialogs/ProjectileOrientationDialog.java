package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import cgt.policy.StatePolicy;
import cgt.util.ProjectileOrientation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infolev on 23/02/15.
 */
public class ProjectileOrientationDialog extends VBox {
    @FXML public Button btnConfirm;
    private List<EnumMap<StatePolicy>> listAuxStates;
    public VBox panStates;
    public IntegerTextField txtPosRelX;
    public IntegerTextField txtPosRelY;

    private ProjectileOrientation orientation;

    private final Stage stage;
    public ComboBox<EnumMap<StatePolicy>> boxStates;

    public ProjectileOrientationDialog() {
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

        listAuxStates = new ArrayList<EnumMap<StatePolicy>>();
        boxStates.getItems().setAll(AnimationDialog.getListActorStates());

        orientation = null;
        btnConfirm.setDisable(true);
    }

    public void setOrientation(ProjectileOrientation orientation) {
        this.orientation = orientation;
        btnConfirm.setText("Editar");
        boolean seek;
        for (StatePolicy p : orientation.getStates()) {
            seek = false;
            for (int i = 0; i < boxStates.getItems().size() && !seek; i++) {
                if (boxStates.getItems().get(i).getKey() == p) {
                    listAuxStates.add(boxStates.getItems().remove(i));
                    seek = true;
                }
            }
        }

        updateStates();
        txtPosRelX.setValue(orientation.getPositionRelativeToGameObject().x);
        txtPosRelY.setValue(orientation.getPositionRelativeToGameObject().y);
    }

    public void addState() {
        if (!boxStates.getSelectionModel().isEmpty() &&
                !listAuxStates.contains(boxStates.getSelectionModel().getSelectedItem())) {
            listAuxStates.add(boxStates.getSelectionModel().getSelectedItem());
            boxStates.getItems().remove(boxStates.getSelectionModel().getSelectedItem());
            updateStates();
        }
    }

    private void addStateOnPanel(final EnumMap<StatePolicy> state) {
        ItemViewPane pane = new ItemViewPane(state.getValue());
        pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean find = false;
                for (int i = 0; i < listAuxStates.size() && !find; i++) {
                    if (listAuxStates.get(i).getKey() == state.getKey()) {
                        boxStates.getItems().add(listAuxStates.remove(i));
                        find = true;
                    }
                }
                updateStates();
            }
        });
        panStates.getChildren().add(pane);
    }

    private void updateStates() {
        panStates.getChildren().clear();
        if (listAuxStates.isEmpty()) {
            panStates.getChildren().add(new Label("Vazio"));
            btnConfirm.setDisable(true);
        } else {
            btnConfirm.setDisable(false);
            for (EnumMap<StatePolicy> s : listAuxStates) {
                addStateOnPanel(s);
            }
        }
        stage.sizeToScene();
    }

    public void confirm() {
        if (orientation == null && !listAuxStates.isEmpty()) {
            orientation = new ProjectileOrientation();
        }

        if (orientation != null) {
            orientation.getPositionRelativeToGameObject().set(txtPosRelX.getValue(), txtPosRelY.getValue());
            orientation.getStates().clear();
            for (EnumMap<StatePolicy> e : listAuxStates) {
                orientation.addState(e.getKey());
            }
        }

        stage.close();
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public ProjectileOrientation getOrientation() {
        return orientation;
    }

    public void show() {
        stage.show();
    }
}
