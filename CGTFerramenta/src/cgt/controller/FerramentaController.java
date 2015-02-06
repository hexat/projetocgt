package cgt.controller;


import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.CGTGameWorld;
import cgt.CGTScreen;
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
import util.DialogsUtil;

public class FerramentaController implements Initializable
{
  
   
    @FXML
    private Button btnMyWorld;
    @FXML
    private Accordion configAccordion;
    @FXML
    private MenuItem menuExportar;

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
            final CGTGameWorld world = Config.getGame().createWorld(id);
            if (world != null) {
                Tab aba = new Tab(response.get());
                aba.setOnCloseRequest(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        Action response = Dialogs.create()
                                .owner(Main.getApp())
                                .title("Excluir mundo")
                                .masthead("Ao fechar esta aba você esterá removendo este mundo do jogo.")
                                .message("Tem certeza que deseja fazer isso?")
                                .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
                                .showConfirm();

                        if (response == Dialog.ACTION_OK) {
                            Config.getGame().removeWorld(world);
                            if (tabFerramenta.getTabs().contains(event.getSource())) {
                                tabFerramenta.getTabs().remove(event.getSource());
                            }
                        } else {

                        }
                        event.consume();
                    }
                });
                aba.setContent(WorldController.getNode(world));
                tabFerramenta.getTabs().add(aba);
                tabFerramenta.getSelectionModel().select(aba);
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
            final CGTScreen screen = Config.getGame().createScreen(id);
            if (screen != null) {
                Tab aba = new Tab(response.get());
                aba.setOnCloseRequest(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        Action response = Dialogs.create()
                                .owner(Main.getApp())
                                .title("Excluir mundo")
                                .masthead("Ao fechar esta aba você esterá removendo este mundo do jogo.")
                                .message("Tem certeza que deseja fazer isso?")
                                .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
                                .showConfirm();

                        if (response == Dialog.ACTION_OK) {
                            Config.getGame().removeScreen(screen);
                            if (tabFerramenta.getTabs().contains(event.getSource())) {
                                tabFerramenta.getTabs().remove(event.getSource());
                            }
                        } else {

                        }
                        event.consume();
                    }
                });
                aba.setContent(ScreenController.getNode(screen));
                tabFerramenta.getTabs().add(aba);
                tabFerramenta.getSelectionModel().select(aba);
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

    @FXML public void testeBackGame() {
        File back = DialogsUtil.showOpenDialog("Escolha background");

        Config.export();
    }
    @FXML
    public void click() {

    }
}
