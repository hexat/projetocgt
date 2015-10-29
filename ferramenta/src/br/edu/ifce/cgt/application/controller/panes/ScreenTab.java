package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.Main;
import br.edu.ifce.cgt.application.controller.ScreenController;
import br.edu.ifce.cgt.application.controller.WorldController;
import br.edu.ifce.cgt.application.controller.titleds.CameraTitledPane;
import br.edu.ifce.cgt.application.controller.titleds.ScreenTitledPane;
import br.edu.ifce.cgt.application.controller.titleds.WorldTitledPane;
import br.edu.ifce.cgt.application.util.Config;
import cgt.game.CGTGameWorld;
import cgt.game.CGTScreen;
import cgt.screen.CGTWindow;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;

import java.util.Optional;

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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Config.get().getGame().remove(screen);
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
            setContent(new WorldController((CGTGameWorld) screen));
        }
        setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (((Tab) event.getSource()).isSelected()) {
                    configAccordion.getPanes().clear();
                    if (screen instanceof CGTScreen) {
                        configAccordion.getPanes().add(ScreenTitledPane.getNode((CGTScreen) screen));
                    } else {
                        configAccordion.getPanes().add(new WorldTitledPane((CGTGameWorld) screen));
                        configAccordion.getPanes().add(new CameraTitledPane(((CGTGameWorld) screen).getCamera()));
                    }
                    configAccordion.getPanes().get(0).setExpanded(true);
                }
            }
        });
	}
}
