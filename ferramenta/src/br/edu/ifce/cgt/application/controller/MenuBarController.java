package br.edu.ifce.cgt.application.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.dialogs.ExportDialog;
import br.edu.ifce.cgt.application.controller.dialogs.ListSpriteDialog;
import br.edu.ifce.cgt.application.controller.dialogs.SpriteSheetDialog;
import br.edu.ifce.cgt.application.util.Pref;
import cgt.screen.CGTWindow;
import cgt.util.CGTError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import br.edu.ifce.cgt.application.util.DialogsUtil;
import br.edu.ifce.cgt.application.util.Config;
import javafx.fxml.FXML;
import br.edu.ifce.cgt.application.controller.panes.ScreenTab;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.FileUtils;
import org.controlsfx.dialog.Dialogs;

public class MenuBarController implements Initializable {

    private final String desktopJarPath = "desktop/lib/desktop-1.0.jar";
    private final String coreJarPath = "desktop/lib/core-1.0.jar";
    private final String cgtJarPath = "desktop/lib/CearaGameTools-1.0.jar";

    public Menu menuRecent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateRecent();
    }

	@FXML
	public void novo() {

	}

	@FXML
	public void abrir() {
        File file = DialogsUtil.showOpenDialog("Abrir projeto", DialogsUtil.CGT_FILTER);
		if (file != null) {
            Pref.load().addRecentProject(file.getAbsolutePath());
            Pref.load().save();

            updateRecent();

            open(file);
        }
	}

    private void updateRecent() {
        MenuItem item;
        menuRecent.getItems().clear();
        for (String path : Pref.load().getRecentProjects()) {
            final File file = new File(path);
            item = new MenuItem(file.getName());
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    open(file);
                }
            });
            menuRecent.getItems().add(item);
        }
        if (menuRecent.getItems().size() > 0) {
            item = new MenuItem("Limpar");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Pref.load().getRecentProjects().clear();
                    Pref.load().save();
                    updateRecent();
                }
            });
            menuRecent.getItems().add(new SeparatorMenuItem());
            menuRecent.getItems().add(item);
        } else {
            item = new MenuItem("Vazio");
            item.setDisable(true);
            menuRecent.getItems().add(item);
        }
    }

    private void open(File open) {
        TabPane tabFerramenta = (TabPane) Main.getApp().getScene().lookup("#tabFerramenta");
        Main.getApp().setTitle(open.getName());
        Config.unzip(open);
        if (tabFerramenta.getTabs().size() > 1) {
            tabFerramenta.getTabs().remove(1, tabFerramenta.getTabs().size());
        }
        for (CGTWindow w : Config.get().getGame().getWindows()) {
            ScreenTab tab = new ScreenTab(w);

            tabFerramenta.getTabs().add(tab);
            tabFerramenta.getSelectionModel().select(tab);
        }
    }

	@FXML
	public void salvar() {
        File save;
        if (Config.get().isLoaded()) {
            save = Config.get().getInputProjectFile();
        } else {
            save = DialogsUtil.showSaveDialog("Salvar projeto");
        }

        if (save != null) {

            Pref.load().addRecentProject(save.getAbsolutePath());
            Pref.load().save();

            try {
                Config.get().zip(save);
                Dialogs.create().owner(Main.getApp()).message(":)").title("Salvando Projeto").showInformation();
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
	}

    @FXML public void export() {
        List<CGTError> errors = Config.get().getGame().validate();
        if (errors.isEmpty()) {
            new ExportDialog().show();
        } else {
            String msg = "";
            ResourceBundle bundle = Pref.load().getBundle();
            for (CGTError e : errors) {
                msg += bundle.getString(e.getValidate().name()) + " em " + e.getId() + "." + '\n';
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText("Verifique os seguintes itens");
            alert.setContentText(msg);
            alert.showAndWait();
        }
    }

    @FXML public void run() {
        Main.getApp().getScene().setCursor(Cursor.WAIT);
        Config.get().saveConfig();
        File base = Config.get().getProjectDir();

        try {
            if (new File(localDefaultDirectory()+desktopJarPath).exists()) {
                String[] paths = new String[] {coreJarPath, desktopJarPath};
                boolean changed = false;
                long foo;
                long bar;
                for (int i = 0; i < paths.length && !changed; i++) {
                    foo = new File(localDefaultDirectory()+paths[i]).lastModified();
                    bar = new File(Main.class.getResource("/bin/"+paths[i]).toURI()).lastModified();

                    if (foo != bar) {
                        copyDesktopFiles();
                        changed = true;
                    }
                }
            } else {
                copyLibDesktopFiles();
            }

            ZipFile jar = new ZipFile(localDefaultDirectory()+desktopJarPath);
            for (File f : base.listFiles()) {
                if (f.isDirectory()) {
                    jar.addFolder(f, new ZipParameters());
                } else {
                    jar.addFile(f, new ZipParameters());
                }
            }

            runDesktop();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Main.getApp().getScene().setCursor(Cursor.DEFAULT);
    }

    private void runDesktop() {
        String path = localDefaultDirectory()+"desktop/bin/desktop";
        Runtime runtime = Runtime.getRuntime();
			try {
                Process p1;
                if (isWin()) {
                    p1 = runtime.exec("cmd /c start "+path+".bat");
                } else {
                    p1 = runtime.exec("sh "+path);
                }
                InputStream is = p1.getInputStream();
                int i;
                String res = "";
                while( (i = is.read() ) != -1) {
                    System.out.print((char)i);
                }
                } catch(IOException e) {
                e.printStackTrace();
			}
    }

    private boolean isWin() {
        String OS = System.getProperty("os.name").toUpperCase();
        return OS.contains("WIN");
    }

    @FXML
	public void addSpriteSheet() {
		SpriteSheetDialog dia =  new SpriteSheetDialog(null);
        dia.show();
	}
	@FXML public void editSpriteSheet(){
		new ListSpriteDialog().show();
	}

    public void exit(ActionEvent actionEvent) {
        Main.getApp().close();
    }

    public void saveAs(ActionEvent actionEvent) {
        File save = DialogsUtil.showSaveDialog("Salvar projeto");
        if (save != null) {
            try {
                Main.getApp().setTitle(save.getName());
                Config.get().zip(save);
                Dialogs.create().owner(Main.getApp()).message(":)").title("Salvando Projeto").showInformation();
            } catch (IOException e) {
                DialogsUtil.showErrorDialog();
                e.printStackTrace();
            }
        }
    }

    private void copyDesktopFiles() {
        URL url = Main.class.getResource("/bin/desktop");
        if (url == null) {
            // error - missing folder
        } else {
            File file = new File(localDefaultDirectory()+"desktop/");
            file.delete();
            file.mkdirs();
            File dir = null;
            try {
                dir = new File(url.toURI());
                FileUtils.copyDirectory(dir, file);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyLibDesktopFiles() {
        if (!new File(localDefaultDirectory()+desktopJarPath).exists()) {
            copyDesktopFiles();
        } else {
            URL url = null;
            for (String path : new String[]{cgtJarPath, coreJarPath}) {
                url = Main.class.getResource("/bin/" + path);
                if (url != null) {
                    File file = new File(localDefaultDirectory() + path);
                    file.delete();
                    File input = null;
                    try {
                        input = new File(url.toURI());
                        FileUtils.copyFile(input, file);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    static String localDefaultDirectory() {
        String path = "";

        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            path = System.getenv("APPDATA");
        } else {
            path = System.getProperty("user.home") + "/.local";
        }
        path += "/CGTData/";
        return path;
    }
}
