package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.panes.ProjectileOrientationPane;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.core.CGTProjectile;
import cgt.util.ProjectileOrientation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by infolev on 23/02/15.
 */
public class ProjectileTitledPane extends TitledPane {
    public IntegerTextField txtNumFires;
    public IntegerTextField txtDamage;
    public IntegerTextField txtInterval;
    public IntegerTextField txtAmmo;
    public IntegerTextField txtMaxAmmo;
    public FloatTextField txtAngle;
    public VBox panOrientations;

    private CGTProjectile projectile;

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

        init();
    }

    private void init() {
        txtNumFires.setText(projectile.getNumFiresForOneInput()+"");
        txtAmmo.setText(projectile.getAmmo()+"");
        txtAngle.setText(projectile.getAngle()+"");
        txtDamage.setText(projectile.getDamage()+"");
        txtInterval.setText(projectile.getInterval()+"");
        txtMaxAmmo.setText(projectile.getMaxAmmo()+"");

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

        txtNumFires.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    projectile.setNumFiresForOneInput(txtNumFires.getValue());
                }
            }
        });
    }

    public void addOrientation() {
        ProjectileOrientation o = new ProjectileOrientation();
        ProjectileOrientationPane dialog = new ProjectileOrientationPane(o);
        dialog.getStage().showAndWait();

        if (dialog.isConfirmed()) {
            projectile.addOrientation(o);
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
        ItemViewPane pane = new ItemViewPane(o.getPositionRelativeToGameObject().toString());
        pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                projectile.getOrientations().remove(o);
                updateOrientations();
            }
        });
        panOrientations.getChildren().add(pane);
    }
}
