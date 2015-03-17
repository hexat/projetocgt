package br.edu.ifce.cgt.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		URL url = Main.class.getResource("/bin/desktop");
		if (url == null) {
			// error - missing folder
		} else {
			File file = new File(System.getProperty("user.home")+"/testeteste");
			file.delete();
			file.mkdirs();
			File dir = null;
			try {
				dir = new File(url.toURI());
				FileUtils.copyDirectory(dir, file);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getApp() {
		return app;
	}
}
