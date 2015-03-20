package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ActorDeadPane;
import br.edu.ifce.cgt.application.controller.panes.LoseCriteriaPane;
import br.edu.ifce.cgt.application.controller.panes.TargetTimePane;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.game.CGTGameWorld;
import cgt.game.LoseCriteria;
import cgt.lose.LifeDepleted;
import cgt.policy.LosePolicy;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class LoseDialog extends BorderPane {

    private final Stage stage;
    private final CGTGameWorld world;

    @FXML private ComboBox<EnumMap<LosePolicy>> boxCriteria;

    public LoseDialog(CGTGameWorld world) {
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

        List<EnumMap<LosePolicy>> list = new ArrayList<EnumMap<LosePolicy>>();
        ResourceBundle bundle = Pref.load().getBundle();
        for (LosePolicy p : LosePolicy.values()) {
            list.add(new EnumMap<LosePolicy>(p, bundle.getString(p.name())));
        }

        boxCriteria.getItems().setAll(list);
        boxCriteria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EnumMap<LosePolicy>>() {
            @Override
            public void changed(ObservableValue<? extends EnumMap<LosePolicy>> observable, EnumMap<LosePolicy> oldValue, EnumMap<LosePolicy> newValue) {
                updateContent();
            }
        });
        boxCriteria.getSelectionModel().selectFirst();
    }

    private void updateContent() {
        switch (boxCriteria.getValue().getKey()) {
            case TARGET_TIME:
                setCenter(new TargetTimePane());
                break;
            case ACTOR_DEAD:
                setCenter(new ActorDeadPane(world.getActor()));
                break;
        }
        stage.sizeToScene();
    }

    public Stage getStage() {
        return stage;
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public void addWin(ActionEvent actionEvent) {

        world.addLoseCriterion(((LoseCriteriaPane)getCenter()).getCriteria());

        stage.close();
    }
}
