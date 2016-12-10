/*
 * File: Mover.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.Position;
import Normal.Sprite;
import Normal.mail.Mail;

import java.awt.event.MouseEvent;
import java.util.Collection;

public class Mover extends Placable {

    private static final int MOVE_AMOUNT = 50;

    public Mover(int x, int y) {
        super(new Sprite("mover", 50), new Position(x, y));
    }

    @Override
    public void sense(Collection<Mail> sensed, MailRemovedListener listener) {
        for (Mail mail : sensed) {
            getDirection().addToPosition(mail.getPosition(), MOVE_AMOUNT);
            mail.setDirection(getDirection());
            listener.mailRemoved(mail);
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
    public void click(MouseEvent event) {

    }
}
