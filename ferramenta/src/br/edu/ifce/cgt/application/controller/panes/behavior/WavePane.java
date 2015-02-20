package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import cgt.behaviors.Behavior;
import cgt.behaviors.SineWave;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class WavePane extends GridPane implements BehaviorPane {
    @FXML public TextField txtAmplitude;
    @FXML public TextField txtFreq;
    @FXML public TextField txtPhase;
    @FXML public TextField txtMax;

    public WavePane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/SineWaveBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Behavior getBehavior() {
        double ap = Double.parseDouble(txtAmplitude.getText());
        double fr = Double.parseDouble(txtFreq.getText());
        double ph = Double.parseDouble(txtPhase.getText());
        SineWave res = new SineWave(ap, fr, ph);
        res.setMaxX(Integer.parseInt(txtMax.getText()));

        return res;
    }
}
