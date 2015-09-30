package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTProject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ConfigProjectPane extends AnchorPane {

    private static final String[] projectSizes = {"1920x1080", "1366x768", "1334x750", "1280x720", "1024x768", "1136x640", "960x640", "1024x600", "800x600", "854x480", "800x480", "768x480", "640x480"};

    @FXML
    private ChoiceBox<String> canvasSize;

    @FXML
    private ComboBox<String> firstWindow;

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

        this.canvasSize.getItems().addAll(projectSizes);
        this.canvasSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeProjectSize(newValue);
            }
        });
        firstWindow.getItems().setAll(Config.get().getGame().getIds());
    }

    private void changeProjectSize(String size) {
        String[] widthHeight = size.split("x");
        int width = Integer.valueOf(widthHeight[0]);
        int height = Integer.valueOf(widthHeight[1]);
        this.cgtProject.setCanvasWidth(width);
        this.cgtProject.setCanvasHeight(height);
        this.onUpdateRunner.run();
    }

    public ConfigProjectPane(CGTProject cgtProject, Runnable onUpdateRunner) {
        this(cgtProject);
        this.onUpdateRunner = onUpdateRunner;
    }
}
