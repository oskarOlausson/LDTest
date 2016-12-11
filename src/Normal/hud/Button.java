/*
 * File: Button.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.hud;

import Normal.DrawFunctions;
import Normal.Position;
import Normal.Sprite;
import Normal.placable.Placeable;

import java.awt.*;

public class Button {

    private Sprite sprite;
    private Class<? extends Placeable> clazz;
    private Position position;
    private Dimension size;
    private String title;

    public Button(String image, Class<? extends Placeable> clazz,
                  int x, int y, int width, int height) {
        sprite = new Sprite(image, width, height);
        this.clazz = clazz;
        position = new Position(x, y);
        size = new Dimension(width, height);
        title = image.substring(0, 1).toUpperCase() + image.substring(1);
    }

    public void draw(Graphics g, int x, int y) {
        DrawFunctions.drawCenteredText(g, title, getTextRectangle(x, y));
        DrawFunctions.drawImage(g, sprite.getImage(), x+position.getDrawX(), y+position.getDrawY(), 1, 1, 0);
    }

    public Class<? extends Placeable> getType() {
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

    public Rectangle getTextRectangle(int x, int y) {
        return new Rectangle(
                x+position.getDrawX()-(int)size.getWidth()*3/4,
                y+position.getDrawY()-(int)size.getHeight()*3/4,
                (int)(size.getWidth()*1.5),
                10);
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getSize() {
        return size;
    }
}
