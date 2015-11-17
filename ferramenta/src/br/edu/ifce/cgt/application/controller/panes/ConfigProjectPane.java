package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTProject;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ConfigProjectPane extends StackPane {

    private static final String[] projectSizes = {"16:9", "17:10", "16:10", "3:2", "4:3"};
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
        this.firstWindow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Config.get().getGame().setStartWindowId(newValue);
            }
        });
    }

    private void changeProjectSize(String size) {
        String[] widthHeight = size.split(":");
        int width = Integer.valueOf(widthHeight[0]);
        int height = Integer.valueOf(widthHeight[1]);
        if(width == 16 && height == 9){
            width = 640;
            height = 360;
        }
        else if(width == 16 && height == 10){
            width = 576;
            height = 360;
        }
        else if(width == 17 && height == 10){
            width = 612;
            height = 360;
        }
        else if(width == 3 && height == 2){
            width = 540;
            height = 360;
        }
        else if(width == 4 && height == 3){
            width = 480;
            height = 360;
        }
        this.cgtProject.setCanvasWidth(width);
        this.cgtProject.setCanvasHeight(height);
        for(CGTGameWorld w : Config.get().getGame().getWorlds()) {
            w.setHeightAndWidth(height, width);
        }
        for(CGTScreen s : Config.get().getGame().getScreens()) {
            s.setHeightAndWidth(height, width);
        }
        this.onUpdateRunner.run();
    }

    public ConfigProjectPane(CGTProject cgtProject, Runnable onUpdateRunner) {
        this(cgtProject);
        this.onUpdateRunner = onUpdateRunner;
    }

    public CGTProject getCgtProject(){ return this.cgtProject; }

    public ComboBox<String> getComboBox() {
        return this.firstWindow;
    }
}
