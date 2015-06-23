package br.edu.ifce.cgt.application.controller;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Pref;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;

public class PreviewPane extends BorderPane {

    @FXML
    private Menu openRecentMenu;

    @FXML
    private AnchorPane drawablePane;

    public PreviewPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/Ferramenta2.fxml"));
        xml.setController(this);
        xml.setRoot(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.updateRecent();
    }

    @FXML
    public void newProject() {

    }

    @FXML
    public void openProject() {

    }

    @FXML
    public void saveProject() {

    }

    @FXML
    public void saveProjectAs() {

    }

    @FXML
    public void exportProject() {

    }

    @FXML
    public void closeProject() {

    }

    @FXML
    public void exit() {

    }

    @FXML
    public void runProject() {

    }

    @FXML
    public void about() {

    }

    @FXML
    public void addItem() {
        drawablePane.setStyle("-fx-background-color: black;");
        Circle circle = new Circle(50, Color.BLUE);
        circle.relocate(20, 20);
        Rectangle rectangle = new Rectangle(100, 100, Color.RED);
        rectangle.relocate(70, 70);
        drawablePane.getChildren().addAll(circle, rectangle);
    }

    private void updateRecent() {
        MenuItem item;
        openRecentMenu.getItems().clear();

        for (String path : Pref.load().getRecentProjects()) {

            final File file = new File(path);

            if (file.exists()) {
                item = new MenuItem(file.getName());
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // open(file);
                    }
                });

                openRecentMenu.getItems().add(item);
            }
        }

        if (openRecentMenu.getItems().size() > 0) {
            item = new MenuItem("Limpar");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Pref.load().getRecentProjects().clear();
                    Pref.load().save();
                    updateRecent();
                }
            });
            openRecentMenu.getItems().add(new SeparatorMenuItem());
            openRecentMenu.getItems().add(item);
        } else {
            item = new MenuItem("Vazio");
            item.setDisable(true);
            openRecentMenu.getItems().add(item);
        }
    }
}
