package br.edu.ifce.cgt.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.ZipHelper;
import cgt.game.CGTGame;
import cgt.util.CGTFile;

public abstract class Config {
	private final static String GRADLE_PATH = "/Users/luanjames/src/projetocgt/";

	public final static String BASE = System.getProperty("user.home")
			+ "/.cgt/";
	public final static String BASE_IMG = "data/img/";
	public final static String BASE_AUDIO = "data/audio/";

    private static File inputProjectFile = null;

//	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.String", new Locale("en", "EN"));

	private static CGTGame instance = null;

	public static CGTGame getGame() {
		if (instance == null) {
			instance = CGTGame.get();
			File file = new File(Config.BASE);
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException e) {
				e.printStackTrace();
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
				FileUtils.copyFile(src, new File(BASE + BASE_IMG + filename));
				res = new CGTFile(BASE_IMG + filename);
				res.setFilename(src.getName());
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
		res.setFilename(src.getName());
		return res;
	}

	public static boolean export(Observer observer) {
		if (getGame().validate().isEmpty()) {
			File configWorld = new File(BASE + "config.cgt");
			getGame().saveGame(configWorld);

			File out = new File("android/assets/");
            System.out.println(out.getAbsolutePath());
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

            observer.update(null, "Iniciando...");
			Runtime runtime = Runtime.getRuntime();
			try {
                Process p1 = runtime.exec("sh "+GRADLE_PATH+"run");
                InputStream is = p1.getInputStream();
                int i;
                String res = "";
                while( (i = is.read() ) != -1) {
                    if (((char)i) == '\n') {
                        observer.update(null, res);
                        res = "";
                    } else {
                        res += (char) i;
                    }
                }
                observer.update(null, "Concluido...");
                } catch(IOException e) {
                e.printStackTrace();
			}

			return true;
		}

        observer.update(null, "Problemas");
		return false;
	}

	public static void zip(File outFile) throws IOException {
		File configWorld = new File(BASE + "config.cgt");
		getGame().saveGame(configWorld);
		new ZipHelper().zipDir(BASE, outFile.getAbsolutePath());
	}
	public static void unzip(File inputFile) {
        inputProjectFile = inputFile;
		File o = new File(BASE);
		try {
			FileUtils.deleteDirectory(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ZipFile zip = new ZipFile(inputFile);
			zip.extractAll(new File(BASE).getParentFile().getAbsolutePath());
		} catch (ZipException e) {
			e.printStackTrace();
		}
		File configWorld = new File(BASE + "config.cgt");
		if (configWorld.exists()) {
			try {
				InputStream io = new FileInputStream(configWorld);
				instance = CGTGame.getSavedGame(io);
				io.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public static void destroy(CGTFile file) {
		File f = new File(BASE+file.getFile().getPath());
		f.delete();
	}

    public static boolean isLoaded() {
        return inputProjectFile != null;
    }

    public static File getInputProjectFile() {
        return inputProjectFile;
    }

//	public static void loadView(String pathResource, Object controller) {
//		FXMLLoader xml = new FXMLLoader(Main.class.getResource(pathResource));
//		xml.setRoot(controller);
//		xml.setController(controller);
//		xml.setResources(resourceBundle);
//
//		try {
//			xml.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	public static String t(String key) {
//		return resourceBundle.getString(key);
//	}
}
