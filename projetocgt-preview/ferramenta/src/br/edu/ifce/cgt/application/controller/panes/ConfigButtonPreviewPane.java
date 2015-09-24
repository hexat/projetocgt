package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.vo.CGTButtonScreenPreview;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Edy Junior on 24/09/2015.
 */
public class ConfigButtonPreviewPane extends Accordion {

    @FXML
    public TextField textura;
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
    private Button p2;
    @FXML
    private Button p1;
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

    @FXML public void Search(ActionEvent actionEvent) {
        btn.getButton().setTextureUp();
    }

}
