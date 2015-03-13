package br.edu.ifce.cgt.application.controller.panes;

import br.edu.ifce.cgt.application.util.Config;
import cgt.util.CGTTexture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by luanjames on 26/02/15.
 */
public class ImagePane extends ImageView {
    private Image image;
    private double height;
    private double width;

    public ImagePane() {
        super();

        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
    }

    public ImagePane(CGTTexture texture) {
        this(texture, 256, 256);
    }

    public ImagePane(CGTTexture texture, double width, double height) {
        this();
        this.height = height;
        this.width = width;

        setTexture(texture);
    }

    public void setSize(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public void setTexture(CGTTexture texture) {
        image = new Image("file:"+ Config.BASE+texture.getFile().getFile().getPath());

        if (image.getHeight() > height) {
            setFitHeight(height);
        } else if (image.getWidth() > width) {
            setFitWidth(width);
        }

        setImage(image);
    }
}
