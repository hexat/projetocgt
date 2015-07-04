package br.edu.ifce.cgt.application.controller;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Pref;
import br.edu.ifce.cgt.application.vo.CGTGameWorldDrawable;
import br.edu.ifce.cgt.application.vo.CGTProjectDrawable;
import br.edu.ifce.cgt.application.vo.DrawableObject;
import br.edu.ifce.cgt.application.vo.DrawableObjectTreeCellImpl;
import cgt.game.CGTGameWorld;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;

public class PreviewPane extends BorderPane {

    @FXML
    public AnchorPane drawableObjectPane;

    @FXML
    public AnchorPane drawableConfigurationsPane;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private TreeView<DrawableObject> tree;

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

        DrawableObject rootItem = new CGTProjectDrawable("New Project", this.drawableObjectPane, this.drawableConfigurationsPane);
        TreeItem<DrawableObject> root = new TreeItem<>(rootItem);
        root.setExpanded(true);
        tree.setRoot(root);
    }

    @FXML
    public void newProject() {

    }

    @FXML
    public void openProject() {

    }

    @FXML
    public void saveProject() {

    }

    @FXML
    public void saveProjectAs() {

    }

    @FXML
    public void exportProject() {

    }

    @FXML
    public void closeProject() {

    }

    @FXML
    public void exit() {

    }

    @FXML
    public void runProject() {

    }

    @FXML
    public void about() {

    }

    @FXML
    public void addWorld() {
        CGTGameWorld world = new CGTGameWorld();
        DrawableObject obj = new CGTGameWorldDrawable(world, this.drawableObjectPane, this.drawableConfigurationsPane);
    }

    @FXML
    public void addScreen() {

    }

    @FXML
    public void addEnemy() {

    }

    @FXML
    public void addOpponent() {

    }

    @FXML
    public void addBonus() {

    }

    @FXML
    public void addBullet() {

    }

    @FXML
    public void addGearInformation() {

    }

    @FXML
    public void addObjectLifeBar() {

    }

    @FXML
    public void addEnemyLifeBar() {

    }

    @FXML
    public void addButtonScreen() {

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
}
