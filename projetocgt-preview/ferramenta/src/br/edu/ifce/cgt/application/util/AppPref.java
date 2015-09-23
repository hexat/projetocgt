package br.edu.ifce.cgt.application.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by luanjames on 27/02/15.
 */
public class AppPref {
    private final String APK_NAME="APK_NAME";
    private final String VERSION_NAME="VERSION_NAME";
    private final String VERSION_CODE="VERSION_CODE";
    private final String APPID="APPID";
    private final String STORE_PASSWORD="STORE_PASSWORD";
    private final String KEY_PATH="KEY_PATH";
    private final String KEY_ALIAS="KEY_ALIAS";
    private final String KEY_PASSWORD="KEY_PASSWORD";
    private final String TARGET_VERSION="TARGET_VERSION";
    private final String MIN_SDK_VERSION="MIN_SDK_VERSION";
    private final String BUILD_TOOLS_VERSION="BUILD_TOOLS_VERSION";

    private String apkName;
    private String versionName;
    private int versionCode;
    private String appId;

    private File file;

    protected AppPref(File file) {
        this.file = file;
        Properties prefs = new Properties();
        if (file.exists()) {
            try {
                FileInputStream stream = new FileInputStream(file);
                prefs.load(stream);
                apkName = prefs.getProperty(APK_NAME);
                versionCode = Integer.parseInt(prefs.getProperty(VERSION_CODE));
                versionName = prefs.getProperty(VERSION_NAME);
                appId = prefs.getProperty(APPID);

                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            apkName = "Ceara Game Tools";
            versionCode = 1;
            versionName = "1.0";
            appId = "br.edu.ifce.cearagametools";
            save();
        }

    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void saveSignPref(File out) {
        out.delete();
        Properties prefs = getNewProperties();

        Pref settings = Pref.load();
        prefs.put(STORE_PASSWORD, settings.getStorePassword());
        prefs.put(KEY_PASSWORD, settings.getKeyPassword());
        prefs.put(KEY_PATH, settings.getKeyPath());
        prefs.put(BUILD_TOOLS_VERSION, settings.getBuildToolsVersion());
        prefs.put(MIN_SDK_VERSION, settings.getMinSdkVersion()+"");
        prefs.put(TARGET_VERSION, settings.getTargetVersion()+"");
        prefs.put(KEY_ALIAS, settings.getKeyAlias());

        try {
            out.createNewFile();
            FileOutputStream stream = new FileOutputStream(out);
            prefs.store(stream, null);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        Properties prefs = getNewProperties();
        try {
            prefs.store(new FileOutputStream(file), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties getNewProperties() {
        Properties prefs = new Properties();

        prefs.put(APK_NAME, getApkName());
        prefs.put(VERSION_NAME, getVersionName());
        prefs.put(VERSION_CODE, getVersionCode()+"");
        prefs.put(APPID, getAppId());

        return prefs;
    }

    public File getFile() {
        return file;
    }
}
