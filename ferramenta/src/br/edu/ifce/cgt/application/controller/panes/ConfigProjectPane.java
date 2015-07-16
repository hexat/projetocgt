package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import cgt.core.CGTProject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ConfigProjectPane extends AnchorPane {

    @FXML
    private ChoiceBox<String> canvasSize;

    private Runnable onUpdateRunner;

    private CGTProject cgtProject;

    public ConfigProjectPane(CGTProject cgtProject) {
        this.cgtProject = cgtProject;

        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigProject.fxml"));

        xml.setController(this);
        xml.setRoot(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.canvasSize.getItems().addAll("800x600", "856x480", "640x480");
        this.canvasSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String[] widthHeight = newValue.split("x");
                int width = Integer.valueOf(widthHeight[0]);
                int height = Integer.valueOf(widthHeight[1]);
                cgtProject.setCanvasWidth(width);
                cgtProject.setCanvasHeight(height);
                onUpdateRunner.run();
            }
        });
    }

    public ConfigProjectPane(CGTProject cgtProject, Runnable onUpdateRunner) {
        this(cgtProject);
        this.onUpdateRunner = onUpdateRunner;
    }
}
