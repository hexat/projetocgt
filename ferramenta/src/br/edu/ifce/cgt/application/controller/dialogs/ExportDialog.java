package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.MenuBarController;
import br.edu.ifce.cgt.application.util.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 * Created by luanjames on 25/02/15.
 */
public class ExportDialog extends BorderPane {
    private final String PROPERTIES_ANDROID_FILE = "android/local.properties";

    private final Stage stage;
    @FXML public TitledPane tpDetails;
    @FXML private TextArea textArea;
    @FXML private Button btnOk;
    @FXML private TextField txtAndroidSdk;
    private Process process;

    public ExportDialog() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/ExportDialog.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());

        unZip();

        init();
    }

    private void init() {
        Properties prefs = new Properties();
        try {
            FileInputStream stream = new FileInputStream(MenuBarController.localDefaultDirectory()+PROPERTIES_ANDROID_FILE);
            prefs.load(stream);
            String dir = prefs.getProperty("sdk.dir");
            txtAndroidSdk.setText(dir);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                copyFiles();
                runGradle();
            }
        });
        tpDetails.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                stage.sizeToScene();
            }
        });
    }

    private void copyFiles() {
        File assets = new File(MenuBarController.localDefaultDirectory()+"android/app/assets");
        try {
            FileUtils.deleteDirectory(assets);
            assets.mkdirs();

            FileUtils.copyDirectory(new File(Config.BASE), assets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File input = Config.get().getPref().getFile();
        File out = new File(MenuBarController.localDefaultDirectory()+"android/"+input.getName());
        try {
            FileUtils.copyFile(input, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unZip() {
        File android = new File(MenuBarController.localDefaultDirectory()+"android/");
        if (!android.exists()) {
            android.mkdirs();
            File out = new File(MenuBarController.localDefaultDirectory()+"android.zip");
            InputStream io = Main.class.getResourceAsStream("/base-android.zip");

            try {
                FileUtils.copyInputStreamToFile(io, out);
                io.close();

                ZipFile zip = new ZipFile(out);
                zip.extractAll(MenuBarController.localDefaultDirectory());

                out.delete();

                io = Main.class.getResourceAsStream("/bin/android-1.0.zip");

                FileUtils.copyInputStreamToFile(io, out);
                io.close();

                zip = new ZipFile(out);
                zip.extractAll(MenuBarController.localDefaultDirectory()+"android/app/");
                out.delete();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }
    }

    public void selSdkFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Android SDK");
        File selectedDirectory = chooser.showDialog(Main.getApp());
        if (selectedDirectory != null) {
            Properties properties = new Properties();
            properties.put("sdk.dir", selectedDirectory.getAbsolutePath());
            try {
                FileOutputStream stream = new FileOutputStream(MenuBarController.localDefaultDirectory()+PROPERTIES_ANDROID_FILE);
                properties.store(stream, null);
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            txtAndroidSdk.setText(selectedDirectory.getAbsolutePath());
        }
    }

    public void show() {
        stage.show();
        Config.get().export();
    }

    public void runGradle() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String path = MenuBarController.localDefaultDirectory() + "android/gradlew";
                String comm = "app:assembleRelease";
                try {
                    FileUtils.deleteDirectory(new File(MenuBarController.localDefaultDirectory() + "android/app/build/"));
                    if (MenuBarController.isWin()) {
                        ProcessBuilder builder = new ProcessBuilder("\"" + path + ".bat\"", comm);
                        builder.directory(new File(path).getParentFile());
                        process = builder.start();
//                        process = Runtime.getRuntime().exec("\"" + path + ".bat\" "+comm);
                    } else {
                        Runtime.getRuntime().exec("chmod +x " + path);
                        ProcessBuilder builder = new ProcessBuilder("./gradlew", comm);
                        builder.directory(new File(path).getParentFile());
                        process = builder.start();
//                        process = Runtime.getRuntime().exec(path + " "+comm);
                    }
                    StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");

                    // any output?
                    StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

                    // start gobblers
                    outputGobbler.start();
                    errorGobbler.start();

                    process.waitFor();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            File file = new File(MenuBarController.localDefaultDirectory() + "android/app/build/outputs/apk/app-release-unsigned.apk");
                            if (file.exists()) {
                                FileChooser chooser = new FileChooser();
                                chooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Android App", "*.apk"));
                                File save = chooser.showSaveDialog(Main.getApp());
                                if (save != null) {
                                    try {
                                        FileUtils.copyFile(file, save);
                                        if (save.exists()) {
                                            if (MenuBarController.isWin()) {
                                                Runtime.getRuntime().exec("explorer " + save.getParent());
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private class StreamGobbler extends Thread {

        InputStream is;
        String type;

        private StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            textArea.clear();
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    final String str = line;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            textArea.appendText(str + "\n");
                        }
                    });
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
