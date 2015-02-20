package br.edu.ifce.cgt.application.controller.dialogs;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import cgt.game.CGTSpriteSheet;
import cgt.game.SpriteSheetDB;
import cgt.util.CGTTexture;

/**
 * Created by infolev on 06/02/15.
 */
public class SpriteSheetDialog extends HBox {
    private CGTSpriteSheet spriteSheet;

    private Stage stage;

    @FXML private TextField txtImgName;
    @FXML private TextField txtNameSprite;
    @FXML private TextField txtNumLines;
    @FXML private TextField txtNumCol;

    private File imgFile;
    private ImageView imgView;

    public SpriteSheetDialog(CGTSpriteSheet sheet) {
    	
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/ConfigSprite.fxml"));
        view.setRoot(this);
        view.setController(this);

        try {
            view.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getApp().getScene().getWindow());
        stage.sizeToScene();


        imgView = null;
        setSpriteSheet(sheet);
        txtImgName.setEditable(false);
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

        updateImage();
    }

    public void updateImage() {
        if (imgFile != null) {
            txtImgName.setText(imgFile.getName());
//          Image image = new Image("file:"+file.getAbsolutePath(), 0, imgView.fitHeightProperty().get(), false, false);
            Image image = new Image("file:"+imgFile.getAbsolutePath());
            if (imgView == null) {
                imgView = new ImageView();
                imgView.setFitHeight(160);
                imgView.setPreserveRatio(true);
                imgView.setSmooth(true);
                imgView.setCache(true);
                this.getChildren().add(0, imgView);
            }
            imgView.setImage(image);
            stage.sizeToScene();
            stage.centerOnScreen();
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

            imgFile = new File(Config.BASE + spriteSheet.getTexture().getFile().getFile().getPath());
            updateImage();
        }
    }

    public CGTSpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

	public void show() {
		stage.show();
	}
}
