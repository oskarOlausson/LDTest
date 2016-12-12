/*
 * File: Button.java
 * Author: Fredrik Johansson
 * Date: 2016-12-11
 */
package Normal.hud;

import Normal.*;

import java.awt.*;

public class Button {

    protected Sprite sprite;
    protected Dimension size;
    protected Position position;
    protected Level level;
    protected Timer timer = new Timer(3);

    public Button(String image, int x, int y, int width, int height, Level level) {
        this.level = level;
        sprite = new Sprite(image, width);
        size = new Dimension(width, height);
        position = new Position(x, y);
    }

    public boolean inside(int x, int y) {
        return getCollisionBox().contains(x, y);
    }

    public Rectangle getCollisionBox() {
        return new Rectangle(position.getDrawX() - (int)size.getWidth() / 2,
                position.getDrawY() - (int)size.getHeight() / 2,
                (int)size.getWidth(), (int)size.getHeight());
    }

    public void tick() {

        timer.update();
        if (level.getPlay()) sprite.setImageIndex(1);
        else sprite.setImageIndex(0);
    }

    public void click() {
        if (timer.isDone()) {
            level.togglePlay();
        }

        timer.restart();
    }

    public void draw(Graphics g, int x, int y) {
        DrawFunctions.drawImage(g, sprite.getImage(), x+position.getDrawX(), y+position.getDrawY(), 1, 1, 0);
    }
}
