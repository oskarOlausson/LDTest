package Normal;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Created by oskar on 2016-12-09.
 * This classes has some inputs and outputs
 */
public class Sprite {

    private BufferedImage[] img;
    private double imageSpeed = .1;
    private double imageIndex = 0;

    public Sprite(String imagePath) {
        img = new BufferedImage[1];
        img[0] = Library.loadImage(imagePath);
        imageSpeed = 0;
    }

    public Sprite(String imagePath, int width, int height) {
        img = Library.loadSprite(imagePath, width, height);
    }

    public Sprite(String imagePath, int width) {
        img = Library.loadSprite(imagePath, width);
    }

    public Sprite(BufferedImage image) {
        img = new BufferedImage[1];
        img[0] = image;
        imageSpeed = 0;
    }

    public void setImageSpeed(double imageSpeed) {
        this.imageSpeed = imageSpeed;
    }

    public void update() {
        if (imageSpeed != 0) {
            imageIndex += imageSpeed;

            int floored = (int) Math.floor(imageIndex);

            if (floored >= img.length) {
                imageIndex -= img.length;
            }

            if (floored < 0) {
                imageIndex += img.length;
            }
        }
        //TODO, fix
        if (imageIndex < 0 || imageIndex >= img.length) throw new AssertionError();
    }

    public int getWidth() {
        return img[(int) imageIndex].getWidth(null);
    }

    public int getHeight() {
        return img[(int) imageIndex].getHeight(null);
    }

    public Image getImage() {
        return img[(int) imageIndex];
    }

}
