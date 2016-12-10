/*
 * File: Wall.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal;

import java.awt.*;

public class Wall {
    private Image image;
    private int x;
    private int y;

    public Wall(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, image, x, y);
    }
}
