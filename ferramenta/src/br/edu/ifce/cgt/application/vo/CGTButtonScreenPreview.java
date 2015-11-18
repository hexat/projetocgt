package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigButtonPreviewPane;
import br.edu.ifce.cgt.application.util.Config;
import br.edu.ifce.cgt.application.util.Draggable;
import cgt.core.CGTGameObject;
import cgt.game.CGTScreen;
import cgt.hud.CGTButtonScreen;
import cgt.util.CGTTexture;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Optional;

/**
 * Created by Edy Junior on 23/09/2015.
 */
public class CGTButtonScreenPreview extends AbstractDrawableObject<CGTButtonScreen> {
    private String name;
    private String screenName;
    private ConfigButtonPreviewPane buttonPane;
    private Draggable preview = new Draggable();

    public CGTButtonScreenPreview(Pane drawableObjectPane, Pane drawableConfigurationsPane){
        super(drawableObjectPane, drawableConfigurationsPane);
        setObject(new CGTButtonScreen());
        preview = new Draggable(buttonPane.getRelX(), buttonPane.getRelY(), getObject());
        preview.setOnMouseEntered(E->{
            if (!buttonPane.getTextPress().getText().isEmpty())
                preview.setImage(Config.get().getImage(getObject().getTextureDown().getFile().getFile().getName()));
        });
        preview.setOnMouseExited(e -> {
            if (!buttonPane.getTextUp().getText().isEmpty())
                preview.setImage(Config.get().getImage(getObject().getTextureUp().getFile().getFile().getName()));
        });
    }

    public CGTButtonScreenPreview(CGTButtonScreen object, String screenName, Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(object, drawableObjectPane, drawableConfigurationsPane);
        this.screenName = screenName;
        name = "-> " + object.getWindowId();
        preview = new Draggable(buttonPane.getRelX(), buttonPane.getRelY(), getObject());
        preview.setOnMouseEntered(E->{
            if (!buttonPane.getTextPress().getText().isEmpty())
                preview.setImage(Config.get().getImage(getObject().getTextureDown().getFile().getFile().getName()));
        });
        preview.setOnMouseExited(e -> {
            if (!buttonPane.getTextUp().getText().isEmpty())
                preview.setImage(Config.get().getImage(getObject().getTextureUp().getFile().getFile().getName()));
        });
    }

    @Override
    public void onStart() {
        this.buttonPane = new ConfigButtonPreviewPane(this, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
    }

    @Override
    public void drawObject() {

        if(!buttonPane.getTextUp().getText().isEmpty() && buttonPane.getRelX().getValue() >= 0 &&
                buttonPane.getRelY().getValue() >= 0 && buttonPane.getWRel().getValue() > 0
                && buttonPane.getHRel().getValue() > 0){
            setSizeButton();

            super.updateDrawPane(preview);
        }
    }

    @Override
    public Node getPane() {
        return this.buttonPane;
    }

    @Override
    public void drawConfigurationPanel() {super.updateConfigPane(buttonPane);}

    @Override
    public void onCreate() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Botao");
        dialog.setHeaderText("Criacao de um botao");

        ButtonType createButtonType = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField btnName = new TextField();
        btnName.setPromptText("Nome do botao");
        ComboBox<String> screenCombobox = new ComboBox<>();
        List<CGTScreen> screens = Config.get().getGame().getScreens();
        screens.stream().forEach(w -> screenCombobox.getItems().add(w.getId()));

        grid.add(new Label("Nome do botao:"), 0, 0);
        grid.add(btnName, 1, 0);
        grid.add(new Label("Tela:"), 0, 1);
        grid.add(screenCombobox, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(createButtonType);
        loginButton.setDisable(true);

        btnName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> btnName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(btnName.getText(), screenCombobox.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String screenName = result.get().getValue();
            setObject(new CGTButtonScreen());
            this.name = id;
            this.screenName = screenName;
        }
    }

    @Override
    public String toString() {
        return name + " (Bot�o de tela)";
    }

    public String getScreenName() {
        return screenName;
    }

    public Draggable getImage(){
        return  this.preview;
    }

    public void setSizeButton(){
        CGTScreen screen = Config.get().getGame().getScreen(screenName);
        int x = screen.getWidthP();
        int y = screen.getHeightP();

        CGTTexture bkg = screen.getBackground();
        if(x == 0 && bkg != null) {
            preview.setWidthBCKG(
                    Config.get().getImage(bkg.getFile().getFile().getName()).getWidth());
            preview.setHeightBCKG(
                    Config.get().getImage(bkg.getFile().getFile().getName()).getHeight());
        }
        else{
            preview.setWidthBCKG(x);
            preview.setHeightBCKG(y);
        }
        preview.setFitWidth(buttonPane.getWRel().getValue() * preview.getWidthBCKG());
        preview.setFitHeight(buttonPane.getHRel().getValue() * preview.getHeightBCKG());
        getObject().setRelativeHeight(buttonPane.getHRel().getValue());
        getObject().setRelativeWidth(buttonPane.getWRel().getValue());
        getObject().setRelativeX(buttonPane.getRelX().getValue());
        getObject().setRelativeY(buttonPane.getRelY().getValue() -
                (float) (preview.getFitHeight()/preview.getHeightBCKG()));
    }

    @Override
    public boolean destroy() {
        return Config.get().getGame().getScreen(screenName).getButtons().remove(getObject());
    }
}
