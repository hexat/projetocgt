package br.edu.ifce.cgt.application;


import br.edu.ifce.cgt.application.controller.PreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.Pref;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
    private static Stage app;

    @Override
    public void start(Stage primaryStage) {
        app = primaryStage;
        PreviewPane root = new PreviewPane();
        primaryStage.setTitle("Ceará Game Tools");
        Scene scene = new Scene(root, 1080, 820);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/logo.png")));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                root.beforeClosing();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getApp() {
        return app;
    }
}
