package cgt.controller;

import application.Config;
import application.Main;
import cgt.controller.screen.ButtonPane;
import cgt.controller.screen.HudPane;
import cgt.hud.CGTButtonScreen;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import util.DialogsUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luan on 18/02/2015.
 */
public class ConfigButtonScreenController extends TitledPane {
    private final CGTButtonScreen buttonScreen;

    @FXML private ComboBox<String> boxWindows;

    @FXML private ButtonPane buttonControl;

    @FXML private HudPane hudControl;

    public ConfigButtonScreenController(final CGTButtonScreen buttonScreen) {
        this.buttonScreen = buttonScreen;
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

        hudControl.getTFRelativeX().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonScreen.setRelativeX(hudControl.getRelativeX());
            }
        });

        hudControl.getTFRelativeY().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonScreen.setRelativeY(hudControl.getRelativeY());
            }
        });

        hudControl.getTFRelativeWidth().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonScreen.setRelativeWidth(hudControl.getRelativeWidth());
            }
        });

        hudControl.getTFRelativeHeight().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonScreen.setRelativeHeight(hudControl.getRelativeHeight());
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

        if (buttonScreen.getRelativeY() > 0) {
            hudControl.getTFRelativeY().setText(buttonScreen.getRelativeY() + "");
        }
        if (buttonScreen.getRelativeX() > 0) {
            hudControl.getTFRelativeX().setText(buttonScreen.getRelativeX()+"");
        }

        if (buttonScreen.getRelativeHeight() > 0) {
            hudControl.getTFRelativeHeight().setText(buttonScreen.getRelativeHeight()+"");
        }

        if (buttonScreen.getRelativeWidth() > 0) {
            hudControl.getTFRelativeWidth().setText(buttonScreen.getRelativeHeight()+"");
        }

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
}
