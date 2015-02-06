package util;

import java.io.File;

import application.Main;
import javafx.stage.FileChooser;

/**
 * Created by Luan on 29/01/2015.
 */
public class DialogsUtil {
    public static final FileChooser.ExtensionFilter IMG_FILTER = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");

    private static FileChooser fileChooser = null;

    public static void showErrorDialog() {
        System.out.println("Error");
    }

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
