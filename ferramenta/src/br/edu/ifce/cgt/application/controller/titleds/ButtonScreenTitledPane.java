package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.ButtonPane;
import br.edu.ifce.cgt.application.controller.panes.HudPane;
import cgt.hud.CGTButtonScreen;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import br.edu.ifce.cgt.application.util.DialogsUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class ButtonScreenTitledPane extends TitledPane {
    private final CGTButtonScreen buttonScreen;

    @FXML private ComboBox<String> boxWindows;

    @FXML private ButtonPane buttonControl;

    @FXML private HudPane hudControl;

    public ButtonScreenTitledPane(CGTButtonScreen button) {
        this.buttonScreen = button;

        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigButtonScreen.fxml"));
        view.setRoot(this);
        view.setController(this);
        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        init();

        boxWindows.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonScreen.setScreenToGo(boxWindows.getValue());
            }
        });

        buttonControl.getBtnTextureDown().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setTextureDown();
            }
        });

        buttonControl.getBtnTextureUp().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setTextureUp();
            }
        });
    }

    private void init() {
        boxWindows.getItems().setAll(Config.getGame().getIds());

        hudControl.setHud(buttonScreen);

        if (buttonScreen.getScreenToGo() != null) {
            boxWindows.getSelectionModel().select(buttonScreen.getScreenToGo().getId());
        }

        if (buttonScreen.getTextureDown() != null) {
            buttonControl.getTxtTextureDown().setText(buttonScreen.getTextureDown().getFile().getFilename());
        }

        if (buttonScreen.getTextureUp() != null) {
            buttonControl.getTxtTextureUp().setText(buttonScreen.getTextureUp().getFile().getFilename());
        }
    }

    public void setTextureUp() {
        File file = DialogsUtil.showOpenDialog("Selecionar Imagem", DialogsUtil.IMG_FILTER);

        if (file != null) {
            if (buttonScreen.getTextureUp() != null) {
                Config.destroy(buttonScreen.getTextureUp().getFile());
                buttonScreen.setTextureUp(null);
            }
            buttonScreen.setTextureUp(new CGTTexture(Config.createImg(file)));

            buttonControl.getTxtTextureUp().setText(file.getName());
        }
    }

    public void setTextureDown() {
        File file = DialogsUtil.showOpenDialog("Selecionar Imagem", DialogsUtil.IMG_FILTER);

        if (file != null) {
            if (buttonScreen.getTextureDown() != null) {
                Config.destroy(buttonScreen.getTextureDown().getFile());
                buttonScreen.setTextureDown(null);
            }
            buttonScreen.setTextureDown(new CGTTexture(Config.createImg(file)));

            buttonControl.getTxtTextureDown().setText(file.getName());
        }
    }

    public CGTButtonScreen getButtonScreen() {
        return buttonScreen;
    }

    public void commit() {
        hudControl.commit();
    }

    public ComboBox<String> getBoxWindows() {
        return boxWindows;
    }
}
