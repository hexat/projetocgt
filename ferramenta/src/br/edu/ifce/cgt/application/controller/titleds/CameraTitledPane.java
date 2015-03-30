package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.policy.GameModePolicy;
import cgt.util.Camera;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by luanjames on 04/03/15.
 */
public class CameraTitledPane extends TitledPane {
    private final Camera camera;

    @FXML private ComboBox<EnumMap<GameModePolicy>> boxGameMode;
    @FXML private FloatTextField txtInitialWidth;
    @FXML private FloatTextField txtInitialHeight;
    @FXML private FloatTextField txtCloseWidth;
    @FXML private FloatTextField txtCloseHeight;
    @FXML private FloatTextField txtFullWidth;
    @FXML private FloatTextField txtFullHeight;
    @FXML private FloatTextField txtScale;
    @FXML private FloatTextField txtVolumeFull;

    public CameraTitledPane(Camera camera) {
        this.camera = camera;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/config_camera.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();

        txtInitialWidth.setValue(camera.getInitialWidth());
        txtInitialHeight.setValue(camera.getInitialHeight());
        txtCloseWidth.setValue(camera.getCloseWidth());
        txtCloseHeight.setValue(camera.getCloseHeight());
        txtFullWidth.setValue(camera.getFullWidth());
        txtFullHeight.setValue(camera.getFullHeight());
        txtScale.setValue(camera.getScale()*100);
        txtVolumeFull.setValue(camera.getVolumeOnFullCamera());

        setActions();

        int i = 0;
        while (i < boxGameMode.getItems().size() && boxGameMode.getItems().get(i).getKey() != camera.getGameMode()) i++;
        if (i < boxGameMode.getItems().size()) boxGameMode.getSelectionModel().select(i);
    }

    private void setActions() {
        txtInitialWidth.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setInitialWidth(txtInitialWidth.getValue());
                }
            }
        });
        txtInitialHeight.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setInitialHeight(txtInitialHeight.getValue());
                }
            }
        });
        txtCloseWidth.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setCloseWidth(txtCloseWidth.getValue());
                }
            }
        });
        txtCloseHeight.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setCloseHeight(txtCloseHeight.getValue());
                }
            }
        });
        txtFullWidth.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setFullWidth(txtFullWidth.getValue());
                }
            }
        });
        txtFullHeight.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setFullHeight(txtFullHeight.getValue());
                }
            }
        });
        txtScale.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setScale(txtScale.getValue());
                }
            }
        });
        txtVolumeFull.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    camera.setVolumeOnFullCamera(txtVolumeFull.getValue());
                }
            }
        });
        boxGameMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EnumMap<GameModePolicy> item = boxGameMode.getSelectionModel().getSelectedItem();
                if (item != null) {
                    camera.setGameMode(item.getKey());
                }
            }
        });
    }

    private void init() {
        txtCloseHeight.setMaxMin(0, 1);
        txtCloseWidth.setMaxMin(0, 1);
        txtFullHeight.setMaxMin(0, 1);
        txtFullWidth.setMaxMin(0, 1);
        txtInitialHeight.setMaxMin(0, 1);
        txtInitialWidth.setMaxMin(0, 1);
        txtVolumeFull.setMaxMin(0, 1);

        ResourceBundle bundle = Pref.load().getBundle();

        List<EnumMap<GameModePolicy>> list = new ArrayList<EnumMap<GameModePolicy>>();

        for (GameModePolicy p : GameModePolicy.values()) {
            list.add(new EnumMap<GameModePolicy>(p, bundle.getString(p.name())));
        }
        boxGameMode.getItems().setAll(list);

    }
}
