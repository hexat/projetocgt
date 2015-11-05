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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;

/**
 * Created by Edy Junior on 24/09/2015.
 */
public class ConfigButtonPreviewPane extends StackPane {

    @FXML
    private TextField textUp;
    @FXML
    private TextField textPres;
    @FXML
    private FloatTextField hRel;
    @FXML
    private FloatTextField wRel;
    @FXML
    private FloatTextField relX;
    @FXML
    private FloatTextField relY;
    @FXML
    private ComboBox<String> choices;
    private CGTButtonScreenPreview btn;
    private Runnable onUpdateRunnable;
    private double x = 0, y = 1.0f, w = 0.2f, h = 0.2f;

    public ConfigButtonPreviewPane(CGTButtonScreenPreview btn) {
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

    public ConfigButtonPreviewPane(CGTButtonScreenPreview btn, Runnable onUpdateRunnable) {
        this(btn);
        this.onUpdateRunnable = onUpdateRunnable;
    }

    private void init() {
        choices.setOnMouseClicked(e -> {
            choices.getItems().clear();
            choices.getItems().addAll(Config.get().getGame().getIds());
        });

        relX.setValue(0.0f);
        relY.setValue(1.0f);
        wRel.setValue(0.2f);
        hRel.setValue(0.2f);

        choices.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btn.getObject().setScreenToGo(choices.getValue());
            }
        });

        if (btn.getObject().getScreenToGo() != null) {
            choices.getSelectionModel().select(btn.getObject().getScreenToGo().getId());
        }

        relX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && relX.getValue() >= 0 && relX.getValue() <= 1 - wRel.getValue()) {
                    btn.getImage().setX(relX.getValue() * btn.getImage().getWidthBCKG());
                    btn.getObject().setRelativeX(relX.getValue());
                    x = relX.getValue();
                } else if (!newValue) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Coordenadas fora dos limites");
                    alert.setContentText("Intervalo aceito: [0 ; " + (1 - wRel.getValue()) + "]");
                    alert.show();
                    btn.getObject().setRelativeX((float) x);
                    relX.setValue(x);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });

        relY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && relY.getValue() >= hRel.getValue() && relY.getValue() <= 1) {
                    btn.getImage().setY((1 - relY.getValue()) * btn.getImage().getHeightBCKG());
                    btn.getObject().setRelativeY(1 - relY.getValue());
                    y = relY.getValue();
                } else if (!newValue) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Coordenadas fora dos limites");
                    alert.setContentText("Intervalo aceito: [" + hRel.getValue() + " ; 1]");
                    alert.show();
                    btn.getObject().setRelativeY(1.0f - (float) y);
                    relY.setValue(y);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
        wRel.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && wRel.getValue() >= 0 && wRel.getValue() <= 1 - relX.getValue()) {
                    btn.getObject().setRelativeWidth(wRel.getValue());
                    w = wRel.getValue();
                } else if (!newValue) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Largura fora dos limites");
                    alert.setContentText("Intervalo aceito: [0 ; " + (1 - relX.getValue()) + "]");
                    alert.show();
                    btn.getObject().setRelativeWidth((float) (w));
                    wRel.setValue(w);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
        hRel.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && hRel.getValue() >= 0 && hRel.getValue() <= relY.getValue()) {
                    btn.getObject().setRelativeHeight(hRel.getValue());
                    h = hRel.getValue();
                } else if (!newValue) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Largura fora dos limites");
                    alert.setContentText("Intervalo aceito: [0 ; " + relY.getValue() + "]");
                    alert.show();
                    btn.getObject().setRelativeHeight((float) (h));
                    hRel.setValue(h);
                }
                if (onUpdateRunnable != null)
                    onUpdateRunnable.run();
            }
        });
    }

    @FXML
    public void searchTextureUp(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem do botao", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            btn.getObject().setTextureUp(new CGTTexture(Config.get().createImg(chosenFile)));
            Image img = Config.get().getImage(btn.getObject().getTextureUp().getFile().getFile().getName());
            btn.getImage().setImage(img);
            path = chosenFile.getName();
        }

        textUp.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }

    @FXML
    public void searchTexturePressed(ActionEvent actionEvent) {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar imagem do botao pressionado", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            btn.getObject().setTextureDown(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
        }

        textPres.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }

    public TextField getTextUp() {
        return this.textUp;
    }

    public TextField getTextPress() {
        return this.textPres;
    }

    public FloatTextField getRelX() {
        return this.relX;
    }

    public FloatTextField getRelY() {
        return this.relY;
    }

    public FloatTextField getWRel() {
        return this.wRel;
    }

    public FloatTextField getHRel() {
        return this.hRel;
    }

    public ComboBox<String> getScreens() {
        return this.choices;
    }

}
