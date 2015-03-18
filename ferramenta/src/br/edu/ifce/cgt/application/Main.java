package br.edu.ifce.cgt.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Main extends Application {
	private static Stage app;

	@Override
	public void start(Stage primaryStage) {
		app = primaryStage;
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource(
					"/view/Ferramenta.fxml"));
			Scene scene = new Scene(root, 900, 650);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ceará Game Tools");
			primaryStage.getIcons().add(
					new Image(
							Main.class.getResourceAsStream( "/logo.png" )));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getApp() {
		return app;
	}
}
