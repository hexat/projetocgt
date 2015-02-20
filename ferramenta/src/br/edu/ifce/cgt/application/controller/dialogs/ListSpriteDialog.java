package br.edu.ifce.cgt.application.controller.dialogs;

import java.io.IOException;

import cgt.game.CGTSpriteSheet;
import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by infolev on 06/02/15.
 */
public class ListSpriteDialog extends VBox {
    @FXML private ListView<String> listViewSprites;
	private Stage stage;

    public ListSpriteDialog() {
    	
        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/listaSprites.fxml"));
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

        ObservableList<String> a = FXCollections.observableArrayList(Config.getGame().getSpriteDB().findAllId());
        listViewSprites.setItems(a);
	}
    
    public void editSprite() {
    	String teste = listViewSprites.getSelectionModel().getSelectedItem();
    	if (teste != null) {
    		CGTSpriteSheet sheet = Config.getGame().getSpriteDB().find(teste);
    		if (sheet != null) {
    			SpriteSheetDialog dialog = new SpriteSheetDialog(sheet);
    			dialog.show();
    		}
    	}
    }
    
    public void delSprite() {
    	String teste = listViewSprites.getSelectionModel().getSelectedItem();
    	if (teste != null) {
    		if (Config.getGame().getSpriteDB().delete(teste)) {
    			listViewSprites.getItems().remove(teste);
    		}
    	}
    }

	public void show() {
		stage.show();
	}
}
