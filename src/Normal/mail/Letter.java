/*
 * File: Letter.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.mail;

import Normal.Position;
import Normal.Sprite;

import java.awt.event.MouseEvent;

public class Letter extends Mail {

    public Letter(int x, int y) {
        super(new Sprite("player", 32), new Position(x, y));
    }

    @Override
    public void tick() {

    }

    @Override
    public void step() {

    }

    @Override
    public void click(MouseEvent event) {

    }
}
