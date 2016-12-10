/*
 * File: Placable.java
 * Author: Fredrik Johansson
 * Date: 2016-12-10
 */
package Normal.placable;

import Normal.*;
import Normal.mail.Mail;

import java.util.Collection;
import java.util.HashMap;

public abstract class Placable extends Entity {

    public interface MailRemovedListener {
        void mailRemoved(Mail mail);
    }

    public Placable(Sprite sprite, Position position) {
        super(sprite, position);
    }

    public Placable(Sprite sprite) {
        super(sprite);
    }

    public abstract void sense(Collection<Mail> sensed,
                               Tile tile, MailRemovedListener listener);
}
