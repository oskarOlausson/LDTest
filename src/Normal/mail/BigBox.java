/*
 * File: Letter.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.DrawFunctions;
import Normal.Entity;
import Normal.Position;
import Normal.Sprite;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BigBox extends Mail {

    public BigBox(int x, int y, boolean international) {
        super(new Position(x, y));

        this.international = international;

        String path = "bigBoxIn";
        if (international) path = "bigBoxOut";
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
    public Entity click(MouseEvent event) {
        return null;
    }

    @Override
    public Type getType() {
        return Type.SMALL_BOX;
    }

    @Override
    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, getImage(), animation.getDrawX(), animation.getDrawY(), 1, 1, direction.toRad());
    }
}
