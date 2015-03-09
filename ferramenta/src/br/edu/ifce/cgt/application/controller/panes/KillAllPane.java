package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.controller.panes.WinCriteriaPane;
import cgt.game.CGTGameWorld;
import cgt.game.WinCriteria;
import cgt.win.KillAllEnemies;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class KillAllPane extends GridPane implements WinCriteriaPane {
    private KillAllEnemies kill;

    public KillAllPane(CGTGameWorld world) {
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(60);

        getColumnConstraints().setAll(c1, c2);

        add(new Label("Mundo:"), 0, 0);
        TextField txtWorld = new TextField();
        txtWorld.setDisable(true);
        txtWorld.setEditable(false);
        txtWorld.setText(world.getId());

        add(txtWorld, 1, 0);

        setKillAllEnemies(new KillAllEnemies());
    }

    public void setKillAllEnemies(KillAllEnemies killAllEnemies) {
        this.kill = killAllEnemies;
    }

    @Override
    public WinCriteria getCriteria() {
        return kill;
    }
}
