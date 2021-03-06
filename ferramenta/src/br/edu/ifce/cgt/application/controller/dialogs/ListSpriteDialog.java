package br.edu.ifce.cgt.application.controller.dialogs;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTSpriteSheet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by infolev on 06/02/15.
 */
public class ListSpriteDialog extends VBox {
    @FXML private ListView<String> listViewSprites;
	private Stage stage;

    public ListSpriteDialog() {
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/ListSprite.fxml"));
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

        ObservableList<String> a = FXCollections.observableArrayList(Config.get().getGame().getSpriteDB().findAllId());
        listViewSprites.setItems(a);
	}
    
    public void editSprite() {
    	String teste = listViewSprites.getSelectionModel().getSelectedItem();
    	if (teste != null) {
    		CGTSpriteSheet sheet = Config.get().getGame().getSpriteDB().find(teste);
    		if (sheet != null) {
    			SpriteSheetDialog dialog = new SpriteSheetDialog(sheet);
    			dialog.show();
    		}
    	}
    }
    
    public void delSprite() {
    	String teste = listViewSprites.getSelectionModel().getSelectedItem();
    	if (teste != null) {
    		if (Config.get().getGame().getSpriteDB().delete(teste)) {
    			listViewSprites.getItems().remove(teste);
    		}
    	}
    }

	public void show() {
		stage.show();
	}
}
