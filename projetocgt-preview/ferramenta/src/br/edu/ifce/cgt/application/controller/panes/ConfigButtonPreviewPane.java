package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.vo.CGTButtonScreenPreview;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

/**
 * Created by Edy Junior on 24/09/2015.
 */
public class ConfigButtonPreviewPane extends Accordion {

    @FXML
    public TextField texture;
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
    /*@FXML
    private Button p2;
    @FXML
    private Button p1;*/
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
    }

    public ConfigButtonPreviewPane(CGTButtonScreenPreview btn, Runnable onUpdateRunnable){
        this(btn);
        this.onUpdateRunnable = onUpdateRunnable;
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

    @FXML public void BX(ActionEvent actionEvent) {
        if(RelX.getValue() != 0)
            btn.getButton().setRelativeX(RelX.getValue());
    }
    @FXML public void BY(ActionEvent actionEvent) {
        if(RelY.getValue() != 0)
            btn.getButton().setRelativeY(RelY.getValue());
    }
    @FXML public void BW(ActionEvent actionEvent) {
        if(WRel.getValue() != 0)
            btn.getButton().setRelativeWidth(WRel.getValue());
    }@FXML public void BH(ActionEvent actionEvent) {
        if(HRel.getValue() != 0)
            btn.getButton().setRelativeHeight(HRel.getValue());
    }


}
