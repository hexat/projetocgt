package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.Main;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.Zip4jUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by luanjames on 25/02/15.
 */
public class ExportDialog extends BorderPane implements Observer {
    private final Stage stage;
    private final Label labStatus;

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
}
