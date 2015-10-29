package br.edu.ifce.cgt.application.util;

import cgt.game.CGTGame;
import cgt.util.CGTFile;
import com.badlogic.gdx.utils.Json;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public final static String SEPARATOR = System.getProperty("file.separator");
    public final static String BASE = System.getProperty("user.home") + SEPARATOR + ".cgt" + SEPARATOR;
    public final static String BASE_IMG = "data" + SEPARATOR + "img" + SEPARATOR;
    public final static String BASE_AUDIO = "data" + SEPARATOR + "audio" + SEPARATOR;

    private static File inputProjectFile = null;
    private static Config instance = null;

    private CGTGame game = null;
    private AppPref pref;

    private Config() {
        game = CGTGame.get();

        File file = new File(Config.BASE);

        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.mkdirs();
        pref = new AppPref(new File(BASE + "pref.properties"));
    }

    private Config(CGTGame game, AppPref pref) {
        this.game = game;
        this.pref = pref;
    }

    public static Config get() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public CGTGame getGame() {
        return game;
    }

    public Image getImage(String filename) {
        Image img = null;

        try {
            InputStream is = new FileInputStream(BASE + BASE_IMG + filename);
            img = new Image(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return img;
    }

    public CGTFile createImg(File src) {
        CGTFile res = null;
        if (src != null && src.exists()) {
            try {
                String filename = src.getName().replace(' ', '_') + "_" + System.currentTimeMillis() + ".png";
                FileUtils.copyFile(src, new File(BASE + BASE_IMG + filename));
                res = new CGTFile(BASE_IMG + filename);
                res.setFilename(filename);
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
        return res;
    }

    public File createIcon(File src, int size) {
        File res = new File(BASE + "icons/" + size + ".png");

        try {
            FileUtils.copyFile(src, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public CGTFile createAudio(File src) throws IOException {
        CGTFile res = null;
        String filename = System.currentTimeMillis() + src.getName().substring(src.getName().length() - 4);
        FileUtils.copyFile(src, new File(BASE + BASE_AUDIO + filename));
        res = new CGTFile(BASE_AUDIO + filename);
        res.setFilename(src.getName().replace(' ', '_'));
        return res;
    }

    public boolean export() {
        if (getGame().validate().isEmpty()) {
            saveConfig();
            File out = new File("android/assets/");
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
            return true;
        }
        return false;
    }

    public void zip(File outFile) throws IOException {
        inputProjectFile = outFile;
        File configWorld = new File(BASE + "config.cgt");
        getGame().saveGame(configWorld);
        new ZipHelper().zipDir(BASE, outFile.getAbsolutePath());
    }

    public static void unzip(File inputFile) throws ZipException, IOException {
        inputProjectFile = inputFile;
        File o = new File(BASE);
        FileUtils.deleteDirectory(o);
        ZipFile zip = new ZipFile(inputFile);
        zip.extractAll(new File(BASE).getParentFile().getAbsolutePath());
        File configWorld = new File(BASE + "config.cgt");
        CGTGame game = null;
        if (configWorld.exists()) {
            InputStream io = new FileInputStream(configWorld);
            game = CGTGame.getSavedGame(io);
            io.close();

        }
        AppPref pref = new AppPref(new File(BASE + "pref.properties"));
        if (game != null) {
            instance = new Config(game, pref);
        }
    }

    public void destroy(CGTFile file) {
        File f = new File(BASE + file.getFile().getPath());
        f.delete();
    }

    public static boolean isLoaded() {
        return inputProjectFile != null;
    }

    public static boolean isCreated() {
        return instance != null;
    }

    public File getInputProjectFile() {
        return inputProjectFile;
    }

    public File getIcon(int size) {
        return new File(BASE + "icons/" + size + ".png");
    }

    public AppPref getPref() {
        return pref;
    }

    public void saveConfig() {
        File configWorld = new File(BASE + "config.cgt");
        getGame().saveGame(configWorld);
    }

    public File getProjectDir() {
        return new File(BASE);
    }

    public boolean wasModified() {
        try {
            String initial = FileUtils.readFileToString(new File(BASE + "config.cgt"));
            return !initial.equals(new Json().toJson(Config.get().getGame()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void reset() {
        instance = null;
        inputProjectFile = null;
    }

    public static Image[] splitImage(Image img, int cols, int rows, int prefWidth, int prefHeight) {

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        int w = bufferedImage.getWidth()/cols;
        int h = bufferedImage.getHeight()/rows;
        int num = 0;
        BufferedImage imgs[] = new BufferedImage[w*h];

        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < cols; x++) {
                imgs[num] = new BufferedImage(w, h, bufferedImage.getType());
                Graphics2D g = imgs[num].createGraphics();
                g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
                g.dispose();
                num++;
            }
        }

        Image images[] = new Image[cols*rows];

        for (int i = 0; i < images.length; i++ ) {
            BufferedImage image = imgs[i];
            BufferedImage scaledImage = new BufferedImage(prefWidth, prefHeight, image.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(image, 0, 0, prefWidth, prefHeight, null);
            graphics2D.dispose();
            images[i] = SwingFXUtils.toFXImage(scaledImage, null);
        }

        return images;
    }

    public static List<SpriteSheetTile> splitImage(Image img, int cols, int rows) {
        List<SpriteSheetTile> tiles = new ArrayList<>();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        int w = bufferedImage.getWidth()/cols;
        int h = bufferedImage.getHeight()/rows;

        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < cols; x++) {
                SpriteSheetTile tile = new SpriteSheetTile();
                tile.setCol(x);
                tile.setRow(y);
                tile.setHeight(h);
                tile.setWidth(w);

                BufferedImage buffer = new BufferedImage(w, h, bufferedImage.getType());
                Graphics2D g = buffer.createGraphics();
                g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
                g.dispose();

                tile.setImage(SwingFXUtils.toFXImage(buffer, null));
                tiles.add(tile);
            }
        }

        return tiles;
    }
}
