package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.controller.panes.ImagePane;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Mapa;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.game.CGTSpriteSheet;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import cgt.core.CGTGameObject;
import cgt.policy.StatePolicy;
import cgt.core.CGTAnimation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by infolev on 10/02/15.
 */
public class AnimationDialog extends HBox {

    private final Stage dialogStage;
    @FXML public ImagePane imgPane;

    private CGTGameObject object;
    private CGTAnimation animation;

    @FXML private FloatTextField txtVel;
    @FXML private IntegerTextField txtFrameInicialX;
    @FXML private IntegerTextField txtFrameFinalX;
    @FXML private IntegerTextField txtFrameInicialY;
    @FXML private IntegerTextField txtFrameFinalY;
    @FXML private CheckBox chkFlipHor;
    @FXML private CheckBox chkFlipVertical;
    @FXML private ComboBox<Mapa<Animation.PlayMode, String>> boxAnimationPolicy;
    @FXML private ComboBox<String> boxSprite;
    @FXML private ComboBox<Mapa<StatePolicy, String>> boxStates;

    public AnimationDialog(CGTGameObject object) {
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

        imgPane.setSize(256, 256);

        boxSprite.getItems().setAll(Config.getGame().getSpriteDB().findAllId());

        boxSprite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CGTSpriteSheet sheet = Config.getGame().getSpriteDB().find(boxSprite.getValue());
                imgPane.setTexture(sheet.getTexture());
            }
        });

        boxSprite.getSelectionModel().selectFirst();

        ResourceBundle bundle = Pref.load().getBundle();

        List<Mapa<Animation.PlayMode, String>> listModes = new ArrayList<Mapa<Animation.PlayMode, String>>();

        for (Animation.PlayMode p : Animation.PlayMode.values()) {
            listModes.add(new Mapa<Animation.PlayMode, String>(p, bundle.getString(p.name())));
        }

        boxAnimationPolicy.getItems().setAll(listModes);
        boxAnimationPolicy.getSelectionModel().selectFirst();


        List<Mapa<StatePolicy, String>> list = new ArrayList<Mapa<StatePolicy, String>>();

        for (StatePolicy s : StatePolicy.values()) {
            list.add(new Mapa<StatePolicy, String>(s, bundle.getString(s.name())));
        }

        boxStates.getItems().setAll(list);
        boxStates.getSelectionModel().selectFirst();
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
                animation.setAnimationPolicy(boxAnimationPolicy.getSelectionModel().getSelectedItem().getKey());
                animation.setFlipHorizontal(chkFlipHor.isSelected());
                animation.setFlipVertical(chkFlipVertical.isSelected());
                animation.setSpriteVelocity(txtVel.getValue());
                animation.setInitialFrame(new Vector2(txtFrameInicialX.getValue(),
                        txtFrameInicialX.getValue()));
                animation.setEndingFrame(new Vector2(txtFrameFinalX.getValue(),
                        txtFrameFinalY.getValue()));
                object.addAnimation(boxStates.getValue().getKey(), animation);
                dialogStage.getOnCloseRequest().handle(null);
                dialogStage.close();
            }
        }
    }

    private boolean validate() {
        return txtVel.getValue() > 0 && txtFrameFinalX.getValue() >= 0 &&
                txtFrameFinalY.getValue() >= 0 && txtFrameInicialX.getValue() >= 0 &&
                txtFrameInicialY.getValue() >= 0;
    }

    public void show() {
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
}
