package application;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import cgt.CGTGameWorld;
import cgt.util.CGTFile;
import util.DialogsUtil;
import util.F;

public class Config {
    private static CGTGameWorld instance = null;

    private final static String GRADLE_PATH = "/Users/infolev/src/projetocgt/";

    public final static String BASE = System.getProperty("user.home") + "/.cgt/";
    public final static String BASE_IMG = "data/img/";
    public final static String BASE_AUDIO = "data/audio/";

    public static CGTFile createImg(File src) {
        CGTFile res = null;
        if (src != null && src.exists()) {
            try {
                String filename = System.currentTimeMillis() + ".png";
                FileUtils.copyFile(src, new File(BASE + BASE_IMG + filename));
                res = new CGTFile(BASE_IMG + filename);
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
        return res;
    }

    public static CGTFile createAudio(File src) throws IOException {
        CGTFile res = null;
        String filename = System.currentTimeMillis() + ".wav";
        FileUtils.copyFile(src, new File(BASE + BASE_AUDIO + filename));
        res = new CGTFile(BASE_AUDIO + filename);
        return res;
    }

    public static boolean export() {
        File configWorld = new File(BASE+"config.cgt");
        F.getGame().saveGame(configWorld);

        File out = new File("../android/assets/");
        try {
            FileUtils.deleteDirectory(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.mkdir();
        try {
            FileUtils.copyDirectory(new File(BASE), out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime runtime = Runtime.getRuntime();
        try {
            Process p1 = runtime.exec("sh "+GRADLE_PATH+"run");
            InputStream is = p1.getInputStream();
            int i = 0;
            while( (i = is.read() ) != -1) {
                System.out.print((char)i);
            }
        } catch(IOException e) {
        	e.printStackTrace();
        }
        return false;
    }
}
