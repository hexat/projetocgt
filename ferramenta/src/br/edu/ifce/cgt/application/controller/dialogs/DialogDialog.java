package br.edu.ifce.cgt.application.controller.dialogs;

import java.io.File;
import java.io.IOException;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import br.edu.ifce.cgt.application.util.Util;
import cgt.hud.CGTButtonScreen;
import cgt.screen.CGTDialog;
import cgt.util.CGTTexture;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.edu.ifce.cgt.application.util.DialogsUtil;

/**
 * Created by infolev on 19/02/15.
 */
public class DialogDialog extends GridPane {

    private final Stage stage;
    private CGTDialog dialog;

    @FXML public ListView<CGTButtonScreen> listButtons;
    @FXML private FloatTextField txtRelX;
    @FXML private FloatTextField txtRelY;
    @FXML private FloatTextField txtRelW;
    @FXML private FloatTextField txtRelH;

    @FXML private Button btnCorner;
    @FXML private Button btnBorder;
    @FXML private Button btnClose;
    @FXML private Button btnBackground;

    @FXML private Button btnRemButtonScreen;

    public DialogDialog(CGTDialog dialog) {
        this.dialog = dialog;

        FXMLLoader view = new FXMLLoader(Main.class.getResource("/view/dialogs/Dialog.fxml"));
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

        init();

        setActions();
    }

    private void setActions() {
        txtRelX.setMaxMin(0, 1);
        txtRelY.setMaxMin(0, 1);
        txtRelW.setMaxMin(0, 1);
        txtRelH.setMaxMin(0, 1);

        txtRelY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.setRelativeY(Float.parseFloat(txtRelY.getText()));
            }
        });

        txtRelX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.setRelativeX(Float.parseFloat(txtRelX.getText()));
            }
        });

        txtRelW.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.setRelativeWidth(Float.parseFloat(txtRelW.getText()));
            }
        });

        txtRelH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.setRelativeHeight(Float.parseFloat(txtRelH.getText()));
            }
        });

        btnBackground.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configBackground();
            }
        });

        btnBorder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configBorder();
            }
        });

        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configClose();
            }
        });

        btnCorner.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configCorner();
            }
        });

        listButtons.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && !listButtons.getSelectionModel().isEmpty()) {
                    ButtonScreenDialog screenDialog = new ButtonScreenDialog(listButtons.getSelectionModel().getSelectedItem(),
                            stage);
                    screenDialog.showAndWait();
                }
            }
        });

        listButtons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CGTButtonScreen>() {
            @Override
            public void changed(ObservableValue<? extends CGTButtonScreen> observable, CGTButtonScreen oldValue, CGTButtonScreen newValue) {
                btnRemButtonScreen.setDisable(false);
            }
        });
    }

    private void configCorner() {
        if (dialog.getRightBottomCorner() != null) {
            Config.destroy(dialog.getRightBottomCorner().getFile());
            dialog.setRightBottomCorner(null);
            btnCorner.setText("Selecionar");
        } else {
            File file = DialogsUtil.showOpenDialog("Selecionar Textura Inferior Direita",
                    DialogsUtil.IMG_FILTER);

            if (file != null) {
                dialog.setRightBottomCorner(new CGTTexture(Config.createImg(file)));
                btnCorner.setText(file.getName());
            }
        }
    }

    private void configClose() {
        ButtonDialog buttonDialog = new ButtonDialog(dialog.getCloseButton(), stage);
        buttonDialog.showAndWait();

        dialog.setCloseButton(buttonDialog.getButton());
    }

    private void configBorder() {
        if (dialog.getHorizontalBorderTexture() != null) {
            Config.destroy(dialog.getHorizontalBorderTexture().getFile());
            dialog.setHorizontalBorderTexture(null);
            btnBorder.setText("Selecionar");
        } else {
            File file = DialogsUtil.showOpenDialog("Selecionar Borda", DialogsUtil.IMG_FILTER);
            if (file != null) {
                dialog.setHorizontalBorderTexture(new CGTTexture(Config.createImg(file)));
                btnBorder.setText(file.getName());
            }
        }
    }

    private void configBackground() {

        if (dialog.getWindow() != null) {
            Config.destroy(dialog.getWindow().getFile());
            dialog.setWindow(null);
            btnBackground.setText("Selecionar");
        } else {
            File file = DialogsUtil.showOpenDialog("Selecionar background", DialogsUtil.IMG_FILTER);

            if (file != null) {
                dialog.setWindow(new CGTTexture(Config.createImg(file)));
                btnBackground.setText(file.getName());
            }
        }
    }

    private void init() {
        if (dialog != null) {
            txtRelH.setText(dialog.getRelativeHeight()+"");
            txtRelW.setText(dialog.getRelativeWidth()+"");
            txtRelX.setText(dialog.getRelativeX()+"");
            txtRelY.setText(dialog.getRelativeY()+"");

            if (dialog.getRightBottomCorner() != null) {
                btnCorner.setText(dialog.getRightBottomCorner().getFile().getFilename());
            }

            if (dialog.getWindow() != null) {
                btnBackground.setText(dialog.getWindow().getFile().getFilename());
            }

            if (dialog.getHorizontalBorderTexture() != null) {
                btnBorder.setText(dialog.getHorizontalBorderTexture().getFile().getFilename());
            }

            for (CGTButtonScreen bs : dialog.getButtons()) {
                listButtons.getItems().add(bs);
            }

            btnRemButtonScreen.setDisable(true);
        } else {
            dialog = new CGTDialog();
        }
    }

    @FXML private void addButtonScreen() {
        ButtonScreenDialog screenDialog = new ButtonScreenDialog(new CGTButtonScreen(), stage);
        screenDialog.showAndWait();
        if (screenDialog.getButtonScreen().validate()) {
            dialog.addButton(screenDialog.getButtonScreen());
            listButtons.getItems().add(screenDialog.getButtonScreen());
        }
    }

    @FXML private void removeButtonScreen() {
        if (!listButtons.getSelectionModel().isEmpty()) {
            CGTButtonScreen screen = listButtons.getSelectionModel().getSelectedItem();
            Config.destroy(screen.getTextureUp().getFile());
            if (screen.getTextureDown() != null) {
                Config.destroy(screen.getTextureDown().getFile());
            }
            dialog.getButtons().remove(screen);
            listButtons.getItems().remove(screen);
        }
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public CGTDialog getDialog() {
        return dialog;
    }

    public void confirmAction() {
        dialog.setRelativeY(Float.parseFloat(txtRelY.getText()));
        dialog.setRelativeX(Float.parseFloat(txtRelX.getText()));
        dialog.setRelativeWidth(Float.parseFloat(txtRelW.getText()));
        dialog.setRelativeHeight(Float.parseFloat(txtRelH.getText()));

        stage.close();
    }
}
