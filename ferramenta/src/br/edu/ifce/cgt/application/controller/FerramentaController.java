package br.edu.ifce.cgt.application.controller;


import br.edu.ifce.cgt.application.controller.titleds.GameTitledPane;
import org.controlsfx.dialog.Dialogs;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.controller.panes.ScreenTab;

public class FerramentaController implements Initializable {
    @FXML
    private Button btnMyWorld;
    @FXML
    private Accordion configAccordion;
    @FXML
    private MenuItem menuExportar;

    @FXML private Tab tabGame;

    @FXML private TabPane tabFerramenta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        menuExportar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Config.export();
//                System.out.println("Tetstet ");
//            }
//        });
        tabGame.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tabGame.isSelected()) {
                    configAccordion.getPanes().clear();
                    configAccordion.getPanes().add(new GameTitledPane());
                }
            }
        });
    }

    @FXML
    public void clickMyWorld() {
        configAccordion.getPanes().clear();
        TitledPane accordion = null;

        try {
            accordion = FXMLLoader.load(getClass().getResource("/view/ConfigWorld.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (accordion != null) {
            configAccordion.getPanes().add(accordion);
        }
    }

    @FXML public void createWorld() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o mundo")
                .message("Digite um nome para o mundo:")
                .showTextInput("Mundo ");

        if (response.isPresent()) {
            String id = response.get().trim();
            CGTGameWorld world = Config.getGame().createWorld(id);
            if (world != null) {
                ScreenTab tab = new ScreenTab(world);
                tabFerramenta.getTabs().add(tab);
                tabFerramenta.getSelectionModel().select(tab);
            } else {
                Dialogs.create()
                        .owner(Main.getApp())
                        .title("Atenção")
                        .message("Já existe uma janela com este ID!")
                        .showWarning();
            }
        }
    }

    @FXML public void createScreen() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o screen")
                .message("Digite um nome para o screen:")
                .showTextInput("Screen");

        if (response.isPresent()) {
            String id = response.get().trim();
            CGTScreen screen = Config.getGame().createScreen(id);
            if (screen != null) {
//                Tab aba = new Tab(response.get());
//                aba.setOnCloseRequest(new EventHandler<Event>() {
//                    @Override
//                    public void handle(Event event) {
//                        Action response = Dialogs.create()
//                                .owner(Main.getApp())
//                                .title("Excluir mundo")
//                                .masthead("Ao fechar esta aba você esterá removendo este mundo do jogo.")
//                                .message("Tem certeza que deseja fazer isso?")
//                                .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
//                                .showConfirm();
//
//                        if (response == Dialog.ACTION_OK) {
//                            Config.getGame().removeScreen(screen);
//                            if (tabFerramenta.getTabs().contains(event.getSource())) {
//                                tabFerramenta.getTabs().remove(event.getSource());
//                            }
//                        } else {
//
//                        }
//                        event.consume();
//                    }
//                });
//                aba.setContent(ScreenController.getNode(screen));
//                aba.setOnSelectionChanged(new EventHandler<Event>() {
//                    @Override
//                    public void handle(Event event) {
//                        if (((Tab) event.getSource()).isSelected()) {
//                            configAccordion.getPanes().clear();
//                            configAccordion.getPanes().add(ConfigScreenController.getNode(screen));
//                        }
//                    }
//                });
                ScreenTab tab = new ScreenTab(screen);
                tabFerramenta.getTabs().add(tab);
                tabFerramenta.getSelectionModel().select(tab);
            } else {
                Dialogs.create()
                        .owner(Main.getApp())
                        .title("Atenção")
                        .message("Já existe uma janela com este ID!")
                        .showWarning();
            }
        }

    }
    
    @FXML
    public void menuExportarAction() {
        System.out.println("ok");
    }

    @FXML
    public void click() {

    }

    private class Teste {
        private final String APK_NAME="APK_NAME";
        private final String VERSION_NAME="VERSION_NAME";
        private final String VERSION_CODE="VERSION_CODE";
        private final String APPID="APPID";

        private String apkName;
        private String versionName;
        private int versionCode;
        private String appId;

        public Teste() {
            Properties prefs = new Properties();
            File file = new File("prefs.properties");
            if (file.exists()) {
                try {
                    prefs.load(new FileInputStream(file));
                    apkName = prefs.getProperty(APK_NAME);
                    versionCode = Integer.parseInt(prefs.getProperty(VERSION_CODE));
                    versionName = prefs.getProperty(VERSION_NAME);
                    appId = prefs.getProperty(APPID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public String getApkName() {
            return apkName;
        }

        public void setApkName(String apkName) {
            this.apkName = apkName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        private void save() {
            Properties prefs = new Properties();

            prefs.put(APK_NAME, getApkName());
            prefs.put(VERSION_NAME, getVersionName());
            prefs.put(VERSION_CODE, getVersionCode());
            prefs.put(APPID, getAppId());

            try {
                prefs.store(new FileOutputStream(new File("prefs.properties")), null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
