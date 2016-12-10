/*
 * File: Letter.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.DrawFunctions;
import Normal.Position;
import Normal.Sprite;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Letter extends Mail {

    public Letter(int x, int y, boolean international) {
        super(new Position(x, y));
        this.international = international;

        String path = "postIn";
        if (international) path = "postOut";
        setSprite(new Sprite(path));
    }

    @Override
    public void tick() {
        animate();
    }

    @Override
    public void step() {

    }

    @Override
    public void click(MouseEvent event) {

    }

    @Override
    public Type getType() {
        return Type.LETTER;
    }

    @Override
    public void draw(Graphics g) {

        DrawFunctions.drawImage(g, getImage(), animation.getDrawX(), animation.getDrawY(), 1, 1, direction.toRad());
    }
}
