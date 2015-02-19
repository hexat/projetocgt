package application.controller.panes;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import application.Config;
import application.Main;
import application.controller.titleds.ScreenTitledPane;
import application.controller.titleds.WorldTitledPane;
import application.controller.ScreenController;
import application.controller.WorldController;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import cgt.screen.CGTWindow;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;

public class ScreenTab extends Tab {
    private final Accordion configAccordion;
    private CGTWindow screen;

	public ScreenTab(CGTWindow s) {
		super(s.getId());
		this.screen = s;
        this.configAccordion = (Accordion) Main.getApp().getScene().lookup("#configAccordion");

		setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Action response = Dialogs.create()
                        .owner(Main.getApp())
                        .title("Excluir tela")
                        .masthead("Ao fechar esta aba você esterá removendo esta tela do jogo.")
                        .message("Tem certeza que deseja fazer isso?")
                        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
                        .showConfirm();

                if (response == Dialog.ACTION_OK) {
                    Config.getGame().remove(screen);
                    if (ScreenTab.this.getTabPane().getTabs().contains(event.getSource())) {
                    	ScreenTab.this.getTabPane().getTabs().remove(event.getSource());
                    }
                } else {

                }
                event.consume();
            }
        });
        if (screen instanceof CGTScreen) {
            setContent(ScreenController.getNode((CGTScreen) screen));
        } else {
            setContent(WorldController.getNode((CGTGameWorld) screen));
        }
        setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (((Tab) event.getSource()).isSelected()) {
                    configAccordion.getPanes().clear();
                    if (screen instanceof CGTScreen) {
                        configAccordion.getPanes().add(ScreenTitledPane.getNode((CGTScreen) screen));
                    } else {
                        configAccordion.getPanes().add(WorldTitledPane.getNode((CGTGameWorld) screen));
                    }
                }
            }
        });
	}
}
