package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigLifePane;
import br.edu.ifce.cgt.application.controller.titleds.IndividualLifeBarTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Draggable;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import cgt.hud.IndividualLifeBar;
import cgt.util.CGTTexture;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

/**
 * Created by Edy Junior on 22/10/2015.
 */
public class CGTLifeBarDrawable extends AbstractDrawableObject {

    private IndividualLifeBar life;
    private ConfigLifePane lifePane;
    private Draggable preview = new Draggable();

    public CGTLifeBarDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane){
        super(drawableObjectPane,drawableConfigurationsPane);
        preview = new Draggable(lifePane.getRelX(), lifePane.getRelY(),this.life);
    }

    @Override
    public void drawObject() {
        if(!lifePane.getTextBar().getText().isEmpty() && lifePane.getRelX().getValue() >= 0 &&
                lifePane.getRelY().getValue() >= 0 && lifePane.getWRel().getValue() > 0
                && lifePane.getHRel().getValue() > 0){
            setSizeLife();

            super.updateDrawPane(preview);
        }
    }

    @Override
    public void onCreate() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Barra de vida");
        dialog.setHeaderText("Criacao de uma barra de vida");

        ButtonType createButtonType = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField lifeName = new TextField();
        lifeName.setPromptText("Nome da barra de vida");
        ComboBox<String> worldCombobox = new ComboBox<>();
        List<CGTGameWorld> worlds = Config.get().getGame().getWorlds();
        worlds.stream().forEach(w -> worldCombobox.getItems().add(w.getId()));
        ComboBox<String> objectsCombobox = new ComboBox<>();
        List<String> obj = Config.get().getGame().objectIds();
        obj.stream().forEach(w -> objectsCombobox.getItems().add(w));

        grid.add(new Label("Nome da barra de vida:"), 0, 0);
        grid.add(lifeName, 1, 0);
        grid.add(new Label("Objeto:"), 0, 1);
        grid.add(objectsCombobox, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(createButtonType);
        loginButton.setDisable(true);

        lifeName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> lifeName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(lifeName.getText(), objectsCombobox.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String object = result.get().getValue();
            this.life = new IndividualLifeBar(id);
            this.life.setOwner(object);
            this.life.setName(id);
            CGTGameObject res = null;
            for (CGTGameWorld w : Config.get().getGame().getWorlds() ) {
                res = w.getObjectByLabel(object);
                if (res != null) {
                    Config.get().getGame().getWorld(w.getId()).addLifeBar(getLife());
                    //Sem o break todos os mundos com objetos de mesmo nome receber�o lifeBar?
                }
            }
        }
    }

    @Override
    public void onStart() {
        this.lifePane = new ConfigLifePane(this, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public void drawConfigurationPanel() {super.updateConfigPane(this.lifePane);}

    @Override
    public Node getPane() {
        return this.preview;
    }

    public IndividualLifeBar getLife(){
        return this.life;
    }

    public Draggable getDraggable(){
        return this.preview;
    }

    @Override
    public String toString() {
        return this.life.getName() + " (Barra de vida)";
    }

    public void setSizeLife(){
        String name = getLife().getWorld().getId();
        int x = Config.get().getGame().getWorld(name).getWidthP();
        int y = Config.get().getGame().getWorld(name).getHeightP();

        CGTTexture bkg = Config.get().getGame().getWorld(name).getBackground();
        if(x == 0 && bkg != null) {
            preview.setWidthBCKG(
                    Config.get().getImage(bkg.getFile().getFile().getName()).getWidth()
            );
            preview.setHeightBCKG(
                    Config.get().getImage(bkg.getFile().getFile().getName()).getHeight()
            );
        }
        else {
            preview.setWidthBCKG(x);
            preview.setHeightBCKG(y);
        }

        preview.setFitWidth(lifePane.getWRel().getValue() * preview.getWidthBCKG());
        preview.setFitHeight(lifePane.getHRel().getValue() * preview.getHeightBCKG());
        getLife().setRelativeHeight(lifePane.getHRel().getValue());
        getLife().setRelativeWidth(lifePane.getWRel().getValue());
        getLife().setRelativeX(lifePane.getRelX().getValue());
        getLife().setRelativeY(lifePane.getRelY().getValue() -
                (float) (preview.getFitHeight() / preview.getHeightBCKG()));
    }

    @Override
    public boolean destroy() {
        // TODO nao implementado ainda
        return false;
    }
}
