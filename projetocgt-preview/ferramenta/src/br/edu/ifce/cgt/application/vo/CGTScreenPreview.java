package br.edu.ifce.cgt.application.vo;

import cgt.game.CGTScreen;
import cgt.screen.CGTWindow;

import java.io.Serializable;

/**
 * Created by Edy Junior on 24/09/2015.
 */

public class CGTScreenPreview{
    private CGTScreen screen;

    public void addButtons(CGTButtonScreenPreview btn){
        getScreen().getButtons().add(btn.getButton());
    }

    public CGTScreen getScreen(){
        return screen;
    }
    public void setScreen(CGTScreen screen){
        this.screen = screen;
    }

}