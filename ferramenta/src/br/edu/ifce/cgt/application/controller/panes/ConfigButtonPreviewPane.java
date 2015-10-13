package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.vo.CGTButtonScreenPreview;
import cgt.util.CGTTexture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

/**
 * Created by Edy Junior on 24/09/2015.
 */
public class ConfigButtonPreviewPane extends Accordion {

    @FXML
    private TextField textUp;
    @FXML
    private TextField textPres;
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
    private CGTButtonScreenPreview btn;
    private Runnable onUpdateRunnable;
    private double x = 0, y = 1.0f, w = 0.2f, h = 0.2f;

    public ConfigButtonPreviewPane(CGTButtonScreenPreview btn){
        FXMLLoader xml2 = new FXMLLoader(Main.class.getResource("/view/ConfigButtonScreen2.fxml"));
        xml2.setController(this);
        xml2.setRoot(this);

        try {
            xml2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.btn = btn;

        init();
    }

    public ConfigButtonPreviewPane(CGTButtonScreenPreview btn, Runnable onUpdateRunnable){
        this(btn);
        this.onUpdateRunnable = onUpdateRunnable;
    }

    private void init() {
        choices.setOnMouseClicked(e-> {
            choices.getItems().clear();
            choices.getItems().addAll(Config.get().getGame().getIds());
        });
        RelX.setValue(0.0f);
        RelY.setValue(1.0f);
        WRel.setValue(0.2f);
        HRel.setValue(0.2f);

        choices.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btn.getButton().setScreenToGo(choices.getValue());
            }
        });

        if (btn.getButton().getScreenToGo() != null) {
            choices.getSelectionModel().select(btn.getButton().getScreenToGo().getId());
        }
        RelX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && RelX.getValue() >= 0 && RelX.getValue() <= 1 - WRel.getValue()) {
                    System.out.println("Mexe\n");
                    btn.getImage().setX(RelX.getValue() * btn.getImage().getWidthBCKG());
                    btn.getButton().setRelativeX(RelX.getValue());
                    x = RelX.getValue();
                }

                else if(!newValue){
                    System.out.println("ERROR");
                    btn.getButton().setRelativeX((float)x);
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
                    btn.getImage().setY((1 - RelY.getValue()) * btn.getImage().getHeightBCKG());
                    btn.getButton().setRelativeY(1 - RelY.getValue());
                    y = RelY.getValue();
                }
                else if(!newValue){
                    System.out.println("ERROR");
                    btn.getButton().setRelativeY(1.0f - (float)y);
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
                    btn.getButton().setRelativeWidth(WRel.getValue());
                    w = WRel.getValue();
                }
                else if(!newValue){
                    System.out.println("ERROR");
                    btn.getButton().setRelativeWidth((float)(w));
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
                    btn.getButton().setRelativeHeight(HRel.getValue());
                    h = HRel.getValue();
                }
                else if(!newValue){
                    System.out.println("ERROR");
                    btn.getButton().setRelativeHeight((float)(h));
                    HRel.setValue(h);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
    }

    @FXML public void Search1(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem do botao", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            btn.getButton().setTextureUp(new CGTTexture(Config.get().createImg(chosenFile)));
            Image img = Config.get().getImage(btn.getButton().getTextureUp().getFile().getFile().getName());
            btn.getImage().setImage(img);
            path = chosenFile.getName();
        }

        textUp.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }
    @FXML public void Search2(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem do botao pressionado", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            btn.getButton().setTextureDown(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
        }

        textPres.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }

    public TextField getTextUp(){
        return this.textUp;
    }
    public TextField getTextPress(){
        return this.textPres;
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

    public ComboBox<String> getScreens(){
        return this.choices;
    }

}
