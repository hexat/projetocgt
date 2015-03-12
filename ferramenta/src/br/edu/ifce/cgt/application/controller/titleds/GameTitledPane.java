package br.edu.ifce.cgt.application.controller.titleds;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by infolev on 06/02/15.
 */
public class GameTitledPane extends TitledPane {
    @FXML private TextField txtVersion;
    @FXML private TextField txtGameName;
    @FXML private TextField txtGameId;
    @FXML private TextField txtVersionCode;
    @FXML private ImageView img32;
    @FXML private ImageView img48;
    @FXML private ImageView img72;
    @FXML private ImageView img96;
    @FXML private ImageView img144;

    @FXML private ComboBox<String> boxWindows;

    public GameTitledPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGame.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        boxWindows.getItems().clear();
        for (String s : Config.getGame().getIds()) {
            boxWindows.getItems().add(s);
        }
        if (Config.getGame().getStartWindow() != null) {
            boxWindows.getSelectionModel().select(Config.getGame().getStartWindow().getId());
        }
    }

    @FXML public void setWindowDefault() {
        String id = boxWindows.getSelectionModel().getSelectedItem();
        System.out.println(id);
        if (id != null) {
            Config.getGame().setStartWindowId(id);
        }
    }

    public void setImg(MouseEvent event) {
        if (event.getClickCount() == 2) {
            File file = DialogsUtil.showOpenDialog("Selecione Icone", DialogsUtil.IMG_FILTER);

            if (file != null) {
                Class<? extends GameTitledPane> c = this.getClass();

                Field f = null;
                try {
                    f = c.getDeclaredField("img"+((Node)event.getSource()).getId());
                    f.setAccessible(true);

                    ImageView img = (ImageView) f.get(this);
                    img.setImage(new Image("file:"+file.getAbsolutePath()));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
