package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.vo.CGTEnemyGroupLifeBarDrawable;
import br.edu.ifce.cgt.application.vo.CGTLifeBarDrawable;
import cgt.core.CGTGameObject;
import cgt.util.CGTTexture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

/**
 * Created by Edy Junior on 10/11/2015.
 */
public class ConfigGroupLifePane extends Accordion {

    @FXML
    private TextField textBar;
    @FXML
    private TextField textback;
    @FXML
    private FloatTextField HRel;
    @FXML
    private FloatTextField WRel;
    @FXML
    private FloatTextField RelX;
    @FXML
    private FloatTextField RelY;
    @FXML
    private ComboBox<String> choices;
    private CGTEnemyGroupLifeBarDrawable life;
    private Runnable onUpdateRunnable;
    private double x = 0, y = 1.0f, w = 0.2f, h = 0.2f;

    public ConfigGroupLifePane(CGTEnemyGroupLifeBarDrawable life){
        FXMLLoader xml2 = new FXMLLoader(Main.class.getResource("/view/ConfigGroupLifeBar.fxml"));
        xml2.setController(this);
        xml2.setRoot(this);

        try {
            xml2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.life = life;

        init();
    }

    public ConfigGroupLifePane(CGTEnemyGroupLifeBarDrawable life, Runnable onUpdateRunnable){
        this(life);
        this.onUpdateRunnable = onUpdateRunnable;
    }

    private void init() {
        choices.setOnMouseClicked(e-> {
            choices.getItems().clear();
            choices.getItems().addAll(Config.get().getGame().getEnemiesGroup());
        });
        RelX.setValue(0.0f);
        RelY.setValue(1.0f);
        WRel.setValue(0.2f);
        HRel.setValue(0.2f);

        choices.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CGTGameObject ob = Config.get().getGame().findObject(choices.getValue().toString());
                //life.getLife().setOwner(ob.getId());
            }
        });

        /*if (life.getLife() != null) {
            choices.getSelectionModel().select(life.getLife().getOwnerId());
        }*/
        RelX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && RelX.getValue() >= 0 && RelX.getValue() <= 1 - WRel.getValue()) {
                    life.getDraggable().setX(RelX.getValue() * life.getDraggable().getWidthBCKG());
                    life.getLife().setRelativeX(RelX.getValue());
                    x = RelX.getValue();
                }

                else if(!newValue){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Coordenadas fora dos limites");
                    alert.setContentText("Intervalo aceito: [0 ; " + (1 - WRel.getValue()) + "]");
                    alert.show();
                    life.getLife().setRelativeX((float)x);
                    RelX.setValue(x);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
        RelY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && RelY.getValue() >= HRel.getValue() && RelY.getValue() <= 1) {
                    life.getDraggable().setY((1 - RelY.getValue()) * life.getDraggable().getHeightBCKG());
                    life.getLife().setRelativeY(1 - RelY.getValue());
                    y = RelY.getValue();
                }
                else if(!newValue){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Coordenadas fora dos limites");
                    alert.setContentText("Intervalo aceito: [" + HRel.getValue() + " ; 1]");
                    alert.show();
                    life.getLife().setRelativeY(1.0f - (float)y);
                    RelY.setValue(y);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
        WRel.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && WRel.getValue() >= 0 && WRel.getValue() <= 1 - RelX.getValue()) {
                    life.getLife().setRelativeWidth(WRel.getValue());
                    w = WRel.getValue();
                }
                else if(!newValue){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Largura fora dos limites");
                    alert.setContentText("Intervalo aceito: [0 ; " + (1 - RelX.getValue()) + "]");
                    alert.show();
                    life.getLife().setRelativeWidth((float)(w));
                    WRel.setValue(w);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
        HRel.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && HRel.getValue() <= RelY.getValue()) {
                    life.getLife().setRelativeHeight(HRel.getValue());
                    h = HRel.getValue();
                }
                else if(!newValue){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Largura fora dos limites");
                    alert.setContentText("Intervalo aceito: [0 ; " + RelY.getValue() + "]");
                    alert.show();
                    life.getLife().setRelativeHeight((float)(h));
                    HRel.setValue(h);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
    }

    @FXML public void Search1(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem da barra de vida", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            life.getLife().setBar(new CGTTexture(Config.get().createImg(chosenFile)));
            Image img = Config.get().getImage(life.getLife().getBar().getFile().getFile().getName());
            life.getDraggable().setImage(img);
            path = chosenFile.getName();
            life.getLife().setBar(new CGTTexture(Config.get().createImg(chosenFile)));
        }

        textBar.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }
    @FXML public void Search2(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem de fundo", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            life.getLife().setBackgroundBar(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
            life.getLife().setBackgroundBar(new CGTTexture(Config.get().createImg(chosenFile)));
        }

        textback.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }

    public TextField getTextBar(){
        return this.textBar;
    }
    public TextField getTextBack(){
        return this.textback;
    }
    public FloatTextField getRelX(){
        return this.RelX;
    }
    public FloatTextField getRelY(){
        return this.RelY;
    }
    public FloatTextField getWRel(){
        return this.WRel;
    }
    public FloatTextField getHRel(){
        return this.HRel;
    }

    public ComboBox<String> getChoices(){
        return this.choices;
    }
}
