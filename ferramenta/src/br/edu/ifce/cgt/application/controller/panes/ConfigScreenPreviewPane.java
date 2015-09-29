package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.titleds.ScreenTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import cgt.util.CGTSound;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

/**
 * Created by Edy Junior on 15/09/2015.
 */
public class ConfigScreenPreviewPane extends AnchorPane {
    @FXML
    public TextField txtSound;
    @FXML
    private TextField txtBackScreen;
    @FXML
    private TextField txtScreenId;
    @FXML
    private CGTScreen screen;
    @FXML
    private VBox btnBox;
    private Runnable onUpdateRunnable;

    public ConfigScreenPreviewPane(CGTScreen screen) {
        FXMLLoader xml2 = new FXMLLoader(Main.class.getResource("/view/ConfigScreen2.fxml"));
        xml2.setController(this);
        xml2.setRoot(this);

        try {
            xml2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.screen = screen;
        this.btnBox = new VBox(30);
        //this.camera = getScreen().getCamera();

        //init();
    }

    public ConfigScreenPreviewPane(CGTScreen screen, Runnable onUpdateRunnable) {
        this(screen);
        this.onUpdateRunnable = onUpdateRunnable;
    }

    public void setScreen(CGTScreen screen) {
        this.screen = screen;
        txtScreenId.setText(screen.getId());
        if (screen.getBackground() != null) {
            txtBackScreen.setText(screen.getBackground().getFile().getFilename());
        }
    }

    public CGTScreen getScreen() {
        return screen;
    }

    @FXML public void setSound(ActionEvent actionEvent) {
        File file = DialogsUtil.showOpenDialog("Selecione audio", DialogsUtil.WAV_FILTER);
        if (file != null) {
            if (screen.getSound() != null) {
                Config.get().destroy(screen.getSound().getFile());
            }

            try {
                CGTSound sound = new CGTSound(Config.get().createAudio(file));
                screen.setSound(sound);
                txtSound.setText(file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public VBox getBtnBox(){
        return  btnBox;
    }

    @FXML
    public void btnChooseBackScreen() {
        File chosenFile = DialogsUtil.showOpenDialog("Selecionar background", DialogsUtil.IMG_FILTER);

        String path = "";

        if (chosenFile != null) {
            screen.setBackground(new CGTTexture(Config.get().createImg(chosenFile)));
            path = chosenFile.getName();
        }

        txtBackScreen.setText(path);

        if (onUpdateRunnable != null)
            onUpdateRunnable.run();
    }
}
