package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.MenuBarController;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.AppPref;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Pref;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
public class ExportDialog extends VBox {
    private final String PROPERTIES_ANDROID_FILE = "android/local.properties";

    private final Stage stage;
    private final Pref settings;
    @FXML public TitledPane tpDetails;
    @FXML public ProgressBar barStatus;
    @FXML public TextField txtKeyStorePath;
    @FXML public TextField txtAliasKey;
    @FXML public PasswordField txtAliasPassword;
    @FXML public PasswordField txtStorePassword;
    @FXML public VBox boxButton;
    @FXML public IntegerTextField txtTarget;
    @FXML public TextField txtBuildTools;
    @FXML public IntegerTextField txtMinVersion;
    @FXML private TextArea textArea;
    @FXML private Button btnOk;
    @FXML private TextField txtAndroidSdk;
    private ProgressIndicator progressIndicator;
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

        settings = Pref.load();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                saveSettings();
            }
        });
        unZip();

        init();
    }

    private void saveSettings() {
        settings.setMinSdkVersion(txtMinVersion.getValue());
        settings.setBuildToolsVersion(txtBuildTools.getText());
        settings.setTargetVersion(txtTarget.getValue());
        settings.setKeyAlias(txtAliasKey.getText());
        settings.setStorePassword(txtStorePassword.getText());
        settings.setKeyPassword(txtAliasPassword.getText());

        settings.save();
    }

    private void init() {
        progressIndicator = new ProgressIndicator();

        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (check()) {
                    boxButton.getChildren().setAll(progressIndicator);
                    barStatus.setProgress(0);
                    barStatus.setStyle(null);
                    copyFiles();
                    runGradle();
                }
            }
        });

        txtAndroidSdk.setText(settings.getSdkPath());
        if (settings.getTargetVersion() > 0) {
            txtTarget.setValue(settings.getTargetVersion());
        }
        txtBuildTools.setText(settings.getBuildToolsVersion());
        if (settings.getMinSdkVersion() > 0) {
            txtMinVersion.setValue(settings.getMinSdkVersion());
        }
        txtAliasKey.setText(settings.getKeyAlias());
        txtKeyStorePath.setText(settings.getKeyPath());

        tpDetails.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (tpDetails.isExpanded()) {
                    stage.setHeight(stage.getHeight()+200);
                } else {
                    stage.setHeight(stage.getHeight()-200);
                }
//                stage.sizeToScene();
            }
        });

    }

    private boolean check() {
        boolean result = !(txtKeyStorePath.getText().isEmpty() || txtAndroidSdk.getText().isEmpty()
                || txtAliasPassword.getText().isEmpty()
                || txtAliasKey.getText().isEmpty()
                || txtStorePassword.getText().isEmpty()
                || txtMinVersion.getValue() <= 0
                || txtBuildTools.getText().isEmpty()
                || txtTarget.getValue() <= 0);

        if (!result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        }
        return result;
    }

    private void copyFiles() {
        Properties properties = new Properties();
        properties.put("sdk.dir", settings.getSdkPath());
        try {
            FileOutputStream stream = new FileOutputStream(MenuBarController.localDefaultDirectory()+PROPERTIES_ANDROID_FILE);
            properties.store(stream, null);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File assets = new File(MenuBarController.localDefaultDirectory()+"android/app/assets");
        try {
            FileUtils.deleteDirectory(assets);
            assets.mkdirs();

            FileUtils.copyDirectory(new File(Config.BASE), assets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveSettings();

        // Copy keystore
        File key = new File(settings.getKeyPath());
        File outKey = new File(MenuBarController.localDefaultDirectory()+"android/app/android.keystore");

        try {
            FileUtils.copyFile(key, outKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppPref pref = Config.get().getPref();

        File out = new File(MenuBarController.localDefaultDirectory()+"android/"+pref.getFile().getName());
        pref.saveSignPref(out);

        File icon;
        int[] sizes = new int[]{36, 48, 72, 96, 144};
        String[] paths = new String[]{"ldpi", "mdpi", "hdpi", "xhdpi", "xxhdpi"};

        for (int i = 0; i < sizes.length; i++) {
            icon = Config.get().getIcon(sizes[i]);
            if (icon.exists()) {
                try {
                    FileUtils.copyFile(icon, new File(MenuBarController.localDefaultDirectory()+"android/app/res/drawable-"+paths[i]+"/ic_launcher.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            txtAndroidSdk.setText(selectedDirectory.getAbsolutePath());
            settings.setSdkPath(selectedDirectory.getAbsolutePath());
        }
    }

    public void selectKeyStore() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            txtKeyStorePath.setText(file.getAbsolutePath());
            settings.setKeyPath(txtKeyStorePath.getText());
        }
    }

    public void createKey() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ButtonType button = new ButtonType("Criar Chave com Portable");
        alert.getButtonTypes().setAll(button);

        if (alert.showAndWait().get() == button) {
            File out = new File(MenuBarController.localDefaultDirectory()+"portecle.zip");
            File path = new File(MenuBarController.localDefaultDirectory() + "portecle-1.7/");
            if (!out.exists()) {
                InputStream io = Main.class.getResourceAsStream("/portecle-1.7.zip");
                try {
                    FileUtils.copyInputStreamToFile(io, out);
                    io.close();

                    ZipFile zip = new ZipFile(out);
                    zip.extractAll(MenuBarController.localDefaultDirectory());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ZipException e) {
                    e.printStackTrace();
                }
            }
            stage.getScene().setCursor(Cursor.WAIT);
            ProcessBuilder builder = new ProcessBuilder("java", "-jar", "portecle.jar");
            builder.directory(path);
            try {
                Process run = builder.start();
                run.waitFor();
                stage.getScene().setCursor(Cursor.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void show() {
        stage.show();
//        Config.get().export();
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
                            endBuild();
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

    private void endBuild() {
        barStatus.setProgress(1);
        File file = new File(MenuBarController.localDefaultDirectory() + "android/app/build/outputs/apk/app-release.apk");
        if (file.exists()) {
            barStatus.setStyle("-fx-accent: green; ");
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Android App", "*.apk"));
            File save = chooser.showSaveDialog(stage);
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Ocorreu algum erro. Verifique se sua chave foi criada corretamente");
            alert.show();
            barStatus.setStyle("-fx-accent: red; ");
        }
        boxButton.getChildren().setAll(btnOk);
    }

    private class StreamGobbler extends Thread {

        InputStream is;
        String type;
        double count;

        private StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
            count = 0;
        }

        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textArea.clear();
                }
            });
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
                            barStatus.setProgress(count++ / 30 );
                        }
                    });
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
