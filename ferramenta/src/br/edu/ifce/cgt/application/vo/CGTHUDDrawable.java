package br.edu.ifce.cgt.application.vo;

/**
 * Created by Edy Junior on 16/11/2015.
 */

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/*Classe que reunira os Huds do mundo*/

public class CGTHUDDrawable extends AbstractDrawableObject {
    private String name;

    public CGTHUDDrawable(Pane drawableObjectPane, Pane drawableConfigurationsPane, String name) {
        super(drawableObjectPane, drawableConfigurationsPane);
        this.name = name;
    }

    @Override
    public void drawObject(){

    }
    @Override
    public Node getPane(){
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void drawConfigurationPanel(){

    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "HUDs";
    }

    @Override
    public boolean destroy() {
        return false;
    }
}
