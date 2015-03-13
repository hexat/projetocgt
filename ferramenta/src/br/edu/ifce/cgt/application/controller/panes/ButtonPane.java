package br.edu.ifce.cgt.application.controller.panes;

import java.io.File;
import java.io.IOException;

import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import cgt.hud.CGTButton;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by infolev on 19/02/15.
 */
public class ButtonPane extends GridPane {

    @FXML private TextField txtTextureUp;
    @FXML private TextField txtTextureDown;

    @FXML private Button btnTextureUp;
    @FXML private Button btnTextureDown;

    private CGTButton button;


    public ButtonPane() {
        this.button = null;
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/CGTButton.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setActions();
    }

    public void setButton(CGTButton button) {
        this.button = button;

        if (button.getTextureDown() != null) {
            txtTextureDown.setText(button.getTextureDown().getFile().getFilename());
        }

        if (button.getTextureUp() != null) {
            txtTextureUp.setText(button.getTextureUp().getFile().getFilename());
        }
    }

    private void setActions() {
        btnTextureUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = DialogsUtil.showOpenDialog("Selecionar Imagem", DialogsUtil.IMG_FILTER);

                if (file != null) {
                    if (button.getTextureUp() != null) {
                        Config.get().destroy(button.getTextureUp().getFile());
                        button.setTextureUp(null);
                    }
                    button.setTextureUp(new CGTTexture(Config.get().createImg(file)));

                    txtTextureUp.setText(file.getName());
                }
            }
        });

        btnTextureDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                File file = DialogsUtil.showOpenDialog("Selecionar Imagem", DialogsUtil.IMG_FILTER);

                if (file != null) {
                    if (button.getTextureDown() != null) {
                        Config.get().destroy(button.getTextureDown().getFile());
                        button.setTextureDown(null);
                    }
                    button.setTextureDown(new CGTTexture(Config.get().createImg(file)));

                    txtTextureDown.setText(file.getName());
                }
            }
        });
    }
}
