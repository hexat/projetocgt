package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.panes.FontPane;
import br.edu.ifce.cgt.application.controller.panes.HudPane;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import cgt.hud.AmmoDisplay;
import cgt.util.CGTTexture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luan James on 21/02/2015.
 */
public class AmmoDisplayTitledPane extends TitledPane {
    @FXML public HudPane hudControl;
    @FXML public FontPane fontControl;

    public ComboBox boxProjectile;
    public Button btnSelectIcon;

    private AmmoDisplay ammoDisplay;

    public AmmoDisplayTitledPane(AmmoDisplay ammoDisplay) {
        this.ammoDisplay = ammoDisplay;
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigAmmoDisplay.fxml"));
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

    private void setActions() {

        btnSelectIcon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ammoDisplay.getIcon() == null) {
                    File file = DialogsUtil.showOpenDialog("Selecionar Icone", DialogsUtil.IMG_FILTER);
                    if (file != null) {
                        ammoDisplay.setIcon(new CGTTexture(Config.createImg(file)));
                        btnSelectIcon.setText(file.getName());
                    }
                } else {
                    Config.destroy(ammoDisplay.getIcon().getFile());
                    ammoDisplay.setIcon(null);
                    btnSelectIcon.setText("Selecionar");
                }
            }
        });

        boxProjectile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!boxProjectile.getSelectionModel().isEmpty()) {
                    ammoDisplay.setOwner(boxProjectile.getId());
                }
            }
        });

        boxProjectile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (String s : ammoDisplay.getWorld().getProjectilesLabels()) {
                    if (!boxProjectile.getItems().contains(s)) {
                        boxProjectile.getItems().add(s);
                    }
                }
            }
        });
    }

    private void init() {
        fontControl.setFont(ammoDisplay.getLabel());
        hudControl.setHud(ammoDisplay);
        if (ammoDisplay != null) {
            if (ammoDisplay.getIcon() != null) {
                btnSelectIcon.setText(ammoDisplay.getIcon().getFile().getFilename());
            }
            if (ammoDisplay.getOwnerLabel() != null) {
                boxProjectile.getSelectionModel().select(ammoDisplay.getOwnerLabel());
            }
        }
    }

    public AmmoDisplay getAmmoDisplay() {
        return ammoDisplay;
    }

    public void setAmmoDisplay(AmmoDisplay ammoDisplay) {
        this.ammoDisplay = ammoDisplay;
        init();
    }
}
