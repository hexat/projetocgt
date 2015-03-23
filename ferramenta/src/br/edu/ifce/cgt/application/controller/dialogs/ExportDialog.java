package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.MenuBarController;
import br.edu.ifce.cgt.application.util.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by luanjames on 25/02/15.
 */
public class ExportDialog extends BorderPane implements Observer {
    private final Stage stage;
    private final Label labStatus;
    private TextArea textArea;
    private Button btnOk;

    private ProcessBuilder processBuilder;
    private Process process;

    public ExportDialog() {
        this(Main.getApp().getScene().getWindow());
    }

    public ExportDialog(Window owner) {
        setPrefSize(500, 100);
        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        labStatus = new Label();
        labStatus.setAlignment(Pos.CENTER);
        labStatus.setText("teste");
        setCenter(labStatus);
        textArea = new TextArea();
        setBottom(textArea);

        btnOk = new Button();
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                unZip();
                runGradle();
            }
        });
        setRight(btnOk);
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

    @Override
    public void update(Observable o, Object arg) {
        final String txt = arg.toString();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labStatus.setText(txt);
            }
        });
    }

    public void show() {
        stage.show();
        Config.get().export(this);
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
                        process = new ProcessBuilder("sh " + path, comm).start();
//                        process = Runtime.getRuntime().exec("sh " + path + " "+comm);
                    }
                    System.out.println("init"+process.toString());
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
