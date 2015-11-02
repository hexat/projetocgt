package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import cgt.game.LifeBar;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luan on 21/02/2015.
 */
public class LifebarPane extends VBox {
    @FXML public Button btnBarBack;
    @FXML public Button btnBarTexture;
    @FXML public HudPane hudPane;

    private LifeBar lifeBar;

    public LifebarPane(LifeBar lifeBar) {
        this.lifeBar = lifeBar;

        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/panes/Lifebar.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        hudPane.setHud(lifeBar);

        init();

        setActions();
    }

    private void setActions() {
        btnBarBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = DialogsUtil.showOpenDialog("Select Background", DialogsUtil.IMG_FILTER);
                if (file != null) {
                    if (lifeBar.getBackgroundBar() == null) {
                        lifeBar.setBackgroundBar(new CGTTexture(Config.get().createImg(file)));
                        btnBarBack.setText(file.getName());
                    } else {
                        Config.get().destroy(lifeBar.getBackgroundBar().getFile());
                        lifeBar.setBackgroundBar(null);
                        btnBarBack.setText("Select");
                    }
                }
            }
        });

        btnBarTexture.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = DialogsUtil.showOpenDialog("Select Bar Texture", DialogsUtil.IMG_FILTER);
                if (file != null) {
                    if (lifeBar.getBar() == null) {
                        lifeBar.setBar(new CGTTexture(Config.get().createImg(file)));
                        btnBarTexture.setText(file.getName());
                    } else {
                        Config.get().destroy(lifeBar.getBar().getFile());
                        lifeBar.setBar(null);
                        btnBarTexture.setText("Select");
                    }
                }
            }
        });
    }

    private void init() {
        if (lifeBar != null) {
            if (lifeBar.getBackgroundBar() != null) {
                btnBarBack.setText(lifeBar.getBackgroundBar().getFile().getFilename());
            }
            if (lifeBar.getBar() != null) {
                btnBarTexture.setText(lifeBar.getBar().getFile().getFilename());
            }
        }
    }

    public HudPane getHudPane(){ return this.hudPane; }
}
