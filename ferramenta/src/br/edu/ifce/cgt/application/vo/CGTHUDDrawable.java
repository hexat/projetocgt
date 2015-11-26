package br.edu.ifce.cgt.application.vo;

/**
 * Created by Edy Junior on 16/11/2015.
 */

import cgt.hud.HUDComponent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/*Classe que reunira os Huds individuais do mundo*/

public class CGTHUDDrawable extends AbstractDrawableObject {
    private String name;
    private ArrayList<CGTLifeBarDrawable> lifes;

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
        boolean ok = true;
        for(CGTLifeBarDrawable a : getLifes()){
            if(a.destroy() == false)
                ok = false;
        }
        if(this.destroy() == false)
            ok = false;
        return ok;
    }

    public ArrayList<CGTLifeBarDrawable> getLifes(){ return this.lifes; }
}
