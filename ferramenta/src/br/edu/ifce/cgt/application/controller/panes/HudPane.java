package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import cgt.hud.HUDComponent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class HudPane extends GridPane {
    private HUDComponent hud;

    @FXML private FloatTextField txtRelX;
    @FXML private FloatTextField txtRelY;
    @FXML private FloatTextField txtWid;
    @FXML private FloatTextField txtHei;

    public HudPane() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigHudComponent.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setHandler();
    }

    private void setHandler() {
        txtRelX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hud.setRelativeX(txtRelX.getValue());
            }
        });

        txtRelY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hud.setRelativeY(txtRelY.getValue());
            }
        });

        txtWid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hud.setRelativeWidth(txtWid.getValue());
            }
        });

        txtHei.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hud.setRelativeHeight(txtHei.getValue());
            }
        });
    }

    public void commit() {
        hud.setRelativeX(txtRelX.getValue());
        hud.setRelativeY(txtRelY.getValue());
        hud.setRelativeWidth(txtWid.getValue());
        hud.setRelativeHeight(txtHei.getValue());
    }

    public void setHud(HUDComponent hud) {
        this.hud = hud;
        init();
    }

    public void init() {
        if (hud != null) {
            if (hud.getRelativeY() > 0) {
                txtRelY.setText(hud.getRelativeY() + "");
            }
            if (hud.getRelativeX() > 0) {
                txtRelX.setText(hud.getRelativeX() + "");
            }

            if (hud.getRelativeHeight() > 0) {
                txtHei.setText(hud.getRelativeHeight() + "");
            }

            if (hud.getRelativeWidth() > 0) {
                txtWid.setText(hud.getRelativeWidth() + "");
            }
        }
    }
}
