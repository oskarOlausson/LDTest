/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.*;
import Normal.mail.Mail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Mover extends Placeable {

    public Mover(int x, int y) {
        super(new Sprite("mover", 50), new Position(x, y));
    }

    @Override
    public void sense(Collection<Mail> sensed, Tile tile) {
        if (sensed.size() == 0) return;
        List<Tile> list = new ArrayList<>();
        tile.moveMaid(list, getDirection());
    }

    @Override
    public void tick() {
        sprite.update();
    }

    @Override
    public void step() {

    }

}
