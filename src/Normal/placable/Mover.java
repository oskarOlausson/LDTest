/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.*;
import Normal.mail.Mail;

import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;

public class Mover extends Placable {

    private static final int MOVE_AMOUNT = 50;

    public Mover(int x, int y) {
        super(new Sprite("mover", 50), new Position(x, y));
    }

    @Override
    public void sense(Collection<Mail> sensed, Tile tile, MailRemovedListener listener) {
        for (Mail mail : sensed) {

            mail.setDirection(getDirection());
            HashMap<Direction, Tile> neighbours = tile.getNeighbours();

            Tile goTo = neighbours.get(getDirection());

            if (goTo == null) continue;
            if (goTo.hasMail()) continue;

            //success
            listener.mailRemoved(mail);
            getDirection().addToPosition(mail.getPosition(), MOVE_AMOUNT);
        }
    }

    @Override
    public void tick() {
        sprite.update();
    }

    @Override
    public void step() {

    }

    @Override
    public Entity click(MouseEvent event) {
        return null;
    }
}
