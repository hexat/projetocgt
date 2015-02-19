package application.controller.dialogs;

import java.io.File;
import java.io.IOException;

import application.Config;
import application.Main;
import application.controller.panes.ButtonPane;
import application.controller.panes.HudPane;
import cgt.hud.CGTButton;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import application.util.DialogsUtil;

/**
 * Created by infolev on 19/02/15.
 */
public class ButtonDialog extends BorderPane {

    private final Stage stage;

    @FXML private ButtonPane buttonControl;
    @FXML private HudPane hudControl;

    private CGTButton button;

    public ButtonDialog(CGTButton button, Window owner) {
        this.button = button;

        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/CGTButtonDialog.fxml"));
        view.setRoot(this);
        view.setController(this);


        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);

        if (button == null) {
            this.button = new CGTButton();
        } else {
            init();
        }

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
        if (button.getRelativeY() > 0) {
            hudControl.getTFRelativeY().setText(button.getRelativeY() + "");
        }
        if (button.getRelativeX() > 0) {
            hudControl.getTFRelativeX().setText(button.getRelativeX()+"");
        }

        if (button.getRelativeHeight() > 0) {
            hudControl.getTFRelativeHeight().setText(button.getRelativeHeight()+"");
        }

        if (button.getRelativeWidth() > 0) {
            hudControl.getTFRelativeWidth().setText(button.getRelativeHeight()+"");
        }

        if (button.getTextureDown() != null) {
            buttonControl.getTxtTextureDown().setText(button.getTextureDown().getFile().getFilename());
        }

        if (button.getTextureUp() != null) {
            buttonControl.getTxtTextureUp().setText(button.getTextureUp().getFile().getFilename());
        }
    }

    public void add() {
        button.setRelativeX(hudControl.getRelativeX());
        button.setRelativeY(hudControl.getRelativeY());
        button.setRelativeWidth(hudControl.getRelativeWidth());
        button.setRelativeHeight(hudControl.getRelativeHeight());

        stage.close();
    }

    public void setTextureUp() {
        File file = DialogsUtil.showOpenDialog("Selecionar Imagem", DialogsUtil.IMG_FILTER);

        if (file != null) {
            if (button.getTextureUp() != null) {
                Config.destroy(button.getTextureUp().getFile());
                button.setTextureUp(null);
            }
            button.setTextureUp(new CGTTexture(Config.createImg(file)));

            buttonControl.getTxtTextureUp().setText(file.getName());
        }
    }

    public void setTextureDown() {
        File file = DialogsUtil.showOpenDialog("Selecionar Imagem", DialogsUtil.IMG_FILTER);

        if (file != null) {
            if (button.getTextureDown() != null) {
                Config.destroy(button.getTextureDown().getFile());
                button.setTextureDown(null);
            }
            button.setTextureDown(new CGTTexture(Config.createImg(file)));

            buttonControl.getTxtTextureDown().setText(file.getName());
        }
    }

    public CGTButton getButton() {
        return button;
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
