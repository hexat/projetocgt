package br.edu.ifce.cgt.application.controller;


import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.ExportDialog;
import br.edu.ifce.cgt.application.controller.dialogs.ListSpriteDialog;
import br.edu.ifce.cgt.application.controller.dialogs.SpriteSheetDialog;
import br.edu.ifce.cgt.application.controller.panes.ScreenTab;
import br.edu.ifce.cgt.application.controller.titleds.GameTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import cgt.screen.CGTWindow;
import cgt.util.CGTError;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.FileUtils;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainPane extends BorderPane {
    public static final String desktopJarPath = "desktop/desktop-1.0/lib/desktop-1.0.jar";
    public static final String desktopZipPath = "desktop/desktop.zip";

    public Menu menuRecent;
    public Menu menuSprite;
    public MenuItem menuRun;
    public MenuItem menuExport;
    @FXML
    private Button btnMyWorld;
    @FXML
    private Accordion configAccordion;
    @FXML
    private MenuItem menuExportar;
    @FXML
    private Tab tabGame;
    @FXML
    private TabPane tabFerramenta;
    private boolean running;

    public MainPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/Ferramenta.fxml"));
        xml.setController(this);
        xml.setRoot(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateRecent();
        menuSprite.setDisable(true);
        menuRun.setDisable(true);
        menuExport.setDisable(true);
        running = false;
        menuRun.setAccelerator(new KeyCodeCombination(KeyCode.F6));

        tabGame.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tabGame.isSelected()) {
                    configAccordion.getPanes().clear();
                    if (Config.isCreated()) {
                        configAccordion.getPanes().add(new GameTitledPane());
                        configAccordion.getPanes().get(0).setExpanded(true);
                    }
                }
            }
        });
    }

    @FXML
    public void createWorld() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o mundo")
                .message("Digite um nome para o mundo:")
                .showTextInput("Mundo");

        if (response.isPresent()) {
            String id = response.get().trim();
            CGTGameWorld world = Config.get().getGame().createWorld(id);
            if (world != null) {
                ScreenTab tab = new ScreenTab(world);
                tabFerramenta.getTabs().add(tab);
                tabFerramenta.getSelectionModel().select(tab);
                menuSprite.setDisable(false);
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
    public void createScreen() {
        Optional<String> response = Dialogs.create()
                .owner(Main.getApp())
                .title("Nome para o screen")
                .message("Digite um nome para o screen:")
                .showTextInput("Screen");

        if (response.isPresent()) {
            String id = response.get().trim();
            CGTScreen screen = Config.get().getGame().createScreen(id);
            if (screen != null) {
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
    public void novo() {
        Config.reset();
        TabPane tabFerramenta = (TabPane) Main.getApp().getScene().lookup("#tabFerramenta");
        Main.getApp().setTitle("Ceará Game Tools");
        tabFerramenta.getTabs().remove(1, tabFerramenta.getTabs().size());
    }

    @FXML
    public void abrir() {
        File file = DialogsUtil.showOpenDialog("Abrir projeto", DialogsUtil.CGT_FILTER);
        if (file != null) {
            Pref.load().addRecentProject(file.getAbsolutePath());
            Pref.load().save();
            updateRecent();
            open(file);
        }
    }

    private void updateRecent() {
        MenuItem item;
        menuRecent.getItems().clear();
        for (String path : Pref.load().getRecentProjects()) {
            final File file = new File(path);
            if (file.exists()) {
                item = new MenuItem(file.getName());
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        open(file);
                    }
                });
                menuRecent.getItems().add(item);
            }
        }
        if (menuRecent.getItems().size() > 0) {
            item = new MenuItem("Limpar");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Pref.load().getRecentProjects().clear();
                    Pref.load().save();
                    updateRecent();
                }
            });
            menuRecent.getItems().add(new SeparatorMenuItem());
            menuRecent.getItems().add(item);
        } else {
            item = new MenuItem("Vazio");
            item.setDisable(true);
            menuRecent.getItems().add(item);
        }
    }

    private void open(File open) {
        Main.getApp().getScene().setCursor(Cursor.WAIT);
        try {
            Config.unzip(open);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Problema ao abrir o arquivo");
            alert.showAndWait();
            return;
        }

        menuSprite.setDisable(false);
        menuRun.setDisable(false);
        menuExport.setDisable(false);
        TabPane tabFerramenta = (TabPane) Main.getApp().getScene().lookup("#tabFerramenta");

        Main.getApp().setTitle(open.getName());

        if (tabFerramenta.getTabs().size() > 1) {
            tabFerramenta.getTabs().remove(1, tabFerramenta.getTabs().size());
        }

        for (CGTWindow w : Config.get().getGame().getWindows()) {
            ScreenTab tab = new ScreenTab(w);

            tabFerramenta.getTabs().add(tab);
            tabFerramenta.getSelectionModel().select(tab);
        }
        Main.getApp().getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void salvar() {
        File save;
        if (Config.get().isLoaded()) {
            save = Config.get().getInputProjectFile();
        } else {
            save = DialogsUtil.showSaveDialog("Salvar projeto");
        }

        if (save != null) {

            Pref.load().addRecentProject(save.getAbsolutePath());
            Pref.load().save();

            try {
                Config.get().zip(save);
                Dialogs.create().owner(Main.getApp()).message(":)").title("Salvando Projeto").showInformation();
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void export() {
//        Config.get().export();
        List<CGTError> errors = Config.get().getGame().validate();

        if (errors.isEmpty()) {
            Main.getApp().getScene().setCursor(Cursor.WAIT);
            new ExportDialog().show();
            Main.getApp().getScene().setCursor(Cursor.DEFAULT);
        } else {
            showValidateDialog(errors);
        }
    }

    private void showValidateDialog(List<CGTError> errors) {
        String msg = "";
        ResourceBundle bundle = Pref.load().getBundle();

        for (CGTError e : errors) {
            msg += bundle.getString(e.getValidate().name()) + " em " + e.getId() + "." + '\n';
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText("Verifique os seguintes itens");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void run() {
        List<CGTError> errors = Config.get().getGame().validate();
        if (errors.isEmpty()) {
            Main.getApp().getScene().setCursor(Cursor.WAIT);
            Config.get().saveConfig();
            File base = Config.get().getProjectDir();

            try {
                File localZip = new File(localDefaultDirectory() + desktopZipPath);
                if (localZip.exists()) {
                    long foo = localZip.lastModified();
                    InputStream bar = Main.class.getResourceAsStream("/bin/desktop-1.0.zip");
                    File file = new File(localDefaultDirectory() + "tmp.zip");
                    FileUtils.copyInputStreamToFile(bar, file);
                    if (localZip.lastModified() != file.lastModified()) {
                        copyDesktopFiles();
                    }
                    file.delete();
                } else {
                    copyDesktopFiles();
                }

                ZipFile jar = new ZipFile(localDefaultDirectory() + desktopJarPath);
                for (File f : base.listFiles()) {
                    if (f.isDirectory()) {
                        jar.addFolder(f, new ZipParameters());
                    } else {
                        jar.addFile(f, new ZipParameters());
                    }
                }

                runDesktop();
            } catch (ZipException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.getApp().getScene().setCursor(Cursor.DEFAULT);
        } else {
            showValidateDialog(errors);
        }
    }

    private void runDesktop() {
        if (!running) {
            new Thread() {
                @Override
                public void run() {
                    running = true;
                    String path = localDefaultDirectory() + "desktop/desktop-1.0/bin/desktop";
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process p1;
                        if (isWin()) {
                            p1 = runtime.exec("\"" + path + ".bat\"");
                        } else {
                            runtime.exec("chmod +x " + path);
                            p1 = runtime.exec("sh " + path);
                        }
                        InputStream is = p1.getInputStream();
                        int i;
                        String res = "";
                        while ((i = is.read()) != -1) {
                            System.out.print((char) i);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    running = false;
                }
            }.start();
        }
    }

    public static boolean isWin() {
        String OS = System.getProperty("os.name").toUpperCase();
        return OS.contains("WIN");
    }

    @FXML
    public void addSpriteSheet() {
        SpriteSheetDialog dia = new SpriteSheetDialog(null);
        dia.show();
    }

    @FXML
    public void editSpriteSheet() {
        new ListSpriteDialog().show();
    }

    public void exit(ActionEvent actionEvent) {
        Main.getApp().getOnCloseRequest()
                .handle(
                        new WindowEvent(
                                Main.getApp(),
                                WindowEvent.WINDOW_CLOSE_REQUEST
                        )
                );

        Main.getApp().close();
    }

    public void saveAs(ActionEvent actionEvent) {
        File save = DialogsUtil.showSaveDialog("Salvar projeto");
        if (save != null) {
            try {
                Main.getApp().setTitle(save.getName());
                Config.get().zip(save);
                Dialogs.create().owner(Main.getApp()).message(":)").title("Salvando Projeto").showInformation();
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
    }

    private void copyDesktopFiles() {
        System.out.print("Copiando arquivos...");
        InputStream url = Main.class.getResourceAsStream("/bin/desktop-1.0.zip");
        File file = new File(localDefaultDirectory() + "desktop/desktop.zip");
        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        try {
            FileUtils.copyInputStreamToFile(url, file);
            url.close();

            ZipFile zipFile = new ZipFile(file);

            zipFile.extractAll(file.getParent());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        }
        System.out.println("fim.");
    }

//    private void copyLibDesktopFiles() {
//        if (!new File(localDefaultDirectory()+DESKTOP_JAR_PATH).exists()) {
//            copyDesktopFiles();
//        } else {
//            URL url = null;
//            for (String path : new String[]{cgtJarPath, coreJarPath}) {
//                url = Main.class.getResource("/bin/" + path);
//                if (url != null) {
//                    File file = new File(localDefaultDirectory() + path);
//                    file.delete();
//                    File input = null;
//                    try {
//                        input = new File(url.toURI());
//                        FileUtils.copyFile(input, file);
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }

    public static String localDefaultDirectory() {
        String path = "";

        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            path = System.getenv("APPDATA");
        } else {
            path = System.getProperty("user.home") + "/.local";
        }
        path += "/CGTData/";
        return path;
    }

    public void showInfo(ActionEvent event) {
        try {
            String url = "http://www.cgt.ifce.edu.br";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
