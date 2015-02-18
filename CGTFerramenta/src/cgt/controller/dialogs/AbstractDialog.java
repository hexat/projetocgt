package cgt.controller.dialogs;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public abstract class AbstractDialog <T extends Parent> {
    private T window;
    private final Stage stage;

    public AbstractDialog(String name, T obj) {
        window = obj;
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/"+name+".fxml"));
        view.setRoot(window);
        view.setController(this);

        try {
            window = view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(window));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());
    }

    protected T getWindow() {
        return window;
    }

    public abstract void addWin();

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
