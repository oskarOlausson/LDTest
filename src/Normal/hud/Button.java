/*
 * File: Button.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.DrawFunctions;
import Normal.Position;
import Normal.Sprite;
import Normal.placable.Placable;

import java.awt.*;

public class Button {

    private Sprite sprite;
    private Class<? extends Placable> clazz;
    private Position position;
    private Dimension size;

    public Button(String image, Class<? extends Placable> clazz,
                  int x, int y, int width, int height) {
        sprite = new Sprite(image, width);
        this.clazz = clazz;
        position = new Position(x, y);
        size = new Dimension(width, height);
    }

    public void draw(Graphics g, int x, int y) {
        DrawFunctions.drawImage(g, sprite.getImage(), x+position.getDrawX(), y+position.getDrawY(), .7, .7, 0);
    }

    public Class<? extends Placable> getType() {
        return clazz;
    }

    public boolean inside(int x, int y) {
        return getCollisionBox().contains(x, y);
    }

    public Rectangle getCollisionBox() {
        return new Rectangle(position.getDrawX() - (int)size.getWidth() / 2,
                             position.getDrawY() - (int)size.getHeight() / 2,
                             (int)size.getWidth(), (int)size.getHeight());
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getSize() {
        return size;
    }
}
