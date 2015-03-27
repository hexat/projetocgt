package br.edu.ifce.cgt.application.util;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.MenuBarController;
import com.badlogic.gdx.utils.Json;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Luan James on 26/02/15.
 */
public class Pref {
    private static final String FILE_NAME = "config.properties";

    private static final int CURRENT_VERSION = 1;
    private int lastVersion;
    private String lang;
    private String lastDir;
    private String sdkPath;
    private String keyPath;
    private String storePassword;
    private String keyPassword;
    private String keyAlias;
    private String buildToolsVersion;
    private int minSdkVersion;
    private int targetVersion;
    private List<String> recentProjects;

    private static Pref instance = null;

    private Pref() {
        sdkPath = null;
        lastDir = null;
        keyPath = null;
        storePassword = null;
        keyAlias = null;
        keyPassword = null;
        buildToolsVersion = null;
        targetVersion = 0;
        minSdkVersion = 0;
        lastVersion = 0;
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
                    FileInputStream stream = new FileInputStream(FILE_NAME);
                    instance = new Json().fromJson(Pref.class, stream);
                    stream.close();

                    if (instance.getLastVersion() != CURRENT_VERSION) {
                        FileUtils.deleteDirectory(new File(MenuBarController.localDefaultDirectory()));
                        instance.setLastVersion(CURRENT_VERSION);
                        instance.save();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                instance = new Pref();
            }
        }

        return instance;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getBuildToolsVersion() {
        return buildToolsVersion;
    }

    public void setBuildToolsVersion(String buildToolsVersion) {
        this.buildToolsVersion = buildToolsVersion;
    }

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(int minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public int getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(int targetVersion) {
        this.targetVersion = targetVersion;
    }

    public void setSdkPath(String sdkPath) {
        this.sdkPath = sdkPath;
    }

    public String getSdkPath() {
        return sdkPath;
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

    public int getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(int lastVersion) {
        this.lastVersion = lastVersion;
    }
}
