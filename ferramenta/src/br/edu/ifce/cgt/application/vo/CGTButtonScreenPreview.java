package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.panes.ConfigButtonPreviewPane;
import br.edu.ifce.cgt.application.controller.panes.ConfigScreenPreviewPane;
import br.edu.ifce.cgt.application.controller.titleds.GameObjectTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.core.CGTGameObject;
import cgt.game.CGTScreen;
import cgt.hud.CGTButtonScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

/**
 * Created by Edy Junior on 23/09/2015.
 */
public class CGTButtonScreenPreview extends AbstractDrawableObject {
    private CGTButtonScreen btn;
    private CGTGameObject gameObject;
    private String name;
    private String screenName;
    private ConfigButtonPreviewPane buttonPane;
    private Rectangle bounds;

    public CGTButtonScreenPreview(CGTButtonScreen btn, AnchorPane drawableObjectPane, AnchorPane drawableConfigurationsPane){
        super( drawableObjectPane, drawableConfigurationsPane);
        this.btn = btn;
        this.buttonPane = new ConfigButtonPreviewPane(this, new Runnable() {
            @Override
            public void run() {
                drawObject();
            }
        });
        //this.bounds = new Rectangle(this.gameObject.getBounds().getWidth(), this.gameObject.getBounds().getHeight());
    }

    @Override
    public CGTGameObject getObject() {
        return gameObject;
    }

    @Override
    public void setObject(Object obj) {
        if (obj instanceof CGTGameObject)
            this.gameObject = (CGTGameObject) obj;
    }

    @Override
    public void drawObject() {
        if(!buttonPane.getTexture().getText().isEmpty()){// && this.btn.getTextureUp().getFile().getFile().exists()) {
            ImageView preview = new ImageView(this.btn.getTextureUp().getFile().getFile().getAbsolutePath());
            preview.setX(buttonPane.getRelX().getValue() * getDrawableObjectPane().getWidth());
            preview.setY(buttonPane.getRelY().getValue() * getDrawableObjectPane().getHeight());
            //preview.setScaleX(buttonPane.getWRel().getValue() * getDrawableObjectPane().getWidth());
            //preview.setScaleY(buttonPane.getHRel().getValue() * getDrawableObjectPane().getHeight());
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
            this.name = id;
            this.screenName = screenName;
        }
    }

    public CGTButtonScreen getButton(){
        return this.btn;
    }

    public void setButton(CGTButtonScreen btn){
        this.btn = btn;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

}
