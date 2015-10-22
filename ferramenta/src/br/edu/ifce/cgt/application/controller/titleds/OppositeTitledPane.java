package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Main;
import cgt.core.CGTOpposite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Luan James on 02/02/15.
 */
public class OppositeTitledPane extends TitledPane {
    @FXML public GridPane gridOpposite;
    @FXML public CheckBox chkBlock;
    @FXML public CheckBox chkDestroyable;

    private CGTOpposite opposite;

    public OppositeTitledPane(CGTOpposite opposite) {
        this.opposite = opposite;

        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigOpposite.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
        setActions();
    }

    private void init() {
        if (opposite != null) {
            chkDestroyable.setSelected(opposite.isDestroyable());
            chkBlock.setSelected(opposite.isBlock());
        }
    }

    private void setActions() {
        chkBlock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                opposite.setBlock(chkBlock.isSelected());
            }
        });

        chkDestroyable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                opposite.setDestroyable(chkDestroyable.isSelected());
            }
        });

    }

    public GridPane getGridOpposite() {
        return gridOpposite;
    }

    public CheckBox getCheckBlock() {
        return chkBlock;
    }

    public CheckBox getCheckDestroyable() {
        return chkDestroyable;
    }

    public void setOpposite(CGTOpposite opposite) {
        this.opposite = opposite;
    }

    public CGTOpposite getOpposite() {
        return opposite;
    }
}
