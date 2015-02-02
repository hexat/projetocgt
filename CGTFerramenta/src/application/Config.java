package application;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import cgt.CGTGameWorld;
import cgt.util.CGTFile;
import util.Dialogs;

public class Config {
    private static CGTGameWorld instance = null;

    private final static String GRADLE_PATH = "/Users/infolev/src/projetocgt/";

    private final static String BASE = System.getProperty("user.home") + "/.cgt/";
    private final static String BASE_IMG = "data/img/";
    private final static String BASE_AUDIO = "data/audio/";

    private Config() {
        instance = new CGTGameWorld();
    }

    public static CGTGameWorld getWorld() {
        if (instance == null) {
            instance = new CGTGameWorld();
            File file = new File(BASE);
            if (file.exists()) {
                file.delete();
            }

            file.mkdirs();
        }

        return instance;
    }

    public static CGTFile createImg(File src) {
        CGTFile res = null;
        if (src != null && src.exists()) {
            try {
                String filename = System.currentTimeMillis() + ".png";
                copyFile(src, new File(BASE + BASE_IMG + filename));
                res = new CGTFile(BASE_IMG + filename);
            } catch (IOException e) {
                Dialogs.showErrorDialog();
                e.printStackTrace();
            }
        }
        return res;
    }

    public static CGTFile createAudio(File src) throws IOException {
        CGTFile res = null;
        String filename = System.currentTimeMillis() + ".wav";
        copyFile(src, new File(BASE + BASE_AUDIO + filename));
        res = new CGTFile(BASE_AUDIO + filename);
        return res;
    }


    private static void copyFile(File source, File destination) throws IOException {
        if (destination.exists()) {
            destination.delete();
        }

        destination.getParentFile().mkdirs();
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
        }
    }

    public static boolean export() {

        File configWorld = new File(BASE+"config.cgt");
        getWorld().salvaStream(configWorld);

        try {
            FileUtils.copyDirectory(new File(BASE), new File("../android/assets/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime runtime = Runtime.getRuntime();
        try {
            Process p1 = runtime.exec("sh "+GRADLE_PATH+"gradlew tasks");
            InputStream is = p1.getInputStream();
            int i = 0;
            while( (i = is.read() ) != -1) {
                System.out.print((char)i);
            }
        } catch(IOException ioException) {
            System.out.println(ioException.getMessage() );
        }
        return false;
    }
}
