package Normal;

import java.awt.*;

/**
 * Created by oskar on 2016-12-07.
 * This classes has some inputs and outputs
 */
public class Tile {
    private Image image;
    private int x;
    private int y;

    public Tile(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
