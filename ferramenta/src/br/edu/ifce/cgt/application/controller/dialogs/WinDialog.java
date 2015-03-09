package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.*;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.game.CGTGameWorld;
import cgt.policy.WinPolicy;
import cgt.win.KillAllEnemies;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Luan on 16/02/2015.
 */
public class WinDialog extends BorderPane {

    private final Stage stage;
    private final CGTGameWorld world;

    @FXML private ComboBox<EnumMap<WinPolicy>> boxCriteria;

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

        ResourceBundle bundle = Pref.load().getBundle();
        List<EnumMap<WinPolicy>> list = new ArrayList<EnumMap<WinPolicy>>();

        for (WinPolicy w : WinPolicy.values()) {
            list.add(new EnumMap<WinPolicy>(w, bundle.getString(w.name())));
        }

        boxCriteria.getItems().setAll(list);
        boxCriteria.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateContent();
            }
        });
        boxCriteria.getSelectionModel().selectFirst();
    }

    private void updateContent() {
        switch (boxCriteria.getValue().getKey()) {
            case KILL_ENEMIES:
                setCenter(new KillAllPane(world));
                break;
            case SURVIVE:
                setCenter(new SurvivePane());
                break;
            case COMPLETE_CROSSING:
                setCenter(new CompleteCrossingPane());
                break;
            case TARGET_SCORE:
                setCenter(new TargetScorePane());
                break;
            case GET_ALL_BONUS:
                setCenter(new GetAllBonusPane(world));
                break;
        }
        setMargin(getCenter(), new Insets(5, 0, 5, 0));
        stage.sizeToScene();
    }

    public Stage getStage() {
        return stage;
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public void addWin(ActionEvent actionEvent) {
        world.addWinCriterion( ( (WinCriteriaPane) getCenter() ).getCriteria() );
        stage.close();
    }
}
