package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Config;
import application.Main;
import cgt.game.CGTSpriteSheet;
import cgt.game.SpriteSheetDB;
import cgt.util.CGTTexture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import util.DialogsUtil;

/**
 * Created by infolev on 06/02/15.
 */
public class ConfigSpriteController implements Initializable {
    private CGTSpriteSheet spriteSheet;
    @FXML private TextField txtNameSprite;
    @FXML private TextField txtNumLines;
    @FXML private TextField txtNumCol;

    private File imgFile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML public void addNewSprite() {
        if (validate()) {
            SpriteSheetDB db = Config.getGame().getSpriteDB();
            if (spriteSheet == null) {
                spriteSheet = db.create(txtNameSprite.getText(), new CGTTexture(Config.createImg(imgFile)));
                spriteSheet.setColumns(Integer.parseInt(txtNumCol.getText()));
                spriteSheet.setRows(Integer.parseInt(txtNumLines.getText()));
            } else {
                spriteSheet.setTexture(new CGTTexture(Config.createImg(imgFile)));
                spriteSheet.setColumns(Integer.parseInt(txtNumCol.getText()));
                spriteSheet.setRows(Integer.parseInt(txtNumLines.getText()));
            }
        }
    }

    private boolean validate() {
        return (imgFile != null && !txtNameSprite.getText().equals("") && !txtNumCol.getText().equals("")
                && !txtNumLines.getText().equals("") && Config.getGame()
                .getSpriteDB().find(txtNameSprite.getText()) == null);
    }

    @FXML public void setTextureSprite() {
        File file = DialogsUtil.showOpenDialog("Selecione o Sprite", DialogsUtil.IMG_FILTER);

        imgFile = file;
    }

    public void setSpriteSheet(CGTSpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public CGTSpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public static GridPane getNode(CGTSpriteSheet o) {
        GridPane res = null;

        try {
            FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigSprite.fxml"));
            res = xml.load();
            ConfigSpriteController c = xml.getController();
            c.setSpriteSheet(o);
        } catch (IOException e) {

        }

        return res;
    }
}
