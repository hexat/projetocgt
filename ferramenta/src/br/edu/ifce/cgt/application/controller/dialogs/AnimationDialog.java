package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.controller.panes.ImagePane;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.game.CGTSpriteSheet;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import cgt.core.CGTGameObject;
import cgt.policy.StatePolicy;
import cgt.core.CGTAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    @FXML private IntegerTextField txtFrameInitialX;
    @FXML private IntegerTextField txtFrameFinalX;
    @FXML private IntegerTextField txtFrameInitialY;
    @FXML private IntegerTextField txtFrameFinalY;
    @FXML private CheckBox chkFlipHor;
    @FXML private CheckBox chkFlipVertical;
    @FXML private ComboBox<EnumMap<Animation.PlayMode>> boxAnimationPolicy;
    @FXML private ComboBox<String> boxSprite;
    @FXML private ComboBox<EnumMap<StatePolicy>> boxStates;

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

        List<EnumMap<Animation.PlayMode>> listModes = new ArrayList<EnumMap<Animation.PlayMode>>();

        for (Animation.PlayMode p : Animation.PlayMode.values()) {
            listModes.add(new EnumMap<Animation.PlayMode>(p, bundle.getString(p.name())));
        }

        boxAnimationPolicy.getItems().setAll(listModes);
        boxAnimationPolicy.getSelectionModel().selectFirst();



        boxStates.getItems().setAll(getListActorStates());
        boxStates.getSelectionModel().selectFirst();
    }

    public AnimationDialog(CGTAnimation animation) {
        this(animation.getOwner());

        this.animation = animation;
        boxSprite.getSelectionModel().select(animation.getSpriteSheet().getId());
        int i = 0;
        while (i < boxAnimationPolicy.getItems().size() &&
                boxAnimationPolicy.getItems().get(i).getKey() != animation.getAnimationPolicy()) i++;
        if (i < boxAnimationPolicy.getItems().size()) {
            boxAnimationPolicy.getSelectionModel().select(i);
        }
        i = 0;
        while (i < boxStates.getItems().size() &&
                boxStates.getItems().get(i).getKey() != animation.getActorStage()) i++;
        if (i < boxStates.getItems().size()) {
            boxStates.getSelectionModel().select(i);
        }
        txtFrameInitialX.setValue(animation.getInitialFrame().x);
        txtFrameInitialY.setValue(animation.getInitialFrame().y);
        txtFrameFinalX.setValue(animation.getEndingFrame().x);
        txtFrameFinalY.setValue(animation.getEndingFrame().y);
        txtVel.setValue(animation.getSpriteVelocity());

        chkFlipHor.setSelected(animation.isFlipHorizontal());
        chkFlipVertical.setSelected(animation.isFlipVertical());
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
                animation = new CGTAnimation();
            }
            animation.setSpriteSheet(boxSprite.getSelectionModel().getSelectedItem());
            animation.setAnimationPolicy(boxAnimationPolicy.getSelectionModel().getSelectedItem().getKey());
            animation.setFlipHorizontal(chkFlipHor.isSelected());
            animation.setFlipVertical(chkFlipVertical.isSelected());
            animation.setSpriteVelocity(txtVel.getValue());
            animation.setInitialFrame(new Vector2(txtFrameInitialX.getValue(),
                    txtFrameInitialY.getValue()));
            animation.setEndingFrame(new Vector2(txtFrameFinalX.getValue(),
                    txtFrameFinalY.getValue()));
            animation.setActorStage(boxStates.getValue().getKey());
            object.addAnimation(animation);
            dialogStage.close();
        }
    }

    private boolean validate() {
        return txtVel.getValue() > 0 && txtFrameFinalX.getValue() >= 0 &&
                txtFrameFinalY.getValue() >= 0 && txtFrameInitialX.getValue() >= 0 &&
                txtFrameInitialY.getValue() >= 0;
    }

    public void showAndWait() {
        dialogStage.showAndWait();
    }

    public static List<EnumMap<StatePolicy>> getListActorStates() {
        ResourceBundle bundle = Pref.load().getBundle();

        List<EnumMap<StatePolicy>> list = new ArrayList<EnumMap<StatePolicy>>();

        for (StatePolicy s : StatePolicy.values()) {
            list.add(new EnumMap<StatePolicy>(s, bundle.getString(s.name())));
        }
        return list;
    }
}
