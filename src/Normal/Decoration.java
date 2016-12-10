/*
 * File: Wall.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal;

import java.awt.*;

public class Decoration {
    private Image image;
    private int x;
    private int y;

    public Decoration(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
