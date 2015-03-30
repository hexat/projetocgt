package br.edu.ifce.cgt.application.controller.panes.behavior;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.behaviors.SineWave;
import cgt.core.AbstractBehavior;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class WavePane extends GridPane implements BehaviorPane {
    @FXML public FloatTextField txtAmplitude;
    @FXML public FloatTextField txtFreq;
    @FXML public FloatTextField txtPhase;
    @FXML public IntegerTextField txtMax;
    private SineWave result;

    public WavePane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/behavior/SineWaveBehavior.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = null;
    }

    @Override
    public AbstractBehavior getBehavior() {
        double ap = txtAmplitude.getValue();
        double fr = txtFreq.getValue();
        double ph = txtPhase.getValue();
        if (result == null) {
            result = new SineWave();
        }
        result.setAmplitude(ap);
        result.setFrequency(fr);
        result.setPhase(ph);
        result.setMaxX(txtMax.getValue());

        return result;
    }

    @Override
    public void setBehavior(AbstractBehavior behavior) {
        result = (SineWave) behavior;

        txtAmplitude.setValue(result.getAmplitude());
        txtFreq.setValue(result.getFrequency());
        txtMax.setValue(result.getMaxX());
        txtPhase.setValue(result.getPhase());

    }
}
