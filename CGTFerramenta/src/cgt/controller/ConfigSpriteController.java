package cgt.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import util.DialogsUtil;
import application.Config;
import application.Main;
import cgt.game.CGTSpriteSheet;
import cgt.game.SpriteSheetDB;
import cgt.util.CGTTexture;

/**
 * Created by infolev on 06/02/15.
 */
public class ConfigSpriteController extends BorderPane {
    private CGTSpriteSheet spriteSheet;

    private Stage stage;

    @FXML private TextField txtImgName;
    @FXML private TextField txtNameSprite;
    @FXML private TextField txtNumLines;
    @FXML private TextField txtNumCol;
    @FXML private ImageView imgView;

    private File imgFile;

    public ConfigSpriteController(CGTSpriteSheet sheet) {
    	
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigSprite.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    	setSpriteSheet(sheet);
    	txtImgName.setEditable(false);
        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());
        getChildren().remove(imgView);

        imgView.fitHeightProperty().bind(stage.heightProperty());
        imgView.fitWidthProperty().bind(stage.widthProperty());
        
        stage.sizeToScene();
	}

    @FXML public void addNewSprite() {
        if (validate()) {
            SpriteSheetDB db = Config.getGame().getSpriteDB();
            if (spriteSheet == null) {
                spriteSheet = db.create(txtNameSprite.getText(), new CGTTexture(Config.createImg(imgFile)));
                spriteSheet.setColumns(Integer.parseInt(txtNumCol.getText()));
                spriteSheet.setRows(Integer.parseInt(txtNumLines.getText()));
            } else {
            	if (imgFile != null) {
            		spriteSheet.setTexture(new CGTTexture(Config.createImg(imgFile)));
            	}
                spriteSheet.setColumns(Integer.parseInt(txtNumCol.getText()));
                spriteSheet.setRows(Integer.parseInt(txtNumLines.getText()));
            }
            stage.close();
        } else {
            Dialogs.create().message("Preencha todos os campos").showError();
        }
    }

    private boolean validate() {
    	if (spriteSheet != null) {
    		return (!txtNumCol.getText().equals("") && !txtNumLines.getText().equals(""));
    	} else {
    		return (imgFile != null && !txtNameSprite.getText().equals("") && !txtNumCol.getText().equals("")
                && !txtNumLines.getText().equals("") && Config.getGame()
                .getSpriteDB().find(txtNameSprite.getText()) == null);
    	}
        
    }

    @FXML public void setTextureSprite() {
        File file = DialogsUtil.showOpenDialog("Selecione o Sprite", txtImgName.getScene().getWindow(), DialogsUtil.IMG_FILTER);

        imgFile = file;

        if (imgFile != null) {
            txtImgName.setText(imgFile.getName());
//          Image image = new Image("file:"+file.getAbsolutePath(), 0, imgView.fitHeightProperty().get(), false, false);
          Image image = new Image("file:"+file.getAbsolutePath());            
            imgView.setImage(image);
            setLeft(imgView);
            stage.sizeToScene();
        } else {
        }
    }


    public void setSpriteSheet(CGTSpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        if (spriteSheet != null) {
		    txtImgName.setText(spriteSheet.getTexture().getFile().getFilename());
		    txtNameSprite.setText(spriteSheet.getId());
		    txtNameSprite.setEditable(false);
		    txtNumCol.setText(spriteSheet.getColumns()+"");
		    txtNumLines.setText(spriteSheet.getRows()+"");
        }
    }

    public CGTSpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

	public void show() {
		stage.show();
	}
}
