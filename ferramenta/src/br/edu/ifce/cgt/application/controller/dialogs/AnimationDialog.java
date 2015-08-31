package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ImagePane;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.EnumMap;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.core.CGTAnimation;
import cgt.core.CGTGameObject;
import cgt.game.CGTSpriteSheet;
import cgt.policy.StatePolicy;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML private VBox panStates;

    private List<EnumMap<StatePolicy>> statePolicies;

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
        boxSprite.getItems().setAll(Config.get().getGame().getSpriteDB().findAllId());
        boxSprite.getSelectionModel().selectFirst();

        if (!boxSprite.getItems().isEmpty()) {
            updateImgPane();
        }

        boxSprite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateImgPane();
            }
        });



        ResourceBundle bundle = Pref.load().getBundle();

        List<EnumMap<Animation.PlayMode>> listModes = new ArrayList<EnumMap<Animation.PlayMode>>();

        for (Animation.PlayMode p : Animation.PlayMode.values()) {
            listModes.add(new EnumMap<Animation.PlayMode>(p, bundle.getString(p.name())));
        }

        boxAnimationPolicy.getItems().setAll(listModes);
        boxAnimationPolicy.getSelectionModel().select(2);

        statePolicies = new ArrayList<EnumMap<StatePolicy>>();

        boxStates.getItems().setAll(getListActorStates());
        boxStates.getSelectionModel().selectFirst();
    }

    private void updateImgPane() {
        CGTSpriteSheet sheet = Config.get().getGame().getSpriteDB().find(boxSprite.getValue());
        imgPane.setTexture(sheet.getTexture());
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

        Iterator<StatePolicy> itr = animation.getStatesIterator();
        boolean found;
        while (itr.hasNext()) {
            StatePolicy item = itr.next();
            found = false;
            for (i = 0; i < boxStates.getItems().size() && !found; i++) {
                if (boxStates.getItems().get(i).getKey() == item) {
                    found = true;
                    statePolicies.add(boxStates.getItems().get(i));
                    boxStates.getItems().remove(i);
                }
            }
        }
        updateStates();
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
        List<String> list = validate();
        if (list.isEmpty()) {
            if (animation == null) {
                animation = new CGTAnimation();
                object.addAnimation(animation);
            }
            animation.cleanActorStates();
            for (EnumMap<StatePolicy> p : statePolicies) {
                animation.addActorState(p.getKey());
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
            dialogStage.close();
        } else {
            String msg = "";
            for (String s : list) {
                msg += s + '\n';
            }

            Dialogs.create().title("Problemas").message(msg).showWarning();
        }
    }

    private List<String> validate() {
        List<String> errors = new ArrayList<String>();
        ResourceBundle bundle = Pref.load().getBundle();
        if (txtVel.getValue() <= 0) {
            errors.add(bundle.getString("vel_not_neg"));
        }

        if (txtFrameFinalX.getValue() < 0) {
            errors.add(bundle.getString("frame_final_x_not_neg"));
        }

        if (txtFrameFinalY.getValue() < 0) {
            errors.add(bundle.getString("frame_final_y_not_neg"));
        }

        if (txtFrameInitialX.getValue() < 0) {
            errors.add(bundle.getString("frame_initial_x_not_neg"));
        }

        if (txtFrameInitialY.getValue() < 0) {
            errors.add(bundle.getString("frame_initial_y_not_neg"));
        }

        if (boxSprite.getSelectionModel().isEmpty()) {
            errors.add(bundle.getString("sprite_not_void"));
        }

        if (statePolicies.isEmpty()) {
            errors.add(bundle.getString("state_actor_not_void"));
        }

        return errors;
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

    public void addState() {
        if (!boxStates.getSelectionModel().isEmpty()) {
            EnumMap<StatePolicy> el = boxStates.getSelectionModel().getSelectedItem();
            statePolicies.add(el);
            boxStates.getItems().remove(el);
            updateStates();
            dialogStage.sizeToScene();
        }
    }

    private void updateStates() {
        panStates.getChildren().clear();
        if (statePolicies.size() > 0) {
            for (final EnumMap<StatePolicy> s : statePolicies) {
                ItemViewPane pane = new ItemViewPane(s.getValue());
                pane.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        statePolicies.remove(s);
                        boxStates.getItems().add(s);
                        updateStates();
                        dialogStage.sizeToScene();
                    }
                });
                panStates.getChildren().add(pane);
            }
        } else {
            panStates.getChildren().add(new Label("Adicione pelo menos um Estado"));
        }
    }
}
