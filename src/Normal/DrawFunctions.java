package Normal;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by oskar on 2016-12-07.
 * This class has some inputs and outputs
 */
public class DrawFunctions {
    /**
     * Draws an image
     */
    public static void drawImage(Graphics g, Image image, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, x, y, null);
    }

    /**
     * Draws a scaled and rotated version of an image
     * @param scaleX, 1 is normal size
     * @param scaleY, 1 is normal size
     * @param rotation (in radians)
     */
    public static void drawImage(Graphics g, Image image, int x, int y, double scaleX, double scaleY, double rotation) {
        if (scaleX == 0 || scaleY == 0) return;
        Graphics2D g2d = (Graphics2D) g;


        AffineTransform tx;
        AffineTransformOp op;

        int width = image.getWidth(null);
        int height = image.getHeight(null);
        tx = AffineTransform.getScaleInstance(scaleX, scaleY);


        int translateX = 0;
        int translateY = 0;

        if (scaleX < 0) translateX = -width;
        if (scaleY < 0) translateY = -height;

        tx.translate(translateX, translateY);
        tx.concatenate(AffineTransform.getRotateInstance(rotation, width / 2, height / 2));

        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        g2d.drawImage(op.filter((BufferedImage) image, null), (int) (x - Math.abs(scaleX) * width * .5), (int) (y - Math.abs(scaleY) * height * .5), null);
    }


    /**
     * Draw text in middle of rectangle
     */
    public static void drawCenteredText(Graphics g, String text, Rectangle rect) {
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g2d);
        int x = ((int)rect.getWidth() - (int) r.getWidth()) / 2;
        int y = ((int)rect.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, (int)rect.getX()+x, (int)rect.getY()+y);
    }
}
