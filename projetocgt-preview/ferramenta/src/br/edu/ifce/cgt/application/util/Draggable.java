package br.edu.ifce.cgt.application.util;

import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.hud.CGTButtonScreen;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//import java.awt.*;
import javafx.scene.shape.Rectangle;


/**
 * Created by Edy Junior on 08/10/2015.
 */
public class Draggable extends ImageView{

    /*variáveis que guardam a largura e a altura,respectivamente, do background que conterá a imagem draggable*/

    private double widthBCKG = 0;
    private double heightBCKG = 0;

    public Draggable (){
        super();
    }

    public Draggable (Image img){
        super(img);
    }

    public Draggable (FloatTextField X, FloatTextField Y, CGTButtonScreen btn){
        super();
        this.setOnMouseDragged(event-> {
            if(this.getFitWidth() + event.getX() < widthBCKG &&
                    this.getFitHeight() + event.getY() < heightBCKG &&
                    event.getX() > 0 && event.getY() > 0) {
                this.setX(event.getX());
                this.setY(event.getY());
                X.setValue(event.getX() / widthBCKG);
                Y.setValue(1 - (event.getY()) / heightBCKG);
                btn.setRelativeX(X.getValue());
                btn.setRelativeY(Y.getValue() - (float) (getFitHeight()/getHeightBCKG()));
            }
        });
    }

    public Draggable (IntegerTextField X, IntegerTextField Y){
        super();
        this.setOnMouseDragged(event-> {
            if(this.getFitWidth() + event.getX() < widthBCKG &&
                    this.getFitHeight() + event.getY() < heightBCKG &&
                    event.getX() > 0 && event.getY() > 0) {
                this.setX(event.getX());
                this.setY(event.getY());
                X.setValue((int) event.getX());
                Y.setValue((int) heightBCKG - (int) event.getY());
                System.out.printf("%d %d\n",(int) widthBCKG,(int) heightBCKG);
            }
        });
    }

    public Draggable(IntegerTextField X, IntegerTextField Y, Rectangle a, Rectangle b){
        this(X, Y);
        a.xProperty().bind(this.xProperty());
        a.yProperty().bind(this.yProperty());
        b.xProperty().bind(this.xProperty());
        b.yProperty().bind(this.yProperty());
    }

    public void setWidthBCKG(double w){
        this.widthBCKG = w;
    }
    public void setHeightBCKG(double h){
        this.heightBCKG = h;
    }
    public double getWidthBCKG(){return  widthBCKG;}
    public double getHeightBCKG(){return  heightBCKG;}
}

