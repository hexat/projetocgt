package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.AppPref;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by infolev on 06/02/15.
 */
public class GameTitledPane extends TitledPane {
    @FXML public CheckBox chkDebug;
    @FXML private TextField txtVersion;
    @FXML private TextField txtGameName;
    @FXML private TextField txtGameId;
    @FXML private IntegerTextField txtVersionCode;
    @FXML private ImageView img32;
    @FXML private ImageView img48;
    @FXML private ImageView img72;
    @FXML private ImageView img96;
    @FXML private ImageView img144;
    @FXML private Button btn32;
    @FXML private Button btn48;
    @FXML private Button btn72;
    @FXML private Button btn96;
    @FXML private Button btn144;

    @FXML private ComboBox<String> boxWindows;

    private Map<Integer, ImageView> iconsMap;

    public GameTitledPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGame.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iconsMap = new HashMap<Integer, ImageView>();
        iconsMap.put(32, img32);
        iconsMap.put(48, img48);
        iconsMap.put(72, img72);
        iconsMap.put(96, img96);
        iconsMap.put(144, img144);


        init();
        updateIcons();
        setActions();
    }

    private void setActions() {
        final AppPref pref = Config.get().getPref();
        txtGameId.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    pref.setAppId(txtGameId.getText());
                    pref.save();
                }
            }
        });

        txtGameName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    pref.setApkName(txtGameName.getText());
                    pref.save();
                }
            }
        });

        txtVersion.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    pref.setVersionName(txtVersion.getText());
                    pref.save();
                }
            }
        });

        txtVersionCode.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    pref.setVersionCode(txtVersionCode.getValue());
                    pref.save();
                }
            }
        });

        chkDebug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Config.get().getGame().setDebug(chkDebug.isSelected());
            }
        });
    }

    private void init() {
        chkDebug.setSelected(Config.get().getGame().isDebug());
        boxWindows.getItems().clear();
        for (String s : Config.get().getGame().getIds()) {
            boxWindows.getItems().add(s);
        }
        if (Config.get().getGame().getStartWindow() != null) {
            boxWindows.getSelectionModel().select(Config.get().getGame().getStartWindow().getId());
        }
        AppPref pref = Config.get().getPref();
        txtVersionCode.setValue(pref.getVersionCode());
        txtVersion.setText(pref.getVersionName());
        txtGameName.setText(pref.getApkName());
        txtGameId.setText(pref.getAppId());
    }

    @FXML public void setWindowDefault() {
        String id = boxWindows.getSelectionModel().getSelectedItem();
        System.out.println(id);
        if (id != null) {
            Config.get().getGame().setStartWindowId(id);
        }
    }

    public void setImg(ActionEvent event) {
        Config config = Config.get();
        int size = Integer.parseInt(((Node) event.getSource()).getId());
        if (iconsMap.get(size).getImage() == null) {
            File file = DialogsUtil.showOpenDialog("Selecione Icone", DialogsUtil.IMG_FILTER);
            if (file != null) {
                File imgFile = config.createIcon(file, size);
                Image image = new Image("file:"+imgFile.getAbsolutePath());
                iconsMap.get(size).setImage(image);
            }
            ((Button)event.getSource()).setText("Remover");
        } else {
            File file = config.getIcon(size);
            if (file.exists()) {
                file.delete();
                iconsMap.get(size).setImage(null);
                ((Button)event.getSource()).setText("Selecionar");
            }
        }
    }

    private void updateIcons() {
        File aux;
        for (Map.Entry<Integer, ImageView> e : iconsMap.entrySet()) {
            aux = Config.get().getIcon(e.getKey());
            if (aux.exists()) {
                if (e.getValue().getImage() == null) {
                    e.getValue().setImage(new Image("file:" + aux.getAbsolutePath()));
                }
                getButton(e.getKey()).setText("Remover");
            } else {
                e.getValue().setImage(null);
                getButton(e.getKey()).setText("Selecionar");
            }
        }
    }

    private Button getButton(int size) {
        switch (size) {
            case 32: return btn32;
            case 48: return btn48;
            case 72: return btn72;
            case 96: return btn96;
            case 144: return btn144;
        }
        return null;
    }
}
