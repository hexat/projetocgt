package br.edu.ifce.cgt.application.controller.panes;

import cgt.core.CGTActor;
import cgt.game.LoseCriteria;
import cgt.lose.LifeDeleted;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by luanjames on 09/03/15.
 */
public class ActorDeadPane extends GridPane implements LoseCriteriaPane {
    private LifeDeleted actorDead;

    public ActorDeadPane(CGTActor actor) {
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(60);
        getColumnConstraints().setAll(c1, c2);

        add(new Label("Ator"), 0, 0);
        TextField txtActor = new TextField(actor.getId());
        txtActor.setEditable(false);
        txtActor.setDisable(true);
        txtActor.setText(actor.getId());
        add(txtActor, 1, 0);

        setActorDead(new LifeDeleted());
    }

    public void setActorDead(LifeDeleted actorDead) {
        this.actorDead = actorDead;
    }

    @Override
    public LoseCriteria getCriteria() {
        return actorDead;
    }
}
