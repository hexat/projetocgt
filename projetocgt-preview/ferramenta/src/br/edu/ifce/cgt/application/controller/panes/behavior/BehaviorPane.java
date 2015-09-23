package br.edu.ifce.cgt.application.controller.panes.behavior;

import cgt.core.AbstractBehavior;

/**
 * Created by Luan on 18/02/2015.
 */
public interface BehaviorPane {
    public AbstractBehavior getBehavior();

    void setBehavior(AbstractBehavior behavior);
}
