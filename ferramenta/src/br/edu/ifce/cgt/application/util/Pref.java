package br.edu.ifce.cgt.application.util;

import br.edu.ifce.cgt.application.Main;
import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Luan James on 26/02/15.
 */
public class Pref {
    private static final String FILE_NAME = "config.properties";

    private String lang;
    private String lastDir;
    private List<String> recentProjects;

    private static Pref instance = null;

    private Pref() {
        lastDir = null;
        lang = "pt";
        recentProjects = new ArrayList<String>();
    }

    public void save() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) file.createNewFile();

            PrintWriter writer = new PrintWriter(file);
            new Json().toJson(instance, writer);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static Pref load() {
        if (instance == null) {
            File file = new File(FILE_NAME);

            if (file.exists()) {
                try {
                    instance = new Json().fromJson(Pref.class, new FileInputStream(FILE_NAME));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                instance = new Pref();
            }
        }

        return instance;
    }

    public void setLastDir(String lastDir) {
        this.lastDir = lastDir;
    }

    public String getLastDir() {
        if (lastDir != null && !(new File(lastDir).exists())) {
            lastDir = null;
        }
        return lastDir;
    }

    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("i18n.String", new Locale(lang, lang.toUpperCase()));
    }

    public List<String> getRecentProjects() {
        return recentProjects;
    }

    public void addRecentProject(String absolutePath) {
        if (!recentProjects.contains(absolutePath)) {
            recentProjects.add(0, absolutePath);
            if (recentProjects.size() > 10) {
                recentProjects.remove(recentProjects.size() - 1);
            }
        }
    }
}
