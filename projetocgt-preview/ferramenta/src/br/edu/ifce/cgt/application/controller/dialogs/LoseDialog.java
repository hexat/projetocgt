package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ActorDeadPane;
import br.edu.ifce.cgt.application.controller.panes.LoseCriteriaPane;
import br.edu.ifce.cgt.application.controller.panes.TargetTimePane;
import cgt.game.CGTGameWorld;
import cgt.policy.LosePolicy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoseDialog extends BorderPane {

    private final Stage stage;
    private final CGTGameWorld world;
    private boolean result;

    public LoseDialog(CGTGameWorld world, LosePolicy policy) {
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

        switch (policy) {
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

    public void addCriteria(ActionEvent actionEvent) {
        world.addLoseCriterion(((LoseCriteriaPane) getCenter()).getCriteria());
        this.result = true;
        stage.close();
    }

    public boolean getResult() {
        return this.result;
    }
}
