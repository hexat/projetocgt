package application.util;

import java.io.File;

import application.Main;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Created by Luan on 29/01/2015.
 */
public class DialogsUtil {
    public static File LAST_OPEN_DIR = null;
    public static final FileChooser.ExtensionFilter IMG_FILTER = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");
    public static final FileChooser.ExtensionFilter WAV_FILTER = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");

	public static final ExtensionFilter CGT_FILTER = new FileChooser.ExtensionFilter("Projeto CGT (*.pcgt)", "*.pcgt");

    private static FileChooser fileChooser = null;

    public static void showErrorDialog() {
        System.out.println("Error");
    }

    private static File sop(String title, Window owner, FileChooser.ExtensionFilter... filters) {
        init();

        File result = null;
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().clear();
        for (FileChooser.ExtensionFilter f : filters) {
            fileChooser.getExtensionFilters().add(f);
        }

        if (LAST_OPEN_DIR != null) {
            fileChooser.setInitialDirectory(LAST_OPEN_DIR);
        }

        if (owner == null) {
            result = fileChooser.showOpenDialog(Main.getApp());
        } else {
            result = fileChooser.showOpenDialog(owner);
        }

        if (result != null) {
            LAST_OPEN_DIR = result.getParentFile();
        }

        return result;
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

	public static File showSaveDialog(String string) {
		init();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(CGT_FILTER);
		return fileChooser.showSaveDialog(Main.getApp());
	}
}
