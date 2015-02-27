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

    private String apkName;
    private String versionName;
    private int versionCode;
    private String appId;

    public AppPref() {
        Properties prefs = new Properties();
        File file = new File("prefs.properties");
        if (file.exists()) {
            try {
                prefs.load(new FileInputStream(file));
                apkName = prefs.getProperty(APK_NAME);
                versionCode = Integer.parseInt(prefs.getProperty(VERSION_CODE));
                versionName = prefs.getProperty(VERSION_NAME);
                appId = prefs.getProperty(APPID);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void toProperties() {
        Properties prefs = new Properties();

        prefs.put(APK_NAME, getApkName());
        prefs.put(VERSION_NAME, getVersionName());
        prefs.put(VERSION_CODE, getVersionCode());
        prefs.put(APPID, getAppId());

        try {
            prefs.store(new FileOutputStream(new File("prefs.properties")), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}