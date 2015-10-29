package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.IndividualLifeBarTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Draggable;
import cgt.game.CGTGameWorld;
import cgt.hud.IndividualLifeBar;
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
    //private ConfigLifePane lifePane;
    private IndividualLifeBarTitledPane lifePane;
    private Draggable preview = new Draggable();

    public CGTLifeBarDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane){
        super(drawableObjectPane,drawableConfigurationsPane);
        /*this.lifePane = new ConfigLifePane(this, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });*/

        //preview = new Draggable(lifePane.getRelX(), lifePane.getRelY(),this.life);

    }

    @Override
    public void drawObject() {

    }

    @Override
    public void onCreate() {

        /*TextField LName = new TextField();
        Label AskNameL = new Label("Nome da barra de vida");
        Label AskNameW = new Label("Qual o mundo?");
        Label AskNameO = new Label("Qual o objeto?");
        Label warning = new Label("O mundo e o objeto devem ser escolhidos!");
        warning.setVisible(false);

        ComboBox<String> worldCombobox = new ComboBox<>();
        List<CGTGameWorld> worlds = Config.get().getGame().getWorlds();
        worlds.stream().forEach(w -> worldCombobox.getItems().add(w.getId()));
        ComboBox<String> objectCombobox = new ComboBox<>();

        worldCombobox.setOnAction(e->{
            objectCombobox.getItems().clear();
            objectCombobox.getItems().addAll(Config.get().getGame().getWorld(worldCombobox.getValue()).getObjectIds());
        });

        Button ok = new Button("OK");
        Button cancel = new Button("Cancelar");

        HBox HName = new HBox(20.5);
        HName.getChildren().addAll(AskNameL,LName);
        HBox HWorld = new HBox(20.5);
        HWorld.getChildren().addAll(AskNameW,worldCombobox);
        HBox HObject = new HBox(20.5);
        HObject.getChildren().addAll(AskNameO,objectCombobox);
        HBox HButton = new HBox(20.5);
        HButton.getChildren().addAll(ok,cancel);


        VBox ask = new VBox(20.0);
        ask.getChildren().addAll(HName,HWorld,HObject,HButton,warning);

        Stage stage = new Stage();
        stage.setTitle("Criando barra de vida");
        ok.setOnAction(e->{
            String id = LName.getText();
            String world = worldCombobox.getValue();
            String object = objectCombobox.getValue();

            if(!id.isEmpty() && !world.isEmpty() && !object.isEmpty()) {
                this.life = new IndividualLifeBar(id);
                this.life.setOwner(object);
                stage.close();
            }
            else
                warning.setVisible(true);
        });

        cancel.setOnAction(e -> stage.close());
        stage.setScene(new Scene(ask));
        stage.show();*/

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
            //IndividualLifeBar lifeBar = new IndividualLifeBar();

        }
    }

    @Override
    public void onStart() {
        this.lifePane = new IndividualLifeBarTitledPane(life);
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

    /*@Override
    public String toString() {
        return this.life.getId();
    }*/
}
