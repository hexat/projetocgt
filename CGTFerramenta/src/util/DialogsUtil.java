package util;

import java.io.File;

import application.Main;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Created by Luan on 29/01/2015.
 */
public class DialogsUtil {
    public static final FileChooser.ExtensionFilter IMG_FILTER = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");

    private static FileChooser fileChooser = null;

    public static void showErrorDialog() {
        System.out.println("Error");
    }

    private static File sop(String title, Window owner, FileChooser.ExtensionFilter... filters) {
        init();

        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().clear();
        for (FileChooser.ExtensionFilter f : filters) {
            fileChooser.getExtensionFilters().add(f);
        }

        if (owner == null) {
            return fileChooser.showOpenDialog(Main.getApp());
        } else {
            return fileChooser.showOpenDialog(owner);
        }
    }

    public static File showOpenDialog(String title, FileChooser.ExtensionFilter... filters) {
        return sop(title,null,filters);
    }

    public static File showOpenDialog(String title, Window owner, FileChooser.ExtensionFilter... filters) {
        return sop(title,owner,filters);
    }

    private static void init() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
        }
    }
}
