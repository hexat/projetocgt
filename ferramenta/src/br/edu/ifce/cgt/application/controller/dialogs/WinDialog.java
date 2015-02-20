package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import cgt.game.CGTGameWorld;
import cgt.policy.WinPolicy;
import cgt.win.KillAllEnemies;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Luan on 16/02/2015.
 */
public class WinDialog extends BorderPane {

    private final Stage stage;
    private final CGTGameWorld world;

    @FXML private ComboBox<WinPolicy> boxCriteria;

    public WinDialog(CGTGameWorld world) {
        this.world = world;
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/Win.fxml"));
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

        boxCriteria.getItems().addAll(WinPolicy.values());
        boxCriteria.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateContent();
            }
        });
        boxCriteria.getSelectionModel().selectFirst();
    }

    private void updateContent() {
        switch (boxCriteria.getValue()) {
            case KILL_ENEMIES:
                try {
                    FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigKillAllEnemies.fxml"));
                    view.setController(this);
                    GridPane pane = view.load();
                    setCenter(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        stage.sizeToScene();
    }

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }

    public void addWin(ActionEvent actionEvent) {
        switch (boxCriteria.getValue()) {
            case KILL_ENEMIES:
                boolean contem = false;
                for (int i = 0; i < world.getWinCriteria().size() && !contem; i++) {
                    if (world.getWinCriteria().get(i).getPolicy() == WinPolicy.KILL_ENEMIES) {
                        contem = true;
                    }
                }
                if (!contem) {
                    world.addWinCriterion(new KillAllEnemies(world.getEnemies()));
                }
                break;

        }
        stage.getOnCloseRequest().handle(null);
        stage.close();
    }
}
