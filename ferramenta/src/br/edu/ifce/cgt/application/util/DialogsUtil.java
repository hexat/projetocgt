package br.edu.ifce.cgt.application.util;

import br.edu.ifce.cgt.application.Main;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by Luan on 29/01/2015.
 */
public class DialogsUtil {
    public static final FileChooser.ExtensionFilter IMG_FILTER = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");
    public static final FileChooser.ExtensionFilter WAV_FILTER = new FileChooser.ExtensionFilter("Arquivo Audio (*.wav, *.mp3)", "*.wav", "*.mp3");

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

        Pref pref = Pref.load();
        if (pref.getLastDir() != null) {
            fileChooser.setInitialDirectory(new File(pref.getLastDir()));
        }

        if (owner == null) {
            result = fileChooser.showOpenDialog(Main.getApp());
        } else {
            result = fileChooser.showOpenDialog(owner);
        }

        if (result != null) {
            pref.setLastDir(result.getParent());
            pref.save();
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
        Pref pref = Pref.load();

        if (pref.getLastDir() != null) {
            File file = new File(pref.getLastDir());
            if (file.exists()) {
                fileChooser.setInitialDirectory(file);
            } else {
                pref.setLastDir(null);
            }
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        File res = fileChooser.showSaveDialog(Main.getApp());

        if (res != null) {
            pref.setLastDir (res.getParent());
            pref.save();

            if (!FilenameUtils.getExtension(res.getName()).equalsIgnoreCase("pcgt")) {
                res = new File(res.toString() + ".pcgt");
            }
        }
        
		return res;
	}
}
