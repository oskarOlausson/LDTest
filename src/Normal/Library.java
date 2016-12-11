package Normal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Library {



    //standard images
    public static BufferedImage loadImage(String string) {
        ImageRes res = new ImageRes(string);
        return loadImage(res);
    }

    public static BufferedImage loadImage(ImageRes res) {
        BufferedImage img = null;

        try {
            if (!res.isValid()) throw new IOException();
            else img = ImageIO.read(res.getPath());
        } catch (IOException e) {
            System.err.println("Could not find file at " + res.toString());
        }

        return img;
    }

    //tiles (sub-images really)
    public static BufferedImage loadTile(BufferedImage image, int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }
    public static BufferedImage loadTile(BufferedImage image, int i, int width, int height) {
        final int cols = image.getWidth() / width;

        int x = i % cols;
        int y = i / cols;
        return loadTile(image, x * width, y * height, width, height);
    }
    public static BufferedImage loadTile(String path, int x, int y, int width, int height) {
        BufferedImage sheet = Library.loadImage(path);
        return loadTile(sheet, x, y, width, height);
    }

    public static BufferedImage loadTile(String path, int i, int width, int height) {
        BufferedImage sheet = Library.loadImage(path);
        return loadTile(sheet, i, width, height);
    }

    //sprites as image arrays
    public static BufferedImage[] loadSprite(String path, int width) {
        BufferedImage img = null;
        ImageRes res = new ImageRes(path);
        try {
            if (!res.isValid()) throw new IOException();
            else img = ImageIO.read(res.getPath());
        } catch (IOException e) {
            System.err.println("Could not find file at " + res.toString());
        }

        return loadSprite(img, width);
    }
    public static BufferedImage[] loadSprite(BufferedImage image, int width) {
        final int cols = image.getWidth() / width;
        int height = image.getHeight();
        BufferedImage[] sprites = new BufferedImage[cols];

        for (int i = 0; i < cols; i++) {
            sprites[i] = image.getSubimage(i * width, 0, width, height);
        }

        return sprites;
    }
    public static BufferedImage[] loadSprite(BufferedImage image, int width, int height) {
        final int cols = image.getWidth() / width;
        final int rows = image.getHeight() / height;
        BufferedImage[] sprites = new BufferedImage[rows * cols];

        for (int yy = 0; yy < rows; yy++) {
            for (int xx = 0; xx < cols; xx++) {
                sprites[(yy * cols) + xx] = image.getSubimage(xx * width, yy * height, width, height);
            }
        }
        return sprites;
    }
    public static BufferedImage[] loadSprite(String string, int width, int height) {
        BufferedImage sheet = Library.loadImage(string);
        return loadSprite(sheet, width, height);
    }

    public static BufferedImage tint(BufferedImage img, Color color) {
        return tint(img, color, 1);
    }
    public static BufferedImage tint(BufferedImage img, Color color, double alpha) {
        float r = color.getRed()    / 255f;
        float g = color.getGreen()  / 255f;
        float b = color.getBlue()   / 255f;
        float a = (float) alpha;
        return tint(img, r, g, b, a);
    }

    public static BufferedImage tint(BufferedImage img, float r, float g, float b, float a) {
        BufferedImage tintedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedImage.createGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedImage.getWidth(); i++) {
            for (int j = 0; j < tintedImage.getHeight(); j++) {
                int ax = tintedImage.getColorModel().getAlpha(tintedImage.getRaster().
                        getDataElements(i, j, null));
                int rx = tintedImage.getColorModel().getRed(tintedImage.getRaster().
                        getDataElements(i, j, null));
                int gx = tintedImage.getColorModel().getGreen(tintedImage.getRaster().
                        getDataElements(i, j, null));
                int bx = tintedImage.getColorModel().getBlue(tintedImage.getRaster().
                        getDataElements(i, j, null));
                rx *= r;
                gx *= g;
                bx *= b;
                ax *= a;
                tintedImage.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
            }
        }
        return tintedImage;
    }


    public static Color hsvColor(int hIn, int sIn, int vIn) {

        double h = (hIn * 360 / 255f);
        double s = (sIn / 255f);
        double v = (vIn /255f);

        double c = s * v;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = v - c;

        double r = 0;
        double g = 0;
        double b = 0;

        int decider = (int) (h / 60f);
        switch(decider) {
            case 0:
                r = c; g = x;
                break;
            case 1:
                r = x; g = c;
                break;
            case 2:
                g = c; b = x;
                break;
            case 3:
                g = x; b = c;
                break;
            case 4:
                r = x; b = c;
                break;
            case 5:
                r = c; b = x;
                break;
            default: System.err.println("H not in range 0-255");
        }
        return new Color((int)((r + m) * 255), (int)((g + m) * 255), (int)((b + m) * 255));
    }

}
