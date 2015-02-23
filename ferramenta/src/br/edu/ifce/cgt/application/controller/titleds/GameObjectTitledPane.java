package br.edu.ifce.cgt.application.controller.titleds;

import br.edu.ifce.cgt.application.Config;
import br.edu.ifce.cgt.application.controller.dialogs.AnimationDialog;
import cgt.core.CGTAnimation;
import cgt.util.CGTFile;
import cgt.util.CGTSound;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import cgt.core.CGTGameObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.controller.panes.AnimationViewPane;
import br.edu.ifce.cgt.application.controller.panes.ItemViewPane;

public class GameObjectTitledPane extends TitledPane {
    @FXML public VBox boxAnimations;
    @FXML public VBox boxPositions;
    @FXML public VBox boxSoundColision;
    @FXML public VBox boxSoundDie;

    @FXML private TextField txtBoundsW;
    @FXML private TextField txtBoundsH;

    @FXML private TextField txtPositionX;
    @FXML private TextField txtPositionY;

    @FXML private TextField txtColisionX;
    @FXML private TextField txtColisionY;
    @FXML private TextField txtColisionW;
    @FXML private TextField txtColisionH;

    @FXML private TextField txtVelocidade;

    @FXML private TextField txtLife;

    @FXML private Button btnSetSound;
    @FXML private VBox listTeste;

	@FXML private TableView<String> tableSomColisao;
	private ObservableList<String> listaSomColisao;
	
	private CGTGameObject gameObject;

    public GameObjectTitledPane(CGTGameObject object) {
        FXMLLoader xml = new FXMLLoader(Main.class.getResource("/view/ConfigGameObject.fxml"));
        xml.setRoot(this);
        xml.setController(this);

        try {
            xml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    public void init() {
        btnSetSound.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameObject.getSound() == null) {
                    File audio = DialogsUtil.showOpenDialog("Selecione um audio", DialogsUtil.WAV_FILTER);
                    if (audio != null) {
                        try {
                            CGTFile som = Config.createAudio(audio);
                            gameObject.setSound(new CGTSound(som));
                            btnSetSound.setText(som.getFilename());
                        } catch (IOException e) {
                            e.printStackTrace();
                            DialogsUtil.showErrorDialog();
                        }
                    }
                } else {
                    Config.destroy(gameObject.getSound().getFile());
                    gameObject.setSound(null);
                    btnSetSound.setText("Selecionar");
                }
            }
        });

        txtLife.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameObject.setLife(Integer.parseInt(txtLife.getText()));
            }
        });

        txtBoundsH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float h = Float.parseFloat(txtBoundsH.getText());
                gameObject.getBounds().height = h;
            }
        });

        txtBoundsW.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float w = Float.parseFloat(txtBoundsW.getText());
                gameObject.getBounds().width = w;
            }
        });

        txtColisionX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float x = Float.parseFloat(txtColisionX.getText());
                gameObject.getCollision().x = x;
            }
        });

        txtColisionY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float y = Float.parseFloat(txtColisionY.getText());
                gameObject.getCollision().y = y;
            }
        });

        txtColisionW.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float w = Float.parseFloat(txtColisionW.getText());
                gameObject.getCollision().width = w;

            }
        });

        txtColisionH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                float h = Float.parseFloat(txtColisionH.getText());
                gameObject.getCollision().height = h;
            }
        });

        txtVelocidade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameObject.setSpeed(Integer.parseInt(txtVelocidade.getText()));
            }
        });
    }

    @FXML public void teste() {
        listTeste.getChildren().add(new Label("teste"));
    }

    @FXML public void teste2() {
        listTeste.getChildren().clear();
    }
	
	@FXML public void btnProcurarSom(){
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

		File chosenFile = DialogsUtil.showOpenDialog("Som de Colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
        } else {
		    path = null;
		}
	}

    @FXML public void addInitialPosition() {
        if (!txtPositionX.getText().equals("") && !txtPositionY.getText().equals("")) {
            int x = Integer.parseInt(txtPositionX.getText());
            int y = Integer.parseInt(txtPositionY.getText());

            gameObject.getInitialPositions().add(new Vector2(x, y));

            txtPositionX.clear();
            txtPositionY.clear();
            updateBoxPositions();
        }
    }

    private void updateBoxPositions() {
        boxPositions.getChildren().clear();
        if (gameObject.getInitialPositions().size() > 0) {
            for (final Vector2 v : gameObject.getInitialPositions()) {
                ItemViewPane pane = new ItemViewPane(v.toString());
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.getInitialPositions().remove(v);
                        updateBoxPositions();
                    }
                });
                boxPositions.getChildren().add(pane);
            }
        } else {
            boxPositions.getChildren().add(new Label("Vazio"));
        }
    }

    public void updateBoxSoundCollision() {
        boxSoundColision.getChildren().clear();
        if (gameObject.getSoundCollision().size() > 0) {
            for (final CGTSound s : gameObject.getSoundCollision()) {
                ItemViewPane pane = new ItemViewPane(s.getFile().getFilename());
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.getSoundCollision().remove(s);
                        Config.destroy(s.getFile());
                        updateBoxSoundCollision();
                    }
                });
                boxSoundColision.getChildren().add(pane);
            }
        } else {
            boxSoundColision.getChildren().add(new Label("Vazio"));
        }
    }

    public void updateBoxSoundDie() {
        boxSoundDie.getChildren().clear();
        if (gameObject.getSoundsDie().size() > 0) {
            for (final CGTSound s : gameObject.getSoundsDie()) {
                ItemViewPane pane = new ItemViewPane(s.getFile().getFilename());
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.getSoundsDie().remove(s);
                        Config.destroy(s.getFile());
                        updateBoxSoundDie();
                    }
                });
                boxSoundDie.getChildren().add(pane);
            }
        } else {
            boxSoundDie.getChildren().add(new Label("Vazio"));
        }
    }

    @FXML public void btnProcurarSomColisao(){

		
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o Background");

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo WAV (*.wav)", "*.wav");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo OGG (*.ogg)", "*.ogg");

		File chosenFile = DialogsUtil.showOpenDialog("Selecione o som de colisão", extFilter, extFilter2);
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		} else {
		    //default return value
		    path = null;
		}
		
		listaSomColisao.add(path);
		tableSomColisao.setEditable(true);
		tableSomColisao.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableSomColisao.setItems(listaSomColisao);
	}

    public void addAnimation() {
        AnimationDialog dialog = new AnimationDialog(gameObject);
        dialog.getDialogStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateBoxAnimation();
            }
        });
        dialog.show();
    }

    private void updateBoxAnimation() {
        boxAnimations.getChildren().clear();
        List<CGTAnimation> lista = gameObject.getAnimations();
        if (lista.size() > 0) {
            for (final CGTAnimation animation : lista) {
                AnimationViewPane pane = new AnimationViewPane(animation);
                pane.getBtnExcluir().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        gameObject.removeAnimation(animation);
                        updateBoxAnimation();
                    }
                });
                boxAnimations.getChildren().add(pane);
            }
        } else {
            boxAnimations.getChildren().add(new Label("Nenhuma Animação"));
        }
    }

    @FXML public void addSoundDie() {
        File file = DialogsUtil.showOpenDialog("Selecionar som de morte", DialogsUtil.WAV_FILTER);

        if (file != null) {
            try {
                gameObject.getSoundsDie().add(new CGTSound(Config.createAudio(file)));
            } catch (IOException e) {
                e.printStackTrace();
                DialogsUtil.showErrorDialog();
            }

            updateBoxSoundDie();
        }
    }

    @FXML public void addSoundCollision() {
        File file = DialogsUtil.showOpenDialog("Selecionar som de colisão", DialogsUtil.WAV_FILTER);

        if (file != null) {
            try {
                gameObject.getSoundCollision().add(new CGTSound(Config.createAudio(file)));
            } catch (IOException e) {
                e.printStackTrace();
                DialogsUtil.showErrorDialog();
            }

            updateBoxSoundCollision();
        }
    }

    public void setGameObject(CGTGameObject gameObject) {
        this.gameObject = gameObject;

        updateBoxAnimation();
        updateBoxPositions();
        updateBoxSoundCollision();
        updateBoxSoundDie();
        if (gameObject.getSound() != null) {
            btnSetSound.setText(gameObject.getSound().getFile().getFilename());
        }
        if (gameObject.getBounds().width > 0) {
            txtBoundsW.setText(gameObject.getBounds().width + "");
        }
        if (gameObject.getBounds().height > 0) {
            txtBoundsH.setText(gameObject.getBounds().height + "");
        }

        if (gameObject.getCollision().height > 0) {
            txtColisionH.setText(gameObject.getCollision().height+"");
        }
        if (gameObject.getCollision().width  > 0) {
            txtColisionW.setText(gameObject.getCollision().width+"");
        }
        if (gameObject.getCollision().x > 0) {
            txtColisionX.setText(gameObject.getCollision().x+"");
        }
        if (gameObject.getCollision().y > 0) {
            txtColisionY.setText(gameObject.getCollision().y+"");
        }

        if (gameObject.getSpeed() > 0) {
            txtVelocidade.setText(gameObject.getSpeed()+"");
        }

        txtLife.setText(gameObject.getLife()+"");
    }

    public CGTGameObject getGameObject() {
        return gameObject;
    }

}
