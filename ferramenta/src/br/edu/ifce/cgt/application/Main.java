package br.edu.ifce.cgt.application;

import br.edu.ifce.cgt.application.controller.MainPane;
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
        //BorderPane root = new MainPane();
        BorderPane root = new PreviewPane();

        Scene scene = new Scene(root, 900, 650);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Ceará Game Tools");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/logo.png")));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (Config.isCreated() && (!Config.isLoaded() || (Config.isLoaded() && Config.get().wasModified()))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Salvar");
                    alert.setHeaderText(null);
                    alert.setContentText("Você modificou seu projeto. Deseja salvar antes de sair?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        File save;
                        if (Config.isLoaded()) {
                            save = Config.get().getInputProjectFile();
                        } else {
                            save = DialogsUtil.showSaveDialog("Salvar projeto");
                        }

                        if (save != null) {

                            Pref.load().addRecentProject(save.getAbsolutePath());
                            Pref.load().save();

                            try {
                                Config.get().zip(save);
                            } catch (IOException e) {
                                DialogsUtil.showErrorDialog();
                                e.printStackTrace();
                            }
                        }
                    }
                }
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
