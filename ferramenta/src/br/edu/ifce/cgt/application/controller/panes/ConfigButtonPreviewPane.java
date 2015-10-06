package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.vo.CGTButtonScreenPreview;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

/**
 * Created by Edy Junior on 24/09/2015.
 */
public class ConfigButtonPreviewPane extends Accordion {

    @FXML
    private TextField texture;
    @FXML
    private TextField texPres;
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

        choices.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btn.getButton().setScreenToGo(choices.getValue());
            }
        });

        if (btn.getButton().getScreenToGo() != null) {
            choices.getSelectionModel().select(btn.getButton().getScreenToGo().getId());
        }
    }

    @FXML public void Search1(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem do botao", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            btn.getButton().setTextureUp(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
        }

        texture.setText(path);

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

        texPres.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }

    @FXML public void Save(ActionEvent actionEvent) {
        if(RelX.getValue() >= 0 && RelY.getValue() >= 0 && WRel.getValue() > 0 && HRel.getValue() > 0){
            btn.getButton().setRelativeX(RelX.getValue());
            btn.getButton().setRelativeY(RelY.getValue());
            btn.getButton().setRelativeWidth(WRel.getValue());
            btn.getButton().setRelativeHeight(HRel.getValue());
            System.out.println("\nCRIOU\n");
        }else
            System.out.println("\nProblema\n");
    }

    public TextField getTexture(){
        return this.texture;
    }
    public TextField getTextPress(){
        return this.texPres;
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
        return  this.choices;
    }

}
