package br.edu.ifce.cgt.application.util;

import br.edu.ifce.cgt.application.controller.ui.FloatTextField;
import br.edu.ifce.cgt.application.controller.ui.IntegerTextField;
import cgt.hud.CGTButtonScreen;
import cgt.hud.HUDComponent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.awt.*;


/**
 * Created by Edy Junior on 08/10/2015.
 */
public class Draggable extends ImageView {

    private double widthBCKG = 0;
    private double heightBCKG = 0;
    private double WI = 0;
    private double HI = 0;

    public Draggable() {
        super();
    }

    public Draggable(Image img) {
        super(img);
    }

    public Draggable(FloatTextField X, FloatTextField Y, HUDComponent hud) {
        super();

        this.setOnMouseDragged(event -> {
            //System.out.printf("%f %f %f %f\n",event.getX(),event.getY(),widthBCKG,heightBCKG);
            if (this.getFitWidth() + event.getX() < widthBCKG &&
                    this.getFitHeight() + event.getY() < heightBCKG &&
                    event.getX() > 0 && event.getY() > 0) {

                this.setX(event.getX());
                this.setY(event.getY());
                X.setValue(event.getX() / widthBCKG);
                Y.setValue(1 - (event.getY()) / heightBCKG);
                hud.setRelativeX(X.getValue());
                hud.setRelativeY(Y.getValue() - (float) (getFitHeight() / getHeightBCKG()));
            }
        });
    }

    public Draggable(IntegerTextField X, IntegerTextField Y) {
        super();
        this.setOnMouseDragged(event -> {
            if (this.getFitWidth() + event.getX() < widthBCKG &&
                    this.getFitHeight() + event.getY() < heightBCKG &&
                    event.getX() > 0 && event.getY() > 0) {
                this.setX(event.getX());
                this.setY(event.getY());
                X.setValue((int) event.getX());
                Y.setValue((int) heightBCKG - (int) event.getY());// + (float) this.prefHeight().getFitHeight());
            }
        });
    }

    public Draggable(IntegerTextField X, IntegerTextField Y, Rectangle a,
                     Rectangle b, IntegerTextField sX, IntegerTextField sY) {
        super();
        this.setOnMouseDragged(event -> {
            if (//this.getFitWidth() + event.getX() < widthBCKG &&
                    //this.getFitHeight() + event.getY() < heightBCKG &&
                    event.getX() > 0 && event.getY() > 0) {
                this.setX(event.getX());
                this.setY(event.getY());
                b.setX(event.getX() + sX.getValue());
                b.setY(event.getY() - sY.getValue());
                X.setValue((int) (event.getX() * (getWI()/getWidthBCKG())));
                Y.setValue((int) ((heightBCKG -  (event.getY())) * (getHI()/getHeightBCKG())));
            }
        });
        a.xProperty().bind(this.xProperty());
        a.yProperty().bind(this.yProperty());

    }

    public void setWidthBCKG(double w) {
        this.widthBCKG = w;
    }

    public void setHeightBCKG(double h) {
        this.heightBCKG = h;
    }

    public double getWidthBCKG() {
        return widthBCKG;
    }

    public double getHeightBCKG() {
        return heightBCKG;
    }

    public void setWI(double w) {
        this.WI = w;
    }

    public void setHI(double h) {
        this.HI = h;
    }

    public double getWI() {return WI;}

    public double getHI() {
        return HI;
    }
}