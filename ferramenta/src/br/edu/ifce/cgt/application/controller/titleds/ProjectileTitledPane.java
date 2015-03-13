package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemEditPane;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.dialogs.ProjectileOrientationDialog;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTProjectile;
import cgt.util.ProjectileOrientation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by infolev on 23/02/15.
 */
public class ProjectileTitledPane extends TitledPane {
    public IntegerTextField txtDamage;
    public IntegerTextField txtInterval;
    public IntegerTextField txtAmmo;
    public IntegerTextField txtMaxAmmo;
    public FloatTextField txtAngle;
    public VBox panOrientations;
    @FXML public VBox panGroup;

    private CGTProjectile projectile;

    @FXML private ComboBox<String> boxGroup;

    public ProjectileTitledPane(CGTProjectile projectile) {
        this.projectile = projectile;

        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigProjectile.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boxGroup.getItems().setAll(Config.get().getGame().getEnemiesGroup());
        boxGroup.getSelectionModel().selectFirst();
        updateGroups();
        init();
    }

    private void init() {
        txtAmmo.setValue(projectile.getAmmo());
        txtAngle.setValue(projectile.getAngle());
        txtDamage.setValue(projectile.getDamage());
        txtInterval.setValue(projectile.getInterval());
        txtMaxAmmo.setValue(projectile.getMaxAmmo());
        updateOrientations();

        txtMaxAmmo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (!newValue) {
                    projectile.setMaxAmmo(txtMaxAmmo.getValue());
                }
            }
        });

        txtInterval.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    projectile.setInterval(txtInterval.getValue());
                }
            }
        });

        txtDamage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    projectile.setDamage(txtDamage.getValue());
                }
            }
        });

        txtAngle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    projectile.setAngle(txtAngle.getValue());
                }
            }
        });
    }

    public void addOrientation() {
        ProjectileOrientationDialog dialog = new ProjectileOrientationDialog();
        dialog.showAndWait();

        if (dialog.getOrientation()!= null) {
            projectile.addOrientation(dialog.getOrientation());
            updateOrientations();
        }
    }

    private void updateOrientations() {
        panOrientations.getChildren().clear();
        for (ProjectileOrientation o : projectile.getOrientations()) {
            addOrientationOnPane(o);
        }
    }

    private void addOrientationOnPane(final ProjectileOrientation o) {
        ItemEditPane pane = new ItemEditPane(o.getPositionRelativeToGameObject().toString());
        pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                projectile.getOrientations().remove(o);
                updateOrientations();
            }
        });
        pane.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProjectileOrientationDialog dialog = new ProjectileOrientationDialog();
                dialog.setOrientation(o);
                dialog.show();
            }
        });
        panOrientations.getChildren().add(pane);
    }

    public void addEnemyGroup() {
        if (boxGroup.getValue() != null) {
            projectile.addGroup(boxGroup.getValue());
            updateGroups();
        }
    }

    private void addGroupToPane(final String value) {
        ItemViewPane item = new ItemViewPane(value);
        item.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                projectile.removeGroup(value);
                updateGroups();
            }
        });
        panGroup.getChildren().add(item);
    }

    private void updateGroups() {
        panGroup.getChildren().clear();
        if (projectile.getGroups().size() > 0) {
            for (String s : projectile.getGroups()) {
                addGroupToPane(s);
            }
        } else {
            panGroup.getChildren().add(new Label("Todos"));
        }
    }
}
