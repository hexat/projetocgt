package application;

import java.io.File;

import javafx.stage.FileChooser;

public class FileUtils {
	
	private static FileChooser fileChooser = null;

	public static File showOpenDialog(String title, FileChooser.ExtensionFilter... filters) {
		init();
		
		fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().clear();
		for (FileChooser.ExtensionFilter f : filters) {
			fileChooser.getExtensionFilters().add(f);			
		}
		
		return fileChooser.showOpenDialog(Main.getApp());
	}

	private static void init() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
		}
	}
}
