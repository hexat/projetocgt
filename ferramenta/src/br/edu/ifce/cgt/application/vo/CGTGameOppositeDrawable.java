package br.edu.ifce.cgt.application.vo;

import br.edu.ifce.cgt.application.controller.titleds.OppositeTitledPane;
import cgt.core.CGTOpposite;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Optional;

/**
 * Created by Joel on 05/11/2015.
 */
public class CGTGameOppositeDrawable extends CGTGameObjectDrawable<CGTOpposite> {

    private OppositeTitledPane oppositeTitledPane;

    public CGTGameOppositeDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane) {
        super(drawableObjectPane, drawableConfigurationsPane);
        super.getObjectPane().getAccordionRoot().getPanes().add(this.oppositeTitledPane);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.oppositeTitledPane = new OppositeTitledPane(getObject());
    }

    @Override
    public Node getPane() {
        return this.oppositeTitledPane;
    }

    @Override
    public void onCreate() {
        Optional<Pair<String, String>> result = showGameObjectDialog();

        if (result.isPresent()) {
            String id = result.get().getKey();
            String worldName = result.get().getValue();
            setObject(new CGTOpposite(id));
            setWorldName(worldName);
        }
    }

    @Override
    public String toString() {
        return getObject().getId() ;
    }
}
