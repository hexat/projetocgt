package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                runGradle();
            }
        });
        setRight(btnOk);
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
                try {
                    process = Runtime.getRuntime().exec("./gradlew android:build");

                    StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");

                    // any output?
                    StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

                    // start gobblers
                    outputGobbler.start();
                    errorGobbler.start();

                    process.waitFor();

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
