package cgt.util;

import cgt.game.CGTSpriteSheet;
import javafx.scene.image.Image;

/**
 * Created by jrocha on 14/10/2015.
 */
public class SpriteSheetTile {

    private Image image;

    private int row;

    private int col;

    private double width;

    private double height;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
