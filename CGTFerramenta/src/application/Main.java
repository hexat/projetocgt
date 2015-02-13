package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage app;

	@Override
	public void start(Stage primaryStage) {
		app = primaryStage;
		try {
			VBox root = FXMLLoader.load(getClass().getResource(
					"/view/Ferramenta.fxml"));
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
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
