package cgt.controller;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.List;

import application.Config;
import application.Main;
import cgt.core.CGTGameObject;
import cgt.policy.StatePolicy;
import cgt.core.CGTAnimation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by infolev on 10/02/15.
 */
public class ConfigAnimationController extends GridPane {

    private final Stage dialogStage;

    private CGTGameObject object;
    private CGTAnimation animation;


    @FXML private TextField txtVel;
    @FXML private TextField txtFrameInicialX;
    @FXML private TextField txtFrameFinalX;
    @FXML private TextField txtFrameInicialY;
    @FXML private TextField txtFrameFinalY;
    @FXML private CheckBox chkFlipHor;
    @FXML private CheckBox chkFlipVertical;
    @FXML private ComboBox<Animation.PlayMode> boxAnimationPolicy;
    @FXML private ComboBox<String> boxSprite;
    @FXML private ComboBox<StatePolicy> boxStates;

    public ConfigAnimationController(CGTGameObject object) {
        this.object = object;

        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigAnimation.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogStage = new Stage();
        dialogStage.setScene(new Scene(this));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(Main.getApp().getScene().getWindow());


        List<String> list = Config.getGame().getSpriteDB().findAllId();

        boxSprite.getItems().setAll(FXCollections.observableList(list));
        boxSprite.getSelectionModel().selectFirst();
        boxAnimationPolicy.getSelectionModel().selectFirst();
        boxAnimationPolicy.getItems().setAll(Animation.PlayMode.values());
        boxAnimationPolicy.getSelectionModel().selectFirst();
        boxStates.getItems().setAll(StatePolicy.values());
        boxStates.getSelectionModel().selectFirst();
        txtFrameInicialX.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("") && newValue.matches("\\d*")) {
                    int value = Integer.parseInt(newValue);
                } else {
                    txtFrameInicialX.setText(oldValue);
                }
            }
        });
    }

    public void setObject(CGTGameObject object) {
        this.object = object;
    }

    public CGTGameObject getObject() {
        return object;
    }

    public void addAnimation() {
        if (validate()) {
            if (animation == null) {
                animation = new CGTAnimation(boxSprite.getSelectionModel().getSelectedItem());
                animation.setAnimationPolicy(boxAnimationPolicy.getSelectionModel().getSelectedItem());
                animation.setFlipHorizontal(chkFlipHor.isSelected());
                animation.setFlipVertical(chkFlipVertical.isSelected());
                animation.setSpriteVelocity(Float.parseFloat(txtVel.getText()));
                animation.setInitialFrame(new Vector2(Integer.parseInt(txtFrameInicialX.getText()),
                        Integer.parseInt(txtFrameInicialX.getText())));
                animation.setEndingFrame(new Vector2(Integer.parseInt(txtFrameFinalX.getText()),
                        Integer.parseInt(txtFrameFinalY.getText())));
                object.addAnimation(boxStates.getValue(), animation);
                dialogStage.getOnCloseRequest().handle(null);
                dialogStage.close();
            }
        }
    }

    private boolean validate() {
        return !txtVel.getText().equals("") && !txtFrameFinalX.getText().equals("") &&
                !txtFrameFinalY.getText().equals("") && !txtFrameInicialX.getText().equals("") &&
                !txtFrameInicialY.getText().equals("");
    }

    public void show() {
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
}
