package br.edu.ifce.cgt.application.controller;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.ExportDialog;
import br.edu.ifce.cgt.application.controller.dialogs.ListSpriteDialog;
import br.edu.ifce.cgt.application.controller.dialogs.SpriteSheetDialog;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.Pref;
import br.edu.ifce.cgt.application.vo.*;
import cgt.core.CGTActor;
import cgt.core.CGTBonus;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.game.CGTGame;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import cgt.game.LifeBar;
import cgt.hud.CGTButtonScreen;
import cgt.hud.IndividualLifeBar;
import cgt.util.CGTError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PreviewPane extends BorderPane {

    public static final String DESKTOP_JAR_PATH = "desktop/desktop-1.0/lib/desktop-1.0.jar";
    public static final String DESKTOP_ZIP_PATH = "desktop/desktop.zip";

    @FXML
    public Pane drawableObjectPane;

    @FXML
    public Pane drawableConfigurationsPane;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private TreeView<DrawableObject> tree;

    @FXML
    private CGTProjectDrawable rootItem;

    private boolean running;

    public PreviewPane() {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/Ferramenta2.fxml"));
        xml.setController(this);
        xml.setRoot(this);

        try {
            xml.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setupTree();
        this.updateRecent();
    }

    private void setupTree() {
        tree.setEditable(true);
        tree.setCellFactory(new Callback<TreeView<DrawableObject>, TreeCell<DrawableObject>>() {
            @Override
            public TreeCell<DrawableObject> call(TreeView<DrawableObject> param) {
                return new DrawableObjectTreeCellImpl();
            }
        });

        rootItem = new CGTProjectDrawable("Projeto", this.drawableObjectPane, this.drawableConfigurationsPane);
        TreeItem<DrawableObject> root = new TreeItem<>(rootItem);
        root.setExpanded(true);
        tree.setRoot(root);
    }

    @FXML
    public void newProject() {
        Config.reset();
        this.tree = new TreeView<>();
        this.setupTree();
        this.drawableObjectPane.getChildren().removeAll();
        this.drawableConfigurationsPane.getChildren().removeAll();
    }

    @FXML
    public void openProject() {
        File file = DialogsUtil.showOpenDialog("Abrir projeto", DialogsUtil.CGT_FILTER);
        if (file != null) {
            Pref.load().addRecentProject(file.getAbsolutePath());
            Pref.load().save();
            updateRecent();

            open(file);
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

//        TabPane tabFerramenta = (TabPane) Main.getApp().getScene().lookup("#tabFerramenta");

        Main.getApp().setTitle(open.getName());
        updateTree();
        Main.getApp().getScene().setCursor(Cursor.DEFAULT);
    }

    private void updateTree() { //TODO terminar
        CGTGame game = Config.get().getGame();
        this.tree.getRoot().getChildren().clear();
        for (CGTGameWorld w : game.getWorlds()) {
            DrawableObject<CGTGameWorld> worldDrawable = new CGTGameWorldDrawable(w, this.drawableObjectPane, this.drawableConfigurationsPane);
            TreeItem<DrawableObject> worldTreeItem = new TreeItem<>(worldDrawable);
            this.tree.getRoot().getChildren().add(worldTreeItem);

            // add actor
            CGTGameActorDrawable actor = new CGTGameActorDrawable(w.getActor(), w.getId(), this.drawableObjectPane, this.drawableConfigurationsPane);
            worldTreeItem.getChildren().add(new TreeItem<>(actor));

            // add enemies
            for (CGTEnemy e : w.getEnemies()) {
                CGTGameEnemyDrawable enemyDrawableObject = new CGTGameEnemyDrawable(e, w.getId(), this.drawableObjectPane, this.drawableConfigurationsPane);
                TreeItem<DrawableObject> enemyTreeItem = new TreeItem<>(enemyDrawableObject);
                worldTreeItem.getChildren().add(enemyTreeItem);
            }

            for (CGTBonus b : w.getBonus()) {
                CGTGameBonusDrawable bonus = new CGTGameBonusDrawable(b, w.getId(), this.drawableObjectPane, this.drawableConfigurationsPane);
                worldTreeItem.getChildren().add(new TreeItem<>(bonus));
            }
        }

        for (CGTScreen s : game.getScreens()) {
            DrawableObject<CGTScreen> screenDrawable = new CGTGameScreenDrawable(s, this.drawableObjectPane, this.drawableConfigurationsPane);
            TreeItem<DrawableObject> screenItem = new TreeItem<>(screenDrawable);
            this.tree.getRoot().getChildren().add(screenItem);

            for (CGTButtonScreen bs : s.getButtons()) {
                CGTButtonScreenPreview btnScreen = new CGTButtonScreenPreview(bs, this.drawableObjectPane, this.drawableConfigurationsPane);
                screenItem.getChildren().add(new TreeItem<>(btnScreen));
            }
        }
    }

    @FXML
    public void saveProject() {
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
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void saveProjectAs() {
        File save = DialogsUtil.showSaveDialog("Salvar projeto");

        if (save != null) {
            try {
                Main.getApp().setTitle(save.getName());
                Config.get().zip(save);
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void exportProject() {
        List<CGTError> errors = Config.get().getGame().validate();

        if (errors.isEmpty()) {
            Main.getApp().getScene().setCursor(Cursor.WAIT);
            new ExportDialog().show();
            Main.getApp().getScene().setCursor(Cursor.DEFAULT);
        } else {
            showValidateDialog(errors);
        }
    }

    @FXML
    public void closeProject() {
        this.beforeClosing();
        this.newProject();
    }

    @FXML
    public void exit() {
        Main.getApp().getOnCloseRequest().handle(new WindowEvent(Main.getApp(), WindowEvent.WINDOW_CLOSE_REQUEST));

        Main.getApp().close();
    }

    @FXML
    public void runProject() {
        List<CGTError> errors = Config.get().getGame().validate();
        if (errors.isEmpty()) {
            Main.getApp().getScene().setCursor(Cursor.WAIT);
            Config.get().saveConfig();
            File base = Config.get().getProjectDir();

            try {
                File localZip = new File(localDefaultDirectory() + DESKTOP_ZIP_PATH);
                if (localZip.exists()) {
                    long foo = localZip.lastModified();
                    InputStream bar = Main.class.getResourceAsStream("/bin/desktop-1.0.zip");
                    if (bar == null) throw new RuntimeException("run task ferramenta:copyDesktop to copy runner");
                    File file = new File(localDefaultDirectory() + "tmp.zip");
                    FileUtils.copyInputStreamToFile(bar, file);
                    if (localZip.lastModified() != file.lastModified()) {
                        copyDesktopFiles();
                    }
                    file.delete();
                } else {
                    copyDesktopFiles();
                }

                ZipFile jar = new ZipFile(localDefaultDirectory() + DESKTOP_JAR_PATH);
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

    @FXML
    public void about() {
        try {
            String url = "http://www.cgt.ifce.edu.br";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addWorld() {
        DrawableObject<CGTGameWorld> worldDrawable = new CGTGameWorldDrawable(this.drawableObjectPane, this.drawableConfigurationsPane);
        DrawableObject<CGTActor> drawableActor = new CGTGameActorDrawable(worldDrawable.getObject().getActor(), worldDrawable.getObject().getId(), this.drawableObjectPane, this.drawableConfigurationsPane);
        TreeItem<DrawableObject> worldTreeItem = new TreeItem<>(worldDrawable);
        TreeItem<DrawableObject> actorTreeItem = new TreeItem<>(drawableActor);
        worldTreeItem.getChildren().add(actorTreeItem);
        this.tree.getRoot().getChildren().add(worldTreeItem);
        this.rootItem.getConfig().getComboBox().getItems().add(worldDrawable.toString());
    }

    @FXML
    public void addScreen() {
        DrawableObject<CGTScreen> screenDrawable = new CGTGameScreenDrawable(this.drawableObjectPane, this.drawableConfigurationsPane);
        if(screenDrawable.getObject() != null) {
            TreeItem<DrawableObject> screenTreeItem = new TreeItem<>(screenDrawable);
            this.tree.getRoot().getChildren().add(screenTreeItem);
            this.rootItem.getConfig().getComboBox().getItems().add(screenDrawable.toString());
        }
        else{
            System.out.println("desistiu");
            //Config.get().getGame().removeScreen(screenDrawable.getObject());
        }
    }

    @FXML
    public void addEnemy() {
        CGTGameEnemyDrawable enemyDrawableObject = new CGTGameEnemyDrawable(this.drawableObjectPane, this.drawableConfigurationsPane);
        TreeItem<DrawableObject> enemyTreeItem = new TreeItem<>(enemyDrawableObject);
        String worldName = enemyDrawableObject.getWorldName();
        TreeItem<DrawableObject> worldTreeItem = this.getWorldNode(worldName);
        worldTreeItem.getChildren().add(enemyTreeItem);
        CGTGameWorld cgtGameWorld = Config.get().getGame().getWorld(worldName);
        cgtGameWorld.addEnemy((CGTEnemy) enemyDrawableObject.getObject());
    }

    @FXML
    public void addOpponent() {

    }

    @FXML
    public void addBonus() {
        CGTGameBonusDrawable bonusDrawable = new CGTGameBonusDrawable(this.drawableObjectPane, this.drawableConfigurationsPane);
        TreeItem<DrawableObject> bonusTreeItem = new TreeItem<>(bonusDrawable);
        String worldName = bonusDrawable.getWorldName();
        TreeItem<DrawableObject> worldTreeItem = this.getWorldNode(worldName);
        worldTreeItem.getChildren().add(bonusTreeItem);
        CGTGameWorld cgtGameWorld = Config.get().getGame().getWorld(worldName);
        cgtGameWorld.addBonus((CGTBonus) bonusDrawable.getObject());
    }

    @FXML
    public void addBullet() {

    }

    @FXML
    public void addGearInformation() {

    }

    @FXML
    public void addObjectLifeBar() {
        CGTLifeBarDrawable life = new CGTLifeBarDrawable(drawableObjectPane,drawableConfigurationsPane);
        //if(life.getLife() != null) {
            TreeItem<DrawableObject> lifeTreeItem = new TreeItem<>(life);
            String worldName = life.getLife().getWorld().getId();
            String bar = life.getLife().getOwnerId();
            TreeItem<DrawableObject> worldTreeItem = this.getWorldNode(worldName);
            CGTGameObject go = Config.get().getGame().findObject(bar);
            for (TreeItem<DrawableObject> a : worldTreeItem.getChildren()) {
                if (a.getValue().getObject().equals(go))
                    a.getChildren().add(lifeTreeItem);
            }
            CGTGameWorld cgtGameWorld = Config.get().getGame().getWorld(worldName);
            cgtGameWorld.addLifeBar(life.getLife());
        //}
        //else
          //  System.out.println("DesistiuNULL");
    }

    @FXML
    public void addEnemyLifeBar() {

    }

    @FXML
    public void addButtonScreen() {
        CGTButtonScreenPreview btn = new CGTButtonScreenPreview(this.drawableObjectPane, this.drawableConfigurationsPane);
        TreeItem<DrawableObject> btnTreeItem = new TreeItem<>(btn);
        String screenName = btn.getScreenName();
        TreeItem<DrawableObject> screenTreeItem = this.getScreenNode(screenName);
        screenTreeItem.getChildren().add(btnTreeItem);
        CGTScreenPreview cgtScreen = new CGTScreenPreview();
        cgtScreen.setScreen(Config.get().getGame().getScreen(screenName));
        cgtScreen.addButtons(btn);
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

    public void beforeClosing() {
        if (Config.isCreated() && (!Config.isLoaded() || (Config.isLoaded() && Config.get().wasModified()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salvar");
            alert.setHeaderText(null);
            alert.setContentText("Você modificou seu projeto. Deseja salvar antes de sair?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                this.saveProject();
            }
        }
    }

    private TreeItem<DrawableObject> getWorldNode(String worldName) {
        TreeItem<DrawableObject> result = null;

        for (int i = 0; i < this.tree.getExpandedItemCount(); i++) {
            DrawableObject drawableObject = this.tree.getTreeItem(i).getValue();

            if (drawableObject instanceof CGTGameWorldDrawable) {
                CGTGameWorldDrawable gameDrawable = (CGTGameWorldDrawable) drawableObject;

                if (gameDrawable.getObject().getId().equals(worldName)) {
                    result = this.tree.getTreeItem(i);
                    break;
                }
            }
        }

        return result;
    }

    private TreeItem<DrawableObject> getScreenNode(String screenName) {
        TreeItem<DrawableObject> result = null;

        for (int i = 0; i < this.tree.getExpandedItemCount(); i++) {
            DrawableObject drawableObject = this.tree.getTreeItem(i).getValue();

            if (drawableObject instanceof CGTGameScreenDrawable) {
                CGTGameScreenDrawable gameDrawable = (CGTGameScreenDrawable) drawableObject;

                if (gameDrawable.getObject().getId().equals(screenName)) {
                    result = this.tree.getTreeItem(i);
                    break;
                }
            }
        }

        return result;
    }

    private void runDesktop() {
        if (!running) {
            new Thread() {
                @Override
                public void run() {
                    running = true;
                    String path = localDefaultDirectory() + "desktop/desktop-1.0/bin/desktop";
                    Runtime runtime = Runtime.getRuntime();
                    Process p1;
                    int i;

                    try {
                        if (isWin()) {
                            p1 = runtime.exec("\"" + path + ".bat\"");
                        } else {
                            runtime.exec("chmod +x " + path);
                            p1 = runtime.exec("sh " + path);
                        }

                        InputStream is = p1.getInputStream();

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

    private boolean isWin() {
        String OS = System.getProperty("os.name").toUpperCase();
        return OS.contains("WIN");
    }

    private String localDefaultDirectory() {
        return (isWin()) ? System.getenv("APPDATA") : System.getProperty("user.home") + "/.local/";
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

    private void updateRecent() {
        MenuItem item;
        openRecentMenu.getItems().clear();

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

                openRecentMenu.getItems().add(item);
            }
        }

        if (openRecentMenu.getItems().size() > 0) {
            item = new MenuItem("Limpar");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Pref.load().getRecentProjects().clear();
                    Pref.load().save();
                    updateRecent();
                }
            });
            openRecentMenu.getItems().add(new SeparatorMenuItem());
            openRecentMenu.getItems().add(item);
        } else {
            item = new MenuItem("Vazio");
            item.setDisable(true);
            openRecentMenu.getItems().add(item);
        }
    }

    private void copyDesktopFiles() {
        InputStream url = Main.class.getResourceAsStream("/bin/desktop-1.0.zip");
        File file = new File(localDefaultDirectory() + "desktop/desktop.zip");
        file.delete();
        file.getParentFile().mkdirs();

        try {
            FileUtils.copyInputStreamToFile(url, file);
            url.close();
            ZipFile zipFile = new ZipFile(file);
            zipFile.extractAll(file.getParent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}